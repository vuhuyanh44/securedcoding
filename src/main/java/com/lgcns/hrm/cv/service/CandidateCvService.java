package com.lgcns.hrm.cv.service;

import com.lgcns.hrm.cv.entity.CandidatesCv;
import com.lgcns.hrm.cv.model.vo.CandidateCvVoHasLink;
import com.lgcns.hrm.cv.model.vo.CandidatesCvVo;
import com.lgcns.hrm.cv.repository.CandidateCvRepository;
import com.lgcns.hrm.cv.repository.base.BaseRepository;
import com.lgcns.hrm.cv.service.base.AbstractBaseService;
import com.lgcns.hrm.cv.transform.CandidateCvHasLinkTransform;
import com.lgcns.hrm.cv.transform.CandidatesCvTranform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CandidateCvService extends AbstractBaseService<CandidatesCv, String> {

    private final CandidateCvRepository candidateCvRepository;
    private final MinioService minioService;
    private final CandidatesCvTranform candidatesCvTranform;
    private final CandidateCvHasLinkTransform candidateCvHasLinkTransform;

    @Override
    public BaseRepository<CandidatesCv, String> getRepository() {
        return candidateCvRepository;
    }

    @Value("${spring.minio.bucket-name}")
    private String bucketName;

    public CandidatesCv SaveWithFile(MultipartFile file) throws Exception {
        CandidatesCvVo candidatesCv = new CandidatesCvVo();
        String [] fileTypeTmp = file.getOriginalFilename().split("[.]");
        String fileType = fileTypeTmp[fileTypeTmp.length - 1];
        candidatesCv.setBucketName(bucketName);
        candidatesCv.setFileName(file.getOriginalFilename());
        candidatesCv.setFileByte(file.getBytes());
        candidatesCv.setFileSize(file.getSize());
        candidatesCv.setFileType(fileType);
        LocalDate currentDate = LocalDate.now();
        String year = String.valueOf(currentDate.getYear());
        String month = String.valueOf(currentDate.getMonth());
        String date = String.valueOf(currentDate.getDayOfMonth());
        String path = year + "/" + month + "/" + date + "/";
        minioService.createFolder(bucketName, path);
        path = path + System.currentTimeMillis() + "." + fileType;
        candidatesCv.setFilePath(path);
        CandidatesCv cv = candidatesCvTranform.toEntity(candidatesCv);
        try {
            minioService.uploadFile(candidatesCv);
            candidateCvRepository.saveAndFlush(cv);
        } catch (Exception e) {
            candidatesCv.setRetry(1);
        }
        return cv;
    }

    // Find missing File CVs and update
    public void updateMissingCV(Date startDate, Date endDate) {
        log.info("start update missing CV");
        try{
            List<CandidateCvVoHasLink> candidatesCvVoList = findMissingCVCandidates(startDate, endDate);
            log.info("updateMissingCV: Missing cv found: {}" ,candidatesCvVoList.size());
            AtomicInteger updatedRow = new AtomicInteger();
            candidatesCvVoList.forEach(cv -> {
                String linkCV = cv.getDownloadLink();
                String resolveLink = resolveLink(linkCV);
                MultipartFile CVFile = null;
                if (!resolveLink.equals(linkCV)) {
                    CVFile = convertLinkToFile(resolveLink);
                }
                try {
                    if(CVFile != null) {
                        updateCandidateCvWithFile(cv, CVFile);
                        updatedRow.getAndIncrement();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            log.info("updateMissingCV: Updated {} rows", updatedRow.get());
        }
        catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    private String resolveLink(String linkCV) {
        String resolveLink = linkCV;
        try {
            URL url = new URL(linkCV);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false);
            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM) {
                String redirectUrl = connection.getHeaderField("Location");
                resolveLink = redirectUrl == null ? linkCV : redirectUrl;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return resolveLink;
    }

    private MultipartFile convertLinkToFile(String linkCV) {
        String result;
        HttpURLConnection connection;
        MultipartFile attachCV;
        try {
            String topCvApi = linkCV.replace("tuyendung.topcv.vn/app/cvs-management", "tuyendung-api.topcv.vn/api/v1/cv-management");
            StringBuilder sb = new StringBuilder();
            URL url = new URL(topCvApi);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json, text/plain, */*");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            while ((result = br.readLine()) != null) {
                sb.append(result);
            }
            result = sb.toString();
            JSONObject data = new JSONObject(result);
            JSONObject cv = (JSONObject) data.get("cv");
            String downloadUrl = cv.getString("download_url");
            attachCV = downloadFile(downloadUrl);
            return attachCV;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    private MultipartFile downloadFile(String downloadUrl) {
        HttpURLConnection connection;
        InputStream inputStream;
        try {
            URL url = new URL(downloadUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json, text/plain, */*");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            String disposition = connection.getHeaderField("Content-Disposition");
            String fileName = Objects.requireNonNull(disposition).replace("attachment; filename=", "");
            String contentType = connection.getContentType();
            inputStream = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int nRead;
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return new MultipartFile() {
                @NotNull
                @Override
                public String getName() {
                    return fileName;
                }
                @Override
                public String getOriginalFilename() {
                    return fileName;
                }
                @Override
                public String getContentType() {
                    return contentType;
                }
                @Override
                public boolean isEmpty() {
                    return false;
                }
                @Override
                public long getSize() {
                    return buffer.size();
                }
                @NotNull
                @Override
                public byte[] getBytes() {
                    return buffer.toByteArray();
                }
                @NotNull
                @Override
                public InputStream getInputStream() {
                    return new ByteArrayInputStream(buffer.toByteArray());
                }
                @Override
                public void transferTo(@NotNull File dest) throws IOException, IllegalStateException {
                    try (FileOutputStream fos = new FileOutputStream(dest)) {
                        buffer.writeTo(fos);
                    }
                }
            };
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private void updateCandidateCvWithFile(CandidateCvVoHasLink candidateCvVoHasLink, MultipartFile file) throws Exception {
        String [] fileTypeTmp = file.getOriginalFilename().split("[.]");
        String fileType = fileTypeTmp[fileTypeTmp.length - 1];
        candidateCvVoHasLink.setBucketName(bucketName);
        candidateCvVoHasLink.setFileName(file.getOriginalFilename());
        candidateCvVoHasLink.setFileByte(file.getBytes());
        candidateCvVoHasLink.setFileSize(file.getSize());
        candidateCvVoHasLink.setFileType(fileType);
        LocalDate currentDate = LocalDate.now();
        String year = String.valueOf(currentDate.getYear());
        String month = String.valueOf(currentDate.getMonth());
        String date = String.valueOf(currentDate.getDayOfMonth());
        String path = year + "/" + month + "/" + date + "/";
        minioService.createFolder(bucketName, path);
        path = path + System.currentTimeMillis() + "." + fileType;
        candidateCvVoHasLink.setFilePath(path);
        try {
            minioService.uploadFile(CandidatesCvVo.builder().BucketName(candidateCvVoHasLink.getBucketName()).filePath(candidateCvVoHasLink.getFilePath())
                    .fileByte(candidateCvVoHasLink.getFileByte()).fileSize(candidateCvVoHasLink.getFileSize()).build());
            candidateCvRepository.updateCandidatesCv(candidateCvVoHasLink.getFilePath(), candidateCvVoHasLink.getBucketName(),
                    candidateCvVoHasLink.getFileName(), candidateCvVoHasLink.getFileType(), candidateCvVoHasLink.getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            candidateCvVoHasLink.setRetry(1);
        }
    }


    public InputStream download(String id) throws Exception{
        CandidatesCv cv = candidateCvRepository.findById(id).orElse(null);
        if (Objects.isNull(cv))
            return null;

        return minioService.getObj(cv);
    }

    private List<CandidateCvVoHasLink> findMissingCVCandidates(Date startDate, Date endDate) {
        List<CandidatesCv> listFound = candidateCvRepository.findMissingFilePath(startDate, endDate);
        return listFound.stream().map(candidateCvHasLinkTransform::toModel).collect(Collectors.toList());
    }

}

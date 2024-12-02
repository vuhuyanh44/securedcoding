package com.lgcns.hrm.cv.email.extract;

import com.lgcns.hrm.cv.common.constants.ErrorCodes;
import com.lgcns.hrm.cv.common.utils.DateUtil;
import com.lgcns.hrm.cv.common.utils.MimeMessageParser;
import com.lgcns.hrm.cv.common.utils.StringUtil;
import com.lgcns.hrm.cv.entity.Candidates;
import com.lgcns.hrm.cv.entity.CandidatesCv;
import com.lgcns.hrm.cv.exception.EmailException;
import com.lgcns.hrm.cv.service.CandidateCvService;
import com.lgcns.hrm.cv.service.CandidatesService;
import com.lgcns.hrm.cv.service.SysParamService;
import jakarta.activation.DataSource;
import jakarta.mail.Flags;
import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
@RequiredArgsConstructor
public class ExtractExecutor implements Runnable {
    private final CandidateCvService candidateCvService;
    private final CandidatesService candidatesService;
    private final SysParamService sysParamService;
    private final ExtractContent extractContent;
    private final Message message;
    private final StartCreatingContent creatingContent = new StartCreatingContent();

    @Override
    public void run() {
        log.info("start extract email");
        extractMail(this.message);
    }

    private void extractMail(Message message) {
        try {
            final MimeMessage messageToExtract = (MimeMessage) message;
            final MimeMessageParser mimeMessageParser = new MimeMessageParser(messageToExtract).parse();
            var processedSuccessfully = creatingContent.getMailContent(mimeMessageParser);
            if (processedSuccessfully) {
                messageToExtract.setFlag(Flags.Flag.SEEN, true);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static String parseHtml(String html) {
        Document document = Jsoup.parse(html);
        return document.text();
    }

    class StartCreatingContent {

        @Transactional
        public boolean getMailContent(MimeMessageParser mimeMessageParser) {
            try {
                var content = mimeMessageParser.getPlainContent();
                if (content == null) {
                    content = parseHtml(mimeMessageParser.getHtmlContent());
                }
                String subject = mimeMessageParser.getSubject();
                String regex = "\\p{So}+";
                String sj = subject.replaceAll(regex, "").trim();
                var fullName = extractContent.getFullName(content);
                var email = extractContent.getEmail(content);
                var phone = extractContent.getPhone(content);
                var linkCV = extractContent.getLinkCV(mimeMessageParser.getHtmlContent());
                var receivedDate = mimeMessageParser.getMimeMessage().getReceivedDate() == null ? new Date() : mimeMessageParser.getMimeMessage().getReceivedDate();
                var attachCV = !mimeMessageParser.getAttachmentList().isEmpty() ? mimeMessageParser.getAttachmentList().get(0) : null;
                if (linkCV == null && attachCV == null) {
                    return false;
                }
                CandidatesCv candidatesCv;
                if (ExtractType.TOPCV.getLabel().equals(extractContent.getType())) {
                    String resolveLink = resolveLink(linkCV);
                    MultipartFile CVFile = null;
                    if (!resolveLink.equals(linkCV)) {
                        CVFile = convertLinkToFile(resolveLink);
                    }
                    candidatesCv = Objects.isNull(CVFile) ?
                            candidateCvService.save(new CandidatesCv().setDownloadLink(linkCV)) :
                            candidateCvService.SaveWithFile(CVFile);
                } else {
                    candidatesCv = Objects.isNull(attachCV) ?
                            candidateCvService.save(new CandidatesCv().setDownloadLink(linkCV)) :
                            candidateCvService.SaveWithFile(convertToMultipartFile(attachCV));
                }

                saveCandidate(
                        new Candidates()
                                .setName(fullName)
                                .setEmail(email)
                                .setPhoneNumber(phone)
                );
                return true;
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                return false;
            }
        }

        public MultipartFile convertLinkToFile(String linkCV) {
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

        private MultipartFile convertToMultipartFile(DataSource attachCV) {
            return new MultipartFile() {
                @NotNull
                @Override
                public String getName() {
                    return attachCV.getName();
                }

                @Override
                public String getOriginalFilename() {
                    return attachCV.getName();
                }

                @Override
                public String getContentType() {
                    return attachCV.getContentType();
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public long getSize() {
                    try {
                        return attachCV.getInputStream().available();
                    } catch (IOException e) {
                        throw new EmailException(ErrorCodes.IO_EXCEPTION);
                    }
                }

                @NotNull
                @Override
                public byte[] getBytes() throws IOException {
                    return attachCV.getInputStream().readAllBytes();
                }

                @NotNull
                @Override
                public InputStream getInputStream() throws IOException {
                    return attachCV.getInputStream();
                }

                @Override
                public void transferTo(@NotNull File dest) throws IOException, IllegalStateException {
                    try (FileOutputStream fos = new FileOutputStream(dest)) {
                        fos.write(getBytes());
                    }
                }
            };
        }

        private void saveCandidate(Candidates candidates) {
            var timePoint = LocalDate.now().minusMonths(6);
            var candidate = StringUtil.isBlank(candidates.getEmail()) ? Optional.empty() :
                    candidatesService.findCandidateByEmailAndTimePoint(candidates.getEmail(), timePoint);
//            if (candidate.isPresent()) {
//                candidates.setStatus(sysParamService.getParamByTypeAndCode("CV_STATUS", "DUPLICATE"));
//            } // MOVE TO CandidateCv
            candidatesService.save(candidates);
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
}

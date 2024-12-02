package com.lgcns.hrm.cv.service.impl;

import com.lgcns.hrm.cv.entity.CandidatesCv;
import com.lgcns.hrm.cv.model.vo.CandidatesCvVo;
import com.lgcns.hrm.cv.service.MinioService;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MinioServiceImpl implements MinioService {

    @Autowired
    private MinioClient minioClient;
    @Override
    public boolean checkExits(String bucketName) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    @Override
    public void uploadFile(CandidatesCvVo candidatesCv) throws Exception {
        minioClient.putObject(PutObjectArgs.builder().bucket(candidatesCv.getBucketName())
                .object(candidatesCv.getFilePath())
                .stream(new ByteArrayInputStream(candidatesCv.getFileByte()), candidatesCv.getFileSize(), -1).build());
    }

    @Override
    public InputStream getObj(CandidatesCv candidatesCv) throws Exception {
        InputStream stream;
        try {
            stream = minioClient.getObject(GetObjectArgs.builder().bucket(candidatesCv.getBucketName()).object(candidatesCv.getFilePath()).build());
            Map<String, String> reqParams = new HashMap<>();
            reqParams.put("response-content-type", "application/json");

//            String presignedUrl = minioClient.getPresignedObjectUrl(
//                    GetPresignedObjectUrlArgs.builder()
//                            .bucket(candidatesCv.getBucketName())
//                            .object(candidatesCv.getFilePath())
//                            .expiry(60)
//                            .extraQueryParams(reqParams)
//                            .build());
//
//            System.out.println("Presigned URL: " + presignedUrl);
        } catch (Exception e) {
            log.error("Happened error when get obj from minio: ", e);
            return null;
        }
        return stream;
    }

    @Override
    public void createBucket(String bucketName) throws Exception {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public String createFolder(String bucketName, String path) throws Exception{
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(path)
                    .stream(new ByteArrayInputStream(new byte[] {}), 0, -1).build());
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage(), e);
        }

        return bucketName + "/" + path;
    }
}

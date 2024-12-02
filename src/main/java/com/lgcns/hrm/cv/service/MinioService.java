package com.lgcns.hrm.cv.service;

import com.lgcns.hrm.cv.entity.CandidatesCv;
import com.lgcns.hrm.cv.model.vo.CandidatesCvVo;

import java.io.InputStream;

public interface MinioService {

    boolean checkExits(String bucketName) throws Exception;
    void uploadFile(CandidatesCvVo candidatesCv) throws Exception;
    public InputStream getObj(CandidatesCv candidatesCv) throws Exception;
    public void createBucket(String bucketName) throws Exception;
    public String createFolder(String bucketName, String path) throws Exception;
}

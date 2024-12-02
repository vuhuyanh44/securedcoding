package com.lgcns.hrm.cv.repository;

import com.lgcns.hrm.cv.entity.CandidatesCv;
import com.lgcns.hrm.cv.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface CandidateCvRepository extends BaseRepository<CandidatesCv, String> {

    @Query("select c from CandidatesCv c where c.createTime >= :startDate and c.createTime <= :endDate and c.filePath is null")
    List<CandidatesCv> findMissingFilePath(Date startDate, Date endDate);

    @Modifying
    @Transactional
    @Query("update CandidatesCv c set c.filePath = :filePath, c.BucketName = :bucketName, c.fileName = :fileName," +
            "c.fileType = :fileType, c.downloadLink = null where c.id = :id")
    void updateCandidatesCv(String filePath, String bucketName, String fileName, String fileType, String id);
}

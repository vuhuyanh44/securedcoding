package com.lgcns.hrm.cv.model.vo;

import com.lgcns.hrm.cv.common.definition.domain.AbstractVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CandidateCvVoHasLink extends AbstractVo {
    private String id;

    private String candidateId;

    private Long emailNumber;

    private String fileName;

    private String fileType;

    private String filePath;

    private int retry;

    private Boolean status;

    private String BucketName;

    private byte[] fileByte;
    private String fileContent;
    private Long fileSize;
     private String downloadLink;
}

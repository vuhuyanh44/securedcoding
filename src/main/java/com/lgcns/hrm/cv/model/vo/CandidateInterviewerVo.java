package com.lgcns.hrm.cv.model.vo;

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
public class CandidateInterviewerVo {
    private String id;
    private String evaluate;
    private UserVo interviewer;
    private String reason;
}

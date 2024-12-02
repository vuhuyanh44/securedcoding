package com.lgcns.hrm.cv.common.definition.exception;

import com.lgcns.hrm.cv.common.domain.Feedback;
import com.lgcns.hrm.cv.common.domain.Result;

public interface HrmException {

    Feedback getFeedback();

    Result<String> getResult();
}
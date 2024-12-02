package com.lgcns.hrm.cv.common.exception.feedback;

import com.lgcns.hrm.cv.common.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;

public class PreconditionFailedFeedback extends Feedback {
    public PreconditionFailedFeedback(String value) {
        super(value, HttpStatus.SC_PRECONDITION_FAILED);
    }

    public PreconditionFailedFeedback(String value, int custom) {
        super(value, HttpStatus.SC_PRECONDITION_FAILED, custom);
    }
}

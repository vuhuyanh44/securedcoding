package com.lgcns.hrm.cv.common.exception.feedback;

import com.lgcns.hrm.cv.common.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;

public class MethodNotAllowedFeedback extends Feedback {
    public MethodNotAllowedFeedback(String value) {
        super(value, HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    public MethodNotAllowedFeedback(String value, int custom) {
        super(value, HttpStatus.SC_METHOD_NOT_ALLOWED, custom);
    }
}

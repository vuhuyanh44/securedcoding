package com.lgcns.hrm.cv.common.exception.feedback;


import com.lgcns.hrm.cv.common.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;

public class ForbiddenFeedback extends Feedback {
    public ForbiddenFeedback(String value) {
        super(value, HttpStatus.SC_FORBIDDEN);
    }

    public ForbiddenFeedback(String value, int custom) {
        super(value, HttpStatus.SC_FORBIDDEN, custom);
    }
}

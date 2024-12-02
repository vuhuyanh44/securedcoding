package com.lgcns.hrm.cv.common.exception.feedback;


import com.lgcns.hrm.cv.common.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;

public class UnauthorizedFeedback extends Feedback {
    public UnauthorizedFeedback(String value) {
        super(value, HttpStatus.SC_UNAUTHORIZED);
    }

    public UnauthorizedFeedback(String value, int custom) {
        super(value, HttpStatus.SC_UNAUTHORIZED, custom);
    }
}

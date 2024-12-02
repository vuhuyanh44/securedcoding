package com.lgcns.hrm.cv.common.exception.feedback;

import com.lgcns.hrm.cv.common.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;

public class NotAcceptableFeedback extends Feedback {
    public NotAcceptableFeedback(String value) {
        super(value, HttpStatus.SC_NOT_ACCEPTABLE);
    }

    public NotAcceptableFeedback(String value, int custom) {
        super(value, HttpStatus.SC_NOT_ACCEPTABLE, custom);
    }
}

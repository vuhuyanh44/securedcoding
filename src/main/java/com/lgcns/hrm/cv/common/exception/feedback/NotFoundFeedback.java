package com.lgcns.hrm.cv.common.exception.feedback;

import com.lgcns.hrm.cv.common.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;

public class NotFoundFeedback extends Feedback {

    public NotFoundFeedback(String message) {
        super(message, HttpStatus.SC_NOT_FOUND);
    }

    public NotFoundFeedback(String message, int custom) {
        super(message, HttpStatus.SC_NOT_FOUND, custom);
    }
}

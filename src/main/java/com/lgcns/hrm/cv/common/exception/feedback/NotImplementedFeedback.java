package com.lgcns.hrm.cv.common.exception.feedback;


import com.lgcns.hrm.cv.common.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;

public class NotImplementedFeedback extends Feedback {
    public NotImplementedFeedback(String value) {
        super(value, HttpStatus.SC_NOT_IMPLEMENTED);
    }

    public NotImplementedFeedback(String value, int custom) {
        super(value, HttpStatus.SC_NOT_IMPLEMENTED, custom);
    }
}

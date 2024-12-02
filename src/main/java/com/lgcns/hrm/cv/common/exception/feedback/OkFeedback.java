package com.lgcns.hrm.cv.common.exception.feedback;

import com.lgcns.hrm.cv.common.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;

public class OkFeedback extends Feedback {
    public OkFeedback(String value) {
        super(value, HttpStatus.SC_OK);
    }

    public OkFeedback(String value, int custom) {
        super(value, HttpStatus.SC_OK, custom);
    }
}

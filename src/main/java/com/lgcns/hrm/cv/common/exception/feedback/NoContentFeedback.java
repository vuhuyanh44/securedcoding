package com.lgcns.hrm.cv.common.exception.feedback;

import com.lgcns.hrm.cv.common.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;

public class NoContentFeedback extends Feedback {
    public NoContentFeedback(String value) {
        super(value, HttpStatus.SC_NO_CONTENT);
    }

    public NoContentFeedback(String value, int custom) {
        super(value, HttpStatus.SC_NO_CONTENT, custom);
    }
}

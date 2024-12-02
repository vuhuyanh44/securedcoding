package com.lgcns.hrm.cv.common.exception.feedback;

import com.lgcns.hrm.cv.common.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;

public class UnsupportedMediaTypeFeedback extends Feedback {
    public UnsupportedMediaTypeFeedback(String value) {
        super(value, HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

    public UnsupportedMediaTypeFeedback(String value, int custom) {
        super(value, HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE, custom);
    }
}

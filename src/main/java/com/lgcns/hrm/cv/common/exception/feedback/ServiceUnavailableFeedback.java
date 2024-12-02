package com.lgcns.hrm.cv.common.exception.feedback;

import com.lgcns.hrm.cv.common.domain.Feedback;
import org.apache.hc.core5.http.HttpStatus;

public class ServiceUnavailableFeedback extends Feedback {
    public ServiceUnavailableFeedback(String value) {
        super(value, HttpStatus.SC_SERVICE_UNAVAILABLE);
    }

    public ServiceUnavailableFeedback(String value, int custom) {
        super(value, HttpStatus.SC_SERVICE_UNAVAILABLE, custom);
    }
}

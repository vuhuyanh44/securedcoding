package com.lgcns.hrm.cv.exception;

import com.lgcns.hrm.cv.common.domain.Feedback;
import com.lgcns.hrm.cv.common.exception.PlatformException;

public class EmailException extends PlatformException {
    public EmailException(String message) {
        super(message);
    }

    public EmailException(Feedback feedback) {
        super(feedback);
    }

    public EmailException(Feedback feedback, String message) {
        super(feedback, message);
    }

    public EmailException(Feedback feedback, String message, Throwable cause) {
        super(feedback, message, cause);
    }

    public EmailException(Feedback feedback, Throwable cause) {
        super(feedback, cause);
    }

    protected EmailException(Feedback feedback, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(feedback, message, cause, enableSuppression, writableStackTrace);
    }
}

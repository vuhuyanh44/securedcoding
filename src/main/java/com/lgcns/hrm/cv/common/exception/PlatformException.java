package com.lgcns.hrm.cv.common.exception;

import com.lgcns.hrm.cv.common.constants.ErrorCodes;
import com.lgcns.hrm.cv.common.definition.exception.AbstractHrmException;
import com.lgcns.hrm.cv.common.domain.Feedback;

public class PlatformException extends AbstractHrmException {

    private final Feedback feedback;

    public PlatformException(String message) {
        this(ErrorCodes.INTERNAL_SERVER_ERROR, message);
    }

    public PlatformException(Feedback feedback) {
        this(feedback, feedback.getMessage());
    }

    public PlatformException(Feedback feedback, String message) {
        this(feedback, message, null);
    }

    public PlatformException(Feedback feedback, String message, Throwable cause) {
        this(feedback, message, cause, false, false);
    }

    public PlatformException(Feedback feedback, Throwable cause) {
        this(feedback, null, cause);
    }

    protected PlatformException(Feedback feedback, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.feedback = feedback;
    }

    @Override
    public Feedback getFeedback() {
        return feedback;
    }
}

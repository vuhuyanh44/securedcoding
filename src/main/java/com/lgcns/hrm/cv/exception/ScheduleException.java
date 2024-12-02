package com.lgcns.hrm.cv.exception;

import com.lgcns.hrm.cv.common.constants.ErrorCodes;
import com.lgcns.hrm.cv.common.domain.Feedback;
import com.lgcns.hrm.cv.common.exception.PlatformException;

public class ScheduleException extends PlatformException {

    public ScheduleException(String message) {
        super(ErrorCodes.SERVICE_UNAVAILABLE, message);
    }

    public ScheduleException(Feedback feedback, String message) {
        super(feedback, message);
    }

    public ScheduleException(Feedback feedback, String message, Throwable cause) {
        super(feedback, message, cause);
    }

    public ScheduleException(Feedback feedback, Throwable cause) {
        super(feedback, cause);
    }

    public ScheduleException(Feedback feedback, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(feedback, message, cause, enableSuppression, writableStackTrace);
    }
}

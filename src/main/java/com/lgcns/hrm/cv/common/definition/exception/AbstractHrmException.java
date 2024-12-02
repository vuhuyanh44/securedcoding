package com.lgcns.hrm.cv.common.definition.exception;

import com.lgcns.hrm.cv.common.domain.Result;

public abstract class AbstractHrmException extends RuntimeException implements HrmException {

    protected AbstractHrmException() {
        super();
    }

    protected AbstractHrmException(String message) {
        super(message);
    }

    protected AbstractHrmException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AbstractHrmException(Throwable cause) {
        super(cause);
    }

    protected AbstractHrmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Result<String> getResult() {
        Result<String> result = Result.failure(getFeedback());
        result.stackTrace(super.getStackTrace());
        result.detail(super.getMessage());
        return result;
    }
}

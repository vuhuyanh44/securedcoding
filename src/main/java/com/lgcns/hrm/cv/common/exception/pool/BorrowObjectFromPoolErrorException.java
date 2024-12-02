package com.lgcns.hrm.cv.common.exception.pool;

import com.lgcns.hrm.cv.common.constants.ErrorCodes;
import com.lgcns.hrm.cv.common.domain.Feedback;
import com.lgcns.hrm.cv.common.exception.PlatformException;

public class BorrowObjectFromPoolErrorException extends PlatformException {
    public BorrowObjectFromPoolErrorException() {
        super(ErrorCodes.BORROW_OBJECT_FROM_POOL_ERROR_EXCEPTION);
    }

    public BorrowObjectFromPoolErrorException(String message) {
        super(ErrorCodes.BORROW_OBJECT_FROM_POOL_ERROR_EXCEPTION,message);
    }

    public BorrowObjectFromPoolErrorException(String message, Throwable cause) {
        super(ErrorCodes.BORROW_OBJECT_FROM_POOL_ERROR_EXCEPTION, message, cause);
    }

    public BorrowObjectFromPoolErrorException(Throwable cause) {
        super(ErrorCodes.BORROW_OBJECT_FROM_POOL_ERROR_EXCEPTION, cause);
    }

    protected BorrowObjectFromPoolErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ErrorCodes.BORROW_OBJECT_FROM_POOL_ERROR_EXCEPTION, message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return ErrorCodes.BORROW_OBJECT_FROM_POOL_ERROR_EXCEPTION;
    }
}

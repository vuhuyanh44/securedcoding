package com.lgcns.hrm.cv.exception;

import java.util.Objects;

public class ResourceException extends Exception {
    private static final long serialVersionUID = 1L;

    private String code;

    private String message;
    private String param;
    private Object[] params;

    public ResourceException() {
        super();
    }

    public ResourceException(String rs_msg) {
        super(rs_msg);
        this.message = rs_msg;
    }

    public ResourceException(String message, Object[] params) {
        super(message);
        this.message = message;
        this.params = params;
    }

    public ResourceException(String rs_msg, String param) {
        super(rs_msg);
        this.message = rs_msg;
        this.param = param;
    }

    public ResourceException(String code, String rs_msg, String param) {
        super(rs_msg);
        this.code = code;
        this.message = rs_msg;
        this.param = param;
    }

    public ResourceException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ResourceException(Throwable throwable) {
        super(throwable);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getParam() {
        return param;
    }

    public Object[] getParams() {
        return params;
    }

    public String transformMessage() {
        if (message == null)
            return null;
        if (!Objects.isNull(param))
            return String.format(message, param);
        if (!Objects.isNull(params))
            return String.format(message, params);
        return message;
    }
}

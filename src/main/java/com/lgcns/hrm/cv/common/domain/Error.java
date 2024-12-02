package com.lgcns.hrm.cv.common.domain;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(title = "Response error details", description = "Validation error information entity added for compatibility with Validation")
public class Error implements Serializable {

    @Schema(title = "Exception complete information", type = "string")
    private String detail;

    @Schema(title = "Additional error information, currently mainly Validation Message")
    private String message;

    @Schema(title = "Additional error codes, currently mainly Validation Code")
    private String code;

    @Schema(title = "Additional error fields, currently mainly Validation Field")
    private String field;

    @Schema(title = "Error stack information")
    private StackTraceElement[] stackTrace;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public StackTraceElement[] getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(StackTraceElement[] stackTrace) {
        this.stackTrace = stackTrace;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("detail", detail)
                .add("message", message)
                .add("code", code)
                .add("field", field)
                .toString();
    }
}
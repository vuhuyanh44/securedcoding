package com.lgcns.hrm.cv.common.domain;

import com.google.common.base.Objects;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class Feedback implements Serializable {

    private final String message;
    private final int status;
    private final int custom;

    public Feedback(String message, int status) {
        this(message, status, 0);
    }

    public Feedback(String message, int status, int custom) {
        this.message = message;
        this.status = status;
        this.custom = custom;
    }

    public boolean isCustom() {
        return custom == 0;
    }

    public int getSequence() {
        if (isCustom()) {
            return status * 100;
        } else {
            return custom + 10000;
        }
    }

    public int getSequence(int index) {
        return getSequence() + index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Feedback feedback = (Feedback) o;
        return Objects.equal(message, feedback.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }
}

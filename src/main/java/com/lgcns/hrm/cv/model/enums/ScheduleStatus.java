package com.lgcns.hrm.cv.model.enums;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum ScheduleStatus {
    S("Success"),
    F("Fail"),
    A("ACTIVE"),
    I("INACTIVE");

    private String label;

    ScheduleStatus(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static String getLabel(String val) {
        if (Strings.isEmpty(val)) {
            return null;
        }
        Optional<ScheduleStatus> scheduleStatus = Arrays.stream(ScheduleStatus.values())
                .filter(s -> s.name().equals(val)).findFirst();
        return scheduleStatus.map(ScheduleStatus::getLabel).orElse(null);
    }
}

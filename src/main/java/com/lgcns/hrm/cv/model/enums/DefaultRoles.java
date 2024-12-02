package com.lgcns.hrm.cv.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DefaultRoles {
    SUPER_ADMIN("SUPER_ADMIN"),
    ADMIN("ADMIN"),
    USER("USER");
    private String label;

    public static String getLabel(String val) {
        if (Strings.isEmpty(val)) {
            return null;
        }
        Optional<ScheduleStatus> scheduleStatus = Arrays.stream(ScheduleStatus.values())
                .filter(s -> s.name().equals(val)).findFirst();
        return scheduleStatus.map(ScheduleStatus::getLabel).orElse(null);
    }
}

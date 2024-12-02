package com.lgcns.hrm.cv.email.extract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExtractType {
    TOPCV("TopCV"),
    ITVIEC("ItViet"),
    TOPDEV("TopDev"),
    VIETNAMWORKS("VietNamWorks"),
    GDCRECRUITMENT("GDC Recruitment"),
    GDCMAIL("GDC Mail");
    private String label;

    public static ExtractType getExtractType(String val) {
        if (Strings.isEmpty(val)) {
            return null;
        }
        return Arrays.stream(ExtractType.values())
                .filter(s -> s.getLabel().equals(val)).findFirst().orElse(null);
    }
}

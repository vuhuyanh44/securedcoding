package com.lgcns.hrm.cv.email.extract;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ExtractFactory {

    public static ExtractContent getExtractContent(ExtractType extractType) {
        return switch (extractType) {
            case TOPCV -> new TopCV();
            case ITVIEC -> new ITViec();
            case TOPDEV -> new TopDev();
            case VIETNAMWORKS -> new VietNamWorks();
            case GDCRECRUITMENT -> new GDCRecruitment();
            case GDCMAIL -> new GDCMail();
            default -> throw new IllegalArgumentException("This bank type is unsupported");
        };
    }

}

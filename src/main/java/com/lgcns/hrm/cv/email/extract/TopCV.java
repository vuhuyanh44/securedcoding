package com.lgcns.hrm.cv.email.extract;

import java.util.Objects;

public class TopCV implements ExtractContent {
    private static final String FULL_NAME_START_POINT = "Ứng viên";
    private static final String FULL_NAME_END_POINT = "vừa ứng tuyển cho công việc";


    private static final String LINK_CV_START_POINT = "Xem và Duyệt CV ứng viên: <a href=";
    private static final String LINK_CV_END_POINT = "target";

    @Override
    public String getFullName(String content) {
        var startPoint = content.lastIndexOf(FULL_NAME_START_POINT) + FULL_NAME_START_POINT.length();
        var endPoint = content.lastIndexOf(FULL_NAME_END_POINT);
        return Objects.isNull(content.substring(startPoint, endPoint)) ? "" : content.substring(startPoint, endPoint).trim();
    }

    @Override
    public String getEmail(String content) {
        return null;
    }

    @Override
    public String getLinkCV(String content) {
        var startPoint = content.lastIndexOf(LINK_CV_START_POINT) + LINK_CV_START_POINT.length();
        var endPoint = content.indexOf(LINK_CV_END_POINT, startPoint);
        String linkCV = content.substring(startPoint, endPoint).trim();
        return linkCV.substring(1, linkCV.length() - 1);
    }

    @Override
    public String getPhone(String content) {
        return null;
    }

    @Override
    public String getType() {
        return ExtractType.TOPCV.getLabel();
    }
}

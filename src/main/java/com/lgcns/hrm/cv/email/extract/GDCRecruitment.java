package com.lgcns.hrm.cv.email.extract;

public class GDCRecruitment implements ExtractContent {

    private static final String FULL_NAME_START_POINT = "Your job has a new applicant";

    @Override
    public String getFullName(String content) {
        try {
            var startPoint = content.indexOf(FULL_NAME_START_POINT) + FULL_NAME_START_POINT.length();
            var newContent = content.substring(startPoint).trim();
            var endPoint = newContent.indexOf("\n");
            return newContent.substring(0, endPoint).trim();
        } catch (Exception e) {
           return null;
        }
    }

    @Override
    public String getEmail(String content) {
        return null;
    }

    @Override
    public String getLinkCV(String content) {
        return null;
    }

    @Override
    public String getPhone(String content) {
        return null;
    }

    @Override
    public String getType() {
        return ExtractType.GDCRECRUITMENT.getLabel();
    }
}

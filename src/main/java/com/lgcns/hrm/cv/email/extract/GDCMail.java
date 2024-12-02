package com.lgcns.hrm.cv.email.extract;

public class GDCMail implements ExtractContent {
    private static final String FULL_NAME_START_POINT = "From: ";
    private static final String FULL_NAME_END_POINT = " <";
    private static final String EMAIL_START_POINT = "<";
    private static final String EMAIL_END_POINT = ">";

    @Override
    public String getFullName(String content) {
        try {
            var startPoint = content.indexOf(FULL_NAME_START_POINT) + FULL_NAME_START_POINT.length();
            var endPoint = content.indexOf(FULL_NAME_END_POINT);
            return content.substring(startPoint, endPoint).trim();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getEmail(String content) {
        try {
            var startPoint = content.indexOf(EMAIL_START_POINT) + EMAIL_START_POINT.length();
            var endPoint = content.indexOf(EMAIL_END_POINT);
            return content.substring(startPoint, endPoint).trim();
        } catch (Exception e) {
            return null;
        }
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
        return ExtractType.GDCMAIL.getLabel();
    }
}

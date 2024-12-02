package com.lgcns.hrm.cv.email.extract;

import com.lgcns.hrm.cv.common.utils.StringUtil;

import java.util.Objects;

public class ITViec implements ExtractContent {
    private static final String FULL_NAME_START_POINT = "Name:";
    private static final String FULL_NAME_END_POINT = "Email:";

    private static final String FULL_EMAIL_START_POINT = "Email:";
    private static final String FULL_EMAIL_END_POINT = "Question:";

    @Override
    public String getFullName(String content) {
        var fullName = StringUtil.subBetween(content, FULL_NAME_START_POINT, FULL_NAME_END_POINT);
        return Objects.isNull(fullName) ? "" : fullName.trim();
    }

    @Override
    public String getEmail(String content) {
        var email = StringUtil.getEmailFromStr(content);
        return email;
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
        return ExtractType.ITVIEC.getLabel();
    }


}

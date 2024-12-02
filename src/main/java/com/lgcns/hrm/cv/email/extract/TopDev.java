package com.lgcns.hrm.cv.email.extract;

import com.lgcns.hrm.cv.common.utils.StringUtil;

import java.util.Objects;

public class TopDev implements ExtractContent{
    private static final String FULL_NAME_START_POINT = "Fullname:";
    private static final String FULL_NAME_END_POINT = "Email:";

    private static final String FULL_EMAIL_START_POINT = "Email:";
    private static final String FULL_EMAIL_END_POINT = "Mobile:";

    private static final String PHONE_START_POINT = "Mobile:";
    private static final String PHONE_END_POINT = "LinkCv:";

    @Override
    public String getFullName(String content) {
        var name = StringUtil.subBetween(content, FULL_NAME_START_POINT, FULL_NAME_END_POINT);
        return Objects.isNull(name) ? "" : name.trim();
    }

    @Override
    public String getEmail(String content) {
        var email = StringUtil.getEmailFromStr(content);
        return email;
    }

    @Override
    public String getPhone(String content) {
        var phone = StringUtil.subBetween(content, PHONE_START_POINT, PHONE_END_POINT);
        return Objects.isNull(phone) ? "" : phone.trim();
    }

    @Override
    public String getLinkCV(String content) {
        return null;
    }

    @Override
    public String getType() {
        return ExtractType.TOPDEV.getLabel();
    }
}

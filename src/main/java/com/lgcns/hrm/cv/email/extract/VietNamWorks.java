package com.lgcns.hrm.cv.email.extract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class VietNamWorks implements ExtractContent {
    private static final String FULL_NAME_START_POINT = "for help. ";
    private static final String FULL_NAME_END_POINT_VI = "đã ứng tuyển vào vị trí";
    private static final String FULL_NAME_END_POINT_EN = "has applied for";

    @Override
    public String getFullName(String content) {
        var startPoint = content.lastIndexOf(FULL_NAME_START_POINT) + FULL_NAME_START_POINT.length();
        var endPoint = content.lastIndexOf(FULL_NAME_END_POINT_VI);
        if (endPoint == -1) {
            endPoint = content.lastIndexOf(FULL_NAME_END_POINT_EN);
        }
        return content.substring(startPoint, endPoint).trim();
    }

    @Override
    public String getEmail(String content) {
        return null;
    }

    @Override
    public String getLinkCV(String content) {
        Document doc = Jsoup.parse(content);
        Elements links = doc.select("a");
        if (links.size() >= 7) {
            return links.get(6).attr("href");
        }
        return null;
    }

    @Override
    public String getPhone(String content) {
        return null;
    }

    @Override
    public String getType() {
        return ExtractType.VIETNAMWORKS.getLabel();
    }
}

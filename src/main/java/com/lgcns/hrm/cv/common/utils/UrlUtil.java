package com.lgcns.hrm.cv.common.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

public class UrlUtil extends org.springframework.web.util.UriUtils {

    public static String encodeURL(String source, Charset charset) {
        return UrlUtil.encode(source, charset.name());
    }

    public static String decodeURL(String source, Charset charset) {
        return UrlUtil.decode(source, charset.name());
    }

    public static String getPath(String uriStr) {
        URI uri;

        try {
            uri = new URI(uriStr);
        } catch (URISyntaxException var3) {
            throw new RuntimeException(var3);
        }

        return uri.getPath();
    }

}


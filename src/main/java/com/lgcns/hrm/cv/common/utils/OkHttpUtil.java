package com.lgcns.hrm.cv.common.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.util.Map;

@Slf4j
public class OkHttpUtil {

    public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static MediaType XML = MediaType.parse("application/xml; charset=utf-8");


    public static String get(String url, Map<String, String> queries) {
        return get(url, null, queries);
    }

    public static String get(String url, Map<String, String> header, Map<String, String> queries) {
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && !queries.keySet().isEmpty()) {
            sb.append("?clientId=blade");
            queries.forEach((k, v) -> sb.append("&").append(k).append("=").append(v));
        }

        Request.Builder builder = new Request.Builder();

        if (header != null && !header.keySet().isEmpty()) {
            header.forEach(builder::addHeader);
        }

        Request request = builder.url(sb.toString()).build();
        return getBody(request);
    }

    public static String post(String url, Map<String, String> params) {
        return post(url, null, params);
    }


    public static String post(String url, Map<String, String> header, Map<String, String> params) {
        FormBody.Builder formBuilder = new FormBody.Builder().add("clientId", "blade");
        if (params != null && !params.keySet().isEmpty()) {
            params.forEach(formBuilder::add);
        }

        Request.Builder builder = new Request.Builder();

        if (header != null && !header.keySet().isEmpty()) {
            header.forEach(builder::addHeader);
        }

        Request request = builder.url(url).post(formBuilder.build()).build();
        return getBody(request);
    }

    public static String postJson(String url, String json) {
        return postJson(url, null, json);
    }

    public static String postJson(String url, Map<String, String> header, String json) {
        return postContent(url, header, json, JSON);
    }


    public static String postXml(String url, String xml) {
        return postXml(url, null, xml);
    }

    public static String postXml(String url, Map<String, String> header, String xml) {
        return postContent(url, header, xml, XML);
    }

    public static String postContent(String url, Map<String, String> header, String content, MediaType mediaType) {
        RequestBody requestBody = RequestBody.create(content, mediaType);
        Request.Builder builder = new Request.Builder();

        if (header != null && !header.keySet().isEmpty()) {
            header.forEach(builder::addHeader);
        }
        Request request = builder.url(url).post(requestBody).build();
        return getBody(request);
    }

    private static String getBody(Request request) {
        String responseBody = "";
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                return response.body().string();
            }
        } catch (Exception e) {
            log.error("okhttp3 post error >> ex = {}", e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

}

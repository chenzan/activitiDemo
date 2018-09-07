package com.act.demo.util;

import com.squareup.okhttp.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpUtil {
    private static final long TIME_OUT = 15000;
    private static OkHttpClient mOkHttpClient;

    /**
     * post请求
     *
     * @param url
     * @return
     */
    public static String sendPost(String url, String tag, MultipartBuilder multipartBuilder) {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
            mOkHttpClient.setConnectTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
            mOkHttpClient.setReadTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
        }
        if (multipartBuilder == null) {
            multipartBuilder = new MultipartBuilder();
        }
        RequestBody formBody = multipartBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .tag(tag)
                .addHeader("User-Agent", "cz")
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                log.info(result);
                return result;
            } else {
                log.info(response.toString());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage() + " ");
        }
        return null;
    }

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static String sendGet(String url, String tag) {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
            mOkHttpClient.setConnectTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
            mOkHttpClient.setReadTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
        }
        Request request = new Request.Builder()
                .url(url)
                .tag(tag)
                .addHeader("User-Agent", "cz")
                .build();
        Response response = null;
        try {
            response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                log.info(response.message());
                log.info(response.toString());
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 判断是否是ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxWithRequest(HttpServletRequest request) {
        if (null != request) {
            if (null != request.getHeader("x-requested-with")) {
                return request.getHeader("x-requested-with").equals("XMLHttpRequest");
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * 获取请求ip
     *
     * @param request
     * @return
     */
    public static String getRequestAddr(HttpServletRequest request) {
        if (request == null) {
            return "";
        } else {
            String ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip)) {
                ip = request.getHeader("x-real-ip");
            }

            if (StringUtils.isEmpty(ip)) {
                ip = request.getRemoteAddr();
            }

            if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)) {
                ip = "本地主机";
            }

            return ip;
        }
    }

    /**
     * 根据标识取消请求
     *
     * @param tag
     */
    public static void cancelByTag(String tag) {
        if (mOkHttpClient != null)
            mOkHttpClient.cancel(tag);
    }

    /**
     * 获取浏览器类型
     *
     * @param request
     * @return
     */
    public static String getBrowser(HttpServletRequest request) {
        String browser = "未知";
        if (request != null) {
            try {
                String ua = getUserAgent(request);
                int idx;
                if (ua.contains("MSIE")) {
                    idx = ua.indexOf("MSIE");
                    browser = ua.substring(idx, ua.indexOf(";", idx));
                } else if (ua.contains("Edge/")) {
                    idx = ua.indexOf("Edge/");
                    browser = ua.substring(idx);
                } else if (ua.contains("gecko") && ua.contains("rv:11.0")) {
                    browser = "MSIE 11.0";
                } else if (ua.contains("OPR/")) {
                    idx = ua.indexOf("OPR/");
                    browser = ua.substring(idx);
                    browser = browser.replace("OPR", "Opera");
                } else if (ua.contains("Chrome/")) {
                    idx = ua.indexOf("Chrome");
                    browser = ua.substring(idx, ua.indexOf(" ", idx));
                } else if (ua.contains("Firefox/")) {
                    idx = ua.indexOf("Firefox/");
                    browser = ua.substring(idx);
                } else if (ua.contains("Safari/")) {
                    idx = ua.indexOf("Safari/");
                    browser = ua.substring(idx);
                }
            } catch (Exception var4) {
                browser = "未知";
            }
        }

        return browser;
    }

    /**
     * 获取请求平台
     *
     * @param request
     * @return
     */
    public static String getPlatform(HttpServletRequest request) {
        String platform = "未知";
        if (request != null) {
            String ua = getUserAgent(request);
            if (ua.contains("Windows Phone")) {
                platform = "Windows Phone";
            } else if (ua.contains("Windows")) {
                platform = "Windows";
                if (ua.contains("Windows NT 10.0")) {
                    platform = "Windows 10";
                } else if (ua.contains("Windows NT 6.3")) {
                    platform = "Windows 8.1";
                } else if (ua.contains("Windows NT 6.2")) {
                    platform = "Windows 8";
                } else if (ua.contains("Windows NT 6.1")) {
                    platform = "Windows 7";
                } else if (ua.contains("Windows NT 6.0")) {
                    platform = "Windows Vista";
                } else if (ua.contains("Windows NT 5.1")) {
                    platform = "Windows XP";
                }
            } else if (ua.contains("iPad")) {
                platform = "iPad";
            } else if (ua.contains("iPhone")) {
                platform = "iPhone";
            } else if (ua.contains("Android")) {
                platform = "Android";
            } else if (ua.contains("Linux")) {
                platform = "Linux";
            }
        }

        return platform;
    }

    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = "";
        if (request != null) {
            userAgent = request.getHeader("user-agent");
        }

        return userAgent;
    }
}

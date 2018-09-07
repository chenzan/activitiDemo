package com.act.demo.support;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class ResponseResult {
    /**
     * 成功
     *
     * @return
     */
    public static JSONObject success() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", true);
        return jsonObject;
    }

    /**
     * 成功
     *
     * @return
     */
    public static JSONObject success(Object data) {
        JSONObject successObj = success();
        successObj.put("data", data);
        return successObj;
    }

    /**
     * 错误
     *
     * @param errorMsg 错误信息
     * @return
     */
    public static JSONObject error(String errorMsg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", false);
        jsonObject.put("errorMsg", errorMsg);
        return jsonObject;
    }

    /**
     * 错误
     *
     * @param errorMsg 错误信息
     * @param ext      额外参数信息
     * @return
     */
    public static JSONObject error(String errorMsg, Map<String, Object> ext) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", false);
        jsonObject.put("errorMsg", errorMsg);
        for (Map.Entry<String, Object> entry : ext.entrySet()) {
            jsonObject.put(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }

    /**
     * 错误
     *
     * @param errorMsg  错误信息
     * @param errorCode 错误码
     * @return
     */
    public static JSONObject error(String errorMsg, int errorCode) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", false);
        jsonObject.put("errorMsg", errorMsg);
        jsonObject.put("errorCode", errorCode);
        return jsonObject;
    }

    /**
     * web请求返回json
     *
     * @param response
     * @param json
     */
    public static void renderJson(HttpServletResponse response, JSONObject json) {
        try {
            response.setContentType("text/json");
            PrintWriter out = response.getWriter();
            out.println(json);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

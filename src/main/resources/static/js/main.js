//定义全局异步请求
$(document).bind("ajaxComplete", function (event, xhr, settings) {
    //判断响应内容是否是json格式
    if (/^\[|^\{/m.test(xhr.responseText)) {
        try {
            var data = jQuery.parseJSON(xhr.responseText);
            if (data.flag === "sessionTimeout") {//响应超时
                Toast.alert(data.message, function () {
                    top.window.location.reload();
                });
                return false;
            }
            // else if (data.type == "noPermission" || data.type == "needPassword" || data.type == "notEnable" || data.type == "notAllow") {
            //     settings.success({success: false, message: data.message}, xhr.status, xhr);
            //     return false;
            // }
        } catch (e) {
            console.dir(e);
        }
    }
});
$(document).bind("ajaxError", function (event, xhr, settings) {
    if ($.isFunction(settings.success))
        settings.success({success: false, message: '网络请求异常：' + xhr.status}, xhr.status, xhr);
    else
        Toast.error('网络请求异常：' + xhr.status);
});

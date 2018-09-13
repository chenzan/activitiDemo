var ajaxUtil = function () {
    return {
        get: function (url, callback) {
            $.ajax({
                type: "GET",
                url: url,
                success: function (data) {
                    if (typeof(callback) != "function") return;
                    var success = true;
                    if (data.result == false) {
                        success = false;
                        data = data.errorMsg;
                    }
                    callback(success, data);
                },
                error: function (xhr, msg, e) {
                    if (typeof(callback) != "function") return;
                    var data = {result: false, errorMg: "请求异常:" + xhr.status + " " + e};
                    callback(false, data);
                }
            });
        },
        post: function (url, param, callback) { //异步Post提交
            $.ajax({
                type: "POST",
                url: url,
                data: param,
                success: function (data) {
                    if (typeof(callback) != "function") return;
                    var success = true;
                    if (data.result == false) {
                        success = false;
                        data = data.errorMg;
                    }
                    callback(success, data);
                },
                error: function (xhr, msg, e) {
                    if (typeof(callback) != "function") return;
                    var data = {result: false, errorMg: "请求异常:" + xhr.status + " " + e};
                    callback(false, data);
                }
            });
        },
        //同步get请求方法
        syncGet: function (url, callback) {
            $.ajax({
                type: "GET",
                url: url,
                cache: false,
                async: false,
                success: function (data) {
                    if (typeof(callback) != "function") return;
                    var success = true;
                    if (data.result == false) {
                        success = false;
                    }
                    callback(success, data);
                },
                error: function (xhr, msg, e) {
                    if (typeof(callback) != "function") return;
                    var data = {result: false, errorMg: "请求异常:" + xhr.status + " " + e};
                    callback(false, data);
                }
            });
        },
        //同步post请求
        syncPost: function (url, param, callback) {
            $.ajax({
                type: "POST",
                url: url,
                data: param,
                cache: false,
                async: false,
                success: function (data) {
                    if (typeof(callback) != "function") return;
                    var success = true;
                    if (data.result == false) {
                        success = false;
                        data = data.errorMsg;
                    }
                    callback(success, data);
                },
                error: function (xhr, msg, e) {
                    if (typeof(callback) != "function") return;
                    var data = {result: false, errorMsg: "请求异常:" + xhr.status + " " + e};
                    callback(false, data);
                }
            });
        }
    }
}();
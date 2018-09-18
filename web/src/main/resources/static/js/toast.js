/**
 * 消息提示
 * @type {{success: Toast.success, info: Toast.info, warning: Toast.warning, _error: Toast.error, _show: Toast._show, init: Toast.init}}
 * @msg 消息内容 必填
 * @title 消息标题 可选
 * @fn 点击消息触发 可选
 * @center 是否居中显示 true/false 可选，默认false
 */
var Toast = {
    alert: function (msg, title, fn, center) {
        Toast._show("normal", msg, title, fn, center);
    },
    alertCenter: function (msg, title, fn) {
        Toast._show("normal", msg, title, fn, true);
    },
    success: function (msg, title, fn, center) {
        Toast._show("success", msg, title, fn, center);
    },
    successCenter: function (msg, title, fn) {
        Toast._show("success", msg, title, fn, true);
    },
    info: function (msg, title, fn, center) {
        Toast._show("info", msg, title, fn, center);
    },
    infoCenter: function (msg, title, fn) {
        Toast._show("info", msg, title, fn, true);
    },
    warning: function (msg, title, fn, center) {
        Toast._show("warning", msg, title, fn, center);
    },
    warningCenter: function (msg, title, fn) {
        Toast._show("warning", msg, title, fn, true);
    },
    error: function (msg, title, fn, center) {
        Toast._show("error", msg, title, fn, center);
    },
    errorCenter: function (msg, title, fn) {
        Toast._show("error", msg, title, fn, true);
    },
    _show: function (type, msg, title, fn, center) {
        if (fn)
            toastr.options.onclick = fn;
        if (center) {
            toastr.options.hideDuration = 0;
            toastr.clear();
            toastr.options.hideDuration = 300;
            toastr.options.positionClass = "toast-center";
            toastr.options.showMethod = "fadeIn";
            toastr.options.hideMethod = "fadeOut";
        } else {
            toastr.options.positionClass = "toast-top-center";
            toastr.options.showMethod = "slideDown";
            toastr.options.hideMethod = "slideUp";
        }
        var el;
        if (title)
            el = toastr[type](msg, title);
        else
            el = toastr[type](msg);

        //矫正位置
        if (center)
            el.css("marginLeft", (-el.width() / 2) + "px");
    },
    init: function () {
        //引入必要的样式+JS文件
        toastr.options = {
            closeButton: false,
            debug: false,
            positionClass: "toast-top-right",
            onclick: null,
            showDuration: 300,
            hideDuration: 300,
            timeOut: 3000,
            extendedTimeOut: 1000,
            showEasing: 'swing',
            hideEasing: 'linear',
            showMethod: 'show',
            hideMethod: 'hide',
            stack: false
        };
    }
};
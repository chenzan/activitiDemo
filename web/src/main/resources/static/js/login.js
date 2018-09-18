$(document).ready(function () {
    var animating = false,
        submitPhase1 = 1100,
        submitPhase2 = 400,
        logoutPhase1 = 800,
        $login = $(".login"),
        $app = $(".app");

    function ripple(elem, e) {
        $(".ripple").remove();
        var elTop = elem.offset().top,
            elLeft = elem.offset().left,
            x = e.pageX - elLeft,
            y = e.pageY - elTop;
        var $ripple = $("<div class='ripple'></div>");
        $ripple.css({top: y, left: x});
        elem.append($ripple);
    }

    function validate(username, password) {
        if ($.trim(username.val()).length === 0) {
            Toast.alert("请输入用户名");
            username.focus();
            return false;
        }
        if ($.trim(password.val()).length === 0) {
            Toast.alert("请输入密码");
            password.focus();
            return false;
        }
        return true;
    }
    var loginForm = $("#loginForm");

    loginForm.ajaxForm({
        beforeSubmit: function (arr, form, opt) {
            var username = $("#username"), password = $("#password");
            if (!validate(username, password)) {
                return false;
            }
        },
        success: function (data, statusText, xhr, $form) {
            if (data.result === true) {
                loginForm.addClass("success");
                setTimeout(function () {
                    $app.show();
                    $app.css("top");
                    $app.addClass("active");
                }, submitPhase2 - 70);
                setTimeout(function () {
                    $login.hide();
                    $login.addClass("inactive");
                    animating = false;
                    loginForm.removeClass("success processing");

                    setTimeout(function () {
                        window.location.href = "/main/index";
                    }, 1000);
                }, submitPhase2);
            } else {
                Toast.error(data.errorMsg);
                loginForm.removeClass("processing");
            }
        }
    });

    // $(document).on("submit", "#loginForm", function (e) {
    //     var username = $("#username"), password = $("#password");
    //     if (validate(username, password)) {
    //         var that = this;
    //         ripple($(that), e);
    //         $(that).addClass("processing");
    //
    //         //执行登录
    //         $.post("/loginAction", {username: username.val(), password: password.val()}, function (data) {
    //             if (data.success === true) {
    //                 $(that).addClass("success");
    //                 setTimeout(function () {
    //                     $app.show();
    //                     $app.css("top");
    //                     $app.addClass("active");
    //                 }, submitPhase2 - 70);
    //                 setTimeout(function () {
    //                     $login.hide();
    //                     $login.addClass("inactive");
    //                     animating = false;
    //                     $(that).removeClass("success processing");
    //
    //                     setTimeout(function () {
    //                         window.location.href = "$!redirect_url";
    //                     }, 1000);
    //                 }, submitPhase2);
    //             } else {
    //                 Toast._error(data.message);
    //                 $(that).removeClass("processing");
    //             }
    //         });
    //     }
    //     return false;
    // });
});
//进入页面后，通过ajax访问sso工程，认证是否登录
$(function(){
    $.ajax({
        url:"http://localhost:8086/sso/islogin",
        success:function(data){
            //json对象 —> json字符串
            if(data != null){
                //登录
                $("#pid").html(data.name + "您好，欢迎来到<b>ShopCZ商城</b> <a href='http://localhost:8086/sso/logout'>注销</a>");
            } else {
                //未登录
                $("#pid").html("[<a href=\"javascript:login();\">登录</a>][<a href=\"\">注册</a>]");
            }
        },
        dataType:"jsonp",
        jsonpCallback: "islogin"
    });
});

//登录
function login(){
    //获取当前页面的url
    var returnUrl = location.href;
    returnUrl = encodeURI(returnUrl, "utf-8");
    returnUrl = returnUrl.replace("&", "%26");//??????

    //跳转到tologin请求
    location.href="http://localhost:8086/sso/tologin?returnUrl=" + returnUrl;
}
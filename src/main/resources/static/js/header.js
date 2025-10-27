$(function(){
    //header.html，載入完成後才執行裡面的程式
    $('#site-header').load('header.html', function() {

        //檢查登入狀態
        $.ajax({
            url: "/user/userInformation",
            type: "GET",
            success: function (response) {
                if (response.code === 200 && response.data) {
                    //未登入時顯示,註冊,登入
                    $("#btn-register, #btn-login").hide();
                    //會員登入後顯示,會員中心,購物車,登出
                    $("#btn-user, #btn-cart, #logoutBtn").show();
                } else {
                    //未登入時顯示,註冊,登入
                    $("#btn-register, #btn-login").show();
                    //隱藏,會員中心,購物車,登出
                    $("#btn-user, #btn-cart, #logoutBtn").hide();
                }
            },
            error: function (xhr) {
                console.error(xhr);
                alert("系統錯誤！");
            }
        });

        //登出
        $("#logoutBtn").on("click", function (event) {
            event.preventDefault();
            $.ajax({
                url: "/user/logout",
                type: "GET",
                success: function (response) {
                    if (response.code === 200) {
                        window.location.href = "index.html";
                    } else {
                        alert("登出失敗");
                    }
                },
                error: function (xhr) {
                    console.error(xhr);
                    alert("系統錯誤！");
                }
            });
        });
    });
});

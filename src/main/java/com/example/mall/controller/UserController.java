package com.example.mall.controller;

import com.example.mall.dto.UserDto;
import com.example.mall.response.JsonResult;
import com.example.mall.service.Impl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    //註冊
    @PostMapping("/saveUser")
    public JsonResult saveUser(@RequestBody UserDto userDto){
        return userServiceImpl.saveUser(userDto);
    }

    //驗證token及信箱
    @GetMapping("/verify-email")
    public JsonResult verifyEmail(UserDto userDto){
        return userServiceImpl.verifyEmail(userDto);
    }

    //登入查詢帳號密碼是否正確,並保存session
    @PostMapping("/login")
    public JsonResult selectAccountPassword(@RequestBody UserDto userDto, HttpSession session){
        return userServiceImpl.selectAccountPassword(userDto,session);
    }

    //登出
    @GetMapping("/logout")
    public JsonResult logout(HttpSession session){
        session.invalidate();
        return JsonResult.ok();
    }

    //利用登入存的session去顯示會員資料
    @GetMapping("/userInformation")
    public JsonResult userByInformation(HttpSession session){
        return userServiceImpl.userByInformation(session);
    }

    //修改會員資料
    @PostMapping("/updateUser")
    public JsonResult updateUser(@RequestBody UserDto userDto,HttpSession session){
        return userServiceImpl.updateUser(userDto,session);
    }
    //忘記密碼接收輸入框的email
    @PostMapping("/sendResetPasswordEmail")
    public JsonResult sendResetPasswordEmail(@RequestBody UserDto userDto){
        return userServiceImpl.resetPwd(userDto);
    }

    //
    @PostMapping("/resetPassword")
    public JsonResult getResetPwd(@RequestBody UserDto userDto){
        return userServiceImpl.getResetPwd(userDto);
    }

}

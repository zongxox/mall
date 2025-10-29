package com.example.mall.controller;

import com.example.mall.dto.UserDTO;
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
    public JsonResult saveUser(@RequestBody UserDTO userDTO){
        return userServiceImpl.saveUser(userDTO);
    }

    //驗證token及信箱
    @GetMapping("/verify-email")
    public JsonResult verifyEmail(UserDTO userDTO){
        return userServiceImpl.verifyEmail(userDTO);
    }

    //登入查詢帳號密碼是否正確,並保存session
    @PostMapping("/login")
    public JsonResult selectAccountPassword(@RequestBody UserDTO userDTO, HttpSession session){
        return userServiceImpl.selectAccountPassword(userDTO,session);
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
    public JsonResult updateUser(@RequestBody UserDTO userDTO, HttpSession session){
        return userServiceImpl.updateUser(userDTO,session);
    }
    //忘記密碼接收輸入框的email
    @PostMapping("/sendResetPasswordEmail")
    public JsonResult sendResetPasswordEmail(@RequestBody UserDTO userDTO){
        return userServiceImpl.resetPwd(userDTO);
    }

    //
    @PostMapping("/resetPassword")
    public JsonResult getResetPwd(@RequestBody UserDTO userDTO){
        return userServiceImpl.getResetPwd(userDTO);
    }

}

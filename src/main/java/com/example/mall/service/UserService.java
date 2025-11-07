package com.example.mall.service;

import com.example.mall.pojo.dto.UserDTO;
import com.example.mall.response.JsonResult;
import jakarta.servlet.http.HttpSession;

public interface UserService {
    JsonResult saveUser(UserDTO userDTO);//註冊
    JsonResult verifyEmail(UserDTO userDTO);//註冊時email驗證,token是否存在及過期 及 email是否驗證過
    JsonResult selectAccountPassword (UserDTO userDTO, HttpSession session);//登入查詢帳號密碼是否正確
    JsonResult userByInformation(HttpSession session);//利用登入存的session去顯示會員資料
    JsonResult updateUser(UserDTO userDTO, HttpSession session);//修改會員資料
    JsonResult resetPwd(UserDTO userDTO);//忘記密碼
    JsonResult getResetPwd(UserDTO userDTO);//修改密碼

}

package com.example.mall.mapper;

import com.example.mall.pojo.dto.UserDTO;
import com.example.mall.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {
    int saveUser(User user);//註冊

    User getUserByAccount(String account);//註冊查詢帳號是否存在

    User getUserByEmail(String email);//註冊查詢emaul是否存在

    User getTokenTime(String emailVerifyToken);//註冊時email驗證,token是否存在及過期及email是否驗證過

    int delTokenTime(String emailVerifyToken);//清空註冊時token跟時間

    User selectAccountPassword(UserDTO userDto);//登入查詢帳號密碼是否正確

    int updateUser(User user);//修改會員資料

    User userById(Integer id);//修改會員資料 基於用戶id查詢用戶資料

    int updateResetTokenTime(String email, String resetToken, LocalDateTime resetTokenExpire);//忘記密碼設置resetToken,resetTokenExpire


    User getResetTokenTime(String resetToken);//查詢前端傳送過來忘記密碼的token

    int delResetTokenTime(String resetToken,String password);//清空前端透過連結過來的忘記密碼的token跟時間,並修改密碼

    int delResetToken(String resetToken);//清空前端透過連結過來的忘記密碼的token跟時間
}

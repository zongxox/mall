package com.example.mall.mapper;

import com.example.mall.dto.UserDto;
import com.example.mall.entity.User;
import com.example.mall.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int saveUser(User user);//註冊

    User getUserByAccount(String account);//註冊查詢帳號是否存在

    User getUserByEmail(String email);//註冊查詢emaul是否存在

    User getTokenTime(String emailVerifyToken);//註冊時email驗證,token是否存在及過期及email是否驗證過

    int delTokenTime(String emailVerifyToken);//清空註冊時token跟時間

    User selectAccountPassword(UserDto userDto);//登入查詢帳號密碼是否正確

    int updateUser(User user);//修改會員資料

    User userById(Integer id);//修改會員資料 基於用戶id查詢用戶資料
}

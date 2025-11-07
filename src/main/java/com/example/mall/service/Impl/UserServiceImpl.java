package com.example.mall.service.Impl;

import com.example.mall.aop.RequiredLog;
import com.example.mall.pojo.dto.UserDTO;
import com.example.mall.pojo.entity.User;
import com.example.mall.mapper.UserMapper;
import com.example.mall.response.JsonResult;
import com.example.mall.response.StatusCode;
import com.example.mall.service.UserService;
import com.example.mall.pojo.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JavaMailSender javaMailSender;

    //註冊
    @RequiredLog("用戶註冊")
    @Override
    public JsonResult saveUser(UserDTO userDTO) {
        String email = userDTO.getEmail();//前端傳送過來的email

        //去除空白跟判斷是否為空值
        if(email == null || email.trim().isEmpty()){
            return new JsonResult(StatusCode.EMAIL_EMPTY,"不可為空");
        }

        // 不允許符號 ._-+%，且網域與帳號皆僅限英數
        //String emailRegex = "^[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$";
        //String email = "user@example.com";
        //email格式驗證
        String emailRegex = "^[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$";
        if (!Pattern.matches(emailRegex, email)) {//判斷emailRegex跟email
            return new JsonResult(StatusCode.PARAM_ERROR, "Email 格式不正確");
        }

        //查詢數據庫是否有該帳號
        User userByAccount = userMapper.getUserByAccount(userDTO.getAccount());
        if(userByAccount != null){
            return new JsonResult(StatusCode.ACCOUNT_ALREADY_EXISTS,"帳號已存在");
        }

        User userByEmail = userMapper.getUserByEmail(email);//查詢是否數據庫是否有該email
        //查詢數據庫是否有該email
        if(userByEmail != null){
            return new JsonResult(StatusCode.EMAIL_ALREADY_EXISTS,"email已存在");
        }


        User user = new User();//創建user
        String email_verify_token = UUID.randomUUID().toString();//設定token
        LocalDateTime email_verify_token_expire = LocalDateTime.now().plusMinutes(30);

        BeanUtils.copyProperties(userDTO,user);//將前端表單傳過來的值複製到user裡面
        user.setIsAdmin(false);//設定是否是管理員
        user.setEmailVerified(false);//email是否驗證
        user.setCreatedTime(LocalDateTime.now());//註冊時創建時間
        user.setEmailVerifyToken(email_verify_token);//token雜湊碼
        user.setEmailVerifyTokenExpire(email_verify_token_expire);//token有效時間
        int rows = userMapper.saveUser(user);//新增SQL
        if(rows > 0){//新增成功時
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(user.getEmail());
            simpleMailMessage.setSubject("你的email驗證連結");
            simpleMailMessage.setText("http://localhost:8080/verify-email.html?emailVerifyToken="+email_verify_token);
            javaMailSender.send(simpleMailMessage);
            return JsonResult.ok();
        }
        return new JsonResult(StatusCode.EMAIL_VERIFICATION_FAILED,"驗證失敗");
    }

    //驗證token及信箱
    @RequiredLog("驗證token及信箱")
    @Override
    public JsonResult verifyEmail(UserDTO userDTO) {
        String email_verify_token = userDTO.getEmailVerifyToken();//獲取接收到的token

        //查詢數據庫的token過期跟是否存在email是否驗證過
        User tokenTime = userMapper.getTokenTime(email_verify_token);

        //判斷連結是否有token 判斷數據庫是否有token值
        if(tokenTime == null){
            return new JsonResult(StatusCode.EMAIL_TOKEN_INVALID_OR_USED,"無效或已使用的驗證連結");
        }
        //判斷token是否過期
        LocalDateTime email_verify_token_expire = tokenTime.getEmailVerifyTokenExpire();
        if (email_verify_token_expire==null||LocalDateTime.now().isAfter(email_verify_token_expire)){
            return new JsonResult(StatusCode.EMAIL_TOKEN_EXPIRED);//1011
        }

        //判斷查詢到的email是否驗證過
        if (tokenTime.getEmailVerified()) {
            return new JsonResult(StatusCode.EMAIL_ALREADY_VERIFIED, "該 email 已驗證過");
        }

        //將獲取到的token拿去執行新增
        int rows = userMapper.delTokenTime(tokenTime.getEmailVerifyToken());

        if(rows > 0){
            return JsonResult.ok("驗證成功");
        }
        return new JsonResult(StatusCode.EMAIL_VERIFICATION_FAILED,"驗證失敗");
    }

    //登入查詢帳號密碼是否正確
    @RequiredLog("登入查詢帳號密碼是否正確")
    @Override
    public JsonResult selectAccountPassword(UserDTO userDto, HttpSession session) {
        User user = userMapper.selectAccountPassword(userDto);//將前端傳遞過來的帳號密碼跟數據庫做比對
        if(user == null){//判斷查詢出來的結果是否為空
            return new JsonResult(StatusCode.ACCOUNT_PASSWORD_ERROR,"帳號密碼錯誤");
        }
        UserVO vo = new UserVO();//創建user返回前端的對象
        BeanUtils.copyProperties(user, vo);//將查詢到的對象存到UserVo裡面
        session.setAttribute("sessionVO", vo);//再把登入查詢到的vo對象存到session對象裡面
        return JsonResult.ok();
    }

    //利用登入存的session去顯示會員資料
    @RequiredLog("利用登入存的session去顯示會員資料")
    @Override
    public JsonResult userByInformation(HttpSession session) {
        UserVO vo = (UserVO) session.getAttribute("sessionVO");
        return JsonResult.ok(vo);
    }

    //修改會員資料
    @RequiredLog("修改會員資料")
    @Override
    public JsonResult updateUser(UserDTO userDto, HttpSession session) {
        //先重登入後的session取得會員id
        UserVO sessionVo =(UserVO) session.getAttribute("sessionVO");
        if(sessionVo == null){//判斷session裡面是不是沒有用戶
            return new JsonResult(StatusCode.NOT_LOGIN,"尚未登入");
        }

        User user = new User();//用於將dot的值複製近來
        BeanUtils.copyProperties(userDto, user);//將前端的值複製到user裡面
        user.setId(sessionVo.getId());//將session裡面的用戶id設定進去

        int row = userMapper.updateUser(user);//在將複製進去的值拿去修改

        if(row>0){//判斷是否修改成功
            //修改成功的話先查詢,再將資料返回給前端
            User userById = userMapper.userById(user.getId());//將修改成功的並且複製進去的id拿取查詢
            UserVO vo = new UserVO();//創建返回對象
            BeanUtils.copyProperties(userById, vo);//將查詢到的結果複製給vo
            session.setAttribute("sessionVO", vo);//將結果設置到session裡面
            return JsonResult.ok(vo);//將vo返回給前端
        }
        return new JsonResult(StatusCode.OPERATION_FAILED,"操作失敗");
    }

    //忘記密碼
    @RequiredLog("忘記密碼")
    @Override
    public JsonResult resetPwd(UserDTO userDTO) {
        String email = userDTO.getEmail();
        //判斷email是否為空
        if(email == null){
            return new JsonResult(StatusCode.EMAIL_EMPTY,"email不可為空");
        }
        //email格式驗證
        String emailRegex = "^[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$";
        if (!Pattern.matches(emailRegex, email)) {//判斷emailRegex跟email
            return new JsonResult(StatusCode.PARAM_ERROR, "Email 格式不正確");
        }

        //查詢資料庫是否有該email
        User userByEmail = userMapper.getUserByEmail(email);

        //判斷數據庫是否有前端傳遞過來的email
        if(userByEmail==null){
            return new JsonResult(StatusCode.EMAIL_NOT_FOUND,"沒有該信箱");
        }

        String resetToken = UUID.randomUUID().toString();
        LocalDateTime resetTokenExpire = LocalDateTime.now().plusMinutes(30);
        //LocalDateTime resetTokenExpire = LocalDateTime.now().plusSeconds(1); 測試用
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        user.setResetToken(resetToken);
        user.setResetTokenExpire(resetTokenExpire);
        int rows = userMapper.updateResetTokenTime(user.getEmail(),user.getResetToken(),user.getResetTokenExpire());
        if(rows > 0){//新增成功時
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(user.getEmail());
            simpleMailMessage.setSubject("重設密碼連結");
            simpleMailMessage.setText("http://localhost:8080/reset-password.html?resetToken="+resetToken);
            javaMailSender.send(simpleMailMessage);
            return JsonResult.ok();
        }

        return new JsonResult(StatusCode.EMAIL_VERIFICATION_FAILED,"驗證失敗");
    }

    //修改密碼並判斷token是否過期或無效
    @RequiredLog("修改密碼並判斷token是否過期或無效")
    @Override
    public JsonResult getResetPwd(UserDTO userDto) {
        String reset_token = userDto.getResetToken();
        User resetTokenTime = userMapper.getResetTokenTime(reset_token);
        String password = userDto.getPassword();


        //判斷是否為空或已經驗證過
        if(resetTokenTime==null){
            return new JsonResult(StatusCode.EMAIL_TOKEN_INVALID_OR_USED,"無效或以使用的連結");
        }
        //用現在時間跟數據庫的時間比對
        LocalDateTime resetTokenExpire = resetTokenTime.getResetTokenExpire();
        if(resetTokenExpire==null || LocalDateTime.now().isAfter(resetTokenExpire)){
            userMapper.delResetToken(reset_token);//失敗時刪掉token讓用戶重新點選忘記密碼
            return new JsonResult(StatusCode.EMAIL_TOKEN_EXPIRED,"驗證連結已過期");
        }

        if (password == null || password.trim().isEmpty()) {
            return new JsonResult(StatusCode.PASSWORD_EMPTY,"密碼不可為空");
        }


        if(password.length()<6){
            return new JsonResult(StatusCode.PASSWORD_EMPTY,"請至少輸入6位數,含英文");
        }

        int rows = userMapper.delResetTokenTime(reset_token, password);
        if(rows>0){
            return JsonResult.ok("更新密碼成功");
        }

        return new JsonResult(StatusCode.PASSWORD_UPDATE_FAILED);
    }


}

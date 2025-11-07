package com.example.mall.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//枚舉類 定義回應的長量信息 狀態碼,回應信息
@AllArgsConstructor //全參建構子
@NoArgsConstructor //無參建構子
@Getter
public enum StatusCode {
    //定義枚舉實例
    SUCCESS(200, "操作成功"),
    NOT_LOGIN(1001, "尚未登入"),
    ACCOUNT_PASSWORD_ERROR(1002, "帳號或密碼錯誤"),
    ACCOUNT_ALREADY_EXISTS(1003, "帳號已存在"),
    OPERATION_FAILED(1004, "操作失敗"),
    PARAM_ERROR(1005, "參數錯誤"),
    NO_PERMISSION(1006, "沒有權限"),
    SYSTEM_ERROR(1007, "系統錯誤，請稍後再試"),
    EMAIL_NOT_FOUND(1008, "Email 查無資料"),
    EMAIL_ALREADY_EXISTS(1009, "該信箱已存在"),
    EMAIL_TOKEN_INVALID_OR_USED(1010, "無效或已使用的驗證連結"),
    EMAIL_TOKEN_EXPIRED(1011, "驗證連結已過期，請重新註冊或請求新連結"),
    EMAIL_ALREADY_VERIFIED(1012, "此帳號已完成驗證，請直接登入"),
    EMAIL_VERIFICATION_FAILED(1013, "驗證失敗"),
    EMAIL_EMPTY(1014, "Email 不可為空"),
    PASSWORD_EMPTY(1015, "密碼不可為空"),
    PASSWORD_UPDATE_FAILED(1016, "密碼更新失敗"),
    PRODUCTS_FAIL(1017,"目前沒有商品資料"),
    ORDER_NOT_FOUND(1018,"訂單不存在"),
    ORDER_NOT_USER(1019,"此訂單不屬於你"),
    ORDER_ALREADY_PAID(1020,"訂單已付款或狀態錯誤"),
    ORDER_ITEM_EMPTY(1021, "訂單明細為空"),
    STOCK_NOT_ENOUGH(1022,"庫存不足或不存在"),
    ORDER_STATUS_CONFLICT(1023,"更新狀態失敗");
    private Integer code; //回應狀態碼
    private String msg; //回應狀態信息結果

}
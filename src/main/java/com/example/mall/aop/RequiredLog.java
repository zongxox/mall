package com.example.mall.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})//指定作用範圍
@Retention(RetentionPolicy.RUNTIME)//指定生命週期,執行時
public @interface RequiredLog {

    //定義操作詳情長量
    //註解中屬性的定義
    //作用:誰用當前註解,就加上具體方法的操作詳情,該方法的具體操作
    String value();

}

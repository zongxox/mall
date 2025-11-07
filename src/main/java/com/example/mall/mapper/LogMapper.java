package com.example.mall.mapper;


import com.example.mall.pojo.entity.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {
    @Insert("INSERT INTO log(ip,username,created_time,operation,method,params,time,status,error) " +
            "VALUES (#{ip},#{username},#{createdTime},#{operation},#{method},#{params},#{time},#{status},#{error})")
    int insert(Log log);
}

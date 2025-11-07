package com.example.mall.service.Impl;


import com.example.mall.mapper.LogMapper;
import com.example.mall.pojo.entity.Log;
import com.example.mall.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public void insert(Log log) {
        System.out.println(log);
        logMapper.insert(log);
    }
}
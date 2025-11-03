package com.example.mall.service;


import com.example.mall.dto.OrderDTO;
import com.example.mall.response.JsonResult;
import jakarta.servlet.http.HttpSession;

public interface OrderService {
    //新增用戶配送資訊
    JsonResult insertOrder(OrderDTO orderDTO, HttpSession session);
}

package com.example.mall.service;


import com.example.mall.pojo.dto.OrderDTO;
import com.example.mall.response.JsonResult;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

public interface OrderService {
    //新增用戶配送資訊
    JsonResult insertOrder(OrderDTO orderDTO, HttpSession session);

    //用戶付款（更新訂單狀態＋扣庫存）
    JsonResult payOrder(Integer orderId, HttpSession session);

    //會員中心用戶的全部訂單
    List<Map<String, Object>> getAllOrders();
}

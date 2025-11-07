package com.example.mall.controller;


import com.example.mall.dto.OrderDTO;
import com.example.mall.response.JsonResult;
import com.example.mall.service.Impl.OrderServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderServiceImpl;

    //新增用戶配送資訊
    @PostMapping("/order")
    public JsonResult insertOrder(@RequestBody OrderDTO orderDTO, HttpSession session){
        return orderServiceImpl.insertOrder(orderDTO,session);
    }

    //用戶付款（更新訂單狀態＋扣庫存）
    @PostMapping("/pay")
    public JsonResult payOrder(@RequestBody Integer orderId, HttpSession session) {
        return orderServiceImpl.payOrder(orderId, session);
    }
}

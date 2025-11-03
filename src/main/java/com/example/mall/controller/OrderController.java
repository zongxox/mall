package com.example.mall.controller;


import com.example.mall.dto.OrderDTO;
import com.example.mall.response.JsonResult;
import com.example.mall.service.Impl.OrderServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

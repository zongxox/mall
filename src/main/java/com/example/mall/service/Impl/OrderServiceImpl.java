package com.example.mall.service.Impl;



import com.example.mall.dto.OrderDTO;
import com.example.mall.entity.Order;
import com.example.mall.mapper.OrderMapper;
import com.example.mall.response.JsonResult;
import com.example.mall.response.StatusCode;
import com.example.mall.service.OrderService;
import com.example.mall.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    //新增用戶配送資訊
    @Override
    public JsonResult insertOrder(OrderDTO orderDTO, HttpSession session) {
        UserVO sessionVO = (UserVO) session.getAttribute("sessionVO");
        if(sessionVO==null){
           return new JsonResult(StatusCode.NOT_LOGIN,"尚未登入");
        }

        Order order = new Order();
        BeanUtils.copyProperties(orderDTO,order);
        order.setUserId(sessionVO.getId());
        order.setStatus("未付款");
        order.setCreatedTime(LocalDateTime.now());
        order.setUpdatedTime(LocalDateTime.now());
        int rows = orderMapper.insertOrder(order);
        if(rows>0){
            return JsonResult.ok();
        }
        return new JsonResult(StatusCode.PRODUCTS_FAIL,"目前沒有商品資料");
    }
}

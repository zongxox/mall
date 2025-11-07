package com.example.mall.mapper;

import com.example.mall.entity.Order;
import com.example.mall.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    //建立訂單
    int insertOrderItem(OrderItem orderItem);

    //基於訂單id找到該用戶所有訂單明細
    List<OrderItem> userByOrderId(Integer orderId);

}

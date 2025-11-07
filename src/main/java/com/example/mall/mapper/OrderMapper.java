package com.example.mall.mapper;


import com.example.mall.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    //新增用戶配送資訊
    int insertOrder(Order order);

    //查詢訂單id
    Order selectOrderId(Integer id);

    //更新訂單狀態
    int updateOrderStatus(Integer id,String status);
}

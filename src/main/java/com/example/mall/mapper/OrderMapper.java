package com.example.mall.mapper;


import com.example.mall.pojo.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    //新增用戶配送資訊
    int insertOrder(Order order);

    //查詢訂單id
    Order selectOrderId(Integer id);

    //更新訂單狀態
    int updateOrderStatus(Integer id,String status);

    //會員中心用戶的全部訂單
    List<Map<String, Object>> selectAllOrders();
}

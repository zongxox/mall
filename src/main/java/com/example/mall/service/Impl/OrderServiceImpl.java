package com.example.mall.service.Impl;



import com.example.mall.dto.OrderDTO;
import com.example.mall.entity.Order;
import com.example.mall.entity.OrderItem;
import com.example.mall.mapper.CartItemsMapper;
import com.example.mall.mapper.OrderItemMapper;
import com.example.mall.mapper.OrderMapper;
import com.example.mall.mapper.ProductVariantMapper;
import com.example.mall.response.JsonResult;
import com.example.mall.response.StatusCode;
import com.example.mall.service.OrderService;
import com.example.mall.vo.CartItemsVO;
import com.example.mall.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CartItemsMapper cartItemsMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductVariantMapper productVariantMapper;

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
            //查詢使用這購物車資訊
            List<CartItemsVO> cartItemsVOS = cartItemsMapper.selectByUserId(sessionVO.getId());
            //判斷查詢到的購物車資訊是不是空的
            if(cartItemsVOS == null){
                return new JsonResult(StatusCode.PRODUCTS_FAIL,"購物車是空的");
            }
            for (CartItemsVO item:cartItemsVOS) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(order.getId());//訂單id
                orderItem.setProductVariantId(item.getProductVariantId());//訂單商品規格
                orderItem.setQuantity(item.getQuantity());//購買商品數量
                orderItem.setPrice(item.getProductPrice());//商品總金額
                orderItemMapper.insertOrderItem(orderItem);
            }
            cartItemsMapper.deleteCartByUserId(order.getUserId());//刪除整個購物車
            return JsonResult.ok(order.getId());//回傳orderId給前端,方便後面抓取使用
        }

        return new JsonResult(StatusCode.PRODUCTS_FAIL,"目前沒有商品資料");
    }

    //用戶付款（更新訂單狀態＋扣庫存）
    @Override
    public JsonResult payOrder(Integer orderId, HttpSession session) {

        UserVO sessionVO = (UserVO) session.getAttribute("sessionVO");
        if (sessionVO == null) {
            return new JsonResult(StatusCode.NOT_LOGIN, "尚未登入");
        }

        //查訂單是否存在
        Order order = orderMapper.selectOrderId(orderId);
        //判斷訂單存不存在
        if(order ==null){
            return new JsonResult(StatusCode.ORDER_NOT_FOUND,"訂單不存在");
        }

        //檢查訂單是否屬於目前用戶
        if (!order.getUserId().equals(sessionVO.getId())) {
            return new JsonResult(StatusCode.ORDER_NOT_USER, "此訂單不屬於你");
        }

        //判斷是否已付款
        if (!"未付款".equals(order.getStatus())) {
            return new JsonResult(StatusCode.ORDER_ALREADY_PAID, "訂單已付款或狀態錯誤");
        }

        //查所有訂單明細（用來扣庫存）
        List<OrderItem> items = orderItemMapper.userByOrderId(orderId);
        if (items == null || items.isEmpty()) {
            return new JsonResult(StatusCode.ORDER_ITEM_EMPTY, "訂單明細為空");
        }

        //扣庫存（逐筆扣）
        for (OrderItem item : items) {
            int affected = productVariantMapper.delStock(
                    item.getProductVariantId(),
                    item.getQuantity()
            );

            if (affected == 0) {
                return new JsonResult(StatusCode.STOCK_NOT_ENOUGH, "庫存不足，付款失敗");
            }
        }

        //更新訂單狀態
        int updated = orderMapper.updateOrderStatus(orderId, "已付款");
        if (updated == 0) {
            return new JsonResult(StatusCode.ORDER_STATUS_CONFLICT, "訂單狀態更新失敗");
        }
        return JsonResult.ok("付款成功");
    }
}

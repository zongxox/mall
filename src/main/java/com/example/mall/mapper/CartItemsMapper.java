package com.example.mall.mapper;

import com.example.mall.entity.CartItems;
import com.example.mall.vo.CartItemsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartItemsMapper {
    //加入購物車表中
    int insertCart(CartItems cartItems);

    //透過登入狀態的userid顯示購物車商品
    List<CartItemsVO> selectByUserId(Integer userId);

    //刪除購物車商品
    int deleteCart(Integer userId,Integer id);

    //按結帳後,修改購物車表中數量
    int updateCart(Integer userId,Integer id,Integer quantity);

}

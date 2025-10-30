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

    //查詢該使用者購物車中所有商品規格的詳細資料（例如圖片、顏色、尺寸、價格、數量）

    //刪除購物車商品
    int deleteCart(Integer userId,Integer cartItemId);

    //更新購物車數量
}

package com.example.mall.mapper;

import com.example.mall.entity.CartItems;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartItemsMapper {
    //加入購物車
    int insertCart(CartItems cartItems);
    //透過userid顯示購物車商品
    //查詢該使用者購物車中所有商品規格的詳細資料（例如圖片、顏色、尺寸、價格、數量）
    //刪除購物車商品
    //更新購物車數量
}

package com.example.mall.service;

import com.example.mall.dto.CartItemsDTO;
import com.example.mall.response.JsonResult;
import jakarta.servlet.http.HttpSession;

public interface CartItemsService {
    JsonResult insertCart(CartItemsDTO cartItemsDTO, HttpSession session);//加入購物車表中
    JsonResult selectByUserId(HttpSession session);//透過登入狀態的userid顯示購物車商品
    JsonResult deleteCart(CartItemsDTO cartItemsDTO,HttpSession session);//刪除購物車商品
}

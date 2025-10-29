package com.example.mall.service;

import com.example.mall.dto.CartItemsDTO;
import com.example.mall.response.JsonResult;
import jakarta.servlet.http.HttpSession;

public interface CartItemsService {
    JsonResult insertCart(CartItemsDTO cartItemsDTO, HttpSession session);//加入購物車
}

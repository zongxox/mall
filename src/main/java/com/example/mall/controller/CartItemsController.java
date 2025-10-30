package com.example.mall.controller;

import com.example.mall.dto.CartItemsDTO;
import com.example.mall.response.JsonResult;
import com.example.mall.service.Impl.CartItemsServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartItemsController {
    @Autowired
    private CartItemsServiceImpl cartItemsServiceImpl;

    //加入購物車表中
    @PostMapping("/addCart")
    public JsonResult addCart(@RequestBody CartItemsDTO cartItemsDTO, HttpSession session){
        return cartItemsServiceImpl.insertCart(cartItemsDTO, session);
    }

    //透過登入狀態的userid顯示購物車商品
    @GetMapping("/getCart")
    public JsonResult selectByUserId(HttpSession session){
        return cartItemsServiceImpl.selectByUserId(session);
    }

    //刪除購物車商品
    @DeleteMapping("/del")
    public JsonResult deleteCart(@RequestBody CartItemsDTO cartItemsDTO,HttpSession session){
        return cartItemsServiceImpl.deleteCart(cartItemsDTO,session);
    }
}

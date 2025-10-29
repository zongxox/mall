package com.example.mall.controller;

import com.example.mall.dto.CartItemsDTO;
import com.example.mall.response.JsonResult;
import com.example.mall.service.Impl.CartItemsServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartItemsController {
    @Autowired
    private CartItemsServiceImpl cartItemsService;
    @PostMapping("/addCart")
    public JsonResult addCart(@RequestBody CartItemsDTO cartItemsDTO, HttpSession session){
        return cartItemsService.insertCart(cartItemsDTO, session);
    }
}

package com.example.mall.controller;

import com.example.mall.entity.Products;
import com.example.mall.response.JsonResult;
import com.example.mall.service.Impl.ProductsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsServiceImpl productsServiceImpl;

    @GetMapping("/{productId}")
    public JsonResult selectProducts(@PathVariable Integer productId){
        return productsServiceImpl.selectProducts(productId);
    }
}

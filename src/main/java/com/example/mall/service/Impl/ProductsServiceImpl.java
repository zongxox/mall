package com.example.mall.service.Impl;

import com.example.mall.entity.ProductVariant;
import com.example.mall.entity.Products;
import com.example.mall.mapper.ProductsMapper;
import com.example.mall.response.JsonResult;
import com.example.mall.response.StatusCode;
import com.example.mall.service.ProductsService;
import com.example.mall.vo.ProductsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductsMapper productsMapper;


    //基於id查詢商品資訊
    @Override
    public JsonResult selectProducts(Integer productId) {
        //查詢到的商品資訊
        Products products1 = productsMapper.selectProducts(productId);

        //判斷商品資訊是不是空的
        if(products1==null){
            return new JsonResult(StatusCode.OPERATION_FAILED,"沒有此商品");
        }

        //基於id查詢尺寸庫存顏色
        List<ProductVariant> productVariants = productsMapper.selectProductsVariant(productId);

        ProductsVo productsVO = new ProductsVo();
        //將商品資訊複製到vo對象,並返回給前端
        BeanUtils.copyProperties(products1, productsVO);
        //將查詢到的商品id,尺寸,庫存,顏色,設置到vo對象
        productsVO.setVariants(productVariants);
        return JsonResult.ok(productsVO);
    }
}

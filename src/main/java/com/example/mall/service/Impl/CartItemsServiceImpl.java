package com.example.mall.service.Impl;

import com.example.mall.dto.CartItemsDTO;
import com.example.mall.entity.CartItems;
import com.example.mall.mapper.CartItemsMapper;
import com.example.mall.response.JsonResult;
import com.example.mall.response.StatusCode;
import com.example.mall.service.CartItemsService;
import com.example.mall.vo.CartItemsVO;
import com.example.mall.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemsServiceImpl implements CartItemsService {
    @Autowired
    private CartItemsMapper cartItemsMapper;
    //加入購物車表中
    @Override
    public JsonResult insertCart(CartItemsDTO cartItemsDTO, HttpSession session) {
        UserVO sessionVO = (UserVO)session.getAttribute("sessionVO");
        if(sessionVO==null){
            return new JsonResult(StatusCode.NOT_LOGIN,"尚未登入");
        }
        if(cartItemsDTO.getQuantity()==null||cartItemsDTO.getQuantity()<=0){
            return new JsonResult(StatusCode.PRODUCTS_FAIL,"商品數量不可為0");
        }
        CartItems cartItems = new CartItems();
        BeanUtils.copyProperties(cartItemsDTO, cartItems);
        cartItems.setUserId(sessionVO.getId());//從登入狀態取得用戶id
        cartItems.setProductVariantId(cartItemsDTO.getProductVariantId());//設置商品規格!!
        cartItems.setQuantity(cartItemsDTO.getQuantity());//設置商品數量
        int rows = cartItemsMapper.insertCart(cartItems);
        if(rows > 0){
            return JsonResult.ok();
        }
        return new JsonResult(StatusCode.PRODUCTS_FAIL,"沒有商品資料");
    }


    //透過登入狀態的userid顯示購物車商品
    @Override
    public JsonResult selectByUserId(HttpSession session) {
        UserVO sessionVO = (UserVO) session.getAttribute("sessionVO");
        if(sessionVO==null){
            return new JsonResult(StatusCode.NOT_LOGIN,"尚未登入");
        }
        List<CartItemsVO> list = cartItemsMapper.selectByUserId(sessionVO.getId());
        if(list == null || list.isEmpty()){
            return new JsonResult(StatusCode.PRODUCTS_FAIL,"沒有商品資料");
        }
        return JsonResult.ok(list);
    }

    //刪除購物車商品
    @Override
    public JsonResult deleteCart(CartItemsDTO cartItemsDTO,HttpSession session) {
        UserVO sessionVo = (UserVO) session.getAttribute("sessionVO");
        Integer id = sessionVo.getId();
        if(id==null){
            return new JsonResult(StatusCode.NOT_LOGIN,"尚未登入");
        }
        int rows = cartItemsMapper.deleteCart(id, cartItemsDTO.getId());
        if (rows > 0){
            return JsonResult.ok();
        }
        return new JsonResult(StatusCode.OPERATION_FAILED,"刪除失敗");
    }
}

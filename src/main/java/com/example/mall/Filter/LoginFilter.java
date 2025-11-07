package com.example.mall.Filter;

import com.example.mall.pojo.vo.UserVO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "loginFilter",urlPatterns = {"/user.html","/cart.html","/payment_confirm3.html","/credit-card-payment.html","/order.html"})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        System.out.println("請求過濾中");
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();

        UserVO sessionVo = (UserVO) session.getAttribute("sessionVO");
        if(sessionVo==null){
            res.sendRedirect("login.html");
            return;
        }
        filterChain.doFilter(req, res);
        System.out.println("過濾器執行結束");
    }
}

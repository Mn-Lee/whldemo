package com.whl.demo.controller;

import com.whl.demo.mapper.UserMapper;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        for(Cookie cookie : cookies){
            if("token".equals(cookie.getName())){
                String token = cookie.getValue();
                User user = userMapper.findUserByToken(token);

                request.getSession().setAttribute("user",user);

                break;
            }
        }

        return "index";
    }
}

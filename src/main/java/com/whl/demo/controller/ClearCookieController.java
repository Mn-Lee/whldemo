package com.whl.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ClearCookieController {

    @GetMapping("/clearCookies")
    public String clearCookie(HttpServletRequest request,
                              HttpServletResponse response){
        Cookie[] cookies = request.getCookies();

        for(Cookie cookie : cookies){
            String name = cookie.getName();
            response.addCookie(new Cookie(name,null));
        }

        return "redirect:/";
    }
}

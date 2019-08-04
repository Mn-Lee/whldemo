package com.whl.demo.controller;

import com.whl.demo.mapper.UserMapper;
import com.whl.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class CheckJoinTokenController {

    @Autowired(required = false)
    UserMapper userMapper;

    @PostMapping("/editor/join/checkJoinToken")
    public String checkJoinToken(
            @RequestParam(name="joinToken") String joinToken,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        Integer editorId;

        if("123456".equals(joinToken)){
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if(user==null){
                response.sendRedirect(request.getContextPath() + "/appError/loginFirst.html");
            }

            userMapper.setEditorIdById(user.getId());
            editorId = userMapper.findEditorIdById(user.getId());

            if(editorId != -1) {
                user.setEditorId(editorId);
                return "editor/join/joinSuccess";
            }else {
                return "editor/error/joinFail.html";
            }
        }else{
            return "editor/error/joinFail.html";
        }
    }
}

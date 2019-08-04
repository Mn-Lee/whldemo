package com.whl.demo.filter;

import com.whl.demo.model.User;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@Component
@WebFilter(urlPatterns="/editor/join/*",filterName = "joinEditorFilter")
public class JoinEditorFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest)req).getSession();
        User user = (User) session.getAttribute("user");
        Integer editorId;

        System.out.println("joinfilter");

        if(user==null){
            //跳转至"请先登录"页面
            ((HttpServletResponse)resp).sendRedirect(((HttpServletRequest) req).getContextPath() + "/appError/loginFirst.html");
        }else{
            editorId = user.getEditorId();

            if(editorId!=-1){
                //跳转至"非法操作"页面
                ((HttpServletResponse)resp).sendRedirect(((HttpServletRequest) req).getContextPath() + "/appError/illegalBehaviour.html");

            }else{
                chain.doFilter(req, resp);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

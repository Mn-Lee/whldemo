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
@WebFilter(urlPatterns="/editor/*",filterName = "editorFilter")
public class EditorFilter implements Filter {

    //排除不拦截的url
    private static final String[] excludePathPatternsStart = { "/editor/error/","/editor/join/"};

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest)req).getSession();
        User user = (User) session.getAttribute("user");
        String requestUri = ((HttpServletRequest)req).getRequestURI();
        Integer editorId;

        System.out.println("editorfilter");
        System.out.println(requestUri);

        for(String path:excludePathPatternsStart) {
            if (requestUri.startsWith(path)) {
                chain.doFilter(req,resp);
                return;
            }
        }

        if(user==null){
            //跳转至"请先登录"页面
            ((HttpServletResponse)resp).sendRedirect(((HttpServletRequest) req).getContextPath() + "/appError/loginFirst.html");
        }else{
            editorId = user.getEditorId();

            if(editorId==-1){
                //跳转至"成为开发者"页面
                ((HttpServletResponse)resp).sendRedirect(((HttpServletRequest) req).getContextPath() + "/editor/join/join.html");

            }else{
                chain.doFilter(req, resp);
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}

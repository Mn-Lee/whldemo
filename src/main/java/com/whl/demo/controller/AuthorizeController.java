package com.whl.demo.controller;

import com.whl.demo.dto.AccessTokenDTO;
import com.whl.demo.dto.GithubUserDTO;
import com.whl.demo.mapper.UserMapper;
import com.whl.demo.provider.GithubProvider;
import com.whl.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/callback")
    //如果有request参数,spring会自动放入
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUserDTO githubUserDTO = githubProvider.getUser(accessToken);

        System.out.println(accessToken);
        System.out.println(githubUserDTO.getId() + " --- " + githubUserDTO.getLogin() + " --- " + githubUserDTO.getName());

        if(githubUserDTO!=null){
            //登陆成功

            //通过githubId判断是否注册
            Long githubId = githubUserDTO.getId();
            User user = userMapper.findUserByGitHubId(githubId);

            if(user==null){
                //未注册，将gitHubUserDTO转换成user并入库
                user = new User();

                user.setName(githubUserDTO.getLogin());
                user.setAccountId(UUID.randomUUID().toString());
                user.setToken(accessToken);
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setBio(githubUserDTO.getAvatarUrl());
                user.setGithubId(githubUserDTO.getId());

                userMapper.insert(user);
            }
            //写cookie
            Cookie loginTokenCookie = new Cookie("token",user.getToken());
            //失效时间以秒为单位
            loginTokenCookie.setMaxAge(60*60*24);
            response.addCookie(loginTokenCookie);

            return "redirect:/";
        }else{
            //登陆失败
            return "redirect:/";
        }

    }
}

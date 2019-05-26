package com.whl.demo.controller;

import com.whl.demo.dto.AccessTokenDTO;
import com.whl.demo.dto.GithubUserDTO;
import com.whl.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("491096625fc95d5bb1bb");
        accessTokenDTO.setClient_secret("f4e9cf03768d077a43e3447173c2e932f41c19c8");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUserDTO user = githubProvider.getUser(accessToken);

        System.out.println(user.getId() + " --- " + user.getLogin() + " --- " + user.getName());

        return "index";
    }
}

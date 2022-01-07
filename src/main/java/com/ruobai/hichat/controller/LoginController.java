package com.ruobai.hichat.controller;

import com.ruobai.hichat.service.LoginService;
import com.ruobai.hichat.vo.ChatHomeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@Slf4j
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url,String id, Model model){
        model.addAttribute("id",id);
        return url;
    }
    @PostMapping("/loginUser")
    public String loginUser(String username, String password, Model model){
        log.info("user:"+username+"\t"+password);
        if (loginService.login(username,password)!=null){
            model.addAttribute("user", loginService.login(username, password));
            return "chat";
        }else{
            return "login";
        }
    }
}

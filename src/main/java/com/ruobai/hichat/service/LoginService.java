package com.ruobai.hichat.service;


import com.ruobai.hichat.entity.User;
import com.ruobai.hichat.vo.ChatHomeVO;

public interface LoginService {
    public ChatHomeVO<User> login(String username, String password);
}

package com.ruobai.hichat.service.impl;

import com.ruobai.hichat.entity.Mine;
import com.ruobai.hichat.entity.User;
import com.ruobai.hichat.repository.LoginRepository;
import com.ruobai.hichat.service.LoginService;
import com.ruobai.hichat.vo.ChatHomeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;
    @Override
    public ChatHomeVO<User> login(String username, String password) {
        User user = loginRepository.login(username, password);
        ChatHomeVO chatHomeVO = new ChatHomeVO();
        if (user == null){
            return null;
        }else {
            chatHomeVO.setCode(200);
            chatHomeVO.setMsg("");
            return chatHomeVO;
        }
    }
}

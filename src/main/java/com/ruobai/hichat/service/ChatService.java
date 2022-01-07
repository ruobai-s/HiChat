package com.ruobai.hichat.service;

import com.ruobai.hichat.po.FriendPO;
import com.ruobai.hichat.vo.ChatHomeVO;

import java.util.List;
import java.util.Map;

public interface ChatService {
    public List getGroup(String id);
    public Map getMine(String id);
    public List getFriend(String id);
    public ChatHomeVO getData(String id);
}

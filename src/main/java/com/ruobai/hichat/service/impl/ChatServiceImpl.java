package com.ruobai.hichat.service.impl;

import com.ruobai.hichat.entity.Friend;
import com.ruobai.hichat.entity.Group;
import com.ruobai.hichat.entity.Mine;
import com.ruobai.hichat.po.FriendPO;
import com.ruobai.hichat.po.GroupPO;
import com.ruobai.hichat.po.MinePO;
import com.ruobai.hichat.service.ChatService;
import com.ruobai.hichat.vo.ChatHomeVO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final String FurtherURI="http://101.37.174.206:8081/cq";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ChatService chatService;
    private Map<String, Object> chatHomeData = new HashMap<String, Object>();

    @Override
    public List getGroup(String id) {
        List<GroupPO> groupPOS = new ArrayList<>();
        List<Group> groups = (List<Group>) restTemplate.getForObject(FurtherURI+"/getGroupInfo?bot="+id,Object.class);
        for (Object object : groups) {
            GroupPO groupPO = new GroupPO();
            JSONObject jsonObject=JSONObject.fromObject(object);
            Group group = (Group) jsonObject.toBean(jsonObject,Group.class);
            groupPO.setId(group.getGroup_id());
            groupPO.setGroupname(group.getGroup_name());
            groupPO.setAvatar(group.getImage_url());
            groupPOS.add(groupPO);
        }
        log.info("Group"+groups);
        return groupPOS;
    }

    @Override
    public Map getMine(String id) {
        Map mine = (Map) restTemplate.getForObject("https://api.66mz8.com/api/qq.info.php?qq"+id,Object.class);
        log.info("Mine:"+mine);
        return mine;
    }

    @Override
    public List getFriend(String id) {
        List<MinePO> minePOS = new ArrayList<>();
        List<FriendPO> friendPOS = new ArrayList<>();
        FriendPO friendPO = new FriendPO();
        List<Friend> friends = (List<Friend>) restTemplate.getForObject(FurtherURI+"/getFriendsInfo?bot="+id,Object.class);
        for (Object object : friends) {
            MinePO minePO = new MinePO();
            JSONObject jsonObject=JSONObject.fromObject(object);
            Friend friend = (Friend) jsonObject.toBean(jsonObject,Friend.class);
            minePO.setId(friend.getUser_id());
            minePO.setSign(friend.getRemark());
            minePO.setAvatar(friend.getImage_url());
            minePO.setStatus("online");
            minePO.setUsername(friend.getNickname());
            minePOS.add(minePO);
        }
        friendPO.setGroupname("你的专属列表");
        friendPO.setId("0");
        friendPO.setList(minePOS);
        friendPOS.add(friendPO);
        log.info("minePOS"+minePOS);
        log.info("friends:"+friends+"\t;type:"+friends.getClass());
        return friendPOS;
    }

    @Override
    public ChatHomeVO getData(String id) {
        ChatHomeVO chatHomeVO = new ChatHomeVO();
        MinePO minePO = new MinePO();
        List groups = chatService.getGroup(id);
        List friends = chatService.getFriend(id);
        String msg =restTemplate.getForObject("https://q.qlogo.cn/headimg_dl?dst_uin="+id+"&spec=100",String.class);

        if (getMine(id).get("username")!=null) {
            minePO.setUsername(getMine(id).get("username").toString());
        }else{
            minePO.setUsername(id);
        }
        minePO.setId(id);
        minePO.setStatus("online");
        minePO.setAvatar("https://q.qlogo.cn/headimg_dl?dst_uin="+id+"&spec=100");
        minePO.setSign("");
        chatHomeData.put("mine",minePO);
        chatHomeData.put("group",groups);
        chatHomeData.put("friend",friends);
        log.info("chat:"+chatHomeData);
        chatHomeVO.setCode(0);
        chatHomeVO.setMsg("");
        chatHomeVO.setData(chatHomeData);
        return chatHomeVO;
    }
}

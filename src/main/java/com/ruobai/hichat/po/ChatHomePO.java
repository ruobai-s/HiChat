package com.ruobai.hichat.po;

import com.ruobai.hichat.entity.Friend;
import com.ruobai.hichat.entity.Group;
import com.ruobai.hichat.entity.Mine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatHomePO {
    private List<Mine> mines;
    private List<Friend> friends;
    private List<Group> groups;
}

package com.ruobai.hichat.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendPO {
    private String groupname;
    private String id;
    private List<MinePO> list;
}

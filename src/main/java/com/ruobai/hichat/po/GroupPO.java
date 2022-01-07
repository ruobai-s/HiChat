package com.ruobai.hichat.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupPO {
    private String groupname;
    private String id;
    private String avatar;
}

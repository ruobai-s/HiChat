package com.ruobai.hichat.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MinePO {
    private String username;
    private String id;
    private String status;
    private String sign;
    private String avatar;
}

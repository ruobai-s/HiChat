package com.ruobai.hichat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    public String user_id;
    public String nickname;
    public String remark;
    public String image_url;
}

package com.ruobai.hichat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mine {
    public String username;
    public String id;
    public String status;
    public String remark;
    public String image_url;
}

package com.ruobai.hichat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    public String group_id;
    public String group_name;
    public int max_member_count;
    public int member_count;
    public String image_url;
}

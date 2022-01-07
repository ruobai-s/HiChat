package com.ruobai.hichat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public String avatar;
    public String content;
    public String id;
    public boolean mine;
    public String username;
    public String password;
}

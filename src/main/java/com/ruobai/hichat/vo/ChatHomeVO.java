package com.ruobai.hichat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatHomeVO<T> {
    public Integer code;
    public String msg;
    private Map<String,T> data;
}

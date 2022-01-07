package com.ruobai.hichat.repository;

import com.ruobai.hichat.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginRepository {
    User login(String username, String password);
}

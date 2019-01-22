package com.qf.service;

import com.qf.entity.User;

/**
 * @Author ken
 * @Time 2019/1/22 9:27
 * @Version 1.0
 */
public interface IUserService {

    User queryByUserNameAndPassword(String username, String password);
}

package com.example.webfluxdemo1.service;

import com.example.webfluxdemo1.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/7
 * 用户操作接口
 **/
public interface UserService {
    //根据ID查询用户
    Mono<User> getUserById(int id);
    //查询所有用户
    Flux<User> getAllUser();
    //添加用户
    Mono<Void> saveUserInfo(Mono<User> user);
}

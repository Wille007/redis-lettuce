package com.wille.redis.lettuce.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wille.redis.lettuce.pojo.User;
import com.wille.redis.lettuce.mapper.UserMapper;
import com.wille.redis.lettuce.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * User Service Implement class
 * </p>
 *
 * @author Wille
 * @since 2022-10-13
 */
@Service
@CacheConfig(cacheNames = "user")
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Cacheable(key="#id", unless = "#result==null")
    @Override
    public User queryById(Long id) {
        Optional<User> user= Optional.ofNullable(this.getOne(Wrappers.<User>lambdaQuery().eq(User::getId, id)));
        return user.get();
    }
    @CachePut(key = "#user.id", unless = "#result==null or #result.id ==null")
    @Override
    public User insert(User user){
        save(user);
        Optional<User> userResult= Optional.ofNullable(this.getOne(Wrappers.<User>lambdaQuery().eq(User::getCode, user.getCode())));
        return userResult.get();
    }

    @CacheEvict(key = "#id", condition = "#result==true")
    @Override
    public boolean deleteById(Long id) {
        return true;
    }



}

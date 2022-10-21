package com.wille.redis.lettuce.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONB;
import com.alibaba.fastjson2.JSONObject;
import com.wille.redis.lettuce.pojo.User;
import com.wille.redis.lettuce.pojo.request.UserSetParams;
import com.wille.redis.lettuce.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wille
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, Object> objectRedisTemplate;

    @PostMapping("/setUser")
    public JSONObject setUser(@RequestBody UserSetParams userSetParams){
        User insertUser = new User();
        BeanUtils.copyProperties(userSetParams, insertUser);
        stringRedisTemplate.opsForValue().set("str",JSON.toJSONString(insertUser));
        objectRedisTemplate.opsForValue().set("obj",insertUser);
        return JSONObject.parseObject(JSON.toJSONString(insertUser));
    }

    @GetMapping("/getStrObj")
    public User getObjStr(){
        return JSONObject.parseObject(stringRedisTemplate.opsForValue().get("str"),User.class);
    }

    @GetMapping("/getObj")
    public User getObj(){
        return (User) objectRedisTemplate.opsForValue().get("obj");
    }

    @GetMapping("/obj/getObj")
    public User getObjObj(){
        return JSONB.parseObject(JSONB.toBytes(objectRedisTemplate.opsForValue().get("obj")),User.class);
    }

    @GetMapping("/cache/getById/{id}")
    public User getCacheById(@PathVariable("id") Long id){
        return userService.queryById(id);
    }

    @PostMapping("/cache/addUser")
    public User addUser(@RequestBody User user){
        return userService.insert(user);
    }

    @PostMapping("/cache/delete/{id}")
    public boolean delete(@PathVariable("id") Long id){
        return userService.deleteById(id);
    }




}

package com.wille.redis.lettuce;

import com.wille.redis.lettuce.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
@Slf4j
public class RedisTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    void testString(){
        stringRedisTemplate.opsForValue().set("str","this is str");
        log.info("str result: {}",stringRedisTemplate.opsForValue().get("str"));
    }

    @Test
    void testObject(){
        User user1 = User.builder().id(1L).userName("wille1").code("c1").nickName("wille1").password("pwd1").build();
        User user2 = User.builder().id(2L).userName("wille2").code("c2").nickName("wille2").password("pwd2").build();
        List<User> list1 = Arrays.asList(user1,user2);

        redisTemplate.opsForValue().set("obj",list1);
        List<User> resultList = (List<User>) redisTemplate.opsForValue().get("obj");
        log.info("obj result: {}",resultList);
    }

}

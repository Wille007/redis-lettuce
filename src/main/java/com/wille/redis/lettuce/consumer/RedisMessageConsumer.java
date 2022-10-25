package com.wille.redis.lettuce.consumer;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.wille.redis.lettuce.pojo.mq.redis.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wille
 */
@Component
@Slf4j
public class RedisMessageConsumer implements MessageListener {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("SUB->topic: {}",new String(pattern));
        log.info("SUB->body pre: {}",new String(message.getBody()));
        MessageDTO messageBody = JSONObject.parseObject(new String(message.getBody()),new TypeReference<MessageDTO>() {});
        log.info("SUB->message body finally: {}",messageBody);
    }
}

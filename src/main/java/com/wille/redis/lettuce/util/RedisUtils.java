package com.wille.redis.lettuce.util;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.wille.redis.lettuce.pojo.mq.redis.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author wille
 */
@Component
@Slf4j
public class RedisUtils {

    /**
     * atomic delete lock key lua script
     */
    private static final String LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    /**
     * redis Template for String
     */
    private static StringRedisTemplate stringRedisTemplate;
    /**
     * redis template for object
     */
    private static RedisTemplate redisTemplate;

    @Resource
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate){
        RedisUtils.stringRedisTemplate = stringRedisTemplate;
    }
    @Resource
    public void setRedisTemplate(RedisTemplate redisTemplate){
        RedisUtils.redisTemplate = redisTemplate;
    }

    /**
     * get StringRedisTemplate
     * @return StringRedisTemplate
     */
    public static StringRedisTemplate getStringRedisTemplate(){
        return stringRedisTemplate;
    }
    /**
     * get RedisTemplate
     * @return RedisTemplate
     */
    public static RedisTemplate getRedisTemplate(){
        return redisTemplate;
    }

    /**
     * save formal String
     * @param key
     * @param value
     */
    public static void setString(String key, String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

    /**
     * save formal String with expire time
     * @param key
     * @param value
     * @param expireTime
     * @param unit
     */
    public static void setString(String key, String value,long expireTime,TimeUnit unit){
        stringRedisTemplate.opsForValue().set(key,value,expireTime,unit);
    }

    /**
     * save formal hash String
     * @param key
     * @param hashKey
     * @param value
     */
    public static void setHashString(String key, Object hashKey, String value){
        stringRedisTemplate.opsForHash().put(key,hashKey,value);
    }

    /**
     * save formal hash String with expire time
     * @param key
     * @param hashKey
     * @param value
     * @param expireTime
     * @param unit
     */
    public static void setHashString(String key, Object hashKey, String value,long expireTime, TimeUnit unit){
        stringRedisTemplate.opsForHash().put(key,hashKey,value);
        stringRedisTemplate.expire(key,expireTime,unit);
    }
    /**
     * save hashed key,object
     * @param key
     * @param hashKey
     * @param object
     */
    @SuppressWarnings("unchecked")
    public static void setHashObject(String key, Object hashKey, Object object){
        redisTemplate.opsForHash().put(key, hashKey,object);
    }

    /**
     * save hashed key,object with expire time
     * @param key
     * @param hashKey
     * @param object
     * @param expireTime
     * @param unit
     */
    @SuppressWarnings("unchecked")
    public static void setHashObject(String key, Object hashKey, Object object, long expireTime, TimeUnit unit){
        redisTemplate.opsForHash().put(key, hashKey,object);
        redisTemplate.expire(key,expireTime,unit);
    }
    /**
     * save formal key,Object
     * @param key
     * @param object
     */
    @SuppressWarnings("unchecked")
    public static void setObject(String key, Object object){
        redisTemplate.opsForValue().set(key,object);
    }

    /**
     * save formal key, object with expire time
     * @param key
     * @param object
     * @param expireTime
     * @param unit
     */
    @SuppressWarnings("unchecked")
    public static void setObject(String key, Object object, long expireTime, TimeUnit unit){
        redisTemplate.opsForValue().set(key,object,expireTime,unit);
    }
    /**
     * save List
     * @param key
     * @param list
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> void setList(String key, List<T> list){
        redisTemplate.opsForList().rightPush(key,list);
    }

    /**
     * save List with expire time
     * @param key
     * @param list
     * @param expireTime
     * @param unit
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> void setList(String key, List<T> list, long expireTime, TimeUnit unit){
        redisTemplate.opsForList().rightPush(key,list);
        redisTemplate.expire(key,expireTime,unit);
    }
    /**
     * save hashed key,list
     * @param key
     * @param hashKey
     * @param list
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> void setHashList(String key, Object hashKey, List<T> list){
        redisTemplate.opsForHash().put(key, hashKey, list);
    }

    /**
     * save hashed key,list with expire time
     * @param key
     * @param hashKey
     * @param list
     * @param expireTime
     * @param unit
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> void setHashList(String key, Object hashKey, List<T> list, long expireTime, TimeUnit unit){
        redisTemplate.opsForHash().put(key, hashKey, list);
        redisTemplate.expire(key,expireTime,unit);
    }
    /**
     * get String result from normal string key
     * @param key
     * @return
     */
    public static String getString(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }
    /**
     * get String result from hashed string key
     * @param key
     * @param hashKey
     * @return
     */
    public static String getHashString(String key, Object hashKey){
        return (String) stringRedisTemplate.opsForHash().get(key,hashKey);
    }
    /**
     * get Bean object（only can be used for single class）
     * @param key
     * @return
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> T getObject(String key){
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * get bean object from hash Key
     * @param key
     * @param hashKey
     * @return
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> T getHashObject(String key, Object hashKey){
        return (T) redisTemplate.opsForHash().get(key,hashKey);
    }
    /**
     * get List bean from hashed key
     * @param key
     * @param hashKey
     * @return
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getHashList(String key, Object hashKey){
        return (List<T>) redisTemplate.opsForHash().get(key, hashKey);
    }
    /**
     * get List bean
     * @param key
     * @return
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getList(String key){
        return redisTemplate.opsForList().range(key,0,-1);
    }
    /**
     * save Set
     * @param key
     * @param set
     * @param <T>
     */
    public static <T> void setSet(String key, Set<T> set){
        Set<T> newSet = getSet(key);
        if(!CollectionUtils.isEmpty(newSet)) {
            newSet.addAll(set);
            stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(newSet));
        }
        else{
            stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(set));
        }
    }

    /**
     * save Set with expire time
     * @param key
     * @param set
     * @param <T>
     */
    public static <T> void setSet(String key, Set<T> set,long expireTime, TimeUnit unit){
        Set<T> newSet = getSet(key);
        if(!CollectionUtils.isEmpty(newSet)) {
            newSet.addAll(set);
            stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(newSet),expireTime, unit);
        }
        else{
            stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(set), expireTime, unit);
        }
    }

    /**
     * get Set
     * @param key
     * @return
     * @param
     */
    public static <T> Set<T> getSet(String key){
        return JSONObject.parseObject(stringRedisTemplate.opsForValue().get(key),new TypeReference<Set<T>>(){});
    }

    /**
     * save map （right push for each time）
     * @param key
     * @param map
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> void setMap(String key, Map<String,T> map){
        if(!CollectionUtils.isEmpty(map)) {
            redisTemplate.opsForHash().putAll(key, map);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> void setMap(String key, Map<String,T> map, long expireTime, TimeUnit unit){
        if(!CollectionUtils.isEmpty(map)) {
            redisTemplate.opsForHash().putAll(key, map);
            redisTemplate.expire(key, expireTime,unit);
        }
    }

    /**
     * get map
     * @param key
     * @return
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String,T> getMap(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * increase 1 (if there is no key, then it will be ok, after increase 1, then the value will be 1)
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Long increase(String key){
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * decrease 1
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Long decrease(String key){
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * get Long value result
     * @param key
     * @return
     */
    public static Long getLong(String key){
        Object obj = redisTemplate.opsForValue().get(key);
        if(ObjectUtils.isEmpty(obj)){
            return null;
        }
        return (Long) redisTemplate.opsForValue().get(key);
    }

    /**
     * delete key
     * @param keys
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean del(String ... keys){
        if(ObjectUtils.isEmpty(keys)) {
            return false;
        }
        return redisTemplate.delete(keys);
    }

    /**
     * get object size
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Long size(String key){
        return redisTemplate.opsForValue().size(key);
    }
    /**
     * check key is exist or not
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean hasKey(String key){
        if(StringUtils.isEmpty(key)) {
            return false;
        }
        return redisTemplate.hasKey(key);
    }

    // redis lock
    /**
     * lock
     * @param lockKey
     * @param lockId
     * @param time
     * @return
     */
    public static boolean setNx(String lockKey, String lockId, long time, TimeUnit unit){
        return Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent(lockKey,lockId,time, unit));
    }
    /**
     * check it is still in lock ior not
     * @param lockKey
     * @param lockId
     * @return
     */
    public static boolean isLock(String lockKey, String lockId){
        if(StringUtils.isEmpty(lockKey)){
            return false;
        }
        if(stringRedisTemplate.hasKey(lockKey)){
            if(lockId.equals(stringRedisTemplate.opsForValue().get(lockKey))){
                return false;
            }
        }
        return true;
    }
    /**
     * delete lock by lua script
     * @param lockKey
     * @param lockId
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean delLock(String lockKey, String lockId){
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(LUA_SCRIPT);
        redisScript.setResultType(Long.class);
        Long result = (Long) redisTemplate.execute(redisScript, Arrays.asList(lockKey), lockId);
        if(ObjectUtils.isEmpty(result) || result.longValue() ==0){
            return false;
        }
        return true;
    }

    // pub/sub

    /**
     * pub messages
     * @param topic
     * @param message
     */
    public static boolean pub(String topic, MessageDTO message){
        if(StringUtils.isEmpty(topic) || ObjectUtils.isEmpty(message)){
            log.error("PUB ERROR： topic or message is NULL");
            return false;
        }
        try{
            stringRedisTemplate.convertAndSend(topic, JSON.toJSONString(message));
            log.info("Redis PUB is success： topic->{}, message->{}",topic,message);
            return true;
        }catch (Exception e){
            log.error("Redis PUB is fail： topic->{}, message->{}, FAIL ERROR: {}",topic,message,e);
            return false;
        }
    }






}

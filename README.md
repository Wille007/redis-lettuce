# redis-lettuce

Based on Maven

## Version:

jdk->17  
lombok->1.18.24  
fastjson->2.0.15  
mybatis-plus->3.5.2  
mybatis-boot->2.2.2   
spring-boot-redis->2.7.4  

## Redis Serializer Description:


*GenericJackson2JsonRedisSerializer VS Jackson2JsonRedisSerializer*  

### data store type difference  
a data collection can be saved in **GenericJackson2JsonRedisSerializer**  
but that can not be saved in **Jackson2JsonRedisSerializer**  
*(we are just discussing then in the redis area, but you can still use it by transfer collection to JSON String)*  

### GenericJackson2JsonRedisSerializer Saving data example
```json
{
  "@class": "com.wille.redis.lettuce.pojo.User",
  "code": "c001",
  "userName": "wille",
  "nickName": "wille007",
  "password": "12345678",
  "privateKey": "willeprivatekey"
}
```
### Jackson2JsonRedisSerializer Saving data example
```json
{
  "code": "c001",
  "userName": "wille",
  "nickName": "wille007",
  "password": "12345678",
  "privateKey": "willeprivatekey"
}
```  
Casting Error using Jackson2JsonRedisSerializer like below
```java
(User) objectRedisTemplate.opsForValue().get("obj")
```
the result comes from redis is ***LinkedHashMap*** if using Jackson2JsonRedisSerializer
so _we can not cast redis result to Bean or Bean Collections directly_  

### Solve Casting problem of Jackson2JsonRedisSerializer  
Re-define ObjectMapper of Jackson2JsonRedisSerializer
```java
    private Jackson2JsonRedisSerializer<Object> serializer(){
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jsonRedisSerializer.setObjectMapper(objectMapper);
        return jsonRedisSerializer;
    }
```
So we are using GenericJackson2JsonRedisSerializer, if you want to use more personal setting for serializer, then you can use Jackson2JsonRedisSerializer.
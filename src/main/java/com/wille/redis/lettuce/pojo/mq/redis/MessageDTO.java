package com.wille.redis.lettuce.pojo.mq.redis;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author wille
 */
@Data
@Builder
@ToString
public class MessageDTO implements Serializable {

    private String title;

    private String content;

    private String data;
}

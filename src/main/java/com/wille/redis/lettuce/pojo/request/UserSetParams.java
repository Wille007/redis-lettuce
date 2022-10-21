package com.wille.redis.lettuce.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author wille
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSetParams implements Serializable {

    /**
     * 用户编码
     */
    private String code;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 私钥
     */
    private String privateKey;
}

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
     * user code
     */
    private String code;

    /**
     * username
     */
    private String userName;

    /**
     * nickname
     */
    private String nickName;

    /**
     * password
     */
    private String password;

    /**
     * private key
     */
    private String privateKey;
}

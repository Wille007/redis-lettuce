package com.wille.redis.lettuce.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * User
 * </p>
 *
 * @author Wille
 * @since 2022-10-13
 */
@Data
@TableName("tb_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * user code
     */
    private String code;

    /**
     * username
     */
    private String userName;

    /**
     * nickName
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

    /**
     * last updated by
     */
    private String lastUpdatedBy;

    /**
     * last updated on
     */
    private Long lastUpdatedOn;

    /**
     * created on (mills)
     */
    private Long createOn;

    /**
     * created by
     */
    private String createdBy;
}

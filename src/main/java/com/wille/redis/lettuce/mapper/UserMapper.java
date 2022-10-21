package com.wille.redis.lettuce.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wille.redis.lettuce.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * User Mapper Interface
 * </p>
 *
 * @author Wille
 * @since 2022-10-13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

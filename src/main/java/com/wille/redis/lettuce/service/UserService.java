package com.wille.redis.lettuce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wille.redis.lettuce.pojo.User;


/**
 * <p>
 * User Interface class
 * </p>
 *
 * @author Wille
 * @since 2022-10-13
 */
public interface UserService extends IService<User> {

    /**
     *  find By User Id
     * @param id
     * @return Optional<User>
     */
    User queryById(Long id);

    /**
     * save by entity
     * @param user
     * @return
     */
    User insert(User user);

    /**
     * delete by User Id
     * @param id
     * @return
     */
    boolean deleteById(Long id);

}

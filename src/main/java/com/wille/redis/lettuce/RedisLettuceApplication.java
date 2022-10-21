package com.wille.redis.lettuce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author wille
 */
@SpringBootApplication
@EnableCaching
public class RedisLettuceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisLettuceApplication.class, args);
	}

}

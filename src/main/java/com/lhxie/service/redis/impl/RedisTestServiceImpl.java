package com.lhxie.service.redis.impl;

import org.springframework.stereotype.Service;

import com.lhxie.service.redis.IRedisTestService;

@Service
public class RedisTestServiceImpl implements IRedisTestService {

	@Override
	public String getTimestamp(String param) {
		Long timestamp = System.currentTimeMillis();
		return timestamp.toString();
	}
}

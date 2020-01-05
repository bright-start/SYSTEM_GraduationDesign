package com.cys.sso.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.cys.sso.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

@RestController
@EnableScheduling
public class SmsController {

	@Autowired
	private RedisTemplate redisTemplate;

	@RequestMapping("/sendSms")
	@Async
	public Result sendSms(String phone) {
		Map<String, String> mapMessage = new HashMap<String, String>();
		mapMessage.put("mobile", phone);
		mapMessage.put("signName", "梦想合资");

		Map<String, String> map = new HashMap<String, String>();
		String code = null;
		for (int i = 0; i < 6; i++) {
			code += String.valueOf(Math.random()*10);
		}
		map.put("code", code);

		mapMessage.put("number", JSON.toJSONString(map));

		RedisSerializer redisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(redisSerializer);
		redisTemplate.opsForValue().set(phone,code);

		return new Result().success(200,"验证码发送成功");
	}

}

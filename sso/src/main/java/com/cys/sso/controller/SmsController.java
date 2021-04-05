package com.cys.sso.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.cys.sso.pojo.Result;
import com.cys.sso.sms.SmsCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

@RestController
public class SmsController {

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private SmsCode smsCode;

	@PostMapping("/sendSms")
	public Result sendSms(String phone) {
		Map<String, String> mapMessage = new HashMap<String, String>();
		mapMessage.put("mobile", phone);
		mapMessage.put("signName", "梦想合资");

		Map<String, String> map = new HashMap<String, String>();
		StringBuilder code = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			code.append((int)(Math.random()*10));
		}
		map.put("code", code.toString());

		mapMessage.put("templateParam", JSON.toJSONString(map));

		try {
			SendSmsResponse sendSmsResponse = smsCode.sendSms(JSONObject.toJSONString(mapMessage));
			if(sendSmsResponse.getCode() == "OK"){
				RedisSerializer redisSerializer = new StringRedisSerializer();
				redisTemplate.setKeySerializer(redisSerializer);
				redisTemplate.opsForValue().set(phone,code.toString());
				redisTemplate.expire(phone,60, TimeUnit.SECONDS);
				return new Result().success(200,"验证码发送成功");
			}
		} catch (ClientException e) {
			//日志记录
		}
		return new Result().success(200,"发送失败，尝试再次发送");
	}

}

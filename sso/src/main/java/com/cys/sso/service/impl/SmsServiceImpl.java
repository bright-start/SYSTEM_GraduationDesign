package com.pyg.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

@RestController
public class SmsController {

	@Autowired@Qualifier("jmsTemplate")
	private JmsTemplate jmsTemplate;

	@GetMapping("/index")
	public String index() {
		return "hello";
	}
	
	@RequestMapping("/sendSms")
	public String sendSms() {
		Map<String, String> mapMessage = new HashMap<String, String>();
		mapMessage.put("mobile", "15836182721");
		mapMessage.put("signName", "梦想合资");

		Map<String, String> map = new HashMap<String, String>();
		map.put("code", "666666");

		mapMessage.put("number", JSON.toJSONString(map));

		jmsTemplate.convertAndSend("smsQueue",JSON.toJSONString(mapMessage));
		
		return "success";
	}

}

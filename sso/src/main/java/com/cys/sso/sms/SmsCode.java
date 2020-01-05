package com.pyg.sms;

import java.util.Map;

import com.aliyuncs.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class SmsCode {
	
	@Autowired
	private Environment env;
	
	@Value("${template_code}")
	private String templateCode;

	private Logger logger = LoggerFactory.getLogger(SmsCode.class);

	public void sendSms(String message) {
		Map maps = JSON.parseObject(message,Map.class);
		String mobile = (String)maps.get("mobile");
		String code = (String)maps.get("number");
		String signName = (String)maps.get("signName");
		String accessKeyId = env.getProperty("accessKeyId");
		String accessKeySecret = env.getProperty("accessKeySecret");
		System.out.println(code);
		try {
			com.cys.sso.utils.SmsUtils.sendSms(mobile,signName,templateCode,code,accessKeyId,accessKeySecret);
		} catch (ClientException e) {
			logger.error(e.getMessage());
		}
		
	}
}

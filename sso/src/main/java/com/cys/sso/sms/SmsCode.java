package com.cys.sso.sms;

import java.util.Map;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
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

	public SendSmsResponse sendSms(String message) throws ClientException {
		Map maps = JSON.parseObject(message,Map.class);
		String mobile = (String)maps.get("mobile");
		String templateParam = (String)maps.get("templateParam");
		String signName = (String)maps.get("signName");
		String accessKeyId = env.getProperty("accessKeyId");
		String accessKeySecret = env.getProperty("accessKeySecret");
		return SmsUtils.sendSms(mobile, signName, templateCode, templateParam, accessKeyId, accessKeySecret);
	}
}

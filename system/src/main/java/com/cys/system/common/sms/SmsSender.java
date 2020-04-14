package com.cys.system.common.sms;

import com.aliyuncs.exceptions.ClientException;
import com.cys.system.common.config.OnlyOneClassConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SmsSender {

    private String templateCode = "SMS_171750396";

    public void sendSms(String message) throws ClientException {
        Map maps = OnlyOneClassConfig.gson.fromJson(message, Map.class);
        String mobile = (String) maps.get("mobile");
        String signName = (String) maps.get("signName");
        String shopName = (String) maps.get("shopName");
        String accessKeyId = "LTAIJHgAkr7FB8tK";
        String accessKeySecret = "K43dQJfVFuSNEwQVoDwJaYcdIw2O9m";
        SmsUtils.sendSms(mobile, signName, shopName,templateCode, accessKeyId, accessKeySecret);
    }
}

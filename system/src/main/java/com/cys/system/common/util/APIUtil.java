package com.cys.system.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class APIUtil {
    public static Map<String,String> query(String ip){
        String host = "https://jkyip.market.alicloudapi.com";
        String path = "/ip";
        String method = "GET";
        String appcode = "c7698bf9ecd44a2eaaabfc405cfe888f";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ip", ip);

        Map<String,String> map = new LinkedHashMap<>();

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String string = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String,String> map1 = objectMapper.readValue(string, Map.class);
            if("0".equals(map1.get("status"))) {
                map.put("city", map1.get("city"));
                map.put("province", map1.get("province"));
            }else {
                map.put("city",null);
                map.put("province",null);
            }
        } catch (Exception e) {
            map.put("city",null);
            map.put("province",null);
        }
        return map;
    }

//    public static void main(String[] args) {
//        query("101.67.148.47");
//    }
}

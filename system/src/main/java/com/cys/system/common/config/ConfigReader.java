package com.cys.system.common.config;

import com.cys.system.common.util.AesUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;

//@Configuration
public class ConfigReader {

    private final static Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private final static Gson gson = new GsonBuilder().serializeNulls().create();

    public ConfigReader() {
        try {
            //读取到静态资源文件
            org.springframework.core.io.Resource resource = new ClassPathResource("config/config.json");
            File file = resource.getFile();
            //使用io读出数据
            InputStream in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            BufferedInputStream inputStream = new BufferedInputStream(in);
            inputStream.read(bytes);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(bytes);
            outputStream.flush();

            byte[] bytes1 = outputStream.toByteArray();
//            String decrypt = AesUtil.decrypt(new String(bytes1));
//
//            String jsonStr = decrypt.toString().replace(" ","");
            gson.fromJson(new String(bytes1), ConfigValue.class);

        } catch (Exception e) {
            logger.error("初始化配置文件错误");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        try {
            //混淆并加密
            File file = new File("src/main/resources/comfig_temp/config.json");
            //使用io读出数据
            InputStream in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            BufferedInputStream inputStream = new BufferedInputStream(in);
            inputStream.read(bytes);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(bytes);
            outputStream.flush();

            byte[] bytes1 = outputStream.toByteArray();
            String json = gson.toJson(new String(bytes1), ConfigValue.class);
            String encrypt = AesUtil.encrypt(json);

            Resource resource2 = new ClassPathResource("config/config.json");
            File file1 = resource2.getFile();

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file1));
            bufferedOutputStream.write(encrypt.getBytes());
            bufferedOutputStream.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

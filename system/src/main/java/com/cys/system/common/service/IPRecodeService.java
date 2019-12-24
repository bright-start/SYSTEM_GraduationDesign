package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.IPRecord;
import org.apache.ibatis.annotations.Param;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public interface IPRecodeService {
    Result findAllIPRecode(Integer start,Integer rows,IPRecord ipRecord) throws Exception;
    IPRecord getIPRecode(String ip);
    void recodeIP(String ip) throws Exception;
    void updateIPRecord(IPRecord ipRecord);
    void refreshRecode();
}

package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.mapper.IPRecordMapper;
import com.cys.system.common.pojo.IPRecord;
import com.cys.system.common.service.IPRecodeService;
import com.cys.system.common.util.APIUtil;
import com.cys.system.common.util.AesUtil;
import com.cys.system.common.util.TimeConverter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.crypto.RsaMd5CksumType;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

@Service
public class IPRecordServiceImpl implements IPRecodeService {

    @Resource
    private IPRecordMapper ipMapper;

    @Override
    public Result findAllIPRecode(Integer page, Integer rows, IPRecord ipRecord) throws Exception {
        long totalCount = ipMapper.getTotalCount();

        if (totalCount > 0) {
            int pageNum = (int) Math.ceil(totalCount * 1.0 / rows);
            if (page >= pageNum) {
                page = pageNum;
            }
            int start = (page - 1) * rows;

            PageHelper.startPage(start, rows);
            if (ipRecord.getBlackList() == null) {
                ipRecord.setBlackList(AesUtil.encrypt("0"));
            } else {
                ipRecord.setBlackList(AesUtil.encrypt(ipRecord.getBlackList()));
            }
            if (ipRecord.getIpAreaCity() != null) {
                ipRecord.setIpAreaCity(AesUtil.encrypt(ipRecord.getIpAreaCity()));
            }
            if (ipRecord.getIpAreaProvince() != null) {
                ipRecord.setIpAreaProvince(AesUtil.encrypt(ipRecord.getIpAreaProvince()));
            }
            List<IPRecord> allIPRecode = ipMapper.findAllIPRecode(ipRecord);
            if (allIPRecode != null && !allIPRecode.isEmpty()) {
                PageInfo<IPRecord> pageInfo = new PageInfo<>();
                pageInfo.setList(allIPRecode);
                pageInfo.setTotal(totalCount);

                return new Result().success(pageInfo);
            }
        }
        return new Result().success("无数据");
    }

    @Override
    public IPRecord getIPRecode(String ip) {
        return ipMapper.getIPRecode(ip);
    }

    @Override
    public void recodeIP(String ip) throws Exception {
        IPRecord ip1 = new IPRecord();
        ip1.setIpAddr(AesUtil.encrypt(ip));
        Date date = new Date();
        String dateToString = TimeConverter.DateToString(date);
        ip1.setFirstBrowseTime(AesUtil.encrypt(dateToString));
        ip1.setBeginBrowseTime(AesUtil.encrypt(dateToString));
        ip1.setBlackList(AesUtil.encrypt("0"));
        ip1.setBrowseTotalCount(AesUtil.encrypt("0"));

        Map<String, String> map = APIUtil.query(ip);
        if (map.get("city") != null) {
            ip1.setIpAreaCity(AesUtil.encrypt(map.get("city")));
        } else {
            ip1.setIpAreaCity(AesUtil.encrypt("暂无数据"));
        }
        if (map.get("province") != null) {
            ip1.setIpAreaProvince(AesUtil.encrypt(map.get("province")));
        } else {
            ip1.setIpAreaProvince(AesUtil.encrypt("暂无数据"));
        }
        ipMapper.recodeIP(ip1);
    }

    @Override
    public void updateIPRecord(IPRecord ipRecord) {
        ipMapper.updateIPRecord(ipRecord);
    }

    @Override
    public void refreshRecode() {
        List<String> ipList = ipMapper.listIPAddr();
        String init = null;
        try {
            init = AesUtil.encrypt("0");
        } catch (Exception e) {
            // 记录日志
        } finally {
            init = " ";
        }
        for (String ip : ipList) {
            ipMapper.refreshRecode(ip, init);
        }
    }
}

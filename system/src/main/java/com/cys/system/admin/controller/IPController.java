package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.InvalidRequestException;
import com.cys.system.common.pojo.IPLog;
import com.cys.system.common.pojo.IPRecord;
import com.cys.system.common.service.IPLogService;
import com.cys.system.common.service.IPRecodeService;
import com.cys.system.common.util.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/ip")
public class IPController {

    @Autowired
    private IPRecodeService ipRecordService;

    @Autowired
    private IPLogService ipLogService;

    private static final Pattern pattern = Pattern.compile("[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])");

    @PostMapping("/list")
    public Result list(Integer page, Integer rows, @RequestBody(required = false) IPRecord ipRecord) throws Exception {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        if (rows == 10 || rows == 20 || rows == 30 || rows == 40) {
            return ipRecordService.findAllIPRecode(page, rows, ipRecord);
        } else {
            throw new InvalidRequestException();
        }
    }

    @GetMapping("/get")
    public Result get(String ipAddr, @RequestParam(required = false) String date) throws InvalidRequestException {
        IPLog ipLog = new IPLog();
        ipLog.setIpAddr(ipAddr);
        if(!"null".equals(date)) {
            String date1 = date.split(" ")[0];
            Matcher matcher = pattern.matcher(date1);
            if(!matcher.find()){
                throw new InvalidRequestException();
            }
            final String[] s = date1.split("-");
            ipLog.setBrowseDateYear(s[0]);
            ipLog.setBrowseDateMonth(s[1]);
            ipLog.setBrowseDateDay(s[2]);
        }else {
            String date1 = TimeConverter.DateToString(new Date());
            String[] s = date1.split(" ")[0].split("-");
            ipLog.setBrowseDateYear(s[0]);
            ipLog.setBrowseDateMonth(s[1]);
            ipLog.setBrowseDateDay(s[2]);
        }
        return ipLogService.listIPLog(ipLog);
    }

    @GetMapping("/log/download")
    public void download() {
        // 生成报表
        // 下载
    }
}

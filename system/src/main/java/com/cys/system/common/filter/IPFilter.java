package com.cys.system.common.filter;

import com.cys.system.common.pojo.IPLog;
import com.cys.system.common.pojo.IPRecord;
import com.cys.system.common.service.IPLogService;
import com.cys.system.common.service.IPRecodeService;
import com.cys.system.common.util.AesUtil;
import com.cys.system.common.util.TimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Configuration
@EnableScheduling
@EnableAsync
public class IPFilter implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(IPRecord.class);

    @Autowired
    private IPRecodeService ipRecodeService;

    @Autowired
    private IPLogService ipLogService;

    private static String IP = null;

    private static Integer IPBROWSECOUNT = -1;

    // 阈值
    private static final Integer THRESHOLD = 100;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IPRecord");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IPRecord");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        IP = ip;
        try {
            recodeIp(ip);
            recordIPLog();
        } catch (Exception e) {
            // 打印过滤层异常日志
        }
        IP = null;

        return true;
    }

    private void recodeIp(String ip) throws Exception {
        IPRecord ipRecode = ipRecodeService.getIPRecode(AesUtil.encrypt(ip));
        if (ipRecode != null) {
            // 每天第一次访问
            if (ipRecode.getBeginBrowseTime() == null) {
                ipRecode.setBeginBrowseTime(TimeConverter.DateToString(new Date()));
                ipRecode.setBrowseTotalCount(AesUtil.encrypt("0"));
            } else {
                ipRecode.setBeginBrowseTime(TimeConverter.DateToString(new Date()));
                String totalCount = AesUtil.encrypt(String.valueOf(Integer.parseInt(AesUtil.decrypt(ipRecode.getBrowseTotalCount())) + 1));
                ipRecode.setBrowseTotalCount(totalCount);
            }
            ipRecodeService.updateIPRecord(ipRecode);
        } else {
            // 第一次访问
            ipRecodeService.recodeIP(ip);
        }

    }

    /**
     * 认为一秒之内所有请求算一次请求
     */
    @Async
    @Scheduled(fixedRate = 10000, initialDelay = 1000)
    void recordIPLog() throws Exception {
        if (IP == null) {
            return;
        }

        // ip日志记录
        String date = TimeConverter.DateToString(new Date());
        String[] split = date.split(" ");
        String[] split1 = split[0].split("-");
        IPLog ipLog = new IPLog();
        ipLog.setIpAddr(IP);
        ipLog.setBrowseTime(split[1]);
        ipLog.setBrowseDateYear(split1[0]);
        ipLog.setBrowseDateMonth(split1[1]);
        ipLog.setBrowseDateDay(split1[2]);
        ipLogService.insertIPLog(ipLog);

        // 判断一秒访问次数是否超过阈值，超过拉黑名单
        IPRecord ipRecord = ipRecodeService.getIPRecode(AesUtil.encrypt(IP));
        if (ipRecord != null) {
            Integer lastbrowseCount = Integer.parseInt(AesUtil.decrypt(ipRecord.getBrowseTotalCount()));
            if (IPBROWSECOUNT != -1) {
                if (lastbrowseCount - IPBROWSECOUNT > THRESHOLD) {
                    // 拉黑
                    ipRecord.setBlackList(AesUtil.encrypt("1"));
                    ipRecodeService.updateIPRecord(ipRecord);
                }
                IPBROWSECOUNT = lastbrowseCount;
            }
        }
    }

    /**
     * 每天00:00刷新
     */
    @Async
    @Scheduled(cron = "0 0 0 * * ?")
    void timeRefreshIPTask() {
        ipRecodeService.refreshRecode();
    }
}

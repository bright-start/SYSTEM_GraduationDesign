package com.cys.system.common.mapper;

import com.cys.system.common.pojo.IPRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IPRecordMapper {
    List<IPRecord> findAllIPRecode(IPRecord ipRecord);

    IPRecord getIPRecode(String ipAddr);

    List<String> listIPAddr();

    void recodeIP(IPRecord ipRecord);

    void updateIPRecord(IPRecord ipRecord);

    long getTotalCount();

    void refreshRecode(@Param("ipAddr") String ipAddr, @Param("init") String init);
}

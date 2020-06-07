package com.cys.system.common.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.config.OnlyOneClassConfig;
import com.cys.system.common.mapper.ShopMapper;
import com.cys.system.common.service.ShopService;
import com.cys.system.common.sms.SmsSender;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true, rollbackFor = {Exception.class})
@Service
public class ShopServiceImpl implements ShopService {

    @Resource
    private ShopMapper shopMapper;

    @Resource
    private SmsSender smsSender;

    private String templateCode = "SMS_188625955";

    @Override
    public Result listApplyShop(Integer page, Integer rows) {
        long count = shopMapper.countApplyShop();

        if (count == 0) {
            return new Result().success("无数据");
        }
        int pageNum = (int) Math.ceil(count * 1.0 / rows);

        if (page >= pageNum) {
            page = pageNum;
        }
        int start = (page - 1) * rows;

        PageHelper.startPage(start, rows);
        List<Map> applyUserList = shopMapper.listApplyShop();

        PageInfo<Map> pageInfo = new PageInfo<>();
        pageInfo.setTotal(count);
        pageInfo.setPageNum(pageNum);
        pageInfo.setList(applyUserList);
        return new Result().success(pageInfo);
    }

    @Transactional(readOnly = false)
    @Override
    public Result examine(Integer[] ids, Integer status) {
        for (Integer id : ids) {
            shopMapper.examine(id,status);
            try {
                Map info = shopMapper.getShopInfoByShopId(id);
                Map map = new HashMap();
                map.put("mobile",info.get("bind_phone"));
                map.put("templateCode",templateCode);
                Map templateParam = new HashMap();
                templateParam.put("shopName",info.get("shopName"));
                map.put("templateCode",templateCode);
                String msg = OnlyOneClassConfig.gson.toJson(map);
                smsSender.sendSms(msg);
            } catch (ClientException e) {
            }
        }
        return new Result().success("操作成功");
    }
}

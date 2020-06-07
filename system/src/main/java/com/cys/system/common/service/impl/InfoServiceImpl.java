package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.config.OnlyOneClassConfig;
import com.cys.system.common.mapper.*;
import com.cys.system.common.pojo.*;
import com.cys.system.common.service.InfoService;
import com.cys.system.common.util.TimeConverter;
import com.cys.system.common.util.TimeFormat;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true, rollbackFor = {Exception.class})
@Service
public class InfoServiceImpl implements InfoService {

    @Resource
    private SysLogMapper sysLogMapper;
    @Resource
    private EchartsMapper echartsMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private OrderMapper orderMapper;

    /**
     * 待优化
     *
     * @return
     */
    @Override
    public Result getUserInfoChart() {

        //获取图表模版
        String echartsTemplateData = echartsMapper.getEchartsTemplateByType(1);
        EchartsData echartsData = OnlyOneClassConfig.gson.fromJson(echartsTemplateData, new TypeToken<EchartsData>() {
        }.getType());

        //图表填充数据
        echartsData.getTitle().setText("近6个月注册注销堆叠图");
        EchartsData.Legend legend = echartsData.getLegend();
        legend.setData(new String[]{"用户注册人数", "店铺注册数量", "店铺关闭数量"});

        EchartsData.XAxis xAxis = echartsData.getxAxis().get(0);
        Date nowDate = new Date();
        xAxis.setData(timeFrame(nowDate, 6));

        List<EchartsData.Series> series = echartsData.getSeries();
        EchartsData.Series seriesObj = series.get(0);
        series.clear();


        int row = echartsData.getLegend().getData().length;
        List<String> data = xAxis.getData();
        int col = data.size();
        Long[][] dataValue = new Long[row][col];
        for (int i = 0; i < col; i++) {
            String[] split = data.get(i).split("[.]");
            SysLog sysLogInfo = sysLogMapper.getUserInfo(split[0], split[1]);
            if (sysLogInfo == null) {
                dataValue[0][i] = Long.valueOf(0);
                dataValue[1][i] = Long.valueOf(0);
                dataValue[2][i] = Long.valueOf(0);
            } else {
                dataValue[0][i] = sysLogInfo.getSysRegisterUserCountMonth();
                dataValue[1][i] = sysLogInfo.getSysRegisterShopCountMonth();
                dataValue[2][i] = sysLogInfo.getSysDestoryShopCountMonth();
            }
        }
        for (int i = 0; i < row; i++) {

            try {
                EchartsData.Series newSeriesObj = seriesObj.clone();
                newSeriesObj.setName(echartsData.getLegend().getData()[i]);
                newSeriesObj.setData(Arrays.asList(dataValue[i]));
                series.add(newSeriesObj);
            } catch (CloneNotSupportedException e) {
                //不处理异常
            }
        }
        echartsData.setSeries(series);
        return new Result().success(OnlyOneClassConfig.gson1.toJson(echartsData));
    }

    private List<String> timeFrame(Date nowDate, int range) {
        List<String> timeArray = new ArrayList<>();
        timeArray.add(TimeConverter.getInstance().DateToString(nowDate, TimeFormat.Y_M));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        for (int i = 0; i < range - 1; i++) {
            calendar.add(Calendar.MONTH, -1);
            Date time = calendar.getTime();
            String beforeDate = TimeConverter.getInstance().DateToString(time, TimeFormat.Y_M);
            timeArray.add(beforeDate);
        }
        Collections.reverse(timeArray);
        return timeArray;
    }

    @Override
    public Result getUserMap() {
        //获取图表模版
        String echartsTemplateData = echartsMapper.getEchartsTemplateByType(2);
        EchartsData echartsData = OnlyOneClassConfig.gson1.fromJson(echartsTemplateData, new TypeToken<EchartsData>() {
        }.getType());

        List<EchartsData.Series> series = echartsData.getSeries();
        EchartsData.Series seriesObj = series.get(0);
        List<Object> data = seriesObj.getData();

        String[] titleData = echartsData.getLegend().getData();
        int len = titleData.length;
        for (int i = 0; i < len; i++) {
            Map roleMap = new HashMap();
            Long roleNum = userMapper.getNumForRole(i);
            roleMap.put("value", roleNum);
            roleMap.put("name", titleData[i]);
            data.add(roleMap);
        }
        return new Result().success(OnlyOneClassConfig.gson.toJson(echartsData));
    }

    @Override
    public Result getShopMap(Integer shopId) {
        Long waitDealOrderNum = orderMapper.getWaitDealOrderNum(shopId);
        Long publishedGoodsNum = goodsMapper.getPublishedGoodsNum(shopId);
        Map map = new HashMap();
        map.put("waitDealOrderNum", waitDealOrderNum);
        map.put("publishedGoodsNum", publishedGoodsNum);
        return new Result().success(map);
    }
}

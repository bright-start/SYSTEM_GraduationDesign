package com.cys.search.pay.zfb.service.impl;

import com.cys.search.pay.zfb.AlipayConfig;
import com.cys.search.pay.zfb.service.AlipayService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class AlipayServiceImpl implements AlipayService {

    @Override
    public void aliPay(HttpServletResponse response, HttpServletRequest request) throws IOException {
//        response.setContentType("text/html;charset=utf-8");
//
//        PrintWriter out = response.getWriter();
//        //获得初始化的AlipayClient
//        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
//        //设置请求参数
//        AlipayTradePagePayRequest aliPayRequest = new AlipayTradePagePayRequest();
//        aliPayRequest.setReturnUrl(AlipayConfig.return_url);
//        aliPayRequest.setNotifyUrl(AlipayConfig.notify_url);
//
//        //商户订单号，后台可以写一个工具类生成一个订单号，必填
//        String order_number = new String("121311414");
//        //付款金额，从前台获取，必填
//        String total_amount = new String("201314");
//        //订单名称，必填
//        String subject = new String("祖万敏小可爱");
//        aliPayRequest.setBizContent("{\"out_trade_no\":\"" + order_number + "\","
//                + "\"total_amount\":\"" + total_amount + "\","
//                + "\"subject\":\"" + subject + "\","
//                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
//        //请求
//        String result = null;
//        try {
//            result = alipayClient.pageExecute(aliPayRequest).getBody();
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        //输出
//        out.println(result);
//        log.info("返回结果={}",result);

    }
}

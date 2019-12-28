package com.neuedu.service.impl;

import com.neuedu.common.OrderStatusEnum;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.TradeStatusEnum;
import com.neuedu.dao.PayInfoMapper;
import com.neuedu.pojo.Order;
import com.neuedu.pojo.PayInfo;
import com.neuedu.service.IOrderService;
import com.neuedu.service.IPayService;
import com.neuedu.utils.DateUtil;
import com.neuedu.utils.OrderInfoUtil2_0;
import com.neuedu.utils.ServerResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class PayServiceImpl implements IPayService {

    @Value("${alipay.appid}")
    private String APPID;

    @Value("${alipay.rsa2_private}")
    private String RSA2_PRIVATE;

    @Value("${alipay.notify_url}")
    private String NOTIFY_URL;

    @Autowired
    IOrderService orderService;
    @Autowired
    PayInfoMapper payInfoMapper;
    @Override
    public ServerResponse pay(Long orderNo) {
        //生成支付信息

        if (StringUtils.isBlank(APPID) || (StringUtils.isBlank(RSA2_PRIVATE) )) {

            return ServerResponse.createServerResponseByFail(ResponseCode.PAY_PARAM_ERROR.getCode(),ResponseCode.PAY_PARAM_ERROR.getMsg());
        }

        //1.根据订单号查询订单
        ServerResponse serverResponse = orderService.findOrderByOrderno(orderNo);
        if (!serverResponse.isSucess()){
            return serverResponse;
        }
        Order order = (Order) serverResponse.getData();
        //创建支付订单,返回的是一个map类型
        Map<String,String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID,true,order,NOTIFY_URL);
        //将map转换成字符串
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        //构建签名，用rsa2的方式
        String sign = OrderInfoUtil2_0.getSign(params, RSA2_PRIVATE, true);
        //构建orderInfo
        final String orderInfo = orderParam + "&" + sign;
        return ServerResponse.createServerResponseBySucess(orderInfo);
    }

    @Override
    public String callbackLogic(Map<String, String> stringMap) {
        //1.获取订单编号orderNo
        Long orderNo = Long.valueOf(stringMap.get("out_trade_no"));
        //2.校验orderNo是否存在
        ServerResponse serverResponse = orderService.findOrderByOrderno(orderNo);
        if(!serverResponse.isSucess()){//如果订单号不存在的话，没有再通知的必要了，所以返回fail
            return "success";
        }
        //3.判断订单是否被修改
        Order order = (Order) serverResponse.getData();

        //4.修改订单状态
        if (order.getStatus() >= OrderStatusEnum.ORDER_PAYED.getStatus()) {//判断订单的状态是不是大于等于20
            //订单已经支付
            return "success";
        }

        //获取订单的支付状态
        String trade_status = stringMap.get("trade_status");
        Date payedtime = DateUtil.string2Date(stringMap.get("gmt_payment"));

        ServerResponse serverResponse1 = orderService.updateOrderByPayed(orderNo, TradeStatusEnum.statusOf(trade_status),payedtime);
        if (!serverResponse1.isSucess()){
            return "fail";
        }
        //5.插入获更新支付信息，如果没有支付信息则插入支付信息；如果有支付信息则更新支付信息
        PayInfo payInfo = new PayInfo();
        payInfo.setOrderNo(orderNo);
        payInfo.setUserId(order.getUserId());
        payInfo.setPayPlatform(1);//支付方式，是支付宝
        payInfo.setPlatformNumber(stringMap.get("trade_no"));  //支付宝交易凭证号，即交易流水号
        payInfo.setPlatformStatus(trade_status); //支付状态
        //下面判断一下到底有没有支付信息
        PayInfo payInfo1 = findPayInfoByOrderNo(orderNo);
        int count = 0;
        if (payInfo1 == null){
            //插入
            count = payInfoMapper.insert(payInfo);//写着写着，觉得这里应该在insert加上主键，下面更新之前先给它赋值id了
        }else {
            //更新
            payInfo.setId(payInfo1.getId());
            payInfoMapper.updateByPrimaryKey(payInfo);
        }
        if (count == 0){
            return "fail";
        }

        //6。返回结果
        return "success";
    }
    //根据订单号来查询支付信息
    private PayInfo findPayInfoByOrderNo(Long orderNo){
        PayInfo payInfo = payInfoMapper.selectByOrderNo(orderNo);
        return payInfo;
    }
}

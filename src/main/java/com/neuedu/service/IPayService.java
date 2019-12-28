package com.neuedu.service;

import com.neuedu.utils.ServerResponse;

import java.util.Map;

public interface IPayService {
    public ServerResponse pay(Long orderNo);
    //支付宝回调逻辑，支付成功后的业务逻辑,传的参数是支付宝支付成功后返回的参数
    public String callbackLogic(Map<String, String> stringMap);
}

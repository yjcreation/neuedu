package com.neuedu.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.neuedu.service.IPayService;
import com.neuedu.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/portal/pay/")
public class AliPayController {
    @Autowired
    IPayService payService;
    @Value("${alipay.publickey}")
    private String alipayPublicKey;

    //支付接口
    @RequestMapping("pay.do")
    public ServerResponse pay(Long orderNo){
        return payService.pay(orderNo);
    }

    //回调alipay，让支付宝服务器调用.返回值是String类型，往支付宝服务器返回，
    //返回success或者fail，如果返回success，表示商家已经处理完了订单；如果返回fail，支付宝认为你没有处理完。
    @RequestMapping("callback.do")
    public  String  alipayCallBack(HttpServletRequest request){
        //支付宝验签，确保是支付宝回调，不能是别的黑客什么之类伪装支付宝（确定接口是支付宝服务器调用的）
        //从请求中获得map
        Map<String,String[]> params=request.getParameterMap();
        //判断参数是否为空或者长度等于0
        if(params==null||params.size()==0){
            return "fail";
        }
        //签名参数
        Map<String,String> signParam=new HashMap<>();
        //取出所有的key，是一个set集合
        Set<String> keys=params.keySet();
        Iterator<String> iterator=keys.iterator();
        //迭代，将params——>装换成signParam
        while (iterator.hasNext()){
            String key=iterator.next();
            String[] values=params.get(key);
            StringBuilder stringBuilder=new StringBuilder();
            for(String value:values){
                stringBuilder.append(value+",");
            }
            String value=stringBuilder.toString();
            value= value.substring(0,value.length()-1);
            signParam.put(key,value);

        }



        //验证签名,第一个参数是map,第二个是支付宝公钥，第三个是字符编码，第四个是签名方式RSA2
        try {
            signParam.remove("sign_type");//此处在验证之前，必须得先移除一个参数
            boolean checkSign = AlipaySignature.rsaCheckV2(signParam,alipayPublicKey,"utf-8","RSA2");
            if (checkSign){
                //验签通过
                //处理业务逻辑
                System.out.println("验证签名通过");
                return payService.callbackLogic(signParam);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        String success = "success";//返回给支付宝的不能加引号，所以先赋值给一个字符串变量，然后返回（不过个人不是很理解）
        return success;

    }
}

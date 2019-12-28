package com.neuedu.controller;

import com.neuedu.common.Const;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.IShippingService;
import com.neuedu.utils.ServerResponse;
import com.neuedu.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Service;

@RestController
@RequestMapping("/portal/shipping/")
public class ShippingController {
    @Autowired
    IShippingService shippingService;
    //1.添加地址
    @RequestMapping("add.do")
    public ServerResponse add(Shipping shipping, HttpSession session){
        UserVO userVO = (UserVO) session.getAttribute(Const.CURRENT_USER);
        return shippingService.add(shipping,userVO.getId());
    }
    //2.删除地址
    @RequestMapping("delete.do")
    public ServerResponse delete(Integer shippingId){
        return shippingService.delete(shippingId);
    }

    //3.登录状态更新地址
    @RequestMapping("update.do")
    public ServerResponse update(Shipping shipping){
        return shippingService.update(shipping);
    }

    //4.选中查看具体的地址
    @RequestMapping("select.do")
    public ServerResponse select(Integer shippingId){
        return shippingService.select(shippingId);
    }
    //5.地址列表
    @RequestMapping("list.do")
    public ServerResponse list(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        return shippingService.list(pageNum, pageSize);
    }
}

package com.neuedu.service;

import com.neuedu.pojo.Shipping;
import com.neuedu.utils.ServerResponse;

public interface IShippingService {
    //1.添加地址
    public ServerResponse add(Shipping shipping, Integer userId);
    //2.删除地址
    public ServerResponse delete(Integer shippingId);

    //3.登录状态更新地址
    public ServerResponse update(Shipping shipping);
    //4.选中查看具体的地址
    public ServerResponse select(Integer shippingId);
    //5.地址列表(管理员查询所有)
    public ServerResponse list(Integer pageNum, Integer pageSize);
}

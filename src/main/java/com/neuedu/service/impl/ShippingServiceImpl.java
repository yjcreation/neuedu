package com.neuedu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neuedu.common.ResponseCode;
import com.neuedu.dao.ShippingMapper;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.IShippingService;
import com.neuedu.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Shipping shipping, Integer userId) {
        if (shipping == null){
            return ServerResponse.createServerResponseByFail(ResponseCode.PARAMTER_NOT_EMPTY.getCode(), ResponseCode.PARAMTER_NOT_EMPTY.getMsg());
        }
        shipping.setUserId(userId);
        int count = shippingMapper.insert(shipping);
        if (count == 0){
            //添加失败
            return ServerResponse.createServerResponseByFail(ResponseCode.SHIPPING_ADD_FAIL.getCode(), ResponseCode.SHIPPING_ADD_FAIL.getMsg());
        }

        return ServerResponse.createServerResponseBySucess(shipping);
    }

    @Override
    public ServerResponse delete(Integer shippingId) {
        if (shippingId == null){
            return ServerResponse.createServerResponseByFail(ResponseCode.PARAMTER_NOT_EMPTY.getCode(), ResponseCode.PARAMTER_NOT_EMPTY.getMsg());
        }
        int count = shippingMapper.deleteByPrimaryKey(shippingId);
        if (count == 0){
            //删除失败
            return ServerResponse.createServerResponseByFail(ResponseCode.SHIPPING_DELETE_FAIL.getCode(),ResponseCode.SHIPPING_DELETE_FAIL.getMsg());
        }
        return ServerResponse.createServerResponseBySucess();
    }

    @Override
    public ServerResponse update(Shipping shipping) {
        if (shipping == null){
            return ServerResponse.createServerResponseByFail(ResponseCode.PARAMTER_NOT_EMPTY.getCode(), ResponseCode.PARAMTER_NOT_EMPTY.getMsg());
        }
        //shipping.setUserId(userId);//此处存不存无所谓
        int count = shippingMapper.updateByPrimaryKey(shipping);
        if (count == 0){
            //更新失败
            return ServerResponse.createServerResponseByFail(ResponseCode.SHIPPING_UPDATE_FAIL.getCode(), ResponseCode.SHIPPING_UPDATE_FAIL.getMsg());
        }

        return ServerResponse.createServerResponseBySucess(shipping);
    }

    @Override
    public ServerResponse select(Integer shippingId) {
        if (shippingId == null){
            return ServerResponse.createServerResponseByFail(ResponseCode.PARAMTER_NOT_EMPTY.getCode(), ResponseCode.PARAMTER_NOT_EMPTY.getMsg());
        }
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        if (shipping == null){
            //查询失败
            return ServerResponse.createServerResponseByFail(ResponseCode.SHIPPING_SELECT_FAIL.getCode(),ResponseCode.SHIPPING_SELECT_FAIL.getMsg());
        }
        return ServerResponse.createServerResponseBySucess();
    }

    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);

        List<Shipping> shippingList =  shippingMapper.selectAll();
        /*if (shippingList == null){

        }*/
        PageInfo pageInfo=new PageInfo(shippingList);
        //pageInfo.setList(productListVOList);


        return ServerResponse.createServerResponseBySucess(pageInfo);
    }
}

package com.neuedu.dao;

import com.neuedu.pojo.PayInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_payinfo
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_payinfo
     *
     * @mbg.generated
     */
    int insert(PayInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_payinfo
     *
     * @mbg.generated
     */
    PayInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_payinfo
     *
     * @mbg.generated
     */
    List<PayInfo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_payinfo
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PayInfo record);

    //根据订单编号来查询支付信息
    PayInfo selectByOrderNo(@Param("orderNo") Long orderNo);
}
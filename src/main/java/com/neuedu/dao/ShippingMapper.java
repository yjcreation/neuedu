package com.neuedu.dao;

import com.neuedu.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_shipping
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(@Param("shippingId") Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_shipping
     *
     * @mbg.generated
     */
    int insert(Shipping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_shipping
     *
     * @mbg.generated
     */
    Shipping selectByPrimaryKey(@Param("shippingId") Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_shipping
     *
     * @mbg.generated
     */
    List<Shipping> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_shipping
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(@Param("shipping")Shipping record);
}
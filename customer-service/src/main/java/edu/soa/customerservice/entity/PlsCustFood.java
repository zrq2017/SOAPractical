package edu.soa.customerservice.entity;

import groovy.transform.ToString;
import lombok.Data;

import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 *  客户商品单实体
 * Created by Liuwu on 2018/5/19.
 */
@ToString
@Data
public class PlsCustFood {
    /** 主键ID*/
    private String id;
    /** 客户ID*/
    private Long customerId;
    /** 食品ID*/
    private Long foodId;
    /** 订购数量*/
    private BigDecimal number;

    /** 食品单价*/
    private BigDecimal unitPrice;

    /**食品名称*/
    @Transient
    private String foodName;
    @Transient
    private Integer unitId;
    /**总下单量*/
    @Transient
    private BigDecimal totalNumber;
}
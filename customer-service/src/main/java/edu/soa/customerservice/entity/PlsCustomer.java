package edu.soa.customerservice.entity;

import com.fs.dishes.base.common.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 客户实体
 * Created by liuwu on 2018/4/2 0002.
 */
@Data
@ToString
@Table(name = "pls_customer")
public class PlsCustomer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //主键ID

    private String name; //客户名称

    private String remarks;//客户备注

    private String address;//客户地址

    private String phone; //手机号码

    private Integer status; //状态 1 正常 2 伪删除

    private Integer level; //客户级别,按照消费金额计算（1-5）
}

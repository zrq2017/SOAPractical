package edu.soa.customerservice.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.customer.entity.PlsCustomer;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by liuwu on 2018/4/2 0002.
 */
@DataRepository
public interface PlsCustomerDao extends Mapper<PlsCustomer> {
    /**
     * 根据查询条件查询客户信息
     *
     * @param params
     * @return
     */
    List<PlsCustomer> queryList(Map<String, Object> params);

    /**
     * 根据条件判断是否已存在用户
     *
     * @param customer
     * @return
     */
    Boolean exists(PlsCustomer customer);

    /**
     * 批量伪删除
     * @param idList
     * @param status
     * @return
     */
    Boolean batchDel(@Param("idList") List<Long> idList, @Param("status") Integer status);
}

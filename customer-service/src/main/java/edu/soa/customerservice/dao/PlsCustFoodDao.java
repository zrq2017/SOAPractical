package edu.soa.customerservice.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.customer.entity.PlsCustFood;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 客户商品单持久层
 * Created by Liuwu on 2018/5/19.
 */
@DataRepository
public interface PlsCustFoodDao extends Mapper<PlsCustFood>{

    /**
     * 客户商品单列表查询
     * @param params 查询条件
     * @return
     */
    List<PlsCustFood> queryList(Map<String, Object> params);

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    Boolean batchAdd(@Param("list") List<PlsCustFood> list);

    /**
     * 删除操作
     *
     * @param idList
     * @return
     */
    Boolean batchDel(@Param("idList") List<String> idList);
}

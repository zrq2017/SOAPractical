package edu.soa.customerservice.service;

import com.fs.dishes.base.common.Constant;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.service.BaseService;
import com.fs.dishes.module.customer.dao.PlsCustomerDao;
import com.fs.dishes.module.customer.entity.PlsCustomer;
import com.fs.dishes.module.order.dao.PlsSubOrderDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户业务类
 * Created by liuwu on 2018/4/2 0002.
 */
@Service
public class PlsCustomerService extends BaseService {

    @Autowired
    private PlsCustomerDao plsCustomerDao;

    @Autowired
    private PlsSubOrderDao plsSubOrderDao;


    /**
     * 客户分页搜索
     *
     * @param params
     * @return
     */
    public ResResult pageCustomer(Map<String, Object> params) {
        Integer status = MapUtils.getInteger(params, "status", 1);
        params.put("status", status);
        PageHelper.startPage(getPageNo(params), getPageSize(params));
        List<PlsCustomer> list = plsCustomerDao.queryList(params);
        PageInfo<PlsCustomer> page = new PageInfo<>(list);
        logger.info("搜索条件：{}，搜索到的客户信息共{}条", params, page.getTotal());
        return ResResult.ok().withData(page);
    }

    /**
     * 客户列表
     *
     * @return
     */
    public ResResult listCustomer() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("status", Constant.DataState.NORMAL.getValue());
        List<PlsCustomer> list = plsCustomerDao.queryList(params);
        logger.info("客户信息共{}条", list.size());
        return ResResult.ok().withData(list);
    }

    /**
     * 根据客户ID获取客户详情
     *
     * @param customerId
     * @return
     */
    public ResResult getById(Long customerId) {
        PlsCustomer plsCustomer = plsCustomerDao.selectByPrimaryKey(customerId);
        return ResResult.ok().withData(plsCustomer);
    }

    /**
     * 删除该客户
     *
     * @param id 客户ID
     * @return
     */
    public ResResult delCustomer(Long id) {
        PlsCustomer customer = plsCustomerDao.selectByPrimaryKey(id);
        if (customer != null) {
            customer.setStatus(Constant.DataState.FAKE_DEL.getValue());
            plsCustomerDao.updateByPrimaryKey(customer);
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }


    /**
     * 批量删除该客户
     *
     * @param ids 客户ID数组
     * @return
     */
    public ResResult delCustomers(List<Long> ids) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("idList", ids);
        params.put("status", Constant.DataState.NORMAL.getValue());
        List<PlsCustomer> customerList = plsCustomerDao.queryList(params);
        Boolean flag = Boolean.TRUE;
        StringBuilder errorMsg = new StringBuilder();
        if (CollectionUtils.isNotEmpty(customerList)) {
            List<Long> customerIdList = plsSubOrderDao.queryCustomerByCondition(params);
            if (CollectionUtils.isNotEmpty(customerIdList)) {
                flag = Boolean.FALSE;
                errorMsg.append("客户名称 [");
                for (int i = 0; i < customerList.size(); i++) {
                    PlsCustomer customer = customerList.get(i);
                    if (customerIdList.contains(customer.getId())) {
                        errorMsg.append(customer.getName());
                        if (i < customerList.size() - 1){
                            errorMsg.append("，");
                        }
                    }
                }
                errorMsg.append("],已存在下单信息，请重新选择！");
                logger.info(errorMsg.toString());
            } else {
                flag = plsCustomerDao.batchDel(ids, Constant.DataState.FAKE_DEL.getValue());
                logger.info("客戶ids：{},共{}个,删除成功！", ids, ids.size());
            }
        }
        if (flag) {
            return ResResult.ok().withData(flag);
        } else {
            return ResResult.error(300, errorMsg.toString());
        }
    }

    /**
     * 新增或者修改客户信息
     *
     * @param plsCustomer
     * @return
     */
    public ResResult modifyCustomer(PlsCustomer plsCustomer) {
        Boolean isExists = existsCustomer(plsCustomer);
        if (isExists) {
            return ResResult.error(300, "客户名称已存在，请重新输入！");
        }
        plsCustomer.setStatus(Constant.DataState.NORMAL.getValue());
        if (plsCustomer.getId() != null) {
            plsCustomer.setModifyBy(getUserId());
            plsCustomer.setModifyTime(new Date());
            plsCustomerDao.updateByPrimaryKeySelective(plsCustomer);
        } else {
            //默认为1
            plsCustomer.setLevel(1);
            plsCustomer.setCreateBy(getUserId());
            plsCustomer.setCreateTime(new Date());
            plsCustomerDao.insert(plsCustomer);
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }


    /**
     * 是否已存在该客户
     *
     * @param plsCustomer
     * @return
     */
    public boolean existsCustomer(PlsCustomer plsCustomer) {
        return plsCustomerDao.exists(plsCustomer);
    }

}

package edu.soa.customerservice.service;

import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.service.BaseService;
import com.fs.dishes.module.customer.dao.PlsCustFoodDao;
import com.fs.dishes.module.customer.entity.PlsCustFood;
import com.fs.dishes.module.order.dao.PlsOrderFoodDao;
import com.fs.dishes.module.order.entity.OrderFoodVo;
import com.fs.dishes.module.res.dao.PlsFoodDao;
import com.fs.dishes.module.res.entity.PlsFood;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 客户单业务类
 * Created by Liuwu on 2018/5/19.
 */
@Service
public class PlsCustFoodService extends BaseService {

    @Autowired
    private PlsCustFoodDao plsCustFoodDao;

    @Autowired
    private PlsOrderFoodDao plsOrderFoodDao;

    @Autowired
    private PlsFoodDao plsFoodDao;

    /**
     * 客户单分頁集合
     *
     * @return
     */
    public ResResult pageCustFood(Map<String, Object> params) {
        PageHelper.startPage(getPageNo(params), getPageSize(params));
        List<PlsCustFood> list = plsCustFoodDao.queryList(params);
        PageInfo<PlsCustFood> page = new PageInfo<>(list);
        List<PlsCustFood> custFoodList = page.getList();
        setFoodName(custFoodList);
        return ResResult.ok().withData(page);
    }

    /**
     * 客户单集合
     *
     * @return
     */
    public ResResult listCustFood(Map<String, Object> params) {
        List<PlsCustFood> list = plsCustFoodDao.queryList(params);
        setFoodName(list);
        Long mainOrderId = MapUtils.getLong(params, "mainOrderId");
        setMainInfo(mainOrderId, list);
        return ResResult.ok().withData(list);
    }

    /**
     * 保存客户商品单数据
     *
     * @param custFoodList
     * @return
     */
    public ResResult modifyCustFood(List<PlsCustFood> custFoodList) {
        if (CollectionUtils.isNotEmpty(custFoodList)) {
            PlsCustFood custFood = custFoodList.stream().findFirst().get();
            Map<String, Object> params = Maps.newHashMap();
            params.put("customerId", custFood.getCustomerId());
            List<PlsCustFood> preList = plsCustFoodDao.queryList(params);
            List<String> preIdList = preList.stream().map(item -> item.getId()).collect(Collectors.toList());
            List<String> currIdList = custFoodList.stream().filter(item -> StringUtils.isNotBlank(item.getId())).
                    map(item -> item.getId()).collect(Collectors.toList());
            //需删除数据
            preIdList.removeAll(currIdList);
            if (CollectionUtils.isNotEmpty(preIdList)) {
                plsCustFoodDao.batchDel(preIdList);
                logger.info("客户商品单子单商品信息ids:{}，共{}个，刪除成功", preIdList, preIdList.size());
            }

            //需新增数据
            List<PlsCustFood> insertList = custFoodList.stream().filter(item ->
                    StringUtils.isBlank(item.getId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(insertList)) {
                plsCustFoodDao.batchAdd(insertList);
                logger.info("新增客户商品单{}商品信息共{}个，新增成功", custFood.getCustomerId(), insertList.size());
            }

            //需更新数据
            List<PlsCustFood> updateList = custFoodList.stream().filter(item ->
                    StringUtils.isNotBlank(item.getId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(updateList)) {
                for (PlsCustFood plsCustFood : updateList) {
                    plsCustFoodDao.updateByPrimaryKey(plsCustFood);
                }
                logger.info("更新客户商品单{}商品信息共{}个，更新成功", custFood.getCustomerId(), updateList.size());
            }
            return ResResult.ok().withData(Boolean.TRUE);
        }
        return ResResult.error(300, "客户商品单数据入库失败！");
    }


    /**
     * 设置食品名称
     *
     * @param custFoodList
     */
    private void setFoodName(List<PlsCustFood> custFoodList) {
        if (CollectionUtils.isNotEmpty(custFoodList)) {
            List<Long> foodIdList = custFoodList.stream().map(item -> item.getFoodId()).
                    distinct().collect(Collectors.toList());
            Map<String, Object> foodParams = Maps.newHashMap();
            foodParams.put("idList", foodIdList);
            List<PlsFood> foodList = plsFoodDao.queryList(foodParams);
            Map<Long, PlsFood> foodMap = foodList.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));
            custFoodList.forEach(item -> {
                PlsFood food = (PlsFood) MapUtils.getObject(foodMap, item.getFoodId());
                if (food != null) {
                    item.setFoodName(food.getName());
                    item.setUnitId(food.getUnitId());
                }
            });
        }
    }

    /**
     * 设置主单信息
     * @param mainOrderId
     * @param custFoodList
     */
    private void setMainInfo(Long mainOrderId, List<PlsCustFood> custFoodList) {
        if (CollectionUtils.isNotEmpty(custFoodList)) {
            List<Long> foodIdList = custFoodList.stream().map(item -> item.getFoodId()).
                    distinct().collect(Collectors.toList());

            Map<String, Object> params = Maps.newHashMap();
            params.put("mainOrderId", mainOrderId);
            params.put("foodIdList", foodIdList);
            List<OrderFoodVo> orderFoodVoList = plsOrderFoodDao.queryFoodOrderByCondition(params);

            Map<Long, OrderFoodVo> orderFoodVoMap = orderFoodVoList.stream().collect(Collectors.toMap(item -> item.getFoodId(),
                    item -> item));

            custFoodList.forEach(item -> {
                OrderFoodVo orderFoodVo = orderFoodVoMap.get(item.getFoodId());
                if (orderFoodVo != null) {
                    item.setTotalNumber(orderFoodVo.getNumber());
                }
            });
        }
    }
}

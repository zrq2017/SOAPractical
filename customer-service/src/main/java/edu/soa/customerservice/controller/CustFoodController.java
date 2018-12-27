package edu.soa.customerservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 客户商品单信息
 */
@Api(description = "客户商品单相关接口")
@RestController
@RequestMapping("/api/custfood")
public class CustFoodController extends AbstractController {

    @Autowired
    private PlsCustFoodService plsCustFoodService;

    /**
     * 客户商品单分页列表
     */
    @ApiOperation(value = "客户商品单分页列表接口", notes = "客户商品单分页列表接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页记录数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "customerId", value = "客户Id", dataType = "Long", paramType = "query"),
    })
    @RequestMapping(value = "/page", method = RequestMethod.POST)
//    @RequiresPermissions("custfood:page")
    public ResResult page(@RequestBody Map<String, Object> params) {
        return plsCustFoodService.pageCustFood(params);
    }

    /**
     * 客户商品单分页列表
     */
    @ApiOperation(value = "客户商品单列表接口", notes = "客户商品单列表接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户Id", dataType = "Long", paramType = "query"),
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
//    @RequiresPermissions("custfood:list")
    public ResResult list(@RequestBody Map<String, Object> params) {
        return plsCustFoodService.listCustFood(params);
    }


    /**
     * 保存客户商品单信息
     */
    @ApiOperation(value = "保存客户商品单信息", notes = "保存客户商品单信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "customerId", value = "客户Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "foodId", value = "食品ID", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "number", value = "数量", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "unitPrice", value = "单价", dataType = "double", paramType = "query")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @RequiresPermissions("custfood:save")
    public ResResult save(@RequestBody List<PlsCustFood> list) {
        return plsCustFoodService.modifyCustFood(list);
    }
}

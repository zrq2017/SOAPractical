package edu.soa.customerservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 客户管理
 */
@Api(description = "客户相关接口")
@RestController
@RequestMapping("/api/customer")
public class CustomerController extends AbstractController {

    @Autowired
    private PlsCustomerService plsCustomerService;

    /**
     * 所有客户列表
     */
    @ApiOperation(value = "客户列表接口", notes = "客户列表接口", httpMethod = "POST")
    @RequestMapping(value = "/listMenu", method = RequestMethod.POST)
//    @RequiresPermissions("customer:list")
    public ResResult list() {
        return plsCustomerService.listCustomer();
    }


    /**
     * 客户分页列表
     */
    @ApiOperation(value = "客户分页列表接口", notes = "客户列表接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页记录数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "客户名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "Integer", paramType = "query")
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
//    @RequiresPermissions("customer:list")
    public ResResult page(@RequestBody Map<String, Object> params) {
        return plsCustomerService.pageCustomer(params);
    }

    /**
     * 获取客户信息
     */
    @ApiOperation(value = "客户详情接口", notes = "客户详情接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户ID", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/info{customerId}", method = RequestMethod.POST)
    public ResResult info(@PathVariable("customerId") Long customerId) {
        return plsCustomerService.getById(customerId);
    }

    /**
     * 保存以及更新客户
     */
    @ApiOperation(value = "保存以及更新客户信息接口", notes = "保存以及更新客户信息接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "客户ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "客户名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "remarks", value = "客户详情", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "品种ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "客户图片地址", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "level", value = "客户单价", dataType = "String", paramType = "query")
    })
    @LogManage("保存以及更新客户信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @RequiresPermissions("customer:save")
    public ResResult save(@RequestBody PlsCustomer customer) {
        ValidatorUtils.validateEntity(customer, AddGroup.class);
        return plsCustomerService.modifyCustomer(customer);
    }


    /**
     * 删除客户信息
     */
    @ApiOperation(value = "删除客户信息", notes = "删除客户信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerIds", value = "客户ID集合", paramType = "query"),
    })
    @LogManage("删除客户信息")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
//    @RequiresPermissions("customer:delete")
    public ResResult delete(@RequestBody Map<String, Object> params) {
        List<Long> customerIdList = (List) params.get("customerIds");
        return plsCustomerService.delCustomers(customerIdList);
    }
}

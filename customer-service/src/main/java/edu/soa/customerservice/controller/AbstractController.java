package edu.soa.customerservice.controller;

import edu.soa.customerservice.entity.SysUser;
import edu.soa.customerservice.util.DateUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * Controller公共组件
 */
public abstract class AbstractController {

    @Autowired
    protected HttpServletRequest request;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SysUser getUser() {
        return new SysUser();
    }

    protected String getUserId() {
        return getUser().getUserId();
    }

    /**
     * 初始化数据绑定
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2. 将字段中Date类型转换为String类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 获取参数值
     * @param parameterName
     * @return
     */
    protected String getParameter(String parameterName) {
        return request.getParameter(parameterName) != null ? request.getParameter(parameterName) : StringUtils.EMPTY;
    }

    /**
     * 获取路径地址
     *
     * @return
     */
    protected String getRealPath(String folderName) {
        return this.getClass().getClassLoader().getResource(folderName).getPath();
    }
}

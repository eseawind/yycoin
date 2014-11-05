package com.china.center.oa.product.constant;

import com.china.center.jdbc.annotation.Defined;

public interface ProductApplyConstant {

    /*产品申请状态*/
    @Defined(key = "productApplyStatus", value = "保存")
    int STATUS_SAVE = 0;
    
    @Defined(key = "productApplyStatus", value = "提交")
    int STATUS_SUBMIT = 1;
    
    @Defined(key = "productApplyStatus", value = "驳回")
    int STATUS_REJECT = 2;
    
    @Defined(key = "productApplyStatus", value = "待部门审批")
    int STATUS_DEPARTMENTAPPLY = 3;
    
    @Defined(key = "productApplyStatus", value = "待产品管理中心审批")
    int STATUS_PRODUCTAPPLY = 4;
    
    @Defined(key = "productApplyStatus", value = "结束")
    int STATUS_FINISHED = 99;
    
    @Defined(key="productOPRMode", value="保存")
    int OPRMODE_SAVE = 0 ;
    
    @Defined(key="productOPRMode", value="提交")
    int OPRMODE_SUBMIT = 1 ;
    
    @Defined(key="productOPRMode", value="通过")
    int OPRMODE_PASS = 2 ;
    
    @Defined(key="productOPRMode", value="驳回")
    int OPRMODE_REJECT = 3 ;
    
    /**
     * 项目经理
     */
    @Defined(key = "stafferRole", value = "项目经理")
    int STAFFERROLE_PM = 0;
    
    /**
     * 创意
     */
    @Defined(key = "stafferRole", value = "创意")
    int STAFFERROLE_IDEA = 1;
    
    /**
     * 设计
     */
    @Defined(key = "stafferRole", value = "设计")
    int STAFFERROLE_DESIGN = 2;
    
    /**
     * 文案
     */
    @Defined(key = "stafferRole", value = "文案")
    int STAFFERROLE_DOC = 3;
    
    /**
     * 质检
     */
    @Defined(key = "stafferRole", value = "质检")
    int STAFFERROLE_QQUALITY = 4;
    
    /**
     * 销售人员
     */
    @Defined(key = "stafferRole", value = "销售人员")
    int STAFFERROLE_SALER = 5;
    
    /**
     * 管理人员
     */
    @Defined(key = "stafferRole", value = "管理人员")
    int STAFFERROLE_MANAGER = 6;
    
    /**
     * 合成产品
     */
    @Defined(key = "natureType", value = "成品")
    int NATURE_COMPOSE = 1;
    
    /**
     * 单元产品
     */
    @Defined(key = "natureType",value = "配件产品")
    int NATURE_SINGLE = 0;
    
}

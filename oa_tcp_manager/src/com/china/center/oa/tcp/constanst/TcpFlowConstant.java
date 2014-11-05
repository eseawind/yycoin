/**
 * File Name: TcpFlowConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.constanst;

/**
 * TcpFlowConstant
 * 
 * @author ZHUZHU
 * @version 2011-7-17
 * @see TcpFlowConstant
 * @since 3.0
 */
public interface TcpFlowConstant
{
    /**
     * 5000以内的报销
     */
    String TRAVELAPPLY_0_5000 = "travel-0-5000";

    /**
     * 5000-10000的报销
     */
    String TRAVELAPPLY_5000_10000 = "travel-5000-10000";

    /**
     * 10000-50000的报销
     */
    String TRAVELAPPLY_10000_50000 = "travel-10000-50000";

    /**
     * 50000+的报销
     */
    String TRAVELAPPLY_50000_MAX = "travel-50000+";
    
    /**
     * 中收报销
     */
    String TRAVELAPPLY_MID = "travel-mid";

    /**
     * 5000以内的报销
     */
    String STOCK_APPLY_0_5000 = "stock-0-5000";

    /**
     * 5000-10000的报销
     */
    String STOCK_APPLY_5000_10000 = "stock-5000-10000";

    /**
     * 10000-50000的报销
     */
    String STOCK_APPLY_10000_50000 = "stock-10000-50000";

    /**
     * 50000+的报销
     */
    String STOCK_APPLY_50000_MAX = "stock-50000+";

    /**
     * 职能系50000+
     */
    String WORK_APPLY_50000_MAX = "work-50000+";

    /**
     * 职能系50000-
     */
    String WORK_APPLY_0_50000 = "work-50000-";

    /**
     * 职能系50000+(采购)
     */
    String WORK_STOCK_APPLY_50000_MAX = "work-stock-50000+";

    /**
     * 职能系50000-(采购)
     */
    String WORK_STOCK_APPLY_0_50000 = "work-stock-50000-";

    /**
     * 管理系50000+
     */
    String MANAGER_APPLY_50000_MAX = "manager-50000+";

    /**
     * 管理系50000-
     */
    String MANAGER_APPLY_0_50000 = "manager-50000-";

    /**
     * 管理系50000+(采购)
     */
    String MANAGER_STOCK_APPLY_50000_MAX = "manager-stock-50000+";

    /**
     * 管理系50000-(采购)
     */
    String MANAGER_STOCK_APPLY_0_50000 = "manager-stock-50000-";

    /**
     * 返利申请流
     */
    String REBATE_APPLY_0_50000 = "rebate-apply-50000";
    
    /***
     * 预开票
     */
    String PREINVOICE_APPLY = "preinvoice-apply"; 
    
    /**
     * 加班、请假审批流
     */
    String EXTRA_WORK_AND_LEAVE = "extra_work_leave";
    
    /**
     * 加班、请假审批流-副总裁审批
     */
    String EXTRA_WORK_AND_LEAVE_CEO = "extra_work_leave_ceo";
    
    /**
     * 预收退款
     */
    String BACKPREPAY_APPLY = "backPrePay";
    
    /**
     * 部门经理
     */
    String GROUP_DM = "A220110406000200001";

    /**
     * 大区经理
     */
    String GROUP_AM = "A220110406000200002";

    /**
     * 事业部经理
     */
    String GROUP_SM = "A220110406000200003";

    /**
     * 财务总监
     */
    String GROUP_CFO = "A220110406000200004";

    /**
     * 总裁
     */
    String GROUP_CEO = "A220110406000200005";

    /**
     * 董事长
     */
    String GROUP_TOP = "A220110406000200006";

    /**
     * 稽核
     */
    String GROUP_CHECK = "A220110406000200007";

    /**
     * 财务支付
     */
    String GROUP_PAY = "A220110406000200008";

    /**
     * 财务入账
     */
    String GROUP_LASTCHECK = "A220110406000200009";

    /**
     * 中心总监
     */
    String GROUP_CENTER_CHECK = "A220110406000200011";

    /**
     * 锁
     */
    String DRAW_LOCK = "TCP_DRAW_LOCK";
}

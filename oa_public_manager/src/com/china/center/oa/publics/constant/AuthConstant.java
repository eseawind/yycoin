/**
 * File Name: AuthConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.constant;

/**
 * AuthConstant
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see AuthConstant
 * @since 1.0
 */
public interface AuthConstant
{
    /**
     * 扩展权限-仓库
     */
    String EXPAND_AUTH_DEPOT = "80";

    /**
     * 默认都有的
     */
    String PUNLIC_AUTH = "0000";

    /**
     * 操作区域
     */
    String LOCATION_OPR = "010101";

    /**
     * 操作人员
     */
    String STAFFER_OPR = "010201";

    /**
     * 操作角色(暂时和操作用户一样的权限)
     */
    String ROLE_OPR = "010401";

    /**
     * 操作用户
     */
    String USER_OPR = "010401";

    /**
     * 组织结构管理
     */
    String ORG_MANAGER = "0105";

    /**
     * 部门管理
     */
    String DEPARTMENT_MANAGER = "0106";

    /**
     * 职务管理
     */
    String POST_MANAGER = "0107";

    /**
     * 申请增删改客户
     */
    String CUSTOMER_OPR = "0202";

    /**
     * 客户分配审批
     */
    String CUSTOMER_CHECK = "0203";

    /**
     * 查看非本人的客户联系方式
     */
    String CUSTOMER_OQUERY = "0204";

    /**
     * 申请分配客户
     */
    String CUSTOMER_APPLY_ASSIGN = "0205";

    /**
     * 查询历史
     */
    String CUSTOMER_QUERY_HIS = "0206";

    /**
     * 查询分公司所有客户
     */
    String CUSTOMER_QUERY_LOCATION = "0207";

    /**
     * 导入客户
     */
    String CUSTOMER_UPLOAD = "0208";

    /**
     * 客户分布
     */
    String CUSTOMER_DISTRIBUTE = "0209";

    /**
     * 客户分公司全量同步
     */
    String CUSTOMER_SYNCHRONIZATION = "0210";

    /**
     * 客户回收
     */
    String CUSTOMER_RECLAIM = "0211";

    /**
     * 查询供应商
     */
    String CUSTOMER_QUERY_PROVIDER = "0212";

    /**
     * 操作供应商
     */
    String CUSTOMER_OPR_PROVIDER = "0213";

    /**
     * 客户编码分配
     */
    String CUSTOMER_ASSIGN_CODE = "0214";

    /**
     * 客户/供应商 修改核对
     */
    String CUSTOMER_HIS_CHECK = "0215";

    /**
     * 客户审计
     */
    String CUSTOMER_CHECK_PARENT = "0216";

    /**
     * 客户审计
     */
    String CUSTOMER_CHECK_COMMON = "021601";

    /**
     * 客户审计审批
     */
    String CUSTOMER_CHECK_CHECK = "021602";

    /**
     * 所有审计结果查看
     */
    String CUSTOMER_CHECK_ALLQUERY = "021603";

    /**
     * 客户信用审核
     */
    String CUSTOMER_CREDIT_CHECK = "0217";

    /**
     * 利润分配申请
     */
    String CUSTOMER_ASSIGNPER_APPLY = "021801";

    /**
     * 利润分配审核
     */
    String CUSTOMER_ASSIGNPER_APPROVE = "021802";

    /**
     * 客户信息审批
     */
    String CUSTOMER_INFO_CHECK = "0220";

    /**
     * 查询地市考核配置
     */
    String CITYCONFIG_QUERY = "0301";

    /**
     * 操作地市考核配置
     */
    String CITYCONFIG_OPR = "0302";

    /**
     * 查询考核
     */
    String EXAMINE_QUERY = "0303";

    /**
     * 操作考核
     */
    String EXAMINE_OPR = "0304";

    /**
     * 考核变更确认
     */
    String EXAMINE_UPDATE_COMMIT = "0305";

    /**
     * 客户利润查询
     */
    String EXAMINE_PROFIT_QUERY = "0306";

    /**
     * 客户利润操作
     */
    String EXAMINE_PROFIT_OPR = "0307";

    /**
     * 业务员的日志
     */
    String WORKLOG_OPR = "0401";

    /**
     * 日志分析
     */
    String WORKLOG_ANY = "0402";

    /**
     * 日志客户全局查询
     */
    String WORKLOG_GBOAL_QUERY = "0403";

    /**
     * 预算查询
     */
    String BUDGET_QUERY = "0501";

    /**
     * 事业部预算维护
     */
    String BUDGET_LOCATION_OPR = "0502";

    /**
     * 事业部预算审批
     */
    String BUDGET_LOCATION_CHECK = "0503";

    /**
     * 根预算的增加
     */
    String BUDGET_ROOT_OPR = "0504";

    /**
     * 根预算的审核(包括预算)
     */
    String BUDGET_ROOT_CHECK = "0505";

    /**
     * 预算变更
     */
    String BUDGET_CHANGE = "0506";

    /**
     * 预算变更--申请变更
     */
    String BUDGET_CHANGE_APPLY = "050601";

    /**
     * 审批部门预算
     */
    String BUDGET_DEPARTMENT_CHECK = "0508";

    /**
     * 部门预算维护
     */
    String BUDGET_DEPARTMENT_OPR = "0509";

    /**
     * 强制删除
     */
    String BUDGET_FORCE_DELETE = "0510";

    /**
     * 私有群组操作
     */
    String GROUP_OPR = "0601";

    /**
     * 公共群组操作
     */
    String GROUP_PUBLIC_OPR = "0602";

    /**
     * 系统群组操作
     */
    String GROUP_SYSTEM_OPR = "0603";

    /**
     * 流程模板操作
     */
    String FLOW_TEMPLATE_OPR = "0701";

    /**
     * 流程定义操作
     */
    String FLOW_DEFINED_OPR = "0702";

    /**
     * 强制废弃
     */
    String FLOW_DEFINED_FORCE_DROP = "0703";

    /**
     * 信用等级操作
     */
    String CREDIT_OPR = "0901";

    /**
     * 信用等级操作
     */
    String CREDIT_LEVEL_OPR = "0902";

    /**
     * 强制修改
     */
    String CREDIT_FORCE_UPDATE = "0903";

    /**
     * 导入
     */
    String CREDIT_IMPOTR = "0904";

    /**
     * 信用杠杆维护(总裁)
     */
    String CUSTOMER_LEVER_OPR = "0905";

    /**
     * 产品订货
     */
    String PRODUCT_ORDER = "1001";

    /**
     * 查询产品
     */
    String PRODUCT_QUERY = "1002";

    /**
     * 操作产品
     */
    String PRODUCT_OPR = "1003";

    /**
     * 审核产品
     */
    String PRODUCT_CHECK = "1004";

    /**
     * 申请产品合成/分解
     */
    String PRODUCT_CD = "100501";

    /**
     * 生产部经理审批
     */
    String PRODUCT_CD_MANGAER = "100502";

    /**
     * 运营总监审批
     */
    String PRODUCT_CD_CRO = "100503";

    /**
     * 产品调价
     */
    String PRODUCT_CHANGE_PRICE = "1006";

    /**
     * 税务实体的操作
     */
    String DUTY_OPR = "010801";

    /**
     * 配置的操作
     */
    String ENUM_OPR = "0109";

    /**
     * 发票的操作
     */
    String INVOICE_OPR = "0110";

    /**
     * 品名管理
     */
    String SHOW_OPR = "0111";

    /**
     * 查询仓库
     */
    String DEPOT_QUERY = "1101";

    /**
     * 操作仓库
     */
    String DEPOT_OPR = "1102";

    /**
     * 查询仓区
     */
    String DEPOTPART_QUERY = "1103";

    /**
     * 操作仓区
     */
    String DEPOTPART_OPR = "1104";

    /**
     * 查询储位
     */
    String STORAGE_QUERY = "1105";

    /**
     * 操作储位
     */
    String STORAGE_OPR = "1106";

    /**
     * 转移公共库存
     */
    String STORAGE_TRANS = "1108";

    /**
     * 增加询价(业务员)
     */
    String PRICE_ASK_ADD = "1201";

    /**
     * 处理询价(询价员)-PRICE
     */
    String PRICE_ASK_PROCESS = "1202";

    /**
     * 内网询价管理(采购主管)-STOCK
     */
    String PRICE_ASK_MANAGER = "1203";

    /**
     * 外网询价管理(外网采购主管)-NETSTOCK
     */
    String PRICE_ASK_NET_MANAGER = "1204";

    /**
     * 外网询价处理(外网供应商询价员)-NETASK
     */
    String PRICE_ASK_NET_PROCESS = "1205";

    /**
     * 外网询价处理(外网内部询价员)-NETCOMMON
     */
    String PRICE_ASK_NET_INNER_PROCESS = "1206";

    /**
     * 生产采购
     */
    String PRICE_ASK_MAKE = "1207";

    /**
     * 增加采购(业务员)
     */
    String STOCK_ADD = "1301";

    /**
     * 事业部经理
     */
    String STOCK_MANAGER_PASS = "1302";

    /**
     * DROP 内网采购主管审批(内网采购主管)
     */
    String STOCK_INNER_STOCK_PASS = "1303";

    /**
     * 销售采购主管审批
     */
    String STOCK_NET_STOCK_PASS = "1304";

    /**
     * DROP 异常采购最终确认(采购经理)
     */
    String STOCK_STOCK_MANAGER_PASS = "1305";

    /**
     * DROP 内网采购询价(询价员)
     */
    String STOCK_PRICE_PASS = "1306";

    /**
     * 董事长通知
     */
    String STOCK_NOTICE_CHAIRMA = "1307";

    /**
     * 总经理
     */
    String STOCK_NOTICE_CEO = "1308";

    /**
     * 结束采购(采购主管)
     */
    String STOCK_PRICE_END = "1309";

    /**
     * 采购付款-申请
     */
    String STOCK_PAY_APPLY = "1310";

    /**
     * 采购付款-总裁审批
     */
    String STOCK_PAY_CEO = "1311";

    /**
     * 采购付款-财务审批
     */
    String STOCK_PAY_SEC = "1312";

    /**
     * 增加生产采购
     */
    String STOCK_MAKE_ADD = "1313";

    /**
     * 生产采购主管审批
     */
    String STOCK_MAKE_PASS = "1314";

    /**
     * 采购付款-营运中心审批
     */
    String STOCK_PAY_SAIL = "1315";

    /**
     * 采购付款-稽核审批
     */
    String STOCK_PAY_CHECK = "1316";

    /**
     * 采购付款-财务总监审批
     */
    String STOCK_PAY_CFO = "1317";

    /**
     * 提交库单(业务员)
     */
    String SAIL_SUBMIT = "1401";

    /**
     * 结算中心审批
     */
    String SAIL_MONEY_CENTER = "1402";

    /**
     * 物流审批
     */
    String SAIL_FLOW = "1403";

    /**
     * 库管发货
     */
    String SAIL_ADMIN = "1404";

    /**
     * 财务收款
     */
    String SAIL_SEC = "1405";

    /**
     * 总裁审批
     */
    String SAIL_CEO = "1406";

    /**
     * 调拨处理
     */
    String SAIL_INVOKE = "1407";

    /**
     * 事业部(信用分担)
     */
    String SAIL_LOCATION_MANAGER = "1408";

    /**
     * 全部库单查询
     */
    String SAIL_QUERY_ALL = "1409";

    /**
     * 总部核对
     */
    String SAIL_CENTER_CHECK = "1410";

    /**
     * 发货管理
     */
    String SAIL_CONSIGN_MANAGER = "1411";

    /**
     * 运输管理
     */
    String SAIL_TRANSPORT_MANAGER = "1412";

    /**
     * 查询部下的申请单(5以下的)
     */
    String SAIL_QUERY_SUB = "1413";

    /**
     * 申请退款-结算中心审核
     */
    String SAIL_BACKPAY_CENTER = "1414";

    /**
     * 申请退款-出纳审核
     */
    String SAIL_BACKPAY_SEC = "1415";

    /**
     * 查询事业部的销售单
     */
    String SAIL_QUERY_INDUSTY = "1416";

    /**
     * 导出销售单
     */
    String SAIL_QUERY_EXPORT = "1417";

    /**
     * 导出销售单
     */
    String SAIL_UPDATE_INVOICE = "1418";

    /**
     * 查询真正成本
     */
    String SAIL_QUERY_COST = "1419";

    /**
     * 销售单移交审核
     */
    String SAIL_TRAN_CHECK = "1420";

    /**
     * 提交入库单
     */
    String BUY_SUBMIT = "1501";

    /**
     * 入库单-分公司经理审批
     */
    String BUY_LOCATION_MANAGER = "1502";

    /**
     * 入库单-总裁审核
     */
    String BUY_CEO = "1503";

    /**
     * 入库单-董事长审核
     */
    String BUY_CHAIRMA = "1504";

    /**
     * 入库单-所有入库单查看
     */
    String BUY_QUERYALL = "1505";

    /**
     * 入库单-导出入库单
     */
    String BUY_EXPORT = "1506";

    /**
     * 入库单-财务审核
     */
    String BUY_SEC = "1507";

    /**
     * 帐户操作
     */
    String BANK_OPR = "1601";

    /**
     * 回款操作
     */
    String PAYMENT_OPR = "1602";

    /**
     * 收款操作
     */
    String INBILL_OPR = "1603";

    /**
     * 开发票操作(发票审核)
     */
    String INVOICEINS_OPR = "1604";

    /**
     * 删除开票
     */
    String INVOICEINS_DEL = "1605";

    /**
     * 收款申请审核
     */
    String INBILL_APPROVE = "1606";

    /**
     * 付款操作
     */
    String OUTBILL_OPR = "1607";

    /**
     * 总部会计(总部核对,所有查询)
     */
    String BILL_QUERY_ALL = "1608";

    /**
     * 收款申请稽核
     */
    String INBILL_CHECK = "1609";

    /**
     * 开票稽核
     */
    String INVOICEINS_CHECK = "1610";

    /**
     * 科目操作
     */
    String TAX_OPR = "1801";

    /**
     * 凭证操作
     */
    String FINANCE_OPR = "1802";

    /**
     * 总部核对
     */
    String FINANCE_CHECK = "1803";

    /**
     * 凭证删除
     */
    String FINANCE_DELETE = "1804";

    /**
     * 月度结转
     */
    String FINANCE_TURN = "1805";

    /**
     * 提成操作
     */
    String COMMISSION_OPR = "1806";
    
    /**
     * 非商务模式下，业务员可以操作的权限
     */
    String DIRECT_SALE = "010403";
}

/**
 * File Name: PublicConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * PublicConstant
 * 
 * @author ZHUZHU
 * @version 2008-11-2
 * @see
 * @since
 */
public interface PublicConstant
{
    /**
     * 可见
     */
    int ROLE_VISIBLE_YES = 1;

    /**
     * 不可见
     */
    int ROLE_VISIBLE_NO = 0;

    /**
     * 根权限的级别
     */
    int ROLE_LEVEL_ROOT = 0;

    /**
     * 区域权限
     */
    int AUTH_TYPE_LOCATION = 0;

    /**
     * 系统权限
     */
    int AUTH_TYPE_SYSTEM = 1;

    /**
     * 男
     */
    int SEX_MAN = 0;

    /**
     * 女
     */
    int SEX_WOMAN = 0;

    /**
     * 锁定
     */
    @Defined(key = "userStatus", value = "锁定")
    int LOGIN_STATUS_LOCK = 1;

    /**
     * 状态正常
     */
    @Defined(key = "userStatus", value = "正常")
    int LOGIN_STATUS_COMMON = 0;

    /**
     * 最大登录失败次数
     */
    int LOGIN_FAIL_MAX = 5;

    /**
     * 根节点
     */
    int BOTTOMFLAG_NO = 0;

    /**
     * 叶节点
     */
    int BOTTOMFLAG_YES = 1;

    /**
     * 虚拟区域
     */
    String VIRTUAL_LOCATION = "-1";

    /**
     * 总部区域(OSGi改造后就是就是99)
     */
    String CENTER_LOCATION = "99";

    /**
     * 超级管理员
     */
    String SUPER_MANAGER = "0";

    /**
     * 密码最小长度
     */
    int PASSWORD_MIN_LENGTH = 10;

    /**
     * 组织结构的顶层
     */
    int ORG_ROOT = 0;

    /**
     * 组织结构的第一层
     */
    int ORG_FIRST = 1;

    /**
     * 操作--提交
     */
    String OPERATION_SUBMIT = "提交";

    /**
     * 操作--通过
     */
    String OPERATION_PASS = "通过";

    /**
     * 操作--驳回
     */
    String OPERATION_REJECT = "驳回";

    /**
     * 操作--异常结束
     */
    String OPERATION_EXCEPTIONEND = "异常结束";

    /**
     * 变更
     */
    String OPERATION_CHANGE = "变更";

    /**
     * 变更
     */
    String OPERATION_DEL = "删除";

    /**
     * OSGi的临时文件存放处
     */
    String OSGI_TEMP_PATH = "osgi.tmp.path";

    /**
     * OSGi下工程的模板文件存放处
     */
    String OSGI_TEMPLATE_PATH = "osgi.template.path";

    String DEFAULT_ENCODING = "UTF-8";

    int PAGE_SIZE = 20;

    int PAGE_COMMON_SIZE = 10;

    int PAGE_EXPORT = 500;

    /**
     * 全局session区域表示
     */
    String CURRENTLOCATIONID = "currentLocationId";

    @Defined(key = "enumStatus", value = "系统初始")
    int ENUM_INIT = 0;

    @Defined(key = "enumStatus", value = "人工添加")
    int ENUM_ADD = 1;

    int ENUMDEFINE_TYPE_STRING = 0;

    int ENUMDEFINE_TYPE_INT = 1;

    /**
     * 通过操作
     */
    @Defined(key = "oprMode", value = "通过")
    int OPRMODE_PASS = 0;

    /**
     * 驳回
     */
    @Defined(key = "oprMode", value = "驳回")
    int OPRMODE_REJECT = 1;

    /**
     * 提交
     */
    @Defined(key = "oprMode", value = "提交")
    int OPRMODE_SUBMIT = 2;

    /**
     * 异常处理
     */
    @Defined(key = "oprMode", value = "异常处理")
    int OPRMODE_EXCEPTION = 3;

    /**
     * 保存
     */
    @Defined(key = "oprMode", value = "保存")
    int OPRMODE_SAVE = 4;

    /**
     * 驳回到上一步
     */
    @Defined(key = "oprMode", value = "驳回到上一步")
    int OPRMODE_REJECT_PRE = 5;
    
    /**
     * 接受任务
     */
    @Defined(key = "oprMode", value = "接受任务")
    int OPRMODE_RECEIVE = 6;
    
    /**
     * 责任人申请任务停止
     */
    @Defined(key = "oprMode", value = "责任人申请任务停止")
    int OPRMODE_APPLY_STOP = 7;
    
    /**
     * 同意申请任务停止
     */
    @Defined(key = "oprMode", value = "同意申请任务停止")
    int OPRMODE_AGREE_APPLY_STOP = 8;
    
    /**
     * 不同意申请任务停止
     */
    @Defined(key = "oprMode", value = "不同意申请任务停止")
    int OPRMODE_NOT_AGREE_APPLY_STOP = 9;
    
    /**
     * 责任人申请任务完成
     */
    @Defined(key = "oprMode", value = "责任人申请任务完成")
    int OPRMODE_APPLY_FINISH = 10;
    
    /**
     * 同意申请任务完成
     */
    @Defined(key = "oprMode", value = "同意申请任务完成")
    int OPRMODE_AGREE_APPLY_FINISH = 11;
    
    /**
     * 不同意申请任务完成
     */
    @Defined(key = "oprMode", value = "不同意申请任务完成")
    int OPRMODE_NOT_AGREE_APPLY_FINISH = 12;



    /**
     * 纳税-1
     */
    String DEFAULR_DUTY_ID = "90201008080000000001";

    /**
     * 纳税-2
     */
    String MANAGER_DUTY_ID = "A1201112260004531364";

    /**
     * 纳税-3 玉林
     */
    String MANAGER2_DUTY_ID = "A1201204010005525066";
    
    /**
     * 未核对
     */
    @Defined(key = "pubCheckStatus", value = "未核对")
    int CHECK_STATUS_INIT = 0;

    /**
     * 已核对
     */
    @Defined(key = "pubCheckStatus", value = "已核对")
    int CHECK_STATUS_END = 1;

    /**
     * 事业部经理
     */
    String POST_SHI_MANAGER = "16";

    /**
     * 管理
     */
    @Defined(key = "pubManagerType", value = "管理")
    int MANAGER_TYPE_MANAGER = 0;

    /**
     * 普通
     */
    @Defined(key = "pubManagerType", value = "普通")
    int MANAGER_TYPE_COMMON = 1;
    

    /**
     * 普通单据
     */
    @Defined(key = "pubVtype", value = "普通单据")
    int VTYPE_DEFAULT = 0;

    /**
     * 关注单据
     */
    @Defined(key = "pubVtype", value = "关注单据")
    int VTYPE_SPECIAL = 1;
    
    /**
     * 组织架构是否在用
     */
    @Defined(key = "orgStatus", value = "在用")
    int ORG_STATUS_USED = 0;
    
    @Defined(key = "orgStatus", value="停用")
    int ORG_STATUS_STOP = 1;
    
    /**
     * 职员
     */    
    String POST_WORKER = "4";
    
    /**
     * 部门经理
     */
    String POST_DEPART_MANAGER = "17";
    
    /**
     * 大区经理
     */
    String POST_AREA_MANAGER = "18";
    
    /**
     * 费用挂靠人
     */
    String POST_FEE_DEPENDER = "19";
    
    /**
     * 公司负责人
     */
    String POST_COMPANY_MANAGER = "20";
    
    /**
     * 虚拟部门
     * 
     */
    String VIRTUAL_DEPARTMENT [] = new String[]{"2043333","2043337","2043334","2043335","2043338","2043336"};
    
}

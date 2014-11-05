package com.china.center.oa.project.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * @author ZHUZHU
 */
public interface ProjectConstant
{

    /**
     * 合同状态
     */
    @Defined(key = "agreementStatus", value = "初始")
    int AGREEMENT_INIT = 0;

    @Defined(key = "agreementStatus", value = "待审批")
    int AGREEMENT_APPROVE = 1;

    @Defined(key = "agreementStatus", value = "结束")
    int AGREEMENT_OVER = 99;
    
    /**
     * 任务状态
     */
    @Defined(key = "taskStatus", value = "保存")
    int TASK_INIT = 0;

/*    @Defined(key = "taskStatus", value = "确认中")
    int TASK_CONFIRM = 1;*/

    @Defined(key = "taskStatus", value = "进行中")
    int TASK_START = 2;
    
    @Defined(key = "taskStatus", value = "停止")
    int TASK_CEASE = 3;
    
    @Defined(key = "taskStatus", value = "完成")
    int TASK_FINISH = 4;
    
    /**
     * 合同阶段
     */
    @Defined(key = "agreementStage", value = "合同阶段1")
    String AGREEMENT_STAGE_ONE = "0";

    @Defined(key = "agreementStage", value = "合同阶段2")
    String AGREEMENT_STAGE_TWO = "1";

    @Defined(key = "agreementStage", value = "合同阶段3")
    String AGREEMENT_STAGE_THREE = "2";
    
    /**
     * 项目阶段
     */
    @Defined(key = "projectStage", value = "项目阶段1")
    String PROJECT_STAGE_ONE = "0";

    @Defined(key = "projectStage", value = "项目阶段2")
    String PROJECT_STAGE_TWO = "1";

    @Defined(key = "projectStage", value = "项目阶段3")
    String PROJECT_STAGE_THREE = "2";
    
    /**
     * 任务阶段
     */
    @Defined(key = "taskStage", value = "任务阶段1")
    String TASK_STAGE_ONE = "0";

    @Defined(key = "taskStage", value = "任务阶段2")
    String TASK_STAGE_TWO = "1";

    @Defined(key = "taskStage", value = "任务阶段3")
    String TASK_STAGE_THREE = "2";
    
    /**
     * 申请类型
     */
    @Defined(key = "projectApplyType", value = "项目管理申请")
    int PROJECT_APPLY = 0;

    @Defined(key = "projectApplyType", value = "合同管理申请")
    int AGREEMENT_APPLY = 1;

    @Defined(key = "projectApplyType", value = "任务管理申请")
    int TASK_APPLY = 2;
    
    
    /**
     * 初始
     */
    @Defined(key = "projectStatus", value = "初始")
    int PROJECT_STATUS_INIT = 0;

    /**
     * 确认中
     */
    @Defined(key = "projectStatus", value = "确认中")
    int PROJECT_STATUS_CONFIRM = 1;

    /**
     * 进行中
     */
    @Defined(key = "projectStatus", value = "进行中")
    int PROJECT_STATUS_RECEIVE = 2;


    /**
     * 同意停止任务
     */
    @Defined(key = "projectStatus", value = "同意停止任务")
    int PROJECT_STATUS_AGREE = 3;

    /**
     * 不同意停止任务
     */
    @Defined(key = "projectStatus", value = "不同意停止任务")
    int PROJECT_STATUS_NO_AGREE = 5;

    /**
     * 同意完成任务
     */
    @Defined(key = "projectStatus", value = "同意完成任务")
    int PROJECT_STATUS_AGREE_FINISH = 4;
    
    /**
     * 不同意完成任务
     */
    @Defined(key = "projectStatus", value = "不同意完成任务")
    int PROJECT_STATUS_NO_FINISH = 6;
    
    /**
     * 申请任务停止
     */
    @Defined(key = "projectStatus", value = "申请任务停止")
    int PROJECT_STATUS_APPLY_STOP = 7;
    
    /**
     * 申请任务完成
     */
    @Defined(key = "projectStatus", value = "申请任务完成")
    int PROJECT_STATUS_APPLY_FINISH = 8;

    
    /**
     * 项目申请处理的URL
     */
    String PROJECT_PROJECT_PROCESS_URL = "../project/project.do?method=findProject&update=2&id=";
    
    /**
     * 合同申请处理的URL
     */
    String PROJECT_AGREEMENT_PROCESS_URL = "../project/project.do?method=findAgreement&update=1&id=";

    /**
     * 任务处理的URL
     */
    String PROJECT_TASK_PROCESS_URL = "../project/project.do?method=findTask&update=2&id=";
 
    /**
     * 任务紧急类型 - 普通
     */
    @Defined(key = "taskEmergencyType", value = "普通")
    int TASK_EMERGENCY_COMMON = 0;
    
    /**
     * 任务紧急类型 - 紧急
     */
    @Defined(key = "taskEmergencyType", value = "紧急")
    int TASK_EMERGENCY_VERY = 1;
    
    /**
     * 任务紧急类型 - 特急
     */
    @Defined(key = "taskEmergencyType", value = "特急")
    int TASK_EMERGENCY_VERYVERY = 2;
}

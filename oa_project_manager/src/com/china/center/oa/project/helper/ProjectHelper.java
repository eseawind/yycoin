package com.china.center.oa.project.helper;

/**
 * File Name: TCPHlper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-17<br>
 * Grant: open source to everybody
 */

import com.china.center.common.taglib.DefinedCommon;
import com.china.center.oa.project.constant.ProjectConstant;
import com.china.center.oa.project.vo.ProjectApproveVO;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.oa.tcp.helper.TCPHelper;
import com.china.center.tools.BeanUtil;


/**
 * TCPHlper
 * 
 * @author ZHUZHU
 * @version 2011-7-17
 * @see TCPHelper
 * @since 3.0
 */
public abstract class ProjectHelper
{
	/**
     * getTcpApproveVO
     * 
     * @param vo
     */
    public static void getProjectApproveVO(ProjectApproveVO vo)
    {
    	
        if (vo.getType() == 2)
        {
            vo.setUrl(ProjectConstant.PROJECT_TASK_PROCESS_URL + vo.getApplyId()+"&appid="+vo.getId());
        }
        else if(vo.getType() == 1)
        {
            vo.setUrl(ProjectConstant.PROJECT_AGREEMENT_PROCESS_URL + vo.getApplyId());
        }
        else
        {
        	vo.setUrl(ProjectConstant.PROJECT_PROJECT_PROCESS_URL + vo.getApplyId());
        }
    }

    public static FlowLogVO getProjectFlowLogVO(FlowLogBean bean)
    {
        FlowLogVO vo = new FlowLogVO();

        if (bean == null)
        {
            return vo;
        }

        BeanUtil.copyProperties(vo, bean);

        vo.setOprModeName(DefinedCommon.getValue("oprMode", vo.getOprMode()));

        vo.setPreStatusName(DefinedCommon.getValue("projectStatus", vo.getPreStatus()));

        vo.setAfterStatusName(DefinedCommon.getValue("projectStatus", vo.getAfterStatus()));

        return vo;
    }
    
    public static String getStatus(int i)
    {
        return DefinedCommon.getValue("projectStatus", i);
    }
    
    public static String getOprMode(int i)
    {
    	return DefinedCommon.getValue("oprMode", i);
    }
}

package com.china.center.oa.project.listener.impl;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.project.bean.TaskBean;
import com.china.center.oa.project.dao.ProjectApproveDAO;
import com.china.center.oa.project.dao.TaskDAO;
import com.china.center.oa.project.vo.ProjectApproveVO;
import com.china.center.oa.publics.listener.DesktopListener;
import com.china.center.oa.publics.wrap.DesktopItemWrap;
import com.china.center.oa.publics.wrap.DesktopWrap;

public class DesktopListenerTaskImpl implements DesktopListener
{
	private ProjectApproveDAO projectApproveDAO = null;
	
	private TaskDAO taskDAO = null;
	
	private final static String URL1 = "../project/project.do?method=findTask&update=2&ttflag=11&id=%s&appid=%s";
	
	public DesktopWrap getDeskWrap(User user)
	{
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter();

		DesktopWrap wrap = new DesktopWrap();
        
        List<DesktopItemWrap> itemList = new ArrayList<DesktopItemWrap>();
        
        wrap.setItemList(itemList);

        wrap.setName("任务管理");
        
        ConditionParse condtion = new ConditionParse();
		
		condtion.addWhereStr();
		
		condtion.addCondition(" and (ProjectApproveBean.approverId = '" + user.getStafferId() 
				+ "' or ProjectApproveBean.receiver = '" + user.getStafferId() + "')");

		condtion.addCondition("order by ProjectApproveBean.logTime desc");
		
		List<ProjectApproveVO> warnCEOList = projectApproveDAO.queryEntityVOsByCondition(condtion);
		
        // 
        for (ProjectApproveVO vo : warnCEOList)
        {
            DesktopItemWrap item = new DesktopItemWrap();

            TaskBean task = taskDAO.find(vo.getApplyId());
            
            if (null == task)
            	continue;
            
            item.setHref(formatter.format(URL1, vo.getApplyId().toString(), vo.getId()).toString());

            item.setTitle("[" + task.getTaskName() + "] "
                          + "- 类型:" + DefinedCommon.getValue("223", task.getTaskType()) + " - 状态:" + DefinedCommon.getValue("projectStatus", task.getTaskStatus()) + "任务等待您审批" );

            itemList.add(item);
        }

        return wrap;
    }

	@Override
	public String getKey()
	{
		return "task";
	}

	/**
	 * @return the projectApproveDAO
	 */
	public ProjectApproveDAO getProjectApproveDAO() {
		return projectApproveDAO;
	}

	/**
	 * @param projectApproveDAO the projectApproveDAO to set
	 */
	public void setProjectApproveDAO(ProjectApproveDAO projectApproveDAO) {
		this.projectApproveDAO = projectApproveDAO;
	}

	/**
	 * @return the taskDAO
	 */
	public TaskDAO getTaskDAO() {
		return taskDAO;
	}

	/**
	 * @param taskDAO the taskDAO to set
	 */
	public void setTaskDAO(TaskDAO taskDAO) {
		this.taskDAO = taskDAO;
	}
}

/**
 * File Name: CommonManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import java.util.Iterator;
import java.util.List;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.bean.AlarmBean;
import com.china.center.oa.publics.bean.AreaBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.AlarmConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.AlarmDAO;
import com.china.center.oa.publics.dao.AreaDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.manager.CommonManager;
import com.china.center.oa.publics.vo.UserVO;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * CommonManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see CommonManagerImpl
 * @since 1.0
 */
@Exceptional
public class CommonManagerImpl implements CommonManager
{
    private CommonDAO commonDAO = null;

    private AreaDAO areaDAO = null;

    private StafferDAO stafferDAO = null;

    private UserDAO userDAO = null;

    private AlarmDAO alarmDAO = null;

    public List<AreaBean> queryAreaByParentId(String parentId)
    {
        return areaDAO.queryEntityBeansByFK(parentId);
    }

    public List<StafferBean> queryStafferByLocationId(String locationId)
    {
        return stafferDAO.queryStafferByLocationId(locationId);
    }

    /**
     * 查询区域下的是业务员考核的职员
     * 
     * @param locationId
     * @param type
     * @param attType
     * @return
     */
    public List<StafferBean> queryStafferByLocationIdAndFiter(String locationId, int type,
                                                              int attType)
    {
        List<StafferBean> stafferList = stafferDAO.queryStafferByLocationId(locationId);

        // 当是分公司考核
        if (attType == 0)
        {
            type = -1;
        }

        for (Iterator iterator = stafferList.iterator(); iterator.hasNext();)
        {
            StafferBean stafferBean = (StafferBean)iterator.next();

            if (type == -1)
            {
                if (stafferBean.getExamType() != StafferConstant.EXAMTYPE_EXPAND
                    && stafferBean.getExamType() != StafferConstant.EXAMTYPE_TERMINAL)
                {
                    iterator.remove();
                }
            }
            else
            {
                if (stafferBean.getExamType() != type)
                {
                    iterator.remove();
                }
            }
        }

        return stafferList;
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean updateAlarm(User user, AlarmBean alarm)
        throws MYException
    {
        return alarmDAO.updateEntityBean(alarm);
    }

    public boolean addAlarmNT(String refId, String alarm)
    {
        AlarmBean hasAlarm = alarmDAO.findByUnique(refId);

        if (hasAlarm != null)
        {
            return false;
        }

        AlarmBean alarmBean = new AlarmBean();
        alarmBean.setId(commonDAO.getSquenceString20());
        alarmBean.setAlarmContent(alarm);
        alarmBean.setLogTime(TimeTools.now());
        alarmBean.setRefId(refId);
        alarmBean.setStatus(AlarmConstant.ALARMBEAN_STATUS);
        return alarmDAO.saveEntityBean(alarmBean);
    }

    public boolean addAlarmNT(String refId, String alarm, int type)
    {
        AlarmBean hasAlarm = alarmDAO.findByUnique(refId);

        if (hasAlarm != null)
        {
            return false;
        }

        AlarmBean alarmBean = new AlarmBean();
        alarmBean.setId(commonDAO.getSquenceString20());
        alarmBean.setAlarmContent(alarm);
        alarmBean.setLogTime(TimeTools.now());
        alarmBean.setRefId(refId);
        alarmBean.setStatus(AlarmConstant.ALARMBEAN_STATUS);
        alarmBean.setRefType(type);
        return alarmDAO.saveEntityBean(alarmBean);
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean addAlarm(String refId, String alarm)
    {
        return addAlarmNT(refId, alarm);
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean addAlarm(String refId, String alarm, int type)
    {
        return addAlarmNT(refId, alarm, type);
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean addAlarm(AlarmBean alarm)
    {
        AlarmBean hasAlarm = alarmDAO.findByUnique(alarm.getRefId());

        if (hasAlarm != null)
        {
            return false;
        }

        alarm.setId(commonDAO.getSquenceString20());

        return alarmDAO.saveEntityBean(alarm);
    }

    /**
     * 
     */
	public String getAllSubStafferIds(String stafferId)
	{
		StringBuffer sb = new StringBuffer();
		
        final String TAB = ",";
        final String TAB2 = "'";
        
        sb.append("(");
        
        StafferBean  stafferBean = stafferDAO.find(stafferId); 
        
        String postId = stafferBean.getPostId();
        
        if(null != postId && postId.length() > 0)
        {
        	
	        if(postId.equals(PublicConstant.POST_WORKER)) //4为职员属性
	        {
	        	sb.append(stafferBean.getId());
	        }
	        else if(postId.equals(PublicConstant.POST_SHI_MANAGER)) //16为事业部体系负责人属性
	        {
		        	String industryid = stafferBean.getIndustryId();
		        	
		        	if(null == industryid || "".equals(industryid))
		        	{
		        		sb.append(TAB2);
		        		sb.append(TAB2);
		        	}
		        	else
		        	{
		        	List<StafferBean> stafferList = stafferDAO.queryStafferByindustryid(industryid);
		        	
		        	if(null != stafferList)
		        	{
		        		for(int i = 0 ; i < stafferList.size(); i++ )
		        		{
		        			sb.append(TAB2);
		        			sb.append(stafferList.get(i).getId());
		        			sb.append(TAB2);
		        			if(i+1 < stafferList.size())
		        			{
		        				sb.append(TAB);
		        			}
		        		}
		        	}
		        }
	        }
	        else if(postId.equals(PublicConstant.POST_DEPART_MANAGER)) //17为部门负责人属性
	        {
	        		String principalshipId = stafferBean.getPrincipalshipId();
	        	
		        	if(null == principalshipId || "".equals(principalshipId))
		        	{
		        		sb.append(TAB2);
		        		sb.append(TAB2);
		        	}
		        	else
		        	{
		        	
		        	List<StafferBean> stafferList = stafferDAO.queryStafferByPrincipalshipId(principalshipId);
		        	
		        	if(null != stafferList)
		        	{
		        		for(int i = 0 ; i < stafferList.size(); i++ )
		        		{
		        			sb.append(TAB2);
		        			sb.append(stafferList.get(i).getId());
		        			sb.append(TAB2);
		        			
		        			if(i+1 < stafferList.size())
		        			{
		        				sb.append(TAB);
		        			}
		        		}
		        	}
	        	}
	        }
	        else if(postId.equals(PublicConstant.POST_AREA_MANAGER)) //18为大区负责人属性
	        {
	        	String industryid2 = stafferBean.getIndustryId2();
	        	
	        	if(null == industryid2 || "".equals(industryid2))
	        	{
	        		sb.append(TAB2);
	        		sb.append(TAB2);
	        	}
	        	else
	        	{
		        	List<StafferBean> stafferList = stafferDAO.queryStafferByIndustryid2(industryid2);
		        	
		        	if(null != stafferList)
		        	{
		        		for(int i = 0 ; i < stafferList.size(); i++ )
		        		{
		        			sb.append(TAB2);
		        			sb.append(stafferList.get(i).getId());
		        			sb.append(TAB2);
		        			
		        			if(i+1 < stafferList.size())
		        			{
		        				sb.append(TAB);
		        			}
		        		}
		        	}
	        	}
	        }
	        else if(postId.equals(PublicConstant.POST_COMPANY_MANAGER)) //20为公司负责人属性
	        {
	        	//String industryid3 = stafferBean.getIndustryId3();
	        	//公司负责人查询所有
	        	List<StafferBean> stafferList = stafferDAO.queryAllStaffer();
	        	
	        	if(null != stafferList)
	        	{
	        		for(int i = 0 ; i < stafferList.size(); i++ )
	        		{
	        			sb.append(TAB2);
	        			sb.append(stafferList.get(i).getId());
	        			sb.append(TAB2);
	        			
	        			if(i+1 < stafferList.size())
	        			{
	        				sb.append(TAB);
	        			}
	        		}
	        	}
	        }
        }
        else
        {
        	sb.append(TAB2);
    		sb.append(TAB2);
        }
        sb.append(")");
        
        return sb.toString();
	}
    
	/**
	 * 
	 * {@inheritDoc}
	 */
	public List<StafferBean> queryAllSubStaffers(String stafferId)
	{
		ConditionParse con = new ConditionParse();
		
		con.addWhereStr();
		
		String innerCon = getAllSubStafferIds(stafferId);
		
		con.addCondition(" and StafferBean.id in " + innerCon);
		
		return stafferDAO.queryEntityBeansByCondition(con);
	}
    
	/**
	 * Buffalo JS 远程调用
	 * 
	 * {@inheritDoc}
	 */
	public boolean checkStafferByHandPhone(String handPhone)
	{
		boolean result = false;
		
	    if (!StringTools.isNullOrNone(handPhone))
   		{
   			ConditionParse con = new ConditionParse();
       		
       		con.addWhereStr();
       		
       		con.addCondition("StafferBean.handphone", "=", handPhone);
       		
       		List<StafferBean> stafferList = stafferDAO.queryEntityBeansByCondition(con);
       		
       		if (!ListTools.isEmptyOrNull(stafferList))
       		{
       			String stafferId = stafferList.get(0).getId();
           		
           		List<UserVO> userList = userDAO.queryEntityVOsByFK(stafferId);
           		
           		if (!ListTools.isEmptyOrNull(userList))
           		{
           			result = true;
           		}
       		}
   		}
	    
	    return result;
	}

	
    /**
     * @return the areaDAO
     */
    public AreaDAO getAreaDAO()
    {
        return areaDAO;
    }

    /**
     * @param areaDAO
     *            the areaDAO to set
     */
    public void setAreaDAO(AreaDAO areaDAO)
    {
        this.areaDAO = areaDAO;
    }

    /**
     * @return the stafferDAO
     */
    public StafferDAO getStafferDAO()
    {
        return stafferDAO;
    }

    /**
     * @param stafferDAO
     *            the stafferDAO to set
     */
    public void setStafferDAO(StafferDAO stafferDAO)
    {
        this.stafferDAO = stafferDAO;
    }

    /**
     * @return the userDAO
     */
    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    /**
     * @param userDAO
     *            the userDAO to set
     */
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    /**
     * @return the alarmDAO
     */
    public AlarmDAO getAlarmDAO()
    {
        return alarmDAO;
    }

    /**
     * @param alarmDAO
     *            the alarmDAO to set
     */
    public void setAlarmDAO(AlarmDAO alarmDAO)
    {
        this.alarmDAO = alarmDAO;
    }

    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

}

/**
 * File Name: StafferDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.china.center.jdbc.inter.IbatisDaoSupport;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.tools.ListTools;


/**
 * StafferDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see StafferDAOImpl
 * @since 1.0
 */
public class StafferDAOImpl extends BaseDAO<StafferBean, StafferVO> implements StafferDAO
{
    private IbatisDaoSupport ibatisDaoSupport = null;

    public int countByLocationId(String locationId)
    {
        return jdbcOperation.queryForInt("where locationId = ? ", this.claz, locationId);
    }

    public int countByCode(String code)
    {
        return jdbcOperation.queryForInt("where code = ? ", this.claz, code);
    }

    public boolean updatePwkey(String id, String pwkey)
    {
        this.jdbcOperation.updateField("pwkey", pwkey, id, this.claz);

        return true;
    }

    public boolean updateLever(String id, int lever)
    {
        this.jdbcOperation.updateField("lever", lever, id, this.claz);

        return true;
    }

    public int countByDepartmentId(String departmentId)
    {
        return jdbcOperation.queryForInt("where departmentId = ? ", this.claz, departmentId);
    }

    public int countByPostId(String postId)
    {
        return jdbcOperation.queryForInt("where postId = ? ", this.claz, postId);
    }

    /**
     * 查询区域下的职员
     * 
     * @param locationId
     * @return
     */
    public List<StafferBean> queryStafferByLocationId(String locationId)
    {
        return this.jdbcOperation.queryForList("where locationId = ? order by name", claz,
            locationId);
    }

    /**
     * 重载listEntityBeans
     */
    public List<StafferBean> listEntityBeans()
    {
        return jdbcOperation.queryForList("where 1= 1 order by name", claz);
    }

    /**
     * listCommonEntityBeans
     */
    public List<StafferBean> listCommonEntityBeans()
    {
        return jdbcOperation.queryForList("where status < ? order by name", claz,
            StafferConstant.STATUS_DROP);
    }


    public StafferBean findyStafferByName(String name)
    {
        List<StafferBean> list = this.jdbcOperation.queryForList("where name = ?", claz, name);

        if (ListTools.isEmptyOrNull(list) || list.size() != 1)
        {
            return null;
        }

        return list.get(0);
    }

    public List<StafferBean> queryStafferByAuthId(String authId)
    {
        Map<String, Object> paramterMap = new HashMap();

        paramterMap.put("authId", authId);

        return (List<StafferBean>)this.ibatisDaoSupport.queryForList(
            "StafferDAOImpl.queryStafferByAuthId", paramterMap);
    }

    public List<StafferBean> queryStafferByAuthIdAndIndustryId(String authId, String locationId)
    {
        Map<String, Object> paramterMap = new HashMap();

        paramterMap.put("authId", authId);
        paramterMap.put("industryId", locationId);

        return (List<StafferBean>)this.ibatisDaoSupport.queryForList(
            "StafferDAOImpl.queryStafferByAuthIdAndIndustryId", paramterMap);
    }
    
    public List<StafferBean> queryStafferByPostId(String postId)
    {
        Map<String, Object> paramterMap = new HashMap();

        paramterMap.put("postId", postId);

        return (List<StafferBean>)this.ibatisDaoSupport.queryForList(
            "StafferDAOImpl.queryStafferByPostId", paramterMap);
    }
    
    public List<StafferBean> queryStafferByindustryid(String industryid)
    {
    	return this.jdbcOperation.queryForList("where industryId = ?", claz, industryid);
    }
    
    public List<StafferBean> queryStafferByIndustryid2(String industryid2)
    {
    	return this.jdbcOperation.queryForList("where industryId2 = ?", claz, industryid2);
    }
    
    public List<StafferBean> queryAllStaffer()
    {
    	Map<String, Object> paramterMap = new HashMap();
    	return (List<StafferBean>)this.ibatisDaoSupport.queryForList(
                "StafferDAOImpl.queryAllStaffer", paramterMap);
    }
    
    /**
     * 根据岗位查询人员
     * 
     * @param locationId
     * @return
     */
    public List<StafferBean> queryStafferByPrincipalshipId(String principalshipId)
    {
        return this.jdbcOperation.queryForList("where principalshipId = ?", claz, principalshipId);
    }

    /**
     * @return the ibatisDaoSupport
     */
    public IbatisDaoSupport getIbatisDaoSupport()
    {
        return ibatisDaoSupport;
    }

    /**
     * @param ibatisDaoSupport
     *            the ibatisDaoSupport to set
     */
    public void setIbatisDaoSupport(IbatisDaoSupport ibatisDaoSupport)
    {
        this.ibatisDaoSupport = ibatisDaoSupport;
    }
    
    /**
     * 获取需要提成计算的人员，三大事业部
     * @return
     * @
     */
    public List<StafferBean> getNeedCommissionStaffer() 
    {
        ConditionParse condition = new ConditionParse();
        
        condition.addWhereStr();
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("'").append(StafferConstant.STATS_INDUSTRYID1).append("'").append(",").
            append("'").append(StafferConstant.STATS_INDUSTRYID2).append("'").append(",").
            append("'").append(StafferConstant.STATS_INDUSTRYID3).append("'");
        
        condition.addCondition(" and industryid in (" + sb.toString() +" )");
        
        return this.queryEntityBeansByCondition(condition);
    }
    
    public boolean updateBlack(String id, int black)
    {
        this.jdbcOperation.updateField("black", black, id, this.claz);

        return true;
    }
    
    public List<StafferBean> queryStafferByPostIdAndPrincishipId(String postId,String princiId)
    {
    	Map<String, Object> paramterMap = new HashMap();
    	paramterMap.put("postId", postId);
    	paramterMap.put("principalshipId", princiId);
    	return (List<StafferBean>)this.ibatisDaoSupport.queryForList(
                "StafferDAOImpl.queryStafferByPostIdAndPrincipalshipId", paramterMap);
    }
    
    public List<StafferBean> queryTwoLevel(String princiId)
    {
    	Map<String, Object> paramterMap = new HashMap();
    	paramterMap.put("principalshipId", princiId);
    	return (List<StafferBean>)this.ibatisDaoSupport.queryForList(
                "StafferDAOImpl.queryStafferTwoLevel", paramterMap);
    }
    
    public List<StafferBean> queryThreeLevel(String princiId)
    {
    	Map<String, Object> paramterMap = new HashMap();
    	paramterMap.put("principalshipId", princiId);
    	return (List<StafferBean>)this.ibatisDaoSupport.queryForList(
                "StafferDAOImpl.queryStafferThreeLevel", paramterMap);
    }

	public List<StafferBean> queryByPricipalAndPostId(String principalshipId, String postId)
	{
		if (postId.equals(PublicConstant.POST_DEPART_MANAGER))
		{
			return this.queryEntityBeansByCondition("where principalshipId = ? and postId = ?", principalshipId, postId);
		}
		else if (postId.equals(PublicConstant.POST_SHI_MANAGER))
		{
			return this.queryEntityBeansByCondition("where industryId = ? and postId = ?", principalshipId, postId);
		}
		else if (postId.equals(PublicConstant.POST_COMPANY_MANAGER))
		{
			return this.queryEntityBeansByCondition("where postId = ?", postId);
		}
		else
			return null;
	}
}

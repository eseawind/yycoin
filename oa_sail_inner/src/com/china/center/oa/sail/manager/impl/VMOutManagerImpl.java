package com.china.center.oa.sail.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.BatchReturnLog;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.BatchReturnLogDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.helper.OutHelper;
import com.china.center.oa.sail.manager.VMOutManager;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.TimeTools;

public class VMOutManagerImpl implements VMOutManager
{

	private OutDAO outDAO = null;
	
	private BaseDAO baseDAO = null;
	
	private CommonDAO commonDAO = null;
	
	private DepotpartDAO depotpartDAO = null;
	
	private DepotDAO depotDAO = null;
	
	private BatchReturnLogDAO batchReturnLogDAO = null;
	
	/**
	 * 
	 */
	public VMOutManagerImpl()
	{
	}

	@Override
	@Transactional(rollbackFor = MYException.class)
	public boolean addBeans(User user, List<OutBean> outList) throws MYException
	{
		JudgeTools.judgeParameterIsNull(outList);
		
		String batchId = commonDAO.getSquenceString20();
		
		for (OutBean out : outList)
		{
			List<BaseBean> baseList = out.getBaseList();
			
			String id = getAll(commonDAO.getSquence());

	        String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");
	        
	        String time2 = TimeTools.changeFormat(out.getOutTime(), "yyyy-MM-dd", "yyMMdd");
	        
	        time = time2 + time.substring(6);

	        String flag = OutHelper.getSailHead(out.getType(), out.getOutType());
	        
	        out.setId(getOutId(id));
	    	
	    	out.setFullId(flag + time + id);
	    	
	    	DepotpartBean depotpart = getDepotpart(out.getLocation());
	    	
	    	for (BaseBean each : baseList)
	    	{
	    		each.setId(commonDAO.getSquenceString());
	    		each.setOutId(out.getFullId());
	    		
	    		each.setDepotpartId(depotpart.getId());
	    		each.setDepotpartName(depotpart.getName());
	    	}

	    	outDAO.saveEntityBean(out);
	    	
	    	baseDAO.saveAllEntityBeans(baseList);
	    	
	    	// 记录导入LOG
	    	BatchReturnLog log = new BatchReturnLog();
	    	
	    	log.setBatchId(batchId);
	    	log.setOutId(out.getFullId());
	    	log.setOperatorName(user.getStafferName());
	    	log.setLogTime(TimeTools.now());
	    	
	    	batchReturnLogDAO.saveEntityBean(log);
		}
		
		return true;
	}
	
	private DepotpartBean getDepotpart(String locationId) throws MYException
	{
		DepotBean depot = depotDAO.find(locationId);
		
		List<DepotpartBean> list = depotpartDAO.queryEntityBeansByCondition("WHERE locationId = ? and description like '%默认仓区%'", locationId);
		
		if (ListTools.isEmptyOrNull(list))
		{
			list = depotpartDAO.queryEntityBeansByFK(locationId);
			
			if (ListTools.isEmptyOrNull(list))
			{
				throw new MYException("仓库[%s]下没有 默认仓区", depot.getName());				
			}
		}
		
		return list.get(0);
	}
	
	private String getAll(int i)
    {
        String s = "00000000" + i;

        return s.substring(s.length() - 9);
    }
    
    private String getOutId(String idStr)
    {
        while (idStr.length() > 0 && idStr.charAt(0) == '0')
        {
            idStr = idStr.substring(1);
        }

        return idStr;
    }

	/**
	 * @return the outDAO
	 */
	public OutDAO getOutDAO()
	{
		return outDAO;
	}

	/**
	 * @param outDAO the outDAO to set
	 */
	public void setOutDAO(OutDAO outDAO)
	{
		this.outDAO = outDAO;
	}

	/**
	 * @return the baseDAO
	 */
	public BaseDAO getBaseDAO()
	{
		return baseDAO;
	}

	/**
	 * @param baseDAO the baseDAO to set
	 */
	public void setBaseDAO(BaseDAO baseDAO)
	{
		this.baseDAO = baseDAO;
	}

	/**
	 * @return the commonDAO
	 */
	public CommonDAO getCommonDAO()
	{
		return commonDAO;
	}

	/**
	 * @param commonDAO the commonDAO to set
	 */
	public void setCommonDAO(CommonDAO commonDAO)
	{
		this.commonDAO = commonDAO;
	}

	/**
	 * @return the depotpartDAO
	 */
	public DepotpartDAO getDepotpartDAO()
	{
		return depotpartDAO;
	}

	/**
	 * @param depotpartDAO the depotpartDAO to set
	 */
	public void setDepotpartDAO(DepotpartDAO depotpartDAO)
	{
		this.depotpartDAO = depotpartDAO;
	}

	/**
	 * @return the depotDAO
	 */
	public DepotDAO getDepotDAO()
	{
		return depotDAO;
	}

	/**
	 * @param depotDAO the depotDAO to set
	 */
	public void setDepotDAO(DepotDAO depotDAO)
	{
		this.depotDAO = depotDAO;
	}

	/**
	 * @return the batchReturnLogDAO
	 */
	public BatchReturnLogDAO getBatchReturnLogDAO()
	{
		return batchReturnLogDAO;
	}

	/**
	 * @param batchReturnLogDAO the batchReturnLogDAO to set
	 */
	public void setBatchReturnLogDAO(BatchReturnLogDAO batchReturnLogDAO)
	{
		this.batchReturnLogDAO = batchReturnLogDAO;
	}
}

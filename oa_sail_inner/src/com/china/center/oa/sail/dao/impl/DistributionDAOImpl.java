package com.china.center.oa.sail.dao.impl;

import com.china.center.jdbc.annosql.tools.BeanUtils;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.dao.DistributionDAO;
import com.china.center.oa.sail.vo.DistributionVO;

public class DistributionDAOImpl extends BaseDAO<DistributionBean, DistributionVO> implements
		DistributionDAO
{

	@Override
	public boolean updateBean(String id, DistributionBean bean)
	{
		String sql = "update t_center_distribution set provinceid = ? , cityid = ? , areaid = ? , " +
				"address = ?, receiver = ?, mobile = ?, shipping = ? where id = ?";
		
		int i = jdbcOperation.update(sql, bean.getProvinceId(), bean.getCityId(), bean.getAreaId(), 
				bean.getAddress(), bean.getReceiver(), bean.getMobile(), bean.getShipping(), id);

        if (i == 0)
        {
            return false;
        }else
        {
            return true;
        }
	}

	/* (non-Javadoc)
	 * @see com.china.center.oa.sail.dao.DistributionDAO#updateOutboundDate(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateOutboundDate(String id, String outboundDate) {
		String sql = "update t_center_distribution set outboundDate = ? where outid = ?";
		
		int i = jdbcOperation.update(sql, outboundDate, id);

        if (i == 0)
        {
            return false;
        }else
        {
            return true;
        }
	}
}

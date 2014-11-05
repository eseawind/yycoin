package com.china.center.oa.customerservice.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.china.center.oa.customerservice.bean.FeedBackDetailBean;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.tools.MathTools;
import com.china.center.tools.TimeTools;

public abstract class CustomerServiceHelper
{
	/**
	 * 根据产品与单价合计数量与金额
	 * @param list
	 * @return
	 */
	public static List<FeedBackDetailBean> trimFeedBackDetailList(List<FeedBackDetailBean> list)
	{
		Map<String, FeedBackDetailBean> map = new HashMap<String, FeedBackDetailBean>();
		
		for (FeedBackDetailBean each : list)
		{
			String key = each.getProductId() + "-" + getPriceKey(each.getPrice());
			
			if (!map.containsKey(key))
			{
				map.put(key, each);
			}
			else
			{
				FeedBackDetailBean bean = map.get(key);
				
				bean.setAmount(bean.getAmount() + each.getAmount());
				
				bean.setMoney(bean.getMoney() + each.getMoney());
				
				bean.setHasBack(bean.getHasBack() + each.getHasBack());
				
				bean.setBackMoney(bean.getBackMoney() + each.getBackMoney());
				
				bean.setNoPayAmount(bean.getNoPayAmount() + each.getNoPayAmount());
				
				bean.setNoPayMoneys(bean.getNoPayMoneys() + each.getNoPayMoneys());
			}
		}
		
        Collection<FeedBackDetailBean> values = map.values();

        List<FeedBackDetailBean> lastList = new ArrayList<FeedBackDetailBean>();

        for (FeedBackDetailBean each : values)
        {
            lastList.add(each);
        }
        
        return lastList;
	}
	
    public static String getPriceKey(double value)
    {
        String formatNum = MathTools.formatNum(value);

        long last = Math.round( (Double.parseDouble(formatNum) * 100));

        return String.valueOf(last);
    }
	
	/**
	 * 约定：统计近5天的，自提过滤掉；货运; 近5天;  快递，近3天; 公司, 近4天; 货运+快递：近5天
	 * @param distBean
	 * @param changeTime  
	 * @beginDate 统计开始日期，倒退5天的日期
	 * @return true 可以统计  false 暂不统计（过滤）
	 */
	public static boolean checkStatsOutTime(DistributionBean distBean, OutBean out, String beginDate)
	{
		String changeTime = TimeTools.changeTimeToDate(out.getChangeTime());
		
		// 终端
		if (out.getIndustryId().equals("5111658"))
		{
			if (distBean.getShipping() != 0)
			{
				return false;
			}
			
			return true;
		}
		
		if (distBean.getShipping() == 1) // 公司
		{
			if (changeTime.equals(TimeTools.getSpecialDateStringByDays(beginDate, 1, "yyyy-MM-dd")))
				return true;
			else
				return false;
		}
		else if (distBean.getShipping() == 0 || distBean.getShipping() == 2) // 自提 快递
		{
			if (changeTime.equals(TimeTools.getSpecialDateStringByDays(beginDate, 2, "yyyy-MM-dd")))
				return true;
			else
				return false;
		}
		else if (distBean.getShipping() == 3 || distBean.getShipping() == 4) // 货运
		{
			if (changeTime.equals(beginDate))
				return true;
			else
				return false;
		}
		else
		{
			return true;
		}
	}
}

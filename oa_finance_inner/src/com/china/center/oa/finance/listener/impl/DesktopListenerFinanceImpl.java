package com.china.center.oa.finance.listener.impl;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.dao.StockPayApplyDAO;
import com.china.center.oa.finance.dao.StockPrePayApplyDAO;
import com.china.center.oa.finance.vo.StockPayApplyVO;
import com.china.center.oa.finance.vo.StockPrePayApplyVO;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.listener.DesktopListener;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.wrap.DesktopItemWrap;
import com.china.center.oa.publics.wrap.DesktopWrap;

public class DesktopListenerFinanceImpl //implements DesktopListener
{/*
	private StockPayApplyDAO stockPayApplyDAO = null;
	
	private StockPrePayApplyDAO stockPrePayApplyDAO = null;
	
	private UserManager userManager = null;
	
	private final static String URL1 = "../finance/stock.do?method=findStockPayApply&id=%s&update=1&mode=1";
	
	private final static String URL2 = "../finance/stock.do?method=findStockPrePayApply&id=%s&update=1&mode=1";
	
	@Override
	public DesktopWrap getDeskWrap(User user)
	{
		Formatter formatter = new Formatter();

        DesktopWrap wrap = new DesktopWrap();
        
        List<DesktopItemWrap> itemList = new ArrayList<DesktopItemWrap>();
        
        wrap.setItemList(itemList);

        wrap.setName("采购付款审批");
        
        // 签权
		if (!userManager.containAuth(user.getId(), AuthConstant.STOCK_PAY_CEO))
		{
			return wrap;
		}

        ConditionParse condtion = new ConditionParse();
		
		condtion.addWhereStr();
		
		condtion.addCondition("and StockPayApplyBean.status = 2 ");
		
		condtion.addCondition("order by StockPayApplyBean.payDate desc");
		
		List<StockPayApplyVO> warnCEOList = stockPayApplyDAO.queryEntityVOsByCondition(condtion);
		
		condtion.clear();
		
		condtion.addCondition("and StockPrePayApplyBean.status = 2 ");
		
		condtion.addCondition("order by StockPrePayApplyBean.payDate desc");
		
		List<StockPrePayApplyVO> warnCEOList2 = stockPrePayApplyDAO.queryEntityVOsByCondition(condtion);
		
        // 
        for (StockPayApplyVO vo : warnCEOList)
        {
            DesktopItemWrap item = new DesktopItemWrap();

            item.setHref(formatter.format(URL1, vo.getId()).toString());

            item.setTitle("[" + vo.getLogTime() + "] "
                          + vo.getStafferName() + "申请采购付款等待您审批" );

            itemList.add(item);
        }
        // 
        for (StockPrePayApplyVO vo : warnCEOList2)
        {
            DesktopItemWrap item = new DesktopItemWrap();

            item.setHref(formatter.format(URL2, vo.getId()).toString());

            item.setTitle("[" + vo.getLogTime() + "] "
                          + vo.getStafferName() + "申请采购预付款等待您审批" );

            itemList.add(item);
        }

        return wrap;
    }

	@Override
	public String getKey()
	{
		return "Finance";
	}

	*//**
	 * @return the stockPayApplyDAO
	 *//*
	public StockPayApplyDAO getStockPayApplyDAO()
	{
		return stockPayApplyDAO;
	}

	*//**
	 * @param stockPayApplyDAO the stockPayApplyDAO to set
	 *//*
	public void setStockPayApplyDAO(StockPayApplyDAO stockPayApplyDAO)
	{
		this.stockPayApplyDAO = stockPayApplyDAO;
	}

	*//**
	 * @return the stockPrePayApplyDAO
	 *//*
	public StockPrePayApplyDAO getStockPrePayApplyDAO()
	{
		return stockPrePayApplyDAO;
	}

	*//**
	 * @param stockPrePayApplyDAO the stockPrePayApplyDAO to set
	 *//*
	public void setStockPrePayApplyDAO(StockPrePayApplyDAO stockPrePayApplyDAO)
	{
		this.stockPrePayApplyDAO = stockPrePayApplyDAO;
	}

	*//**
	 * @return the userManager
	 *//*
	public UserManager getUserManager()
	{
		return userManager;
	}

	*//**
	 * @param userManager the userManager to set
	 *//*
	public void setUserManager(UserManager userManager)
	{
		this.userManager = userManager;
	}
*/}

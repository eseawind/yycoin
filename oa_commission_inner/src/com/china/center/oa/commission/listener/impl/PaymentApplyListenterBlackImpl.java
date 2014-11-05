package com.china.center.oa.commission.listener.impl;

import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.commission.dao.BlackDAO;
import com.china.center.oa.commission.dao.BlackOutDAO;
import com.china.center.oa.commission.manager.BlackManager;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.PaymentApplyBean;
import com.china.center.oa.finance.bean.PaymentBean;
import com.china.center.oa.finance.listener.PaymentApplyListener;
import com.china.center.oa.sail.bean.OutBean;

public class PaymentApplyListenterBlackImpl implements PaymentApplyListener 
{
    private BlackDAO blackDAO = null;
    
    private BlackOutDAO blackOutDAO = null;
    
    private BlackManager blackManager = null;        
    
    @Override
    public String getListenerType() 
    {
        return "PaymentApplyListener.BlackImpl";
    }

    /**
     * 处理黑名单中,当回款通过时对黑名单的处理
     * {@inheritDoc}
     */
    public void onPassBean(User user, PaymentApplyBean bean) throws MYException 
    {
    	
    }
   
	@Override
	public void onDrawTransfer(User user, PaymentBean bean, String outBillId) throws MYException 
	{
		
	}
	
	@Override
	public void onStockBack(User user, List<PaymentApplyBean> applyList)
			throws MYException
	{
		// do nothing
	}

    public BlackDAO getBlackDAO() {
        return blackDAO;
    }

    public void setBlackDAO(BlackDAO blackDAO) {
        this.blackDAO = blackDAO;
    }

    public BlackOutDAO getBlackOutDAO() {
        return blackOutDAO;
    }

    public void setBlackOutDAO(BlackOutDAO blackOutDAO) {
        this.blackOutDAO = blackOutDAO;
    }

    public BlackManager getBlackManager() {
        return blackManager;
    }

    public void setBlackManager(BlackManager blackManager) {
        this.blackManager = blackManager;
    }
}

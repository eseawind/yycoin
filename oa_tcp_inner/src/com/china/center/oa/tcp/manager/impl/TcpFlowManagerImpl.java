/**
 * File Name: TcpFlowManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-9-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.manager.impl;


import java.util.ArrayList;
import java.util.List;

import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.PreInvoiceApplyBean;
import com.china.center.oa.finance.dao.PreInvoiceApplyDAO;
import com.china.center.oa.group.dao.GroupVSStafferDAO;
import com.china.center.oa.group.vs.GroupVSStafferBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.tcp.bean.AbstractTcpBean;
import com.china.center.oa.tcp.bean.ExpenseApplyBean;
import com.china.center.oa.tcp.bean.TcpApproveBean;
import com.china.center.oa.tcp.bean.TcpFlowBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.constanst.TcpFlowConstant;
import com.china.center.oa.tcp.dao.ExpenseApplyDAO;
import com.china.center.oa.tcp.dao.TcpApproveDAO;
import com.china.center.oa.tcp.dao.TcpFlowDAO;
import com.china.center.oa.tcp.dao.TravelApplyDAO;
import com.china.center.oa.tcp.manager.TcpFlowManager;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.TimeTools;


/**
 * TcpFlowManagerImpl
 * 
 * @author ZHUZHU
 * @version 2011-9-6
 * @see TcpFlowManagerImpl
 * @since 3.0
 */
@IntegrationAOP
public class TcpFlowManagerImpl implements TcpFlowManager
{
    private TcpApproveDAO tcpApproveDAO = null;

    private CommonDAO commonDAO = null;

    private TravelApplyDAO travelApplyDAO = null;

    private ExpenseApplyDAO expenseApplyDAO = null;

    private TcpFlowDAO tcpFlowDAO = null;

    private GroupVSStafferDAO groupVSStafferDAO = null;
    
    private PreInvoiceApplyDAO preInvoiceApplyDAO = null;
    
    private FlowLogDAO flowLogDAO = null;

    /**
     * default constructor
     */
    public TcpFlowManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.tcp.manager.TcpFlowManager#drawApprove(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @IntegrationAOP(lock = TcpFlowConstant.DRAW_LOCK)
    @Transactional(rollbackFor = MYException.class)
    public boolean drawApprove(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        TcpApproveBean approve = tcpApproveDAO.find(id);

        if (approve == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !approve.getApproverId().equals(user.getStafferId()))
        {
            throw new MYException("没有权限,请确认操作");
        }

        if (approve.getPool() != TcpConstanst.TCP_POOL_POOL)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 删除其他人的认领
        tcpApproveDAO.deleteEntityBeansByFK(approve.getApplyId());

        approve.setId(commonDAO.getSquenceString20());

        approve.setPool(TcpConstanst.TCP_POOL_COMMON);

        tcpApproveDAO.saveEntityBean(approve);

        return true;
    }

    @IntegrationAOP(lock = TcpFlowConstant.DRAW_LOCK)
    @Transactional(rollbackFor = MYException.class)
    public boolean odrawApprove(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        TcpApproveBean approve = tcpApproveDAO.find(id);

        if (approve == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !approve.getApproverId().equals(user.getStafferId()))
        {
            throw new MYException("没有权限,请确认操作");
        }

        if (approve.getPool() != TcpConstanst.TCP_POOL_COMMON)
        {
            throw new MYException("数据错误,请确认操作");
        }

        AbstractTcpBean atb = null;

        if (approve.getType() <= 10)
        {
            atb = travelApplyDAO.find(approve.getApplyId());
        }
        else
        {
            atb = expenseApplyDAO.find(approve.getApplyId());
        }

        if (atb == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 获得当前的处理环节
        TcpFlowBean token = tcpFlowDAO
            .findByFlowKeyAndNextStatus(atb.getFlowKey(), atb.getStatus());

        if (token == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !token.getNextPlugin().startsWith("pool"))
        {
            throw new MYException("当前环节不能退领,请确认操作");
        }

        String groupId = token.getNextPlugin().substring(5);

        List<GroupVSStafferBean> vsList = groupVSStafferDAO.queryEntityBeansByFK(groupId);

        if (ListTools.isEmptyOrNull(vsList))
        {
            throw new MYException("当前群组内没有人员,请确认操作");
        }

        List<String> processList = new ArrayList();

        for (GroupVSStafferBean groupVSStafferBean : vsList)
        {
            processList.add(groupVSStafferBean.getStafferId());
        }

        // 删除全部
        tcpApproveDAO.deleteEntityBeansByFK(approve.getApplyId());

        // 恢复工单池
        for (String processId : processList)
        {
            TcpApproveBean newApprove = new TcpApproveBean();

            newApprove.setId(commonDAO.getSquenceString20());
            newApprove.setApplyerId(atb.getStafferId());
            newApprove.setApplyId(approve.getApplyId());
            newApprove.setApproverId(processId);
            newApprove.setFlowKey(atb.getFlowKey());
            newApprove.setLogTime(TimeTools.now());
            newApprove.setDepartmentId(atb.getDepartmentId());
            newApprove.setName(atb.getName());
            newApprove.setStatus(atb.getStatus());
            newApprove.setTotal(atb.getTotal());
            newApprove.setType(atb.getType());
            newApprove.setStype(atb.getStype());
            newApprove.setPool(TcpConstanst.TCP_POOL_POOL);
            newApprove.setCheckTotal(atb.getBorrowTotal());

            if (atb instanceof ExpenseApplyBean)
            {
                newApprove.setPayType( ((ExpenseApplyBean)atb).getPayType());
            }
            else
            {
                newApprove.setPayType(TcpConstanst.PAYTYPE_GPAY_BO);
            }

            tcpApproveDAO.saveEntityBean(newApprove);
        }

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean endApprove(User user, String id)
        throws MYException
    {
    	JudgeTools.judgeParameterIsNull(user, id);

    	TcpApproveBean approve = tcpApproveDAO.find(id);

        if (approve == null)
        {
            throw new MYException("数据错误,请确认操作1");
        }

        PreInvoiceApplyBean apply = preInvoiceApplyDAO.find(approve.getApplyId());
        
        if (null == apply)
        {
        	throw new MYException("数据错误,请确认操作2");
        }
        
        if (apply.getStatus() != TcpConstanst.TCP_STATUS_APPLY_RELATE)
        {
        	throw new MYException("状态不是待申请人关联销售,请确认操作");
        }
        
    	preInvoiceApplyDAO.updateStatus(approve.getApplyId(), TcpConstanst.TCP_STATUS_END);
    	
    	tcpApproveDAO.deleteEntityBeansByFK(approve.getApplyId());
    	
        FlowLogBean log = new FlowLogBean();

        log.setFullId(approve.getApplyId());

        log.setActor(user.getStafferName());

        log.setActorId(user.getStafferId());

        log.setOprMode(PublicConstant.OPRMODE_PASS);

        log.setDescription(user.getStafferName() + " 强制结束");

        log.setLogTime(TimeTools.now());

        log.setPreStatus(TcpConstanst.TCP_STATUS_APPLY_RELATE);

        log.setAfterStatus(TcpConstanst.TCP_STATUS_END);

        flowLogDAO.saveEntityBean(log);
    	
    	return true;
    }
    
    /**
     * @return the tcpApproveDAO
     */
    public TcpApproveDAO getTcpApproveDAO()
    {
        return tcpApproveDAO;
    }

    /**
     * @param tcpApproveDAO
     *            the tcpApproveDAO to set
     */
    public void setTcpApproveDAO(TcpApproveDAO tcpApproveDAO)
    {
        this.tcpApproveDAO = tcpApproveDAO;
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

    /**
     * @return the travelApplyDAO
     */
    public TravelApplyDAO getTravelApplyDAO()
    {
        return travelApplyDAO;
    }

    /**
     * @param travelApplyDAO
     *            the travelApplyDAO to set
     */
    public void setTravelApplyDAO(TravelApplyDAO travelApplyDAO)
    {
        this.travelApplyDAO = travelApplyDAO;
    }

    /**
     * @return the expenseApplyDAO
     */
    public ExpenseApplyDAO getExpenseApplyDAO()
    {
        return expenseApplyDAO;
    }

    /**
     * @param expenseApplyDAO
     *            the expenseApplyDAO to set
     */
    public void setExpenseApplyDAO(ExpenseApplyDAO expenseApplyDAO)
    {
        this.expenseApplyDAO = expenseApplyDAO;
    }

    /**
     * @return the tcpFlowDAO
     */
    public TcpFlowDAO getTcpFlowDAO()
    {
        return tcpFlowDAO;
    }

    /**
     * @param tcpFlowDAO
     *            the tcpFlowDAO to set
     */
    public void setTcpFlowDAO(TcpFlowDAO tcpFlowDAO)
    {
        this.tcpFlowDAO = tcpFlowDAO;
    }

    /**
     * @return the groupVSStafferDAO
     */
    public GroupVSStafferDAO getGroupVSStafferDAO()
    {
        return groupVSStafferDAO;
    }

    /**
     * @param groupVSStafferDAO
     *            the groupVSStafferDAO to set
     */
    public void setGroupVSStafferDAO(GroupVSStafferDAO groupVSStafferDAO)
    {
        this.groupVSStafferDAO = groupVSStafferDAO;
    }

	public PreInvoiceApplyDAO getPreInvoiceApplyDAO()
	{
		return preInvoiceApplyDAO;
	}

	public void setPreInvoiceApplyDAO(PreInvoiceApplyDAO preInvoiceApplyDAO)
	{
		this.preInvoiceApplyDAO = preInvoiceApplyDAO;
	}

	public FlowLogDAO getFlowLogDAO()
	{
		return flowLogDAO;
	}

	public void setFlowLogDAO(FlowLogDAO flowLogDAO)
	{
		this.flowLogDAO = flowLogDAO;
	}
}

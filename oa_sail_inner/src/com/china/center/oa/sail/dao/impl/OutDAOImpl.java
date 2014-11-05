/**
 * File Name: OutDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.IbatisDaoSupport;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.vo.OutVO;
import com.china.center.oa.sail.wrap.ConfirmInsWrap;
import com.china.center.oa.sail.wrap.CreditWrap;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * OutDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-11-7
 * @see OutDAOImpl
 * @since 1.0
 */
public class OutDAOImpl extends BaseDAO<OutBean, OutVO> implements OutDAO
{
    private IbatisDaoSupport ibatisDaoSupport = null;

    /**
     * default constructor
     */
    public OutDAOImpl()
    {
    }
    

    public boolean mark(String fullId, boolean status)
    {
        int i = jdbcOperation.updateField("mark", status, fullId, this.claz);

        return i != 0;
    }

    public boolean modifyChecks(String fullId, String checks)
    {
        String sql = "update t_center_out set checks = ?, checkStatus = ? where fullid = ?";

        jdbcOperation.update(sql, checks, PublicConstant.CHECK_STATUS_END, fullId);

        return true;
    }
    
    public List<BaseBean> queryBaseByConditions(final Map dataMap)
    throws MYException
    {
    	return (List<BaseBean>)this.ibatisDaoSupport.queryForList(
                "OutDAO.queryBaseByConditions", dataMap);
    }

    public boolean modifyData(String fullId, String date)
    {
        jdbcOperation.updateField("outTime", date, fullId, this.claz);

        return true;
    }

    public boolean modifyManagerTime(String fullId, String managerTime)
    {
        jdbcOperation.updateField("managerTime", managerTime, fullId, this.claz);

        return true;
    }

    public boolean modifyOutHadPay(String fullId, double hadPay)
    {
        jdbcOperation.updateField("hadPay", hadPay, fullId, this.claz);

        return true;
    }

    public boolean modifyBadDebts(String fullId, double badDebts)
    {
        jdbcOperation.updateField("badDebts", badDebts, fullId, this.claz);

        return true;
    }

    public boolean modifyOutStatus(String fullId, int status)
    {
        jdbcOperation.updateField("status", status, fullId, this.claz);

        return true;
    }
    
    public List<OutBean> queryOutByConditions(String cid,String pid)
    {
    	String sql = "select BaseBean.* from T_CENTER_BASE BaseBean,  t_center_out O  " +
		"where BaseBean.OUTID=O.fullid and O.status in(3,4)  and O.customerId="+cid+" and BaseBean.productId=? order by O.pay,O.logTime";
		List<OutBean> list = this.jdbcOperation.queryForListBySql(sql,OutBean.class, pid);
		if(list !=null && list.size() > 0 )
		{
			return list;
		}
		return null;
    }
    
    public List<OutBean> queryOutByConditions1(String cid,String pid)
    {
    	String sql = "select BaseBean.* from T_CENTER_BASE BaseBean,  t_center_out O  " +
		"where BaseBean.OUTID=O.fullid and O.outType=5  and O.customerId="+cid+" and BaseBean.productId =?";
    	List<OutBean> list = this.jdbcOperation.queryForListBySql(sql,OutBean.class, pid);
		if(list !=null && list.size() > 0 )
		{
			return list;
		}
		return null;
    }

    public boolean updatePay(String fullId, int pay)
    {
        if (pay == OutConstant.PAY_NOT)
        {
            String sql = "update t_center_out set pay = ?, lastModified = ? where fullid = ?";

            int i = jdbcOperation.update(sql, pay, TimeTools.now_short(), fullId);

            return i != 0;
        }

        // 付款的       
        String sql = "update t_center_out set pay = ?, redate = ?, payTime = ?, lastModified = ? where fullid = ? and (payTime = '' or payTime is null )";

        int i = jdbcOperation.update(sql, pay, TimeTools.now_short(), TimeTools.now(), TimeTools.now_short(), fullId);

        if (i == 0)
        {
            String sql1 = "update t_center_out set pay = ?, lastModified = ? where fullid = ? ";
            
            int j = jdbcOperation.update(sql1, pay, TimeTools.now_short(), fullId);
            
            return j != 0;
        }else
        {
            return true;
        }
        
    }

    public boolean updatePmtype(String fullId, int pmtype)
    {
        String sql = "update t_center_out set pmtype = ? where fullid = ?";

        int i = jdbcOperation.update(sql, pmtype, fullId);

        return i != 0;
    }

    public boolean modifyReDate(String fullId, String reDate)
    {
        jdbcOperation.updateField("redate", reDate, fullId, this.claz);

        return true;
    }

    public boolean modifyTempType(String fullId, int tempType)
    {
        jdbcOperation.updateField("tempType", tempType, fullId, this.claz);

        return true;
    }

    /**
     * 自己开单占用
     * 
     * @param stafferId
     * @param beginDate
     * @param endDate
     * @return
     */
    public double sumNoPayAndAvouchBusinessByStafferId(String stafferId, String industryId,
                                                       String beginDate, String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("stafferId", stafferId);
        paramterMap.put("beginDate", beginDate);
        paramterMap.put("industryId", industryId);
        paramterMap.put("endDate", endDate);

        Object max = getIbatisDaoSupport().queryForObject(
            "OutDAO.sumNoPayAndAvouchBusinessByStafferId", paramterMap);

        if (max == null)
        {
            return 0.0d;
        }

        return (Double)max;
    }

    /**
     * 查询自己未付款的单据(industryId暂时忽略)
     * 
     * @param stafferId
     * @param industryId
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<CreditWrap> queryNoPayAndAvouchBusinessByStafferId(String stafferId,
                                                                   String industryId,
                                                                   String beginDate, String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("stafferId", stafferId);
        paramterMap.put("beginDate", beginDate);
        paramterMap.put("endDate", endDate);

        List<CreditWrap> result = getIbatisDaoSupport().queryForList(
            "OutDAO.queryNoPayAndAvouchBusinessByStafferId", paramterMap);

        return result;
    }

    public List<CreditWrap> queryNoPayAndAvouchBusinessByManagerId2(String stafferId,
                                                                    String beginDate, String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("stafferId", stafferId);
        paramterMap.put("beginDate", beginDate);
        paramterMap.put("endDate", endDate);

        List<CreditWrap> result = getIbatisDaoSupport().queryForList(
            "OutDAO.queryNoPayAndAvouchBusinessByManagerId2", paramterMap);

        return result;
    }

    public List<CreditWrap> queryNoPayAndAvouchBusinessByManagerId3(String stafferId,
                                                                    String beginDate, String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("stafferId", stafferId);
        paramterMap.put("beginDate", beginDate);
        paramterMap.put("endDate", endDate);

        List<CreditWrap> result = getIbatisDaoSupport().queryForList(
            "OutDAO.queryNoPayAndAvouchBusinessByManagerId3", paramterMap);

        return result;
    }

    public List<CreditWrap> queryNoPayAndAvouchBusinessByManagerId(String stafferId,
                                                                   String industryId,
                                                                   String beginDate, String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("stafferId", stafferId);
        paramterMap.put("beginDate", beginDate);
        paramterMap.put("industryId", industryId);
        paramterMap.put("endDate", endDate);

        List<CreditWrap> result = getIbatisDaoSupport().queryForList(
            "OutDAO.queryNoPayAndAvouchBusinessByManagerId", paramterMap);

        return result;
    }

    public List<CreditWrap> queryAllNoPay(String stafferId, String industryId, String beginDate,
                                          String endDate)
    {
        List<CreditWrap> list = queryNoPayAndAvouchBusinessByStafferId(stafferId, industryId,
            beginDate, endDate);

        list.addAll(queryNoPayAndAvouchBusinessByManagerId(stafferId, industryId, beginDate,
            endDate));

        return list;
    }

    public double sumAllNoPayAndAvouchBusinessByStafferId(String stafferId, String industryId,
                                                          String beginDate, String endDate)
    {
        return sumNoPayAndAvouchBusinessByStafferId(stafferId, industryId, beginDate, endDate)
               + sumNoPayAndAvouchBusinessByManagerId(stafferId, industryId, beginDate, endDate);
    }

    public double sumNoPayAndAvouchBusinessByManagerId(String stafferId, String industryId,
                                                       String beginDate, String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("stafferId", stafferId);
        paramterMap.put("beginDate", beginDate);
        paramterMap.put("industryId", industryId);
        paramterMap.put("endDate", endDate);

        Object max = getIbatisDaoSupport().queryForObject(
            "OutDAO.sumNoPayAndAvouchBusinessByManagerId", paramterMap);

        if (max == null)
        {
            return 0.0d;
        }

        return (Double)max;
    }

    public double sumNoPayAndAvouchBusinessByManagerId2(String stafferId, String beginDate,
                                                        String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("stafferId", stafferId);
        paramterMap.put("beginDate", beginDate);
        paramterMap.put("endDate", endDate);

        Object max = getIbatisDaoSupport().queryForObject(
            "OutDAO.sumNoPayAndAvouchBusinessByManagerId2", paramterMap);

        if (max == null)
        {
            return 0.0d;
        }

        return (Double)max;
    }

    public double sumNoPayAndAvouchBusinessByManagerId3(String stafferId, String beginDate,
                                                        String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("stafferId", stafferId);
        paramterMap.put("beginDate", beginDate);
        paramterMap.put("endDate", endDate);

        Object max = getIbatisDaoSupport().queryForObject(
            "OutDAO.sumNoPayAndAvouchBusinessByManagerId3", paramterMap);

        if (max == null)
        {
            return 0.0d;
        }

        return (Double)max;
    }

    public double sumNoPayBusiness(String cid, String beginDate, String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("customerId", cid);
        paramterMap.put("beginDate", beginDate);
        paramterMap.put("endDate", endDate);

        Object max = getIbatisDaoSupport().queryForObject("OutDAO.sumNoPayBusiness", paramterMap);

        if (max == null)
        {
            return 0.0d;
        }

        return (Double)max;
    }

    public Integer sumPreassignAmount(Map parMap)
    {
        Integer result = (Integer)ibatisDaoSupport.queryForObject("OutDAO.sumPreassignAmount",
            parMap);

        if (result == null)
        {
            return 0;
        }

        return result;

    }

    public Integer countNotEndProductInIn(String productId, String beginDate, String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("productId", productId);
        paramterMap.put("beginDate", beginDate);
        paramterMap.put("endDate", endDate);

        Object count = getIbatisDaoSupport().queryForObject("OutDAO.countNotEndProductInIn",
            paramterMap);

        if (count == null)
        {
            return 0;
        }

        return (Integer)count;
    }

    public Integer sumNotEndProductInIn2(StorageRelationBean relation, String beginDate,
                                         String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("productId", relation.getProductId());
        paramterMap.put("depotpartId", relation.getDepotpartId());
        paramterMap.put("costPriceKey", relation.getPriceKey());
        paramterMap.put("owner", relation.getStafferId());

        paramterMap.put("beginDate", beginDate);
        paramterMap.put("endDate", endDate);

        Object count = getIbatisDaoSupport().queryForObject(
            "OutDAO.sumNotEndProductInInByStorageRelation", paramterMap);

        if (count == null)
        {
            return 0;
        }

        return -(Integer)count;
    }

    public Integer sumInwayProductInBuy(StorageRelationBean relation, String beginDate,
                                        String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("productId", relation.getProductId());
        paramterMap.put("depotpartId", relation.getDepotpartId());
        paramterMap.put("costPriceKey", relation.getPriceKey());
        paramterMap.put("owner", relation.getStafferId());

        paramterMap.put("beginDate", beginDate);
        paramterMap.put("endDate", endDate);

        Object count = getIbatisDaoSupport().queryForObject("OutDAO.sumInwayProductInBuy",
            paramterMap);

        if (count == null)
        {
            return 0;
        }

        return (Integer)count;
    }

    public Integer sumNotEndProductInOut2(StorageRelationBean relation, String beginDate,
                                          String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("productId", relation.getProductId());
        paramterMap.put("depotpartId", relation.getDepotpartId());
        paramterMap.put("costPriceKey", relation.getPriceKey());
        paramterMap.put("owner", relation.getStafferId());

        paramterMap.put("beginDate", beginDate);
        paramterMap.put("endDate", endDate);

        Object count = getIbatisDaoSupport().queryForObject(
            "OutDAO.sumNotEndProductInOutByStorageRelation", paramterMap);

        if (count == null)
        {
            return 0;
        }

        return (Integer)count;
    }

    public Integer countNotEndProductInOut(String productId, String beginDate, String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("productId", productId);
        paramterMap.put("beginDate", beginDate);
        paramterMap.put("endDate", endDate);

        Object count = getIbatisDaoSupport().queryForObject("OutDAO.countNotEndProductInOut",
            paramterMap);

        if (count == null)
        {
            return 0;
        }

        return (Integer)count;
    }

    public Integer sumNotEndProductInInByStorageRelation(String productId, String depotpartId,
                                                         String priceKey, String ower)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("productId", productId);
        paramterMap.put("depotpartId", depotpartId);
        paramterMap.put("costPriceKey", priceKey);
        paramterMap.put("owner", ower);
        paramterMap.put("beginDate", TimeTools.getDateShortString( -180));
        paramterMap.put("endDate", TimeTools.now_short());

        Object count = getIbatisDaoSupport().queryForObject(
            "OutDAO.sumNotEndProductInInByStorageRelation", paramterMap);

        if (count == null)
        {
            return 0;
        }

        // 负数
        return -(Integer)count;
    }

    public Integer sumNotEndProductInInByStorageRelation2(String productId, String depotpartId,
            String ower)
	{
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("productId", productId);
        paramterMap.put("depotpartId", depotpartId);
        paramterMap.put("owner", ower);
        paramterMap.put("beginDate", TimeTools.getDateShortString( -180));
        paramterMap.put("endDate", TimeTools.now_short());

        Object count = getIbatisDaoSupport().queryForObject(
            "OutDAO.sumNotEndProductInInByStorageRelation2", paramterMap);

        if (count == null)
        {
            return 0;
        }

        // 负数
        return -(Integer)count;
    }    
    
    public Integer sumNotEndProductInOutByStorageRelation(String productId, String depotpartId,
                                                          String priceKey, String ower)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("productId", productId);
        paramterMap.put("depotpartId", depotpartId);
        paramterMap.put("costPriceKey", priceKey);
        paramterMap.put("owner", ower);
        paramterMap.put("beginDate", TimeTools.getDateShortString( -180));
        paramterMap.put("endDate", TimeTools.now_short());

        Object count = getIbatisDaoSupport().queryForObject(
            "OutDAO.sumNotEndProductInOutByStorageRelation", paramterMap);

        if (count == null)
        {
            return 0;
        }

        return (Integer)count;
    }
    
    public Integer sumNotEndProductInOutByStorageRelation2(String productId, String depotpartId,
            String ower)
	{

        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("productId", productId);
        paramterMap.put("depotpartId", depotpartId);
        paramterMap.put("owner", ower);
        paramterMap.put("beginDate", TimeTools.getDateShortString( -180));
        paramterMap.put("endDate", TimeTools.now_short());

        Object count = getIbatisDaoSupport().queryForObject(
            "OutDAO.sumNotEndProductInOutByStorageRelation2", paramterMap);

        if (count == null)
        {
            return 0;
        }

        return (Integer)count;
    
	}

    public List<BaseBean> queryInwayOut()
    {
        String sql = "select t1.* from t_center_base t1, t_center_out t2 "
                     + "where t1.outid = t2.fullid and t2.inway = 1 and t2.status in (3, 4) and t2.type = 1 and t2.outType = 1 and t2.outTime > ?";

        return this.jdbcOperation.queryForListBySql(sql, BaseBean.class, "2011-04-01");
    }

    public boolean updataInWay(String fullId, int inway)
    {
        jdbcOperation.updateField("inway", inway, fullId, this.claz);

        return true;
    }
    
    public boolean updateEmergency(String fullId, int emergency)
    {
        jdbcOperation.updateField("emergency", emergency, fullId, this.claz);

        return true;
    }

    public boolean updataBadDebtsCheckStatus(String fullId, int badDebtsCheckStatus)
    {
        jdbcOperation.updateField("badDebtsCheckStatus", badDebtsCheckStatus, fullId, this.claz);

        return true;
    }

    public boolean updateCurcredit(String fullId, double curcredit)
    {
        jdbcOperation.updateField("curcredit", curcredit, fullId, this.claz);

        return true;
    }

    public boolean updateManagerId(String fullId, String managerId)
    {
        jdbcOperation.updateField("managerId", managerId, fullId, this.claz);

        return true;
    }

    public boolean updateVtypeFullId(String fullId, String vtypeFullId)
    {
        jdbcOperation.updateField("vtypeFullId", vtypeFullId, fullId, this.claz);

        return true;
    }

    public boolean updateHadPay(String fullId, double hadPay)
    {
        jdbcOperation.updateField("hadPay", hadPay, fullId, this.claz);

        return true;
    }

    public boolean updateDescription(String fullId, String description)
    {
        jdbcOperation.updateField("description", description, fullId, this.claz);

        return true;
    }

    public boolean updateChangeTime(String fullId, String changeTime)
    {
        jdbcOperation.updateField("changeTime", changeTime, fullId, this.claz);

        return true;
    }

    public boolean updateInvoice(String fullId, String invoiceId)
    {
        String sql = BeanTools.getUpdateHead(claz)
                     + "set invoiceId = ?, hasInvoice = ? where fullid = ?";

        jdbcOperation.update(sql, invoiceId, OutConstant.HASINVOICE_YES, fullId);

        return true;
    }

    public boolean updateInvoiceStatus(String fullId, double invoiceMoney, int invoiceStatus)
    {
        String sql = BeanTools.getUpdateHead(claz)
                     + "set invoiceMoney = ?, invoiceStatus = ? where fullid = ?";

        jdbcOperation.update(sql, invoiceMoney, invoiceStatus, fullId);

        return true;
    }

    public boolean updateOutReserve(String fullId, int reserve4, String reserve6)
    {
        jdbcOperation.updateField("reserve2", reserve4, fullId, this.claz);

        jdbcOperation.updateField("reserve6", reserve6, fullId, this.claz);

        return true;
    }

    public boolean updateStaffcredit(String fullId, double staffcredit)
    {
        jdbcOperation.updateField("staffcredit", staffcredit, fullId, this.claz);

        return true;
    }

    public boolean updateManagercredit(String fullId, String managerId, double managercredit)
    {
        jdbcOperation.updateField("managercredit", managercredit, fullId, this.claz);

        jdbcOperation.updateField("managerId", managerId, fullId, this.claz);

        return true;
    }

    /**
     * 在out里面统计客户的使用
     * 
     * @param id
     * @return
     */
    public int countCustomerInOut(String id)
    {
        return this.jdbcOperation.queryForInt(
            BeanTools.getCountHead(claz) + "where customerId = ?", id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.sail.dao.OutDAO#findRealOut(java.lang.String)
     */
    public OutBean findRealOut(String fullId)
    {
        Connection connection = null;

        PreparedStatement prepareStatement = null;

        ResultSet rst = null;

        try
        {
            connection = this.jdbcOperation.getDataSource().getConnection();

            prepareStatement = connection
                .prepareStatement("select fullid, status, reserve3, type , outType from t_center_out where fullid = '"
                                  + fullId + "'");

            rst = prepareStatement.executeQuery();

            rst.next();

            OutBean out = new OutBean();

            out.setFullId(rst.getString("fullid"));

            out.setStatus(rst.getInt("status"));

            out.setReserve3(rst.getInt("Reserve3"));

            out.setType(rst.getInt("type"));

            out.setOutType(rst.getInt("outType"));

            return out;
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return null;
        }
        finally
        {
            if (connection != null)
            {
                try
                {
                    connection.close();
                }
                catch (SQLException e)
                {
                }
            }

            if (prepareStatement != null)
            {
                try
                {
                    prepareStatement.close();
                }
                catch (SQLException e)
                {
                }
            }

            if (rst != null)
            {
                try
                {
                    rst.close();
                }
                catch (SQLException e)
                {
                }
            }
        }
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

    public double sumOutBackValue(String fullId)
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("OutBean.refOutFullId", "=", fullId);

        con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

        con.addCondition("and OutBean.status in (3, 4)");

        con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_IN_OUTBACK);

        List<OutBean> refList = this.queryEntityBeansByCondition(con);

        double backTotal = 0.0d;

        for (OutBean outBean : refList)
        {
            backTotal += outBean.getTotal();
        }

        return backTotal;
    }

    /**
     * 是否供应商在out里面被引用
     * 
     * @param providerId
     * @return
     */
    public int countProviderInOut(String providerId)
    {
        String sql = "select count(1) from t_center_out where type = 1 and customerId = ?";

        return this.jdbcOperation.queryForInt(sql, providerId);
    }
    
    /**
     * 
     * @param fullId
     * @param outId
     * @return
     */
    public boolean modifyRefBindOutId(String fullId, String outId, boolean flag)
    {
        if (flag)
        {
            String sql = BeanTools.getUpdateHead(claz)
            + "set refBindOutId = ?, promStatus = 1 where fullid = ?";

            jdbcOperation.update(sql, outId, fullId);
        }else
        {
            String sql = BeanTools.getUpdateHead(claz)
            + "set refBindOutId = ?, promStatus = -1, promValue = 0, eventId = '' where fullid = ?";

            jdbcOperation.update(sql, outId, fullId); 
        }

        
        return true;
    }

    @Override
    public boolean updatePromValue(String fullId, double promValue) 
    {
        String sql = BeanTools.getUpdateHead(claz)
        + "set promValue = promValue - " + promValue + " where fullid = ?";

        jdbcOperation.update(sql, fullId);
        
        return true;
    }

    /**
     * 其它入库（含对冲单） 
     * {@inheritDoc}
     */
    public double sumOutForceBackValue(String fullId) 
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("OutBean.refOutFullId", "=", fullId);

        con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

        con.addCondition("and OutBean.status in (3, 4)");

        con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_IN_OTHER);
        
//        con.addCondition("OutBean.reserve8", "<>", "1");

        List<OutBean> refList = this.queryEntityBeansByCondition(con);

        double backTotal = 0.0d;

        for (OutBean outBean : refList)
        {
            backTotal += outBean.getTotal();
        }

        return backTotal;
    }

    /**
     * 不区分状态的退货之和
     * @param fullId
     * @return
     */
    public double sumOutBackValueIgnoreStatus(String fullId)
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("OutBean.refOutFullId", "=", fullId);

        con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

//        con.addCondition("and OutBean.status in (3, 4)");

        con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_IN_OUTBACK);

        List<OutBean> refList = this.queryEntityBeansByCondition(con);

        double backTotal = 0.0d;

        for (OutBean outBean : refList)
        {
            backTotal += outBean.getTotal();
        }

        return backTotal;
    }
    
    public List<String> queryDistinctStafferId(String beginDate, String endDate, int type)
    {
        String sql = "";
        
        if (type == 0)
        {
            sql = "select distinct(t1.stafferId) as stafferId from T_CENTER_OUT t1 "
                + "where t1.outTime >= ? and t1.outTime <= ? " ;
        }
        else
        {
            // 增量 - 当天
            sql = "select distinct(t1.stafferId) as stafferId from T_CENTER_OUT t1 "
                + "where t1.lastModified >= ? and t1.lastModified <= ? " ;
            
            beginDate = endDate;
        }

        final List<String> result = new LinkedList<String>();

        this.jdbcOperation.query(sql, new Object[] {beginDate, endDate},
            new RowCallbackHandler()
            {

                public void processRow(ResultSet rst)
                    throws SQLException
                {
                    String stafferId = rst.getString("stafferId");
                    
                    if (!StringTools.isNullOrNone(stafferId))
                        result.add(stafferId);
                }
            });

        return result;
    
    }

    public boolean updateTotal(String fullId, double total) 
    {
        jdbcOperation.updateField("total", total, fullId, this.claz);

        return true;
    }
    
    public boolean updateDutyId(String fullId, String dutyId)
    {
        String sql = "update t_center_out set dutyId = ? where fullid = ?";

        int i = jdbcOperation.update(sql, dutyId, fullId);

        return i != 0;
    }
    
    public boolean updateFeedBackVisit(String fullId, int feedBackVisit)
    {
        String sql = "update t_center_out set feedBackVisit = ? where fullid = ?";

        int i = jdbcOperation.update(sql, feedBackVisit, fullId);

        return i != 0;
    }
    
    public boolean updateFeedBackCheck(String fullId, int feedBackCheck)
    {
        String sql = "update t_center_out set feedBackCheck = ? where fullid = ?";

        int i = jdbcOperation.update(sql, feedBackCheck, fullId);

        return i != 0;
    }


	public boolean updateRebate(String fullId, int rebate)
	{
        String sql = "update t_center_out set hasRebate = ? where fullid = ?";

        int i = jdbcOperation.update(sql, rebate, fullId);

        return i != 0;
	}
	
	public List<OutBean> queryOutByOneCondition(ConditionParse con)
	{
		String sql = "select distinct OutBean.* from T_CENTER_BASE BaseBean, t_center_out OutBean where BaseBean.outid = OutBean.fullid " + con.toString();
		
		return this.jdbcOperation.queryForListBySql(sql, claz);
	}


	@Override
	public boolean updatePayInvoiceData(String fullId, int piType, int piMtype,
			String piDutyId, int piStatus)
	{
        String sql = BeanTools.getUpdateHead(claz)
        				+ "set piType = ?, piMtype = ?, piDutyId = ?, piStatus = ? where fullid = ?";

		jdbcOperation.update(sql, piType, piMtype, piDutyId, piStatus, fullId);
		
		return true;
	}

	@Override
	public boolean initPayInvoiceData(String fullId)
	{
		 String sql = BeanTools.getUpdateHead(claz)
						+ "set piType = ?, piMtype = ?, piDutyId = ?, piStatus = ? where fullid = ?";

		 jdbcOperation.update(sql, -1, -1, "", -1, fullId);
		
		return true;
	}
	
	public boolean updatePayInvoiceStatus(String fullId, int piStatus)
	{
        String sql = BeanTools.getUpdateHead(claz)
        				+ "set piStatus = ? where fullid = ?";

		jdbcOperation.update(sql, piStatus, fullId);
		
		return true;
	}

	@Override
	public List<ConfirmInsWrap> queryCanConfirmIn(ConditionParse con)
	{
		String sql = "select fullId, outtype, invoiceId, '' as origId, customerName, (total - hasConfirmInsMoney) as mayConfirmMoney " +
				" from t_center_out OutBean " + con.toString();
		
		return this.jdbcOperation.queryObjectsBySql(sql).list(ConfirmInsWrap.class);
	}
	
	@Override
	public boolean updateHasConfirm(String fullId, int hasConfirm)
	{
        String sql = BeanTools.getUpdateHead(claz)
						+ "set hasConfirm = ? where fullid = ?";

		jdbcOperation.update(sql, hasConfirm, fullId);
		
		return true;
	}


	@Override
	public boolean updateHasConfirmMoney(String fullId, double confirmMoney)
	{
        String sql = BeanTools.getUpdateHead(claz)
						+ "set hasConfirmInsMoney = ? where fullid = ?";

		jdbcOperation.update(sql, confirmMoney, fullId);
		
		return true;
	}


	/* (non-Javadoc)
	 * @see com.china.center.oa.sail.dao.OutDAO#updateLocation(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateLocation(String fullId, String location) {

        jdbcOperation.updateField("location", location, fullId, this.claz);

        return true;
	}
}

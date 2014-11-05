/**
 * File Name: InvoiceinsDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-1<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.finance.bean.InvoiceinsBean;
import com.china.center.oa.finance.dao.InvoiceinsDAO;
import com.china.center.oa.finance.vo.InvoiceinsVO;
import com.china.center.tools.StringTools;


/**
 * InvoiceinsDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-1-1
 * @see InvoiceinsDAOImpl
 * @since 3.0
 */
public class InvoiceinsDAOImpl extends BaseDAO<InvoiceinsBean, InvoiceinsVO> implements InvoiceinsDAO
{

	/* (non-Javadoc)
	 * @see com.china.center.oa.finance.dao.InvoiceinsDAO#countInvoiceinsByConstion(com.china.center.jdbc.util.ConditionParse)
	 */
	@Override
	public int countInvoiceinsByConstion(ConditionParse condition) {
		return jdbcOperation
	            .queryObjectsBySql(getLastQuerySelfSql(condition))
	            .getCount();
	}

	/* (non-Javadoc)
	 * @see com.china.center.oa.finance.dao.InvoiceinsDAO#queryInvoiceinsByConstion(com.china.center.jdbc.util.ConditionParse, com.china.center.jdbc.util.PageSeparate)
	 */
	@Override
	public List<InvoiceinsVO> queryInvoiceinsByConstion(
			ConditionParse condition, PageSeparate page) {
		return jdbcOperation.queryObjectsBySqlAndPageSeparate(getLastQuerySelfSql(condition), page, InvoiceinsVO.class);
	}
	
	private String getLastQuerySelfSql(ConditionParse condition)
    {
        ConditionParse newConditionParse = new ConditionParse();

        newConditionParse.setCondition(condition.toString());

        newConditionParse.removeWhereStr();

        if (StringTools.isNullOrNone(newConditionParse.toString()))
        {
            newConditionParse.addString("1 = 1");
        }

        return getQuerySelfSql() + " and " + newConditionParse.toString();
    }
	
	private String getQuerySelfSql()
    {
        String sql = "SELECT distinct InvoiceBean.NAME AS invoiceName,DutyBean.NAME AS dutyName,StafferBean.NAME AS stafferName,StafferBean2.NAME AS processName,UnitViewBean.NAME AS customerName,InvoiceBean.VAL AS val,ProvinceBean.NAME AS provinceName,CityBean.NAME AS cityName,AreaBean.NAME AS areaName,e1.NAME AS transportName1,e2.NAME AS transportName2,InvoiceinsBean.ID,InvoiceinsBean.INVOICEID,InvoiceinsBean.STATUS,InvoiceinsBean.TYPE,InvoiceinsBean.STYPE,InvoiceinsBean.MTYPE,InvoiceinsBean.VTYPE,InvoiceinsBean.DUTYID,InvoiceinsBean.UNIT,InvoiceinsBean.REVEIVE,InvoiceinsBean.LOCATIONID,InvoiceinsBean.CUSTOMERID,InvoiceinsBean.MONEYS,InvoiceinsBean.INVOICEDATE,InvoiceinsBean.STAFFERID,InvoiceinsBean.PROCESSER,InvoiceinsBean.LOGTIME,InvoiceinsBean.REFIDS,InvoiceinsBean.DESCRIPTION,InvoiceinsBean.CHECKS,InvoiceinsBean.CHECKREFID,InvoiceinsBean.CHECKSTATUS,InvoiceinsBean.HEADTYPE,InvoiceinsBean.HEADCONTENT,InvoiceinsBean.OPERATOR,InvoiceinsBean.OPERATORNAME,InvoiceinsBean.PAYCONFIRMSTATUS,InvoiceinsBean.INVOICECONFIRMSTATUS,InvoiceinsBean.OTYPE,InvoiceinsBean.REFID,InvoiceinsBean.INSAMOUNT,InvoiceinsBean.FILLTYPE,InvoiceinsBean.SHIPPING,InvoiceinsBean.TRANSPORT1,InvoiceinsBean.TRANSPORT2,InvoiceinsBean.PROVINCEID,InvoiceinsBean.CITYID,InvoiceinsBean.AREAID,InvoiceinsBean.ADDRESS,InvoiceinsBean.RECEIVER,InvoiceinsBean.MOBILE,InvoiceinsBean.TELEPHONE,InvoiceinsBean.EXPRESSPAY,InvoiceinsBean.TRANSPORTPAY"
        		+ " FROM T_CENTER_INVOICEINS InvoiceinsBean LEFT OUTER JOIN T_CENTER_EXPRESS e1 ON (InvoiceinsBean.transport1 = e1.id) LEFT OUTER JOIN T_CENTER_EXPRESS e2 ON (InvoiceinsBean.transport2 = e2.id) LEFT OUTER JOIN T_CENTER_UNIT UnitViewBean ON (InvoiceinsBean.customerId = UnitViewBean.id) LEFT OUTER JOIN T_CENTER_OASTAFFER StafferBean ON (InvoiceinsBean.stafferId = StafferBean.id) LEFT OUTER JOIN T_CENTER_CITY CityBean ON (InvoiceinsBean.cityId = CityBean.id) LEFT OUTER JOIN T_CENTER_PROVINCE ProvinceBean ON (InvoiceinsBean.provinceId = ProvinceBean.id) LEFT OUTER JOIN T_CENTER_AREA AreaBean ON (InvoiceinsBean.areaId = AreaBean.id) LEFT OUTER JOIN T_CENTER_OASTAFFER StafferBean2 ON (InvoiceinsBean.processer = StafferBean2.id) LEFT OUTER JOIN T_CENTER_VS_INVOICENUM InsVSInvoiceNumBean ON (InvoiceinsBean.id = InsVSInvoiceNumBean.insid) LEFT OUTER JOIN T_CENTER_INVOICE InvoiceBean ON (InvoiceinsBean.invoiceid = InvoiceBean.id),T_CENTER_DUTYENTITY DutyBean "
        		+ " WHERE InvoiceinsBean.dutyId = DutyBean.id";
        
        return sql;
    }
}

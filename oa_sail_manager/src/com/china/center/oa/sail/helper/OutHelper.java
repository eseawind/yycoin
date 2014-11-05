/*
 * File Name: OutBeanHelper.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-8-14
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.helper;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.china.center.common.taglib.DefinedCommon;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;


/**
 * @author ZHUZHU
 * @version 2007-8-14
 * @see
 * @since 1.0
 */
public abstract class OutHelper
{
    public static String createTable(List<BaseBean> list, double tatol)
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<table width='100%' border='0' cellspacing='1'>");
        buffer.append("<tr align='center' class='content0'>");
        buffer.append("<td width='20%' align='center'>品名</td>");
        buffer.append("<td width='5%' align='center'>单位</td>");
        buffer.append("<td width='10%' align='center'>数量</td>");
        buffer.append("<td width='15%' align='center'>单价</td>");
        buffer.append("<td width='20%' align='left'>金额(总计:<span id='total'>"
                      + MathTools.formatNum(tatol) + "</span>)</td>");
        buffer.append("<td width='25%' align='center'>成本</td></tr>");

        int index = 0;
        String cls = null;
        for (BaseBean bean : list)
        {
            if (index % 2 == 0)
            {
                cls = "content1";
            }
            else
            {
                cls = "content2";
            }

            buffer.append("<tr class='" + cls + "'>");

            buffer.append("<td width='20%' align='center'>"
                          + StringTools.getLineString(bean.getProductName()) + "</td>");
            buffer.append("<td width='5%' align='center'>" + bean.getUnit() + "</td>");
            buffer.append("<td width='10%' align='center'>" + bean.getAmount() + "</td>");
            buffer.append(" <td width='15%' align='center'>" + MathTools.formatNum(bean.getPrice())
                          + "</td>");
            buffer.append("<td width='15%' align='center'>" + MathTools.formatNum(bean.getValue())
                          + "</td>");
            buffer.append("<td width='25%' align='center'>"
                          + MathTools.formatNum(bean.getCostPrice()) + "</td>");
            index++ ;
        }

        buffer.append("</table>");

        return StringTools.getLineString(buffer.toString());
    }

    public static String getStatus(int i)
    {
        return getStatus(i, true);
    }

    public static String getStatus2(int i)
    {
        return DefinedCommon.getValue("buyStatus", i);
    }

    /**
     * getStatus
     * 
     * @param i
     * @param color
     * @return
     */
    public static String getStatus(int i, boolean color)
    {
        return DefinedCommon.getValue("outStatus", i);
    }

    public static boolean canDelete(OutBean outBean)
    {
        int status = outBean.getStatus();

        if (status == OutConstant.STATUS_SAVE || status == OutConstant.STATUS_REJECT)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 是否可以fee操作
     * 
     * @param outBean
     * @return
     */
    public static boolean canFeeOpration(OutBean outBean)
    {
        int status = outBean.getStatus();

        if (status == OutConstant.STATUS_SAVE || status == OutConstant.STATUS_REJECT)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public static boolean isSailEnd(OutBean outBean)
    {
        int status = outBean.getStatus();

        if (status == OutConstant.STATUS_PASS || status == OutConstant.STATUS_SEC_PASS)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean canSubmit(OutBean outBean)
    {
        if (outBean.getStatus() != OutConstant.STATUS_SAVE
            && outBean.getStatus() != OutConstant.STATUS_REJECT
            && outBean.getStatus() != OutConstant.STATUS_LOCATION_MANAGER_CHECK)
        {
            return false;
        }

        return true;
    }

    public static boolean canReject(OutBean outBean)
    {
        int status = outBean.getStatus();

        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
        {
            if (status == OutConstant.STATUS_SAVE || status == OutConstant.STATUS_REJECT
                || status == OutConstant.STATUS_PASS || status == OutConstant.STATUS_SEC_PASS)
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL)
        {
            if (outBean.getStatus() == OutConstant.OUT_STATUS_SECOND_PASS)
            {
                return true;
            }

            if (outBean.getStatus() == OutConstant.STATUS_LOCATION_MANAGER_CHECK)
            {
                return true;
            }

            if (outBean.getStatus() == OutConstant.STATUS_CEO_CHECK)
            {
                return true;
            }

            if (outBean.getStatus() == OutConstant.STATUS_CHAIRMA_CHECK)
            {
                return true;
            }

            // 调动分为两个单据 一个是源头的调出入库单 一个是目的的调入入库单
            // 源头调出后状态处于提交态,此时可以驳回的
            if ( (outBean.getStatus() == OutConstant.STATUS_PASS || outBean.getStatus() == OutConstant.STATUS_SEC_PASS)
                && outBean.getType() == OutConstant.OUTTYPE_IN_MOVEOUT)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        return false;
    }

    /**
     * 是否调入
     * 
     * @param outBean
     * @return
     */
    public static boolean isMoveIn(OutBean outBean)
    {
        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
            && outBean.getOutType() == OutConstant.OUTTYPE_IN_MOVEOUT
            && outBean.getReserve1() == OutConstant.MOVEOUT_IN)
        {
            return true;
        }

        return false;
    }

    /**
     * 是否是管理销售普通产品
     * 
     * @param outBean
     * @return
     */
    public static boolean isManagerSail(OutBean outBean)
    {
        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
            && !PublicConstant.DEFAULR_DUTY_ID.equals(outBean.getDutyId())
            && outBean.getPmtype() == PublicConstant.MANAGER_TYPE_COMMON)
        {
            return true;
        }

        return false;
    }

    /**
     * 调出回滚
     * 
     * @param outBean
     * @return
     */
    public static boolean isMoveRollBack(OutBean outBean)
    {
        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
            && outBean.getOutType() == OutConstant.OUTTYPE_IN_MOVEOUT
            && outBean.getReserve1() == OutConstant.MOVEOUT_ROLLBACK)
        {
            return true;
        }

        return false;
    }

    /**
     * getOutType
     * 
     * @param outBean
     * @return
     */
    public static String getOutType(OutBean outBean)
    {
        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
        {
            return DefinedCommon.getValue("outType_out", outBean.getOutType());
        }

        return DefinedCommon.getValue("outType_in", outBean.getOutType());
    }

    public static String getOutStatus(OutBean outBean)
    {
        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
        {
            return DefinedCommon.getValue("outStatus", outBean.getStatus());
        }

        return DefinedCommon.getValue("buyStatus", outBean.getStatus());
    }

    /**
     * 是否调出
     * 
     * @param outBean
     * @return
     */
    public static boolean isMoveOut(OutBean outBean)
    {
        if ((outBean.getType() == OutConstant.OUT_TYPE_INBILL
            && outBean.getOutType() == OutConstant.OUTTYPE_IN_MOVEOUT
            && outBean.getReserve1() == OutConstant.MOVEOUT_OUT)||(outBean.getOutType() == OutConstant.OUTTYPE_OUT_APPLY))
        {
            return true;
        }

        return false;
    }

    /**
     * 归类重复的base
     * 
     * @param baseList
     * @return
     */
    public static List<BaseBean> trimBaseList(List<BaseBean> baseList)
    {
        // 由于是默认仓,所以存在重复产品的行为,这里需要合并重复的
        Map<String, BaseBean> mapBean = new HashMap<String, BaseBean>();

        for (BaseBean each : baseList)
        {
            String key = each.getProductId() + "-" + each.getCostPriceKey() + "-" + each.getOwner()
                         + "-" + each.getDepotpartId();

            BaseBean baseBean = mapBean.get(key);

            if (baseBean == null)
            {
                mapBean.put(key, each);
            }
            else
            {
                baseBean.setAmount(each.getAmount() + baseBean.getAmount());

                baseBean.setValue(each.getValue() + baseBean.getValue());
            }
        }

        Collection<BaseBean> values = mapBean.values();

        List<BaseBean> lastList = new ArrayList<BaseBean>();

        for (BaseBean baseBean : values)
        {
            lastList.add(baseBean);
        }
        return lastList;
    }

    public static String getKey2(BaseBean each)
    {

         String key = each.getProductId() + "-" + each.getCostPriceKey() + "-" + each.getOwner(); 

        return key;
    }

    /**
     * 合并价格、产品、公卖的trim(存在不同仓库同价格的出售)
     * 
     * @param baseList
     * @return
     */
    public static List<BaseBean> trimBaseList2(List<BaseBean> baseList)
    {
        // 由于是默认仓,所以存在重复产品的行为,这里需要合并重复的
        Map<String, BaseBean> mapBean = new HashMap<String, BaseBean>();

        for (BaseBean each : baseList)
        {
            String key = getKey2(each);

            BaseBean baseBean = mapBean.get(key);

            if (baseBean == null)
            {
                mapBean.put(key, each);
            }
            else
            {
                baseBean.setAmount(each.getAmount() + baseBean.getAmount());

                baseBean.setValue(each.getValue() + baseBean.getValue());
            }
        }

        Collection<BaseBean> values = mapBean.values();

        List<BaseBean> lastList = new ArrayList<BaseBean>();

        for (BaseBean baseBean : values)
        {
            lastList.add(baseBean);
        }
        return lastList;
    }

    public static BaseBean findBase(List<BaseBean> baseList, BaseBean base)
    {
        for (BaseBean baseBean : baseList)
        {
            if (baseBean.equals(base))
            {
                return baseBean;
            }
        }

        return null;
    }
    
    public static List<BaseBean> trimBaseList3(List<BaseBean> baseList)
    {
        // 由于是默认仓,所以存在重复产品的行为,这里需要合并重复的
        Map<String, BaseBean> mapBean = new HashMap<String, BaseBean>();

        for (BaseBean each : baseList)
        {
            String key = getKey3(each);

            BaseBean baseBean = mapBean.get(key);

            if (baseBean == null)
            {
                mapBean.put(key, each);
            }
            else
            {
                baseBean.setAmount(each.getAmount() + baseBean.getAmount());

                baseBean.setValue(each.getValue() + baseBean.getValue());
            }
        }

        Collection<BaseBean> values = mapBean.values();

        List<BaseBean> lastList = new ArrayList<BaseBean>();

        for (BaseBean baseBean : values)
        {
            lastList.add(baseBean);
        }
        return lastList;
    }
    
    public static String getKey3(BaseBean each)
    {

         // 其它入库关联原单时，description 用于存放原单行项目的id 
         String key = each.getDescription() ;

        return key;
    }
    
    
    /**
     * // 专为新的开票申请增加方法
     */
    public static List<BaseBean> trimBaseList4(List<BaseBean> baseList)
    {
        // 由于是默认仓,所以存在重复产品的行为,这里需要合并重复的
        Map<String, BaseBean> mapBean = new HashMap<String, BaseBean>();

        for (BaseBean each : baseList)
        {
            String key = getKey2(each);

            BaseBean baseBean = mapBean.get(key);

            if (baseBean == null)
            {
            	// 借用description
            	each.setDescription(each.getId());
            	
                mapBean.put(key, each);
            }
            else
            {
                baseBean.setAmount(each.getAmount() + baseBean.getAmount());

                baseBean.setValue(each.getValue() + baseBean.getValue());
                
                baseBean.setDescription(baseBean.getDescription()+";"+each.getId());
            }
        }

        Collection<BaseBean> values = mapBean.values();

        List<BaseBean> lastList = new ArrayList<BaseBean>();

        for (BaseBean baseBean : values)
        {
            lastList.add(baseBean);
        }
        return lastList;
    }
    
    /**
     * 从3个整数中取一个最小的值
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static int compare3IntMin(int a, int b, int c)
    {
    	int min = Math.min(Math.min(a, b), c);
    	
    	return min;
    }
    
    /**
     * 获取发货方式
     * @return
     */
    public static String getShippingName(int shipping)
    {
    	if (shipping == 0)
    	{
    		return "自提";
    	}
    	else if (shipping == 1)
    	{
    		return "公司";
    	}
    	else if (shipping == 2)
    	{
    		return "第三方快递";
    	}
    	else if (shipping == 3)
    	{
    		return "第三方货运";
    	}
    	else if (shipping == 4)
    	{
    		return "第三方快递+货运";
    	}
    	else
    	{
    		return "其它";
    	}
    }
    
    public static String getSailHead(int type, int outType)
    {
    	String head = "SO";
    	
    	if (type == OutConstant.OUT_TYPE_OUTBILL)
    	{
    		if (outType == OutConstant.OUTTYPE_OUT_COMMON)
    		{
    			head = "SO";
    		}
    		else if (outType == OutConstant.OUTTYPE_OUT_SWATCH || outType == OutConstant.OUTTYPE_OUT_SHOWSWATCH)
    		{
    			head = "LY";
    		}
    		else if (outType == OutConstant.OUTTYPE_OUT_CONSIGN)
    		{
    			head = "DX";
    		}
    		else if (outType == OutConstant.OUTTYPE_OUT_PRESENT)
    		{
    			head = "ZS";
    		}
    		else if (outType == OutConstant.OUTTYPE_OUT_SHOW)
    		{
    			head = "XZ";
    		}
    		else
    		{
    			head = "SO";
    		}
    	}
    	
    	if (type == OutConstant.OUT_TYPE_INBILL)
    	{
    		if (outType == OutConstant.OUTTYPE_IN_MOVEOUT || outType == OutConstant.OUTTYPE_OUT_APPLY)
    		{
    			head = "DB";
    		}
    		else if (outType == OutConstant.OUTTYPE_IN_DROP)
    		{
    			head = "BF";
    		}
    		else if (outType == OutConstant.OUTTYPE_IN_ERRORP)
    		{
    			head = "JZ";
    		}
    		else if (outType == OutConstant.OUTTYPE_IN_STOCK)
    		{
    			head = "CT";
    		}
    		else if (outType == OutConstant.OUTTYPE_IN_OTHER)
    		{
    			head = "QT";
    		}
    		else if (outType == OutConstant.OUTTYPE_IN_OUTBACK)
    		{
    			head = "XT";
    		}
    		else if (outType == OutConstant.OUTTYPE_IN_SWATCH)
    		{
    			head = "LT";
    		}
    		else if (outType == OutConstant.OUTTYPE_IN_PRESENT) {
    			head = "ZT";
    		}
    		else
    		{
    			head = "SO";
    		}
    	}
    	
    	return head;
    }
}

/**
 * File Name: FinanceHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.helper;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.bean.FinanceMonthBean;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.constanst.TaxItemConstanst;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;


/**
 * FinanceHelper
 * 
 * @author ZHUZHU
 * @version 2011-6-12
 * @see FinanceHelper
 * @since 3.0
 */
public abstract class FinanceHelper
{
    /**
     * 字符串数字转换成long(准确到微分,就是小数点后四位)
     * 
     * @param val
     * @return
     */
    public static long doubleToLong(String val)
    {
        // 先格式转成double
        double parseDouble = MathTools.parseDouble(val);

        return Math.round(MathTools.parseDouble(formatNum2(parseDouble))
                          * TaxConstanst.DOUBLE_TO_INT);
    }

    /**
     * 数字转换成long(准确到微分,就是小数点后四位)
     * 
     * @param val
     * @return
     */
    public static long doubleToLong(double val)
    {
        String formatNum2 = formatNum2(val);

        return (long)Math.round(MathTools.parseDouble(formatNum2) * TaxConstanst.DOUBLE_TO_INT);
    }

    public static String formatNum2(double d)
    {
        DecimalFormat df = new DecimalFormat("####0.00");

        String result = df.format(d);

        if (".00".equals(result))
        {
            result = "0" + result;
        }

        return result;
    }

    /**
     * 变成展现的string
     * 
     * @param val
     * @return
     */
    public static String longToString(long val)
    {
        String formatNum = formatNum(longToDouble(val));

        return formatNum;
    }

    public static String longToChineseString(long val)
    {
        return MathTools.hangeToBig(longToDouble(val));
    }

    /**
     * longToDouble
     * 
     * @param val
     * @return
     */
    public static double longToDouble(long val)
    {
        return val / (TaxConstanst.DOUBLE_TO_INT + 0.0d);
    }

    public static String formatNum(double d)
    {
        DecimalFormat df = new DecimalFormat("####,##0.00");

        String result = df.format(d);

        if (".00".equals(result))
        {
            result = "0" + result;
        }

        return result;
    }

    public static void copyTax(TaxBean tax, FinanceItemBean item)
    {
        item.setTaxId(tax.getId());

        item.setTaxId0(tax.getParentId0());
        item.setTaxId1(tax.getParentId1());
        item.setTaxId2(tax.getParentId2());
        item.setTaxId3(tax.getParentId3());
        item.setTaxId4(tax.getParentId4());
        item.setTaxId5(tax.getParentId5());
        item.setTaxId6(tax.getParentId6());
        item.setTaxId7(tax.getParentId7());
        item.setTaxId8(tax.getParentId8());
    }

    public static void copyTax(TaxBean tax, FinanceMonthBean item)
    {
        item.setTaxId(tax.getId());

        item.setTaxId0(tax.getParentId0());
        item.setTaxId1(tax.getParentId1());
        item.setTaxId2(tax.getParentId2());
        item.setTaxId3(tax.getParentId3());
        item.setTaxId4(tax.getParentId4());
        item.setTaxId5(tax.getParentId5());
        item.setTaxId6(tax.getParentId6());
        item.setTaxId7(tax.getParentId7());
        item.setTaxId8(tax.getParentId8());
    }

    /**
     * FinanceBean->FinanceItemBean
     * 
     * @param financeBean
     * @param item
     */
    public static void copyFinanceItem(FinanceBean financeBean, FinanceItemBean item)
    {
        item.setCreateType(financeBean.getCreateType());

        item.setRefId(financeBean.getRefId());

        item.setRefOut(financeBean.getRefOut());

        item.setRefStock(financeBean.getRefStock());

        item.setRefBill(financeBean.getRefBill());

        item.setFinanceDate(financeBean.getFinanceDate());

        item.setType(financeBean.getType());

        item.setDutyId(financeBean.getDutyId());

        item.setLogTime(financeBean.getLogTime());
    }

    public static String createFinanceLink(String id)
    {
        return "<a href='../finance/finance.do?method=findFinance&id=" + id + "'>" + id + "</a>";
    }

    public static String getForwardName(TaxBean taxBean)
    {
        if (taxBean.getForward() == TaxConstanst.TAX_FORWARD_IN)
        {
            return "借";
        }
        else
        {
            return "贷";
        }
    }

    /**
     * 是否是结转的凭证
     * 
     * @param itemList
     * @return
     */
    public static boolean isTurnFinance(List<FinanceItemBean> itemList)
    {
        for (FinanceItemBean financeItemBean : itemList)
        {
            if (financeItemBean.getTaxId().equals(TaxItemConstanst.YEAR_PROFIT))
            {
                return true;
            }
        }

        return false;
    }

    public static List<String>[] parserExpr(String expr)
    {
        expr = expr.trim();

        List<String> listTax = new ArrayList();
        List<String> listOpr = new ArrayList();

        String[] split = expr.split("[+-]");

        for (String each : split)
        {
            if (StringTools.isNullOrNone(each))
            {
                continue;
            }

            listTax.add(each.trim().replaceAll("~", "-"));

            expr = expr.substring(each.length());

            if (expr.length() > 0)
            {
                listOpr.add(String.valueOf(expr.charAt(0)));

                expr = expr.substring(1);
            }
        }

        return new List[] {listTax, listOpr};
    }

    /**
     * 检查销售退库中的促销绑定字段中的第一单子与退库单中关联的原单是否是同一单号.有可能执行促销单退库,也有可能是绑定单退库
     * @param refOutFullId
     * @param refBindOutId
     * @return
     */
    public static String getExecPromOutId(final String refBindOutId)
    {
        String refBind = "";
        
        if (StringTools.isNullOrNone(refBindOutId))
        {
            refBind = ""; 
        }else
        {
            refBind = refBindOutId;    
        }
        
        String [] ref = refBind.split("~");
        
        String execPromOutId = "";
        
        if (ref.length > 0)
            execPromOutId = ref[0];
        
        return execPromOutId;
    }
    
    public static boolean isPromBind(final String refOutFullId, final String refBindOutId)
    {
        String refBind = "";
        
        if (StringTools.isNullOrNone(refBindOutId))
        {
            refBind = ""; 
        }else
        {
            refBind = refBindOutId;
        }
        
        String [] ref = refBind.split("~");
        
        String execPromOutId = "";
        
        if (ref.length > 0)
            execPromOutId = ref[0];
        
        return refOutFullId.equals(execPromOutId);
    }
    
    // public static void main(String[] args)
    // {
    // String expr = "1201+1202+1211+1221+1231+1232+1241+1243+1244-1251+1261+1271-1281+1291";
    //
    // List<String>[] parserExpr = parserExpr(expr);
    //
    // System.out.println(parserExpr[0]);
    // System.out.println(parserExpr[1]);
    // }
}

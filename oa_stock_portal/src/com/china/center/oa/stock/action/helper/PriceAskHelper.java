/*
 * File Name: OutBeanHelper.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-8-14
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.action.helper;


import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.helper.AuthHelper;
import com.china.center.oa.stock.constant.PriceConstant;
import com.china.center.oa.stock.vo.PriceAskProviderBeanVO;


/**
 * @author ZHUZHU
 * @version 2007-8-14
 * @see
 * @since
 */
public abstract class PriceAskHelper
{
    /**
     * createTable
     * 
     * @param list
     * @param user
     * @return
     */
    public static String createTable(List<PriceAskProviderBeanVO> list, User user)
    {
        int type = 0;

        if (AuthHelper.containAuth(user, AuthConstant.STOCK_PRICE_PASS))
        {
            type = 0;
        }
        else
        {
            type = 1;
        }

        return createTable(list, user, type);
    }

    /**
     * createTable
     * 
     * @param list
     * @param user
     * @param type
     *            0:报价员 1:供应商
     * @return
     */
    public static String createTable(List<PriceAskProviderBeanVO> list, User user, int type)
    {
        StringBuffer buffer = new StringBuffer();

        String display = "";

        if (type == 0)
        {
            display = "报价员";
        }
        else
        {
            display = "供应商";
        }

        buffer.append("<table width='100%' border='0' cellspacing='1'>");
        buffer.append("<tr align='center' class='content0'>");
        buffer.append("<td width='30%' align='center'>" + display + "</td>");
        buffer.append("<td width='15%' align='center'>价格</td>");
        buffer.append("<td width='5%' align='center'>数量</td>");
        buffer.append("<td width='15%' align='center'>数量满足</td>");
        buffer.append("<td width='20%' align='center'>备注</td>");
        buffer.append("<td width='30%' align='center'>时间</td>");
        buffer.append("</tr>");

        int index = 0;
        String cls = null;
        for (PriceAskProviderBeanVO bean : list)
        {
            if (index % 2 == 0)
            {
                cls = "content1";
            }
            else
            {
                cls = "content2";
            }

            String str = (bean.getHasAmount() == PriceConstant.HASAMOUNT_OK) ? "满足" : "<font color=red>不满足</font>";

            buffer.append("<tr class='" + cls + "'>");

            String displayName = "";

            if (type == 0)
            {
                displayName = bean.getUserName();
            }
            else
            {
                displayName = bean.getProviderName();
            }

            buffer.append("<td  align='center'>" + displayName + "</td>");
            buffer.append("<td  align='center'>" + bean.getPrice() + "</td>");
            buffer.append("<td  align='center'>" + bean.getSupportAmount() + "</td>");
            buffer.append("<td  align='center'>" + str + "</td>");

            String des = bean.getDescription().replaceAll("\r\n", "");

            buffer.append("<td  align='center'>" + des + "</td>");

            buffer.append("<td  align='center'>" + bean.getLogTime() + "</td>");

            buffer.append("</tr>");

            index++ ;
        }

        buffer.append("</table>");

        return buffer.toString();
    }
}

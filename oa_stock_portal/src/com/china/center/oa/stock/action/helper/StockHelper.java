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

import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.oa.stock.vo.StockItemVO;
import com.china.center.osgi.jsp.ElTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;


/**
 * @author ZHUZHU
 * @version 2007-8-14
 * @see
 * @since
 */
public abstract class StockHelper
{
    public static String createTable(List<StockItemVO> list, int type)
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<table width='100%' border='0' cellspacing='1'>");
        buffer.append("<tr align='center' class='content0'>");
        buffer.append("<td width='30%' align='center'>产品</td>");
        buffer.append("<td width='10%' align='center'>数量</td>");
        buffer.append("<td width='10%' align='center'>报价</td>");
        buffer.append("<td width='10%' align='center'>预期价格</td>");
        buffer.append("<td width='30%' align='center'>供应商</td>");
        buffer.append("<td width='10%' align='center'>合计</td>");

        buffer.append("</tr>");

        int index = 0;
        String cls = null;
        for (StockItemVO bean : list)
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

            buffer.append("<td  align='center'>" + bean.getProductName() + "</td>");
            buffer.append("<td  align='center'>" + bean.getAmount() + "</td>");
            buffer.append("<td  align='center'>" + MathTools.formatNum(bean.getPrice()) + "</td>");
            buffer.append("<td  align='center'>" + MathTools.formatNum(bean.getPrePrice()) + "</td>");

            if (type == 0 || type == 1)
            {
                buffer.append("<td  align='center'></td>");
            }
            else
            {
                buffer.append("<td  align='center'>" + StringTools.print(bean.getProviderName()) + "</td>");
            }
            buffer.append("<td  align='center'>" + MathTools.formatNum(bean.getTotal()) + "</td>");

            buffer.append("</tr>");
            index++ ;
        }

        buffer.append("</table>");

        return buffer.toString();
    }

    public static String getStatus(int i)
    {
        return ElTools.get("stockStatus", i);

    }

    public static FlowLogVO getStockFlowLogVO(FlowLogBean bean)
    {
        FlowLogVO vo = new FlowLogVO();

        if (bean == null)
        {
            return vo;
        }

        BeanUtil.copyProperties(vo, bean);

        if (bean.getOprMode() == PublicConstant.OPRMODE_PASS)
        {
            vo.setOprModeName("通过");
        }

        if (bean.getOprMode() == PublicConstant.OPRMODE_REJECT)
        {
            vo.setOprModeName("驳回");
        }

        vo.setPreStatusName(StockHelper.getStatus(vo.getPreStatus()));

        vo.setAfterStatusName(StockHelper.getStatus(vo.getAfterStatus()));

        return vo;
    }
}

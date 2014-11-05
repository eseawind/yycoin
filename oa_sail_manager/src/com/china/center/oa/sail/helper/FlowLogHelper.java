/*
 * File Name: OutLog.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2008-1-13
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.helper;


import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.tools.BeanUtil;


/**
 * FlowLogHelper
 * 
 * @author ZHUZHU
 * @version 2008-1-13
 * @see
 * @since
 */
public abstract class FlowLogHelper
{
    public static FlowLogVO getOutLogVO(FlowLogBean bean)
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

        vo.setPreStatusName(OutHelper.getStatus(vo.getPreStatus()));

        vo.setAfterStatusName(OutHelper.getStatus(vo.getAfterStatus()));

        return vo;
    }

}

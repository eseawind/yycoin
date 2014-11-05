/**
 * File Name: TokenVSTemplateDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.flow.dao.TokenVSTemplateDAO;
import com.china.center.oa.flow.vo.TokenVSTemplateVO;
import com.china.center.oa.flow.vs.TokenVSTemplateBean;


/**
 * TokenVSTemplateDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see TokenVSTemplateDAOImpl
 * @since 1.0
 */
public class TokenVSTemplateDAOImpl extends BaseDAO<TokenVSTemplateBean, TokenVSTemplateVO> implements TokenVSTemplateDAO
{
    /**
     * queryByFlowId
     * 
     * @param flowId
     * @return
     */
    public List<TokenVSTemplateBean> queryByFlowId(String flowId)
    {
        return this.queryEntityBeansByCondition("where flowid = ?", flowId);
    }
}

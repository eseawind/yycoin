/**
 * File Name: TokenVSTemplateDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.flow.vo.TokenVSTemplateVO;
import com.china.center.oa.flow.vs.TokenVSTemplateBean;


/**
 * TokenVSTemplateDAO
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see TokenVSTemplateDAO
 * @since 1.0
 */
public interface TokenVSTemplateDAO extends DAO<TokenVSTemplateBean, TokenVSTemplateVO>
{
    List<TokenVSTemplateBean> queryByFlowId(String flowId);
}

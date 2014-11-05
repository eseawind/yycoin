/**
 * File Name: TokenVSHanderDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.flow.dao.TokenVSHanderDAO;
import com.china.center.oa.flow.vo.TokenVSHanderVO;
import com.china.center.oa.flow.vs.TokenVSHanderBean;


/**
 * TokenVSHanderDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see TokenVSHanderDAOImpl
 * @since 1.0
 */
public class TokenVSHanderDAOImpl extends BaseDAO<TokenVSHanderBean, TokenVSHanderVO> implements TokenVSHanderDAO
{
    /**
     * queryTokenVSHanderByTokenIdAndType
     * 
     * @param tokenId
     * @param type
     * @return List[TokenVSHanderBean]
     */
    public List<TokenVSHanderBean> queryTokenVSHanderByTokenIdAndType(String tokenId, int type)
    {
        return this.jdbcOperation.queryForList("where tokenId = ? and type = ?", claz, tokenId, type);
    }
}

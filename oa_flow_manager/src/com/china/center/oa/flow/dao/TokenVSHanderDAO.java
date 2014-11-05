/**
 * File Name: TokenVSHanderDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.flow.vo.TokenVSHanderVO;
import com.china.center.oa.flow.vs.TokenVSHanderBean;


/**
 * TokenVSHanderDAO
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see TokenVSHanderDAO
 * @since 1.0
 */
public interface TokenVSHanderDAO extends DAO<TokenVSHanderBean, TokenVSHanderVO>
{
    List<TokenVSHanderBean> queryTokenVSHanderByTokenIdAndType(String tokenId, int type);
}

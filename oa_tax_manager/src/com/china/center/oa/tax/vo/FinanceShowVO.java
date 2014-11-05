/**
 * File Name: FinanceVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.oa.tax.bean.FinanceShowBean;


/**
 * FinanceShowVO
 * 
 * @author ZHUZHU
 * @version 2011-2-6
 * @see FinanceShowVO
 * @since 1.0
 */
@SuppressWarnings("serial")
@Entity(inherit = true)
public class FinanceShowVO extends FinanceShowBean
{
	/**
	 * 
	 */
	public FinanceShowVO()
	{
	}

	/**
	 * @param type
	 */
	public FinanceShowVO(int type)
	{
		super(type);
	}
}

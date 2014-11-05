/**
 * File Name: PriceChangeSrcItemBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.bean;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Table;


/**
 * PriceChangeSrcItemBean
 * 
 * @author ZHUZHU
 * @version 2010-10-4
 * @see PriceChangeNewItemBean
 * @since 1.0
 */
@Entity(inherit = true)
@Table(name = "T_CENTER_PRICE_CHANGE_NITEM")
public class PriceChangeNewItemBean extends AbsPriceChangeItemBean
{

}

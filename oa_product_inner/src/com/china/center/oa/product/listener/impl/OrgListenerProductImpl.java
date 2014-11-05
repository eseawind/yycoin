/**
 * File Name: OrgListenerProductImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.listener.impl;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.oa.product.dao.ProductVSLocationDAO;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.listener.OrgListener;


/**
 * OrgListenerProductImpl
 * 
 * @author ZHUZHU
 * @version 2011-2-14
 * @see OrgListenerProductImpl
 * @since 3.0
 */
public class OrgListenerProductImpl implements OrgListener
{
    private ProductVSLocationDAO productVSLocationDAO = null;

    /**
     * default constructor
     */
    public OrgListenerProductImpl()
    {
    }

    public void onDeleteOrg(User user, PrincipalshipBean org)
        throws MYException
    {
        int count = productVSLocationDAO.countByFK(org.getId(), AnoConstant.FK_FIRST);

        if (count > 0)
        {
            throw new MYException("组织被产品的销售范围引用");
        }
    }

    public String getListenerType()
    {
        return "OrgListener.ProductImpl";
    }

    /**
     * @return the productVSLocationDAO
     */
    public ProductVSLocationDAO getProductVSLocationDAO()
    {
        return productVSLocationDAO;
    }

    /**
     * @param productVSLocationDAO
     *            the productVSLocationDAO to set
     */
    public void setProductVSLocationDAO(ProductVSLocationDAO productVSLocationDAO)
    {
        this.productVSLocationDAO = productVSLocationDAO;
    }

}

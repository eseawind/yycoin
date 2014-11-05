/**
 * File Name: ComposeProductManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager;


import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.ComposeFeeDefinedBean;
import com.china.center.oa.product.bean.ComposeProductBean;
import com.china.center.oa.product.bean.DecomposeProductBean;
import com.china.center.oa.product.listener.ComposeProductListener;
import com.china.center.oa.product.vo.ComposeFeeDefinedVO;
import com.china.center.oa.product.vo.ComposeProductVO;


/**
 * ComposeProductManager
 * 
 * @author ZHUZHU
 * @version 2010-10-2
 * @see ComposeProductManager
 * @since 1.0
 */
public interface ComposeProductManager extends ListenerManager<ComposeProductListener>
{
    /**
     * 合成产品
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addComposeProduct(User user, ComposeProductBean bean)
        throws MYException;

    /**
     * 回滚合成(分解)
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean rollbackComposeProduct(User user, String id)
        throws MYException;

    /**
     * 生产部经理审批
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean passComposeProduct(User user, String id)
        throws MYException;

    /**
     * 运营总监审批
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean lastPassComposeProduct(User user, String id)
        throws MYException;

    /**
     * 驳回合成(就是删除)
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean rejectComposeProduct(User user, String id)
        throws MYException;

    ComposeProductVO findById(String id);

    boolean addComposeFeeDefinedBean(User user, ComposeFeeDefinedBean bean)
        throws MYException;

    boolean updateComposeFeeDefinedBean(User user, ComposeFeeDefinedBean bean)
        throws MYException;

    boolean deleteComposeFeeDefinedBean(User user, String id)
        throws MYException;

    ComposeFeeDefinedVO findComposeFeeDefinedVO(String id)
        throws MYException;
    
    boolean addDecomposeProduct(User user, DecomposeProductBean bean)
    throws MYException;
    
    boolean passDecomposeProduct(User user, String id, String reason)
    throws MYException;
    
    boolean rejectDecomposeProduct(User user, String id, String reason)
    throws MYException;
}

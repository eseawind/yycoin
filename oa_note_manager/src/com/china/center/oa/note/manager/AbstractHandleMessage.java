/**
 * File Name: AbstractHandleMessage.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.note.manager;


import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.china.center.oa.note.bean.ShortMessageTaskBean;
import com.china.center.oa.note.bean.ShortMessageTaskHisBean;
import com.china.center.oa.note.constant.ShortMessageConstant;
import com.china.center.oa.note.dao.ShortMessageTaskDAO;
import com.china.center.oa.note.dao.ShortMessageTaskHisDAO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.ListTools;


/**
 * AbstractHandleMessage
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see AbstractHandleMessage
 * @since 1.0
 */
public abstract class AbstractHandleMessage implements HandleMessage
{
    protected ShortMessageTaskDAO shortMessageTaskDAO = null;

    protected ShortMessageTaskHisDAO shortMessageTaskHisDAO = null;

    protected PlatformTransactionManager transactionManager = null;

    public abstract int getHandleType();

    public abstract void handleMessage(ShortMessageTaskBean task);

    /**
     * moveData
     * 
     * @param shortMessageTaskBean
     */
    protected void moveDataToHis(final ShortMessageTaskBean shortMessageTaskBean)
    {
        TransactionTemplate tran = new TransactionTemplate(transactionManager);

        tran.execute(new TransactionCallback()
        {
            public Object doInTransaction(TransactionStatus arg0)
            {
                ShortMessageTaskHisBean his = new ShortMessageTaskHisBean();

                BeanUtil.copyProperties(his, shortMessageTaskBean);

                his.setStatus(ShortMessageConstant.STATUS_END_COMMON);

                shortMessageTaskDAO.deleteEntityBean(shortMessageTaskBean.getId());

                return shortMessageTaskHisDAO.saveEntityBean(his);
            }

        });
    }

    protected void deleteMsg(final ShortMessageTaskBean task)
    {
        TransactionTemplate tran = new TransactionTemplate(transactionManager);

        tran.execute(new TransactionCallback()
        {
            public Object doInTransaction(TransactionStatus arg0)
            {
                return shortMessageTaskDAO.deleteEntityBean(task.getId());
            }
        });
    }

    protected String[] parserMessage(ShortMessageTaskBean shortMessageTaskBean, int maxLength)
    {
        return ListTools.fill(shortMessageTaskBean.getReceiveMsg().split(":"), maxLength);
    }

    /**
     * @return the shortMessageTaskDAO
     */
    public ShortMessageTaskDAO getShortMessageTaskDAO()
    {
        return shortMessageTaskDAO;
    }

    /**
     * @param shortMessageTaskDAO
     *            the shortMessageTaskDAO to set
     */
    public void setShortMessageTaskDAO(ShortMessageTaskDAO shortMessageTaskDAO)
    {
        this.shortMessageTaskDAO = shortMessageTaskDAO;
    }

    /**
     * @return the shortMessageTaskHisDAO
     */
    public ShortMessageTaskHisDAO getShortMessageTaskHisDAO()
    {
        return shortMessageTaskHisDAO;
    }

    /**
     * @param shortMessageTaskHisDAO
     *            the shortMessageTaskHisDAO to set
     */
    public void setShortMessageTaskHisDAO(ShortMessageTaskHisDAO shortMessageTaskHisDAO)
    {
        this.shortMessageTaskHisDAO = shortMessageTaskHisDAO;
    }

    /**
     * @return the transactionManager
     */
    public PlatformTransactionManager getTransactionManager()
    {
        return transactionManager;
    }

    /**
     * @param transactionManager
     *            the transactionManager to set
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager)
    {
        this.transactionManager = transactionManager;
    }
}

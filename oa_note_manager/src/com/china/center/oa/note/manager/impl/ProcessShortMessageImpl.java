/**
 * File Name: DefaultHandleMessageImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.note.manager.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.note.bean.ShortMessageTaskBean;
import com.china.center.oa.note.bean.ShortMessageTaskHisBean;
import com.china.center.oa.note.constant.ShortMessageConstant;
import com.china.center.oa.note.dao.ShortMessageTaskDAO;
import com.china.center.oa.note.dao.ShortMessageTaskHisDAO;
import com.china.center.oa.note.manager.HandleMessage;
import com.china.center.oa.note.manager.ProcessShortMessage;
import com.china.center.tools.BeanUtil;


/**
 * ProcessShortMessageImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see ProcessShortMessageImpl
 * @since 1.0
 */
public class ProcessShortMessageImpl implements ProcessShortMessage
{
    private final Log _logger = LogFactory.getLog(getClass());

    private Map<Integer, HandleMessage> handleObjectInstanceList = new HashMap();

    private ShortMessageTaskDAO shortMessageTaskDAO = null;

    private ShortMessageTaskHisDAO shortMessageTaskHisDAO = null;

    /**
     * default constructor
     */
    public ProcessShortMessageImpl()
    {
    }

    /**
     * NOTE handle SMS message portal
     */
    public void handleMessage()
    {
        for (HandleMessage eachItem : handleObjectInstanceList.values())
        {
            ConditionParse con = createCondition(eachItem);

            // query near records
            List<ShortMessageTaskBean> smsList = shortMessageTaskDAO.queryEntityBeansByLimit(con, 200);

            for (ShortMessageTaskBean shortMessageTaskBean : smsList)
            {
                try
                {
                    eachItem.handleMessage(shortMessageTaskBean);
                }
                catch (Throwable e)
                {
                    _logger.error(e, e);
                }
            }

            // cancel message
            con = createCondition2(eachItem);

            // query near records
            smsList = shortMessageTaskDAO.queryEntityBeansByLimit(con, 200);

            for (ShortMessageTaskBean shortMessageTaskBean : smsList)
            {
                try
                {
                    eachItem.cancelMessage(shortMessageTaskBean);
                }
                catch (Throwable e)
                {
                    _logger.error(e, e);
                }
            }
        }
    }

    /**
     * moveData
     * 
     * @param shortMessageTaskBean
     */
    public void moveDataToHis(ShortMessageTaskBean shortMessageTaskBean)
    {
        ShortMessageTaskHisBean his = new ShortMessageTaskHisBean();

        BeanUtil.copyProperties(his, shortMessageTaskBean);

        his.setStatus(ShortMessageConstant.STATUS_END_COMMON);

        shortMessageTaskDAO.deleteEntityBean(shortMessageTaskBean.getId());

        shortMessageTaskHisDAO.saveEntityBean(his);
    }

    public void putHandleMessage(HandleMessage handle)
    {
        handleObjectInstanceList.put(handle.getHandleType(), handle);
    }

    public void removeHandleMessage(Integer type)
    {
        handleObjectInstanceList.remove(type);
    }

    /**
     * createCondition
     * 
     * @param eachItem
     * @return
     */
    private ConditionParse createCondition(HandleMessage eachItem)
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addIntCondition("type", "=", eachItem.getHandleType());

        con.addIntCondition("status", "=", ShortMessageConstant.STATUS_RECEIVE_SUCCESS);

        con.addIntCondition("mtype", "=", ShortMessageConstant.MTYPE_ONLY_SEND_RECEIVE);

        return con;
    }

    /**
     * createCondition
     * 
     * @param eachItem
     * @return
     */
    private ConditionParse createCondition2(HandleMessage eachItem)
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addIntCondition("type", "=", eachItem.getHandleType());

        con.addIntCondition("status", "=", ShortMessageConstant.STATUS_INIT);

        con.addIntCondition("mtype", "=", ShortMessageConstant.MTYPE_ONLY_SEND_RECEIVE);

        return con;
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
}

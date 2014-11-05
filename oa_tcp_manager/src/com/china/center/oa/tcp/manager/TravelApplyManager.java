/**
 * File Name: TravelApplyManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.manager;


import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.oa.tcp.bean.TravelApplyBean;
import com.china.center.oa.tcp.listener.TcpPayListener;
import com.china.center.oa.tcp.vo.TravelApplyVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;


/**
 * TravelApplyManager
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see TravelApplyManager
 * @since 3.0
 */
public interface TravelApplyManager extends ListenerManager<TcpPayListener>
{
    /**
     * 增加差旅费申请及借款
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addTravelApplyBean(User user, TravelApplyBean bean)
        throws MYException;

    /**
     * 修改差旅费申请及借款
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean updateTravelApplyBean(User user, TravelApplyBean bean)
        throws MYException;

    
    /**
     * 增加附件
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean updateAttachmentList(User user, TravelApplyBean bean)throws MYException;
    /**
     * 删除
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean deleteTravelApplyBean(User user, String id)  
        throws MYException;
   
    /**
     * findVO
     * 
     * @param id
     * @return
     */
    TravelApplyVO findVO(String id);

    /**
     * 提交
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean submitTravelApplyBean(User user, String id, String processId)
        throws MYException;

    /**
     * 通过
     * 
     * @param user
     * @param id
     * @param processId
     * @param reason
     * @return
     * @throws MYException
     */
    boolean passTravelApplyBean(User user, TcpParamWrap param)
        throws MYException;

    /**
     * rejectTravelApplyBean
     * 
     * @param user
     * @param id
     * @param processId
     * @param reason
     * @return
     * @throws MYException
     */
    boolean rejectTravelApplyBean(User user, TcpParamWrap param)
        throws MYException;
    
    /**
     *加班,撤销申请 
     *
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addWork(TravelApplyVO bean,StafferVO staffervo,User user,String processId,
    		String reason,TcpParamWrap param,int oldStatus)
        throws MYException;
    
    /**
     *请假申请 
     */
    
    @Transactional(rollbackFor = MYException.class)
    public boolean vocationAndWork(TravelApplyVO bean,StafferVO staffervo,User user,String processId,
    		String reason,TcpParamWrap param,int oldStatus)
        throws MYException;
    
    @Transactional(rollbackFor = MYException.class)
    public boolean passVocAndWorkTravelApplyBean(User user, TcpParamWrap param)
        throws MYException;
    
    @Transactional(rollbackFor = MYException.class)
    public boolean addVocAndWorkTravelApplyBean(User user, TravelApplyBean bean)
        throws MYException;
    
    @Transactional(rollbackFor = MYException.class)
    public boolean submitVocationAndWork(User user, String id, String processId)
        throws MYException;
    
    @Transactional(rollbackFor = MYException.class)
    public boolean rejectVocationAndWork(User user, TcpParamWrap param)
        throws MYException;
}

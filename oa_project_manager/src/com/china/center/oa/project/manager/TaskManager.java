/**
 * File Name: TravelApplyManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.project.manager;



import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.project.bean.TaskBean;
import com.china.center.oa.project.vo.TaskVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;


/**
 * TravelApplyManager
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see TaskManager
 * @since 3.0
 */
public interface TaskManager 
{
    /**
     * 增加任务
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addTaskBean(User user, TaskBean bean)
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
    @Transactional(rollbackFor = MYException.class)
    boolean passTaskBean(User user, TcpParamWrap param)
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
    @Transactional(rollbackFor = MYException.class)
    boolean deleteTaskBean(User user, String id)
        throws MYException;
    
    

    /**
     * rejectTaskBean
     * 
     * @param user
     * @param id
     * @param processId
     * @param reason
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = MYException.class)
    boolean rejectTaskBean(User user, TcpParamWrap param)
        throws MYException;

    
    /**
     * 修改任务
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean updateTaskBean(User user, TaskBean bean)
        throws MYException;
    
    public boolean submitTask(User user, String id, String processId)
    throws MYException;
    
     void sendEmail()throws MYException;

    
//    /**
//     * 增加附件
//     * @param user
//     * @param bean
//     * @return
//     * @throws MYException
//     */
//    boolean updateAttachmentList(User user, TravelApplyBean bean)throws MYException;
//    /**
//     * 删除
//     * 
//     * @param user
//     * @param id
//     * @return
//     * @throws MYException
//     */
//    boolean deleteTravelApplyBean(User user, String id)  
//        throws MYException;
//    
//
    /**
     * findVO
     * 
     * @param id
     * @return
     */
    TaskVO findVO(String id);
//
//    /**
//     * 提交
//     * 
//     * @param user
//     * @param id
//     * @return
//     * @throws MYException
//     */
//    boolean submitTravelApplyBean(User user, String id, String processId)
//        throws MYException;
//
//    /**
//     * 通过
//     * 
//     * @param user
//     * @param id
//     * @param processId
//     * @param reason
//     * @return
//     * @throws MYException
//     */
//    boolean passTravelApplyBean(User user, TcpParamWrap param)
//        throws MYException;
//
//    /**
//     * rejectTravelApplyBean
//     * 
//     * @param user
//     * @param id
//     * @param processId
//     * @param reason
//     * @return
//     * @throws MYException
//     */
//    boolean rejectTravelApplyBean(User user, TcpParamWrap param)
//        throws MYException;
//    
//    /**
//     *加班,撤销申请 
//     *
//     */
//    @Transactional(rollbackFor = MYException.class)
//    public boolean addWork(TravelApplyVO bean,StafferVO staffervo,User user,String processId,
//    		String reason,TcpParamWrap param,int oldStatus)
//        throws MYException;
//    
//    /**
//     *请假申请 
//     */
//    
//    @Transactional(rollbackFor = MYException.class)
//    public boolean vocationAndWork(TravelApplyVO bean,StafferVO staffervo,User user,String processId,
//    		String reason,TcpParamWrap param,int oldStatus)
//        throws MYException;
//    
//    @Transactional(rollbackFor = MYException.class)
//    public boolean passVocAndWorkTravelApplyBean(User user, TcpParamWrap param)
//        throws MYException;
    
//    /**
//     * 提成特批申请审批
//     */
//    @Transactional(rollbackFor = MYException.class)
//    public boolean passCommission(User user, TcpParamWrap param)
//        throws MYException;
//    
//    @Transactional(rollbackFor = MYException.class)
//    public boolean addVocAndWorkTravelApplyBean(User user, TravelApplyBean bean)
//        throws MYException;
//    
//    @Transactional(rollbackFor = MYException.class)
//    public boolean submitVocationAndWork(User user, String id, String processId)
//        throws MYException;
//    
//    @Transactional(rollbackFor = MYException.class)
//    public boolean rejectVocationAndWork(User user, TcpParamWrap param)
//        throws MYException;
//    
//    @Transactional(rollbackFor = MYException.class)
//    public boolean rejectCommission(User user, TcpParamWrap param)
//        throws MYException;
//    
//    @Transactional(rollbackFor = MYException.class)
//    public boolean passCommissionApplyBean(User user, TcpParamWrap param)
//        throws MYException;
//    
//    @Transactional(rollbackFor = MYException.class)
//    public boolean addCommissionApplyBean(User user, CommissionApplyBean bean)
//        throws MYException;
//    
//    @Transactional(rollbackFor = MYException.class)
//    public boolean submitCommission(User user, String id, String processId)
//        throws MYException;
    
    void statsTaskForMail();
    
    void sendTaskMail();
    
    /**
     * 
     * 进行中的任务，提前30分钟 发送邮件通知 <br>
     * 超出截止时间，仍未完成的也发送邮件
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    void sendTaskWarningMail();
    
}

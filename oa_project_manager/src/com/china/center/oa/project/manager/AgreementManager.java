package com.china.center.oa.project.manager;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.project.bean.AgreementBean;
import com.china.center.oa.project.vo.AgreementVO;

public interface AgreementManager
{

	
	 /**
     * 增加合同
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addAgreementBean(User user, AgreementBean bean)
        throws MYException;

    
    /**
     * 修改合同
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean updateAgreementBean(User user, AgreementBean bean)
        throws MYException;
    
    public boolean submitAgreement(User user, String id, String processId)
    throws MYException;

    
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
    AgreementVO findVO(String id);
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
    
    
}

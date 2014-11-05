package com.china.center.oa.project.manager;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.project.bean.ProjectBean;
import com.china.center.oa.project.vo.ProjectVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;

public interface ProjectManager 
{

	
	/**
     * 增加项目
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addProjectBean(User user, ProjectBean bean)
        throws MYException;
    
    
    /**
     * 修改项目
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean updateProjectBean(User user, ProjectBean bean)
        throws MYException;
    
    public boolean submitProject(User user, String id, String processId)
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
    ProjectVO findVO(String id);
    
    
}

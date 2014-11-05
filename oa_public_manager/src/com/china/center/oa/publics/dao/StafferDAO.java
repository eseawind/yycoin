/**
 * File Name: StafferDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.vo.StafferVO;


/**
 * StafferDAO
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see StafferDAO
 * @since 1.0
 */
public interface StafferDAO extends DAO<StafferBean, StafferVO>
{
    int countByLocationId(String locationId);

    int countByCode(String code);

    boolean updatePwkey(String id, String pwkey);

    boolean updateLever(String id, int lever);

    int countByDepartmentId(String departmentId);

    int countByPostId(String postId);

    List<StafferBean> queryStafferByLocationId(String locationId);

    List<StafferBean> listEntityBeans();

    List<StafferBean> listCommonEntityBeans();

    StafferBean findyStafferByName(String name);

    /**
     * 根据权限查询职员
     * 
     * @param authId
     * @return
     */
    List<StafferBean> queryStafferByAuthId(String authId);

    List<StafferBean> queryStafferByAuthIdAndIndustryId(String authId, String locationId);
    
    List<StafferBean> queryStafferByPostId(String postId);
    
    List<StafferBean> queryStafferByindustryid(String industryid);
    
    List<StafferBean> queryStafferByPrincipalshipId(String principalshipId);
    
    List<StafferBean> queryStafferByIndustryid2(String industryid2);
    
    List<StafferBean> queryAllStaffer();
    
    List<StafferBean> getNeedCommissionStaffer();
    
    boolean updateBlack(String id, int black);
    
    public List<StafferBean> queryStafferByPostIdAndPrincishipId(String postId,String princiId);
    
    public List<StafferBean> queryTwoLevel(String princiId);
    
    public List<StafferBean> queryThreeLevel(String princiId);
    
    List<StafferBean> queryByPricipalAndPostId(String principalshipId, String postId);
}

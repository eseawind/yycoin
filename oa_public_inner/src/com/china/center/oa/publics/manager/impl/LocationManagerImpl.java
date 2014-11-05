/**
 * File Name: LocationManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import java.util.ArrayList;
import java.util.List;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.constant.OrgConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CityDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.LocationVSCityDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.listener.LocationListener;
import com.china.center.oa.publics.manager.LocationManager;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.publics.vo.LocationVO;
import com.china.center.oa.publics.vo.LocationVSCityVO;
import com.china.center.oa.publics.vs.LocationVSCityBean;
import com.china.center.oa.publics.vs.OrgBean;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.StringTools;


/**
 * LocationManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see LocationManagerImpl
 * @since 1.0
 */
@Exceptional
public class LocationManagerImpl extends AbstractListenerManager<LocationListener> implements LocationManager
{
    private LocationDAO locationDAO = null;

    private UserDAO userDAO = null;

    private StafferDAO stafferDAO = null;

    private CommonDAO commonDAO = null;

    private OrgManager orgManager = null;

    private LocationVSCityDAO locationVSCityDAO = null;

    private CityDAO cityDAO = null;

    public LocationManagerImpl()
    {
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean addBean(User user, LocationBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkAddBean(bean);

        bean.setId(commonDAO.getSquenceString());

        locationDAO.saveEntityBean(bean);

        addLocationToOrg(user, bean);

        return true;

    }

    private void addLocationToOrg(User user, LocationBean bean)
        throws MYException
    {
        PrincipalshipBean org = new PrincipalshipBean();

        org.setId(bean.getId());

        org.setName(bean.getName());

        org.setParentId(bean.getParentId());

        // 默认销售分公司都是四级组织
        org.setLevel(OrgConstant.LOCATION_LEVEL);

        List<OrgBean> parentOrgList = new ArrayList();

        OrgBean oo = new OrgBean();

        oo.setSubId(bean.getId());

        oo.setParentId(bean.getParentId());

        parentOrgList.add(oo);

        org.setParentOrgList(parentOrgList);

        orgManager.addBeanWithoutTransactional(user, org);
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean delBean(User user, String locationId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, locationId);

        checkDelBean(user, locationId);

        locationDAO.deleteEntityBean(locationId);

        locationVSCityDAO.deleteEntityBeansByFK(locationId);

        orgManager.delBeanWithoutTransactional(user, locationId);

        return true;
    }

    /**
     * findVO
     * 
     * @param id
     * @return
     * @throws MYException
     */
    public LocationVO findVO(String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        LocationVO vo = locationDAO.findVO(id);

        if (vo == null)
        {
            return null;
        }

        List<LocationVSCityVO> city = locationVSCityDAO.queryEntityVOsByFK(id);

        vo.setCityVOs(city);

        List<LocationVSCityBean> acity = new ArrayList<LocationVSCityBean>();

        for (LocationVSCityVO locationVSCityVO : city)
        {
            acity.add(locationVSCityVO);
        }

        vo.setCitys(acity);

        return vo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.LocationManager#addLocationVSCity(com.center.china.osgi.publics.User,
     *      java.lang.String, java.util.List)
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean addLocationVSCity(User user, String locationId, List<LocationVSCityBean> list)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user);

        List<LocationVSCityBean> oldList = locationVSCityDAO.queryEntityBeansByFK(locationId);

        locationVSCityDAO.deleteEntityBeansByFK(locationId);

        for (LocationListener listener : this.listenerMapValues())
        {
            listener.onAddLocationVSCityBefore(user, locationId, list);
        }

        for (LocationVSCityBean locationVSCityBean : list)
        {
            locationVSCityBean.setLocationId(locationId);

            if (StringTools.isNullOrNone(locationVSCityBean.getCityId()))
            {
                continue;
            }

            int count = locationVSCityDAO.countByFK(locationVSCityBean.getCityId(),
                AnoConstant.FK_FIRST);

            CityBean city = cityDAO.find(locationVSCityBean.getCityId());

            if (city == null)
            {
                throw new MYException("数据错误请重新操作");
            }

            if (count > 0)
            {
                throw new MYException("区域[%s]已经被其他分公司使用", city.getName());
            }

            if (StringTools.isNullOrNone(locationVSCityBean.getProvinceId()))
            {
                locationVSCityBean.setProvinceId(city.getParentId());
            }

            locationVSCityDAO.saveEntityBean(locationVSCityBean);

            for (LocationListener listener : this.listenerMapValues())
            {
                listener.onAddLocationVSCityEach(user, locationId, locationVSCityBean);
            }

        }

        // 本次删除的地市
        List<LocationVSCityBean> delCity = analyseDelCity(oldList, list);

        for (LocationListener listener : this.listenerMapValues())
        {
            listener.onDeleteLocationVSCity(user, locationId, delCity);
        }

        return true;
    }

    /**
     * 分析del的地市
     * 
     * @param oldList
     * @param newlist
     * @return
     */
    private List<LocationVSCityBean> analyseDelCity(List<LocationVSCityBean> oldList,
                                                    List<LocationVSCityBean> newlist)
    {
        List<LocationVSCityBean> result = new ArrayList<LocationVSCityBean>();

        for (LocationVSCityBean locationVSCityBean : oldList)
        {
            if ( !newlist.contains(locationVSCityBean))
            {
                result.add(locationVSCityBean);
            }
        }

        return result;
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkAddBean(LocationBean bean)
        throws MYException
    {
        if (locationDAO.countByUnique(bean.getName()) > 0)
        {
            throw new MYException("分公司名称已经存在");
        }

        if (locationDAO.countCode(bean.getCode()) > 0)
        {
            throw new MYException("分公司标识已经存在");
        }
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkDelBean(User user, String locationId)
        throws MYException
    {
        if (PublicConstant.VIRTUAL_LOCATION.equals(locationId))
        {
            throw new MYException("虚拟区域不能删除");
        }

        // 存在人员或者user
        if (userDAO.countByLocationId(locationId) > 0)
        {
            throw new MYException("分公司下存在登录用户");
        }

        // stafferDAO
        if (stafferDAO.countByLocationId(locationId) > 0)
        {
            throw new MYException("分公司下存在注册职员");
        }

        // 注入监听
        for (LocationListener listener : this.listenerMapValues())
        {
            listener.onDeleteLocation(user, locationId);
        }
    }

    /**
     * @return the locationDAO
     */
    public LocationDAO getLocationDAO()
    {
        return locationDAO;
    }

    /**
     * @param locationDAO
     *            the locationDAO to set
     */
    public void setLocationDAO(LocationDAO locationDAO)
    {
        this.locationDAO = locationDAO;
    }

    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

    /**
     * @return the userDAO
     */
    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    /**
     * @param userDAO
     *            the userDAO to set
     */
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    /**
     * @return the stafferDAO
     */
    public StafferDAO getStafferDAO()
    {
        return stafferDAO;
    }

    /**
     * @param stafferDAO
     *            the stafferDAO to set
     */
    public void setStafferDAO(StafferDAO stafferDAO)
    {
        this.stafferDAO = stafferDAO;
    }

    /**
     * @return the locationVSCityDAO
     */
    public LocationVSCityDAO getLocationVSCityDAO()
    {
        return locationVSCityDAO;
    }

    /**
     * @param locationVSCityDAO
     *            the locationVSCityDAO to set
     */
    public void setLocationVSCityDAO(LocationVSCityDAO locationVSCityDAO)
    {
        this.locationVSCityDAO = locationVSCityDAO;
    }

    /**
     * @return the cityDAO
     */
    public CityDAO getCityDAO()
    {
        return cityDAO;
    }

    /**
     * @param cityDAO
     *            the cityDAO to set
     */
    public void setCityDAO(CityDAO cityDAO)
    {
        this.cityDAO = cityDAO;
    }

    /**
     * @return the orgManager
     */
    public OrgManager getOrgManager()
    {
        return orgManager;
    }

    /**
     * @param orgManager
     *            the orgManager to set
     */
    public void setOrgManager(OrgManager orgManager)
    {
        this.orgManager = orgManager;
    }
}

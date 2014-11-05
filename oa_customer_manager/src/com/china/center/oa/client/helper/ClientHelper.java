package com.china.center.oa.client.helper;

import com.china.center.oa.client.bean.AbstractCustomerContactBean;
import com.china.center.oa.client.bean.AbstractCustomerIndividualBean;
import com.china.center.tools.DecSecurity;

public class ClientHelper
{
    /**
     * 加密 - 个人
     * 
     * @param bean
     */
    public static void encryptIndividualClient(AbstractCustomerIndividualBean bean)
    {
        bean.setAddress(DecSecurity.encrypt(bean.getAddress()));

        bean.setHandphone(DecSecurity.encrypt(bean.getHandphone()));

        bean.setTel(DecSecurity.encrypt(bean.getTel()));

        bean.setEmail(DecSecurity.encrypt(bean.getEmail()));

        bean.setQq(DecSecurity.encrypt(bean.getQq()));

        bean.setWeixin(DecSecurity.encrypt(bean.getWeixin()));
        
        bean.setWeibo(DecSecurity.encrypt(bean.getWeibo()));
    }
    
    /**
     * 解密 - 个人
     * 
     * @param bean
     */
    public static void decryptIndividualClient(AbstractCustomerIndividualBean bean)
    {
        bean.setAddress(DecSecurity.decrypt(bean.getAddress()));

        bean.setHandphone(DecSecurity.decrypt(bean.getHandphone()));

        bean.setTel(DecSecurity.decrypt(bean.getTel()));

        bean.setEmail(DecSecurity.decrypt(bean.getEmail()));

        bean.setQq(DecSecurity.decrypt(bean.getQq()));

        bean.setWeixin(DecSecurity.decrypt(bean.getWeixin()));
        
        bean.setWeibo(DecSecurity.decrypt(bean.getWeibo()));
    
    }


    /**
     * 保密
     * 
     * @param bean
     */
    public static void hideIndividualClient(AbstractCustomerIndividualBean bean)
    {
        bean.setAddress("保密");

        bean.setHandphone("保密");

        bean.setTel("保密");

        bean.setEmail("保密");

        bean.setQq("保密");

        bean.setWeixin("保密");

        bean.setWeibo("保密");
    }
    
    /**
     * 加密 - 联系信息
     * 
     * @param bean
     */
    public static void encryptClientContact(AbstractCustomerContactBean bean)
    {
        bean.setHandphone(DecSecurity.encrypt(bean.getHandphone()));

        bean.setTel(DecSecurity.encrypt(bean.getTel()));

        bean.setEmail(DecSecurity.encrypt(bean.getEmail()));

        bean.setQq(DecSecurity.encrypt(bean.getQq()));

        bean.setWeixin(DecSecurity.encrypt(bean.getWeixin()));

        bean.setWeibo(DecSecurity.encrypt(bean.getWeibo()));
    }
    
    /**
     * 解密 - 联系信息
     * 
     * @param bean
     */
    public static void decryptClientContact(AbstractCustomerContactBean bean)
    {
		bean.setHandphone(DecSecurity.decrypt(bean.getHandphone()));

        bean.setTel(DecSecurity.decrypt(bean.getTel()));

        bean.setEmail(DecSecurity.decrypt(bean.getEmail()));

        bean.setQq(DecSecurity.decrypt(bean.getQq()));

        bean.setWeixin(DecSecurity.decrypt(bean.getWeixin()));

        bean.setWeibo(DecSecurity.decrypt(bean.getWeibo()));
    }


    /**
     * 保密 - 部门
     * 
     * @param bean
     */
    public static void hideClientContact(AbstractCustomerContactBean bean)
    {
        bean.setHandphone("保密");

        bean.setTel("保密");

        bean.setEmail("保密");

        bean.setQq("保密");

        bean.setWeixin("保密");

        bean.setWeibo("保密");
    }
}

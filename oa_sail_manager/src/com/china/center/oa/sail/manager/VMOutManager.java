package com.china.center.oa.sail.manager;

import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.sail.bean.OutBean;

public interface VMOutManager
{
	boolean addBeans(User user, List<OutBean> outList) throws MYException;
}

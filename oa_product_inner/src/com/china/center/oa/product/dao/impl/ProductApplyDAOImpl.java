package com.china.center.oa.product.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.product.bean.ProductApplyBean;
import com.china.center.oa.product.dao.ProductApplyDAO;
import com.china.center.oa.product.vo.ProductApplyVO;

public class ProductApplyDAOImpl extends BaseDAO<ProductApplyBean,ProductApplyVO> implements ProductApplyDAO {

    @Override
    public boolean modifyProductApplyStatus(String id, int status) {

        jdbcOperation.updateField("status", status, id, this.claz);

        return true;
    
    }

    
}

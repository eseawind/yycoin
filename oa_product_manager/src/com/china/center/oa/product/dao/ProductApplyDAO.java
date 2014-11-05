package com.china.center.oa.product.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.product.bean.ProductApplyBean;
import com.china.center.oa.product.vo.ProductApplyVO;

public interface ProductApplyDAO extends DAO<ProductApplyBean, ProductApplyVO> {

    boolean modifyProductApplyStatus(String id, int status);
    
    
}

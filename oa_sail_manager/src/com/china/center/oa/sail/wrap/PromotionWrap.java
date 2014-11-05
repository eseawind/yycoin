package com.china.center.oa.sail.wrap;

import java.io.Serializable;

/**
 * 
 * 促销活动计算的结果pojo
 *
 * @author fangliwen 2012-8-24
 */
public class PromotionWrap implements Serializable {

    /**
     * 0:成功 1：金额不足 2：数量不足
     */
    private int ret = 0;
    
    private double promValue = 0.0d;

    /**
     * default constructor
     */
    public PromotionWrap()
    {
        
    }
    
    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public double getPromValue() {
        return promValue;
    }

    public void setPromValue(double promValue) {
        this.promValue = promValue;
    }
    
    
}

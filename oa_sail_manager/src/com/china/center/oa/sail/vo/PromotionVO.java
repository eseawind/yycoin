package com.china.center.oa.sail.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.PromotionBean;

/**
 * 
 * 促销规则视图
 *
 * @author fangliwen 2012-8-16
 */
@Entity(inherit = true)
public class PromotionVO extends PromotionBean {

    /**
     * 事业部名称，多个用逗号间隔
     */
    @Ignore
    private String industryName = "";   

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    /**
     * default constructor
     */
    public PromotionVO()
    {
        
    }
    
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("PromotionVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("industryName = ")
            .append(this.industryName)            
            .append(TAB)
            .append(" )");
            ;
        
        return retValue.toString();
    }
}

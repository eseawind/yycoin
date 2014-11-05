/**
 * File Name: StafferConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-2-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * StafferConstant
 * 
 * @author ZHUZHU
 * @version 2009-2-8
 * @see StafferConstant
 * @since 1.0
 */
public interface StafferConstant
{
    /**
     * 系统管理员
     */
    String SUPER_STAFFER = "99999999";

    @Defined(key = "examType", value = "终端")
    int EXAMTYPE_TERMINAL = 0;

    @Defined(key = "examType", value = "拓展")
    int EXAMTYPE_EXPAND = 1;

    @Defined(key = "examType", value = "其他")
    int EXAMTYPE_OTHER = 99;

    @Defined(key = "stafferStatus", value = "正常")
    int STATUS_COMMON = 0;

    @Defined(key = "stafferStatus", value = "废弃")
    int STATUS_DROP = 99;

    @Defined(key = "stafferBlack", value = "正常")
    int BLACK_NO = 0;

    @Defined(key = "stafferBlack", value = "黑名单-款到发货")
    int BLACK_YES = 1;

    @Defined(key = "stafferBlack", value = "黑名单-停止销售")
    int BLACK_FORBID = 2;
    
    int LEVER_DEFAULT = 1;

    /**
     * 销售系
     */
    @Defined(key = "stafferOtype", value = "销售系")
    int OTYPE_SAIL = 0;

    /**
     * 职能系
     */
    @Defined(key = "stafferOtype", value = "职能系")
    int OTYPE_WORK = 1;

    /**
     * 管理系
     */
    @Defined(key = "stafferOtype", value = "管理系")
    int OTYPE_MANAGER = 2;
    
    /**
     * 计算提成
     * 纪念品事业部
     */
    String STATS_INDUSTRYID1 = "5111656";
    
    /**
     * 计算提成
     * 终端
     */
    String STATS_INDUSTRYID2 = "5111658";
    
    /**
     * 计算提成
     * 定制
     */
    String STATS_INDUSTRYID3 = "5111664";
}

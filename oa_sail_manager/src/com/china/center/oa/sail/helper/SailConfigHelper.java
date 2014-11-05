/**
 * File Name: SailConfigHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.helper;


import com.china.center.oa.sail.constanst.SailConstant;
import com.china.center.oa.sail.vo.SailConfigVO;


/**
 * SailConfigHelper
 * 
 * @author ZHUZHU
 * @version 2011-12-17
 * @see SailConfigHelper
 * @since 3.0
 */
public abstract class SailConfigHelper
{
    /**
     * changeVO
     * 
     * @param vo
     */
    public static void changeVO(SailConfigVO vo)
    {
        if (vo.getFinType0() == SailConstant.SAILCONFIG_FIN_NO)
        {
            vo.setFinTypeName0("不开票");
        }
        else
        {
            vo.setFinTypeName0("开票" + '(' + vo.getRatio0() + "‰)");
        }

        if (vo.getFinType1() == SailConstant.SAILCONFIG_FIN_NO)
        {
            vo.setFinTypeName1("不开票");
        }
        else
        {
            vo.setFinTypeName1("开票" + '(' + vo.getRatio1() + "‰)");
        }

        if (vo.getFinType2() == SailConstant.SAILCONFIG_FIN_NO)
        {
            vo.setFinTypeName2("不开票");
        }
        else
        {
            vo.setFinTypeName2("开票" + '(' + vo.getRatio2() + "‰)");
        }

        if (vo.getFinType3() == SailConstant.SAILCONFIG_FIN_NO)
        {
            vo.setFinTypeName3("不开票");
        }
        else
        {
            vo.setFinTypeName3("开票" + '(' + vo.getRatio3() + "‰)");
        }

        if (vo.getFinType4() == SailConstant.SAILCONFIG_FIN_NO)
        {
            vo.setFinTypeName4("不开票");
        }
        else
        {
            vo.setFinTypeName4("开票" + '(' + vo.getRatio4() + "‰)");
        }

        if (vo.getFinType5() == SailConstant.SAILCONFIG_FIN_NO)
        {
            vo.setFinTypeName5("不开票");
        }
        else
        {
            vo.setFinTypeName5("开票" + '(' + vo.getRatio5() + "‰)");
        }
    }
}

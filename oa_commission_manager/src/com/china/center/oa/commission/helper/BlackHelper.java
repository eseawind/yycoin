package com.china.center.oa.commission.helper;

public abstract class BlackHelper 
{

    public static String getProductTypeName(int productType)
    {
        if (productType == 0)
        {
            return "金银章";
        }
        else if (productType == 1)
        {
            return "金银币";
        }
        else if (productType == 2)
        {
            return "流通币";
        }
        else if (productType == 3)
        {
            return "旧币";
        }
        else if (productType == 4)
        {
            return "邮票";
        }
        else
        {
            return "其他";
        }
    }
}

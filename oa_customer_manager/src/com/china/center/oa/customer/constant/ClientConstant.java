package com.china.center.oa.customer.constant;

import com.china.center.jdbc.annotation.Defined;

public interface ClientConstant
{
	/**
	 * 账户性质
	 */
    @Defined(key = "accountType", value = "对公")
    int ACCOUNT_TYPE_PUBLIC = 0;
    
    @Defined(key = "accountType", value = "卡钞")
    int ACCOUNT_TYPE_CASH = 1;
    
    @Defined(key = "gender", value = "男")
    int MALE = 0;
    
    @Defined(key = "gender", value = "女")
    int FEMALE = 1;
    
    @Defined(key = "personal", value = "20-30")
    int PERSONAL_SINGLE = 0;
    
    @Defined(key = "personal", value = "31-40")
    int PERSONAL_DATE = 1;
    
    @Defined(key = "personal", value = "41-50")
    int PERSONAL_MARRIED = 2;
    
    @Defined(key = "personal", value = "51-60")
    int PERSONAL_FIFTY = 3;
    
    @Defined(key = "personal", value = "61-70")
    int PERSONAL_SIXTY = 4;
    
    @Defined(key = "personal", value = "70以上")
    int PERSONAL_SEVENTY = 5;
    
    
    @Defined(key = "relationShip", value = "非常好")
    int RELATIONSHIP_A = 1;
    
    @Defined(key = "relationShip", value = "很好")
    int RELATIONSHIP_B = 2;
    
    @Defined(key = "relationShip", value = "一般")
    int RELATIONSHIP_C = 3;
    
    @Defined(key = "relationShip", value = "差")
    int RELATIONSHIP_D = 4;
    
    @Defined(key = "relationShip", value = "很差")
    int RELATIONSHIP_E = 5;
}

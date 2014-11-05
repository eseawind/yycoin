package com.china.center.oa.commission.listener;

import com.center.china.osgi.publics.ParentListener;

public interface CommissionListener extends ParentListener 
{

    void onRollbackFinanceTurn(String monthKey); 
}

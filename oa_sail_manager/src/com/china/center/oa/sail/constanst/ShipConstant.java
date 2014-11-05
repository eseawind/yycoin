package com.china.center.oa.sail.constanst;

import com.china.center.jdbc.annotation.Defined;

public interface ShipConstant
{
	/**
	 * 发货状态 初始
	 */
	@Defined(key = "shipStatus", value = "初始")
	int SHIP_STATUS_INIT = 0;
	
	/**
	 * 发货状态 已拣配
	 */
	@Defined(key = "shipStatus", value = "已拣配")
	int SHIP_STATUS_PICKUP = 1;
	
	/**
	 * 发货状态 已发货
	 */
	@Defined(key = "shipStatus", value = "已发货")
	int SHIP_STATUS_CONSIGN = 2;
	
	/**
	 * 发货状态 已打印
	 */
	@Defined(key = "shipStatus", value = "已打印")
	int SHIP_STATUS_PRINT = 3;
	
	
}

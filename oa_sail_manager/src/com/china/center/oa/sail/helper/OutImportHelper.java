package com.china.center.oa.sail.helper;

public class OutImportHelper
{
	public static int getStorageType(String name)
	{
		if (name.equals("订货代销"))
		{
			return 1;
		}
		else if (name.equals("铺货库存"))
		{
			return 2;
		}
		else if (name.equals("在途库存"))
		{
			return 3;
		}
		else
		{
			return -1;
		}
			
	}
	
	public static int getInvoiceNature(String name)
	{
		if (name.equals("一张开票"))
		{
			return 1;
		}
		else if (name.equals("单张开票"))
		{
			return 2;
		}
		else
		{
			return -1;
		}
	}
	
	public static int getInvoiceType(String name)
	{
		if (name.equals("增值可抵扣票"))
		{
			return 1;
		}
		else if (name.equals("增值普票"))
		{
			return 2;
		}
		else if (name.equals("普票"))
		{
			return 3;
		}
		else if (name.equals("旧货票"))
		{
			return 4;
		}
		else if (name.equals("零税率票"))
		{
			return 5;
		}
		else
		{
			return -1;
		}
	}
}

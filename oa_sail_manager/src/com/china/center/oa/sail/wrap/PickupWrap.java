package com.china.center.oa.sail.wrap;

import java.io.Serializable;
import java.util.List;

import com.china.center.oa.sail.vo.PackageVO;

@SuppressWarnings("serial")
public class PickupWrap implements Serializable
{
	private String pickupId = "";
	
	private List<PackageVO> packageList = null;

	public PickupWrap()
	{
	}

	public String getPickupId()
	{
		return pickupId;
	}

	public void setPickupId(String pickupId)
	{
		this.pickupId = pickupId;
	}

	public List<PackageVO> getPackageList()
	{
		return packageList;
	}

	public void setPackageList(List<PackageVO> packageList)
	{
		this.packageList = packageList;
	}
}

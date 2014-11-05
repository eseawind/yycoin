function getProvider(value, customername, customerconnector, customerphone)
{
	if (gindex == 0)
	{
		$O('mainProviderName').value = customername;
		$O('mainProvider').value = value;
	}
	
	if (gindex == 1)
	{
		$O('assistantProviderName1').value = customername;
		$O('assistantProvider1').value = value;
	}
	
	if (gindex == 2)
	{
		$O('assistantProviderName2').value = customername;
		$O('assistantProvider2').value = value;
	}
	
	if (gindex == 3)
	{
		$O('assistantProviderName3').value = customername;
		$O('assistantProvider3').value = value;
	}
	
	if (gindex == 4)
    {
        $O('assistantProviderName4').value = customername;
        $O('assistantProvider4').value = value;
    }
}

var gindex = 0;
function selectProvider(obj, index)
{
	gindex = index;
	
	window.common.modal('../provider/provider.do?method=rptQueryProvider&productType=' + $$('type'));
}

function checkDuplicate()
{
	var arr = [];
	
	if ($$('mainProvider') != '')
	{
		var item = $$('mainProvider');
		if (containInList(arr, item))
		{
			alert('供应商不能重复');
			return false;
		}
		
		arr.push(item);
	}
	
	if ($$('assistantProvider1') != '')
	{
		var item = $$('assistantProvider1');
		if (containInList(arr, item))
		{
			alert('供应商不能重复');
			return false;
		}
		
		arr.push(item);
	}
	
	if ($$('assistantProvider2') != '')
	{
		var item = $$('assistantProvider2');
		if (containInList(arr, item))
		{
			alert('供应商不能重复');
			return false;
		}
		
		arr.push(item);
	}
	
	if ($$('assistantProvider3') != '')
	{
		var item = $$('assistantProvider3');
		if (containInList(arr, item))
		{
			alert('供应商不能重复');
			return false;
		}
		
		arr.push(item);
	}
	
	return true;
}


function typeChange()
{
	$O('mainProviderName').value = '';
	$O('mainProvider').value = '';
	
	$O('assistantProviderName1').value = '';
	$O('assistantProvider1').value = '';
	
	$O('assistantProviderName2').value = '';
	$O('assistantProvider2').value = '';
	
	$O('assistantProviderName3').value = '';
	$O('assistantProvider3').value = '';
}

function selectProduct()
{
    var managerType = '';
    
    window.common.modal('../product/product.do?method=rptQueryProduct&load=1&selectMode=1&mtype='+managerType+'&status=0');
}

function getProduct(oos)
{
	var oo = oos[0];
	
    $O('productId').value = oo.value;
    $O('productName').value = oo.pname;
   
}

function selectStaffer()
{
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
    var oo = oos[0];
    
    $O('stafferName').value = oo.pname;
    $O('stafferId').value = oo.value;
}

function compareDate(date1, date2)
{
	var d1 = new Date(date1.replace(/-/g, "/")); 
	var d2 = new Date(date2.replace(/-/g, "/")); 

//	if (Date.parse(d1) - Date.parse(d2) == 0) 
//	{ 
//		window.alert("两个日期相等"); 
//		return false; 
//	} 
//	if (Date.parse(d1) - Date.parse(d2) < 0) { 
//	window.alert("结束日期 大于 开始日期"); 
//	} 
	
	return Date.parse(d1) - Date.parse(d2);
}
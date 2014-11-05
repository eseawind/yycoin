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

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../common/common.jsp"%>

var cmap = window.top.topFrame.cmap;
var pList = window.top.topFrame.pList;

var areaJson = JSON.parse('${areaStrJSON}');	
	
var showJSON = JSON.parse('${showJSON}');
var invoicesJSON = JSON.parse('${invoicesJSON}');
var vsJSON = JSON.parse('${vsJSON}');

var invMap = {};
var invFullMap = {};
var invNameMap = {};
<c:forEach items="${invoiceList}" var="item">
  invFullMap['${item.id}'] = '${item.fullName}';
</c:forEach>

<c:forEach items="${dutyList}" var="item">
  invMap['${item.id}'] = '${item.type}';
  invNameMap['${item.id}'] = '${item.invoicer}';
</c:forEach>

function changes(obj)
{
    removeAllItem($O('cityId'));
    setOption($O('cityId'), "", "--");
    
    if ($$('provinceId') == "")
    {
        return;
    }
    
    var cityList = cmap[$$('provinceId')];
    for (var i = 0; i < cityList.length; i++)
    {
        setOption($O('cityId'), cityList[i].id, cityList[i].name);
    }
    
    removeAllItem($O('areaId'));
    
    setOption($O('areaId'), "", "--");
}

function changeArea(areaId)
{
    var id = $$('cityId');
    
    if (id == "")
    {
        return;
    }
    
    removeAllItem($O('areaId'));
    setOption($O('areaId'), "", "--");
    
    var areaList = areaJson[$$('cityId')];

    removeAllItem($O('areaId'));
    
    setOption($O('areaId'), "", "--");
    
    for (var i = 0; i < areaList.length; i++)
    {
        setOption($O('areaId'), areaList[i].id,  areaList[i].name);
    }
    
    return;
}

function check()
{
	if ($$('fillType') == 0) {
		var radioElements = document.getElementsByName('rshipping');
	
	    var shipping = '';
		for (var i=0; i< radioElements.length; i++)
		{
			if (radioElements[i].checked)
			{
				shipping = radioElements[i].value;
			}
		}
		
		if (shipping != '')
			$O('shipping').value = shipping;
		else
		{
			alert('发货方式为必输');
			return false;	
		}
			
		if (shipping != '0' && shipping != '99')
		{
			$O('provinceId').oncheck = 'notNone';
		    $O('cityId').oncheck = 'notNone';
		    $O('address').oncheck = 'notNone';
		}
	
		if (shipping != '99')
	    {
		    //校验手机与固定电话
		    var mobile = $$('mobile');
		    var telephone = $$('telephone');
		    
		    if (trim(mobile).length == 0 || trim(mobile).length > 13)
		    {
		    	alert('手机的长度须是11位数字.');
		    	
		    	return false;
		    }
		    
		    if (trim(mobile).indexOf('1') != 0)
		    {
		    	alert('手机须是1开头');
		    	
		    	return false;
		    }
	    }
	}
	
    if (!formCheck())
    {
        return false;
    }
    
    return true;
}

// 
function radio_click(obj)
{
	if (obj.value == '2')
	{
		$O('transport1').disabled = false;
		removeAllItem($O('transport1'));
	    setOption($O('transport1'), "", "--");

	    <c:forEach items="${expressList}" var="item">
	    	
			if ("${item.type}" == 0 || "${item.type}" == 99)
			{
				setOption($O('transport1'), "${item.id}", "${item.name}");
			}
		</c:forEach>

		removeAllItem($O('transport2'));
	    setOption($O('transport2'), "", "--");

	    $O('transport2').disabled = true;
	    
	    var expressPay = $O('expressPay');
	    var transportPay = $O('transportPay');
	    
		removeAllItem(expressPay);
					
		setOption(expressPay, '1', '业务员支付');
		setOption(expressPay, '2', '公司支付');
		setOption(expressPay, '3', '客户支付');
	    
		removeAllItem(transportPay);
		
		setOption(transportPay, '', '--');
	}
	else if (obj.value == '3')
	{
		$O('transport2').disabled = false;
		removeAllItem($O('transport2'));
	    setOption($O('transport2'), "", "--");

	    <c:forEach items="${expressList}" var="item">
		if ("${item.type}" == 1 || "${item.type}" == 99)
		{
			setOption($O('transport2'), "${item.id}", "${item.name}");
		}
		</c:forEach>

		removeAllItem($O('transport1'));
		setOption($O('transport1'), "", "--");
		
		$O('transport1').disabled = true;
		
	    var expressPay = $O('expressPay');
	    var transportPay = $O('transportPay');
	    
		removeAllItem(expressPay);
					
		setOption(expressPay, '', '--');
	    
		removeAllItem(transportPay);
					
		setOption(transportPay, '1', '业务员支付');
		setOption(transportPay, '2', '公司支付');
		setOption(transportPay, '3', '客户支付'); 
	}
	else if (obj.value == '4')
	{
		$O('transport1').disabled = false;
		$O('transport2').disabled = false;
		
		removeAllItem($O('transport1'));
	    setOption($O('transport1'), "", "--");

	    removeAllItem($O('transport2'));
	    setOption($O('transport2'), "", "--");

	    <c:forEach items="${expressList}" var="item">
	    	
			if ("${item.type}" == 0 || "${item.type}" == 99)
			{
				setOption($O('transport1'), "${item.id}", "${item.name}");
			}
		</c:forEach>

	    <c:forEach items="${expressList}" var="item">
    	
		if ("${item.type}" == 1 || "${item.type}" == 99)
		{
			setOption($O('transport2'), "${item.id}", "${item.name}");
		}
		</c:forEach>
		
	    var expressPay = $O('expressPay');
	    var transportPay = $O('transportPay');
	    
		removeAllItem(expressPay);
					
		setOption(expressPay, '1', '业务员支付');
		setOption(expressPay, '2', '公司支付');
		setOption(expressPay, '3', '客户支付');
    
		removeAllItem(transportPay);
		
		setOption(transportPay, '1', '业务员支付');
		setOption(transportPay, '2', '公司支付');
		setOption(transportPay, '3', '客户支付');
	}
	else
	{
		$O('transport1').disabled = true;
		$O('transport2').disabled = true;
		
		removeAllItem($O('transport1'));
	    setOption($O('transport1'), "", "--");

		removeAllItem($O('transport2'));
	    setOption($O('transport2'), "", "--");
	    
	    var expressPay = $O('expressPay');
	    var transportPay = $O('transportPay');
	    
		removeAllItem(expressPay);
		
		setOption(expressPay, '', '--');
    
		removeAllItem(transportPay);
		
		setOption(transportPay, '', '--');
	    
	    if (obj.value == '0')
	    {
	    	$O('provinceId').value = '';
		    $O('cityId').value = '';
		    $O('areaId').value = '';
		    $O('address').value = '';
		    
		   	$O('provinceId').oncheck = '';
		    $O('cityId').oncheck = '';
		    $O('areaId').oncheck = '';
		    $O('address').oncheck = '';
	    }
	    
	    if (obj.value == '99')
	    {
	    	$O('provinceId').value = '';
		    $O('cityId').value = '';
		    $O('areaId').value = '';
		    $O('address').value = '';
		    $O('receiver').value = '';
		    $O('mobile').value = '';
		    
		   	$O('provinceId').oncheck = '';
		    $O('cityId').oncheck = '';
		    $O('areaId').oncheck = '';
		    $O('address').oncheck = '';
		    $O('receiver').oncheck = '';
		    $O('mobile').oncheck = '';
	    }
	}
		
}

function selectAddr()
{
	var cust = $O('customerId').value;
	
	if (cust == '')
		cust = 'NONE';
	window.common.modal('../customer/address.do?method=rptQueryAddress&first=1&load=1&selectMode=1&customerId=' + cust);
}

function getAddress(oos)
{
	obj = oos[0];

	$O('provinceId').value = obj.pprovinceid;
	
	changes($O('provinceId'));

    $O('cityId').value = obj.pcityid;

    changeArea(obj.pareaid);
    $O('areaId').value = obj.pareaid;
    
    $O('address').value = obj.paddress;
    $O('receiver').value = obj.preceiver;
    $O('mobile').value = obj.pmobile;
    $O('telephone').value = obj.ptelephone;
}
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../common/common.jsp"%>

function checkTotal()
{
    if ($O('saves').value == 'save')
    {
         if (window.confirm("确定保存库单?"))
         {
            disableAllButton();
            outForm.submit();
         }else
         {
         	$Dbuttons(false);
         }

         return;
    }

    //判断method
    if ($$('method') != 'addOut')
    {
        //alert('提示：提交没有方法，请重新登录操作');
        //return false;
    }

    if (window.confirm("确定提交库单?"))
    {
        disableAllButton();
        outForm.submit();
    }else
    {
    	$Dbuttons(false);
    }
}

function save()
{
    $O('saves').value = 'save';
    
    if (check())
    { 	 
		processProm();
    }
}

function sub()
{
    $O('saves').value = 'submit';
    
    if (check())
    {
       	processProm();
    }
}

function check()
{
	var radioElements = document.getElementsByName('shipping');

    var shipping = '';
	for (var i=0; i< radioElements.length; i++)
	{
		if (radioElements[i].checked)
		{
			shipping = radioElements[i].value;
		}
	}
	
	if (shipping != '')
		$O('rshipping').value = shipping;
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
	
    if (!formCheck())
    {
        return false;
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
    
    return true;
}

function processProm()
{
   	if ($$('outType')==0)
   	{
   		if ($$('hasProm')== '0' && $$('canProm') == '0')
   		{
   		   	if (window.confirm("是否使用促销活动?"))
	   		{
	   			$Dbuttons(true);
	   			
	   			selectProm();
	   			
	   			var sh;
		
				sh=setInterval(function() {
				
					if ($$('isAddUp')=='Y')
					{
						clearInterval(sh);
						checkTotal();					
					}		
				}, 100);
	   			
	   		}else
	   		{
	   			clearProm();
	   			checkTotal();
	   		}
   		}
   		else
   		{
   			checkTotal();
   		}
   	}
   	else
   	{
   		checkTotal();
   	}
}

// 选择促销活动
function selectProm()
{
    //获取付款方式 - 款到发货,信用担保
    var reserve3 = $O('reserve3').value;
    
    window.common.modal('../sail/promotion.do?method=rptQueryPromotion&payType='+reserve3+ '&load=1&selectMode=1');

}

function getProm(oos)
{

	var oo = oos[0];

	// 获取活动ID, 表单中eventId赋值，同时根据活动ID，及当前单据的商品、数量，通过ajax的方式校验活动规则是否满足
	var eventId = oo.value;
    $O('eventName').value = oo.pname;  
    $O('eventId').value = eventId;
    $O('isAddUp').value = oo.paddup;
	$O('eventDate').value = oo.pdate1 + '~' + oo.pdate2;
	
	var products = getProducts();
	
	var hisOrders = '';	

	// flag=0表示不包括历史订单
	$ajax('../sail/out.do?method=checkPromotionAndRetPromValue&thisOrder='+ 
		products + '&hisOrders='+ hisOrders + '&eventId='+ eventId + '&flag=0', callBackProm);
}

function callBackProm(data)
{
	
	var prom = data.msg;
	
	// ret = 1 表示出错
	if (data.ret == 1)
	{
		clearProm();
	
		alert('促销活动执行出错：' + prom);
		
		return false;
	}
	
	if (!isFloat(prom.promValue))
	{
		clearProm();
		
		alert('促销活动执行出错：折扣金额不为数据型')
		
		return false;
	}
	
	// 金额不足
	if (prom.ret == 1 || prom.ret == 4)
	{
		var mess0 = '';
		var mess = '';
		
		if (prom.ret == 1)
		{
			mess0 = '参与活动的商品的金额小于活动的最小金额，是否绑定历史单据?';			
			mess = '促销活动执行出错：不满足活动金额要求';	
		}else
		{
			mess0 = '活动中的商品不都在销售单中，是否绑定历史单据?';			
			mess = '促销活动执行出错：当前是礼包活动，要求活动中的商品在销售单都包含';		
		}
	
		$O('promValue').value = '';
		$O('refBindOutId').value = '';	
		
		if ($$('isAddUp')== 0 )
		{
			if (window.confirm(mess0))
	         {
				selectOrder();
				
				return true;
	         }
		}
		
		alert(mess);
		
		$O('eventId').value = '';
		$O('eventName').value = '';
		$O('isAddUp').value = 'Y';
		$O('eventDate').value = '';
			
		return 	false;
	}	
	
	// 数量不足
	if (prom.ret == 2)
	{
		clearProm();
		
		alert('促销活动执行出错：不满足活动数量要求');
		
		return false;	
	}
	
	// 折扣金额大于参加活动商品的总金额
	if (prom.ret == 3)
	{
		clearProm();
		
		alert('促销活动执行出错：折扣金额大于参加活动商品的总金额');
		
		return false;	
	}
		
	$O('promValue').value = prom.promValue;
	$O('isAddUp').value = 'Y';
}


// 选择历史订单
function selectOrder()
{
	
	var customerId = $O('customerId').value;
	
	var eventDate = $O('eventDate').value;	
	
	window.common.modal('../sail/out.do?method=rptQueryHisOut&customerId='+customerId+ '&eventDate='+eventDate +'&load=1&selectMode=0');
}

function getOrder(oos)
{
	
	var hisOrders = '';	
	
	var eventId = $O('eventId').value;
	
	for (var i=0; i< oos.length; i++)
	{
		hisOrders = hisOrders + oos[i].value + '~'; 
	}
	
	$O('refBindOutId').value = hisOrders;
	
	var products = getProducts();
	
	$ajax('../sail/out.do?method=checkPromotionAndRetPromValue&thisOrder='
			+ products + '&hisOrders='+ hisOrders + '&eventId='+ eventId + '&flag=1', callBackProm);						
}

// 新模式的销售，商品根据单据获取，合成与单元产品
function getProducts()
{
	return $$('fullId');
}

function clearProm()
{
	$O('eventId').value = '';
	$O('promValue').value = '0.0';
	$O('refBindOutId').value = '';	
	$O('isAddUp').value = 'Y';
	$O('eventDate').value = '';
	$O('eventName').value = '';	
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
	    
	    var deliverType = document.getElementsByName('deliverType');
	    var expressPay = $O('expressPay');
	    var transportPay = $O('transportPay');
	    
		for (var i=0; i < deliverType.length; i++)
	    {
			var each = deliverType[i];
		
			removeAllItem(each);
			
			setOption(each, '0', '快递');
	    }
	    
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
		
		var deliverType = document.getElementsByName('deliverType');
	    var expressPay = $O('expressPay');
	    var transportPay = $O('transportPay');
	    
		for (var i=0; i < deliverType.length; i++)
	    {
			var each = deliverType[i];
		
			removeAllItem(each);
			
			setOption(each, '1', '货运');
	    }
	    
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
		
		var deliverType = document.getElementsByName('deliverType');
	    var expressPay = $O('expressPay');
	    var transportPay = $O('transportPay');
	    
	    for (var i=0; i < deliverType.length; i++)
	    {
			var each = deliverType[i];
		
			removeAllItem(each);
			
			setOption(each, '0', '快递');
			setOption(each, '1', '货运');
	    }
	    
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
	    
	    var deliverType = document.getElementsByName('deliverType');
	    var expressPay = $O('expressPay');
	    var transportPay = $O('transportPay');
	    
	    for (var i=0; i < deliverType.length; i++)
	    {
			var each = deliverType[i];
		
			removeAllItem(each);
			
			setOption(each, '', '--');
	    }
	    
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
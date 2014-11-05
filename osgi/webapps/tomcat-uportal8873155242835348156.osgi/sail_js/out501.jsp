<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../common/common.jsp"%>

//当前的焦点对象
var oo;
var ids = '';
var amous = '';
var tsts;
var messk = '';
var locationId = '${currentLocationId}';
var currentLocationId = '${currentLocationId}';
var obj;

var showJSON = JSON.parse('${showJSON}');

var invoicesJSON = JSON.parse('${invoicesJSON}');

var vsJSON = JSON.parse('${vsJSON}');

function selectCustomer()
{
    window.common.modal("../client/client.do?method=rptQuerySelfClient&stafferId=${user.stafferId}&load=1");
}

var invMap = {};
var invFullMap = {};
<c:forEach items="${invoiceList}" var="item">
  invFullMap['${item.id}'] = '${item.fullName}';
</c:forEach>

<c:forEach items="${dutyList}" var="item">
  invMap['${item.id}'] = '${item.type}';
</c:forEach>

//默认黑名单
var BLACK_LEVEL = '90000000000000000000';

function getCustomer(oos)
{
    obj = oos;
    
    if ($$('outType') == 3 && obj.pcreditlevelid == BLACK_LEVEL)
    {
        alert('委托代销的时候不能选择黑名单用户');
        
        return;
    }
    
    if ($$('outType') == 4 && obj.pcreditlevelid == BLACK_LEVEL)
    {
        alert('赠送的时候不能选择黑名单用户');
        
        return;
    }
    
    $O("connector").value = obj.pconnector;
    $O("phone").value = obj.phandphone;
    $O("customerName").value = obj.pname;
    $O("customerId").value = obj.value;
    $O("customercreditlevel").value = obj.pcreditlevelid;
    
    if (obj.pcreditlevelid == BLACK_LEVEL || $$('outType') == 2 || ${staffer.black} == 1)
    {
        removeAllItem($O('reserve3'));
        
        setOption($O('reserve3'), '1', '款到发货(黑名单客户)'); 
        
        return;
    }
    
    if ($$('outType') == 4)
    {
        resetReserve3_ZS(); 
    }
    else
    {
        resetReserve3();
    }
}

function resetReserve3()
{
    removeAllItem($O('reserve3'));
        
    setOption($O('reserve3'), '2', '客户信用和业务员信用额度担保');  
    setOption($O('reserve3'), '3', '信用担保');  
    setOption($O('reserve3'), '1', '款到发货(黑名单客户/零售)');  
}

function resetReserve3_ZS()
{
    removeAllItem($O('reserve3'));
        
    setOption($O('reserve3'), '2', '客户信用和业务员信用额度担保');  
}

function total()
{
    var obj = document.getElementsByName("value");

    var total = 0;
    for (var i = 1; i < obj.length; i++)
    {
        if (obj[i].value != '')
        {
            total = add(total, parseFloat(obj[i].value));
        }
    }

    var ss =  document.getElementById("total");
    tsts = formatNum(total, 2);
    ss.innerHTML = '(总计:' + tsts + ')';
}

function titleChange()
{
    
}


function check(isAdd)
{
    var amountList = document.getElementsByName('amount');
    
    if (isAdd)
    {
        for (var i = 0; i < amountList.length; i++)
        {
            amountList[i].ignorecheck = 'true';
        }
    }
    else
    {
         for (var i = 0; i < amountList.length; i++)
        {
            amountList[i].ignorecheck = 'false';
        }
    }
    
    if (!formCheck())
    {
        return false;
    }

    if ($$('outType') != 0)
    {
	    var lock_sw = false;
		
		lock_sw = '${lock_sw}';
		
		if (lock_sw)
		{
			//alert('领样转销售,只能是销售出库.');
			
			//return;
		}
    }

    ids = '';
    amous = '';
    
    $O('priceList').value = '';
    $O('inputPriceList').value = '';
    $O('totalList').value = '';
    $O('nameList').value = '';
    
    $O('otherList').value = '';
    
    $O('desList').value = '';
    $O('showCostList').value = '';
    
    $O('depotList').value = '';
    $O('mtypeList').value = '';
    $O('oldGoodsList').value = '';
    $O('taxrateList').value = '';
    $O('taxList').value = '';
    $O('inputRateList').value = '';
    
    if (trim($O('outTime').value) == '')
    {
        alert('请选择销售日期');
        return false;
    }

    if ($$('outType') == '')
    {
        alert('请选择库单类型');
        return false;
    }

	if ($$('outType') == '4')
    {
    	if ($$('presentFlag') == ''){
    	   alert('赠送单据须选择赠送类型');
           return false;
    	}
    }

    if ($O('customerId').value == '')
    {
        alert('请选择客户');
        return false;
    }

    if ($$('department') == '')
    {
        alert('请选择销售部门');
        return false;
    }

    if (!eCheck([$O('reday')]))
    {
        alert('请填入1到180之内的数字');
        $O('reday').focus();
        return false;
    }

    var proNames = document.getElementsByName('productName');

    var amounts = document.getElementsByName('amount');

    var prices = document.getElementsByName('price');
    
    var taxrates = document.getElementsByName('taxrate');
    
    var taxs = document.getElementsByName('tax');
    
    var values = document.getElementsByName('value');

    //成本
    var desList = document.getElementsByName('costPrice');
    
    var desciprtList = document.getElementsByName('desciprt');
    
    var depotList = document.getElementsByName('locationIds');

    var tmpMap = {};
    
    var proName = '';
    
    for (var i = 1; i < depotList.length; i++)
    {
    	if (depotList[i].value == '')
    	{
    		alert('数据不完整,请选择产品仓库!');
            
            return false;
    	}
    	
        $O('depotList').value = $O('depotList').value + depotList[i].value + '~';
    }
    
    //isNumbers
    for (var i = 1; i < proNames.length; i++)
    {
    	
        if (proNames[i].value == '' || proNames[i].productid == '')
        {
            alert('数据不完整,请选择产品名称!');
            
            return false;
        }

    	if (proNames[i].productid == '9775852' || proNames[i].productid == '9865735')
    	{
    		proNames[i].price = desciprtList[i].value;
    	}

        ids = ids + proNames[i].productid + '~';

        $O('nameList').value = $O('nameList').value +  proNames[i].value + '~';
        
        $O('mtypeList').value = $O('mtypeList').value +  proNames[i].mtype + '~';
        $O('oldGoodsList').value = $O('oldGoodsList').value +  proNames[i].oldgoods + '~';
        
        $O('inputRateList').value = $O('inputRateList').value +  proNames[i].inputrate + '~';
        
        var ikey = toUnqueStr2(proNames[i]);
        
        if (tmpMap[ikey])
        {
            alert('选择的产品重复[仓区+产品+职员+价格],请核实!');
            
            return false;
        }
        
        tmpMap[ikey] = ikey;
        
        //库存重要的标识
        $O('otherList').value = $O('otherList').value + ikey + '~';

        $O('idsList').value = ids;
    }

    for (var i = 1; i < amounts.length; i++)
    {
        if (trim(amounts[i].value) == '')
        {
            alert('数据不完整,请填写产品数量!');
            amounts[i].focus();
            return false;
        }

        if (!isNumbers(amounts[i].value))
        {
            alert('数据错误,产品数量 只能是整数!');
            amounts[i].focus();
            return false;
        }
        
        if (parseInt(amounts[i].value, 10) < 0)
        {
            alert('数据错误,产品数量不能为负数!');
            amounts[i].focus();
            return false;
        }

        amous = amous + amounts[i].value + '~';

        $O('amontList').value = amous;
    }
    

    for (var i = 1; i < prices.length; i++)
    {            
        if (!isFloat(prices[i].value))
        {
            alert('数据错误,产品输入价格只能是浮点数!');
            prices[i].focus();
            return false;
        }
        
        if (parseFloat(trim(prices[i].value)) == 0)
        {
            if (!window.confirm('除非赠品单价不要填0,否则到总裁审批,你确定?'))
            {
                 prices[i].focus();
                 return false;
            }
        }

        $O('priceList').value = $O('priceList').value + prices[i].value + '~';
                
    }

    for (var i = 1; i < taxrates.length; i++)
    {
        if (taxrates[i].value == '')
        {
            alert('请选择税率!');
            taxrates[i].focus();
            return false;
        }
        
        $O('taxrateList').value = $O('taxrateList').value +  taxrates[i].value + '~';
    }
    
    for (var i = 1; i < taxs.length; i++)
    {            
        $O('taxList').value = $O('taxList').value +  taxs[i].value + '~';
    }

	// 成本 
    for (var i = 1; i < desList.length; i++)
    {
    	if (proNames[i].productid == '9775852' || proNames[i].productid == '9865735')
    	{
    		desList[i].value = desciprtList[i].value;
    	}
    
        if (trim(desList[i].value) == '')
        {
            alert('成本是必填!');
            desList[i].focus();
            return false;
        }
        
        if (!isFloat(desList[i].value))
        {
            alert('格式错误,成本只能是浮点数!');
            desList[i].focus();
            return false;
        }
        
        if (parseFloat(trim(desciprtList[i].value)) == 0)
        {
        	alert('成本不能为0!');
        	prices[i].focus();
            return false;
        }
        
        if (parseFloat(trim(prices[i].value)) != 0 
            && (parseFloat(trim(prices[i].value)) < parseFloat(trim(desciprtList[i].value))))
        {
            if (!window.confirm('产品销售价['+prices[i].value+']低于成本价['+desciprtList[i].value+'],你确定?'))
            {
                 prices[i].focus();
                 return false;
            }
        }
    }
    
    for (var i = 1; i < values.length; i++)
    {
        $O('totalList').value = $O('totalList').value + values[i].value + '~';
        
        //真正的成本
        $O('desList').value = $O('desList').value + desList[i].value + '~';
        
        //显示成本
        $O('showCostList').value = $O('showCostList').value + desciprtList[i].value + '~';
    }

    $O('totalss').value = tsts;

    return true;
}

function checkTotal()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return false
	}

    messk = '';
    
    <c:if test="${lock_sw}">
    
     messk += '\r\n';
     
     messk += '由于是领样转销售保存库存后需要提交才能进入销售流程';
    </c:if>
    
    var gh = $O('nameList').value.split('~');
    var ghk = $O('amontList').value.split('~');

    messk += '\r\n';
    for(var i = 0 ; i < gh.length - 1; i++)
    {
        messk += '\r\n' + '产品【' + gh[i] + '】   数量:' + ghk[i];
    }

	if (gh.length == 1){
		alert('销售单明细不能为空');
		window.history.go(-1);
		return false;
	}

    if ($O('saves').value == 'save')
    {
         if (window.confirm("确定保存库单?" + messk))
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
        alert('提示：提交没有方法，请重新登录操作');
        return false;
    }

    if (window.confirm("确定提交库单?" + messk))
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
    
    if (changePrice)
    {
        changePrice();
    }
    
    if (check(true))
    { 	 
    	checkTotal();
		//processProm();
    }
}

function sub()
{
    $O('saves').value = 'submit';
    if (check(false))
    {
       	processProm();
    }
}

function processProm()
{
   	if ($$('outType')==0)
   	{
   		if ($$('hasProm')== '0')
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

function managerChange()
{
     $O('connector').readOnly = true;
     $O('phone').readOnly = true;
     
     $O('connector').oncheck = '';
     $O('phone').oncheck = '';
     
     //价格为0
     var showArr = $("input[name='inputPrice']") ;
     
     for (var i = 0; i < showArr.length; i++)
     {
         var each = showArr[i];
         each.readOnly = false;
     }
        
    //普通销售/委托代销 - 巡展
    if ($$('outType') == 0 || $$('outType') == 3 || $$('outType') == 5)
    {
        $O('customerName').value = '';
        $O('customerId').value = '';
        $O('customerName').disabled  = false;
        $O('reday').value = '';
        $O('reday').readOnly = false;
        
        resetReserve3();
    }
    
    //个人领样
    if ($$('outType') == 1 || $$('outType') == 6)
    {
        $O('customerName').value = '个人领样';
        $O('customerId').value = '99';
        $O('customerName').disabled  = true;
        $O('reday').value = '${goDays}';
        $O('reday').readOnly = true;
        
        resetReserve3();
    }
    
    //零售 是给公共客户的
    if ($$('outType') == 2)
    {
        $O('customerName').value = '公共客户';
        $O('customerId').value = '99';
        $O('customerName').disabled  = true;
        $O('reday').value = '';
        $O('reday').readOnly = false;
        
        //联系人和电话必填
        $O('connector').readOnly = false;
        $O('phone').readOnly = false;
        
        $O('connector').oncheck = 'notNone';
        $O('phone').oncheck = 'notNone';
        
        removeAllItem($O('reserve3'));
        
        setOption($O('reserve3'), '1', '款到发货(黑名单客户/零售)');   
    }
    
     //赠送
    if ($$('outType') == 4)
    {
        $O('customerName').value = '';
        $O('customerId').value = '';
        $O('customerName').disabled  = false;
        $O('reday').value = '1';
        $O('reday').readOnly = true;
        
        resetReserve3_ZS();
        
        //价格为0
        var showArr = $("input[name='inputPrice']") ;
        
        for (var i = 0; i < showArr.length; i++)
	    {
	        var each = showArr[i];
	        each.readOnly = true;
	        each.value = 0.0;
	    }
	    
	    $v('presentTR', true);
    } else {
    	$v('presentTR', false);
    	$O('presentFlag').value = '';
    }
    
    //处理个人黑名单
    if ($$('outType') != 4 && ${staffer.black} == 1)
    {
        removeAllItem($O('reserve3'));
        
        setOption($O('reserve3'), '1', '款到发货(黑名单客户/零售)');   
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
	$ajax('../sail/out.do?method=checkPromotionAndRetPromValue&thisOrder='
			+ products + '&hisOrders='+ hisOrders + '&eventId='+ eventId + '&flag=0', callBackProm);

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


function getProducts()
{
	var products = '';
	
	var proNames = document.getElementsByName('productName');
    
    var amounts = document.getElementsByName('amount');

	var values = document.getElementsByName('value');
	
	//i 从1开始 ??
 	for (var i = 1; i < proNames.length; i++)
    {
        var productid = proNames[i].productid;
        
        var amount = amounts[i].value;
        
        var outValue = values[i].value;
        
        products = products + productid + '-' + amount + '-' + outValue + '~';

	}
	
	return products;
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

function checkCurrentUser()
{	
     // check
     var elogin = "${g_elogin}";

 	 //  商务登陆
     var top = window.top.topFrame.document;
     //var allDef = window.top.topFrame.allDef;
     var srcStafferId = top.getElementById('srcStafferId').value;
    
     var currentStafferId = "${g_stafferBean.id}";

     var currentStafferName = "${g_stafferBean.name}";

     if (srcStafferId && srcStafferId != currentStafferId)
     {

          alert('请不要同时打开多个OA连接，且当前登陆者不同，当前登陆者应为：' + currentStafferName);
          
          return false;
     }

	return true;
}



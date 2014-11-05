<%@ page contentType="text/html;charset=UTF-8" language="java"%>

//当前的焦点对象
var oo;

var ids = '';
var amous = '';
var tsts;
var messk = '';
var locationId = '${currentLocationId}';
var currentLocationId = '${currentLocationId}';

var showJSON = JSON.parse('${showJSON}');

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
    if ($O('outType'))
    {
        removeOption($O('outType'), 0);
        removeOption($O('outType'), 4);
        removeOption($O('outType'), 5);
    }
}




function load()
{
    titleChange();
    
    loadForm();
    
    managerChange();
    
    forceBuyTypeChange();
}

function check()
{
    if (!formCheck())
    {
        return false;
    }
    
    if ($$('outType') == 1 && $$('destinationId') == $$('location'))
    {
        alert('源仓库和目的仓库不能相同');
        return false;
    }
    
    //只能选择永银和经纬 90201008080000000001/A1201112260004531364
    if ($$('outType') == 1 || true)
    {
        if ($$('dutyId') != '90201008080000000001' && $$('dutyId') != 'A1201112260004531364')
        {
            alert('入库单的时候纳税实体只能选择永银收藏品或者经纬公司');
            return false;
        }
    }

    ids = '';
    amous = '';
    
    $O('priceList').value = '';
    $O('totalList').value = '';
    $O('nameList').value = '';
    $O('unitList').value = '';
    $O('otherList').value = '';
    $O('showIdList').value = '';
    $O('showNameList').value = '';
    $O('desList').value = '';
    
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
    
    //调拨的时候只能选择永银和乐丰 90201008080000000001/A1201112260004531364
    if ($$('outType') == 1)
    {
        if ($$('dutyId') != '90201008080000000001' && $$('dutyId') != 'A1201112260004531364')
        {
            alert('调拨的时候纳税实体只能选择永银收藏品或者经纬公司');
            return false;
        }
    }

    var proNames = document.getElementsByName('productName');
    var units = document.getElementsByName('unit');
    var amounts = document.getElementsByName('amount');
    var prices = document.getElementsByName('price');
    var values = document.getElementsByName('value');
    var outProductNames = document.getElementsByName('outProductName');

    var tmpMap = {};
    //isNumbers
    for (var i = 1; i < proNames.length; i++)
    {
        if (proNames[i].value == '' || proNames[i].productid == '')
        {
            alert('数据不完整,请选择产品名称!');
            
            return false;
        }

        ids = ids + proNames[i].productid + '~';

        $O('nameList').value = $O('nameList').value +  proNames[i].value + '~';
        
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

        amous = amous + amounts[i].value + '~';

        $O('amontList').value = amous;
    }
    
    for (var i = 1; i < outProductNames.length; i++)
    {
        if (trim(outProductNames[i].value) == '')
        {
            alert('数据不完整,请选择!');
            outProductNames[i].focus();
            return false;
        }

        amous = amous + amounts[i].value + '~';

        $O('showIdList').value =  $O('showIdList').value + outProductNames[i].value + '~';
        
        $O('showNameList').value =  $O('showNameList').value + getOptionText(outProductNames[i]) + '~';
    }

    for (var i = 1; i < prices.length; i++)
    {
        if (trim(prices[i].value) == '')
        {
            alert('数据不完整,请填写产品价格!');
            prices[i].focus();
            return false;
        }
        
        if (!isFloat(prices[i].value))
        {
            alert('数据错误,产品数量只能是浮点数!');
            prices[i].focus();
            return false;
        }

        $O('priceList').value = $O('priceList').value + prices[i].value + '~';
    }

    var desList = document.getElementsByName('desciprt');
    
    for (var i = 1; i < desList.length; i++)
    {
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
        
        if ($$('outType') != 1 && parseFloat(trim(desList[i].value)) == 0)
        {
            alert('入库成本价格不能为0!');
            desList[i].focus();
            return false;
        }
    }
    
    for (var i = 1; i < values.length; i++)
    {
        $O('totalList').value = $O('totalList').value + values[i].value + '~';
        $O('desList').value = $O('desList').value + desList[i].value + '~';
    }

    for (var i = 1; i < units.length; i++)
    {
        $O('unitList').value = $O('unitList').value + units[i].value + '~';
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
    var gh = $O('nameList').value.split('~');
    var ghk = $O('amontList').value.split('~');

    messk += '\r\n';
    for(var i = 0 ; i < gh.length - 1; i++)
    {
        messk += '\r\n' + '产品【' + gh[i] + '】   数量:' + ghk[i];
    }


    if ($O('saves').value == 'save')
    {
         if (window.confirm("入库单所有类型都是正数增加库存，负数减少库存，您确认填写的调出符合实际情形,确定保存入库单?" + messk))
         {
            disableAllButton();
            outForm.submit();
         }

         return;
    }

    ccv = $$('location');

    if (ccv == '')
    {
        alert('产品仓库为空，请核实');
        return false;
    }


    if (window.confirm("入库单所有类型都是正数增加库存，负数减少库存，您确认填写的调出符合实际情形,确定提交库单?" + messk))
    {
        disableAllButton();
        outForm.submit();
    }
}

function save()
{
    $O('saves').value = 'save';
    if (check())
    {
        checkTotal();
    }
}

function sub()
{
    $O('saves').value = 'submit';
    if (check())
    {
        checkTotal();
    }
}

var modifyPage = ('${bean.fullId}' != '');

var g_url_query = 0;

function managerChange()
{
    g_url_query = 0;
    
    //调拨
    if ($$('outType') == 2 || $$('outType') == 3 || $$('outType') == 6 )
    {
        showTr('dir_tr', false);
        showTr('forceBuy_tr', false);
        showTr('refOutFullId_tr', false);
        showTr('staffer_tr', false);
        showTr('customer_tr', false);
    }
    
    if ($$('outType') == 1)
    {
        showTr('dir_tr', true);
        showTr('forceBuy_tr', false);
        showTr('refOutFullId_tr', false);
        showTr('staffer_tr', false);
        showTr('customer_tr', false);
    }
    
    //报废
    if ($$('outType') == 2 || $$('outType') == 3)
    {
        showTr('duty_tr', true);
        showTr('invoice_tr', false);
    }
    
    //调拨处理
    if ($$('outType') == 1)
    {
         showTr('duty_tr', true);
         showTr('invoice_tr', false);
    }
    
    if ($$('outType') == 6)
    {
        showTr('pro_tr', true);
        showTr('duty_tr', true);
        showTr('invoice_tr', true);
        $O('customerName').oncheck = 'notNone';
    }
    else
    {
        showTr('pro_tr', false);
        showTr('invoice_tr', false);
        $O('customerName').oncheck = '';
         	 
    }

    if ($$('outType') == 99)
    {
        var nameList = document.getElementsByName("price");
        
        for (var i = 0; i < nameList.length; i++)
        {
            nameList[i].readOnly = false;
        }
        
        //desciprt
        nameList = document.getElementsByName("desciprt");
        
        for (var i = 0; i < nameList.length; i++)
        {
            nameList[i].readOnly = false;
        }
        
        g_url_query = 1;
        
	   	showTr('forceBuy_tr',true);
	   	showTr('refOutFullId_tr',true);
	   	showTr('dir_tr',false);
        showTr('invoice_tr', false);
        showTr('staffer_tr',false);
        showTr('customer_tr',false);
	
		titleChange2();
	
        if ($$('forceBuyType') != -1)
        {
        	forceBuyTypeChange();	
        } 
    }
    else
    {    
	    // 作废
	    if ($$('outType') == 2)
	    {
	    	titleChange2();
	    	showTr('staffer_tr', true);
	    	showTr('forceBuy_tr',true);
	    	
	    	showTr('table_tr', true);
	        showTr('button_tr', true);	
	    }else
	    {
		    var nameList = document.getElementsByName("price");
	        
	        for (var i = 0; i < nameList.length; i++)
	        {
	            nameList[i].readOnly = true;
	            
	            if (!modifyPage)
	            {
	                nameList[i].value = '0.0';
	                cc(nameList[i]);
	            }
	        }
	        
	        nameList = document.getElementsByName("desciprt");
	        
	        for (var i = 0; i < nameList.length; i++)
	        {
	            nameList[i].readOnly = true;
	            
	            if (!modifyPage)
	            {
	                nameList[i].value = '0.0';
	            }
	        }
	        
	         showTr('forceBuy_tr',false);
	    	 showTr('refOutFullId_tr',false);
	         showTr('staffer_tr',false);
	         showTr('customer_tr',false);   
	         
	       	 showTr('table_tr', true);
	         showTr('button_tr', true);	
	    }
        
    }
}

function showTr(id, show)
{
    $v(id, show);
    $d(id, !show);
}

var flag;
function selectCustomer()
{
	flag = 0;
    window.common.modal('../finance/finance.do?method=rptQueryUnit&load=1');
}

function getUnit(oo)
{
	if (flag == 0)
	{
	    $O('customerId').value = oo.value;
    	$O('customerName').value = oo.pname;
	}else
	{
		$O('customerId').value = oo.value;
    	$O('customerName1').value = oo.pname;
	}

}

function selectCustomer1()
{
	flag = 1;
    window.common.modal('../finance/finance.do?method=rptQueryUnit&load=1');
}

function selectStaffer()
{
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
    var oo = oos[0];
    
    $O('stafferName1').value = oo.pname;
    // reserve9 存放业务员
    $O('reserve9').value = oo.value;
}

function forceBuyTypeChange()
{
	
	// 其它入库
	if ($$('outType') == 99)
	{
	 if ($$('forceBuyType') == 0 || $$('forceBuyType') == 1 || $$('forceBuyType') == 3 )
	    {		
	    	showTr('table_tr', false);
	        showTr('button_tr', false);
	    
	        showTr('refOutFullId_tr', true);
	        showTr('staffer_tr', false);
	        showTr('customer_tr', false);
	        $O('customerId').value = '';
	        $O('customerName').value = '';
	        $O('stafferName').value = '';	        
	        $O('reserve9').value = '';	        

	    }

	 if ($$('forceBuyType') == 4 || $$('forceBuyType') == 5 || $$('forceBuyType') == 7 )
	    {
	       	showTr('table_tr', true);
	        showTr('button_tr', true);
	        
	        showTr('refOutFullId_tr', false);
	        showTr('staffer_tr', false);
	        showTr('customer_tr', false);
	        $O('customerId').value = '';
	        $O('customerName').value = '';
	        $O('stafferName').value = '';	        
	        $O('reserve9').value = '';
	        $O('refOutFullId').value = '';
	        
	    }

	 if ($$('forceBuyType') == 2 )
	    {	    
	       	showTr('table_tr', true);
	        showTr('button_tr', true);
	        
	        showTr('refOutFullId_tr', false);
	        showTr('staffer_tr', true);
	        showTr('customer_tr', true);
	        $O('refOutFullId').value = '';
	        
	    }  

	 if ($$('forceBuyType') == 6 )
	    {
	       	showTr('table_tr', true);
	        showTr('button_tr', true);	  
	        
	        showTr('refOutFullId_tr', false);
	        showTr('staffer_tr', true);
	        showTr('customer_tr', false);
	        $O('refOutFullId').value = '';
	        $O('customerId').value = '';
	        $O('customerName').value = '';
	              
	    }
	    
	 }
	 
	 // 报废
	 if ($$('outType')== 2)
	 {
	 	//
	 }   
}

function titleChange2()
{
	if ($O('fullId'))
	{
	 // do noting
	}else
	{	
		removeAllItem($O('forceBuyType'));
		
	    if ($$('outType') == 99)
	    {
	    	setOption($O('forceBuyType'), 0, '20110401前销售单退货');
	        setOption($O('forceBuyType'), 1, '纳税实体错误');
	        setOption($O('forceBuyType'), 2, '原单找不到');
	        setOption($O('forceBuyType'), 3, '转客户或业务员');
	        setOption($O('forceBuyType'), 4, '调品名');
	        setOption($O('forceBuyType'), 5, '不良品拆解');
	        setOption($O('forceBuyType'), 6, '私库转公库');
	        setOption($O('forceBuyType'), 7, '调整库存属性');
	    }
	    
	    if ($$('outType') == 2)
	    {
	        setOption($O('forceBuyType'), 50, '物流报废');
	        setOption($O('forceBuyType'), 51, '事业部报废' );
	        setOption($O('forceBuyType'), 52, '与事业部共同报废');
	        setOption($O('forceBuyType'), 53, '总部报废');
	        setOption($O('forceBuyType'), 54, '业务员报废');
	        setOption($O('forceBuyType'), 55, '配件报废');
	    }
    }
}

function checkCurrentUser()
{	
     // check
     var elogin = "${g_elogin}";

 	 //  商务登陆
     //if (elogin == '1')
     //{
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
     //}

	return true;
}

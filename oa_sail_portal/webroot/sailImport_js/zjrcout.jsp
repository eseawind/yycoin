<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../common/common.jsp"%>

//当前的焦点对象
var oo;
var ids = '';
var amous = '';
var tsts;
var messk = '';
var obj;


//默认黑名单
var BLACK_LEVEL = '90000000000000000000';


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

    ids = '';
    amous = '';
    
    $O('priceList').value = '';
    $O('totalList').value = '';
    $O('nameList').value = '';
    $O('costpriceList').value = '';
    $O('midrevenueList').value = '';
    
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
    
    //校验手机与固定电话
    var mobile = $$('handPhone');
    
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

    var proNames = document.getElementsByName('productName');
    var amounts = document.getElementsByName('amount');
    var prices = document.getElementsByName('price');
    var values = document.getElementsByName('value');

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
        $O('costpriceList').value = $O('costpriceList').value +  proNames[i].costprice + '~';
        $O('midrevenueList').value = $O('midrevenueList').value +  proNames[i].midrevenue + '~';
        
        var ikey = toUnqueStr2(proNames[i]);
        
        if (tmpMap[ikey])
        {
            alert('选择的产品重复[产品],请核实!');
            
            return false;
        }
        
        tmpMap[ikey] = ikey;
        
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
        if (trim(prices[i].value) == '')
        {
            alert('数据不完整,请填写产品价格!');
            prices[i].focus();
            return false;
        }
        
        if (!isFloat(prices[i].value))
        {
            alert('数据错误,产品价格只能是浮点数!');
            prices[i].focus();
            return false;
        }
        
        $O('priceList').value = $O('priceList').value + prices[i].value + '~';
    }

    for (var i = 1; i < values.length; i++)
    {
        $O('totalList').value = $O('totalList').value + values[i].value + '~';
    }

	$O('totalss').value = tsts;

    return true;
}

function checkTotal()
{
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
         if (window.confirm("确定保存库单?" + messk))
         {
            disableAllButton();
            outForm.submit();
         }

         return;
    }

    //判断method
    //if ($$('method') != 'addZJRCOut')
    //{
        //alert('提示：提交没有方法，请重新登录操作');
       // return false;
    //}

    if (window.confirm("确定提交库单?" + messk))
    {
        disableAllButton();
        outForm.submit();
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
    }
}

function sub()
{
    $O('saves').value = 'submit';
    if (check(false))
    {
        checkTotal();
    }
}


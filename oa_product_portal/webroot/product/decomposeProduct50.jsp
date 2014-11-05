<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="拆分产品" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../product_js/composeProduct.js"></script>
<script language="javascript">

function addBean()
{
    submit('确定拆分产品?', null, checks);
}

function checks()
{
	var srs = document.getElementsByName('srcProductId');
	
	var ret = $duplicate(srs);
	
	if (ret)
	{
		alert('相同产品不能在同一个储位');

		return !ret;
	}
	
    var stypes = document.getElementsByName('stype');
    var srcDepots = document.getElementsByName('srcDepot');
    var srcDepotparts = document.getElementsByName('srcDepotpart');
    var srcAmounts = document.getElementsByName('srcAmount');
    var srcPrices = document.getElementsByName('srcPrice');

	var total = 0;

	for (var i = 0; i < srs.length - 1; i++)
    {
        // 库存类型
        if (stypes[i].value == '0')
        {
            if (srcDepots[i].value == '' || srcDepotparts[i].value == '')
            {
            	alert('库存类型时,请选择仓库及仓区');
                return false;
            }
        }else{
        	srcDepots[i].value = '';
        	srcDepotparts[i].value = '';
        }
        
        total += parseFloat(srcAmounts[i].value) * parseFloat(srcPrices[i].value);
    }

    var money = 0;

    money = parseFloat($$('amount')) * parseFloat($$('price'));

    if (compareDouble(total, money) != 0)
    {
    	alert('配件成本之和要与成品成本一致');
        return false;
    }
    
    return true;
}

var current;

var flag = 0;
function selectProduct(obj)
{
	flag = 0;
	
    if ($$('depot') == '')
    {
        alert('请选择仓区');
        return;
    }

    //查询拆分产品列表
    window.common.modal('../depot/storage.do?method=rptQueryStorageRelationInDepot&load=1&selectMode=1&depotId='
            + $$('depot') + '&depotpartId=' + $$('depotpart') + '&ctype=1' + '&init=1');
    
}

function getEle(eles, name)
{
	for (var i = 0; i < eles.length; i++)
	{
		if (eles[i].name == name)
		{
			return eles[i];
		}
	}
	
	return null;
}

function getProductRelation(oos)
{
	var oo = oos[0];
	
	$O('depotpart').value = oo.pdepotpartid;
	$O('productName').value = oo.pname;
	$O('productId').value = oo.ppid;
	$O('amount').value = oo.pamount;
	$O('mayAmount').value = oo.pamount;
	$O('price').value = oo.prealprice;
}

function selectDepotpartProduct(obj)
{
    current = obj;
    
    if ($$("productName") == '')
    {
        alert("请选择拆分产品");
        return;
    }
    
 	//查询配件产品列表
   //window.common.modal(RPT_PRODUCT);
   window.common.modal('../product/product.do?method=rptQueryProduct&load=1&selectMode=1&abstractType=0&status=0');
}

function getProduct(oos)
{
	var oo = oos[0];
	
	current.value = oo.pname;
	
	var tr = getTrObject(current);
	
	var eles = tr.getElementsByTagName('input');
    
    var hobj = getEle(eles, "srcProductId");
    
    hobj.value = oo.value;
}

function getNextInput(el)
{
    if (el.tagName && el.tagName.toLowerCase() == 'input')
    {
        return el;
    }
    else
    {
        return getNextInput(el.nextSibling);
    }
}

var dList = JSON.parse('${depotpartListStr}');

function getDepartmentId(obj)
{
    var tr = getTrObject(obj);
    
    if (tr != null)
    {
    	return tr.getElementsByTagName('select')[2];
    }
}

function getDepotId(obj)
{
    var tr = getTrObject(obj);
    
    if (tr != null)
    {
    	return tr.getElementsByTagName('select')[1];
    }
}

function depotpartChange(obj)
{
    var tr = getTrObject(obj);
    
    var inputs = tr.getElementsByTagName('input');
    
    for (var i = 0 ; i < inputs.length; i++)
    {
        var oo = inputs[i];

        if (oo.type.toLowerCase() != 'button')
        {
            oo.value = '';
        }
    }
}

function depotChange()
{
	var newsrcDepot = $$('depot');

	removeAllItem($O('depotpart'));
	
	//add new option
	for (var j = 0; j < dList.length; j++)
	{
		if (dList[j].locationId == newsrcDepot)
		{
			setOption($O('depotpart'), dList[j].id, dList[j].name);
		}
	}
	
	depotpartChange($O('depotpart'));
}

function srcDepotChange(obj)
{
	var newsrcDepot = obj.value;

	var tr = getTrObject(obj);
    
    var selects = tr.getElementsByTagName('select');

    for (var i = 0 ; i < selects.length; i++)
    {
        var oo = selects[i];

        if (oo.name == 'srcDepotpart')
        {
        	removeAllItem(oo);

        	//add new option
        	for (var j = 0; j < dList.length; j++)
        	{
        		if (dList[j].locationId == newsrcDepot)
        		{
        			setOption(oo, dList[j].id, dList[j].name);
        		}
        	}

        	break;
        }
    }
}

function selectSrcProduct()
{
	var productId = $$('productId');

	if (productId == '')
	{
		alert('请选择 要拆分的产品');
		return;
	}

	// 
	window.common.modal('../product/product.do?method=rptQueryComposeProduct&load=1&selectMode=0&productId=' + productId);
}

function getComposeProduct(oos)
{
	for(var i = 0; i < oos.length; i++)
	{
		var trow = addTrInner();
		
		setInputValueInTr(trow, 'srcProductId', oos[i].value);
		setInputValueInTr(trow, 'srcProductName', oos[i].pname);
	}
}

function load()
{
	addTr1();
	
	depotChange();
}

function addTr1()
{
    for (var k = 0; k < 2; k++)
    {
        var trow = addTrInner();

        var selects = trow.getElementsByTagName('select');

        for (var i = 0 ; i < selects.length; i++)
        {
            var oo = selects[i];

            if (oo.name == 'srcDepotpart')
            {
            	removeAllItem(oo);

            	//add new option
            	for (var j = 0; j < dList.length; j++)
            	{
            		if (dList[j].locationId == '99000000000000000002')
            		{
            			setOption(oo, dList[j].id, dList[j].name);
            		}
            	}

            	break;
            }
        }
    }

    loadForm();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../product/product.do" method="post"><input
	type="hidden" name="method" value="deComposeProduct"> 

<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javaScript:window.history.go(-1);">产品管理</span> &gt;&gt; 产品拆分</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
			<strong>成品</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">
			<p:tr align="left">
			成品仓库：
			<select name="depot" class="select_class" style="width: 15%;" onchange="depotChange()" oncheck="notNone">
		         <c:forEach var="item" items="${depotList}">
		             <option value="${item.id}">${item.name}</option>
		         </c:forEach>
	     	</select>
			成品仓区：
			<select name="depotpart" class="select_class" style="width: 20%;" onchange="depotpartChange(this)" oncheck="notNone">
		         <option value="">--</option>
		         <c:forEach var="item" items="${depotpartList}">
		             <option value="${item.id}">${item.name}</option>
		         </c:forEach>
	         </select>
			拆分产品：<input type="text" style="width: 20%;cursor: pointer;" readonly="readonly" value="" oncheck="notNone" name="productName" 
			onclick="selectProduct(this)">
         <input type="hidden" name="productId" value="">
         	数量：<input type="text" style="width: 5%"
                    name="amount" value="" oncheck="notNone;isNumber;range(1)">
                    <input type="hidden" name="mayAmount" value=""/>
			成本：<input type="text" style="width: 6%" readonly="readonly"
                    name="price" value="" oncheck="notNone;isFloat">                    
			</p:tr>
			<p:tr align="right">
				<input type="button" class="button_class" id="ref_b"
            			value="&nbsp;配件产品查询&nbsp;" onclick="selectSrcProduct()">
			</p:tr>
		</p:table>
	</p:subBody>
	
	<p:title>
        <td class="caption">
         <strong>配件产品</strong>
        </td>
    </p:title>
	
	<tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="5%" align="center">类型</td>
                        <td width="15%" align="center">仓库</td>
                        <td width="15%" align="center">仓区</td>
                        <td width="30%" align="center">产品</td>
                        <td width="5%" align="center">数量</td>
                        <td width="5%" align="center">成本</td>
                        <td width="5%" align="left"><input type="button" accesskey="A"
                            value="增加" class="button_class" onclick="addTr1()"></td>
                    </tr>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		  <input type="button" class="button_class" id="sub_b"
            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean()">
        </div>
	</p:button>
	
	<p:message2/>
</p:body>
</form>
<table>
    <tr class="content1" id="trCopy" style="display: none;">
         <td width="100%" align="center">
         <select name="stype" class="select_class" style="width: 100%;" onchange="stypeChange(this)" oncheck="notNone" values="0">
         <option value="">--</option>
         <option value="0">库存</option>
         <option value="1">费用</option>
         </select>
         </td>
         <td width="95%" align="center">
         <select name="srcDepot" class="select_class" style="width: 100%;" onchange="srcDepotChange(this)" values="99000000000000000002">
         <option value="">--</option>
         <c:forEach var="item" items="${depotList}">
             <option value="${item.id}">${item.name}</option>
         </c:forEach>
         </select>
         </td>
         <td width="95%" align="center">
         <select name="srcDepotpart" class="select_class" style="width: 100%;" values="A1201207271106788121">
         <option value="">--</option>
         </select>
         </td>
         <td width="30%" align="center"><input type="text" 
         style="width: 100%;cursor: pointer;" readonly="readonly" value="" oncheck="notNone" name="srcProductName" onclick="selectDepotpartProduct(this)">
         <input type="hidden" name="srcProductId" value="">
         </td>
         <td width="15%" align="center"><input type="text" style="width: 100%"
                    name="srcAmount" value="" oncheck="notNone;isNumber"></td>
         <td width="15%" align="center"><input type="text" style="width: 100%"
                    name="srcPrice" value="" oncheck="notNone;isFloat">
         </td>
        <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
</table>
</body>
</html>


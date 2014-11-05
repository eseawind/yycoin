<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="金银料入库" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../product_js/composeProduct.js"></script>
<script language="javascript">

function addBean(type)
{
	$O('save').value = type;
	
    submit('确定提交?', null, checks);
}

function checks()
{
	var srs = document.getElementsByName('srcProductId');
	
    var srcDepots = document.getElementsByName('srcDepot');
    var srcDepotparts = document.getElementsByName('srcDepotpart');
    var srcAmounts = document.getElementsByName('srcAmount');
    var goldWeights = document.getElementsByName('goldWeight');
    var silverWeights = document.getElementsByName('silverWeight');
    
	var gotal = 0;
	var stoal = 0;

	for (var i = 0; i < srs.length - 1; i++)
    {
        // 库存类型
        if (srcDepots[i].value == '' || srcDepotparts[i].value == '')
        {
        	alert('请选择仓库及仓区');
            return false;
        }

        gotal += parseInt(srcAmounts[i].value, 10) * parseInt(goldWeights[i].value, 10);
        stoal += parseInt(srcAmounts[i].value, 10) * parseInt(silverWeights[i].value, 10);
    }

    if (gotal != parseInt($$('gtotal'), 10))
    {
    	alert('金料入库克数须与出库克数一致');
        return false;
    }

    if (stoal != parseInt($$('stotal'), 10))
    {
    	alert('银料入库克数须与出库克数一致');
        return false;
    }
    
    return true;
}

var current;

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

function selectDepotpartProduct(obj)
{
    current = obj;
    
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

function load()
{
	depotChange();
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../depot/storage.do" method="post"><input
	type="hidden" name="method" value="handleGSOut"> 
	<input type=hidden name="type" value='1' />
	<input type=hidden name="save" value='0' />
	<input type=hidden name="id" value='${bean.id}' />
	<input type=hidden name="gtotal" value='${bean.gtotal}' />
	<input type=hidden name="stotal" value='${bean.stotal}' />
	

<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javaScript:window.history.go(-1);">产品管理</span> &gt;&gt; 金银料入库</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:subBody width="98%">
		<p:table cells="1">
			<p:cell title="日期">
				${bean.outTime}
			</p:cell>
			
			<p:cell title="申请人">
				${user.stafferName}
			</p:cell>
			
			<p:cell title="原出库单">
				${bean.id}
			</p:cell>
			
			<p:cell title="金料总克数">
				${bean.gtotal}
			</p:cell>
			
			<p:cell title="银料总克数">
				${bean.stotal}
			</p:cell>
			
			<p:cell title="备注" end="true">
				<textarea rows=3 cols=55 oncheck="maxLength(200);" name="description"></textarea>
			</p:cell>
		
		</p:table>
	</p:subBody>
	
	<p:title>
        <td class="caption">
         <strong>入库明细</strong>
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
                        <td width="15%" align="center">仓库</td>
                        <td width="15%" align="center">仓区</td>
                        <td width="30%" align="center">产品</td>
                        <td width="5%" align="center">数量</td>
                        <td width="5%" align="center">金料(克)</td>
                        <td width="5%" align="center">金料成本</td>
                        <td width="5%" align="center">银料(克)</td>
                        <td width="5%" align="center">银料成本</td>
                    </tr>
                    <tr class="content2">
	                    <td align="center">
				         <select name="srcDepot" class="select_class" style="width: 100%;" onchange="srcDepotChange(this)" >
				         <option value="">--</option>
				         <c:forEach var="item1" items="${depotList}">
				             <option value="${item1.id}">${item1.name}</option>
				         </c:forEach>
				         </select>
				         </td>
				         <td align="center">
				         <select name="srcDepotpart" class="select_class" style="width: 100%;" >
				         <option value="">--</option>
				         </select>
				         </td>
				         <td align="center"><input type="text" 
				         style="width: 100%;cursor: pointer;" readonly="readonly" value="" oncheck="notNone" name="srcProductName" onclick="selectDepotpartProduct(this)">
				         <input type="hidden" name="srcProductId" value="">
				         </td>
				         <td align="center"><input type="text" style="width: 100%"
				                    name="srcAmount" value="" oncheck="notNone;isNumber;range(1)">
				         </td>
				         <td align="center"><input type="text" style="width: 100%"
				                    name="goldWeight" value="0" oncheck="notNone;isNumber">
				         </td>
				          <td align="center"><input type="text" style="width: 100%"
				                    name="goldPrice" value="${firstItem.goldPrice}" oncheck="notNone;isFloat"  readonly>
				         </td>
				         <td align="center"><input type="text" style="width: 100%"
				                    name="silverWeight" value="0" oncheck="notNone;isNumber">
				         </td>
				         <td align="center"><input type="text" style="width: 100%"
				                    name="silverPrice" value="${firstItem.silverPrice}" oncheck="notNone;isFloat" readonly>
				         </td>
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
            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean(1)">
        </div>
	</p:button>
	
	<p:message2/>
</p:body>
</form>
<table>
    <tr class="content1" id="trCopy" style="display: none;">
         <td width="95%" align="center">
         <select name="srcDepot" class="select_class" style="width: 100%;" onchange="srcDepotChange(this)" >
         <option value="">--</option>
         <c:forEach var="item" items="${depotList}">
             <option value="${item.id}">${item.name}</option>
         </c:forEach>
         </select>
         </td>
         <td width="95%" align="center">
         <select name="srcDepotpart" class="select_class" style="width: 100%;" >
         <option value="">--</option>
         </select>
         </td>
         <td width="30%" align="center"><input type="text" 
         style="width: 100%;cursor: pointer;" readonly="readonly" value="" oncheck="notNone" name="srcProductName" onclick="selectDepotpartProduct(this)">
         <input type="hidden" name="srcProductId" value="">
         </td>
         <td width="15%" align="center"><input type="text" style="width: 100%"
                    name="srcAmount" value="" oncheck="notNone;isNumber;range(1)">
         </td>
         <td width="15%" align="center"><input type="text" style="width: 100%"
                    name="goldWeight" value="0" oncheck="notNone;isNumber">
         </td>
         <td width="15%" align="center"><input type="text" style="width: 100%"
                    name="silverWeight" value="0" oncheck="notNone;isNumber">
         </td>
    </tr>
</table>
</body>
</html>


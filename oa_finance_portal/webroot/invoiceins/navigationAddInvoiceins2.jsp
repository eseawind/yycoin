<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加发票" />
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/math.js"></script>
<script src="../js/json.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/adapter.js"></script>
<script language="javascript">

var invoicesJSON = JSON.parse('${invoicesJSON}');
var vsJSON = JSON.parse('${vsJSON}');
var showJSON = JSON.parse('${showJSON}');

var invMap = {};
var invFullMap = {};
<c:forEach items="${invoiceList}" var="item">
  invFullMap['${item.id}'] = '${item.fullName}';
</c:forEach>

<c:forEach items="${dutyList}" var="item">
  invMap['${item.id}'] = '${item.type}';
</c:forEach>

var msg = '开单纳税实体与开票纳税实体不一致，审批过程长，请慎重选择。';

var errorType = '${errorType}';

function addBean()
{
    if ('1' == errorType)
    {
	   submit(msg + '确定申请增加发票?', null, check);
	}
	else
	{
	   submit('确定申请增加发票?', null, check);
	}
}

function check()
{
    var totalArr = document.getElementsByName('total');

    var totals = 0.0;
    
    for (var i = 0; i < totalArr.length; i++)
    {
        totals += parseFloat(totalArr[i].value);        
    }
    
    $O('hasMoney').value = formatNum(totals, 2);
    
    if (parseFloat($$('hasMoney')) > parseFloat($$('mayMoney')))
    {
        alert('开票金额不能大于单据的可开票金额');
        
        return false;
    }

    var vsMayMoneysArr = document.getElementsByName('vsMayMoneys');

    var vsMoneysArr = document.getElementsByName('vsMoneys');

    var totals2 = 0.0;
    
    if (vsMayMoneysArr)
    {
    	for (var i = 0; i < vsMayMoneysArr.length; i++)
    	{
        	if (parseFloat(vsMoneysArr[i].value) > parseFloat(vsMayMoneysArr[i].value))
        	{
        		alert('销售单中开票金额不能大于可开票金额');
                
                return false;
        	}

        	totals2 += parseFloat(vsMoneysArr[i].value);
    	}
    }
    
    if (parseFloat(formatNum(totals2, 2)) != parseFloat($$('hasMoney')))
    {
        alert('销售单中开票总金额不等于开票明细中总金额');
        
        return false;
    }
    
    return true;
}

function check()
{
    var priceArr = document.getElementsByName('price');

    var backUnmArr = document.getElementsByName('backUnm');

	var showNameArr = document.getElementsByName('showName');
    
    var totals = 0.0;
    
    for (var i = 0; i < priceArr.length; i++)
    {
		var backUnm = parseInt(backUnmArr[i].value, 10);
		
		if (backUnm > 0)
		{
			if (showNameArr[i].value == '')
			{
				alert('填写了开票数量处，开票品名不可为空');

				$f(showNameArr[i]);
				
				return false;
			}
			
			var price = parseFloat(priceArr[i].value);
			
			totals += mul(price, backUnm);	
		}
		else
			continue;
    }
    
    $O('hasMoney').value = formatNum(totals,2);

    if (totals == 0)
    {
        alert('当前开票金额为0，请输入开票数量.');

        return false;
    }

    var vsMayMoneysArr = document.getElementsByName('vsMayMoneys');

    var vsMoneysArr = document.getElementsByName('vsMoneys');

    var totals2 = 0.0;
    
    if (vsMayMoneysArr)
    {
    	for (var i = 0; i < vsMayMoneysArr.length; i++)
    	{
        	if (parseFloat(vsMoneysArr[i].value) > parseFloat(vsMayMoneysArr[i].value))
        	{
        		alert('销售单中开票金额不能大于可开票金额');
                
                return false;
        	}

        	totals2 += parseFloat(vsMoneysArr[i].value);
    	}
    }
    
    if (parseFloat(formatNum(totals2, 2)) != parseFloat($$('hasMoney')))
    {
        alert('销售单中开票总金额不等于开票明细中总金额');
        
        return false;
    }
    
    return true;
}

function loadShow()
{
	var json = showJSON;
	
	var pid = $$('dutyId');
	
	var showArr = document.getElementsByName('showName');

	var defaultValue = '';
	
	for (var i = 0; i < showArr.length; i++)
	{
		var each = showArr[i];
		
		removeAllItem(each);
		
		//setOption(each, '', '--');
		
		for (var j = 0; j < json.length; j++)
		{
			var item = json[j];
			
			if (item.dutyId == pid)
			{
				setOption(each, item.id, item.name);

				if (item.name == '纪念品')
					defaultValue = item.id;
			}
		}

		if (defaultValue != '')
		{
			setSelect(each, defaultValue);
		}
	}
}

function dialog_open()
{
    $v('dia_inner', true);
}

function addTr()
{
	addTrInner();
}

function addTrInner()
{
    var table = $O("tables");
    
    var tr = $O("trCopy");
    
    trow =  table.insertRow(-1);
    
    if (length % 2 == 1)
    {
        trow.className = 'content2';
    }
    else
    {
        trow.className = 'content1';
    }
    
    for (var i = 0; i < tr.cells.length; i++)
    {
        var tcell = document.createElement("td");
        
        tcell.innerHTML = tr.cells[i].innerHTML;
        
        trow.appendChild(tcell);
    }
    
    trow.appendChild(tcell);
    
    return trow;
}

function removeTr(obj)
{
    //rows
    var table = $O("tables");
    
    if (table.rows.length <= 2)
    {
    	alert('不能全部删除');
    	
    	return false;
    }
    
    obj.parentNode.parentNode.removeNode(true);
    
    
    for (var i = 2; i < table.rows.length; i++)
    {
        if (i % 2 == 1)
        {
            table.rows[i].className = 'content1';
        }
        else
        {
            table.rows[i].className = 'content2';
        }
    }
}

function cc(obj)
{
	var tr = getTrObject(obj);

	var outFullId = getInputInTr(tr, 'outFullId').value;

	var outTotal = 0;

	var outArr = document.getElementsByName('outFullId');
	
	var priceArr = document.getElementsByName('price');

    var backUnmArr = document.getElementsByName('backUnm');
    
    var totals = 0.0;
    
    for (var i = 0; i < priceArr.length; i++)
    {
		var backUnm = parseInt(backUnmArr[i].value, 10);

		var price = parseFloat(priceArr[i].value);
			
		totals += mul(price, backUnm);

		if (outArr[i].value == outFullId)
		{
			outTotal += mul(price, backUnm);
		}
    }
    
    $O('hasMoney').value = formatNum(totals, 2);

    var vsOutIdArr = document.getElementsByName('vsOutId');
    var vsMoneysArr = document.getElementsByName('vsMoneys');

    for (var i = 0; i < vsOutIdArr.length; i++)
    {
        if (vsOutIdArr[i].value == outFullId)
        {
        	vsMoneysArr[i].value = formatNum(outTotal, 2);
        }
    }
}

</script>

</head>
<body class="body_class" onload="loadShow()">
<form name="formEntry" action="../finance/invoiceins.do?method=addInvoiceinsInNavigation" enctype="multipart/form-data" method="post">
<input type="hidden" name="customerId" value=""> 
<input type="hidden" name="type" value="0"> 
<input type="hidden" name="outId" value="${pmap.outId}"> 
<input type="hidden" name="mode" value="0"> 
<input type="hidden" name="id" value="${pmap['id']}">
<input type="hidden" name="attacmentIds" value="${attacmentIds}">

<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">发票管理</span> &gt;&gt; 开票申请向导(2)</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>发票基本信息：(关联多个单据默认依次填充单据的可开票金额,如果有委托代销最大开票金额需要人为减去退货金额,否则有误)</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:class value="com.china.center.oa.finance.bean.InvoiceinsBean" opr="2"/>

		<p:table cells="1">

			<p:pro field="invoiceDate"/>
			
			<p:pro field="headType">
				<p:option type = "invoiceinsHeadType" />
			</p:pro>
			
			<p:pro field="headContent"/>
			
			<!--<p:pro field="unit" innerString="size=60" />
			
			--><p:pro field="dutyId" innerString="onchange=loadShow()">
                <p:option type="dutyList" />
            </p:pro>

			<p:pro field="invoiceId" innerString="style='WIDTH: 340px;'">
			    <c:forEach items="${invoiceList}" var="item">
			    <option value="${item.id}">${item.fullName}</option>
			    </c:forEach>
			</p:pro>

			<p:cell title="开票客户" end="true">
                ${pmap.cname}
            </p:cell>
			
			<p:cell title="关联单据" end="true">
				<textarea name='outid'
					id='outid' rows=2 cols=80 readonly=true>${pmap.outId}</textarea>
			</p:cell>            
            
            <p:cell title="金额">
                可开票金额：<input type="text" size="20" readonly="readonly" name="mayMoney" value="${my:formatNum(pmap.mayMoney)}"> 
                &nbsp;&nbsp;
                当前开票金额：<input type="text" size="20" readonly="readonly" name="hasMoney" value="0.0"> 
            </p:cell>
            
            <p:pro field="processer">
                <p:option type="stafferList" empty="true"/>
            </p:pro>

			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />
			
			<p:cell title="附件" width="8" end="true"><input type="file" name="atts" size="70" >  
            <font color="blue"><b>建议压缩后上传,最大支持3M</b></font>
            </p:cell>

		</p:table>

	</p:subBody>

	<p:tr />


	<p:subBody width="100%">

		<p:table cells="1" id="tables">

			<tr align="center" class="content0">
				<td width="12%" align="center">销售单</td>
				<td width="28%" align="center">品名</td>
				<td width="10%" align="center">单价</td>
				<td width="10%" align="center">数量</td>
				<td width="10%" align="center">退货/已开票数量</td>
				<td width="5%" align="center">可开数量</td>
				<td width="5%" align="center">单位</td>
				<td width="25%" align="center">开票品名</td>
				
			</tr>
			
			<c:forEach items="${itemList}" var="item">
			<tr align="center" class="content0">				
				<td align="center">${item.outId}</td>
                <td align="center">${item.productName}
                <input type="hidden" name="productId" value="${item.productId}"> 
                <input type="hidden" name="baseId" value="${item.id}">
                <input type="hidden" name="outFullId" value="${item.outId}">
                <input type="hidden" name="price" value="${item.price}">
                <input type="hidden" name="costPrice" value="${item.costPrice}">
                <input type="hidden" name="mtype" value="${item.mtype}">
                <input type="hidden" name="refBaseIds" value="${item.description}">
                </td>
                <td align="center">${item.price}</td>
                <td align="center">${item.amount}</td>
                <td align="center">${item.inway}</td>
                <td align="center">
                <input type="text" oncheck="isNumber;range(0, ${item.amount - item.inway})"
                            style="width: 100%" name="backUnm" value="${item.amount - item.inway}" onkeyup="cc(this)">
                </td>
                <td align="center">
                	<select name="sunit" style="WIDTH: 100%;" quick=true values="8874797">
                	 <p:option type="200"></p:option>
					</select>
                </td>
                <td align="center">
                	<select name="showName" style="WIDTH: 100%;" quick=true>
					</select>
                </td>
            </tr>
            </c:forEach>
			
		</p:table>

	</p:subBody>
	
	<p:tr />

	<p:subBody width="100%">

		<p:table cells="1" id="tables">

			<tr align="center" class="content0">
				<td width="30%" align="center">销售单</td>
				<td width="10%" align="center">可开票金额</td>
				<td width="10%" align="center">开票金额</td>
			</tr>
			
			<c:forEach items="${vsList}" var="item">
			<tr align="center" class="content0">	
				<td align="center">
					<input type="text" name="vsOutId" style="width: 100%" readonly="readonly" value="${item.outId}">
					<input type="hidden" name="vsType" value="${item.type}">
                </td>
                 <td align="center">
                <input type="text" name="vsMayMoneys" style="width: 100%" readonly="readonly" value="${my:formatNum(item.moneys)}">
                </td>
                <td align="center">
                <input type="text" name="vsMoneys" style="width: 100%" oncheck="notNone;isFloat2" value="${my:formatNum(item.moneys)}">
                </td>
            </tr>
            </c:forEach>
			
		</p:table>

	</p:subBody>
	
	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
			<input type="button" class="button_class"
			id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()">
			</div>
	</p:button>

	<p:message />
</p:body>
</form>
</body>
</html>
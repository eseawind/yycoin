<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加发票" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="javascript">

var showJSON = JSON.parse('${showJSON}');
var invoicesJSON = JSON.parse('${invoicesJSON}');
var vsJSON = JSON.parse('${vsJSON}');

var invMap = {};
var invFullMap = {};
<c:forEach items="${invoiceList}" var="item">
  invFullMap['${item.id}'] = '${item.fullName}';
</c:forEach>

<c:forEach items="${dutyList}" var="item">
  invMap['${item.id}'] = '${item.type}';
</c:forEach>

function addBean()
{
	submit('确定增加对分公司的开票?', null, check);
}


function check()
{
    if ($$('dutyId') == $$('customerId'))
    {
        alert('纳税实体和分公司不能相同');
            
        return false;
    }
    
    var dest = '90000000000000000003';
    
    if ($$('invoiceId') != dest)
    {
        alert('发票只能是:增值专用发票(一般纳税人)[可抵扣](17.00%)');
            
        return false;
    }
    
    var showArr = document.getElementsByName('showId');
    var amountArr = document.getElementsByName('amount');
    var priceArr = document.getElementsByName('price');
    var priceArr = document.getElementsByName('price');
    var specialArr = document.getElementsByName('special');
    var unitArr = document.getElementsByName('sunit');
    
    var index = -1;
    
    var totals = 0.0;
    
    for (var i = 0; i < showArr.length; i++)
    {
        if (showArr[i].value != '' && index != -1 && index != (i + 1))
        {
            alert('请安装顺序选择品名');
            
            return false;
        }
        
        if (showArr[i].value == '' && i == 0)
        {
            alert('请安装顺序选择品名');
            
            return false;
        }
        
        if (showArr[i].value == '')
        {
            index = i;
        }
        else
        {
            if (!isNumbers(amountArr[i].value))
	        {
	            alert('请填写数量');
	            
	            $f(amountArr[i]);
	            
	            return false;
	        }
	        
	        if (!isFloat(priceArr[i].value))
	        {
	            alert('请填写价格');
	            
	            $f(priceArr[i]);
	            
	            return false;
	        }
	        
	        if (isNoneInCommon(specialArr[i].value))
            {
                alert('请填写规格');
                
                $f(specialArr[i]);
                
                return false;
            }
            
            if (isNoneInCommon(unitArr[i].value))
            {
                alert('请填写单位');
                
                $f(unitArr[i]);
                
                return false;
            }
	        
	        totals += parseInt(amountArr[i].value, 10) * parseFloat(priceArr[i].value);
        }
    }
    
    $O('hasMoney').value = totals;
    
    return true;
}

function loadShow()
{
    var json = showJSON;
    
    var pid = $$('dutyId');
    
    var showArr = document.getElementsByName('showId');
    
    for (var i = 0; i < showArr.length; i++)
    {
        var each = showArr[i];
        
        removeAllItem(each);
        
        setOption(each, "", "--");
        
        for (var j = 0; j < json.length; j++)
        {
            var item = json[j];
            
            if (item.dutyId == pid)
            {
                setOption(each, item.id, item.name);
            }
        }
    }
    
    var vsjson = vsJSON;
    
    var dutyObj = $O('dutyId');
    
    var invObj = $O('invoiceId');
    
    removeAllItem(invObj);
    
    if (invMap[dutyObj.value] == '3')
    {
        setOption(invObj, '', '没有发票');
    }
    
    for (var i = 0; i < vsjson.length; i++)
    {
        var item = vsjson[i];
        
        if (item.dutyType == invMap[dutyObj.value])
        {
            setOption(invObj, item.invoiceId, invFullMap[item.invoiceId]);
        }
    }
}


function clears()
{
    $O('outId').value = '';
    $O('mayMoney').value = '0.0';
}

function cc(obj, index)
{
    var am = $$('amount_' + index);
    
    var pr = $$('price_' + index);
    
    if (am == '' || pr == '')
    {
        $O('e_total_' + index).value = '0.0';
    }
    else
    {
        $O('e_total_' + index).value = parseFloat(pr) * parseInt(am, 10);
    }
}
</script>

</head>
<body class="body_class" onload="loadShow()">
<form name="formEntry" action="../finance/invoiceins.do" method="post">
<input type="hidden" name="method" value="addInvoiceins"> 
<input type="hidden" name="type" value="1"> 
<input type="hidden" name="mode" value="2"> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">发票管理</span> &gt;&gt; 对分公司开票</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>发票基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:class value="com.china.center.oa.finance.bean.InvoiceinsBean" />

		<p:table cells="1">

			<p:pro field="invoiceDate"/>
			
			<p:pro field="unit" innerString="size=60" />
			
			<p:pro field="dutyId" innerString="onchange=loadShow()">
                <p:option type="dutyList" />
            </p:pro>

			<p:pro field="invoiceId" innerString="style='WIDTH: 340px;'">
			    <c:forEach items="${invoiceList}" var="item">
			    <option value="${item.id}">${item.fullName}</option>
			    </c:forEach>
			</p:pro>

			<p:cell title="目的分公司" end="true">
                <select name="customerId" style='WIDTH: 340px;' oncheck="notNone">
                    <p:option type="dutyList" empty="true"/>
                </select>
            </p:cell>
			
            <p:cell title="金额">
                当前开票金额：<input type="text" size="20" readonly="readonly" name="hasMoney" value="0.0"> 
            </p:cell>

			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />

		</p:table>

	</p:subBody>

	<p:tr />


	<p:subBody width="100%">

		<p:table cells="1">

			<tr align="center" class="content0">
				<td width="20%" align="center">品名</td>
				<td width="30%" align="center">规格</td>
				<td width="10%" align="center">单位</td>
				<td width="10%" align="center">数量</td>
				<td width="10%" align="center">单价</td>
				<td width="20%" align="center">合计</td>
			</tr>
			
			<c:forEach begin="1" end="15" var="vs">
			<tr align="center" class="content0">
                <td align="center"><select name="showId" style="WIDTH: 100%;" quick=true ></td>
                <td align="center"><input type="text" name="special" style="width: 100%" ></td>
                <td align="center"><input type="text" name="sunit" style="width: 100%"></td>
                <td align="center"><input type="text" name="amount" id="amount_${vs}" style="width: 100%" oncheck="isNumber" onkeyup="cc(this, ${vs})"></td>
                <td align="center"><input type="text" name="price" id="price_${vs}" style="width: 100%" oncheck="isFloat2" onkeyup="cc(this, ${vs})"></td>
                <td align="center">
                <input type="text" name="e_total" id="e_total_${vs}" style="width: 100%" readonly="readonly" value="0.0"></td>
            </tr>
            </c:forEach>

		</p:table>

	</p:subBody>
	
	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>


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
	submit('确定申请增加发票?', null, check);
}

function check()
{
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
    
    if (totals > parseFloat($$('mayMoney')))
    {
        alert('开票金额不能大于单据的可开票金额');
        
        return false;
    }
    
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
    
    $O('outId').value = '';
    $O('mayMoney').value = '0.0';
}

function opens()
{
    if ($O('customerId').value == '')
    {
        alert('请选择客户');
        return false;
    }
    window.common.modal('../sail/out.do?method=rptQueryOut&mode=1&selectMode=1&invoiceStatus=0&load=1&invoiceId=' + $$('invoiceId') + '&dutyId=' + $$('dutyId') + '&customerId=' + $$('customerId'));
}

function openBalance()
{
    if ($O('customerId').value == '')
    {
        alert('请选择客户');
        return false;
    }
    window.common.modal('../sail/out.do?method=rptQueryOutBalance&mode=1&type=0&selectMode=1&invoiceStatus=0&load=1&invoiceId=' + $$('invoiceId') + '&dutyId=' + $$('dutyId') + '&customerId=' + $$('customerId'));
}


function getOut(oos)
{
    var outId = $$('outId');
    
    var oldm = parseFloat($$('mayMoney'));
    
    for (var i = 0 ; i < oos.length; i++)
    {
        var oo = oos[i];
        
        if (outId.indexOf(oo.value) == -1)
        {
            outId += oo.value + ";";
            
             oldm += parseFloat(oo.pinvoicemoney);
        }
    }
    
    $O('outId').value = outId;
    
    $O('mayMoney').value = oldm;
}

function getOutBalance(oos)
{
	return getOut(oos);
}

function clears()
{
    $O('outId').value = '';
    $O('mayMoney').value = '0.0';
}

function selectCus()
{
    window.common.modal('../client/client.do?method=rptQuerySelfClient&stafferId=${user.stafferId}&load=1');
}

function getCustomer(obj)
{
    $O('customerId').value = obj.value;
    $O('cname').value = obj.pname;
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
<input type="hidden" name="customerId" value=""> 
<input type="hidden" name="type" value="0"> 
<input type="hidden" name="mode" value="${mode}"> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">发票管理</span> &gt;&gt; 开票申请</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>发票基本信息：(关联多个单据默认依次填充单据的可开票金额)</strong></td>
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

			<p:cell title="开票客户" end="true">
                <input type="text" size="60" readonly="readonly" name="cname" oncheck="notNone;"> 
                <font color="red">*</font>
                <input type="button" value="&nbsp;选 择&nbsp;" name="qout1" id="qout1"
                    class="button_class" onclick="selectCus()">
            </p:cell>
			
			<p:cell title="关联单据" end="true">
			    <input type="text" size="60" readonly="readonly" name="outId"> 
                <input type="button" value="&nbsp;销售单&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="opens()">&nbsp;
                 <input type="button" value="&nbsp;委托清单&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="openBalance()">&nbsp;
                <input type="button" value="&nbsp;清 空&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="clears()">&nbsp;&nbsp;
            </p:cell>
            
            <p:cell title="金额">
                可开票金额：<input type="text" size="20" readonly="readonly" name="mayMoney" value="0.0"> 
                &nbsp;&nbsp;
                当前开票金额：<input type="text" size="20" readonly="readonly" name="hasMoney" value="0.0"> 
            </p:cell>
            
            <p:pro field="processer">
                <p:option type="stafferList" empty="true"/>
            </p:pro>

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


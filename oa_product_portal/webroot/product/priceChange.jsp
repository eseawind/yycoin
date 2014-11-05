<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<style type="text/css">
.contentNN {
	font-size: 12px;
	height: 20px;
	background-color: #00FF7F;
}
</style>
<p:link title="产品调价" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../product_js/composeProduct.js"></script>
<script language="javascript">

var max = ${my:length(relationList)};

function addBean()
{
    submit('确定产品调价?', null, checks);
}

function checks()
{
	var alertStr = "";
	
	//价格异动超过5%
	for (var i = 0; i < max; i++)
	{
		var each = $O('p_' + i);
		
		var nowPrice = parseFloat(each.value);
		
		var oldPrice = parseFloat(each.ovalue);
		
		var pname = each.oname;
		
		var paddress = each.oaddress;
		
		if (oldPrice == 0)
		{
			alertStr += paddress + '[' + pname + '] 原价:' + oldPrice + ';现价:' + nowPrice + "\r\n";
		}
		
		var per = ((nowPrice - oldPrice) / oldPrice) * 100;
		
		if (per >= 5)
		{
			alertStr += paddress + '[' + pname + '] 原价:' + oldPrice + ';现价:' + nowPrice + "\r\n";
		}
	}
	
	if (alertStr.length == 0)
	{
		return true;
	}
	
	return window.confirm('以下产品价格异动超过5%,请核实\r\n' + alertStr);
}


function load()
{
	loadForm();
}

function copyLast(index)
{
	if (index > 0)
	{
		var pc = $O('p_' + (index - 1));
		var pc1 = $O('p_' + index);
		
		if (pc.pid == pc1.pid)
		{
			pc1.value = pc.value;
			
			trChange(index);
		}
	}
}

function copyNext(index, value)
{
	var cur = $O('p_' + index);
	
	if (cur == null)
	{
		return;
	}
	
	var next = $O('p_' + (index + 1));
	
	var checkIn = $O('check_' + (index + 1));
	
	if (next == null)
	{
		return;
	}
	
	if (next.pid == cur.pid)
	{
	    if (checkIn.checked)
	    {
			next.value = value;
			
			trChange(index + 1);
		}
		
		copyNext(index + 1, value);
	}
}

function isFristProduct(index)
{
	if (index == 0)
	{
		return true;
	}
	
	var pc = $O('p_' + (index - 1));
	var pc1 = $O('p_' + index);
	
	if (pc.pid == pc1.pid)
	{
		return false;
	}
	
	return true;
}

function trChange(index)
{
	var ac = $O('ac_' + index);
	var pc = $O('p_' + index);
	
	//只有价格不等才是调价
	if (trim(pc.value) == '' || parseFloat(pc.value) != parseFloat(pc.ovalue))
	{
		$O('tr_' + index).className = 'contentNN';
	}
	else
	{
		$O('tr_' + index).className = 'content1';
	}
	
	if (trim(ac.value) != '')
	{
		if (parseInt(ac.value) > parseInt(ac.ovalue))
		{
			alert('调价数量不能大于总数量');
			ac.value = ac.ovalue;
			trChange(index);
		}
	}
	
	if (isFristProduct(index))
	{
		//下面的产品价格需要拷贝
		copyNext(index, pc.value);
	}
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../product/product.do" method="post"><input
	type="hidden" name="method" value="priceChange"> 

<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javaScript:window.history.go(-1);">产品管理</span> &gt;&gt; 产品调价</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>产品调价</strong>(调价是整个公司仓库下的产品调价.如果只调整储位里面的部分价格,请修改调价数量,剩余的数量保持原价)</td>
	</p:title>

	<p:line flag="0" />
	
	<p:subBody width="98%">
		<p:table cells="1">
			<p:tr align="left">
			调价原因：
			<textarea  name='description' head='调价原因' rows=3 cols=55 oncheck="maxLength(255);"></textarea>
			</p:tr>
		</p:table>
	</p:subBody>
	
	<p:tr></p:tr>

	<p:subBody width="98%">
		<p:table cells="1">
                    <tr align="center" class="content0">
                        <td width="25%" align="center">产品</td>
                        <td align="center">总数量</td>
                        <td width="5%" align="center">调价数量</td>
                        <td align="center">原价</td>
                        <td width="8%" align="center">新价</td>
                        <td align="center">选择</td>
                        <td align="center">在途</td>
                        <td align="center">位置</td>
                        <td align="center">职员</td>
                    </tr>
                    
                    <c:forEach items="${relationList}" var="item" varStatus="vs">
                    <tr align="center" class="content1" id="tr_${vs.index}">
                    	<input type="hidden" name="relation_id" value="${item.id}" />
                    	<input type="hidden" name="old_amount" value="${item.amount}" />
                    	<input type="hidden" name="old_price" value="${my:formatNum(item.price)}" />
                        <td width="25%" align="center">${item.productName}(${item.productCode})</td>
                        <td align="center">${item.amount}</td>
                        <td width="5%" align="center"><input type="text" name="changeAmount" ovalue="${item.amount}" value="${item.amount}" style="width: 100%" 
                        onkeyup="trChange(${vs.index}, this)" id="ac_${vs.index}" /></td>
                         <td align="center">${my:formatNum(item.price)}</td>
                        <td align="center"><input type="text" name="changePrice" value="${my:formatNum(item.price)}"  oname="${item.productName}"
                        oaddress="${item.locationName} -- ${item.depotpartName}"
                        ovalue="${my:formatNum(item.price)}" id="p_${vs.index}" pid="${item.productId}"
                        style="width: 100%" ondblclick="copyLast(${vs.index})" onkeyup="trChange(${vs.index}, this)"></td>
                        <td align="center"><input type="checkbox" id="check_${vs.index}" pid="${item.productId}" checked="checked"></td>
                        <td align="center">${item.errorAmount}</td>
                        <td align="center">${item.locationName} --> ${item.depotpartName} --> ${item.storageName}</td>
                        <td align="center">${item.stafferName}</td>
                    </tr>
                    </c:forEach>
          </p:table>
	</p:subBody>
    
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
</body>
</html>


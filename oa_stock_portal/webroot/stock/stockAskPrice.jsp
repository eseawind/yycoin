<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="处理询价" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="javascript">

var cindex = -1;
function addBean()
{
	submit('确定处理询价?', null, lverify);
}

function lverify()
{
	var checkArr = document.getElementsByName('check_init');

	var isSelect = false;

	var imap = {};

	var count = 0;
	for (var i = 0; i < checkArr.length; i++)
	{
		var obj = checkArr[i];

		var index = obj.value;

		if (obj.checked)
		{
			count++;
			isSelect = true;

			if ($O('customerName_' + i).value == '' || $O('customerId_' + i).value == '' )
			{
				alert('供应商不能为空');
				return false;
			}

			if ($O('provideConfirmDate_' + i).value == '')
			{
				alert('供应商确认时间不能为空');
				return false;
			}

			if ($O('confirmSendDate_' + i).value == '')
			{
				alert('确认发货时间不能为空');
				return false;
			}
			
			//if ($$('hasAmount_' + i)  == null)
			//{
				//alert('请选择供应商是否满足数量要求');
				//return false;
			//}
			if ($O('isWrapper_' + i).value == 0)
			{
				if ($O('flow_' + i).value == '')
				{
					alert('需要装配时,物流走向不能为空.');
					return false;
				}
			}

			if (imap[$O('customerId_' + i).value] == $O('customerId_' + i).value)
			{
				alert('选择的供应商不能重复');
				return false;
			}

			imap[$O('customerId_' + i).value] = $O('customerId_' + i).value;
		}
	}

	if(count < 3)
	{
		alert('选择询价供应商必须大于2家');
		return false;
	}

	if (!isSelect)
	{
		alert('请选择询价供应商');
		return false;
	}

	return true;
}
function load()
{
	loadForm();

	init();
}

function init()
{
	var checkArr = document.getElementsByName('check_init');

	for (var i = 0; i < checkArr.length; i++)
	{
		var obj = checkArr[i];

		var index = obj.value;

		if (obj.checked)
		{
			$d('qout_' + index, false);
			$d('qout1_' + index, false);
			$d('goldPrice_' + index, false);
			$d('silverPrice_' + index, false);
			$d('handleFee_' + index, false);
			$d('amount_' + index, false);
			$d('price_' + index, false);
			$d('isWrapper_' + index, false);
			$d('gap_' + index, false);
			//$d('price_' + index, false);
			//$d('hasAmount_' + index, false);
			//$d('supportAmount_' + index, false);
			if ("${product.cost}" == 0){
				$O('goldPrice_' + index).value = '0';
				$d('goldPrice_' + index);
			}

			if ("${product.planCost}" == 0){
				$O('silverPrice_' + index).value = '0';
				$d('silverPrice_' + index);
			}
		}
		else
		{
			$O('unitPrice_' + index).value = '0.0';
			$O('price_' + index).value = '0.0';
			$O('customerName_' + index).value = '';
			$O('customerId_' + index).value = '';
			$O('flowName_' + index).value = '';
			$O('flow_' + index).value = '';
			$O('outTime_' + index).value = '';
			$O('provideConfirmDate_' + index).value = '';
			$O('confirmSendDate_' + index).value = '';
			
			$d('qout_' + index);
			$d('qout1_' + index);
			$d('goldPrice_' + index);
			$d('silverPrice_' + index);
			$d('handleFee_' + index);
			$d('amount_' + index);
			$d('price' + index);
			$d('isWrapper_' + index);
			$d('gap_' + index);
			$v('outTime_' + index, false);
		}
	}
}

var flag = "0";
function selectCustomer(index)
{
	flag = "1";
	cindex = index;
	
	window.common.modal("../provider/provider.do?method=rptQueryProvider&load=1&productType=${product.type}&productId=${product.id}&areaId=${stock.areaId}");
}

function getProvider(id, name)
{
	if (cindex != -1)
	{
		if (flag == '1'){
			$O("customerName_" + cindex).value = name;
			$O("customerId_" + cindex).value = id;
		}else{
			$O("flowName_" + cindex).value = name;
			$O("flow_" + cindex).value = id;
		}
	}
}

function selectFlow(index){
	cindex = index;
	flag = "0";
	window.common.modal("../provider/provider.do?method=rptQueryProvider&load=1&productTypeId=${product.type}&productId=${product.id}&areaId=${bean.areaId}");
}

function calDateInner(obj, name)
{
	var tr = getTrObject(obj);
	
	var el = getInputInTr(tr, name);
	
	return calDate(el)
}

function cc(index)
{
	var price = 0; 

	if ($O('price_' + index).value != '')
	{
		price = parseFloat($O('price_' + index).value);
	}

	var amount = 0; 

	if ($O('amount_' + index).value != '')
	{
		amount = parseFloat($O('amount_' + index).value);
	}

	var unitPrice = 0;
	
	unitPrice = price * amount;

	$O('unitPrice_' + index).value = formatNum(unitPrice + '', 2);
}

function wrapperChange(index)
{
	if ($$('isWrapper_' + index) == '0')
	{
		$d('qout1_' + index, false);
	}else
	{
		$d('qout1_' + index);
	}

	$O('flow_' + index).value = '';
	$O('flowName_' + index).value = '';
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../stock/stock.do" method="post"><input
	type="hidden" name="method" value="stockItemAskPrice">
	<input type="hidden" name="customerId_0" value="">
	<input type="hidden" name="customerId_1" value="">
	<input type="hidden" name="customerId_2" value="">
	<input type="hidden" name="customerId_3" value="">
	<input type="hidden" name="customerId_4" value="">
	
	<input type="hidden" name="flow_0" value="">
	<input type="hidden" name="flow_1" value="">
	<input type="hidden" name="flow_2" value="">
	<input type="hidden" name="flow_3" value="">
	<input type="hidden" name="flow_4" value="">
	
	<input type="hidden" name="stockId" value="${id}">
	<input
	type="hidden" name="id" value="${bean.id}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">询价管理</span> &gt;&gt; 处理采购询价</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>询价信息：【询价供应商必须大于2家】</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:table cells="2">
			<p:cell title="采购单号">
			${bean.stockId}
			</p:cell>

			<p:cell title="产品名称">
			${bean.productName}
			</p:cell>

			<p:cell title="产品编码">
			${bean.productCode}
			</p:cell>

			<p:cell title="采购数量">
			${bean.amount}
			</p:cell>

			<p:cells celspan="2" title="参考价格">
			${my:formatNum(bean.prePrice)}
			</p:cells>


			<p:cells id="selects" celspan="2" title="询价处理">
				<table id="mselect">
				<c:forEach begin="0" end="4" var="item">
				   <tr>
						<td>
							<input type="checkbox" name="check_init" value="${item}" onclick="init()" id="check_init_${item}" >供应商${item + 1}：<input type="button"
								value="&nbsp;选 择&nbsp;" name="qout_${item}" class="button_class"
								onclick="selectCustomer(${item})">&nbsp;
							供应商:<input
							type="text" name="customerName_${item}" value="" size="20" readonly="readonly">&nbsp;
							金价:<input
							type="text" name="goldPrice_${item}" value="" size="6" >&nbsp;
							银价:<input
							type="text" name="silverPrice_${item}" value="" size="6">&nbsp;
							费用:<input
							type="text" name="handleFee_${item}" value="" size="6">
							<br>
							&nbsp;&nbsp;&nbsp;&nbsp;数量:<input
							type="text" name="amount_${item}" value="" size="6" oncheck="isFloat;" onkeyup="cc(${item})">&nbsp;
							单价:<input type="text" name="price_${item}" value="0.0" size="6" oncheck="isFloat;" onkeyup="cc(${item})">&nbsp;
							总价:<input type="text" name="unitPrice_${item}" readonly>&nbsp;
							
							<input type=text name = 'outTime_${item}'  id = 'outTime_${item}' 
					         readonly=readonly class='input_class' size = '20' >
							<br>
							供应商确认时间:
							<input type=text name = 'provideConfirmDate_${item}'  id = 'provideConfirmDate_${item}' 
					         readonly=readonly class='input_class' size = '20' ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "provideConfirmDate_${item}");' height='20px' width='20px'/>
							确认发货时间:
							<input type=text name = 'confirmSendDate_${item}'  id = 'confirmSendDate_${item}' 
					         readonly=readonly class='input_class' size = '20' ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "confirmSendDate_${item}");' height='20px' width='20px'/>
							<br>
							是否装配:
                            <select name="isWrapper_${item}" class="select_class" style="width: 10%;" oncheck="notNone" onchange="wrapperChange(${item})">
					            <p:option type="isWrapper" empty="true"></p:option>
					         </select>&nbsp;
                           	物流走向:<input
                            type="text" name="flowName_${item}" value="" size="20" readonly>&nbsp;
                            <input type="button"
								value="&nbsp;选 择&nbsp;" name="qout1_${item}" class="button_class"
								onclick="selectFlow(${item})">&nbsp;
                          	公差:
                            <select name="gap_${item}" class="select_class" style="width: 10%;" >
					            <p:option type="isGap" empty="true"></p:option>
					         </select>&nbsp;
							<br>
                          	描述${item + 1}: <textarea name="description_${item}" cols="60" rows="3"></textarea>

							</td>
					</tr>
				   </c:forEach>
				
				</table>
			</p:cells>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			name="adds" style="cursor: pointer"
			value="&nbsp;&nbsp;确认询价&nbsp;&nbsp;" onclick="addBean()">&nbsp;&nbsp;
		<input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message2/>
	
</p:body></form>
</body>
</html>


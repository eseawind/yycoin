<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="处理询价" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">

var cindex = -1;
function addBean()
{
	submit('确定处理询价?', null, lverify);
}

var totalAmount = ${bean.amount};

function lverify()
{
	var checkArr = document.getElementsByName('check_init');

	var isSelect = false;

	var imap = {};

	var count = 0;
	var tmpAmount = 0;
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
			
			if ($O('nearlyPayDate_' + i).value == '')
            {
                alert('最早付款时间不能为空');
                return false;
            }


			if (false && imap[$O('customerId_' + i).value] == $O('customerId_' + i).value)
			{
				alert('选择的供应商不能重复');
				return false;
			}
			
			tmpAmount += parseInt($O('amount_' + i).value);

			imap[$O('customerId_' + i).value] = $O('customerId_' + i).value;
		}
	}

	if (!isSelect)
	{
		alert('请选择询价供应商');
		return false;
	}
	
	if (totalAmount != tmpAmount)
	{
	    alert('只能采购:' + totalAmount + "个!");
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
			$d('price_' + index, false);
			$d('amount_' + index, false);
			$d('description_' + index, false);
		}
		else
		{
			$O('price_' + index).value = '';
			$O('customerName_' + index).value = '';
			$O('customerId_' + index).value = '';
			$O('description_' + index).value = '';
			$d('qout_' + index);
			$d('price_' + index);
			$d('amount_' + index);
			$d('description_' + index);
		}
	}
}

function selectCustomer(index)
{
	cindex = index;
	
	window.common.modal("../stock/stock.do?method=rptInQueryPriceAskProvider&firstLoad=1&productId=${bean.productId}&userId=${stock.userId}&stockId=${stock.id}");
}


function getPriceAskProvider(oo)
{
    if (cindex != -1)
    {
        $O("customerName_" + cindex).value = oo.pprovidername;
        $O("customerId_" + cindex).value = oo.pprovideriid;
        $O("stafferId_" + cindex).value = oo.pstafferid;
        $O("price_" + cindex).value = oo.pp;
        $O("netaskId_" + cindex).value = oo.ppid;
    }
}

//自动生成外网询价单
function createAskBean()
{
    if (window.confirm('确定自动生成外网询价单?'))
    {
        $l('../stock/stock.do?method=createAskBean&stockId=${stockId}&itemId=${bean.id}');
    }    
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../stock/stock.do" method="post"><input
	type="hidden" name="method" value="stockItemAskPriceForNet">
	<input type="hidden" name="customerId_0" value="">
	<input type="hidden" name="customerId_1" value="">
	<input type="hidden" name="customerId_2" value="">
	<input type="hidden" name="customerId_3" value="">
	<input type="hidden" name="customerId_4" value="">
	
	<input type="hidden" name="stafferId_0" value="">
    <input type="hidden" name="stafferId_1" value="">
    <input type="hidden" name="stafferId_2" value="">
    <input type="hidden" name="stafferId_3" value="">
    <input type="hidden" name="stafferId_4" value="">
	
	<input type="hidden" name="netaskId_0" value="">
    <input type="hidden" name="netaskId_1" value="">
    <input type="hidden" name="netaskId_2" value="">
    <input type="hidden" name="netaskId_3" value="">
    <input type="hidden" name="netaskId_4" value="">
    
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
		<td class="caption"><strong>选择询价结果：</strong></td>
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
					<tr>
						<td>
							<input type="checkbox" name="check_init" value="0" onclick="init()" id="check_init_0">供应商一：<input type="button"
								value="&nbsp;选 择&nbsp;" name="qout_0" class="button_class"
								onclick="selectCustomer(0)">&nbsp;
							供应商:<input
							type="text" name="customerName_0" value="" size="20" readonly="readonly">&nbsp;
							价格:<input
							type="text" name="price_0" value="" size="6" oncheck="isFloat;">&nbsp;
							数量:<input
                            type="text" name="amount_0" value="" size="6" oncheck="isNumber;">&nbsp;
                                最早付款时间:<p:plugin type="0" name="nearlyPayDate_0"></p:plugin><br>
                                备注:
                            <input
                            type="text" name="description_0" value="" size="60">&nbsp;
							</td>
					</tr>

					<tr>
						<td><input type="checkbox" name="check_init" value="1" onclick="init()" id="check_init_1">供应商二：<input type="button"
								value="&nbsp;选 择&nbsp;" name="qout_1" class="button_class"
								onclick="selectCustomer(1)">&nbsp;
							供应商:<input
							type="text" name="customerName_1" value="" size="20" readonly="readonly">&nbsp;
							价格:<input
							type="text" name="price_1" value="" size="6" oncheck="isFloat;">&nbsp;
							数量:<input
                            type="text" name="amount_1" value="" size="6" oncheck="isNumber;">&nbsp;
                             最早付款时间:<p:plugin type="0" name="nearlyPayDate_1"></p:plugin><br>
                            备注:
                            <input
                            type="text" name="description_1" value="" size="60">&nbsp;

						</td>
					</tr>

					<tr>
						<td><input type="checkbox" name="check_init" value="2" onclick="init()" id="check_init_2">供应商三：<input type="button"
								value="&nbsp;选 择&nbsp;" name="qout_2" class="button_class"
								onclick="selectCustomer(2)">&nbsp;
							供应商:<input
							type="text" name="customerName_2" value="" size="20" readonly="readonly">&nbsp;
							价格:<input
							type="text" name="price_2" value="" size="6" oncheck="isFloat;">&nbsp;
							数量:<input
                            type="text" name="amount_2" value="" size="6" oncheck="isNumber;">&nbsp;
                             最早付款时间:<p:plugin type="0" name="nearlyPayDate_2"></p:plugin><br>
                            备注:
                            <input
                            type="text" name="description_2" value="" size="60">&nbsp;
							</td>
					</tr>

					<tr>
						<td><input type="checkbox" name="check_init" value="3" onclick="init()" id="check_init_3">供应商四：<input type="button"
								value="&nbsp;选 择&nbsp;" name="qout_3" class="button_class"
								onclick="selectCustomer(3)">&nbsp;
							供应商:<input
							type="text" name="customerName_3" value="" size="20" readonly="readonly">&nbsp;
							价格:<input
							type="text" name="price_3" value="" size="6" oncheck="isFloat;">&nbsp;
							数量:<input
                            type="text" name="amount_3" value="" size="6" oncheck="isNumber;">&nbsp;
                             最早付款时间:<p:plugin type="0" name="nearlyPayDate_3"></p:plugin><br>
                            备注:
                            <input
                            type="text" name="description_3" value="" size="60">&nbsp;
							</td>
					</tr>

					<tr>
						<td><input type="checkbox" name="check_init" value="4" onclick="init()" id="check_init_4">供应商五：<input type="button"
								value="&nbsp;选 择&nbsp;" name="qout_4" class="button_class"
								onclick="selectCustomer(4)">&nbsp;
							供应商:<input
							type="text" name="customerName_4" value="" size="20" readonly="readonly">&nbsp;
							价格:<input
							type="text" name="price_4" value="" size="6" oncheck="isFloat;">&nbsp;
							数量:<input
                            type="text" name="amount_4" value="" size="6" oncheck="isNumber;">&nbsp;
                             最早付款时间:<p:plugin type="0" name="nearlyPayDate_4"></p:plugin><br>
                            备注:
                            <input
                            type="text" name="description_4" value="" size="60">&nbsp;
							</td>
					</tr>
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
            name="adds" style="cursor: pointer;display: none;"
            value="&nbsp;&nbsp;自动生成外网询价单&nbsp;&nbsp;" onclick="createAskBean()">&nbsp;&nbsp;
		<input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<tr>
		<td colspan='2' align="center"><FONT color="blue">${MESSAGE_INFO}</FONT><FONT
			color="red">${errorInfo}</FONT></td>
	</tr>
</p:body></form>
</body>
</html>


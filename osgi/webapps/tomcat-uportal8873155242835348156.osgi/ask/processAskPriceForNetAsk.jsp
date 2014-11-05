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

function lverify()
{
	var checkArr = document.getElementsByName('check_init');

	var isSelect = false;
	for (var i = 0; i < checkArr.length; i++)
	{
		var obj = checkArr[i];

		var index = obj.value;

		if (obj.checked)
		{
			isSelect = true;
			if ($O('customerName_' + i).value == '' || $O('customerId_' + i).value == '' )
			{
				alert('供应商不能为空');
				return false;
			}

			if ($$('hasAmount_' + i)  == null)
			{
				alert('请选择供应商是否满足数量要求');
				return false;
			}
		}
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
            $d('price_' + index, false);
            $d('hasAmount_' + index, false);
        }
        else
        {
            $O('price_' + index).value = '';
            //$O('customerName_' + index).value = '';
            //$O('customerId_' + index).value = '';
            $d('qout_' + index);
            $d('price_' + index);
            $d('hasAmount_' + index);
        }
    }
}

function selectCustomer(index)
{
	cindex = index;
	window.common.modal("../provider/provider.do?method=rptQueryProvider&load=1&productTypeId=${product.type}&productId=${product.id}");
}

function getCustmeor(id, name)
{
	if (cindex != -1)
	{
		$O("customerName_" + cindex).value = name;
		$O("customerId_" + cindex).value = id;
	}
}

function rejectBean()
{
	if (window.confirm('确定驳回此询价单?'))
	{
		var sss = window.prompt('请输入驳回询价单原因：', '');

		$O('reason').value = sss;

		if (!(sss == null || sss == ''))
		{
			$O('method').value = 'rejectPriceAsk';
			
			formEntry.submit();
		}
		else
		{
			alert('请输入驳回原因');
		}
	}
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../stock/ask.do" method="post"><input
	type="hidden" name="method" value="processAskPrice">
	<input type="hidden" name="id" value="${bean.id}">
	<input type="hidden" name="customerId_0" value="${GProvider.id}">
	<input type="hidden" value="" name="reason">
	<input type="hidden" value="1" name="askType">
	<input type="hidden" value="${fw}" name="fw">
	<input type="hidden" value="1" name="net">
	<input
	type="hidden" name="id" value="${bean.id}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">询价管理</span> &gt;&gt; 处理询价</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>询价信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:table cells="2">
			<p:cell title="产品名称">
			<a href="../admin/product.do?method=findProduct&productId=${bean.productId}&detail=1" title="查看产品明细">
			${bean.productName}
			</a>
			</p:cell>

			<p:cell title="产品编码">
			${bean.productCode}
			</p:cell>

			<p:cell title="需要数量">
			${bean.amount}
			</p:cell>

			<p:cell title="是否逾期">
			${bean.overTime == 0 ? "<font color=blue>未逾期</font>" : "<font color=red>逾期</font>"}
			</p:cell>

			<p:cell title="询价人">
			${bean.userName}
			</p:cell>

			<p:cell title="询价区域">
			${bean.locationName}
			</p:cell>

			<p:cell title="紧急程度">
			${my:get('priceAskInstancy', bean.instancy)}
			</p:cell>

			<p:cell title="处理时间">
			${bean.processTime}
			</p:cell>
			
			<p:cells title="备注" celspan="2">
            ${bean.description}
            </p:cells>

			<p:cells id="selects" celspan="2" title="询价处理">
				<table id="mselect">
					<tr>
						<td>
							<input type="checkbox" name="check_init" value="0" onclick="init()" checked="checked" style="visibility: hidden;">
							供应商:<input
							type="text" name="customerName_0" value="${GProvider.name}" size="20" readonly="readonly">&nbsp;
							单价:<input
							type="text" name="price_0" value="${my:formatNum(paskBean.price)}" size="6" oncheck="isFloat;">&nbsp;
							具体数量<input
                            type="text" name="supportAmount_0" value="${paskBean.supportAmount}" size="6" oncheck="isFloat;notNone">&nbsp;
							数量是否满足:<input type="radio" name="hasAmount_0" value="0" ${paskBean.hasAmount == 0 ? 'checked=checked' : ''}>满足
							&nbsp;&nbsp;<input type="radio" name="hasAmount_0" value="1" ${paskBean.hasAmount == 1 ? 'checked=checked' : ''}>不满足
							<br>
							描述: <textarea name="description_0" cols="60" rows="3">${paskBean.description}</textarea>
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
			value="&nbsp;&nbsp;确 定&nbsp;&nbsp;" onclick="addBean()">&nbsp;&nbsp;
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


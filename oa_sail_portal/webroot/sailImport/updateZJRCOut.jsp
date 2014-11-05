<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="填写销售单" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/jquery/jquery.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../sailImport_js/addZJRCOut.js"></script>
<script language="javascript">
<%@include file="../sailImport_js/zjrcout.jsp"%>

var cmap = window.top.topFrame.cmap;
var pList = window.top.topFrame.pList;

var areaJson = JSON.parse('${areaStrJSON}');
/**
 * 查询库存
 */
function opens(obj)
{
    oo = obj;
    // 配件
    window.common.modal('../sail/extout.do?method=rptQueryZJRCProduct&load=1&code=' + obj.productcode + '&init=1');
}

function load()
{	
	setOption($O('provinceId'), "", "--");
    for (var i = 0; i < pList.length; i++)
    {
        setOption($O('provinceId'), pList[i].id, pList[i].name);
    }    
    
    loadForm();

    changes($O('cityId'));
    
    loadForm();

    changeArea();
    
}

function changePrice()
{
    var ssList = document.getElementsByName('price');
    
    for (var i = 0; i < ssList.length; i++)
    {
        if (ssList[i].value != '')
        {
           ccs(ssList[i]);
           total();
        }
    }
}

function changes(obj)
{
    removeAllItem($O('cityId'));
    setOption($O('cityId'), "", "--");
    
    if ($$('provinceId') == "")
    {
        return;
    }
    
    var cityList = cmap[$$('provinceId')];
    for (var i = 0; i < cityList.length; i++)
    {
        setOption($O('cityId'), cityList[i].id, cityList[i].name);
    }
    
    removeAllItem($O('areaId'));
    
    setOption($O('areaId'), "", "--");
}

function changeArea(areaId)
{
    var id = $$('cityId');
    
    if (id == "")
    {
        return;
    }
    
    removeAllItem($O('areaId'));
    setOption($O('areaId'), "", "--");
    
    var areaList = areaJson[$$('cityId')];

    removeAllItem($O('areaId'));
    
    setOption($O('areaId'), "", "--");
    
    for (var i = 0; i < areaList.length; i++)
    {
        setOption($O('areaId'), areaList[i].id,  areaList[i].name);
    }
    
    return;
}

</script>
</head>
<body class="body_class" onload="load()">
<form name="outForm" method="post" action="../sail/extout.do">
<input
	type=hidden name="method" value="addZJRCOut" />
<input type=hidden name="update" value="0" /> 
<input type=hidden name="type" value='0' /> 
<input type=hidden name="nameList" /> 
<input type=hidden name="idsList" /> 
<input type=hidden name="amontList" />
<input type=hidden name="id" value="${bean.id}" />
<input type=hidden name="totalList" /> 
<input type=hidden name="totalss" /> 
<input type=hidden name="priceList">
<input type=hidden name="costpriceList">
<input type=hidden name="midrevenueList">
<input type=hidden name="saves" value="" /> 

<p:navigation height="22">
	<td width="550" class="navigation">库单管理 &gt;&gt; 填写销售单</td>
	<td width="85"></td>
</p:navigation> <br>

<table width="95%" border="0" cellpadding="0" cellspacing="0"
	align="center">

	<tr>
		<td colspan='2' align='center'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content2">
						<td width="15%" align="right">销售日期：</td>

						<td width="35%"><input type="text" name="outTime"
							value="${bean.outTime}" maxlength="20" size="20" readonly="readonly"><font
							color="#FF0000">*</font></td>

						<td width="15%" align="right">销售类型：</td>
						<td width="35%"><select name="outType" class="select_class"
							values="0" readonly='readonly'>
							<p:option type="outType_out"></p:option>
						</select><font color="#FF0000">*</font></td>

					</tr>

					<tr class="content1">
						<td align="right">经手人：</td>
						<td><input type="text" name="stafferName" maxlength="14"
							value="${bean.stafferName}" readonly="readonly"></td>
						<td align="right">单据号码：</td>
						<td><input type="text" name="fullId" maxlength="20"
							value="${bean.fullId}" readonly></td>
					</tr>

					<tr class="content2">
						<td align="right">送货地址：</td>
						<td colspan="3">选择地址：&nbsp;&nbsp;<select name="provinceId" quick=true values="${bean.provinceId}"
							onchange="changes(this)" class="select_class" oncheck="notNone;">
							</select>&nbsp;&nbsp; <select name="cityId" values="${bean.cityId}" quick=true onchange="changeArea()"
							class="select_class" oncheck="notNone;"></select>&nbsp;&nbsp; 
							<select name="areaId" values="${bean.areaId}" quick=true class="select_class" oncheck="notNone;"></select>&nbsp;&nbsp; <font color="#FF0000">*</font>
						</td>
					</tr>

					<tr class="content1">
						<td></td>
						<td colspan="3">详细地址：&nbsp;&nbsp;<input type="text" name="address" value="${bean.address}" oncheck="notNone;" size=100
							maxlength="300" style="width: 80%;">&nbsp;&nbsp;<font color="#FF0000">*</font></td>
					</tr>

					<tr class="content2">
						<td align="right"">收 货 人：</td>
						<td colspan="3">
						<input type="text" name="receiver" value="${bean.receiver}" oncheck="notNone;" size=20
							maxlength="30"><font color="#FF0000">*</font>
						</td>
					</tr>

					<tr class="content1">
						<td align="right">手&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
						<td colspan="3"><input type="text" value="${bean.handPhone}" name="handPhone"
							oncheck="notNone;isMathNumber"
							size=20 maxlength="11"><font color="#FF0000">*</font>
						</td>
					</tr>

					<tr class="content2">
						<td align="right">发货备注：</td>
						<td colspan="3"><textarea rows="2" cols="55" name="shipDescription"><c:out value="${bean.shipDescription}"></c:out></textarea></td>
					</tr>

					<tr class="content1">
						<td align="right">发票抬头：</td>
						<td colspan="3"><input type="text" name="invoiceHead" size="60" value="${bean.invoiceHead}"
							maxlength="60"></td>
					</tr>

					<tr class="content2">
						<td align="right">发票明细：</td>
						<td colspan="3"><textarea rows="2" cols="55"
							name="invoiceDetail"><c:out value="${bean.invoiceDetail}"></c:out></textarea></td>
					</tr>

					<tr class="content1">
						<td align="right">发票备注：</td>
						<td colspan="3"><textarea rows="2" cols="55"
							name="invoiceDescription"><c:out value="${bean.invoiceDescription}"></c:out></textarea></td>
					</tr>

					<tr class="content2">
						<td align="right">销售单备注：</td>
						<td colspan="3"><textarea rows="2" cols="55"
							name="description"><c:out value="${bean.description}"></c:out></textarea></td>
					</tr>

				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>


	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td colspan='2' align='center'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables">
					<tr align="center" class="content0">
						<td width="30%" align="center">品名</td>
						<td width="5%" align="center">数量</td>
						<td width="10%" align="center">单价</td>
						<td width="10%" align="left">金额<span id="total"></span></td>
						<td width="5%" align="center"><input type="button"
							accesskey="A" value="增加" class="button_class" onclick="addTr()"></td>
					</tr>

					<tr class="content1" id="trCopy" style="display: none;">
						<td><input type="text" name="productName"
							onclick="opens(this)" productid="" costprice=""
							midrevenue="" 
							readonly="readonly" style="width: 100%; cursor: hand">
						</td>
					
						<td align="center"><input type="text" style="width: 100%"
							maxlength="6" onkeyup="cc(this)" name="amount"></td>
					
						<td align="center"><input type="text" style="width: 100%"
							maxlength="13" onkeyup="cc(this)" cost="" onblur="blu(this)"
							name="price" readonly="readonly"></td>
					
						<td align="center"><input type="text" value="0.00"
							readonly="readonly" style="width: 100%" name="value"></td>
					
						<td align="center"></td>
					</tr>

					<tr class="content2">
						<td><input type="text" name="productName" id="unProductName"
							onclick="opens(this)" productid="${fristBase.zjrcProductId}" costprice="${fristBase.costPrice}" 
								midrevenue="${fristBase.midRevenue}" 
							readonly="readonly" value="${fristBase.zjrcProductName}"
							style="width: 100%; cursor: pointer"></td>

						<td align="center"><input type="text" style="width: 100%"
							id="unAmount" maxlength="8" onkeyup="cc(this)" name="amount" value="${fristBase.amount}"></td>

						<td align="center"><input type="text" style="width: 100%"
							id="unPrice" maxlength="13" onkeyup="cc(this)"
							onblur="blu(this)" name="price" value="${fristBase.price}" readonly="readonly"></td>

						<td align="center"><input type="text" value="0.00"
							readonly="readonly" style="width: 100%" name="value" value="${fristBase.value}"></td>

						<td><input type=button value="清空" class="button_class"
							onclick="clears()"></td>
					</tr>
					
					<c:forEach items="${lastBaseList}" var="fristBase" varStatus="vs">
					<tr class="content2">
						<td><input type="text" name="productName"
							onclick="opens(this)" productid="${fristBase.zjrcProductId}" costprice="${fristBase.costPrice}" 
							midrevenue="${fristBase.midRevenue}" 
							readonly="readonly" value="${fristBase.zjrcProductName}"
							style="width: 100%; cursor: pointer"></td>

						<td align="center"><input type="text" style="width: 100%"
							maxlength="8" onkeyup="cc(this)" name="amount" value="${fristBase.amount}"></td>

						<td align="center"><input type="text" style="width: 100%"
							maxlength="13" onkeyup="cc(this)"
							onblur="blu(this)" name="price" value="${fristBase.price}" readonly="readonly"></td>

						<td align="center"><input type="text" value="0.00"
							readonly="readonly" style="width: 100%" name="value" value="${fristBase.value}"></td>

						<td align="center"><input type=button value="删除" class=button_class onclick="removeTr(this)"></td>
					</tr>
					</c:forEach>
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td width="100%">
		<div align="right">
			<input type="button" class="button_class"
			value="&nbsp;&nbsp;保 存&nbsp;&nbsp;" onClick="save()" />&nbsp;&nbsp;
			<input type="button" class="button_class" id="sub_b"
			value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onClick="sub()" /></div>
		</td>
		<td width="0%"></td>
	</tr>

</table>
</form>
</body>
</html>


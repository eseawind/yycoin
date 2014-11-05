<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="产品库存列表" />
<base target="_self">
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/tableSort.js"></script>
<script language="javascript">

function querys()
{
	formEntry.submit();
}

function sures()
{
	add();
    
    closes();
}

function add()
{
    var opener = window.common.opener();
    
    var oo = getCheckBox("beans");
    
    if (oo && oo.length == 0)
    {
        alert('请选择产品库存');
        return;
    }
    
    if (oo)
    opener.getProductRelation(oo);
}

function closes()
{
	opener = null;
	window.close();
}

function load()
{
	loadForm();
}

function press()
{
    window.common.enter(querys);
} 

function closesd()
{
    var opener = window.common.opener();
    
    opener = null;
    window.close();
}

function resets()
{
    $O('name').value = '';
    $O('code').value = '';
    setSelectIndex($O('depotpartId'), 0);
    setSelectIndex($O('stafferId'), 0);
}

function queryAbs()
{
    formEntry.action = '../product/product.do';
    $O('method').value = 'rptQueryAbsProduct';
    $O('load').value = '1';
    $O('name').value = '';
    $O('code').value = '';
    
    formEntry.submit();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../depot/storage.do" method="post"><input
	type="hidden" name="method" value="rptQueryStorageRelationInDepot"><input
	type="hidden" value="1" name="load">
<input type="hidden" value="${depotId}" name="depotId">
<input type="hidden" value="${selectMode}" name="selectMode">
<input type="hidden" value="${showAbs}" name="showAbs">
<input type="hidden" value="${sailLocation}" name="sailLocation">
<input type="hidden" value="${queryType}" name="queryType">
<input type="hidden" value="${mtype}" name="mtype">
<input type="hidden" value="${sailType}" name="sailType">
<input type="hidden" value="${productType}" name="productType">
<input type="hidden" value="${dutyId}" name="dutyId">
<input type="hidden" value="${invoiceId}" name="invoiceId">
<p:navigation
	height="22">
	<td width="550" class="navigation">产品库存管理(最多返回前100个)</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="condition">
			<tr class="content1">
				<td width="15%" align="center">产品名称</td>
				<td align="center"><input type="text" name="name" onkeypress="press()"
					value="${name}"></td>
				<td width="15%" align="center">产品编码</td>
				<td align="center"><input type="text" name="code" onkeypress="press()"
					value="${code}"></td>
			</tr>
			
			<tr class="content2">
				<td width="15%" align="center">良品仓</td>
				<td align="center">
				<select name="depotpartId" class="select_class" values="${depotpartId}" style="width: 90%">
						<option value="">--</option>
					<c:forEach items='${depotparList}' var="item">
						<option value="${item.id}">${item.name}</option>
					</c:forEach>
				</select>
				</td>
				<td width="15%" align="center">库存属性</td>
				<td align="center">
				<select name="stafferId" class="select_class" values="${stafferId}" >
						<option value="">--</option>
						<option value="0">公共(公共库存)</option>
						<option value="${user.stafferId}">${user.stafferName}(私有库存)</option>
				</select>
				</td>
			</tr>

			<tr class="content1">
				<td colspan="4" align="right">
				<c:if test="${showAbs == 9999}">
				<input type="button"
                    onclick="queryAbs()" class="button_class"
                    value="&nbsp;&nbsp;虚拟产品&nbsp;&nbsp;">
                &nbsp;&nbsp;
				</c:if>
				<input type="button"
                    onclick="querys()" class="button_class"
                    value="&nbsp;&nbsp;查 询&nbsp;&nbsp;">
				&nbsp;&nbsp;
				<input type="button"
					onclick="resets()" class="button_class"
					value="&nbsp;&nbsp;重 置&nbsp;&nbsp;"></td>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>库存列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center">选择</td>
				<td align="center" onclick="tableSort(this)" class="td_class"><strong>仓区</strong></td>
				<td align="center" onclick="tableSort(this)" class="td_class"><strong>储位</strong></td>
				<td align="center" onclick="tableSort(this)" class="td_class"><strong>产品</strong></td>
				<td align="center" onclick="tableSort(this)" class="td_class"><strong>类型</strong></td>
				<td align="center" onclick="tableSort(this, true)" class="td_class"><strong>可发数量</strong></td>
				<td align="center" onclick="tableSort(this, true)" class="td_class"><strong>价格</strong></td>
				<td align="center" onclick="tableSort(this)" class="td_class"><strong>职员</strong></td>
			</tr>

			<c:forEach items="${beanList}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type='${selectMode == 1 ? "radio" : "checkbox"}' name="beans" ppid="${item.productId}"
					pstaffername="${item.stafferName}"
					pstafferid="${item.stafferId}"
					pdepotpartname="${item.depotpartName}"
					pdepotpartid="${item.depotpartId}"
					pname="${item.productName}" 
					pmtype="${item.productMtype}" 
					pcode="${item.productCode}" 
					pshowjosn='${item.showJOSNStr}' 
					pproducttype="${item.productType}" 
					pproductsailtype="${item.productSailType}" 
					pprice="${my:formatNum(item.price)}" 
					prealprice="${my:formatNum(item.price)}" 
					pbatchprice="${my:formatNum(item.batchPrice)}" 
					pcostprice="${my:formatNum(item.costPrice)}" 
					pamount="${item.amount}" value="${item.productId}"/></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.depotpartName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.storageName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.productName}(${item.productCode})</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('pubManagerType', item.productMtype)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.mayAmount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.price)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.stafferName}</td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<c:if test="${selectMode != 1}">
		<input type="button" class="button_class" id="adds"
            value="&nbsp;&nbsp;选 择&nbsp;&nbsp;" onClick="add()">&nbsp;&nbsp;
        </c:if>
        <input type="button" class="button_class" id="sure1"
            value="&nbsp;&nbsp;确 定&nbsp;&nbsp;" onClick="sures()" id="sures">&nbsp;&nbsp;<input type="button" class="button_class"
            value="&nbsp;&nbsp;关 闭&nbsp;&nbsp;" onClick="closesd()" id="clo">&nbsp;&nbsp;
		</div>
	</p:button>

	<p:message2 />

</p:body></form>
</body>
</html>


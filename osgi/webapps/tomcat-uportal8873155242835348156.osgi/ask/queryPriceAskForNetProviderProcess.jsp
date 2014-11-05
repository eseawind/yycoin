<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="询价列表(外网询价处理)" />
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/fanye.js"></script>
<script language="JavaScript" src="../js/tableSort.js"></script>
<script language="JavaScript" src="../js/title_div.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script language="javascript">

var ask = 0;

function querys()
{
	formEntry.submit();
}

function addBean()
{
	document.location.href = '../ask/addPriceAsk.jsp';
}

function press()
{
	if (window.common.getEvent().keyCode == 13)
	{
		querys();
	}
}

function resets()
{
	formEntry.reset();

	$O('productName').value = '';
	$O('productId').value = '';
	$O('qid').value = '';
	setSelectIndex($O('status'), 0);
}

function load()
{
	tooltip.init();
}

function del(id)
{
	if (window.confirm('确定删除询价?'))
	{
		$O('method').value = 'delPriceAsk';
		$O('id').value = id;
		formEntry.submit();
	}
}

function reject(id)
{
	if (window.confirm('确定驳回此询价?'))
	{
		$O('method').value = 'rejectPriceAsk';
		$O('id').value = id;
		formEntry.submit();
	}
}

function detail(id)
{
	document.location.href = '../stock/ask.do?method=findPriceAsk&id=' + id;
}

function selectProduct(index)
{
	ask = index;
	//单选
	window.common.modal(RPT_PRODUCT);
}

function process(id)
{
	document.location.href = '../stock/ask.do?method=preForProcessAskPrice&net=1&fw=queryPriceAskForNetProviderProcess&id=' + id;
}

function end(id)
{
	if (window.confirm('确定结束此询价?'))
	{
		document.location.href = '../stock/ask.do?method=endAskPrice&id=' + id;
	}
}

function getProduct(oo)
{
	var obj = oo[0];

	if (isNull(obj.value))
	{
		return;
	}

	$O('productId').value = obj.value;
	$O('productName').value = obj.pname;
}

var jmap = {};
<c:forEach items="${map}" var="item">
	jmap['${item.key}'] = "${item.value}";
</c:forEach>

function showDiv(id)
{
	if (jmap[id] != null && jmap[id] != '')
	tooltip.showTable(jmap[id]);
}

function updateMaxAmount(id, amount)
{
    $.messager.prompt('更新询价数量', '请询价数量', amount, function(r){
                if (r)
                {
                    $Dbuttons(true);
                    
                    var sss = r;
        
                    if (!(sss == null || sss == ''))
                    {
                        document.location.href = '../stock/ask.do?method=updatePriceAskAmount&updateMax=1&id=' + id + '&newAmount=' + sss;
                    }
                    else
                    {
                        $Dbuttons(false);
                    }
                }
                else
                {
                    alert('请输询价数量');
                }
            });
}

function updatePass(id)
{
    //updatePriceAskAmountStatus
    if (window.confirm('确定放行此询价?'))
    {
        $Dbuttons(true);
        document.location.href = '../stock/ask.do?method=updatePriceAskAmountStatus&updateMax=1&id=' + id;
    }
}
</script>

</head>
<body class="body_class" onload="load()" onkeypress="tooltip.bingEsc(event)">
<form name="formEntry" action="../stock/ask.do"><input
	type="hidden" name="method" value="queryPriceAskForNetProviderProcess"> <input
	type="hidden" name="id" value=""> <input type="hidden"
	name="productId" value="${productId}"><input type="hidden"
	value="1" name="firstLoad">
	<input type="hidden" value="${updateMax}" name="updateMax">
<p:navigation height="22">
	<td width="550" class="navigation">询价管理 &gt;&gt; 供应商询价</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>询价列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="15%"><strong>询价单号</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="5%"><strong>操作</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="15%"><strong>产品</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"
					width="5%"><strong>数量</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="5%"><strong>状态</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="5%"><strong>紧急程度</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="10%"><strong>录入时间</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="10%"><strong>处理时间</strong></td>
			</tr>

			<c:forEach items="${list}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center" onclick="hrefAndSelect(this)"
					onMouseOver="showDiv('${item.id}')" onmousemove="tooltip.move()" onmouseout="tooltip.hide()"
					>
					<a>
					<c:if test="${item.hasItem}">
					<b><font color="blue">${item.id}</font></b>
					</c:if>
					<c:if test="${!item.hasItem}">
					${item.id}
					</c:if>
					</a></td>
					<td align="center" onclick="hrefAndSelect(this)">
					
					<c:if test="${item.status == 0 || item.status == 1 }">
						<a title="处理询价" href="javascript:process('${item.id}')"> <img
							src="../images/opr/change.gif" border="0" height="15" width="15"></a>
					</c:if>

					</td>
					<td align="center" onclick="hrefAndSelect(this)">
					<a>${item.productName}</a></td>
					<td align="center" onclick="hrefAndSelect(this, true)">${item.amount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('priceAskStatus', item.status)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('priceAskInstancy',
					item.instancy)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.logTime}</td>
					<c:if test="${item.overTime == 0}">
					<td align="center" onclick="hrefAndSelect(this)"><font color=blue>${item.processTime}</font></td>
					</c:if>
					<c:if test="${item.overTime == 1}">
					<td align="center" onclick="hrefAndSelect(this)"><font color=red>${item.processTime}</font></td>
					</c:if>
				</tr>
			</c:forEach>
		</table>

		<p:formTurning form="formEntry" method="queryPriceAskForNetProviderProcess"></p:formTurning>
	</p:subBody>

	<p:line flag="1" />

	<p:message2 />

</p:body></form>
</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="采购单" />
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/title_div.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script language="javascript">
function load()
{
	loadForm();

	tooltip.init();
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

function askChange(id, name)
{
	if (window.confirm('确定修改产品[' + name + "]的供应商?"))
	{
		var pid = $$('newProvide_' + id);
		document.location.href = '../stock/stock.do?method=stockItemAskChange&stockId=${bean.id}&id=' + id + "&providerId=" + pid;
	}
}

function passTO()
{
	$.messager.prompt('最早付款日期', '请选择最早付款日期', '', function(value, opr){
                if (opr)
                {
                    $Dbuttons(true);
                    
                    var sss = value;
                    
                    if (!(sss == null || sss == ''))
                    {
                    	$O('nearlyPayDate').value = sss;
                        $O('method').value = 'updateStockStatus';
						$O('reject').value = '';
						$O('pass').value = '1';
						formEntry.submit();
                    }
                    else
                    {
                    	alert('请选择最早付款日期');
                    	
                        $Dbuttons(false);
                        
                        passTo(id, type);
                    }
                }
            }, 1);
}


function rejectToAsk()
{
	 $.messager.prompt('驳回原因', '请输入原因', '', function(r, opr){
                if (opr)
                {
                    $Dbuttons(true);
                    
                    var sss = r;
                    
                    if (!(sss == null || sss == ''))
                    {
                    	$O('reason').value = sss;
                        $O('pass').value = '';
						$O('reject').value = '2';
						formEntry.submit();
                    }
                    else
                    {
                    	alert('请输入驳回原因');
                    	
                        $Dbuttons(false);
                        
                        rejectToAsk();
                    }
                }
            });
}
</script>

</head>
<body class="body_class" onload="load()" onkeydown="tooltip.bingEsc(event)">
<form name="formEntry" action="../stock/stock.do" method="post">
<input type="hidden" name="method" value="updateStockStatus">
<input type="hidden" name="id" value="${bean.id}">
<input type="hidden" name="pass" value="1">
<input type="hidden" value="" name="reason"> 
<input type="hidden" name="reject" value="1">
<input type="hidden" value="" name="nearlyPayDate">
<p:navigation height="22">
	<td width="550" class="navigation">采购单明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>采购单信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:table cells="2">
			<p:cell title="单号">
			${bean.id}
			</p:cell>

			<p:cell title="采购人">
			${bean.userName}
			</p:cell>

			<p:cell title="区域">
			${bean.locationName}
			</p:cell>

			<p:cell title="状态">
			${my:get('stockStatus', bean.status)}
			</p:cell>

			<p:cell title="录入时间">
			${bean.logTime}
			</p:cell>

			<p:cell title="需到货时间">
			${bean.needTime}
			</p:cell>

			<p:cell title="物流">
			${bean.flow}
			</p:cell>

			<p:cell title="总计金额">
			${my:formatNum(bean.total)}
			</p:cell>

			<p:cells celspan="2" title="备注">
			${bean.description}
			</p:cells>
		</p:table>
	</p:subBody>

	<p:tr />

	<p:subBody width="100%">
		<table width="100%" border="0" cellspacing='1' id="tables">
			<tr align="center" class="content0">
				<td width="15%" align="center">采购产品</td>
				<td width="10%" align="center">采购数量</td>
				<td width="10%" align="center">当前数量</td>
				<td width="10%" align="center">是否询价</td>
				<td width="10%" align="center">参考价格</td>
				<td width="10%" align="center">实际价格</td>
				<td width="25%" align="center">供应商</td>
				<td width="5%" align="center">合计金额</td>
				<td width="10%" align="center">操作</td>
			</tr>

			<c:forEach items="${bean.itemVO}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center" style="cursor: pointer;"
					onMouseOver="showDiv('${item.id}')" onmousemove="tooltip.move()" onmouseout="tooltip.hide()"
					>${item.productName}</td>

					<td align="center">${item.amount}</td>
					
					<td align="center">${item.productNum}</td>

					<td align="center">${item.status == 0 ? "否" : "是"}</td>

					<td align="center">${my:formatNum(item.prePrice)}</td>

					<td align="center">${my:formatNum(item.price)}</td>

					<td align="left">
					<select style="width: 250px;" id="newProvide_${item.id}" values="${item.providerId}">
					<c:forEach items="${map1[item.id]}" var="item1">
					<option value="${item1.providerId}">${item1.providerName}</option>
					</c:forEach>
					</select>
					</td>

					<td align="center">${my:formatNum(item.total)}</td>

					<td align="center">
					<a title="变动系统选择询价" href="javascript:askChange('${item.id}', '${item.productName}')"> <img
								src="../images/opr/change.gif" border="0" height="15" width="15"></a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:tr />

	<p:subBody width="100%">
		<table width="100%" border="0" cellspacing='1' id="tables">
			<tr align="center" class="content0">
				<td width="10%" align="center">审批人</td>
				<td width="10%" align="center">审批动作</td>
				<td width="10%" align="center">前状态</td>
				<td width="10%" align="center">后状态</td>
				<td width="45%" align="center">意见</td>
				<td width="15%" align="center">时间</td>
			</tr>

			<c:forEach items="${logs}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center">${item.actor}</td>

					<td align="center">${item.oprModeName}</td>

					<td align="center">${item.preStatusName}</td>

					<td align="center">${item.afterStatusName}</td>

					<td align="center">${item.description}</td>

					<td align="center">${item.logTime}</td>

				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			name="sub1" style="cursor: pointer"
			value="&nbsp;&nbsp;通 过&nbsp;&nbsp;" onclick="passTO()">&nbsp;&nbsp;
			<c:if test="${bean.type == 0}">
			<input type="button" class="button_class"
			name="sub1" style="cursor: pointer"
			value="&nbsp;&nbsp;驳回到询价&nbsp;&nbsp;" onclick="rejectToAsk()">
			&nbsp;&nbsp;
			</c:if>
			<input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>


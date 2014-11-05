<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="采购单列表" />
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/title_div.js"></script>
<script language="JavaScript" src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script language="javascript">
function querys()
{
	$O('method').value = 'queryStock';
	formEntry.submit();
}

function addBean()
{
	document.location.href = '../stock/stock.do?method=preForAddStock';
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

	$O('ids').value = '';
	setSelectIndex($O('status'), 0);
	setSelectIndex($O('locationId'), 0);
}

function load()
{
	loadForm();

	$f('ids');

	$O('update').value = '';
	
	//tooltip.init ();
}

function del(id)
{
	if (window.confirm('确定删除采购单?'))
	{
		$O('method').value = 'delStock';
		$O('id').value = id;
		formEntry.submit();
	}
}

function reject(id)
{
	if (window.confirm('确定驳回此采购单?'))
	{
		var sss = window.prompt('请输入驳回销售单原因：', '');

		$O('reason').value = sss;

		if (!(sss == null || sss == ''))
		{
			$O('method').value = 'updateStockStatus';
			$O('id').value = id;
			$O('reject').value = '1';
			$O('pass').value = '';
			formEntry.submit();
		}
		else
		{
			alert('请输入驳回原因');
		}
	}
}

function reject(id)
{
    $.messager.prompt('驳回原因', '请输入原因', '', function(r, opr){
                if (opr)
                {
                    $Dbuttons(true);
                    
                    var sss = r;
                    
                    if (!(sss == null || sss == ''))
                    {
                    	$O('reason').value = sss;
                        $O('method').value = 'updateStockStatus';
						$O('id').value = id;
						$O('reject').value = '1';
						$O('pass').value = '';
						formEntry.submit();
                    }
                    else
                    {
                    	alert('请输入驳回原因');
                    	
                        $Dbuttons(false);
                        
                        reject(id);
                    }
                }
            });
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

function passTo(id, type, stype)
{
	//正常审批 或者是 代销采购(这路不选择付款时间了)
	if ((type != 4 && type != 2) || stype == '1' || true)
	{
		if (window.confirm('确定通过此采购单?'))
		{
			$O('method').value = 'updateStockStatus';
			$O('id').value = id;
			$O('reject').value = '';
			$O('pass').value = '1';
			formEntry.submit();
		}
	}
	else
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
						$O('id').value = id;
						$O('reject').value = '';
						$O('pass').value = '1';
						formEntry.submit();
                    }
                    else
                    {
                    	alert('请选择最早付款日期');
                    	
                        $Dbuttons(false);
                        
                        passTo(id, type, stype);
                    }
                }
            }, 1);
	}
}

function sub(id)
{
	if (window.confirm('确定提交此采购单?'))
	{
		$O('method').value = 'updateStockStatus';
		$O('id').value = id;
		$O('reject').value = '';
		$O('pass').value = '1';
		formEntry.submit();
	}
}

function update(id)
{
	$O('method').value = 'findStock';
	$O('id').value = id;
	$O('stockAskChange').value = '';
	$O('process').value = '';
	$O('update').value = '1';
	formEntry.submit();
}

function update2(id)
{
    $O('method').value = 'findStock';
    $O('id').value = id;
    $O('stockAskChange').value = '';
    $O('process').value = '';
    $O('update').value = '2';
    formEntry.submit();
}

function ask(id)
{
	$O('method').value = 'findStock';
	$O('id').value = id;
	$O('stockAskChange').value = '';
	$O('process').value = '1';
	$O('update').value = '';
	formEntry.submit();
}

function askChange(id)
{
	$O('method').value = 'findStock';
	$O('id').value = id;
	$O('stockAskChange').value = '1';
	$O('process').value = '';
	$O('update').value = '';
	formEntry.submit();
}

function end(id)
{
	$.messager.prompt('发货备注(自动生成入库单)', '请输入发货备注', '', function(r, opr){
                if (opr)
                {
                    $Dbuttons(true);
                    
                    var sss = r;
                    
                    if (!(sss == null || sss == ''))
                    {
                    	$O('reason').value = sss;
                        $O('method').value = 'endStock';
						$O('id').value = id;
						formEntry.submit();
                    }
                    else
                    {
                    	alert('请输入备注');
                    	
                        $Dbuttons(false);
                    }
                }
            }, 2);
}

function getProduct(id)
{
	$O('method').value = 'findStock';
	$O('id').value = id;
	$O('stockAskChange').value = '';
	$O('process').value = '2';
	$O('update').value = '';
	formEntry.submit();
}

function confirmProduct(id)
{
	$O('method').value = 'findStock';
	$O('id').value = id;
	$O('stockAskChange').value = '';
	$O('process').value = '21';
	$O('update').value = '';
	formEntry.submit();
}

function endProduct(id)
{
	$.messager.prompt('异常结束', '原因', '', function(r){
        if (r)
        {
            $Dbuttons(true);
            getObj('method').value = 'endProduct';
            $O('id').value = id;

            var sss = r;

            getObj('reason').value = r;

            if (!(sss == null || sss == ''))
            {
            	formEntry.submit();
            }
            else
            {
                $Dbuttons(false);
            }
        }
       
    });
}

function pay(id, status)
{
	var tipMap = {"1": "确定批准采购主管的采购付款申请,同时自动生成付款单?", "2": "确定向采购经理申请采购付款?", "3": "确定驳回采购主管采购付款申请?"};

	if (window.confirm(tipMap[status]))
	{
		$O('method').value = 'payStock';
		$O('id').value = id;
		$O('payStatus').value = status;
		formEntry.submit();
	}
}

function out(id)
{
	document.location.href = '../stock/stock.do?method=findStock&id=' + id + "&out=1";
}

function exports()
{
	document.location.href = '../stock/stock.do?method=exportStock';
}
</script>

</head>
<body class="body_class" onload="load()"
	onkeypress="tooltip.bingEsc(event)">
<form name="formEntry" action="../stock/stock.do"><input
	type="hidden" name="method" value="queryStock"> <input
	type="hidden" value="1" name="firstLoad"> <input type="hidden"
	value="" name="id">
<input type="hidden" value="" name="pass">
<input type="hidden" value="${ltype}" name="ltype">
<input type="hidden" value="" name="nearlyPayDate">
<input type="hidden" value="${type}" name="type">
<input type="hidden" value="" name="payStatus"> <input
	type="hidden" value="" name="reject"> <input type="hidden"
	value="" name="reason"> <input type="hidden" value=""
	name="update"><input type="hidden" value=""
	name="stockAskChange"> <input type="hidden" value=""
	name="process"> 
<input type="hidden" value="${extraStatus}" name="extraStatus">
<p:navigation height="22">
	<td width="550" class="navigation">采购单管理 &gt;&gt; 采购单列表${g_ltype}</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center">开始时间</td>
				<td align="center" width="35%"><p:plugin name="alogTime"
					value="${alogTime}" /></td>
				<td align="center">结束时间</td>
				<td align="center" width="35%"><p:plugin name="blogTime"
					value="${blogTime}" /></td>
			</tr>

			<tr align=center class="content1">
				<td align="center">单据</td>
				<td align="center" width="35%"><input type="text"
					onkeydown="press()" name="ids" value="${ids}"></td>
				<td align="center">状态</td>
				<td align="center" width="35%"><select name="status"
					class="select_class" values="${status}">
					<option value="">--</option>
					<p:option type="stockStatus"></p:option>
				</select></td>
			</tr>

			<tr align=center class="content0">
				<td align="center">采购区域</td>
				<td align="center" width="35%"><select name="locationId"
					class="select_class" values="${locationId}">
					<option value="">--</option>
					<c:forEach items="${locations}" var="item">
						<option value="${item.id}">${item.name}</option>
					</c:forEach>
				</select></td>
				<td align="center">采购类型</td>
				<td align="center" width="35%"><select name="stype"
					class="select_class" values="${stype}">
					<option value="">--</option>
					<p:option type="stockStype"></p:option>
				</select></td>
			</tr>

			<tr align=center class="content0">
				<td align="center">是否逾期</td>
				<td align="center" width="35%"><select name="over"
					class="select_class" values="${over}">
					<option value="">--</option>
					<option value="0">未逾期</option>
					<option value="1">已逾期</option>
				</select></td>
				<td align="center">采购模式</td>
                <td align="center" width="35%">
                <select name="mode"
                    class="select_class" values="${mode}">
                    <option value="">--</option>
                    <option value="0">销售采购</option>
                    <option value="1">生产采购</option>
                </select>
                </td>
			</tr>
			
			<tr align=center class="content0">
                <td align="right" colspan="4"><input type="button" id="b_query"
                    class="button_class" value="&nbsp;&nbsp;查 询&nbsp;&nbsp;"
                    onclick="querys()">&nbsp;&nbsp; <input type="button"
                    class="button_class" value="&nbsp;&nbsp;重 置&nbsp;&nbsp;" id="b_reset"
                    onclick="resets()"></td>
            </tr>
		</table>

	</p:subBody>


	<p:title>
		<td class="caption"><strong>采购单列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="10%"><strong>单据</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="5%"><strong>采购人</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="10%"><strong>状态</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="5%"><strong>采购区域</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="10%"><strong>到货日期</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"
					width="7%"><strong>合计金额</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="5%"><strong>采购类型</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
                    width="5%"><strong>采购模式</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="10%"><strong>更新时间</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="8%"><strong>操作</strong></td>
			</tr>

			<c:forEach items="${list}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center" onclick="hrefAndSelect(this)"
						onMouseOver="showDiv('${item.id}')" onmousemove="tooltip.move()"
						onmouseout="tooltip.hide()"><a onclick="hrefAndSelect(this)"
						href="../stock/stock.do?method=findStock&id=${item.id}">
					${item.id} </a></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.userName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('stockStatus', item.status)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.locationName}</td>
					<c:if test="${item.overTime == 0}">
						<td align="center" onclick="hrefAndSelect(this)"><font
							color=blue>${item.needTime}</font></td>
					</c:if>
					<c:if test="${item.overTime == 1}">
						<td align="center" onclick="hrefAndSelect(this)"><font
							color=red>${item.needTime}</font></td>
					</c:if>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.total)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('stockStype', item.stype)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('stockMode', item.mode)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.logTime}</td>
					
					<td align="center" onclick="hrefAndSelect(this)">
					
					<c:if test="${item.display == 0}">
					
						<c:if test="${ltype == '0'}">
							<a title="提交采购单" href="javascript:sub('${item.id}')"> <img
								src="../images/opr/realse.gif" border="0" height="15" width="15"></a>

							<a title="修改采购单" href="javascript:update('${item.id}')"> <img
								src="../images/opr/edit.gif" border="0" height="15" width="15"></a>

							<a title="删除采购单" href="javascript:del('${item.id}')"> <img
								src="../images/opr/del.gif" border="0" height="15" width="15"></a>
						</c:if>

						<c:if test="${ltype == '1'  || ltype == '4' || ltype == '5' || ltype == '6' || ltype == '9' || ltype == '10'}">
							<a title="审批通过采购单" href="javascript:passTo('${item.id}', '${ltype}', '${item.stype}')"> <img id="img_${vs.index}"
								src="../images/opr/realse.gif" border="0" height="15" width="15"></a>

							<a title="驳回采购单" href="javascript:reject('${item.id}')"> <img
								src="../images/opr/reject.gif" border="0" height="15" width="15"></a>
						</c:if>

						<c:if test="${ltype == '3'}">
							<a title="采购单询价" href="javascript:ask('${item.id}')"> <img id="ask_img_${vs.index}"
								src="../images/opr/change.gif" border="0" height="15" width="15"></a>

							<a title="审批通过采购单" href="javascript:passTo('${item.id}', '${ltype}', '${item.stype}')"> <img
								src="../images/opr/realse.gif" border="0" height="15" width="15"></a>

							<a title="驳回采购单" href="javascript:reject('${item.id}')"> <img
								src="../images/opr/reject.gif" border="0" height="15" width="15"></a>
						</c:if>
						
						<c:if test="${ltype == '2'}">
						    <a title="采购税务配置" href="javascript:update2('${item.id}')"> <img
                                src="../images/opr/edit.gif" border="0" height="15" width="15"></a>
                                  
                            <a title="采购单询价" href="javascript:ask('${item.id}')"> <img id="ask_img_${vs.index}"
                                src="../images/opr/change.gif" border="0" height="15" width="15"></a>
                                
                            <a title="审批通过采购单" href="javascript:passTo('${item.id}', '${ltype}', '${item.stype}')"> <img
								src="../images/opr/realse.gif" border="0" height="15" width="15"></a>

                            <a title="驳回采购单" href="javascript:reject('${item.id}')"> <img
                                src="../images/opr/reject.gif" border="0" height="15" width="15"></a>
                        </c:if>
						
						<c:if test="${ltype == '4'}">
							<c:if test="${item.stype != 1}">
							<a title="询价变动" href="javascript:askChange('${item.id}')"> <img
								src="../images/opr/change.gif" border="0" height="15" width="15"></a>
							</c:if>
							<c:if test="${item.status == 7}">
								<a title="采购结束" href="javascript:end('${item.id}')"> <img id="end_img_${vs.index}"
									src="../images/opr/end.gif" border="0" height="15" width="15"></a>
							</c:if>
						</c:if>
						
					</c:if> 
					
					
					<c:if test="${ltype == '7'}">
						<c:if test="${item.status == 7}">
							<a title="采购结束" href="javascript:end('${item.id}')"> <img id="end_img_${vs.index}"
								src="../images/opr/end.gif" border="0" height="15" width="15"></a>
						</c:if>
					
					</c:if> 
					
					<c:if test="${ltype == '8'}">
						<c:if test="${item.status == 6}">
							<c:if test="${extraStatus == 0}">
								<a title="预确认" href="javascript:confirmProduct('${item.id}')"> <img
									src="../images/opr/change.gif" border="0" height="15" width="15"></a>
							</c:if>
							<c:if test="${extraStatus != 0}">
								<a title="询价人拿货" href="javascript:getProduct('${item.id}')"> <img
									src="../images/opr/change.gif" border="0" height="15" width="15"></a>
							</c:if>
						</c:if>
					
					</c:if> 
					
					<c:if test="${ltype == '99'}">
							<c:if test="${item.status != 8}">
								<a title="异常结束" href="javascript:endProduct('${item.id}')"> <img
									src="../images/opr/change.gif" border="0" height="15" width="15"></a>
							</c:if>
					
					</c:if> 
					
					</td>
				</tr>
			</c:forEach>
		</table>

		<p:formTurning form="formEntry" method="queryStock"></p:formTurning>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<c:if test="${ltype == '2' || ltype == '1'|| ltype == '0'}">
		<input type="button" class="button_class"
			id="b_export" style="cursor: pointer"
			value="&nbsp;&nbsp;导出采购单&nbsp;&nbsp;" onclick="exports()">
		</c:if>
			
		</div>
	</p:button>

	<p:message2 />

</p:body></form>
</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="销售空开空退" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../sail_js/addOut.js"></script>
<script language="JavaScript" src="../js/jquery/jquery.js"></script>
<script language="JavaScript" src="../js/plugin/highlight/jquery.highlight.js"></script>
<script language="javascript">

<%@include file="../sail_js/out.jsp"%>

function load()
{
    loadForm();
    
    hides(true);
    
    $detail($O('viewTable'), ['pr', 'ba']);
    
    highlights($("#mainTable").get(0), ['未付款'], 'red');
    
    highlights($("#mainTable").get(0), ['已付款'], 'blue');
}

function hides(boo)
{
    
}


function outRepaire()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return false
	}
	
    submit('确定申请销售空开空退?');  
}


</script>
</head>
<body class="body_class" onload="load()">
<form name="outForm" method=post action="../sail/out.do">
<input type=hidden name="method" value="outRepaire">
<input type=hidden name="outId" value="${bean.fullId}"> 

<div id="na">
<p:navigation
	height="22">
	<td width="550" class="navigation">销售空开空退</td>
				<td width="85"></td>
</p:navigation> <br>
</div>

<table width="95%" border="0" cellpadding="0" cellspacing="0" id="viewTable"
	align="center">

	<tr>
		<td colspan='2' align='center'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="mainTable"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content2">
						<td width="15%" align="right">销售日期：</td>

						<td width="35%"><input type="text" name="outTime"
							value="${bean.outTime}" maxlength="20" size="20"
							readonly="readonly"><font color="#FF0000">*</font></td>

							<td width="15%" align="right">销售类型：</td>
							<td width="35%"><select name="outType" class="select_class" onchange="managerChange()" values="${bean.outType}" readonly=true>
								<p:option type="outType_out"></p:option>
							</select><font color="#FF0000">*</font></td>
						
					</tr>

					<tr class="content1">
						<td align="right" id="outd">客户：</td>
						<td><input type="text" name="customerName" maxlength="14" value="${bean.customerName}" onclick="selectCustomer()"
							 style="cursor: pointer;"
							readonly="readonly"><font color="#FF0000">*</font></td>
						<td align="right">销售部门：</td>
						<td><select name="department" class="select_class" values="${bean.department}">
							<option value=''>--</option>
							<c:forEach items='${departementList}' var="item">
								<option value="${item.name}">${item.name}</option>
							</c:forEach>
						</select><font color="#FF0000">*</font></td>
					</tr>
					
					<tr class="content2">
						<td align="right">联系人：</td>
						<td><input type="text" name="connector" maxlength="14" value="${bean.connector}"
							readonly="readonly"></td>
						<td align="right">联系电话：</td>
						<td><input type="text" name="phone" maxlength="20" readonly="readonly" value="${bean.phone}"></td>
					</tr>
					<tr class="content1">
						<td align="right">经手人：</td>
						<td><input type="text" name="operatorName" maxlength="14"
							value="${bean.operatorName}" readonly="readonly"></td>
						<td align="right">单据标识：</td>
						<td><input type="text" name="fullId" maxlength="40"
							value="${bean.fullId}" readonly="readonly"></td>
					</tr>

                    <tr class="content1">
                        <td align="right">已支付：</td>
                        <td colspan="1">
                        ${my:formatNum(bean.hadPay)}
                        </td>
                        <td align="right">总金额：</td>
                        <td colspan="1">
                       ${my:formatNum(bean.total)}
                       </td>
                    </tr>
                    
                     <tr class="content2">
                        <td align="right">状态：</td>
                        <td colspan="1">
                        <select name="status" class="select_class"  values="${bean.status}">
                           <p:option type="outRepaireStatus"></p:option>
                        </select>
                        </td>
                        <td align="right">申请人：</td>
                        <td colspan="1">
                       ${bean.stafferName}
                       </td>
                    </tr>
                    
                    <tr class="content2">
						<td align="right">回款天数：</td>
						<td colspan="1"><input type="text" name="reday" maxlength="4" oncheck="notNone;isInt;range(1, 180)"
							value="${bean.reday}" title="请填入1到180之内的数字"><font color="#FF0000">*</font></td>

						<td align="right">新回款天数：</td>
						<td colspan="1"><input type="text" name="newReday" maxlength="4" oncheck="notNone;isInt;range(1, 180)"
							value="${bean.newReday}" title="请填入1到180之内的数字"><font color="#FF0000">*</font></td>
					</tr>
					
					<tr class="content1">
                        <td align="right">纳税实体：</td>
                        <td colspan="1">
                        <select name="dutyId" class="select_class" style="width: 240px" values="${bean.dutyId}" >
                            <c:forEach items="${dutyList}" var="item">
                            <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                        <font color="#FF0000">*</font></td>
                        <td align="right">新纳税实体：</td>
                        <td colspan="1">
                        <select name="newDutyId" class="select_class" style="width: 240px" values="${bean.newDutyId}">
                            <c:forEach items="${dutyList}" var="item">
                            <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                        <font color="#FF0000">*</font>
                    </tr>
                    
                    <tr class="content2">
                        <td align="right">发票类型：</td>
                        <td colspan="1">
                        <select name="invoiceId" class="select_class" head="发票类型" style="width: 90%" values="${bean.invoiceId}">
                           <option value="">没有发票</option>
                           <c:forEach items="${invoiceList}" var="item">
                            <option value="${item.id}">${item.fullName}</option>
                            </c:forEach>
                        </select>
                        <font color="#FF0000">*</font></td>
                        <td align="right">新发票类型：</td>
                        <td colspan="1">
                        <select name="newInvoiceId" class="select_class" head="发票类型" style="width: 90%" values="${bean.newInvoiceId}">
                           <option value="">没有发票</option>
                            <c:forEach items="${invoiceList}" var="item">
                            <option value="${item.id}">${item.fullName}</option>
                            </c:forEach>
                        </select>
                        <font color="#FF0000">*</font>
                    </tr>
                    
                    <tr class="content1">
						<td align="right">备注：</td>
						<td colspan="3"><textarea rows="3" cols="55"
							name="description"><c:out value='${bean.description}'/></textarea>
							</td>
					</tr>
                    
					<tr class="content2">
                        <td align="right">空开空退原因：</td>
                        <td colspan="1"><font color="#FF0000">
                       <select name=adescription class="select_class" readonly="readonly" values="${bean.eventId}">
								<p:option type="outRepaireReason" empty="true"></p:option>
							</select></font>
                       </td>
                       <td align="right">经办人：</td>
                        <td colspan="1">
                       ${bean.operatorName}
                       </td>
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
						<td width="20%" align="center">品名</td>						
						<td width="5%" align="center">数量</td>
						<td width="10%" align="center">销售价</td>
						<td width="10%" align="center">新销售价</td>
						<td width="10%" align="center">成本</td>
						<td width="10%" align="center">新成本</td>
						<td width="20%" align="center">开票品名</td>
						<td width="25%" align="center">新开票品名</td>
					</tr>

					<c:forEach items="${bean.baseList}" var="fristBase" varStatus="vs">
                    <tr class="content2">
                        <td align="center"><input type="text" name="productName"                            
                            readonly="readonly"
                            style="width: 100%; cursor: pointer"
                            value="${fristBase.productName}" />
                            </td>

                        <td align="center"><input type="text" style="width: 100%"  value="${fristBase.amount}"
                            maxlength="6" onkeyup="cc(this)" name="amount"></td>

                        <td align="center"><input type="text" style="width: 100%"  value="${my:formatNum(fristBase.price)}"
                            maxlength="8" onkeyup="cc(this)" onblur="blu(this)" name="nprice"></td>

						<td align="center"><input type="text" style="width: 100%"  value="${my:formatNum(fristBase.costPrice)}"
                            maxlength="8" onkeyup="cc(this)" onblur="blu(this)" name="price"></td>

                        <td align="center"><input type="text"  readonly="readonly" value="${my:formatNum(fristBase.inputPrice)}"
                            style="width: 100%" name="ninputPrice"></td>
                            
                        <td align="center"><input type="text"  value="${my:formatNum(fristBase.invoiceMoney)}"
                            style="width: 100%" name="inputPrice"></td>
                            
                       <td  align="center">
						<input name="noutProductName" style="WIDTH: 150px;" readonly="readonly" value="${fristBase.showName}">
						</td>
                            
                        <td  align="center">
						<select name="outProductName" style="WIDTH: 150px;" quick=true values="${fristBase.showId}" oncheck="notNone;">
						</select>
						
						<input type="hidden" name="baseId" value="${fristBase.id}"/>
						</td>

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

			<c:forEach items="${logList}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center">${item.actor}</td>

					<td align="center">${my:get('oprMode', item.oprMode)}</td>

					<td align="center">${my:get('outRepaireStatus', item.preStatus)}</td>

					<td align="center">${my:get('outRepaireStatus', item.afterStatus)}</td>

					<td align="center">${item.description}</td>

					<td align="center">${item.logTime}</td>

				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
        <td width="100%">
        <div align="right"><input
            type="button" name="ba" class="button_class"
            onclick="javascript:history.go(-1)"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
        </td>
        <td width="0%"></td>
    </tr>

</table>
</form>
</body>
</html>


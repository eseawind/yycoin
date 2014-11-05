<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="销售退货" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../sail_js/addOut.js"></script>
<script language="JavaScript" src="../js/jquery/jquery.js"></script>
<script language="JavaScript" src="../js/plugin/highlight/jquery.highlight.js"></script>
<script language="javascript">

<%@include file="../sail_js/out.jsp"%>

function load()
{
    titleChange();
    
    loadForm();
    
    loadShow();
    
    loadForm();

    hides(true);

    <c:if test="${fristBase.costPrice==0}">
    	$detail($O('viewTable'), ['pr', 'ba', 'backUnm', 'adescription', 'dirDeport','desciprt']);
    </c:if>

    <c:if test="${fristBase.costPrice>0}">
	$detail($O('viewTable'), ['pr', 'ba', 'backUnm', 'adescription', 'dirDeport']);
	</c:if>
    
    highlights($("#mainTable").get(0), ['未付款'], 'red');
    
    highlights($("#mainTable").get(0), ['已付款'], 'blue');
}

function hides(boo)
{
    
}


function outBack()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return false
	}
	
	if (costCheck())
	{
		submit('确定根据原销售单强制入库?');
	} 
}

function costCheck()
{
	
	var backUnms = document.getElementsByName('backUnm');
    var desList = document.getElementsByName('desciprt');
    
    for (var i = 0; i < desList.length; i++)
    {

		if (trim(backUnms[i].value) == '0')
		{
			continue;
		}
        
        if (trim(desList[i].value) == '')
        {
            alert('成本是必填!');
            desList[i].focus();
            return false;
        }
        
        if (!isFloat(desList[i].value))
        {
            alert('格式错误,成本只能是浮点数!');
            desList[i].focus();
            return false;
        }
        
        if (parseFloat(trim(desList[i].value)) == 0)
        {
            alert('入库成本价格不能为0!');
            desList[i].focus();
            return false;
        }
        
    }

    return true;
}

</script>
</head>
<body class="body_class" onload="load()">
<form name="outForm" method=post action="../sail/out.do">
<input type=hidden name="method" value="outBack3">
<input type=hidden name="outId" value="${bean.fullId}"> 
<input type="hidden" name="forceBuyType" value="${forceBuyType}">
<input type="hidden" name="addOutDutyId" value="${dutyId}">

<div id="na">
<p:navigation
	height="22">
	<td width="550" class="navigation">关联原单强制入库</td>
				<td width="85"></td>
</p:navigation> <br>
</div>

<table width="95%" border="0" cellpadding="0" cellspacing="0" id="viewTable"
	align="center">
	<tr>
		<td valign="top" colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!--DWLayoutTable-->
			<tr>
				<td width="784" height="6"></td>
			</tr>
			<tr>
				<td align="center" valign="top">
				<div align="left">
				<table width="90%" border="0" cellspacing="2">
					<tr>
						<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="10">
							<tr>
								<td width="35">&nbsp;</td>
								<td width="6"><img src="../images/dot_r.gif" width="6"
									height="6"></td>
								<td class="caption"><strong>填写销售单信息:<font color=red>${hasOver}</font> 您的信用额度还剩下:${credit}</strong>
								<font color="blue">产品仓库：</font>
								<select name="location" class="select_class"  onchange="clearsAll()" values="${bean.location}" readonly=true>
									<c:forEach items='${locationList}' var="item">
										<option value="${item.id}">${item.name}</option>
									</c:forEach>
								</select>
								</td>
							</tr>
						</table>
						</td>
					</tr>


				</table>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>


	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

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
						<td><input type="text" name="stafferName" maxlength="14"
							value="${user.stafferName}" readonly="readonly"></td>
						<td align="right">单据标识：</td>
						<td><input type="text" name="fullId" maxlength="40"
							value="${bean.fullId}" readonly="readonly"></td>
					</tr>

					<tr class="content2">
						<td align="right">回款天数：</td>
						<td colspan="1"><input type="text" name="reday" maxlength="4" oncheck="notNone;isInt;range(1, 180)"
							value="${bean.reday}" title="请填入1到180之内的数字"><font color="#FF0000">*</font></td>

						<td align="right">到货日期：</td>
						<td><p:plugin name="arriveDate"  size="20" oncheck="notNone;cnow('30')" value="${bean.arriveDate}"/><font color="#FF0000">*</font></td>
					</tr>
					
					<tr class="content1">
                        <td align="right">付款方式：</td>
                        <td colspan="1">
                        <select name="reserve3" class="select_class" oncheck="notNone;" head="付款方式" style="width: 240px" values="${bean.reserve3}">
                            <option value='2'>客户信用和业务员信用额度担保</option>
                            <option value='1'>款到发货(黑名单客户/零售)</option>
                            <option value='3'>信用担保</option>
                        </select>
                        <font color="#FF0000">*</font></td>
                        <td align="right">纳税实体：</td>
                        <td colspan="1">
                        <select name="dutyId" class="select_class" style="width: 240px" values="${bean.dutyId}">
                            <c:forEach items="${dutyList}" var="item">
                            <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                        <font color="#FF0000">*</font></td>
                    </tr>
                    
                    <tr class="content2">
                        <td align="right">发票类型：</td>
                        <td colspan="1">
                        <select name="invoiceId" class="select_class" head="发票类型" style="width: 400px" values="${bean.invoiceId}">
                           <option value="">没有发票</option>
                            <c:forEach items="${invoiceList}" var="item">
                            <option value="${item.id}">${item.fullName}</option>
                            </c:forEach>
                        </select>
                        <font color="#FF0000">*</font></td>
                        <td align="right">总金额：</td>
                        <td colspan="1">
                       ${my:formatNum(bean.total)}
                       </td>
                    </tr>
                    
                    <tr class="content1">
                        <td align="right">已支付：</td>
                        <td colspan="1">
                        ${my:formatNum(bean.hadPay)}
                        </td>
                        <td align="right">坏账金额：</td>
                        <td colspan="1">
                        ${my:formatNum(bean.badDebts)}
                        </td>
                    </tr>
                    
                     <tr class="content2">
                        <td align="right">状态：</td>
                        <td colspan="1">
                        <select name="status" class="select_class"  values="${bean.status}">
                           <p:option type="outStatus"></p:option>
                        </select>
                        </td>
                        <td align="right">申请人：</td>
                        <td colspan="1">
                       ${bean.stafferName}
                       </td>
                    </tr>
                    
                     <tr class="content1">
                       <td align="right">付款状态：</td>
                        <td colspan="1">
                       ${my:get('outPay', bean.pay)}
                       </td>
                    </tr>
                    
                    <tr class="content2">
                        <td align="right">信用描述：</td>
                        <td colspan="3">
                        <font color="red">
                       ${bean.reserve6}
                       </font>
                       </td>
                    </tr>
                    
                     <tr class="content1">
                        <td align="right">信用担保：</td>
                        <td colspan="3">
                       客户:${my:formatNum(bean.curcredit)}/${bean.stafferName}:${my:formatNum(bean.staffcredit)}/分公司经理:${my:formatNum(bean.managercredit)}
                       </td>
                    </tr>

					<tr class="content2">
						<td align="right">销售单备注：</td>
						<td colspan="3"><textarea rows="3" cols="55" oncheck="notNone;"
							name="description"><c:out value="${bean.description}"/></textarea>
							<font color="#FF0000">*</font>
							</td>
					</tr>
					
					 <tr class="content1">
                        <td align="right">总部核对：</td>
                        <td colspan="3">
                       ${bean.checks}
                       </td>
                    </tr>
                    
                     <tr class="content2">
                        <td align="right">退货仓库：</td>
                        <td colspan="3">
                        <select name="dirDeport" style="WIDTH: 300px;" values="${location}" oncheck="notNone">
                            <c:forEach items='${locationList}' var="item">
                                        <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select><font color="#FF0000">*</font>
                       </td>
                    </tr>
                    
                    <tr class="content1">
                        <td align="right">退货备注：</td>
                        <td colspan="3">
                       <textarea rows="3" cols="55" oncheck="notNone;"
                            name="adescription"><c:out value="${description}"/></textarea>
                            <font color="#FF0000">*</font>
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
						<td width="5%" align="center">单位</td>
						<td width="5%" align="center">数量</td>
						<td width="10%" align="center">销售价</td>
						<td width="10%" align="center">金额<span id="total"></span></td>
						<td width="10%" align="center">成本</td>
						<td width="25%" align="center">已退数量</td>
						<td width="15%" align="center">退货数量</td>
					</tr>

					<c:forEach items="${bean.baseList}" var="fristBase" varStatus="vs">
                    <tr class="content2">
                        <td align="center"><input type="text" name="productName"
                            onclick="opens(this)" 
                            productid="${fristBase.productId}" 
                            productcode="" 
                            price="${my:formatNum(fristBase.costPrice)}"
                            stafferid="${fristBase.owner}"
                            depotpartid="${fristBase.depotpartId}"
                            readonly="readonly"
                            style="width: 100%; cursor: pointer"
                            value="${fristBase.productName}"></td>

                        <td align="center"><select name="unit" style="WIDTH: 50px;" values="${fristBase.unit}">
                            <option value="套">套</option>
                            <option value="枚">枚</option>
                            <option value="个">个</option>
                            <option value="本">本</option>
                        </select></td>

                        <td align="center"><input type="text" style="width: 100%"  value="${fristBase.amount}"
                            maxlength="6" onkeyup="cc(this)" name="amount"></td>

                        <td align="center"><input type="text" style="width: 100%"  value="${fristBase.price}"
                            maxlength="8" onkeyup="cc(this)" onblur="blu(this)" name="price"></td>

                        <td align="center"><input type="text" value="${fristBase.value}"
                            value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

                        <c:if test="${fristBase.costPrice==0}">
                        <td align="center"><input type="text" value="0.0" style="width: 100%" name="desciprt"></td>
                        </c:if>
                         
                        <c:if test="${fristBase.costPrice > 0}">
                        <td align="center"><input type="text"  readonly="readonly" value="${fristBase.description}"
                            style="width: 100%" name="desciprt"></td>                        
                        </c:if>    
                            
                        <td align="center">${fristBase.inway}</td>
                            
                       <td  align="center">
                        <input type="text" value="0" oncheck="isNumber;range(0, ${fristBase.amount - fristBase.inway})"
                            style="width: 100%" name="backUnm">
                            
                         <input type="hidden" name="baseItem" value="${fristBase.id}">
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
	
	<tr>
        <td colspan='2' align='center'>
        <div id="desc1" style="display: block;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="10%" align="center">销售退货</td>
                        
                        <td width="15%" align="center">时间</td>
                    </tr>

                    <c:forEach items="${refBuyList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center"><a href="../sail/out.do?method=findOut&fow=99&outId=${item.fullId}">${item.fullId}</a></td>

                            <td  align="center">${item.outTime}</td>

                        </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>
        </div>
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
        <div align="right"><input type="button" name="pr"
            class="button_class" onclick="outBack()"
            value="&nbsp;&nbsp;提交&nbsp;&nbsp;">&nbsp;&nbsp;<input
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


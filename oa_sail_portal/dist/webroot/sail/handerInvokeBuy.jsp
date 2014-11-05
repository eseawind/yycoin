<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="入库单明细" />
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
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
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script language="JavaScript" src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src='../js/adapter.js'></script>
<script language="javascript">

function load()
{
	loadForm();

	hides(true);
	
	$detail($O('viewTable'), ['are', 'oi', 're', 'ba', 'otherLocation']);
	
	highlights($("#mainTable").get(0), ['未付款'], 'red');
	
	highlights($("#mainTable").get(0), ['已付款'], 'blue');
	
	$('#dlg').dialog({
                modal:true,
                closed:true,
                buttons:{
                    '确 定':function(){
                        recivesReal();
                    },
                    '取 消':function(){
                        $('#dlg').dialog({closed:true});
                    }
                }
     });
     
     $ESC('dlg');
}

function hides(boo)
{
	
}

function showTr(boo)
{
	
}

function getOutId(id)
{
}

function check()
{
	return true;
}

function checkTotal()
{
	
}

function pagePrint()
{
    $O('na').style.display = 'none';
    $O('pr').style.display = 'none';
    $O('ba').style.display = 'none';
    $O('desc1').style.display = 'none';
    window.print();

    $O('pr').style.display = 'inline';
    $O('ba').style.display = 'inline';
    $O('na').style.display = 'block';
    $O('desc1').style.display = 'block';
}

function invokeOther()
{
    if ($$('otherLocation') == '${bean.location}')
    {
        alert('转调区域不能是产品调出区域');
        return false;
    }
    
    if ($$('otherLocation') == '')
    {
        alert('请选择转调的仓库');
        return false;
    }
    
    if ($$('otherLocation') == '${bean.destinationId}')
    {
        alert('调入仓库就是本仓库');
        return false;
    }
    
    if (window.confirm('确定把此调出库单转调至--' + getOptionText('otherLocation')))
    {
        document.location.href = '../sail/out.do?method=processInvoke&outId=${bean.fullId}&flag=2&changeLocationId=' + $$('otherLocation');
    }
}

function recivesReal()
{
    $l('../sail/out.do?method=processInvoke&outId=${bean.fullId}&flag=1&depotpartId=' + $$('depotpartId'));
}

function rejects()
{
    if (window.confirm('确定驳回此调出的库单?'))
    {
        document.location.href = '../sail/out.do?method=processInvoke&outId=${bean.fullId}&flag=3';
    }
}

function recives()
{
    $('#dlg').dialog({closed:false});
}

</script>
</head>
<body class="body_class" onload="load()">
<form name="outForm" method=post action="../sail/out.do">
<input
	type=hidden name="method" value="addOut" />
<input type=hidden name="nameList" /> 
<input type=hidden name="idsList" /> 
<input
	type=hidden name="unitList" /> 
	<input type=hidden name="amontList" />
<input type=hidden name="priceList" /> 
<input type=hidden
	name="totalList" /> 
<input type=hidden name="totalss" /> 
<input type=hidden name="customerId" value="${bean.customerId}"/> 
<input type=hidden name="type"
	value='0' /> 
<input type=hidden name="saves" value="" />
<input type=hidden name="desList" value="" />
<input type=hidden name="otherList" value="" />
<input type=hidden name="showIdList" value="" />
<input type=hidden name="showNameList" value="" />
<input type=hidden name="customercreditlevel" value="" />
<div id="na">
<p:navigation
	height="22">
	<td width="550" class="navigation">处理调拨</td>
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
								<td class="caption"><strong>填写入库单信息:</strong>
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
						<td width="15%" align="right">入库日期：</td>

						<td width="35%"><input type="text" name="outTime"
							value="${bean.outTime}" maxlength="20" size="20"
							readonly="readonly"><font color="#FF0000">*</font></td>

							<td width="15%" align="right">入库类型：</td>
							<td width="35%"><select name="outType" class="select_class" onchange="managerChange()" values="${bean.outType}" readonly=true>
								<p:option type="outType_in"></p:option>
							</select><font color="#FF0000">*</font></td>
						
					</tr>

					<tr class="content1">
						<td align="right" id="outd">供应商：</td>
						<td><input type="text" name="customerName" maxlength="14" value="${bean.customerName}" onclick="selectCustomer()"
							 style="cursor: pointer;"
							readonly="readonly"><font color="#FF0000">*</font></td>
						<td align="right">调拨方向：</td>
						<td><select name="department" class="select_class" values="${bean.reserve1}">
							<p:option type="moveOut"></p:option>
						</select><font color="#FF0000">*</font></td>
					</tr>
					
					<tr class="content2">
						<td align="right">经手人：</td>
						<td><input type="text" name="stafferName" maxlength="14"
							value="${user.stafferName}" readonly="readonly"></td>
						<td align="right">单据标识：</td>
						<td><input type="text" name="fullId" maxlength="40"
							value="${bean.fullId}" readonly="readonly"></td>
					</tr>

					
					<tr class="content1">
                        <td align="right">纳税实体：</td>
                        <td colspan="1">
                        <select name="dutyId" class="select_class" style="width: 240px" values="${bean.dutyId}">
                            <c:forEach items="${dutyList}" var="item">
                            <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                        <font color="#FF0000">*</font></td>
                         <td align="right">目的库：</td>
                        <td colspan="1">
                       <font color="blue"><b>${bean.destinationName}</b></font>
                       </td>
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
                    
                     <tr class="content2">
                    </tr>

					<tr class="content1">
						<td align="right">入库单备注：</td>
						<td colspan="3"><textarea rows="3" cols="55" oncheck="notNone;"
							name="description"><c:out value="${bean.description}"/></textarea>
							<font color="#FF0000">*</font>
							</td>
					</tr>
					
					 <tr class="content2">
                        <td align="right">总部核对：</td>
                        <td colspan="3">
                       ${bean.checks}
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
						<td width="10%" align="center">入库价</td>
						<td width="10%" align="center">金额<span id="total"></span></td>
						<td width="10%" align="center">成本</td>
						<td width="25%" align="center">类型</td>
						<td width="15%" align="center">开发票品名</td>
					</tr>

					<tr class="content1" id="trCopy" style="display: none;">
						<td>
						<input type="text" name="productName"
							onclick="opens(this)"
							productid="" 
							productcode="" 
							price=""
							stafferid=""
							depotpartid=""
							readonly="readonly"
							style="width: 100%; cursor: hand">
						</td>

						<td><select name="unit" style="WIDTH: 50px;">
							<option value="套">套</option>
							<option value="枚">枚</option>
							<option value="个">个</option>
							<option value="本">本</option>
						</select></td>

						<td align="center"><input type="text"
							style="width: 100%" maxlength="6" onkeyup="cc(this)"
							name="amount"></td>

						<td align="center"><input type="text"
							style="width: 100%" maxlength="8" onkeyup="cc(this)"
							onblur="blu(this)" name="price"></td>

						<td align="center"><input type="text"
							value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

						<td align="center"><input type="text" readonly="readonly"
							style="width: 100%" name="desciprt"></td>
							
						<td align="center"><input type="text" readonly="readonly"
							style="width: 100%" name="rstafferName"></td>
							
						<td  align="center">
						<select name="outProductName" style="WIDTH: 150px;" quick=true>
							<p:option type="123"></p:option>
						</select>
						</td>

						<td align="center"></td>
					</tr>

					<tr class="content2">
						<td align="center"><input type="text" name="productName" id="unProductName"
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

						<td align="center"><input type="text" style="width: 100%" id="unAmount" value="${fristBase.amount}"
							maxlength="6" onkeyup="cc(this)" name="amount"></td>

						<td align="center"><input type="text" style="width: 100%" id="unPrice" value="${fristBase.price}"
							maxlength="8" onkeyup="cc(this)" onblur="blu(this)" name="price"></td>

						<td align="center"><input type="text" value="${fristBase.value}"
							value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

						<td align="center"><input type="text" id="unDesciprt" readonly="readonly" value="${fristBase.description}"
							style="width: 100%" name="desciprt"></td>
							
						<td align="center"><input type="text" id="unRstafferName" readonly="readonly" value="${fristBase.depotpartName}-->${fristBase.ownerName}"
							style="width: 100%" name="rstafferName"></td>
							
						<td align="center">
						<select name="outProductName" style="WIDTH: 150px;" quick=true values="${fristBase.showId}">
							<p:option type="123"></p:option>
						</select>
						</td>

					</tr>
					
					<c:forEach items="${lastBaseList}" var="fristBase" varStatus="vs">
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

                        <td align="center"><input type="text"  readonly="readonly" value="${fristBase.description}"
                            style="width: 100%" name="desciprt"></td>
                            
                        <td align="center"><input type="text" readonly="readonly" value="${fristBase.depotpartName}-->${fristBase.ownerName}"
                            style="width: 100%" name="rstafferName"></td>
                            
                        <td align="center">
                        <select name="outProductName" style="WIDTH: 150px;" quick=true values="${fristBase.showId}">
                            <p:option type="123"></p:option>
                        </select>
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
		<td height="10" colspan='2'></td>
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

                            <td  align="center">${item.oprModeName}</td>

                            <td  align="center">${item.preStatusName}</td>

                             <td  align="center">${my:get('buyStatus', item.afterStatus)}</td>

                            <td  align="center">${item.description}</td>

                            <td  align="center">${item.logTime}</td>

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
    
    <tr id="locations">
        <td height="10" colspan='2' align="center">
              转调其他仓库
            <select name="otherLocation" class="select_class" style="width: 300px">
                <option value="">--</option>
            <c:forEach items='${dirLocationList}' var="item">
                <option value="${item.id}">${item.name}</option>
            </c:forEach>
        </select>
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
        <div align="right"><input type="button" name="are"
            class="button_class" onclick="recives()"
            value="&nbsp;&nbsp;全部接受&nbsp;&nbsp;">&nbsp;&nbsp;
            <input type="button" name="oi"
            class="button_class" onclick="invokeOther()"
            value="&nbsp;转调其他区域&nbsp;">&nbsp;&nbsp;
            <input type="button"  name="re"
            class="button_class" onclick="rejects()"
            value="&nbsp;&nbsp;全部驳回&nbsp;&nbsp;">&nbsp;&nbsp;<input
            type="button" name="ba" class="button_class"
            onclick="javascript:history.go(-1)"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
        </td>
        <td width="0%"></td>
    </tr>

</table>
<div id="dlg" title="选择到货的仓区" style="width:320px;">
    <div style="padding:20px;height:200px;" id="dia_inner1" title="">
    <c:forEach items="${depotpartList}" var="item" varStatus="vs">
     <input type="radio" name="depotpartId" value="${item.id}"  ${vs.index == 0 ? 'checked=checked' : '' }>${item.name}<br>
    </c:forEach>
   </div>
</div>
</form>
</body>
</html>


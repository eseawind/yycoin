<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="修改销售单" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/jquery/jquery.js"></script>
<script language="JavaScript" src="../sail_js/addOut.js"></script>
<script language="javascript">
<%@include file="../sail_js/out.jsp"%>

var duesMap = {};
var duesTypeMap = {};
<c:forEach items="${dutyList}" var="item">
duesMap['${item.id}'] = '${item.dues}';
duesTypeMap['${item.id}'] = '${item.mtype}';
</c:forEach>

/**
 * 查询库存
 */
function opens(obj)
{
    if ($$('location') == '')
    {
        alert('请选择仓库');
        
        return false;
    }
    
    if ($$('dutyId') == '')
    {
        alert('请选择纳税实体');
        
        return false;
    }
    
    var mtype = duesTypeMap[$$('dutyId')];
    
	oo = obj;
	
	window.common.modal('../depot/storage.do?method=rptQueryStorageRelationInDepot&showAbs=1&load=1&depotId='
	           + $$('location') + '&name=' + encodeURIComponent(obj.value)+ '&tmpdutyid=' + $("#dutyId").val());
}

function load()
{
    titleChange();
    
    loadForm();
    
     //load show
    loadShow();
    
    loadForm();
    
     //个人领样
    if ($$('outType') == 1)
    {
        $O('customerName').value = '个人领样';
        $O('customerId').value = '99';
        $O('customerName').disabled  = true;
        $O('reday').value = '${goDays}';
        $O('reday').readOnly = true;
    }
    
      //零售 是给公共客户的
    if ($$('outType') == 2)
    {
        $O('customerName').value = '公共客户';
        $O('customerId').value = '99';
        $O('customerName').disabled  = true;
        $O('reday').readOnly = false;
        
        //联系人和电话必填
        $O('connector').readOnly = false;
        $O('phone').readOnly = false;
        
        $O('connector').oncheck = 'notNone';
        $O('phone').oncheck = 'notNone';
        
        removeAllItem($O('reserve3'));
        
        setOption($O('reserve3'), '1', '款到发货(黑名单客户/零售)');   
    }
    
     //赠送
    if ($$('outType') == 4)
    {
        //价格为0
        var showArr = $("input[name='inputPrice']") ;
        
        for (var i = 0; i < showArr.length; i++)
        {
            var each = showArr[i];
            each.readOnly = true;
            each.value = 0.0;
        }
    }
    
    if ($$('reserve3') == 1)
    {
        removeAllItem($O('reserve3'));
        
        setOption($O('reserve3'), '1', '款到发货(黑名单客户/零售)');   
    }
}

function changePrice()
{
    var ssList = document.getElementsByName('inputPrice');
    
    for (var i = 0; i < ssList.length; i++)
    {
        if (ssList[i].value != '')
        {
	       ccs(ssList[i]);
	       total();
	    }
    }
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
<input type=hidden name="id" value="${bean.id}" />
<input type=hidden name="desList" value="" />
<input type=hidden name="otherList" value="" />
<input type=hidden name="showIdList" value="" />
<input type=hidden name="showNameList" value="" />
<input type=hidden name="customercreditlevel" value="" />
<input type=hidden name="refOutFullId" value="${bean.refOutFullId}" />
<input type=hidden name="inputPriceList"> 
<p:navigation
	height="22">
	<td width="550" class="navigation">库单管理 &gt;&gt; 修改销售单(如果需要增加开发票品名,请到 公共资源-->配置管理)</td>
				<td width="85"></td>
</p:navigation> <br>

<table width="95%" border="0" cellpadding="0" cellspacing="0"
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
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
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
						<td align="right">单据号码：</td>
						<td><input type="text" name="fullId" maxlength="40"
							value="${bean.fullId}" readonly="readonly"></td>
					</tr>

					<tr class="content2">
						<td align="right">回款天数：</td>
						<td colspan="1"><input type="text" name="reday" maxlength="4" oncheck="notNone;isInt;range(1, 180)"
							value="${bean.reday}" title="请填入1到180之内的数字"><font color="#FF0000">*</font></td>

						<td align="right">到货日期：</td>
						<td><p:plugin name="arriveDate"  size="20" oncheck="notNone;cnow('32')" value="${bean.arriveDate}"/><font color="#FF0000">*</font></td>
					</tr>
					
					<tr class="content2">
                        <td align="right">付款方式：</td>
                        <td colspan="1">
                        <select name="reserve3" class="select_class" oncheck="notNone;" head="付款方式" style="width: 240px" values="${bean.reserve3}">
                            <option value='3'>信用担保</option>
                            <option value='2'>客户信用和业务员信用额度担保</option>
                            <option value='1'>款到发货(黑名单客户/零售)</option>
                        </select>
                        <font color="#FF0000">*</font></td>
                        <td align="right">纳税实体：</td>
                        <td colspan="1">
                        <select name="dutyId" id="dutyId" class="select_class" style="width: 240px"  readonly=true
                            values="${bean.dutyId}" onchange="loadShow();changePrice();">
                            <c:forEach items="${dutyList}" var="item">
                            <option value="${item.id}">${item.name} </option>
                            </c:forEach>
                        </select>
                        <font color="#FF0000">*</font></td>
                    </tr>
                    
                    <tr class="content2">
                        <td align="right">发票类型：</td>
                        <td colspan="3">
                        <select name="invoiceId" class="select_class" head="发票类型" style="width: 400px" values="${bean.invoiceId}">
                           <option value="">没有发票</option>
                            <c:forEach items="${invoiceList}" var="item">
                            <option value="${item.id}">${item.fullName}</option>
                            </c:forEach>
                        </select>
                        <font color="#FF0000">*</font></td>
                    </tr>

					<tr class="content1">
						<td align="right">销售单备注：</td>
						<td colspan="3"><textarea rows="3" cols="55" oncheck="notNone;"
							name="description"><c:out value="${bean.description}"/></textarea>
							<font color="#FF0000">*</font>
							<b>(请填写所销售的产品,因为短信审批会发送此内容给您的主管)</b></td>
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
						<td width="15%" align="center">品名</td>
						<td width="5%" align="center">单位</td>
						<td width="5%" align="center">数量</td>
						<td width="10%" align="center">单价</td>
						<td width="10%" align="center">含税价</td>
						<td width="10%" align="left">金额<span id="total"></span></td>
						<td width="10%" align="center">成本</td>
						<td width="20%" align="center">类型</td>
						<td width="15%" align="center">开发票品名</td>
						<td width="15%" align="center">
						<c:if test="${!lock_sw}">
						<input type="button" accesskey="A"
							value="增加" class="button_class" onclick="addTr()">
							</c:if></td>
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
							producttype=""
                            productsailtype=""
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
                            style="width: 100%" maxlength="8" onkeyup="cc(this)" cost=""
                            onblur="blu(this)" name="inputPrice"></td>

						<td align="center"><input type="text" readonly="readonly"
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
						<td><input type="text" name="productName" id="unProductName"
							<c:if test="${!lock_sw}">
							onclick="opens(this)" 
							</c:if>
							productid="${fristBase.productId}" 
                            productcode="" 
                            price="${my:formatNum(fristBase.costPrice)}"
                            stafferid="${fristBase.owner}"
                            depotpartid="${fristBase.depotpartId}"
                            producttype="${bean.productType}"
                            productsailtype="${bean.sailType}"
							readonly="readonly"
							style="width: 100%; cursor: pointer"
							value="${fristBase.productName}"></td>

						<td><select name="unit" style="WIDTH: 50px;" values="${fristBase.unit}">
							<option value="套">套</option>
							<option value="枚">枚</option>
							<option value="个">个</option>
							<option value="本">本</option>
						</select></td>

						<td align="center"><input type="text" style="width: 100%" id="unAmount" value="${fristBase.amount}"
							maxlength="6" onkeyup="cc(this)" name="amount"></td>
							
						<td align="center"><input type="text" style="width: 100%" id="unInputPrice" value="${my:formatNum(fristBase.inputPrice)}"
                            cost="" maxlength="13" onkeyup="cc(this)" onblur="blu(this)" name="inputPrice"></td>

						<td align="center"><input type="text" style="width: 100%" readonly="readonly" id="unPrice" value="${my:formatNum(fristBase.price)}" 
							maxlength="11" onkeyup="cc(this)" onblur="blu(this)" name="price"></td>

						<td align="center"><input type="text" value="${my:formatNum(fristBase.value)}"
							value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

						<td align="center"><input type="text" id="unDesciprt" readonly="readonly" value="${my:formatNum(fristBase.costPrice)}"
							style="width: 100%" name="desciprt"></td>
							
						<td align="center"><input type="text" id="unRstafferName" readonly="readonly" value="${fristBase.depotpartName}-->${fristBase.ownerName}"
							style="width: 100%" name="rstafferName"></td>
							
						<td align="center">
						<select name="outProductName" style="WIDTH: 150px;" quick=true values="${fristBase.showId}">
						</select>
						</td>

						<td align="center"><input type=button value="清空"  class="button_class" onclick="clears()"></td>
					</tr>
					
					<c:forEach items="${lastBaseList}" var="fristBase" varStatus="vs">
                    <tr class="content2">
                        <td><input type="text" name="productName"
                            onclick="opens(this)" 
                            productid="${fristBase.productId}" 
                            productcode="" 
                            price="${my:formatNum(fristBase.costPrice)}"
                            stafferid="${fristBase.owner}"
                            depotpartid="${fristBase.depotpartId}"
                            producttype="${bean.productType}"
                            productsailtype="${bean.sailType}"
                            readonly="readonly"
                            style="width: 100%; cursor: pointer"
                            value="${fristBase.productName}"></td>

                        <td><select name="unit" style="WIDTH: 50px;" values="${fristBase.unit}">
                            <option value="套">套</option>
                            <option value="枚">枚</option>
                            <option value="个">个</option>
                            <option value="本">本</option>
                        </select></td>

                        <td align="center"><input type="text" style="width: 100%"  value="${fristBase.amount}"
                            maxlength="6" onkeyup="cc(this)" name="amount"></td>
                            
                        <td align="center"><input type="text" style="width: 100%" value="${my:formatNum(fristBase.inputPrice)}" cost=""
                            maxlength="13" onkeyup="cc(this)" onblur="blu(this)" name="inputPrice"></td>

                        <td align="center"><input type="text" style="width: 100%"  value="${my:formatNum(fristBase.price)}"
                            maxlength="11" onkeyup="cc(this)" onblur="blu(this)" name="price"></td>

                        <td align="center"><input type="text" value="${fristBase.value}"
                            value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

                        <td align="center"><input type="text"  readonly="readonly" value="${my:formatNum(fristBase.costPrice)}"
                            style="width: 100%" name="desciprt"></td>
                            
                        <td align="center"><input type="text" readonly="readonly" value="${fristBase.depotpartName}-->${fristBase.ownerName}"
                            style="width: 100%" name="rstafferName"></td>
                            
                        <td align="center">
                        <select name="outProductName" style="WIDTH: 150px;" quick=true values="${fristBase.showId}">
                        </select>
                        </td>

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
		<div align="right"><input type="button" class="button_class"
			value="&nbsp;&nbsp;保 存&nbsp;&nbsp;" onClick="save()" />&nbsp;&nbsp;</div>
		</td>
		<td width="0%"></td>
	</tr>

</table>
</form>
</body>
</html>


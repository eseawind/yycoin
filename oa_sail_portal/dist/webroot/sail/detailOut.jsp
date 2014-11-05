<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="销售单明细" guid="true" dialog="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../sail_js/addOut.js"></script>
<script language="JavaScript" src="../js/plugin/highlight/jquery.highlight.js"></script>
<script language="javascript">

<%@include file="../sail_js/out.jsp"%>

function load()
{
    titleChange();
    
    loadForm();
    
    //loadShow(true);
    
    loadForm();

    hides(true);
    
    //$detail($O('viewTable'), ['pr', 'ba', 'pr2']);
    $detail($O('viewTable'), ['ba', 'pr2']);
    
    highlights($("#mainTable").get(0), ['未付款'], 'red');
    
    highlights($("#mainTable").get(0), ['已付款'], 'blue');
}

function hides(boo)
{
    
}


function pagePrint()
{
    $O('na').style.display = 'none';
    //$O('pr').style.display = 'none';
    $O('pr2').style.display = 'none';
    $O('ba').style.display = 'none';
    $O('desc1').style.display = 'none';
    window.print();

    //$O('pr').style.display = 'inline';
    $O('pr2').style.display = 'inline';
    $O('ba').style.display = 'inline';
    $O('na').style.display = 'block';
    $O('desc1').style.display = 'block';
}

function pagePrint2()
{
    $O('na').style.display = 'none';
    //$O('pr').style.display = 'none';
    $O('pr2').style.display = 'none';
    $O('ba').style.display = 'none';
    $O('desc1').style.display = 'none';
    $O('cost_td').style.display = 'none';
    $O('pduty').style.display = 'none';
    $O('pdesc').style.display = 'none';
    $O('ppz').style.display = 'none';
    hiddenCost('none');
    window.print();

    //$O('pr').style.display = 'inline';
    $O('pr2').style.display = 'inline';
    $O('ba').style.display = 'inline';
    $O('na').style.display = 'block';
    $O('desc1').style.display = 'block';
    $O('cost_td').style.display = 'block';
    $O('pduty').style.display = 'block';
    $O('pdesc').style.display = 'block';
    $O('ppz').style.display = 'block';
    hiddenCost('block');
}

function hiddenCost(str)
{
	//var costList = document.getElementsByName('desciprt');
	
	for (var i = 0; i < 30; i++)
	{
		if ($O('sub_td_' + i))
		{
			$O('sub_td_' + i).style.display = str;
		}

		if ($O('subp_td_' + i))
		{
			$O('subp_td_' + i).style.display = str;
		}
	}
}

function checkBean()
{
    $.messager.prompt('总部核对', '请核对说明', '', function(msg){
                if (msg)
                {
                    $l('../sail/out.do?method=checks&outId=${bean.fullId}&formDetail=1&reason=' + ajaxPararmter(msg) + '&queryType=6');
                }
               
            }, 2);
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
	<td width="550" class="navigation">销售单明细</td>
				<td width="85"></td>
</p:navigation> <br>
</div>

<table width="98%" border="0" cellpadding="0" cellspacing="0" id="viewTable"
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
					
					<tr id="presentTR" class="content1" >
						<td align="right" id="outd">赠送类型：</td>
						<td colspan="3"><select name="presentFlag" class="select_class" values="${bean.presentFlag}">
								<p:option type="presentFlag" empty="true"></p:option>
							</select></td>
					</tr>

					<tr class="content1">
						<td align="right" id="outd">客户：</td>
						<td><input type="text" name="customerName" maxlength="14" value="${bean.customerName}" onclick="selectCustomer()"
							 style="cursor: pointer;"
							readonly="readonly"><font color="#FF0000">*</font></td>
						<td align="right">活动号：</td>
						<td><input type="text" name="eventId" maxlength="30"
							value="${bean.eventId}" readonly="readonly"></td>
					</tr>
					
					<!--  
					<tr class="content2">
						<td align="right">联系人：</td>
						<td><input type="text" name="connector" maxlength="14" value="${bean.connector}"
							readonly="readonly"></td>
						<td align="right">联系电话：</td>
						<td><input type="text" name="phone" maxlength="20" readonly="readonly" value="${bean.phone}"></td>
					</tr>
					-->
					
					<tr class="content1">
						<td align="right">经办人：</td>
						<td><input type="text" name="operatorName" maxlength="14"
							value="${bean.operatorName}" readonly="readonly"></td>
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
                        <td align="right">结算中心时间：</td>
                        <td colspan="1">${bean.managerTime}</td>

                        <td align="right">库管通过时间：</td>
                        <td>${bean.changeTime}</td>
                    </tr>
					
					<tr class="content2" id="pduty">
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
                    
                    <tr class="content1">
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
                    
                    <tr class="content2">
                        <td align="right">已支付：</td>
                        <td colspan="1">
                        ${my:formatNum(bean.hadPay)}
                        </td>
                        <td align="right">坏账金额：</td>
                        <td colspan="1">
                        ${my:formatNum(bean.badDebts)}
                        </td>
                    </tr>
                    
                    <tr class="content1">
                        <td align="right">开票金额：</td>
                        <td colspan="1">
                        ${my:formatNum(bean.invoiceMoney)}
                        </td>
                        <td align="right">开票状态：</td>
                        <td colspan="1">
                        ${my:get('invoiceStatus', bean.invoiceStatus)}
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

                     <tr class="content2">
                       <td align="right">管理类型：</td>
                        <td colspan="1">
                       ${my:get('pubManagerType', bean.mtype)}
                       </td>                     
                       <td align="right">付款状态：</td>
                        <td colspan="1">
                       ${my:get('outPay', bean.pay)}
                       </td>
                    </tr>
                    
                    <tr class="content2">
                        <td align="right">关联单据：</td>
                        <td colspan="1">
                       <a href="../sail/out.do?method=findOut&fow=99&outId=${bean.refOutFullId}">${bean.refOutFullId}</a>
                       </td>
                       <td align="right">事业部：</td>
                        <td colspan="1">
                      ${shiye.name}
                       </td>
                    </tr>
                    
                    <tr class="content1">
                        <td align="right">关注类型：</td>
                        <td>
                        ${my:get('outVtype', bean.vtype)}
                       </td>
                       <c:if test="${proBean.rebateType==2 }">
                       <td align="right">客户信用分/业务员信用金额：</td>
                       	<td colspan="1">
                        ${my:formatNum(proBean.custCredit)}/${my:formatNum(proBean.busiCredit)}
                        </td>
                       </c:if>
                       <c:if test="${proBean.rebateType!=2 }">
                       <td align="right">折扣金额：</td>
                       	<td colspan="1">
                        ${my:formatNum(bean.promValue)}
                        </td>
                       </c:if>
                       	
                    </tr>
                    
                     <tr class="content1">
                        <td align="right">管理描述：</td>
                        <td colspan="1">
                       销售类型:${my:get2('productSailType', bean.sailType)}/销售类别:${my:get2('productType', bean.productType)}
                       </td>
                        <td align="right">税额：</td>
                        <td colspan="1">
                       ${my:formatNum(bean.taxTotal)}
                       </td>
                    </tr>
                        
                    <tr class="content2">
                        <td align="right">票款一致描述：</td>
                        <td colspan="1">
                       ${bean.pdescription}
                       </td>
                        <td align="right">票款一致纳税实体：</td>
                        <td colspan="1">
                        <c:if test="${bean.piDutyId == 'COMMON' || bean.piDutyId == 'MANAGE'}">
                        	不限
                        </c:if>
                        <c:if test="${bean.piDutyId != 'COMMON' && bean.piDutyId != 'MANAGE'}">
                        <select name="piDutyId" class="select_class" style="width: 240px" values="${bean.piDutyId}">
	                            <option value="">--</option>
	                            <c:forEach items="${dutyList}" var="item">
	                            <option value="${item.id}">${item.name}</option>
	                            </c:forEach>
	                        </select>
                        </c:if>
                       </td>
                    </tr>
                        
                    <tr class="content1">
                        <td align="right">信用描述：</td>
                        <td colspan="3">
                        <font color="red">
                       ${bean.reserve6}
                       </font>
                       </td>
                    </tr>
                    
                    <tr class="content2">
                        <td align="right">信用担保人：</td>
                        <td colspan="3">
                        <font color="red">
                       ${bean.guarantorName}
                       </font>
                       </td>
                    </tr>
                    
                     <tr class="content1">
                        <td align="right">信用担保：</td>
                        <td colspan="3">
                       客户:${my:formatNum(bean.curcredit)}/${bean.stafferName}:${my:formatNum(bean.staffcredit)}/事业部经理:${my:formatNum(bean.managercredit)}
                       </td>
                    </tr>

					<tr class="content2" id="pdesc">
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
                        <td align="right">付款备注：</td>
                        <td colspan="3">
                       ${checkOutPayStatus.message}
                       </td>
                    </tr>
                    
                    <tr class="content1">
                        <td align="right">配送单号：</td>
                        <td colspan="3">
                       		 <c:forEach items="${distBeanList}" var="item">
		                       <a href="../sail/out.do?method=findDistribution&id=${item.id}">${item.id}</a>
		                       &nbsp;
		                     </c:forEach>
                       </td>
                    </tr>
                    
                     <tr class="content2" id="ppz">
                        <td align="right">关联凭证：</td>
                        <td colspan="3">
                       <c:forEach items="${financeBeanList}" var="item">
                       <a href="../finance/finance.do?method=findFinance&id=${item.id}&backType=0&backId=${bean.fullId}">${item.id}</a>
                       &nbsp;
                       </c:forEach>
                       </td>
                    </tr>

					<tr class="content1">
                        <td align="right">附件：</td>
                        <td colspan="3">
			            <c:forEach items="${bean.attachmentList}" var="item">
			            <a href="../sail/out.do?method=downAttachmentFile&id=${item.id}" title="点击下载附件">${item.name}</a>
			            <br>
			            <br>
			            </c:forEach>
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
						<td width="15%" align="center">品名</td>
						<td width="5%" align="center">单位</td>
						<td width="5%" align="center">数量</td>
						<td width="10%" align="center">销售价</td>
						<td width="10%" align="center">金额<span id="total"></span></td>
						<td width="10%" align="center" id="cost_td">成本/事业部/总部</td>
						<td width="20%" align="center">类型</td>
						<td width="5%" align="center">税率</td>
						<td width="5%" align="center">税额</td>
						<td width="5%" align="center">毛利率</td>
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
							<option value="批">批</option>
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
						
						<td align="center"><input type="text" readonly="readonly" value="0.00%"
							style="width: 100%" name="taxrate"></td>
							
						<td align="center"><input type="text" readonly="readonly" value="0.00"
							style="width: 100%" name="tax"></td>							
							
						<td align="center"><input type="text" readonly="readonly" value="0.00%"
							style="width: 100%" name="profitRatio"></td>

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
							<option value="批">批</option>
						</select></td>

						<td align="center"><input type="text" style="width: 100%" id="unAmount" value="${fristBase.amount}"
							maxlength="6" onkeyup="cc(this)" name="amount"></td>
						
						<td align="center"><input type="text" style="width: 100%" id="unPrice" value="${my:formatNum(fristBase.price)}"
							maxlength="8" onkeyup="cc(this)" onblur="blu(this)" name="price"></td>

						<td align="center"><input type="text" value="${my:formatNum(fristBase.value)}"
							value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

						<td align="center" id="sub_td_0"><input type="text" id="unDesciprt" readonly="readonly" value="${fristBase.description}"
							style="width: 100%" name="desciprt"></td>
							
						<td align="center"><input type="text" id="unRstafferName" readonly="readonly" value="${fristBase.depotpartName}-->${fristBase.ownerName}"
							style="width: 100%" name="rstafferName"></td>
							
						<td align="center">
						<input type="text" readonly="readonly" value="${my:formatNum(fristBase.taxrate * 100)}%"
							style="width: 100%" name="taxrate">
						</td>							
							
						<td align="center">
						<input type="text" readonly="readonly" value="${my:formatNum(fristBase.tax)}"
							style="width: 100%" name="tax">
						</td>							
							
						<td align="center" id="subp_td_0">
						<input type="text" readonly="readonly" value="${my:formatNum(fristBase.profitRatio * 100)}%"
							style="width: 100%" name="profitRatio">
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
                            <option value="批">批</option>
                        </select></td>

                        <td align="center"><input type="text" style="width: 100%"  value="${fristBase.amount}"
                            maxlength="6" onkeyup="cc(this)" name="amount"></td>
                            
                        <td align="center"><input type="text" style="width: 100%"  value="${my:formatNum(fristBase.price)}"
                            maxlength="8" onkeyup="cc(this)" onblur="blu(this)" name="price"></td>

                        <td align="center"><input type="text" value="${my:formatNum(fristBase.value)}"
                            value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

                        <td align="center" id="sub_td_${vs.index + 1 }"><input type="text"  readonly="readonly" value="${fristBase.description}"
                            style="width: 100%" name="desciprt"></td>
                            
                        <td align="center"><input type="text" readonly="readonly" value="${fristBase.depotpartName}-->${fristBase.ownerName}"
                            style="width: 100%" name="rstafferName"></td>
                            
						<td align="center">
						<input type="text" readonly="readonly" value="${my:formatNum(fristBase.taxrate * 100)}%"
							style="width: 100%" name="taxrate">
						</td>
							
						<td align="center">
						<input type="text" readonly="readonly" value="${my:formatNum(fristBase.tax)}"
							style="width: 100%" name="tax">
						</td>	                            
                            
                        <td align="center" id="subp_td_${vs.index + 1 }">
						<input type="text" readonly="readonly" value="${my:formatNum(fristBase.profitRatio * 100)}%"
							style="width: 100%" name="profitRatio">
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
                        <td width="10%" align="center">收付款单</td>
                        <td width="10%" align="center">类型</td>
                        <td width="10%" align="center">帐户</td>
                        <td width="10%" align="center">金额</td>
                        <td width="15%" align="center">时间</td>
                    </tr>

                    <c:forEach items="${billList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center"><a href="../finance/bill.do?method=findInBill&id=${item.id}">${item.id}</a></td>

                            <td  align="center">收款单</td>
                            <td  align="center">${item.bankName}</td>

                            <td  align="center">${my:formatNum(item.moneys)}</td>

                            <td  align="center">${item.logTime}</td>

                        </tr>
                    </c:forEach>
                    
                    <c:forEach items="${outBillList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center"><a href="../finance/bill.do?method=findOutBill&id=${item.id}">${item.id}</a></td>
                             <td  align="center">付款单</td>
                            <td  align="center">${item.bankName}</td>

                            <td  align="center">${my:formatNum(item.moneys)}</td>

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
    
    <c:if test="${bean.type == 0 && bean.outType == 3}">
    
    <tr>
        <td height="10" colspan='2'></td>
    </tr>

    <tr>
        <td background="../images/dot_line.gif" colspan='2'></td>
    </tr>
    
    <tr>
        <td colspan='2' align='center'>
        <div id="desc2" style="display: block;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="10%" align="center">委托结算类型</td>
                        <td width="10%" align="center">金额</td>
                        <td width="15%" align="center">时间</td>
                    </tr>

                    <c:forEach items="${balanceList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td  align="center"><a href="../sail/out.do?method=findOutBalance&id=${item.id}">${my:get('outBalanceType', item.type)}</a></td>

                            <td  align="center">${my:formatNum(item.total)}</td>

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
    
    </c:if>
    
    <c:if test="${my:length(refBuyList) > 0}">
    
    <tr>
        <td height="10" colspan='2'></td>
    </tr>

    <tr>
        <td background="../images/dot_line.gif" colspan='2'></td>
    </tr>
    
    <tr>
        <td colspan='2' align='center'>
        <div id="desc3" style="display: block;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="10%" align="center">退货</td>
                        <td width="10%" align="center">金额</td>
                        <td width="15%" align="center">时间</td>
                    </tr>

                    <c:forEach items="${refBuyList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center"><a href="../sail/out.do?method=findOut&fow=99&outId=${item.fullId}">${item.fullId}</a></td>

                            <td  align="center">${my:formatNum(item.total)}</td>
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
    
    </c:if>
    
    <c:if test="${my:length(refOutList) > 0}">
    
    <tr>
        <td height="10" colspan='2'></td>
    </tr>

    <tr>
        <td background="../images/dot_line.gif" colspan='2'></td>
    </tr>
    
    <tr>
        <td colspan='2' align='center'>
        <div id="desc4" style="display: block;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="10%" align="center">领样转销售</td>
                        <td width="10%" align="center">金额</td>
                        <td width="15%" align="center">时间</td>
                    </tr>

                    <c:forEach items="${refOutList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center"><a href="../sail/out.do?method=findOut&fow=99&outId=${item.fullId}">${item.fullId}</a></td>
                            <td align="center">${my:formatNum(item.total)}</td>
                            <td align="center">${item.outTime}</td>

                        </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>
        </div>
        </td>
    </tr>
    
    </c:if>
    
    <c:if test="${my:length(refOutToBuyList) > 0}">
    
    <tr>
        <td height="10" colspan='2'></td>
    </tr>

    <tr>
        <td background="../images/dot_line.gif" colspan='2'></td>
    </tr>
    
    <tr>
        <td colspan='2' align='center'>
        <div id="desc4" style="display: block;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="10%" align="center">转销售对冲单</td>
                        <td width="10%" align="center">金额</td>
                        <td width="15%" align="center">时间</td>
                    </tr>

                    <c:forEach items="${refOutToBuyList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center"><a href="../sail/out.do?method=findOut&fow=99&outId=${item.fullId}">${item.fullId}</a></td>
                            <td align="center">${my:formatNum(item.total)}</td>
                            <td align="center">${item.outTime}</td>

                        </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>
        </div>
        </td>
    </tr>
    
    </c:if>
    
    <c:if test="${my:length(insList) > 0 || my:length(preInsList) > 0}">
    
    <tr>
        <td height="10" colspan='2'></td>
    </tr>

    <tr>
        <td background="../images/dot_line.gif" colspan='2'></td>
    </tr>
    
    <tr>
        <td colspan='2' align='center'>
        <div id="desc4" style="display: block;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="10%" align="center">开票</td>
                        <td width="10%" align="center">金额</td>
                    </tr>

                    <c:forEach items="${insList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center"><a href="../finance/invoiceins.do?method=findInvoiceins&id=${item.insId}">${item.insId}</a></td>
                            <td align="center">${my:formatNum(item.moneys)}</td>
                        </tr>
                    </c:forEach>
                    <c:forEach items="${preInsList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center"><a href="../tcp/preinvoice.do?method=findPreInvoice&id=${item.parentId}">${item.parentId}</a></td>
                            <td align="center">${my:formatNum(item.invoiceMoney)}</td>
                        </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>
        </div>
        </td>
    </tr>
    
    </c:if>

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

                            <td  align="center">${item.afterStatusName}</td>

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

    <tr>
        <td background="../images/dot_line.gif" colspan='2'></td>
    </tr>

    <tr>
        <td height="10" colspan='2'></td>
    </tr>

	<tr>
        <td width="100%">
        <div align="right">
         <c:if test="${check == 1}">
        <input
            type="button" name="ba" class="button_class"
            onclick="checkBean()"
            value="&nbsp;&nbsp;总部核对&nbsp;&nbsp;">&nbsp;&nbsp;
        </c:if>
        
        <!--<input type="button" name="pr"
            class="button_class" onclick="pagePrint()"
            value="&nbsp;&nbsp;打 印&nbsp;&nbsp;">&nbsp;&nbsp;
            -->
            <input type="button" name="pr2"
            class="button_class" onclick="pagePrint2()"
            value="&nbsp;&nbsp;打印(无成本)&nbsp;&nbsp;">&nbsp;&nbsp;
            <input
            type="button" name="ba" class="button_class"
            onclick="javascript:history.go(-${goback})"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
        </td>
        <td width="0%"></td>
    </tr>

</table>
</form>
</body>
</html>


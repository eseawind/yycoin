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
<script language="JavaScript" src="../js/pop.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/jquery/jquery.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../sail_js/addOut5.js"></script>
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
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return false;
	}
	
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

	window.common.modal('../depot/storage.do?method=rptQueryStorageRelationInDepot&sailLocation=${g_staffer.industryId}&load=1&depotId='
	                    + $$('location') + '&code=' + obj.productcode + '&mtype=' + mtype + '&init=1');
}

function load()
{	
    titleChange();

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
        var showArr = $("input[name='price']") ;
        
        for (var i = 0; i < showArr.length; i++)
        {
            var each = showArr[i];
            each.readOnly = true;
            each.value = 0.0;
        }
        
        $v('presentTR', true);
    }
    
    if ($$('reserve3') == 1)
    {
        removeAllItem($O('reserve3'));
        
        setOption($O('reserve3'), '1', '款到发货(黑名单客户/零售)');   
    }

    loadInvoice();

    var proNames = document.getElementsByName('productName');

    var desciprtList = document.getElementsByName('desciprt');

    for (var i = 1; i < proNames.length; i++)
    {
    	if (proNames[i].productid == '9775852' || proNames[i].productid == '9865735')
    	{
    		desciprtList[i].readOnly=false;
    	}
    }
    
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

function loadInvoice()
{
	var vsjson = vsJSON;
	
	var dutyObj = $O('dutyId');
	
	var invObj = $O('invoiceId');

	removeAllItem(invObj);
	
	if (invMap[dutyObj.value] == '3')
	{
		setOption(invObj, '', '没有发票');
	}
	
	for (var i = 0; i < vsjson.length; i++)
	{
		var item = vsjson[i];
		
		if (item.dutyType == invMap[dutyObj.value])
        {
            setOption(invObj, item.invoiceId, invFullMap[item.invoiceId]);
        }
	}
}

</script>
</head>
<body class="body_class" onload="load()">
<form name="outForm" method=post action="../sail/out.do">
<input type=hidden name="method" value="addOut" />
<input type=hidden name="update" value="1" />
<input type=hidden name="nameList" /> 
<input type=hidden name="idsList" /> 
<input type=hidden name="amontList" />

<input type=hidden name="totalList" /> 
<input type=hidden name="totalss" /> 
<input type=hidden name="customerId" value="${bean.customerId}"/> 
<input type=hidden name="type" value='0' /> 
<input type=hidden name="saves" value="" />
<input type=hidden name="id" value="${bean.id}" />
<input type=hidden name="desList" value="" />
<input type=hidden name="showCostList" value="" />
<input type=hidden name="otherList" value="" />
<input type=hidden name="depotList" value="" />
<input type=hidden name="mtypeList" value="" />
<input type=hidden name="oldGoodsList" value="" />
<input type=hidden name="taxList" value="" />
<input type=hidden name="taxrateList" value="" />
<input type=hidden name="inputRateList" value="" />

<input type=hidden name="customercreditlevel" value="" />
<input type=hidden name="priceList"> 
<input type=hidden name="inputPriceList">
<input type=hidden name="refOutFullId" value="${bean.refOutFullId}" />
<input type=hidden name="mtype" value="${bean.mtype}" />
<input type=hidden name="hasProm" value="${hasProm}" />
<input type=hidden name="step" value="1" />

<input type=hidden name="locationShadow" value="${bean.location}">

<!-- 单一开单 -->
<input type=hidden name="oprType" value="1">

<p:navigation
	height="22">
	<td width="550" class="navigation">库单管理 &gt;&gt; 填写销售单0</td>
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
								<td class="caption"><strong>填写销售单信息:<font color=red>${hasOver}</font>您信用还剩:${credit}</strong>
								<font color="blue">产品仓库：</font>								
								<c:if test="${lock_sw}">
								<select name="location" class="select_class"  onchange="clearsAll()" values="${bean.location}"  readonly=true>
									<c:forEach items='${locationList}' var="item">
										<option value="${item.id}">${item.name}</option>
									</c:forEach>
								</select>
								</c:if>
								<c:if test="${!lock_sw}">
								  <select name="location" class="select_class"  onchange="clearsAll()" values="${bean.location}" >
									<c:forEach items='${locationList}' var="item">
										<option value="${item.id}">${item.name}</option>
									</c:forEach>
								  </select>
								</c:if>
								信用担保：
								<select name="guarantor" style="width: 300px" values="${bean.guarantor}">
                                    <c:forEach items='${mList}' var="item">
                                        <option value="${item.id}">${item.description}</option>
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
							<td width="35%"><select name="outType" class="select_class" onchange="managerChange()" values="${bean.outType}">
								<p:option type="outType_out"></p:option>
							</select><font color="#FF0000">*</font></td>
						
					</tr>
					
					<tr id="presentTR" class="content1" style="display: none;">
						<td align="right" id="outd">赠送类型：</td>
						<td colspan="3"><select name="presentFlag" class="select_class" values="${bean.presentFlag}">
								<p:option type="presentFlag" empty="true"></p:option>
							</select></td>
					</tr>

					<tr class="content1">
						<td align="right" id="outd">客户：</td>
						<td colspan="3"><input type="text" name="customerName" maxlength="14" value="${bean.customerName}" onclick="selectCustomer()"
							 style="cursor: pointer;"
							readonly="readonly"><font color="#FF0000">*</font></td>
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

                        <td align="right">付款方式：</td>
                        <td colspan="1">
                        <select name="reserve3" class="select_class" oncheck="notNone;" head="付款方式" style="width: 240px" values="${bean.reserve3}">
                            <option value='3'>信用担保</option>
                            <option value='2'>客户信用和业务员信用额度担保</option>
                            <option value='1'>款到发货(黑名单客户/零售)</option>
                        </select>
                        <font color="#FF0000">*</font></td>
					</tr>
					
					<tr class="content1">
						<td align="right">促销活动：</td>
						<td><input type="text" name="eventName" value="${bean.eventName}" style="width: 350px"
							value="" readonly="readonly"></td>
						<td align="right">折扣金额：</td>
						<td><input type="text" name="promValue" maxlength="20"
							value="${bean.promValue}" readonly="readonly"></td>
					</tr>

					<tr class="content1">
						<td align="right">销售单备注：</td>
						<td colspan="3"><textarea rows="3" cols="55"
							name="description"><c:out value="${bean.description}"/></textarea>
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
						<td width="30%" align="center">品名</td>						
						<td width="5%" align="center">数量</td>
						<td width="10%" align="center">单价</td>						
						<td width="10%" align="left">金额<span id="total"></span></td>
						<td width="10%" align="center">成本</td>
						<td width="5%" align="center">税率</td>
						<td width="5%" align="center">税额</td>
						<td width="10%" align="center">毛利</td>
						<td width="10%" align="center">毛利率</td>
						<td width="5%" align="center">
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
                            oldgoods=""
                            mtype=""
                            inputrate=""
							readonly="readonly"
							style="width: 100%; cursor: hand">
						</td>

						<td align="center"><input type="text"
							style="width: 100%" maxlength="6" onkeyup="cc(this)"
							name="amount"></td>

						<td align="center"><input type="text"
							style="width: 100%" maxlength="13" onkeyup="cc(this)" cost=""
							onblur="blu(this)" name="price"></td>

						<td align="center"><input type="text"
							value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

						<td align="center"><input type="text" readonly="readonly" onkeyup="cc(this)"
							style="width: 100%" name="desciprt"><input type="hidden" name="costPrice" value="${my:formatNum(fristBase.costPrice)}"></td>
							
						<td align="center">
							<input type="text"
								value="0.00" readonly="readonly" style="width: 100%" name="taxrate">	
						</td>
							
						<td align="center"><input type="text" readonly="readonly" value="0.00"
							style="width: 100%" name="tax"></td>							
							
						<td align="center"><input type="text" readonly="readonly" value="0.00"
							style="width: 100%" name="profit"></td>
							
						<td align="center"><input type="text" readonly="readonly" value="0.00%"
							style="width: 100%" name="profitRatio"></td>
							
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
                            producttype="${fristBase.productType}"
                            productsailtype="${bean.sailType}"
                            oldgoods="${fristBase.oldGoods}"
                            mtype="${fristBase.mtype}"
                            inputrate="${fristBase.inputRate}"
							readonly="readonly"
							style="width: 100%; cursor: pointer"
							value="${fristBase.productName}"></td>

						<td align="center"><input type="text" style="width: 100%" id="unAmount" value="${fristBase.amount}"
							maxlength="6" onkeyup="cc(this)" name="amount"></td>

						<td align="center"><input type="text" style="width: 100%" id="unPrice" value="${my:formatNum(fristBase.price)}" 
							maxlength="11" onkeyup="cc(this)" onblur="blu(this)" name="price"></td>

						<td align="center"><input type="text" value="${my:formatNum(fristBase.value)}"
							value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

						<td align="center"><input type="text" id="unDesciprt" readonly="readonly" value="${my:formatNum(fristBase.inputPrice)}" onkeyup="cc(this)"
							style="width: 100%" name="desciprt"><input type="hidden" id="unCostPrice" name="costPrice" value="${my:formatNum(fristBase.costPrice)}"></td>
							
						<td align="center">
							<input type="text" id="unTaxrate" value="${my:formatNum(fristBase.taxrate)}"
								readonly="readonly" style="width: 100%" name="taxrate">	
						</td>
							
						<td align="center"><input type="text" readonly="readonly" id="unTax" value="${my:formatNum(fristBase.tax)}"
							style="width: 100%" name="tax"></td>							
							
						<td align="center"><input type="text" readonly="readonly" id="unProfit" value="${my:formatNum(fristBase.profit)}"
							style="width: 100%" name="profit"></td>
							
						<td align="center"><input type="text" readonly="readonly" id="unProfitRatio" value="${my:formatNum(fristBase.profitRatio * 100)}%"
							style="width: 100%" name="profitRatio"></td>
							
						<td align="center"><input type=button value="删除" name=eachDel class=button_class onclick="removeTr(this)"></td>
						
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
                            producttype="${fristBase.productType}"
                            productsailtype="${bean.sailType}"
                            oldgoods="${fristBase.oldGoods}"
                            mtype="${fristBase.mtype}"
                            inputrate="${fristBase.inputRate}"
                            readonly="readonly"
                            style="width: 100%; cursor: pointer"
                            value="${fristBase.productName}"></td>

                        <td align="center"><input type="text" style="width: 100%"  value="${fristBase.amount}"
                            maxlength="6" onkeyup="cc(this)" name="amount"></td>                           

                        <td align="center"><input type="text" style="width: 100%"  value="${my:formatNum(fristBase.price)}"
                            maxlength="11" onkeyup="cc(this)" onblur="blu(this)" name="price"></td>

                        <td align="center"><input type="text" value="${my:formatNum(fristBase.value)}"
                            value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

                        <td align="center"><input type="text"  readonly="readonly" value="${my:formatNum(fristBase.inputPrice)}" onkeyup="cc(this)"
                            style="width: 100%" name="desciprt"><input type="hidden" name="costPrice" value="${my:formatNum(fristBase.costPrice)}"></td>
                            
						<td align="center">
							<input type="text" id="unTaxrate" value="${my:formatNum(fristBase.taxrate)}"
								readonly="readonly" style="width: 100%" name="taxrate">		
						</td>
							
						<td align="center"><input type="text" readonly="readonly" id="unTax" value="${my:formatNum(fristBase.tax)}"
							style="width: 100%" name="tax"></td>                            
                            
						<td align="center"><input type="text" readonly="readonly" id="unProfit" value="${my:formatNum(fristBase.profit)}"
							style="width: 100%" name="profit"></td>
							
						<td align="center"><input type="text" readonly="readonly" id="unProfitRatio" value="${my:formatNum(fristBase.profitRatio * 100)}%"
							style="width: 100%" name="profitRatio"></td>
							
                        <td align="center"><input type=button value="删除" name=eachDel class=button_class onclick="removeTr(this)"></td>
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
			value="去完成配送信息" onClick="save()" />&nbsp;&nbsp;
			</div>
		</td>
		<td width="0%"></td>
	</tr>

</table>
</form>
<p:message></p:message>
</body>
</html>


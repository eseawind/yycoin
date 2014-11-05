<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="填写销售单(now)" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
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
    // 配件
    window.common.modal('../depot/storage.do?method=rptQueryStorageRelationInDepot&sailLocation=${g_staffer.industryId}&load=1&depotId='
	                    + $$('location') + '&code=' + obj.productcode + '&mtype=' + mtype 
	                    + '&init=1');
}

function load()
{	
    blackForbid();
    
    titleChange();
    
    loadForm();
    
     //load show
    //loadShow();
    
    //loadForm();
    
    managerChange();


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

function blackForbid()
{
	
	var black = "${black}";

	if (black > '')
	{
		alert(black);

		document.location.href = '../admin/welcome.jsp';
		return false;
	}

	return true;
}

// 产品性质改变
function natureChange()
{
	delAllItem();
    
    total();
    
}

</script>
</head>
<body class="body_class" onload="load()">
<form name="outForm" method="post" action="../sail/out.do?method=addOut">
<input type=hidden name="update" value="0" />
<input type=hidden name="nameList" /> 
<input type=hidden name="idsList" /> 
<input type=hidden name="amontList" />

<input type=hidden name="totalList" /> 
<input type=hidden name="totalss" /> 
<input type=hidden name="customerId" /> 
<input type=hidden name="type" value='0' /> 
<input type=hidden name="saves" value="" />
<input type=hidden name="desList" value="" />
<input type=hidden name="showCostList" value="" />
<input type=hidden name="otherList" value="" />

<input type=hidden name="customercreditlevel" value="" />
<input type=hidden name="id" value="" />
<input type=hidden name="priceList"> 
<input type=hidden name="inputPriceList">
<input type=hidden name="mtype" value="" />
<input type=hidden name="hasProm" value="${hasProm}" />
<input type=hidden name="step" value="1" />

<input type=hidden name="locationShadow" value="">

<p:navigation
	height="22">
	<td width="550" class="navigation">库单管理 &gt;&gt; 填写销售单1</td>
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
								<td width="5">&nbsp;</td>
								<td width="6"><img src="../images/dot_r.gif" width="6"
									height="6"></td>
								<td class="caption"><strong><font color=red>${hasOver}</font> 您信用还剩:${credit}</strong>
								<font color="blue">产品仓库：</font>
								<select name="location" class="select_class" oncheck="notNone" onchange="clearsAll()" style="width: 200px">
								    <option value="">--</option>
									<c:forEach items='${locationList}' var="item">
										<option value="${item.id}">${item.name}</option>
									</c:forEach>
								</select>
								${g_stafferBean.industryName}经理信用：
								<select style="width: 300px">
                                    <c:forEach items='${mList}' var="item">
                                        <option>${item}</option>
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
							value="${current}" maxlength="20" size="20"
							readonly="readonly"><font color="#FF0000">*</font></td>

							<td width="15%" align="right">销售类型：</td>
							<td width="35%"><select name="outType" class="select_class" onchange="managerChange()">
								<p:option type="outType_out"></p:option>
							</select><font color="#FF0000">*</font></td>
						
					</tr>

					<tr class="content1">
						<td align="right" id="outd">客户：</td>
						<td colspan="3"><input type="text" name="customerName" maxlength="14" value=""
							onclick="selectCustomer()" style="cursor: pointer;"
							readonly="readonly"><font color="#FF0000">*</font></td>
					</tr>
					<tr class="content2">
						<td align="right">联系人：</td>
						<td><input type="text" name="connector" maxlength="14"
							readonly="readonly"></td>
						<td align="right">联系电话：</td>
						<td><input type="text" name="phone" maxlength="20" readonly></td>
					</tr>
					<tr class="content1">
						<td align="right">经手人：</td>
						<td><input type="text" name="stafferName" maxlength="14"
							value="${user.stafferName}" readonly="readonly"></td>
						<td align="right">单据号码：</td>
						<td><input type="text" name="idName" maxlength="20"
							value="系统自动生成" readonly></td>
					</tr>

					<tr class="content2">
						<td align="right">回款天数：</td>
						<td colspan="1"><input type="text" name="reday" maxlength="4" oncheck="notNone;isInt;range(1, 180)"
							value="" title="请填入1到180之内的数字"><font color="#FF0000">*</font></td>

						<td align="right"></td>
						<td></td>
					</tr>
					
					<tr class="content2">
                        <td align="right">付款方式：</td>
                        <td colspan="1">
                        <select name="reserve3" class="select_class" oncheck="notNone;" head="付款方式" style="width: 240px">
                            <option value='2'>客户信用和业务员信用额度担保</option>
                            <option value='3'>信用担保</option>
                            <option value='1'>款到发货(黑名单客户/零售)</option>
                        </select>
                        <font color="#FF0000">*</font></td>
                        <td align="right">纳税实体：</td>
                        <td colspan="1">
                        <select name="dutyId" class="select_class" style="width: 240px" oncheck="notNone;" onchange="loadShow();changePrice();delAllItem();">
                            <option value="">--</option>
                            <p:option type="$dutyList2"/>
                        </select>
                        <font color="#FF0000">*</font></td>
                    </tr>
                    
                    <tr class="content2">
                        <td align="right">发票类型：</td>
                        <td colspan="3">
                        <select name="invoiceId" class="select_class" head="发票类型" style="width: 400px">
                        </select>
                        <font color="#FF0000">*</font></td>
                    </tr>				
 
					<tr class="content2">
						<td align="right">销售单备注：</td>
						<td colspan="3"><textarea rows="3" cols="55"
							name="description"></textarea>							
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
						<td width="10%" align="center">毛利</td>
						<td width="10%" align="center">毛利率</td>
						<td width="5%" align="center"><input type="button" accesskey="A"
							value="增加" class="button_class" onclick="addTr()"></td>
					</tr>

					<tr class="content1" id="trCopy" style="display: none;">
						<td>
						<input type="text" name="productName"
							onclick="opens(this)"
							productid="" 
							productcode="" 
							price=""
							addprice=""
							stafferid=""
							depotpartid=""
							producttype=""
							oldgoods=""														
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
							style="width: 100%" name="desciprt"><input type="hidden" name="costPrice" value=""></td>
							
						<td align="center"><input type="text" readonly="readonly" value="0.00"
							style="width: 100%" name="profit"></td>
							
						<td align="center"><input type="text" readonly="readonly" value="0.00%"
							style="width: 100%" name="profitRatio"></td>
							
						<td align="center"></td>
					</tr>

					<tr class="content2">
						<td><input type="text" name="productName" id="unProductName"
							onclick="opens(this)" 
							productid="" 
                            productcode="" 
                            price=""
                            addprice=""
                            stafferid=""
                            depotpartid=""
                            producttype=""
                            productsailtype=""
                            oldgoods=""                            
							readonly="readonly"
							style="width: 100%; cursor: pointer"></td>

						<td align="center"><input type="text" style="width: 100%" id="unAmount"
							maxlength="8" onkeyup="cc(this)" name="amount"></td>

						<td align="center"><input type="text" style="width: 100%" id="unPrice" cost=""
							maxlength="13" onkeyup="cc(this)" onblur="blu(this)" name="price"></td>

						<td align="center"><input type="text"
							value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

						<td align="center"><input type="text" id="unDesciprt" readonly="readonly" onkeyup="cc(this)"
							style="width: 100%" name="desciprt"><input type="hidden" id="unCostPrice" name="costPrice" value=""></td>
							
						<td align="center"><input type="text" readonly="readonly" id="unProfit" value="0.00"
							style="width: 100%" name="profit"></td>
							
						<td align="center"><input type="text" readonly="readonly" id="unProfitRatio" value="0.00%"
							style="width: 100%" name="profitRatio"></td>
							
						<td><input type=button value="清空"  class="button_class" onclick="clears()"></td>
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
</body>
</html>


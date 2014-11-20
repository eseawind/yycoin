<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="查询销售单" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src='../js/adapter.js'></script>
<script language="javascript">
function detail()
{
	document.location.href = '../sail/out.do?method=findOut&outId=' + getRadioValue("fullId");
}

function pagePrint()
{
	window.open('../sail/out.do?method=findOut&fow=4&outId=' + getRadioValue("fullId"));
}

function selectCustomer()
{
    window.common.modal("../client/client.do?method=rptQuerySelfClient&stafferId=${user.stafferId}&load=1");
}

function exports()
{
	if (window.confirm("确定导出当前的全部查询的库单?"))
	document.location.href = '../sail/out.do?method=export&flags=1';
}

function mark()
{
	if (window.confirm("确定标记当前选择的库单?"))
	{
		adminForm.action = '../sail/out.do';
		adminForm.method.value = 'mark';
		adminForm.outId.value = getRadioValue("fullId");

		adminForm.submit();
	}
}

function comp()
{
	var now = '${now}';

	var str1 = $O('outTime').value;

	var str2 = $O('outTime1').value;

	//必须要有开始和结束时间一个
	if (str1 == '' && str2 == '')
	{
		alert('必须要有开始和结束时间一个');
		return false;
	}

	if (str1 != '' && str2 == '')
	{
		if (!coo(str1, now))
		{
			alert('查询日期跨度不能大于3个月(90天)!');
			return false;
		}

		$O('outTime1').value = now;
	}

	if (str1 == '' && str2 != '')
	{
		if (!coo(now, str2))
		{
			alert('查询日期跨度不能大于3个月(90天)!');
			return false;
		}

		$O('outTime').value = now;
	}

	if (str1 != '' && str2 != '')
	{
		if (!coo(str1, str2))
		{
			alert('查询日期跨度不能大于3个月(90天)!');
			return false;
		}
	}

	return true;
}

function comp3()
{
	var now = '${now}';

	var str1 = $O('redateB').value;

	var str2 = $O('redateE').value;
	
	if (str1 != '' && str2 == '')
    {
        if (!coo(str1, now))
        {
            alert('查询日期跨度不能大于3个月(90天)!');
            return false;
        }

        $O('redateE').value = now;
    }

    if (str1 == '' && str2 != '')
    {
        if (!coo(now, str2))
        {
            alert('查询日期跨度不能大于3个月(90天)!');
            return false;
        }

        $O('redateB').value = now;
    }

    if (str1 != '' && str2 != '')
    {
        if (!coo(str1, str2))
        {
            alert('查询日期跨度不能大于3个月(90天)!');
            return false;
        }
    }
	
	

	return true;
}

function coo(str1, str2)
{
	var s1 = str1.split('-');
	var s2 = str2.split('-');

	var year1 = parseInt(s1[0], 10);

	var year2 = parseInt(s2[0], 10);

	var month1 = parseInt(s1[1], 10);

	var month2 = parseInt(s2[1], 10);

	var day1 = parseInt(s1[2], 10);

	var day2 = parseInt(s2[2], 10);

	return Math.abs((year2 - year1) * 365 + (month2 - month1) * 30 + (day2 - day1)) <= 90;
}

function modfiy()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return;
	}
	
	if (getRadio('fullId').statuss == '0' || getRadio('fullId').statuss == '2')
	{
		document.location.href = '../sail/out.do?method=findOut&outId=' + getRadioValue("fullId") + "&fow=1";
	}
	else
	{
		alert('只有保存态和驳回态的库单可以修改!');
	}
}

function updateInvoice()
{
    if ($$('invoices') != '')
    {
        document.location.href = '../sail/out.do?method=updateInvoice&outId=' + getRadioValue("fullId") + "&invoices=" + $$('invoices');
    }
    else
    {
        alert('请选择发票类型');
    }
}

var dutyMap = {};

<c:forEach items="${dutyList}" var="item">
    dutyMap['${item.id}'] = [];
    <c:forEach items="${item.outInvoiceBeanList}" var="item2" varStatus="vs">
        dutyMap['${item.id}'][${vs.index}] = '${item2.id}';
    </c:forEach>
</c:forEach>

function showInvoice(dutyId)
{
    var list = document.getElementsByTagName('p');
    
    var iList = dutyMap[dutyId];
    
    for (var i = 0; i < list.length; i++)
    {
        var ele = list[i];
        
        if (isIn(iList, ele.id))
        {
            $v(ele, true);
        }
        else
        {
            $v(ele, false);
        }
    }
}

function isIn(list, id)
{
    if (id.indexOf('s_') != 0)
    {
        return true;
    }
    
    for (var i = 0; i < list.length; i++)
    {
        if (('s_' + list[i]) == id)
        {
            return true;
        }
    }
    
    return false;
}

function preUpdateInvoice()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return;
	}
	
    if (getRadio('fullId').pinvoiceid == '')
    {
        alert('销售单开单选择不开发票,不能修改开票类型!');
        return;
    }
    
    if (parseFloat(getRadio('fullId').pinvoicemoney) == 0)
    {
        showInvoice(getRadio('fullId').pdutyid);
        
        setRadioValue('invoices', getRadio('fullId').pinvoiceid);
        
        $('#dlg').dialog({closed:false});
    }
    else
    {
        alert('已经开票,不能修改!');
    }
}

function del()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return;
	}
	
	if (getRadio('fullId').statuss == '0' || getRadio('fullId').statuss == '2' || getRadio('fullId').temptype == '1')
	{
		 if (window.confirm("确定删除销售单?"))
		document.location.href = '../sail/out.do?method=delOut&outId=' + getRadioValue("fullId");
	}
	else
	{
		alert('只有保存态和驳回态的库单可以删除!');
	}
}

function balance()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return;
	}
	
    if ((getRadio('fullId').statuss == '3' || getRadio('fullId').statuss == '4') && getRadio('fullId').outtype == '3')
    {
        document.location.href = '../sail/out.do?method=preForAddOutBalance&type=0&outId=' + getRadioValue("fullId");
    }
    else
    {
        alert('不能操作!');
    }
}

function tran()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return;
	}
	
    if ((getRadio('fullId').statuss == '3' || getRadio('fullId').statuss == '4') && getRadio('fullId').ppay == '1')
    {
        if (window.confirm("确定申请移交此销售单到自己名下?"))
        document.location.href = '../sail/out.do?method=tranOut&outId=' + getRadioValue("fullId");
    }
    else
    {
        alert('必须选择已经完全付款的单据,不能操作!');
    }
}

function refBill()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return;
	}
	
    if (getRadio('fullId').statuss != '2' && getRadio('fullId').statuss != '0' 
        && getRadio('fullId').outtype != 1 && getRadio('fullId').outtype != 3 && getRadio('fullId').outtype != 5)
    {
        document.location.href = '../finance/bank.do?method=preForRefBill&outId=' + getRadioValue("fullId") + '&customerId=' + getRadio('fullId').pcustomerid;
    }
    else
    {
        if (getRadio('fullId').outtype == 1 || getRadio('fullId').outtype == 3)
        alert('个人领样和委托代销不走此勾款流程!\r\n委托代销通过委托代销结算/退货勾款,\r\n个人领样通过退库和转销售勾款.');
        else
        alert('不能操作!');
    }
}

function balance2()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return;
	}
	
    if ((getRadio('fullId').statuss == '3' || getRadio('fullId').statuss == '4')
         && getRadio('fullId').outtype == '3')
    {
        document.location.href = '../sail/out.do?method=preForAddOutBalance&type=1&outId=' + getRadioValue("fullId");
    }
    else
    {
        alert('不能操作!');
    }
}

function sub()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return;
	}
	
	if (getRadio('fullId').statuss == '0' || getRadio('fullId').statuss == '2')
	{
		 if (window.confirm("确定提交销售单?"))
		 {
		 	$O('method').value = 'modifyOutStatus';
		 	$O('oldStatus').value = getRadio('fullId').statuss;
		 	$O('statuss').value = '1';
		 	$O('outId').value = getRadioValue("fullId");
		 	
		 	disableAllButton();
		 	adminForm.submit();
		 }
	}
	else
	{
		alert('此状态不能提交!');
	}
}

function query()
{
	if (comp() || comp3())
	{
		adminForm.submit();
	}
}

function getCustomer(oos)
{
    var obj = oos;
    
	$O("customerName").value = obj.pname;
	
	$O("customerId").value = obj.value;
}

function res()
{
	$O('customerName').value = '';
	$O("customerId").value = '';
	$O("id").value = '';
}

var jmap = new Object();
<c:forEach items="${listOut1}" var="item">
	jmap['${item.fullId}'] = "${divMap[item.fullId]}";
</c:forEach>

function showDiv(id)
{
	tooltip.showTable(jmap[id]);
}

function dialog_open()
{
    $v('dia_inner', true);
}

function load()
{
	loadForm();
	
	tooltip.init();
	
	bingTable("mainTable");
	
	highlights($("#mainTable").get(0), ['驳回'], 'red');
	
	highlights($("#mainTable").get(0), ['结束'], 'blue');
	
	$('#dlg').dialog({
                //iconCls: 'icon-save',
                modal:true,
                closed:true,
                buttons:{
                    '确 定':function(){
                        updateInvoice();
                    },
                    '取 消':function(){
                        $('#dlg').dialog({closed:true});
                    }
                }
     });
     
     $ESC('dlg');
}

function checkCurrentUser()
{	
     // check
     var elogin = "${g_elogin}";

 	 //  商务登陆
     //if (elogin == '1')
     //{
          var top = window.top.topFrame.document;
          //var allDef = window.top.topFrame.allDef;
          var srcStafferId = top.getElementById('srcStafferId').value;
         
          var currentStafferId = "${g_stafferBean.id}";

          var currentStafferName = "${g_stafferBean.name}";
         
          if (srcStafferId && srcStafferId != currentStafferId)
          {

               alert('请不要同时打开多个OA连接，且当前登陆者不同，当前登陆者应为：' + currentStafferName);
               
               return false;
          }
     //}

	return true;
}

</script>

</head>
<body class="body_class" onkeypress="tooltip.bingEsc(event)" onload="load()">
<form action="../sail/out.do" name="adminForm"><input type="hidden"
	value="querySelfOut" name="method"> <input type="hidden" value="1"
	name="firstLoad">
	<input type="hidden" value="${customerId}"
	name="customerId">
	<input type="hidden" value="${queryType}"
	name="queryType">
	<input type="hidden" value="${GFlag}"
	name="flagg">
	<input type="hidden" value=""
	name="outId">
	<input type="hidden" value=""
    name="oldStatus">
	<input type="hidden" value=""
	name="statuss">
	<input type="hidden" value="1"
    name="selfQuery">
<input type="hidden" value="${vtype}" name="vtype">
<c:set var="fg" value='销售'/>

<p:navigation
    height="22">
    <td width="550" class="navigation">库单管理 &gt;&gt; 我的销售单${vtype}</td>
                <td width="85"></td>
</p:navigation> <br>

<table width="98%" border="0" cellpadding="0" cellspacing="0"
	align="center">
	<tr>
		<td align='center' colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content1">
						<td width="15%" align="center">开始时间</td>
						<td align="center" width="35%"><p:plugin name="outTime" size="20" value="${outTime}"/></td>
						<td width="15%" align="center">结束时间</td>
						<td align="center"><p:plugin name="outTime1" size="20" value="${outTime1}"/>
						</td>
					</tr>

                    <tr class="content2">
                        <td width="15%" align="center">回款时间从</td>
                        <td align="center" width="35%"><p:plugin name="redateB" type="0" size="20" value="${redateB}"/></td>
                        <td width="15%" align="center">到</td>
                        <td align="center"><p:plugin name="redateE" type="0" size="20" value="${redateE}"/>
                        </td>
                    </tr>

					<tr class="content1">
						<td width="15%" align="center">销售单状态</td>
						<td align="center">
						<c:if test="${!ff}">
						<select name="status" class="select_class" values="${status}">
							<option value="">--</option>
							<p:option type="outStatus"/>
							<option value="99">发货态</option>
						</select>
						</c:if>

						</td>
						<td width="15%" align="center">客户：</td>
						<td align="center"><input type="text" name="customerName" maxlength="14" value="${customerName}"
							onclick="selectCustomer()" style="cursor: pointer;"
							readonly="readonly"></td>
					</tr>

					<tr class="content2">
						<td width="15%" align="center">销售类型</td>
						<td align="center">
						<select name="outType"
							class="select_class" values=${outType}>
							<option value="">--</option>
							<p:option type="outType_out"/>
						</select>

						</td>
						<td width="15%" align="center">销售单号</td>
						<td align="center"><input type="text" name="id" value="${id}"></td>
					</tr>

					<tr class="content1">
						<td width="15%" align="center">是否回款</td>
						<td align="center" colspan="1"><select name="pay" values="${pay}"
							class="select_class">
							<option value="">--</option>
							<option value="1">是</option>
							<option value="0">否</option>
							<option value="2">超期</option>
						</select></td>
						
						<td width="15%" align="center">仓库</td>
                        <td align="center">
                        <select name="location"
                            class="select_class" values=${location}>
                            <option value="">--</option>
                            <c:forEach items="${depotList}" var="item">
                             <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                        </td>
					
					</tr>

                    <tr class="content2">
                        <td width="15%" align="center">产品名称</td>
                        <td align="center"><input type="text" name="product_name"></td>
                    </tr>

					<tr class="content2">

						<td colspan="4" align="right"><input type="button" id="query_b"
							onclick="query()" class="button_class"
							value="&nbsp;&nbsp;查 询&nbsp;&nbsp;">&nbsp;&nbsp;
							<input type="button" onclick="res()" class="button_class" value="&nbsp;&nbsp;重 置&nbsp;&nbsp;"></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>

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
								<td class="caption"><strong>浏览${fg}单:</strong><font color=blue>[当前查询数量:${my:length(listOut1)}]</font></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
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
		<td align='center' colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="mainTable">
					<tr align="center" class="content0">
						<td align="center" width="5%" align="center">选择</td>
						<td align="center" onclick="tableSort(this)" class="td_class">单据编号</td>
						<td align="center" onclick="tableSort(this)" class="td_class">客户</td>
						<td align="center" onclick="tableSort(this)" class="td_class">状态</td>
						<td align="center" onclick="tableSort(this)" class="td_class">${fg}类型</td>
						<td align="center" onclick="tableSort(this)" class="td_class">${fg}时间</td>
						<td align="center" onclick="tableSort(this)" class="td_class">回款日期</td>
						<td align="center" onclick="tableSort(this)" class="td_class">付款方式</td>
						<td align="center" onclick="tableSort(this)" class="td_class">金额</td>
						<td align="center" onclick="tableSort(this)" class="td_class">付款</td>
						<td align="center" onclick="tableSort(this)" class="td_class">${fg}人</td>
						<td align="center" onclick="tableSort(this)" class="td_class">仓库</td>
						<td align="center" onclick="tableSort(this)" class="td_class">发货单</td>
					</tr>

					<c:forEach items="${listOut1}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'
						>
							<td align="center"><input type="radio" name="fullId" 
							    temptype="${item.tempType}"
							    outtype="${item.outType}"
								pcustomerid='${item.customerId}' 
								statuss='${item.status}' 
								pinvoicemoney='${item.invoiceMoney}' 
								pinvoiceid='${item.invoiceId}' 
								ppay='${item.pay}' 
								pdutyid='${item.dutyId}' 
								value="${item.fullId}" ${vs.index== 0 ? "checked" : ""}/></td>
							<td align="center"
							onMouseOver="showDiv('${item.fullId}')" onmousemove="tooltip.move()" onmouseout="tooltip.hide()"><a onclick="hrefAndSelect(this)" href="../sail/out.do?method=findOut&fow=99&outId=${item.fullId}">${item.mark ? "<font color=blue><B>" : ""}
							${item.fullId} ${item.mark ? "</B></font>" : ""}</a></td>
							<td align="center" onclick="hrefAndSelect(this)">${item.customerName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:get('outStatus', item.status)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:get('outType_out', item.outType)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.outTime}</td>
							<c:if test="${item.pay == 0}">
							<td align="center" onclick="hrefAndSelect(this)"><font color=red>${item.redate}</font></td>
							</c:if>
							<c:if test="${item.pay == 1}">
							<td align="center" onclick="hrefAndSelect(this)"><font color=blue>${item.redate}</font></td>
							</c:if>
							
							<c:if test="${item.reserve3 == 1}">
							<td align="center" onclick="hrefAndSelect(this)">款到发货(黑名单客户/零售)</td>
							</c:if>
							<c:if test="${item.reserve3 == 2}">
							<td align="center" onclick="hrefAndSelect(this)">客户信用和业务员信用额度担保</td>
							</c:if>
							<c:if test="${item.reserve3 == 3}">
							<td align="center" onclick="hrefAndSelect(this)">信用担保</td>
							</c:if>
							
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.total)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.hadPay)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.stafferName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.depotName}</td>
							<td align="center" onclick="hrefAndSelect(this)">
                            <a
                            href="../sail/transport.do?method=findConsign&forward=3&fullId=${item.fullId}"
                            >
                            ${my:get('consignStatus', item.consign)}
                            </a>
                            </td>
						</tr>
					</c:forEach>
				</table>

				<p:formTurning form="adminForm" method="querySelfOut"></p:formTurning>
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

	<c:if test="${my:length(listOut1) > 0}">
	<tr>
		<td width="100%">
		<div align="right">
		<input type="button" class="button_class"
			value="&nbsp;&nbsp;标 记&nbsp;&nbsp;" onClick="mark()">&nbsp;&nbsp;
		<input type="button" class="button_class"
            value="&nbsp;修改发票&nbsp;" onClick="preUpdateInvoice()">&nbsp;&nbsp;
		<input
			type="button" class="button_class"
			value="&nbsp;&nbsp;修 改&nbsp;&nbsp;" onclick="modfiy()" />&nbsp;&nbsp;
			<c:if test="${my:auth(user, '999999')}">
			<input type="button" class="button_class"
			value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="sub()" />&nbsp;&nbsp; 
			</c:if>
			<input
			type="button" class="button_class"
			value="&nbsp;&nbsp;删 除&nbsp;&nbsp;" onclick="del()" />&nbsp;&nbsp;
		<input
            type="button" class="button_class"
            value="&nbsp;&nbsp;勾 款&nbsp;&nbsp;" onclick="refBill()" />&nbsp;&nbsp;
		<input
            type="button" class="button_class"
            value="&nbsp;委托代销结算&nbsp;" onclick="balance()" />&nbsp;&nbsp;
        <input
            type="button" class="button_class"
            value="&nbsp;委托代销退货&nbsp;" onclick="balance2()" />&nbsp;&nbsp;
        <input
            type="button" class="button_class"
            value="&nbsp;销售单移交&nbsp;" onclick="tran()" />&nbsp;&nbsp;
            
        <c:if test="${my:auth(user, '1417')}">
			<input
				type="button" class="button_class"
				value="&nbsp;导出查询结果&nbsp;" onclick="exports()" /></div>
			</td>
		</c:if>
		<td width="0%"></td>
	</tr>
	
	</c:if>

	<tr height="10">
		<td height="10" colspan='2'></td>
	</tr>

	<p:message2/>
</table>

</form>

<div id="dlg" title="选择发票类型" style="width:400px;">
    <div style="padding:20px;height:200px;display: none;" id="dia_inner" title="">
    <c:forEach items="${invoiceList}" var="item">
    <p id="s_${item.id}"><input type="radio" name="invoices" value="${item.id}">${item.fullName}<br></p>
    </c:forEach>
    </div>
</div>

</body>
</html>

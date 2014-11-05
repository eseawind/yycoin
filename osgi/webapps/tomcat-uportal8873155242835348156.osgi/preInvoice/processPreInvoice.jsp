<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="预开票" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../tcp_js/travelApply.js"></script>
<script language="javascript">

function processBean(opr)
{
	$("input[name='oprType']").val(opr);
    
    var msg = '';
    
    var checkFun = null;
    
    if ("0" == opr)
    {
        msg = '确定通过预开票申请?';
    }
    
    if ("1" == opr)
    {
        msg = '确定驳回到初始?';
    }
    
    if ($O('processer'))
    {
	    if ("0" != opr)
	    {
	        $O('processer').oncheck = '';
	    }
	    else
	    {
	        $O('processer').oncheck = 'notNone';
	    }
    }

    submit(msg, null, checkInFun);
}

function checkInFun()
{
	var p_mayInvoiceMoneys = document.getElementsByName("p_mayInvoiceMoney");
	var p_invoiceMoneys = document.getElementsByName("p_invoiceMoney");

	var invoiceMoney = 0;

	if (p_mayInvoiceMoneys.length > 1){
		for (var i = 0; i < p_mayInvoiceMoneys.length - 1; i++)
		{
			if (parseFloat(p_mayInvoiceMoneys[i].value) < parseFloat(p_invoiceMoneys[i].value))
			{
				alert('开票金额不能大于可开票金额');
				
				$f(p_invoiceMoneys[i]);

				return false;
			}

			invoiceMoney += parseFloat(p_invoiceMoneys[i].value);
		}

		$O('invoiceMoney').value = invoiceMoney;
	}

	return true;
}

var showTr = false;

function showFlowLogTr()
{
    $v('flowLogTr', showTr);
    
    showTr = !showTr;
}

function load()
{
	showFlowLogTr();
}

function addTr()
{
	return addTrInner("tables_realPay", "trCopy");
}

function opens()
{
    if ($O('customerId').value == '')
    {
        alert('请选择客户');
        return false;
    }
    
    if ($O('customerId').value == '99')
     window.common.modal('../sail/out.do?method=rptQueryOutForInvoice&mode=1&selectMode=1&stafferId=${user.stafferId}&invoiceStatus=0&load=1&customerId=' + $$('customerId'));
     else
     window.common.modal('../sail/out.do?method=rptQueryOutForInvoice&mode=1&selectMode=1&invoiceStatus=0&load=1&customerId=' + $$('customerId'));
}

function openBalance()
{
    if ($O('customerId').value == '')
    {
        alert('请选择客户');
        return false;
    }
    
    window.common.modal('../sail/out.do?method=rptQueryOutBalance&mode=1&type=0&stafferId=${user.stafferId}&selectMode=1&invoiceStatus=0&load=1&customerId=' + $$('customerId'));
}

function getOut(oos)
{
    var outIds = $$('outIds');

    var oldm = parseFloat($$('invoiceMoney'));
    
    for (var i = 0 ; i < oos.length; i++)
    {
        var oo = oos[i];
        
        if (outIds.indexOf(oo.value) == -1)
        {
            outIds += oo.value + ";";
            
            oldm += parseFloat(oo.pinvoicemoney);

            // 增加行项目

            var tr = addTr();
            
			setInputValueInTr(tr, 'p_outId', oo.value);
			setInputValueInTr(tr, 'p_money', oo.ptotal);
			setInputValueInTr(tr, 'p_mayInvoiceMoney', oo.pinvoicemoney);
        }
    }
    
    $O('outIds').value = outIds;   
    
    $O('invoiceMoney').value = formatNum(oldm, 2);

}

function getOutBalance(oos)
{
	return getOut(oos);
}


</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../tcp/preinvoice.do?method=processPreInvoiceBean" method="post">
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="processId" value=""> 
<input type="hidden" name="id" value="${bean.id}"> 
<input type="hidden" name="customerId" value="${bean.customerId}">
<input type="hidden" name="totalMoney" value="${my:formatNum(bean.total / 100.0)}">
<input type="hidden" name="duty" value="${bean.dutyId}">
<input type="hidden" name="outIds" value="${outIds}">

<p:navigation height="22">
	<td width="550" class="navigation">预开票</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		 <strong>预开票</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
	
	    <p:class value="com.china.center.oa.finance.bean.PreInvoiceApplyBean" opr="2"/>
	    
		<p:table cells="2">
			<p:cell title="处理流程" width="8" end="true">
            ${bean.flowDescription}
            </p:cell>
            
            <p:pro field="id" cell="0"/>
            
            <p:pro field="stafferId" value="${bean.stafferName}"/>
            <p:pro field="departmentId" value="${bean.departmentName}"/>            
            
            <p:pro field="name" cell="0" innerString="size=60"/>
            
            <p:pro field="invoiceName" cell="0"/>
            
            <p:pro field="invoiceType">
                <p:option type="preInvoiceType" empty="true"></p:option>
            </p:pro>
            
            <p:pro field="dutyId">
            	<option value="">--</option>
                <p:option type="dutyList" />
            </p:pro>
            
            <p:pro field="customerName" innerString="readonly='readonly'">
            </p:pro>
            
            <p:pro field="planOutTime"/>
            
            <p:pro field="total" value="${my:formatNum(bean.total / 100.0)}"/>
            
            <p:cell title="已开票金额">
               <input type="text" size="20" readonly="readonly" name="invoiceMoney" value="${my:formatNum(bean.invoiceMoney / 100.0)}"> 
            </p:cell>
            
            <p:pro field="description" cell="0" innerString="rows=4 cols=55" />
            
            <p:cell title="处理人" width="8" end="true">
            ${bean.processer}
            </p:cell>
            
        </p:table>
	</p:subBody>
	
	<c:if test="${bean.status == 27}">
	<p:title>
        <td class="caption">
         <strong>销售单明细</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr id="pay_main_tr">
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_realPay">
                    <tr align="center" class="content0">
                        <td width="25%" align="center">销售单</td>
                        <td width="15%" align="center">销售单金额</td>
                        <td width="15%" align="center">可开票金额</td>
                        <td width="15%" align="center">开票金额</td>
                        <td width="10%" align="left">
                            <input type="button"
                            value="销售单" class="button_class" onclick="opens()">&nbsp;
                            <input type="button"
                            value="委托代销单" class="button_class" onclick="openBalance()">&nbsp;
                        </td>
                    </tr>
                    
                    <c:forEach items="${bean.vsList}" var="item">
	                    <td align="left"><input type="text" style="width: 100%" readonly=readonly
	                    name="p_outId" value="${item.outId}" >
	                    <input type="hidden" name="p_id" value="${item.id}">
				         </td>
				         
				         <td align="left">
				         <input type="text" style="width: 100%" readonly=readonly
				                    name="p_money" value="${my:formatNum(item.money)}" >
				         </td>
				         
				         <td align="left">
				         <input type="text" style="width: 100%" readonly=readonly
				                    name="p_mayInvoiceMoney" value="${my:formatNum(item.mayInvoiceMoney)}" >
				         </td>
				         
				         <td align="left">
				         <input type="text" style="width: 100%" readonly="readonly"
				                    name="p_invoiceMoney" value="${my:formatNum(item.invoiceMoney)}" >
				         </td>
				         
				        <td width="10%" align="right"></td>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
	</c:if>
    
    <p:title>
        <td class="caption">
         <strong><span style="cursor: pointer;" onclick="showFlowLogTr()">流程日志(点击查看)</span></strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr id="flowLogTr">
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
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

        </td>
    </tr>
    
    <c:if test="${bean.status != 27}">
    <p:title>
        <td class="caption">
         <strong>审核-${my:get('tcpStatus', bean.status)}</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_pay">
                    <tr align="center" class="content0">
                        <td width="15%" align="center">审批意见</td>
                        <td align="left">
                        <textarea rows="3" cols="55" oncheck="notNone;maxLength(200);" name="reason"></textarea>
                        <font color="red">*</font>
                        </td>
                    </tr>
                    <c:if test="${requestScope.pluginType == 'group'}">
                    <tr align="center" class="content1">
                        <td width="15%" align="center">提交到</td>
                        <td align="left">
                        <input type="text" name="processer" readonly="readonly" oncheck="notNone" head="下环处理人"/>&nbsp;
                        <font color=red>*</font>
                        <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                            class="button_class" onclick="selectNext('${pluginType}', '${pluginValue}')">&nbsp;&nbsp;
                        </td>
                    </tr>
                    </c:if>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
    <p:line flag="1" />
	</c:if>
    
	<p:button leftWidth="98%" rightWidth="0%">
        <div align="right">
        <input type="button" class="button_class" id="sub_b1"
            value="&nbsp;&nbsp;通 过&nbsp;&nbsp;" onclick="processBean(0)">
          &nbsp;&nbsp;
          <c:if test="${bean.status != 27}">
	          <input type="button" class="button_class" id="sub_b2"
	            value="&nbsp;&nbsp;驳回到初始&nbsp;&nbsp;" onclick="processBean(1)">
          </c:if>
          <input type="button" name="pr"
            class="button_class" onclick="pagePrint()"
            value="&nbsp;&nbsp;打 印&nbsp;&nbsp;">
        </div>
    </p:button>
	
	<p:message2/>
</p:body>
</form>
<table>
    <tr class="content1" id="trCopy" style="display: none;">
         
         <td align="left"><input type="text" style="width: 100%" readonly=readonly
                    name="p_outId" value="" >
                    <input type="hidden" name="p_id" value="">
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%" readonly=readonly
                    name="p_money" value="" >
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%" readonly=readonly
                    name="p_mayInvoiceMoney" value="" >
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%" oncheck="notNone;isFloat3"
                    name="p_invoiceMoney" value="" >
         </td>
         
        <td width="10%" align="right"><input type=button name="pay_del_bu"
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    
</table>    
</body>
</html>

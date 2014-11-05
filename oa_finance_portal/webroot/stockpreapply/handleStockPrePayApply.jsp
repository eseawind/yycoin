<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="付款申请" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../stockapply_js/detailStockPayApply.js"></script>
<script language="javascript">

function passBean()
{
    $O('method').value = 'passStockPrePayByCEO';
    
	submit('确定通过付款申请?', null, null);
}

function checkValue()
{
    return true;
}

function rejectBean()
{
    //$O('method').value = 'rejectStockPayApply';
    
    if (window.confirm('确定驳回付款申请?'))
    {
        $l('../finance/stock.do?method=rejectStockPrePayApply&id=' + $$('id') + '&reason=' + ajaxPararmter($$('reason')));
    }
}

function ajaxPararmter(str)
{
    str = str.replace(/\+/g, "%2B"); 
    str = str.replace(/\%/g, "%25"); 
    str = str.replace(/\&/g, "%26");
    
    return str;
}

function endBean()
{
    $O('method').value = 'endStockPrePayBySEC';
    
    submit('确定付款给供应商?付款金额:${my:formatNum(bean.moneys)}', null, null);
}

var g_obj;
function selectBank(obj)
{
    g_obj = obj;
    
    //单选
    window.common.modal('../finance/bank.do?method=rptQueryBank&load=1&invoiceId=${bean.invoiceId}');
}

function getBank(obj)
{
    g_obj.value = obj.pname;
    
    var hobj = getNextInput(g_obj.nextSibling);
    
    hobj.value = obj.value;
}

function load()
{
    <c:if test="${bean.status == 3}">
    addTr();
    </c:if>
}

function getNextInput(el)
{
    if (el.tagName && el.tagName.toLowerCase() == 'input')
    {
        return el;
    }
    else
    {
        return getNextInput(el.nextSibling);
    }
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../finance/stock.do" method="post">
<input type="hidden" name="method" value=""> 
<input type="hidden" name="id" value="${bean.id}"> 
<input type="hidden" name="mode" value="${mode}"> 
<p:navigation
	height="22">
	<td width="550" class="navigation">
	<span style="cursor: pointer;"
        onclick="javascript:history.go(-1)">采购付款</span> &gt;&gt;
	<span>付款申请处理</span></td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>付款基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:class value="com.china.center.oa.finance.bean.StockPrePayApplyBean" />

		<p:table cells="2">

			<p:cell title="申请人">
               ${bean.stafferName}
            </p:cell>

			<p:cell title="预付金额">
               ${my:formatNum(bean.moneys)}
            </p:cell>
            
            <p:cell title="预付勾应付金额">
               ${my:formatNum(bean.realMoneys)}
            </p:cell>
            
            <p:cell title="类型">
               ${my:get('stockPayApplyStatus', bean.status)}
            </p:cell>

			<p:cell title="供应商">
               ${bean.providerName}
            </p:cell>
            
             <p:cell title="发票类型">
               ${bean.invoiceName}
            </p:cell>
            
            <p:cell title="最早付款">
               ${bean.payDate}
            </p:cell>
            
			<p:cell title="供应商银行帐号">
               ${bean.provideBank}
            </p:cell>
            
            <p:cell title="时间" end="true">
               ${bean.logTime}
            </p:cell>
            
            <p:cell title="备注" end="true">
               ${bean.description}
            </p:cell>

			<p:cell title="审批意见" end="true">
				<textarea rows=3 cols=55 oncheck="notNone;maxLength(200);" name="reason"></textarea>
				<font color="red">*</font>
			</p:cell>

		</p:table>

	</p:subBody>
	
	<p:line flag="0" />
	
	<c:if test="${bean.status == 3}">
	<tr>
        <td colspan='2' align='center'>
        <table width="100%" border="0" cellpadding="0" cellspacing="0"
            class="border" id="inner_table">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="30%" align="center">银行</td>
                        <td width="30%" align="center">付款类型</td>
                        <td width="15%" align="center">金额</td>
                        <td width="5%" align="left"><input type="button" accesskey="A"
                            value="增加" class="button_class" onclick="addTr()"></td>
                    </tr>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
    <p:line flag="1" />
	</c:if>

	<p:subBody width="100%">
		<table width="100%" border="0" cellspacing='1'>
			<tr align="center" class="content0">
				<td width="10%" align="center">审批人</td>
				<td width="10%" align="center">审批动作</td>
				<td width="10%" align="center">前状态</td>
				<td width="10%" align="center">后状态</td>
				<td width="45%" align="center">意见</td>
				<td width="15%" align="center">时间</td>
			</tr>

			<c:forEach items="${loglist}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center">${item.actor}</td>

					<td align="center">${my:get('oprMode', item.oprMode)}</td>

					<td align="center">${my:get('stockPayApplyStatus', item.preStatus)}</td>

					<td align="center">${my:get('stockPayApplyStatus', item.afterStatus)}</td>

					<td align="center">${item.description}</td>

					<td align="center">${item.logTime}</td>

				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
			<c:if test="${bean.status == 2 || bean.status == 11 || bean.status == 12 || bean.status == 13}">
            <input type="button" class="button_class"
                id="ok_p" style="cursor: pointer" value="&nbsp;&nbsp;通 过&nbsp;&nbsp;"
                onclick="passBean()">&nbsp;&nbsp;
            <input type="button" class="button_class"
            id="re_b" style="cursor: pointer" value="&nbsp;&nbsp;驳 回&nbsp;&nbsp;"
            onclick="rejectBean()">&nbsp;&nbsp;
            </c:if>
            
            <c:if test="${bean.status == 3}">
            <input type="button" class="button_class"
                id="ok_p2" style="cursor: pointer" value="&nbsp;&nbsp;付 款&nbsp;&nbsp;"
                onclick="endBean()">&nbsp;&nbsp;
            <input type="button" class="button_class"
            id="re_b" style="cursor: pointer" value="&nbsp;&nbsp;驳 回&nbsp;&nbsp;"
            onclick="rejectBean()">&nbsp;&nbsp;
            </c:if>
            
            <input
            type="button" name="ba" class="button_class"
            onclick="javascript:history.go(-1)"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;">
			</div>
	</p:button>

	<p:message2 />
</p:body>
</form>
<table>
    <tr class="content1" id="trCopy" style="display: none;">
         <td width="50%" align="center">
         <input name="bankName" type="text" readonly="readonly" style="width: 100%;cursor: pointer;" oncheck="notNone" onclick="selectBank(this)">
         <input type="hidden" name="bankId" value=""> 
         </td>
         <td width="25%" align="center">
         <select name="payType" style="width: 100%" class="select_class" oncheck="notNone">
                <p:option type="outbillPayType"></p:option>
                </select>
         </td>
         <td width="20%" align="center"><input type="text" style="width: 100%"
                    name="money" value="${my:formatNum(bean.moneys)}" oncheck="notNone;isFloat">
         </td>
         <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
</table>

</body>
</html>


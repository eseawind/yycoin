<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="发票核销" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="javascript">

function addBean()
{
	formEntry.action="../finance/invoiceins.do";
	
	getObj('method').value = 'refConfirmInvoice';
	
	submit('确定发票确认?', null, check);
}

function check()
{
    //计算是否回款溢出
    var total = ${my:formatNum(total)};

    if (total == '0.00')
    {
		alert('发票可确认的金额为0,不可操作');
        
        return false;
    } 
    
    var pu = 0.0;
    var pu1 = 0.0;
    
    var bills = document.getElementsByName('fullId');
    
    var isSelect = false;
    
    for (var i = 0; i < bills.length; i++)
    {
    	pu1 += parseFloat(bills[i].pmoney);
	    if (bills[i].checked)
	    {
	        pu += parseFloat(bills[i].pmoney);
	        
	        isSelect = true;
	    }
    }
    
    if (!isSelect)
    {
        alert('请选择付款单');
        
        return false;
    }
    
    return true;
}

function query()
{
	formEntry.action="../sail/out.do";
	
	if ($$('type') == '0')
		getObj('method').value = 'queryForConfirmRetIns';
	else
		getObj('method').value = 'queryForConfirmOutIns';
	
	formEntry.submit();
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../sail/out.do" method="post">
<input type="hidden" name="method" value="queryForConfirmOutIns"> 
<input type="hidden" name="invoiceId" value="${invoiceId}">
<input type="hidden" name="invoiceName" value="${invoiceName}">
<input type="hidden" name="providerId" value="${providerId}">
<input type="hidden" name="providerName" value="${providerName}">
<input type="hidden" name="total" value="${total}"> 
<input type="hidden" name="type" value="${type}">
<input type="hidden" value="1" name="firstLoad">

<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">发票确认</span> &gt;&gt; 发票确认</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

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
                        <td width="15%" align="center">单号：</td>
                        <td align="center"><input type="text" name="outId" maxlength="30" value="${outId}"></td>
						<td width="15%" align="center"></td>
                        <td align="center"></td>
					</tr>

					<tr class="content2">

						<td colspan="4" align="right"><input type="button" id="query_b"
							onclick="query()" class="button_class"
							value="&nbsp;&nbsp;查 询&nbsp;&nbsp;">&nbsp;&nbsp;
							</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>

	<p:title>
		<td class="caption"><strong>发票【${invoiceName}】,客户/供应商【${providerName}】可用于确认的金额:${my:formatNum(total)}</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		  <table width="100%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="5%" align="center">选择</td>
                        <td width="20%" align="center">单号</td>
                        <td width="20%" align="center">供应商/客户</td>
                        <td width="10%" align="center">需确认金额</td>
                    </tr>

                    <c:forEach items="${wrapList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center"><input type="checkbox" name="fullId" value="${item.fullId}~${item.outType}~${item.mayConfirmMoney}" >
                            </td>
                            
                            <td align="center">
                             <c:if test="${item.outType == 999}">
                            <a href="../finance/stock.do?method=findStockPayApply&id=${item.fullId}">${item.fullId}</a>
                            </c:if>
                            <c:if test="${item.outType == 98}">
                            <a href="../sail/out.do?method=findOutBalance&id=${item.fullId}">${item.fullId}</a>
                            </c:if>
                            <c:if test="${item.outType != 999 && item.outType != 98}">
                            <a href="../sail/out.do?method=findOut&fow=99&outId=${item.fullId}">${item.fullId}</a>
                            </c:if>
                            </td>

                            <td align="center">
                            	${item.customerName}
                            </td>

                            <td align="center">${my:formatNum(item.mayConfirmMoney)}
                            </td>

                        </tr>
                    </c:forEach>
                </table>
                
                <c:if test="${type=='1'}">
                	<p:formTurning form="formEntry" method="queryForConfirmOutIns"></p:formTurning>
                </c:if>
                
                </td>
            </tr>
        </table>
        
	</p:subBody>
	
	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()">&nbsp;&nbsp;
			</div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>


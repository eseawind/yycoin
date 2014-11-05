<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="付款单详细" guid="true" dialog="true"/>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">
function checkBean()
{
    $.messager.prompt('总部核对', '请核对说明', '', function(msg){
        if (msg)
        {
            $l('../finance/finance.do?method=checks2&type=4&id=${bean.id}&reason=' + ajaxPararmter(msg) + '&type=${ltype}');
        }
    }, 2);
}

function pagePrint()
{
    var old = $O('b_div').style.display;
    
    $O('b_div').style.display = 'none';
    window.print();

    $O('b_div').style.display = old;
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../finance/invoiceins.do" method="post">
<input type="hidden" name="method" value=""> <p:navigation
	height="22">
	<td width="550" class="navigation"><span>付款单详细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>付款单信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:class value="com.china.center.oa.finance.bean.OutBillBean" opr="2"/>

		<p:table cells="2">
		
		    <p:cell title="标识">
               ${bean.id}
            </p:cell>
            
            <p:pro field="payType">
                <p:option type="outbillPayType"/>
            </p:pro>

			<p:pro field="type" innerString="style='WIDTH: 340px;'">
			   <p:option type="outbillType" />
			</p:pro>

            <p:pro field="lock" innerString="style='WIDTH: 340px;'">
               <p:option type="billLock" />
            </p:pro>
            
            <p:pro field="mtype" cell="2">
            <p:option type="pubManagerType"></p:option>
            </p:pro>
            
            <p:cell title="帐号">
               ${bean.bankName}
            </p:cell>
            
             <p:cell title="目的帐号">
               ${bean.destBankName}
            </p:cell>
            
            <p:cell title="状态">
               ${my:get('outbillStatus', bean.status)}
            </p:cell>
            
            <p:cell title="发票类型">
               ${bean.invoiceName}
            </p:cell>
			
			<p:cell title="单位">
               ${bean.provideName}
            </p:cell>
			
			<p:cell title="关联单据">
			    ${bean.stockId}-->${bean.stockItemId}
            </p:cell>
            
            <p:cell title="会计">
               ${bean.stafferName}
            </p:cell>
            
            <p:cell title="职员">
               ${bean.ownerName}
            </p:cell>
            
            <p:cell title="总金额">
               ${my:formatNum(bean.moneys)}
            </p:cell>
            
             <p:cell title="原始金额">
               ${my:formatNum(bean.srcMoneys)}
            </p:cell>
            
            <p:cell title="时间">
               ${bean.logTime}
            </p:cell>
            
             <p:cell title="关联收付">
               <a href="../finance/bill.do?method=findBill&id=${bean.refBillId}">${bean.refBillId}</a>
            </p:cell>

			<p:cell title="备注" end="true">
               ${bean.description}
            </p:cell>
			
			<p:cell title="核对状态" end="true">
               ${my:get('pubCheckStatus', bean.checkStatus)}
            </p:cell>
            
            <p:cell title="核对信息" end="true">
               ${bean.checks}
            </p:cell>
            
            <p:cells celspan="2" title="关联凭证">
            <c:forEach items="${financeBeanList}" var="item">
                <a href="../finance/finance.do?method=findFinance&id=${item.id}&backType=1&backId=${bean.id}">${item.id}</a>
                &nbsp;
            </c:forEach>
            </p:cells>

		</p:table>

	</p:subBody>

	
	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right" id="b_div">
		<c:if test="${check == 1}">
        <input
            type="button" name="ba" class="button_class"
            onclick="checkBean()"
            value="&nbsp;&nbsp;总部核对&nbsp;&nbsp;">&nbsp;&nbsp;
        </c:if>
        <input type="button" name="pr"
            class="button_class" onclick="pagePrint()"
            value="&nbsp;&nbsp;打 印&nbsp;&nbsp;">&nbsp;&nbsp;
		<input
            type="button" name="ba" class="button_class"
            onclick="javascript:history.go(-${goback})"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>


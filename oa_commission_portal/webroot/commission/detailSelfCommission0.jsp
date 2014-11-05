<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="提成明细" cal="true" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/tableSort.js"></script>
<script language="javascript">

function load()
{
	loadForm();
	
	bingTable("senfe");
}

function query()
{
	submit(null, null, null);
}


</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../budget/budget.do" method="post">
<input type="hidden" name="method" value="${cacheQueryKey}"> 
<input type="hidden" value="1" name="firstLoad">

<p:navigation
	height="22">
	<td width="550" class="navigation">冻结提成明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		<strong>冻结后的提成公式：（当月冻结前提成+以前冻结提成）*（1-冻结比例）</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		
		<p:table cells="2">
		    <p:cell title="冻结后提成">
               <strong>${my:formatNum(bean.finalCommission)}</strong>
            </p:cell>
            
            <p:cell title="冻结前提成">
               ${my:formatNum(bean.realCommission)}
            </p:cell>
            
            <p:cell title="本月冻结提成">
               ${my:formatNum(freezeBean.freezeMoney)}
            </p:cell>
            
            <p:cell title="本月冻结比例">
               ${my:formatNum(freezeBean.freezeRate)}
            </p:cell>
            
            <p:cell title="以前冻结提成">
               	${my:formatNum(bean.befFreeze)}
            </p:cell>
            
            <p:cell title="信用金额">
            	${my:formatNum(freezeBean.creditMoney)}
            </p:cell>
            
            <p:cell title="信用额度">
            	${my:formatNum(freezeBean.creditAmout)}
            </p:cell>
            
            <p:cell title="信用占比">
            	${freezeBean.creditRate * 100}%
            </p:cell>
                       
            
        </p:table>

	</p:subBody>

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
        <div align="right"><input type="button" class="button_class"
            id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
            onclick="javaScript:window.history.go(-1);"></div>
    </p:button>
	
	<p:message2 />
	
</p:body>
</form>
</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="指标项" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定修改指标项?');
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../credit/credit.do"><input
	type="hidden" name="method" value="updateCreditItemThr">
<input type="hidden" name="id" value="${bean.id}"> 
<input type="hidden" name="pid" value="${parent.id}"> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">指标项管理</span> &gt;&gt; 修改指标项</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>指标项基本信息：(档次在百分比下是标识排序先后,在实际值下档次就是具体衡量指标)</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.credit.bean.CreditItemThrBean" opr="1"/>

		<p:table cells="1">
		    
		    <p:cell title="评价项标题">
		      ${parent.name}(${my:get('creditItemType', parent.type)})
		    </p:cell>  
			
			<p:pro field="name"/>
			
			<p:pro field="per"/>
			
			<p:pro field="indexPos" outString="${parent.unit}"/>
			
		</p:table>
	</p:subBody>
	
	<tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="40%" align="center">评价级别</td>
                        <td width="30%" align="center">得分</td>
                        <td width="30%" align="center">档次</td>
                    </tr>

                    <c:forEach items="${thrList}" var="item" varStatus="vs">
                        <tr class="content2" id="trCopy${vs.index}">
                            <td width="40%" align="center">${item.name}</td>
                            <td width="30%" align="center">${item.per}</td>
                            <td width="30%" align="center">${item.indexPos}</td>

                        </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
		 value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>
</p:body></form>
</body>
</html>


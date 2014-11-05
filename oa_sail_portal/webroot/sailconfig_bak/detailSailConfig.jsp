<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="销售规则明细" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定修改销售规则?');
}
</script>

</head>
<body class="body_class">
<form name="formEntry" action="../sail/config.do" method="post">
<input type="hidden" name="method" value="">
<input type="hidden" name="pareId" value="${bean.pareId}">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">销售规则管理</span> &gt;&gt; 销售规则明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>销售规则基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.sail.bean.SailConfigBean" opr="2"/>

		<p:table cells="2">
		    
		    <p:cells title="开票品名" celspan="2">
	            <c:forEach items="${showList}" var="item" varStatus="vs">
	            <input type="checkbox" name="showIds"  value="${item.id}" 
	            <c:if test="${item.dutyId == '1'}">
	            checked="checked"
                </c:if>
	            >${item.name}&nbsp;&nbsp;
	            <c:if test="${(vs.index + 1) % 7 == 0}">
	            <br/>
	            </c:if>
	            </c:forEach>
            </p:cells>

			<p:pro field="sailType">
				<p:option type="productSailType"/>
			</p:pro>
			
			<p:pro field="productType">
                <p:option type="productType"/>
            </p:pro>

			<p:pro field="finType0">
                <p:option type="sailConfigFinType"/>
            </p:pro>
			<p:pro field="ratio0" />
			
			<p:pro field="finType4">
                <p:option type="sailConfigFinType"/>
            </p:pro>
            <p:pro field="ratio4" />
            
            <p:pro field="finType5">
                <p:option type="sailConfigFinType"/>
            </p:pro>
            <p:pro field="ratio5" />
			
			<p:pro field="finType1">
                <p:option type="sailConfigFinType"/>
            </p:pro>
            <p:pro field="ratio1" />
            
            <p:pro field="finType2">
                <p:option type="sailConfigFinType"/>
            </p:pro>
            <p:pro field="ratio2" />
            
            <p:pro field="finType3">
                <p:option type="sailConfigFinType"/>
            </p:pro>
            <p:pro field="ratio3" />
			
			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
        <div align="right">
        <input type="button" class="button_class" id="ok_b"
            style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
            onclick="javascript:history.go(-1)"></div>
    </p:button>

    <p:message2/>
</p:body></form>
</body>
</html>


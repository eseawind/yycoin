<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="销售单移交申请" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">

function load()
{
  
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/tran.do" method="post">
<input type="hidden" name="method" value=""> 
<input type="hidden" name="id" value="${bean.id}"> 
<p:navigation
	height="22">
	<td width="550" class="navigation">
	<span>销售单移交</span></td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>销售单移交申请基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:class value="com.china.center.oa.sail.bean.SailTranApplyBean" />

		<p:table cells="2">
		
		    <p:cell title="标识" end="true">
                ${bean.id}
            </p:cell>

			<p:cell title="销售标识">
                <a href="../sail/out.do?method=findOut&outId=${bean.outId}">${bean.outId}</a>
            </p:cell>

            <p:cell title="客户">
                ${bean.customerName}
            </p:cell>
            
            <p:cell title="申请人">
                ${bean.stafferName}
            </p:cell>
            
            <p:cell title="原拥有人">
                ${bean.oldStafferName}
            </p:cell>
            
            <p:cell title="状态">
                ${my:get('sailTranApplyStatus', bean.status)}
            </p:cell>
            
            <p:cell title="时间">
                ${bean.logTime}
            </p:cell>

		</p:table>

	</p:subBody>
	
	<p:line flag="0" />

	<p:subBody width="100%">
		<table width="100%" border="0" cellspacing='1' id="tables">
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

					<td align="center">${my:get('sailTranApplyStatus', item.preStatus)}</td>

					<td align="center">${my:get('sailTranApplyStatus', item.afterStatus)}</td>

					<td align="center">${item.description}</td>

					<td align="center">${item.logTime}</td>

				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
			
            <input
	            type="button" name="ba" class="button_class"
	            onclick="javascript:history.go(-1)"
	            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;">
			</div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>


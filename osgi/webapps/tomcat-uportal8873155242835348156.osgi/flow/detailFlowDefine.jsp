<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="流程定义" />
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
<form name="formEntry">
<p:navigation height="22">
	<td width="550" class="navigation">流程定义明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>流程定义信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:table cells="2">
			<p:cell title="流程名称">
			${bean.name}
			</p:cell>

			<p:cell title="制作时间">
			${bean.logTime}
			</p:cell>

			<p:cell title="模式">
			${my:get('flowMode', bean.mode)}
			</p:cell>
			
			<p:cell title="类型">
            ${my:get('parentType', bean.parentType)}
            </p:cell>

			<p:cells title="状态" celspan="2">
			${my:get('flowStatus', bean.status)}
			</p:cells>
			
			<p:cells title="模板" celspan="2">
            <c:forEach items="${templateList}" var="item">
            <a title="查看流程模板" href="../flow/template.do?method=findTemplateFile&id=${item.id}">${item.name}<a>&nbsp;&nbsp;
            </c:forEach>
            </p:cells>

			<p:cells celspan="2" title="备注">
			${bean.description}
			</p:cells>
			
			<p:cells celspan="2" title="流程查阅">
			<c:forEach items="${viewList}" var="item">
			查阅类型:${item.processerType} 查阅者:${item.processerName} <br>
			</c:forEach>
			</p:cells>
		</p:table>
	</p:subBody>

	<p:tr />

	<p:subBody width="100%">
		<table width="100%" border="0" cellspacing='1' id="tables">
			<tr align="center" class="content0">
				<td width="5%" align="center">顺序</td>
				<td width="10%" align="center">环节名称</td>
				<td width="15%" align="center">提交模式</td>
				<td width="15%" align="center">下一环处理者</td>
				<td width="20%" align="center">模板配置</td>
				<td width="25%" align="center">高级操作</td>
			</tr>

			<c:forEach items="${bean.tokenVOs}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center">${item.orders + 1}</td>

                    <c:if test="${item.type == 0}">
					<td align="center">${item.name}</td>
					</c:if>
					
					<c:if test="${item.type == 1}">
                    <td align="center"><a href="../flow/flow.do?method=findFlowDefine&id=${item.subFlowId}" title="点击查询子流程">${item.name}</a></td>
                    </c:if>
                    
                    
					
					<td align="center">${item.handleVOs[0].processType}${item.pluginType == 998 ? '子流程插件' : ''}</td>
					<td align="center">${item.handleVOs[0].processName}</td>
					<td align="center">
					<c:forEach items="${item.tempalteVOs}" var="iitem">
					${iitem.templateName}：<input type="checkbox" ${iitem.viewTemplate == 0 ? "" : "checked=checked"}>查看&nbsp;&nbsp;
					<input type="checkbox" ${iitem.editTemplate == 0 ? "" : "checked=checked"}>编辑
					&nbsp;&nbsp;<br>
					</c:forEach>
					</td>
					<td align="center">
					<c:if test="${item.type == 0}">
					<input type="checkbox" ${item.operation.reject == 0 ? "" : "checked=checked"}>驳回到上一环节
					<c:if test="${subFlow}">
					&nbsp;&nbsp;<input type="checkbox" ${item.operation.rejectParent == 0 ? "" : "checked=checked"}>驳回到主流程的上一环节
					</c:if>
					&nbsp;&nbsp;<input type="checkbox" ${item.operation.rejectAll == 0 ? "" : "checked=checked"}>驳回到初始
					&nbsp;&nbsp;<input type="checkbox" ${item.operation.exends == 0 ? "" : "checked=checked"}>异常结束
					<br>
					阈值(金额):${item.operation.liminal}
					</c:if>
                    </td>
				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message/>
</p:body></form>
</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<c:forEach items="${bean.custBusiList}" var="item" varStatus="vs">
	<table id="tbl_busi_${vs.index}" class="table0" style="width: 100%;">
			<tr class="content2">
				<td width="15%"><strong>客户账号类型：</strong></td>
				<td width="35%">
					${my:get('accountType',item.custAccountType)}
				<c:if test="${item.valid==1}">
					<strong><font color=red size="5">废弃</font></strong>
					</c:if>
				</td>
				<td>客户账号开户行:</td>
				<td>${item.custAccountBank}</td>
			</tr>
			<tr class="content1">
				<td width="15%">客户账号开户名：</td>
				<td width="35%">${item.custAccountName}</td>
				<td width="15%">客户账号：</td>
				<td width="35%">${item.custAccount}</td>
			</tr>
			<tr class="content2">
				<td width="15%">我司账号类型：</td>
				<td width="35%">${my:get('accountType',item.myAccountType)}
				</td>
				<td width="15%">我司账号开户行：</td>
				<td width="35%">${item.myAccountBank}</td>
			</tr>
			<tr class="content1">
				<td width="15%">我司账号开户名：</td>
				<td width="35%">${item.myAccountName}</td>
				<td width="15%">我司账号：</td>
				<td width="35%">${item.myAccount}</td>
			</tr>
			<tr>
				<td background='/uportal/images/dot_line.gif' colspan='4'></td>
			</tr>
	</table>
</c:forEach>
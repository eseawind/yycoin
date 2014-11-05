<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<p:title>
        <td class="caption">
         <strong>业务信息对比</strong>
        </td>
    </p:title>

    <p:line flag="0" />

	<tr id="businessTR">
		<td colspan='2' align='center'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
					<table width="100%" border="0" cellspacing='1'>
					<tr align="center" class="content0">
						<td width="20%">项目</td>
						<td width="40%">更新前值("无"表新增)</td>
						<td width="40%">更新后值("无"表删除)</td>
					</tr>
					
					<c:forEach items="${bean.custBusiList}" var="item" varStatus="vs">
	
						<c:if test="${item.custAccountType != item.self.custAccountType}">
							<tr align="center" class="content1">
							<td width="15%">客户账号类型：</td>
							<td width="35%">${my:get('accountType',item.custAccountType)}</td>
							<td width="35%">${my:get('accountType',item.self.custAccountType)}</td>
						</tr>
						</c:if>
	
						<c:if test="${item.custAccountBank != item.self.custAccountBank}">
							<tr align="center" class="content1">
							<td width="15%">客户账号开户行：</td>
							<td width="35%">${item.custAccountBank}</td>
							<td width="35%">${item.self.custAccountBank}</td>
						</tr>
						</c:if>
						
						<c:if test="${item.custAccountName != item.self.custAccountName}">
							<tr align="center" class="content1">
							<td width="15%">客户账号开户名：</td>
							<td width="35%">${item.custAccountName}</td>
							<td width="35%">${item.self.custAccountName}</td>
						</tr>
						</c:if>
	
						<c:if test="${item.custAccount != item.self.custAccount}">
							<tr align="center" class="content1">
							<td width="15%">客户账号：</td>
							<td width="35%">${item.custAccount}</td>
							<td width="35%">${item.self.custAccount}</td>
						</tr>
						</c:if>
						
						<c:if test="${item.myAccountType != item.self.myAccountType}">
							<tr align="center" class="content1">
							<td width="15%">我司账号类型：</td>
							<td width="35%">${my:get('accountType',item.myAccountType)}</td>
							<td width="35%">${my:get('accountType',item.self.myAccountType)}</td>
						</tr>
						</c:if>
						
						<c:if test="${item.myAccountBank != item.self.myAccountBank}">
							<tr align="center" class="content1">
							<td width="15%">我司账号开户行：</td>
							<td width="35%">${item.myAccountBank}</td>
							<td width="35%">${item.self.myAccountBank}</td>
						</tr>
						</c:if>
						
						<c:if test="${item.myAccountName != item.self.myAccountName}">
							<tr align="center" class="content1">
							<td width="15%">我司账号开户名：</td>
							<td width="35%">${item.myAccountName}</td>
							<td width="35%">${item.self.myAccountName}</td>
						</tr>
						</c:if>		
						
						<c:if test="${item.myAccount != item.self.myAccount}">
							<tr align="center" class="content1">
							<td width="15%">我司账号：</td>
							<td width="35%">${item.myAccount}</td>
							<td width="35%">${item.self.myAccount}</td>
						</tr>
						</c:if>						
						
						<tr>
							<td background='/uportal/images/dot_line.gif' colspan='3'></td>
						</tr>
						
					</c:forEach>
					
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>
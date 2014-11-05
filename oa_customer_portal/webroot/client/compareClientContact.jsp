<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<p:title>
        <td class="caption">
         <strong>联系信息对比</strong>
        </td>
    </p:title>

    <p:line flag="0" />

	<tr id="contactTR">
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
					
					<c:forEach items="${bean.custContList}" var="item" varStatus="vs">
						<c:if test="${item.name != item.self.name}">
							<tr align="center" class="content1">
							<td width="15%">联系人：</td>
							<td width="35%">${item.name}</td>
							<td width="35%">${item.self.name}</td>
						</tr>
						</c:if>
	
						<c:if test="${item.sex != item.self.sex}">
							<tr align="center" class="content1">
							<td width="15%">性别：</td>
							<td width="35%">${my:get('gender',item.sex)}</td>
							<td width="35%">${my:get('gender',item.self.sex)}</td>
						</tr>
						</c:if>
	
						<c:if test="${item.personal != item.self.personal}">
							<tr align="center" class="content1">
							<td width="15%">年龄段：</td>
							<td width="35%">${my:get('personal',item.personal)}</td>
							<td width="35%">${my:get('personal',item.self.personal)}</td>
						</tr>
						</c:if>
	
						<c:if test="${item.age != item.self.age}">
							<tr align="center" class="content1">
							<td width="15%">年龄：</td>
							<td width="35%">${item.age}</td>
							<td width="35%">${item.self.age}</td>
						</tr>
						</c:if>
						
						<c:if test="${item.birthday != item.self.birthday}">
							<tr align="center" class="content1">
							<td width="15%">生日：</td>
							<td width="35%">${item.birthday}</td>
							<td width="35%">${item.self.birthday}</td>
						</tr>
						</c:if>
	
						<c:if test="${item.handphone != item.self.handphone}">
							<tr align="center" class="content1">
							<td width="15%">手机：</td>
							<td width="35%">${item.handphone}</td>
							<td width="35%">${item.self.handphone}</td>
						</tr>
						</c:if>
	
						<c:if test="${item.tel != item.self.tel}">
							<tr align="center" class="content1">
							<td width="15%">固话：</td>
							<td width="35%">${item.tel}</td>
							<td width="35%">${item.self.tel}</td>
						</tr>
						</c:if>
						
						<c:if test="${item.email != item.self.email}">
							<tr align="center" class="content1">
							<td width="15%">电子邮箱：</td>
							<td width="35%">${item.email}</td>
							<td width="35%">${item.self.email}</td>
						</tr>
						</c:if>
	
						<c:if test="${item.qq != item.self.qq}">
							<tr align="center" class="content1">
							<td width="15%">QQ：</td>
							<td width="35%">${item.qq}</td>
							<td width="35%">${item.self.qq}</td>
						</tr>
						</c:if>
	
						<c:if test="${item.weibo != item.self.weibo}">
							<tr align="center" class="content1">
							<td width="15%">微博：</td>
							<td width="35%">${item.weibo}</td>
							<td width="35%">${item.self.weibo}</td>
						</tr>
						</c:if>
						
						<c:if test="${item.weixin != item.self.weixin}">
							<tr align="center" class="content1">
							<td width="15%">微信：</td>
							<td width="35%">${item.weixin}</td>
							<td width="35%">${item.self.weixin}</td>
						</tr>
						</c:if>
	
						<c:if test="${item.duty != item.self.duty}">
							<tr align="center" class="content1">
							<td width="15%">职务：</td>
							<td width="35%">${my:get('300',item.duty)}</td>
							<td width="35%">${my:get('300',item.self.duty)}</td>
						</tr>
						</c:if>
						
						<c:if test="${item.role != item.self.role}">
							<tr align="center" class="content1">
							<td width="15%">角色：</td>
							<td width="35%">${my:get('301',item.role)}</td>
							<td width="35%">${my:get('301',item.self.role)}</td>
						</tr>
						</c:if>						
						
						<c:if test="${item.reportTo != item.self.reportTo}">
							<tr align="center" class="content1">
							<td width="15%">汇报对象：</td>
							<td width="35%">${item.reportTo}</td>
							<td width="35%">${item.self.reportTo}</td>
						</tr>
						</c:if>
						
						<c:if test="${item.interest != item.self.interest}">
							<tr align="center" class="content1">
							<td width="15%">爱好：</td>
							<td width="35%">${item.interest}</td>
							<td width="35%">${item.self.interest}</td>
						</tr>
						</c:if>
						
						<c:if test="${item.relationship != item.self.relationship}">
							<tr align="center" class="content1">
							<td width="15%">关系程度：</td>
							<td width="35%">${my:get('relationShip',item.relationship)}</td>
							<td width="35%">${my:get('relationShip',item.self.relationship)}</td>
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
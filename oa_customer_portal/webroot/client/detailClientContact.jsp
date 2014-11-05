<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<c:forEach items="${bean.custContList}" var="item" varStatus="vs">
	<table id="tbl_linkman_${vs.index}" class="table0" style="width: 100%;">
			<tr class="content1">
				<td width="15%"><strong>姓名：</strong></td>
				<td width="35%">${item.name}
				<c:if test="${item.valid==1}">
					<strong><font color=red size="5">废弃</font></strong>
				</c:if>
				</td>
				<td width="15%">性别：</td>
				<td width="35%">
					<select id="sex1" class="select_class"  values="${item.sex}">
						<option value="0">男</option>
						<option value="1">女</option>
					</select> 
				</td>
			</tr>
			<tr class="content2">
				<td width="15%">年龄段：</td>
				<td width="35%"><select id="personal1" class="select_class" values="${item.personal}">
					<p:option type="personal"/>
				</select> </td>
				<td width="15%">年龄：</td>
				<td width="35%">${item.age}</td>
			</tr>
			<tr class="content1">
				<td width="15%">生日：</td>
				<td width="35%">
					${item.birthday}
				</td>
				<td width="15%">手机：</td>
				<td width="35%">${item.handphone}</td>
			</tr>
			<tr class="content2">
				<td width="15%">固话：</td>
				<td width="35%">${item.tel}</td>
				<td>邮箱：</td>
				<td>${item.email}</td>
			</tr>
			<tr class="content1">
				<td width="15%">QQ号码：</td>
				<td width="35%">${item.qq}</td>
				<td width="15%">微信：</td>
				<td width="35%">${item.weixin}</td>
			</tr>
			<tr class="content2">
				<td width="15%">微博：</td>
				<td width="35%">${item.weibo}</td>
				<td width="15%">职务：</td>
				<td width="35%">
				<select id="duty1" class="select_class"  readonly=readonly  values="${item.duty}">
					<p:option type="300" empty="true"></p:option>
				</select>
				</td>
			</tr>
			<tr class="content1">
				<td width="15%">汇报对象：</td>
				<td width="35%">${item.reportTo}</td>
				<td width="15%">爱好：</td>
				<td width="35%">${item.interest}</td>
			</tr>
			<tr class="content2">
				<td width="15%">关系程度：</td>
				<td width="35%">${my:get('relationShip',item.relationship)}</td>
				<td width="15%">角色</td>
				<td width="35%">
				<select id="role1" class="select_class"  readonly=readonly  values="${item.role}">
					<p:option type="301" empty="true"></p:option>
				</select>
				</td>
			</tr>
			
			<tr class="content1">
				<td width="15%">联系次数：</td>
				<td width="35%">${item.contactTimes}</td>
				<td width="15%">最近联系时间：</td>
				<td width="35%">${item.lastContactTime}</td>
			</tr>
			<tr class="content2">
				<td width="15%">备注：</td>
				<td width="85%" colspan="3"><c:out value="${item.description}"></c:out></td>
			</tr>
			<tr>
				<td background='/uportal/images/dot_line.gif' colspan='4'></td>
			</tr>
	</table>
</c:forEach>

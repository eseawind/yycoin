<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<tr id="mainTR">
		<td colspan='2' align='center'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr align="center" class="content0">
						<td width="20%">项目</td>
						<td width="40%">更新前值</td>
						<td width="40%">更新后值</td>
					</tr>
					
					<c:if test="${bean.name != newBean.name}">
						<tr align="center" class="content1">
						<td width="15%">客户名称：</td>
						<td width="35%">${bean.name}</td>
						<td width="35%">${newBean.name}</td>
					</tr>
					</c:if>

					<c:if test="${bean.simpleName != newBean.simpleName}">
						<tr align="center" class="content1">
						<td width="15%">简称：</td>
						<td width="35%">${bean.simpleName}</td>
						<td width="35%">${newBean.simpleName}</td>
					</tr>
					</c:if>
					
					<c:if test="${bean.provinceId != newBean.provinceId}">
						<tr align="center" class="content1">
						<td width="15%">省：</td>
						<td width="35%">${bean.provinceName}</td>
						<td width="35%">${newBean.provinceName}</td>
					</tr>
					</c:if>

					<c:if test="${bean.cityId != newBean.cityId}">
						<tr align="center" class="content1">
						<td width="15%">市：</td>
						<td width="35%">${bean.cityName}</td>
						<td width="35%">${newBean.cityName}</td>
					</tr>
					</c:if>

					<c:if test="${bean.areaId != newBean.areaId}">
						<tr align="center" class="content1">
						<td width="15%">区：</td>
						<td width="35%">${bean.areaName}</td>
						<td width="35%">${newBean.areaName}</td>
					</tr>
					</c:if>
					
					<c:if test="${bean.address != newBean.address}">
						<tr align="center" class="content1">
						<td width="15%">地址：</td>
						<td width="35%">${bean.address}</td>
						<td width="35%">${newBean.address}</td>
					</tr>
					</c:if>

					<c:if test="${bean.selltype != newBean.selltype}">
						<tr align="center" class="content1">
						<td width="15%">客户类型：</td>
						<td width="35%">${my:get('101',bean.selltype)}</td>
						<td width="35%">${my:get('101',newBean.selltype)}</td>
					</tr>
					</c:if>

					<c:if test="${bean.protype != newBean.protype}">
						<tr align="center" class="content1">
						<td width="15%">客户分类1：</td>
						<td width="35%">${my:get('102',bean.protype)}</td>
						<td width="35%">${my:get('102',newBean.protype)}</td>
					</tr>
					</c:if>
					
					<c:if test="${bean.protype2 != newBean.protype2}">
						<tr align="center" class="content1">
						<td width="15%">客户分类2：</td>
						<td width="35%">${my:get('103',bean.protype2)}</td>
						<td width="35%">${my:get('103',newBean.protype2)}</td>
					</tr>
					</c:if>

					<c:if test="${bean.qqtype != newBean.qqtype}">
						<tr align="center" class="content1">
						<td width="15%">客户等级：</td>
						<td width="35%">${my:get('104',bean.qqtype)}</td>
						<td width="35%">${my:get('104',newBean.qqtype)}</td>
					</tr>
					</c:if>

					<c:if test="${bean.rtype != newBean.rtype}">
						<tr align="center" class="content1">
						<td width="15%">开发进程：</td>
						<td width="35%">${my:get('105',bean.rtype)}</td>
						<td width="35%">${my:get('105',newBean.rtype)}</td>
					</tr>
					</c:if>
					
					<c:if test="${bean.fromType != newBean.fromType}">
						<tr align="center" class="content1">
						<td width="15%">客户来源：</td>
						<td width="35%">${my:get('106',bean.fromType)}</td>
						<td width="35%">${my:get('106',newBean.fromType)}</td>
					</tr>
					</c:if>

					<c:if test="${bean.industry != newBean.industry}">
						<tr align="center" class="content1">
						<td width="15%">行业：</td>
						<td width="35%">${my:get('108',bean.industry)}</td>
						<td width="35%">${my:get('108',newBean.industry)}</td>
					</tr>
					</c:if>

					<c:if test="${bean.introducer != newBean.introducer}">
						<tr align="center" class="content1">
						<td width="15%">介绍人：</td>
						<td width="35%">${bean.introducer}</td>
						<td width="35%">${newBean.introducer}</td>
					</tr>
					</c:if>

					<c:if test="${bean.sex != newBean.sex}">
						<tr align="center" class="content1">
						<td width="15%">性别：</td>
						<td width="35%">${my:get('gender',bean.sex)}</td>
						<td width="35%">${my:get('gender',newBean.sex)}</td>
					</tr>
					</c:if>

					<c:if test="${bean.personal != newBean.personal}">
						<tr align="center" class="content1">
						<td width="15%">年龄段：</td>
						<td width="35%">${my:get('personal',bean.personal)}</td>
						<td width="35%">${my:get('personal',newBean.personal)}</td>
					</tr>
					</c:if>

					<c:if test="${bean.age != newBean.age}">
						<tr align="center" class="content1">
						<td width="15%">年龄：</td>
						<td width="35%">${bean.age}</td>
						<td width="35%">${newBean.age}</td>
					</tr>
					</c:if>
					
					<c:if test="${bean.birthday != newBean.birthday}">
						<tr align="center" class="content1">
						<td width="15%">生日：</td>
						<td width="35%">${bean.birthday}</td>
						<td width="35%">${newBean.birthday}</td>
					</tr>
					</c:if>

					<c:if test="${bean.handphone != newBean.handphone}">
						<tr align="center" class="content1">
						<td width="15%">手机：</td>
						<td width="35%">${bean.handphone}</td>
						<td width="35%">${newBean.handphone}</td>
					</tr>
					</c:if>

					<c:if test="${bean.tel != newBean.tel}">
						<tr align="center" class="content1">
						<td width="15%">固话：</td>
						<td width="35%">${bean.tel}</td>
						<td width="35%">${newBean.tel}</td>
					</tr>
					</c:if>
					
					<c:if test="${bean.email != newBean.email}">
						<tr align="center" class="content1">
						<td width="15%">电子邮箱：</td>
						<td width="35%">${bean.email}</td>
						<td width="35%">${newBean.email}</td>
					</tr>
					</c:if>

					<c:if test="${bean.qq != newBean.qq}">
						<tr align="center" class="content1">
						<td width="15%">QQ：</td>
						<td width="35%">${bean.qq}</td>
						<td width="35%">${newBean.qq}</td>
					</tr>
					</c:if>

					<c:if test="${bean.weibo != newBean.weibo}">
						<tr align="center" class="content1">
						<td width="15%">微博：</td>
						<td width="35%">${bean.weibo}</td>
						<td width="35%">${newBean.weibo}</td>
					</tr>
					</c:if>
					
					<c:if test="${bean.weixin != newBean.weixin}">
						<tr align="center" class="content1">
						<td width="15%">微信：</td>
						<td width="35%">${bean.weixin}</td>
						<td width="35%">${newBean.weixin}</td>
					</tr>
					</c:if>

					<c:if test="${bean.duty != newBean.duty}">
						<tr align="center" class="content1">
						<td width="15%">职务：</td>
						<td width="35%">${my:get('300',bean.duty)}</td>
						<td width="35%">${my:get('300',newBean.duty)}</td>
					</tr>
					</c:if>
					
					<c:if test="${bean.reportTo != newBean.reportTo}">
						<tr align="center" class="content1">
						<td width="15%">汇报对象：</td>
						<td width="35%">${bean.reportTo}</td>
						<td width="35%">${newBean.reportTo}</td>
					</tr>
					</c:if>
					
					<c:if test="${bean.interest != newBean.interest}">
						<tr align="center" class="content1">
						<td width="15%">爱好：</td>
						<td width="35%">${bean.interest}</td>
						<td width="35%">${newBean.interest}</td>
					</tr>
					</c:if>
					
					<c:if test="${bean.relationship != newBean.relationship}">
						<tr align="center" class="content1">
						<td width="15%">关系程度：</td>
						<td width="35%">${my:get('relationShip',bean.relationship)}</td>
						<td width="35%">${my:get('relationShip',newBean.relationship)}</td>
					</tr>
					</c:if>					

				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>
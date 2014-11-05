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

					<c:if test="${bean.people != newBean.people}">
						<tr align="center" class="content1">
						<td width="15%">部门人数：</td>
						<td width="35%">${bean.people}</td>
						<td width="35%">${newBean.people}</td>
						</tr>
					</c:if>

					<c:if test="${bean.departType != newBean.departType}">
						<tr align="center" class="content1">
						<td width="15%">部门性质：</td>
						<td width="35%">${my:get('302',bean.departType)}</td>
						<td width="35%">${my:get('302',newBean.departType)}</td>
						</tr>
					</c:if>

				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>
<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<p:title>
        <td class="caption">
         <strong>地址信息对比</strong>
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
					
					<c:forEach items="${bean.custAddrVOList}" var="item" varStatus="vs">
						<c:if test="${item.provinceId != item.self.provinceId}">
							<tr align="center" class="content1">
							<td width="15%">联系人：</td>
							<td width="35%">${item.provinceName}</td>
							<td width="35%">${item.self.provinceName}</td>
						</tr>
						</c:if>
	
						<c:if test="${item.cityId != item.self.cityId}">
						<tr align="center" class="content1">
						<td width="15%">市：</td>
						<td width="35%">${item.cityName}</td>
						<td width="35%">${item.self.cityName}</td>
						</tr>
						</c:if>
	
						<c:if test="${item.areaId != item.self.areaId}">
							<tr align="center" class="content1">
							<td width="15%">区：</td>
							<td width="35%">${item.areaName}</td>
							<td width="35%">${item.self.areaName}</td>
						</tr>
						</c:if>
						
						<c:if test="${item.address != item.self.address}">
							<tr align="center" class="content1">
							<td width="15%">地址：</td>
							<td width="35%">${item.address}</td>
							<td width="35%">${item.self.address}</td>
						</tr>
						</c:if>
						
						<c:if test="${item.contact != item.self.contact}">
							<tr align="center" class="content1">
							<td width="15%">收货人：</td>
							<td width="35%">${item.contact}</td>
							<td width="35%">${item.self.contact}</td>
						</tr>
						</c:if>						
						
						<c:if test="${item.telephone != item.self.telephone}">
							<tr align="center" class="content1">
							<td width="15%">收货人电话：</td>
							<td width="35%">${item.telephone}</td>
							<td width="35%">${item.self.telephone}</td>
						</tr>
						</c:if>
	
						<c:if test="${item.atype != item.self.atype}">
							<tr align="center" class="content1">
							<td width="15%">地址性质：</td>
							<td width="35%">${my:get('303',item.atype)}
							</td>
							<td width="35%">${my:get('303',item.self.atype)}
							</td>
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
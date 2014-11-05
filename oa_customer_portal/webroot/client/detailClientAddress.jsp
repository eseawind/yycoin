<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<c:forEach items="${bean.custAddrVOList}" var="item" varStatus="vs">
	<table id="tbl_busi_${vs.index}" class="table0" style="width: 100%;">
			<tr class="content1">
				<td width="15%"><strong>地址：</strong></td>
				<td width="85%" colspan="3">${item.fullAddress}
					<c:if test="${item.valid==1}">
					<strong><font color=red size="5">废弃</font></strong>
					</c:if>
				</td>
			</tr>
			
			<tr class="content2">
				<td width="15%">收货人：</td>
				<td width="35%">${item.contact}</td>
				<td width="15%">收货人电话：</td>
				<td width="35%">${item.telephone}</td>
			</tr>
			<tr class="content1">
				<td width="15%">地址性质：</td>
				<td width="35%">
					<select id="atype" class="select_class"  values="${item.atype}">
						<p:option type="303"></p:option>
					</select>					
				</td>
				<td width="15%"></td>
				<td width="35%"></td>
			</tr>
			<tr>
				<td background='/uportal/images/dot_line.gif' colspan='4'></td>
			</tr>
	</table>
</c:forEach>
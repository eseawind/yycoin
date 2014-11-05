<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>


	<table width="100%" border="0" cellspacing='1' id="tables2">
			<tr align="center" class="content0">
				<td width="30%" align="center">送货地址</td>
				<td width="8%" align="center">联系人</td>
				<td width="10%" align="center">联系人电话</td>
				<td width="8%" align="center"><input type="button" accesskey="A"
					value="增加" class="button_class" onclick="opAddrDlg();"></td>
			</tr>
			
			<c:forEach items="${bean.custAddrVOList}" var="item" varStatus="vs">
				<tr class="content1">
					<td>
					<input type="text" name="p_fullAddress"
					readonly="readonly" maxlength="200" value="${item.fullAddress}"
					style="width: 100%;">
					<input type="hidden" name="p_id2" value="${item.id}">
					<input type="hidden" name="p_provinceId2" value="${item.provinceId}">
					<input type="hidden" name="p_cityId2" value="${item.cityId}">
					<input type="hidden" name="p_areaId2" value="${item.areaId}">
					<input type="hidden" name="p_address2" value="${item.address}">
					<input type="hidden" name="p_atype" value="${item.atype}">
					</td>
	
				<td align="center"><input type="text"
					style="width: 100%" maxlength="20" readonly="readonly" name="p_contact" value="${item.contact}"></td>
	
				<td align="center"><input type="text"
					style="width: 100%" maxlength="13" readonly="readonly" name="p_telephone" value="${item.telephone}"></td>
					
				<td align="center"><input type=button value="修改"  class="button_class" onclick="editAddr(this)">|
				<input type=button value="删除"  class="button_class" onclick="removeTr(this)"></td>
				</tr>
			</c:forEach>
			
			<tr class="content1" id="trCopy2" style="display: none;">
				<td>
				<input type="text" name="p_fullAddress"
				readonly="readonly" maxlength="200"
				style="width: 100%;">
				<input type="hidden" name="p_id2" value="">
				<input type="hidden" name="p_provinceId2" value="">
				<input type="hidden" name="p_cityId2" value="">
				<input type="hidden" name="p_areaId2" value="">
				<input type="hidden" name="p_address2" value="">
				<input type="hidden" name="p_atype" value="">
				</td>

			<td align="center"><input type="text"
				style="width: 100%" maxlength="20" readonly="readonly" name="p_contact"></td>

			<td align="center"><input type="text"
				style="width: 100%" maxlength="13" readonly="readonly" name="p_telephone"></td>
				
			<td align="center"><input type=button value="修改"  class="button_class" onclick="editAddr(this)">|
			<input type=button value="删除"  class="button_class" onclick="removeTr(this)"></td>
		</tr>
			
		</table>
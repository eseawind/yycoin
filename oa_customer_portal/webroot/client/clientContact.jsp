<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<table width="100%" border="0" cellspacing='1' id="tables">
		<tr align="center" class="content0">
			<td width="15%" align="center">姓名</td>
			<td width="15%" align="center">手机</td>
			<td width="15%" align="center">固话</td>
			<td width="14%" align="center">电子邮箱</td>
			<td width="10%" align="center">汇报对象</td>
			<td width="10%" align="center">爱好</td>
			<td width="8%" align="center"><input type="button" accesskey="A"
				value="增加" class="button_class" onclick="opLinkmanDlg()"></td>
		</tr>
		
		<c:forEach items="${bean.custContList}" var="item" varStatus="vs">
			<tr class="content1">
				<td>
				<input type="text" name="p_name1" value="${item.name}"
				readonly="readonly"
				style="width: 100%;">
				<input type="hidden" name="p_id1" value="${item.id}">
				<input type="hidden" name="p_sex1" value="${item.sex}">
				<input type="hidden" name="p_personal1" value="${item.personal}">
				<input type="hidden" name="p_relationship1" value="${item.relationship}">
				<input type="hidden" name="p_age1" value="${item.age}">
				<input type="hidden" name="p_birthday1" value="${item.birthday}">
				<input type="hidden" name="p_qq1" value="${item.qq}">
				<input type="hidden" name="p_weixin1" value="${item.weixin}">
				<input type="hidden" name="p_weibo1" value="${item.weibo}">
				<input type="hidden" name="p_description1" value="${item.description}">
				<input type="hidden" name="p_duty1" value="${item.duty}">
				<input type="hidden" name="p_role1" value="${item.role}">
				</td>

				<td align="center"><input type="text"
					style="width: 100%" maxlength="11" readonly="readonly" name="p_handphone1" value="${item.handphone}"></td>
		
				<td align="center"><input type="text"
					style="width: 100%" maxlength="13" readonly="readonly" name="p_tel1" value="${item.tel}"></td>
					
				<td align="center"><input type="text" style="width: 100%"  readonly="readonly"
		                        name="p_email1" value="${item.email}"></td>
		
				<td align="center"><input type="text" readonly="readonly"
					style="width: 100%" name="p_reportTo1" value="${item.reportTo}"></td>								
					
				<td align="center"><input type="text" readonly="readonly"
					style="width: 100%" name="p_interest1" value="${item.interest}"></td>
					
				<td align="center"><input type=button value="修改"  class="button_class" onclick="editLinkman(this)">|
				<input type=button value="删除"  class="button_class" onclick="removeTr(this)"></td>
			</tr>
		</c:forEach>
		
		<tr class="content1" id="trCopy" style="display: none;">
			<td>
			<input type="text" name="p_name1"
			readonly="readonly"
			style="width: 100%;">
			<input type="hidden" name="p_id1" value="">
			<input type="hidden" name="p_sex1" value="">
			<input type="hidden" name="p_personal1" value="">
			<input type="hidden" name="p_relationship1" value="">
			<input type="hidden" name="p_age1" value="">
			<input type="hidden" name="p_birthday1" value="">
			<input type="hidden" name="p_qq1" value="">
			<input type="hidden" name="p_weixin1" value="">
			<input type="hidden" name="p_weibo1" value="">
			<input type="hidden" name="p_description1" value="">
			<input type="hidden" name="p_duty1" value="">
			<input type="hidden" name="p_role1" value="">
			</td>

		<td align="center"><input type="text"
			style="width: 100%" maxlength="11" readonly="readonly" name="p_handphone1"></td>

		<td align="center"><input type="text"
			style="width: 100%" maxlength="13" readonly="readonly" name="p_tel1"></td>
			
		<td align="center"><input type="text" style="width: 100%"  readonly="readonly"
                        name="p_email1"></td>

		<td align="center"><input type="text" readonly="readonly"
			style="width: 100%" name="p_reportTo1"></td>								
			
		<td align="center"><input type="text" readonly="readonly"
			style="width: 100%" name="p_interest1"></td>
			
		<td align="center"><input type=button value="修改"  class="button_class" onclick="editLinkman(this)">|
		<input type=button value="删除"  class="button_class" onclick="removeTr(this)"></td>
	</tr>
		
</table>
<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>


	<table width="100%" border="0" cellspacing='1' id="tables3">
			<tr align="center" class="content0">
				<td width="15%" align="center">客户-开户行</td>
				<td width="15%" align="center">客户-开户名</td>
				<td width="15%" align="center">客户-开户号</td>
				<td width="15%" align="center">公司-开户行</td>
				<td width="15%" align="center">公司-开户名</td>
				<td width="15%" align="center">公司-开户号</td>
				<td width="8%" align="center"><input type="button" accesskey="A"
					value="增加" class="button_class" onclick="opBusiDlg();"></td>
			</tr>
			
			<c:forEach items="${bean.custBusiList}" var="item" varStatus="vs">
				<tr class="content1">
					<td>
					<input type="text" name="p_custAccountBank"
					readonly="readonly" maxlength="200" value="${item.custAccountBank}"
					style="width: 100%;">
					<input type="hidden" name="p_id3" value="${item.id}">
					<input type="hidden" name="p_custAccountType" value="${item.custAccountType}">
					<input type="hidden" name="p_myAccountType" value="${item.myAccountType}">
					</td>
	
				<td align="center"><input type="text"
					readonly="readonly" style="width: 100%" maxlength="100" name="p_custAccountName" value="${item.custAccountName}"></td>
					
				<td align="center"><input type="text"
					readonly="readonly" style="width: 100%" maxlength="100" name="p_custAccount" value="${item.custAccount}"></td>
	
				<td align="center"><input type="text" readonly="readonly" maxlength="100"
					style="width: 100%" name="p_myAccountBank" value="${item.myAccountBank}"></td>								
					
				<td align="center"><input type="text" readonly="readonly" maxlength="100"
					style="width: 100%" name="p_myAccountName" value="${item.myAccountName}"></td>	
					
				<td align="center"><input type="text" readonly="readonly" maxlength="100"
					style="width: 100%" name="p_myAccount" value="${item.myAccount}"></td>
					
				<td align="center"><input type=button value="修改"  class="button_class" onclick="editBusi(this)">|
				<input type=button value="删除"  class="button_class" onclick="removeTr(this)"></td>
				</tr>
			</c:forEach>
			
			<tr class="content1" id="trCopy3" style="display: none;">
				<td>
				<input type="text" name="p_custAccountBank"
				readonly="readonly" maxlength="200"
				style="width: 100%;">
				<input type="hidden" name="p_id3" value="">
				<input type="hidden" name="p_custAccountType" value="">
				<input type="hidden" name="p_myAccountType" value="">
				</td>

			<td align="center"><input type="text"
				readonly="readonly" style="width: 100%" maxlength="100" name="p_custAccountName"></td>

			<td align="center"><input type="text"
					readonly="readonly" style="width: 100%" maxlength="100" name="p_custAccount"></td>

			<td align="center"><input type="text" readonly="readonly" maxlength="100"
				style="width: 100%" name="p_myAccountBank"></td>								
				
			<td align="center"><input type="text" readonly="readonly" maxlength="100"
				style="width: 100%" name="p_myAccountName"></td>	
				
			<td align="center"><input type="text" readonly="readonly" maxlength="100"
					style="width: 100%" name="p_myAccount"></td>					
				
			<td align="center"><input type=button value="修改"  class="button_class" onclick="editBusi(this)">|
			<input type=button value="删除"  class="button_class" onclick="removeTr(this)"></td>
		</tr>
			
		</table>
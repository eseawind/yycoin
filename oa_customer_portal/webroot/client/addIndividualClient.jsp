<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

	<p:class value="com.china.center.oa.client.bean.CustomerIndividualApplyBean" />

	<p:table cells="2">
		<p:pro field="name" innerString="size=60" cell="2"/>
		<p:pro field="simpleName" innerString="size=40" cell="2"/>
		
		<p:pro field="provinceId"
			innerString="quick=true onchange=changes(this)" />
		<p:pro field="cityId"
			innerString="quick=true onchange=changeArea()" />
			
		<p:pro field="areaId" innerString="quick=true" cell="2"/>
		
		<p:pro field="address" innerString="size=120" cell="2"/>
		
		<p:pro field="selltype" innerString="quick=true">
			<p:option type="101" empty="true"></p:option>
		</p:pro>
		<p:pro field="protype" innerString="quick=true">
			<p:option type="102" empty="true"></p:option>
		</p:pro>
		<p:pro field="protype2" innerString="quick=true">
			<p:option type="103" empty="true"></p:option>
		</p:pro>
		<p:pro field="qqtype" innerString="quick=true">
			<p:option type="104" empty="true"></p:option>
		</p:pro>
		
		<p:pro field="sex">
			<option value="">--</option>
			<option value="0">男</option>
			<option value="1">女</option>
		</p:pro>
		
		<p:pro field="personal" >
			<p:option type="personal" empty="true"></p:option>
		</p:pro>
		<p:pro field="birthday" innerString="onblur='blu(this)'"/>
		
		<p:pro field="handphone" />
		<p:pro field="tel" />
		
		<p:pro field="email"/>
		<p:pro field="qq" />
		
		<p:pro field="weibo" />
		<p:pro field="weixin" />
		
		<p:cell title="所属部门">
			<input type="text" name="refDepartCustName" value=""
							onclick="selectRelateCustomer(2)" style="cursor: pointer;"
							readonly="readonly">
		</p:cell>

		<p:cell title="所属组织">
			<input type="text" name="refCorpCustName" value=""
							onclick="selectRelateCustomer(3)" style="cursor: pointer;"
							readonly="readonly">
		</p:cell>
				
		<p:pro field="duty">
			<p:option type="300" empty="true"></p:option>
		</p:pro>
		<p:pro field="reportTo"></p:pro>
		
		<p:pro field="interest"></p:pro>
		<p:pro field="relationship">
			<p:option type="relationShip" empty="true"></p:option>
		</p:pro>
		
		<p:pro field="rtype" innerString="quick=true">
			<p:option type="105" empty="true"></p:option>
		</p:pro>
		<p:pro field="fromType" innerString="quick=true">
			<p:option type="106" empty="true"></p:option>
		</p:pro>
		
		<p:pro field="introducer"/>
		<p:pro field="industry" innerString="quick=true" cell="0">
			<p:option type="108" empty="true"></p:option>
		</p:pro>
		
         <p:cell title="曾用名" end="true">
         <textarea name='formerCustName'
				id='formerCustName' rows=2 cols=60 readonly=readonly></textarea>
				<input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectFormerCust(this)">&nbsp;&nbsp;
                <input type="button" value="清空" name="qout" id="qout"
					class="button_class" onclick="clears()">&nbsp;&nbsp;
         </p:cell>		
		
		<p:pro field="description" cell="0" innerString="rows=2 cols=60" />

	</p:table>

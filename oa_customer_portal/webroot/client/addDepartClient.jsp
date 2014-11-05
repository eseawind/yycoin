<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

	<p:class value="com.china.center.oa.client.bean.CustomerDepartApplyBean" />

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
			<p:option type="101"></p:option>
		</p:pro>
		<p:pro field="protype" innerString="quick=true">
			<p:option type="102"></p:option>
		</p:pro>
		<p:pro field="protype2" innerString="quick=true">
			<p:option type="103"></p:option>
		</p:pro>
		<p:pro field="qqtype" innerString="quick=true">
			<p:option type="104"></p:option>
		</p:pro>
		
		<p:cell title="所属组织" end="true">
			<input type="text" name="refCorpCustName" value=""
							onclick="selectRelateCustomer(3)" style="cursor: pointer;"
							readonly="readonly">
		</p:cell>
		
		<p:pro field="people"></p:pro>
		<p:pro field="departType">
			<p:option type="302" empty="true"></p:option>
		</p:pro>
		
		<p:pro field="rtype" innerString="quick=true">
			<p:option type="105"></p:option>
		</p:pro>
		<p:pro field="fromType" innerString="quick=true">
			<p:option type="106"></p:option>
		</p:pro>
		
		<p:pro field="introducer"/>
		<p:pro field="industry" innerString="quick=true">
			<p:option type="108"></p:option>
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

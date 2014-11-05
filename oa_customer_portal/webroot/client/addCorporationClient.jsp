<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

	<p:class value="com.china.center.oa.client.bean.CustomerCorporationApplyBean" />

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
		
		<p:pro field="licenseNo" />
		<p:pro field="registryMoney" />
		
		<p:pro field="registryAddress" innerString="size=120" cell="2"/>
		<p:pro field="businessaddress" innerString="size=120" cell="2"/>
		
		<p:pro field="assets" />
		<p:pro field="establishDate" />
		
		<p:pro field="lastYearSales" />
		<p:pro field="thisYearSales" />
		
		<p:pro field="employeeAmount" cell="2" />
		
		<p:pro field="sellerAmount" />
		<p:pro field="saleArea"/>
		
		<p:pro field="agent"></p:pro>
		<p:pro field="agentArea"></p:pro>
		
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

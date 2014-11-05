<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

	<p:class value="com.china.center.oa.client.bean.CustomerIndividualBean" opr="2"/>

	<p:table cells="2" tableClass="table0">
		<p:pro field="name" innerString="size=60" cell="2"/>
		<p:pro field="simpleName" innerString="size=40" cell="2"/>
		
        <p:cell title="  省">${bean.provinceName}</p:cell>
            
        <p:cell title="地市">${bean.cityName}</p:cell>
        
        <p:cell title="县区" end="true">${bean.areaName}</p:cell>
		
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
		
		<p:pro field="sex">
			<option value="0">男</option>
			<option value="1">女</option>
		</p:pro>
		
		<p:pro field="personal" >
			<p:option type="personal"></p:option>
		</p:pro>
		
		<p:pro field="birthday" />
		
		<p:pro field="handphone" />
		<p:pro field="tel" />
		
		<p:pro field="email" />
		<p:pro field="qq" />
		
		<p:pro field="weibo" />
		<p:pro field="weixin" />
		
		<p:cell title="所属组织">${bean.refCorpCustName}</p:cell>
		<p:cell title="所属部门">${bean.refDepartCustName}</p:cell>
		
		<p:pro field="duty"></p:pro>
		<p:pro field="reportTo"></p:pro>
		
		<p:pro field="interest"></p:pro>
		<p:pro field="relationship">
			<p:option type="relationShip" empty="true"></p:option>
		</p:pro>
		
		<p:pro field="rtype" innerString="quick=true">
			<p:option type="105"></p:option>
		</p:pro>
		<p:pro field="fromType" innerString="quick=true">
			<p:option type="106"></p:option>
		</p:pro>
		
		<p:pro field="introducer"/>
		<p:pro field="industry" innerString="quick=true" cell="0">
			<p:option type="108"></p:option>
		</p:pro>
		
		<p:pro field="historySales"></p:pro>
		<p:pro field="salesAmount"></p:pro>
		
		<p:pro field="contactTimes"></p:pro>
		<p:pro field="lastContactTime"></p:pro>
		
		<p:pro field="formerCust" cell="0" innerString="readonly=true rows=2 cols=60" value="${bean.formerCustName}">
		</p:pro>
		
		<p:pro field="description" cell="0" innerString="rows=2 cols=60" />
		
	    <c:if test="${apply == true}">
	            <p:cell title="审批意见" end="true">
	               <c:out value="${reson}"/>
	            </p:cell>
        </c:if>
	    
		 <p:cell title="所属职员" end="true">
	                  <c:out value="${vs.stafferName}"/>
	     </p:cell>

	</p:table>

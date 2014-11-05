<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="回访处理" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../feedback_js/feedback.js"></script>
<script language="javascript">

function addBean(param)
{
	$O('status').value = param;

	submit('确定提交该回访?', null, visitCheck);
}

function load()
{
	loadForm();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../customerService/feedback.do" method="post">
	<input type="hidden" name="method" value="addFeedBackVisit">
	<input type="hidden" name="id" value="${bean.id}">
	<input type="hidden" name="exceptionProcesser" value="${bean.exceptionProcesser}">
	<input type="hidden" name="taskId" value="${feedBack.id}">
	<input type="hidden" name="status" value="">	
	
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">回访管理</span> &gt;&gt; 任务处理</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>回访信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="2">
			<p:cell title="任务编号">
             	${feedBack.id}
            </p:cell>
            <p:cell title="任务类型">
             	${my:get('feedbackType', feedBack.type)}
            </p:cell>
            <p:cell title="客户">
             	${feedBack.customerName}
            </p:cell>
            <p:cell title="业务员">
             	${feedBack.stafferName}
            </p:cell>
            <p:cell title="最近发货时间">
             	${feedBack.changeTime}
            </p:cell>
            <p:cell title="负责人">
             	${feedBack.bearName}
            </p:cell>            
		</p:table>
	</p:subBody>

	<p:line flag="0" />
	
	<tr id="product_tr" >
		<td colspan='2' align='center'>
		<table width="98%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables">
					<tr align="center" class="content0">
						<td width="20%" align="center">产品</td>
						<td width="6%" align="center">数量</td>
						<td width="8%" align="center">金额(合计:${my:formatNum(total)})</td>
						<td width="8%" align="center">是否已收货</td>
						<td width="10%" align="center">收货时间</td>
						<td width="8%" align="center">收货是否有异常</td>
						<td width="10%" align="center">异常数量</td>
						<td width="10%" align="center">异常类型</td>
						<td width="30%" align="center">异常情况描述</td>
					</tr>

					<c:if test="${update}">
					<c:forEach items="${detailList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center" onclick="taskDetail('${feedBack.id}','${item.productId}')" style="color:#008AC0;text-decoration:underline;cursor: pointer;">${item.productName}
                             	<input type="hidden" name="p_productId" value="${item.productId}"> 
				                <input type="hidden" name="p_productName" value="${item.productName}">
				                <input type="hidden" name="p_amount" value="${item.amount}">
				                <input type="hidden" name="p_money" value="${item.money}">
                            </td>
                            <td  align="center">${item.amount}</td>
                            <td  align="center">${my:formatNum(item.money)}</td>
                            
                            <td align="left">
					         <select name="p_receive" class="select_class" style="width: 100%;" oncheck="notNone" values="${item.receive}">
					            <p:option type="receive" empty="true"></p:option>
					         </select>
					         </td>
			         
					         <td align="left">
					         <input type=text name = 'p_receiptTime'  id = 'p_receiptTime'  value="${item.receiptTime}"
					         readonly=readonly class='input_class' size = '20' ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "p_receiptTime");' height='20px' width='20px'/>
					         </td>
			         
			                 <td align="left">
					         <select name="p_ifHasException" class="select_class" style="width: 100%;" values="${item.ifHasException}">
					            <p:option type="receiveException" empty="true"></p:option>
					         </select>
					         </td>
			         
					         <td align="left">
					         <input type="text" style="width: 100%"  name="p_exceptionAmount" value="${item.exceptionAmount}" oncheck="isNumber;range(0, ${item.amount})">
					         </td>
										         
				             <td align="left">
					         <select name="p_exceptionType" class="select_class" style="width: 100%;" values="${item.exceptionType}">
					            <p:option type="231" empty="true"></p:option>
					         </select>
					         </td>
			         
					         <td align="left">
					         <textarea name="p_exceptionText" rows="3" style="width: 100%"><c:out value="${item.exceptionText}" /></textarea>
					         </td>
                            
                        </tr>
                    </c:forEach>
					</c:if>
 					
					<c:if test="${!update}">
					<c:forEach items="${detailList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center" onclick="taskDetail('${feedBack.id}','${item.productId}')" style="color:#008AC0;text-decoration:underline;cursor: pointer;">${item.productName}
                             	<input type="hidden" name="p_productId" value="${item.productId}"> 
				                <input type="hidden" name="p_productName" value="${item.productName}">
				                <input type="hidden" name="p_amount" value="${item.amount}">
				                <input type="hidden" name="p_money" value="${item.money}">
                            </td>
                            <td  align="center">${item.amount}</td>
                            <td  align="center">${my:formatNum(item.money)}</td>
                            
                            <td align="left">
					         <select name="p_receive" class="select_class" style="width: 100%;" oncheck="notNone" >
					            <p:option type="receive" empty="true"></p:option>
					         </select>
					         </td>
			         
					         <td align="left">
					         <input type=text name = 'p_receiptTime'  id = 'p_receiptTime'  
					         readonly=readonly class='input_class' size = '20' ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "p_receiptTime");' height='20px' width='20px'/>
					         </td>
			         
			                 <td align="left">
					         <select name="p_ifHasException" class="select_class" style="width: 100%;" >
					            <p:option type="receiveException" empty="true"></p:option>
					         </select>
					         </td>
			         
					         <td align="left">
					         <input type="text" style="width: 100%"  name="p_exceptionAmount"  value="0" oncheck="isNumber;range(0, ${item.amount})">
					         </td>
										         
				             <td align="left">
					         <select name="p_exceptionType" class="select_class" style="width: 100%;" >
					            <p:option type="231" empty="true"></p:option>
					         </select>
					         </td>
			         
					         <td align="left">
					         <textarea name="p_exceptionText" rows="3" style="width: 100%"></textarea>
					         </td>
                            
                        </tr>
                    </c:forEach>
					</c:if>

				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button"
			class="button_class" id="ok_copy" style="cursor: pointer"
			value="&nbsp;&nbsp;复制第一行&nbsp;&nbsp;" onclick="copyValue()">
			</div>
	</p:button>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.customerservice.bean.FeedBackVisitBean" />

		<p:table cells="2">
			
			<p:pro field="exceptionProcesserName" innerString="readonly=true" value="${bean.exceptionProcesserName}">
                <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectStaffer()">&nbsp;&nbsp;
                    <input
						type="button" value="清空" name="qout1" id="qout1"
						class="button_class" onclick="clears(1)">&nbsp;&nbsp;
            </p:pro>
			
			<p:pro field="ifHasContact" value="${bean.ifHasContact}">
				<p:option type="hasConnect" empty="true"></p:option>
			</p:pro>
			
			<p:pro field="contact" innerString="readonly=true" value="${bean.contact}">
                <input type="button" value="更多" name="qout" id="qout"
                    class="button_class" onclick="selectReciever()">&nbsp;&nbsp;
            </p:pro>
			
			<p:pro field="contactPhone" value="${bean.contactPhone}"/>
			
			<p:pro field="planReplyDate" cell="0" value="${bean.planReplyDate}"/>
			
			<p:cell title="异常相关人" end="true">
            <textarea name='exceptRef'
					head='异常相关人' id="exceptRef" onclick='selectStaffer1()' rows=2 cols=80 style='cursor: pointer;' readonly=true>${bean.exceptRef}</textarea>
            <input
						type="button" value="清空" name="qout" id="qout"
						class="button_class" onclick="clears(0)">&nbsp;&nbsp;
            </p:cell>
			
			<p:pro field="description" cell="0" innerString="rows=2 cols=80" value="${bean.description}" />	
		</p:table>
	</p:subBody>
	
	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button"
			class="button_class" id="ok_s" style="cursor: pointer"
			value="&nbsp;&nbsp;保存&nbsp;&nbsp;" onclick="addBean(0)">&nbsp;&nbsp;<input type="button"
			class="button_class" id="ok_b" style="cursor: pointer"
			value="&nbsp;&nbsp;提交&nbsp;&nbsp;" onclick="addBean(1)"></div>
	</p:button>

	<p:message />
	
</p:body>
</form>

</body>
</html>
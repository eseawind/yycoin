<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="回访异常处理" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/jquery/jquery.js"></script>
<script language="JavaScript" src="../feedback_js/feedback.js"></script>
<script language="javascript">

function addBean(param)
{
	$O('status').value = param;

	submit('确定提交该回访异常?', null, visitCheck);
}

function load()
{
	loadForm();

	$detail($O('viewTable'), ['pr', 'ba', 'actualExceptionReason', 'resolve', 'resolveText']);
}

function selectStaffer()
{   
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&excludeQuit=1&load=1&selectMode=1');
}

function getStaffers(oos)
{
	 var oo = oos[0];

	 $O('exceptionProcesserName').value = oo.pname;
     $O('exceptionProcesser').value = oo.value;
     
}

function check()
{
	return true;
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../customerService/feedback.do" method="post">
	<input type="hidden" name="method" value="processFeedBackVisit">
	<input type="hidden" name="exceptionProcesser" value="${bean.exceptionProcesser}">
	<input type="hidden" name="taskId" value="${feedBack.id}">
	<input type="hidden" name="id" value="${bean.id}">
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
            <p:cell title="客户" width="8" end="true">
             	${feedBack.customerName}
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
						<td width="8%" align="center">金额</td>
						<td width="8%" align="center">是否已收货</td>
						<td width="10%" align="center">收货时间</td>
						<td width="8%" align="center">收货是否有异常</td>
						<td width="10%" align="center">异常数量</td>
						<td width="10%" align="center">异常类型</td>
						<td width="30%" align="center">异常情况描述</td>
					</tr>

 					<c:forEach items="${detailList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center" onclick="taskDetail('${feedBack.id}','${item.productId}')" style="color:#008AC0;text-decoration:underline;cursor: pointer;">${item.productName}
                            </td>
                            <td  align="left">${item.amount}</td>
                            <td  align="left">${my:formatNum(item.money)}</td>
                            
                            <td align="left">
                            	${my:get('receive', item.receive)}
					         </td>
			         
					         <td align="left">
					         ${item.receiptTime}
					         </td>
			         
			                 <td align="left">
			                 ${my:get('receiveException', item.ifHasException)}
					         </td>
			         
					         <td align="left">
					         ${item.exceptionAmount}
					         </td>
										         
				             <td align="left">
				             ${my:get('231', item.exceptionType)}
					         </td>
			         
					         <td align="left">
					         <c:out value="${item.exceptionText}" />
					         </td>
                            
                        </tr>
                    </c:forEach>

				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.customerservice.bean.FeedBackVisitBean" />

		<p:table cells="2" id="viewTable">
			
			<p:pro field="exceptionProcesserName" innerString="readonly=true" value="${bean.exceptionProcesserName}">
                <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectStaffer()">&nbsp;&nbsp;
            </p:pro>
			
			<p:pro field="ifHasContact" value="${bean.ifHasContact}">
				<p:option type="hasConnect" empty="true"></p:option>
			</p:pro>
			
			<p:pro field="contact" value="${bean.contact}">
			</p:pro>
			
			<p:pro field="contactPhone" value="${bean.contactPhone}"/>
			
			<p:pro field="planReplyDate" value="${bean.planReplyDate}"/>
			
			<p:pro field="actualExceptionReason" value="${bean.actualExceptionReason}">
				<p:option type="229" empty="true"></p:option>
			</p:pro>
			
			<p:pro field="resolve" cell="0" value="${bean.resolve}">
				<p:option type="230" empty="true"></p:option>
			</p:pro>

			<p:pro field="resolveText" cell="0" innerString="rows=2 cols=55" value="${bean.resolveText}"/>
			
			<p:pro field="description" cell="0" innerString="rows=2 cols=80" value="${bean.description}"/>			
			
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
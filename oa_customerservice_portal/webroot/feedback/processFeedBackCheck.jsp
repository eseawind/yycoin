<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="对账处理" />
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/jquery/jquery.js"></script>
<script language="JavaScript" src="../js/plugin/dialog/jquery.dialog.js"></script>
<script language="JavaScript" src="../feedback_js/feedback.js"></script>
<script language="javascript">

function addBean(param)
{
	$O('status').value = param;

	submit('确定提交该对账?', null, check);
}

function load()
{
	loadForm();

	$detail($O('viewTable'), ['pr', 'ba', 'actualExceptionReason', 'resolve', 'resolveText']);
}

function check()
{
	if ($$('ifPromiseReplyCheck')==1)
	{
		if ($$('planReplyDate') == '')
		{
			alert('计划回复时间须填写');

			return false;
		}
	}
    
    return true;
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../customerService/feedback.do" method="post">
	<input type="hidden" name="method" value="processFeedBackCheck">
	<input type="hidden" name="id" value="${bean.id}">
	<input type="hidden" name="taskId" value="${feedBack.id}">
	<input type="hidden" name="statsStar" value="${feedBack.statsStar}">
	<input type="hidden" name="statsEnd" value="${feedBack.statsEnd}">
	<input type="hidden" name="industryIdName" value="${feedBack.industryIdName}">
	<input type="hidden" name="status" value="">
	<input type="hidden" name="type" value="">
	<input type="hidden" name="mailAddress" value="">
	<input type="hidden" name="exceptionProcesser" value="${bean.exceptionProcesser}">
	
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">对账管理</span> &gt;&gt; 任务处理</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>对账信息：</strong></td>
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
            <p:cell title="业务员">
             	${feedBack.stafferName}
            </p:cell>
			<p:cell title="总金额">
             	${feedBack.moneys}
            </p:cell>
            <p:cell title="统计开始">
             	${feedBack.statsStar}
            </p:cell>
			<p:cell title="统计结束">
             	${feedBack.statsEnd}
            </p:cell>
            <p:cell title="事业部" width="8" end="true">
             	${feedBack.industryIdName}
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
						<td width="6%" align="center">总数量</td>
						<td width="6%" align="center">单价</td>
						<td width="8%" align="center">总金额(合计:${my:formatNum(total + backTotal)})</td>
						<td width="6%" align="center">退货数</td>
						<td width="6%" align="center">已回款数</td>
						<td width="6%" align="center">未付款数</td>
						<td width="6%" align="center">未付款额(合计:${my:formatNum(nopayTotal)})</td>
					</tr>

 					<c:forEach items="${detailList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center" onclick="taskDetail('${feedBack.id}','${item.productId}')" style="color:#008AC0;text-decoration:underline;cursor: pointer;">${item.productName}
                            </td>
                            <td  align="center">${item.amount + item.hasBack}</td>
                            <td  align="right">${my:formatNum(item.price)}</td>
                            <td  align="right">${my:formatNum(item.money + item.backMoney)}</td>
                            <td  align="center">${item.hasBack}</td>
                            <td  align="center">${item.amount - item.noPayAmount}</td>
                            <td  align="center">${item.noPayAmount}</td>
                            <td  align="right">${my:formatNum(item.noPayMoneys)}</td>                            
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
		<p:class value="com.china.center.oa.customerservice.bean.FeedBackCheckBean" />

		<p:table cells="2" id="viewTable">
			
			<p:pro field="contact" innerString="readonly=true" value="${bean.contact}">
                <input type="button" value="更多" name="qout" id="qout"
                    class="button_class" onclick="selectReciever()">&nbsp;&nbsp;
            </p:pro>			
			
			<p:pro field="contactPhone" innerString="size=30" value="${bean.contactPhone}"/>
			
			<p:pro field="ifPromiseReplyCheck" value="${bean.ifPromiseReplyCheck}">
				<p:option type="promiseReplyCheck" empty="true"></p:option>
			</p:pro>
			
			<p:pro field="planReplyDate" value="${bean.planReplyDate}"/>
			
			<p:pro field="ifSendConfirmFax" value="${bean.ifSendConfirmFax}">
				<p:option type="confirmFax" empty="true"></p:option>
			</p:pro>

			<p:pro field="ifReceiveConfirmFax" value="${bean.ifReceiveConfirmFax}">
				<p:option type="confirmFax" empty="true"></p:option>
			</p:pro>
			
			<p:pro field="checkResult" value="${bean.checkResult}">
				<p:option type="checkResult" empty="true"></p:option>
			</p:pro>
			
			<p:pro field="checkProcess" value="${bean.checkProcess}">
				<p:option type="checkProcess" empty="true"></p:option>
			</p:pro>
			
			<p:pro field="actualExceptionReason" value="${bean.actualExceptionReason}">
				<p:option type="229" empty="true"></p:option>
			</p:pro>
			
			<p:pro field="resolve" value="${bean.resolve}">
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

	<p:message2 />
	
</p:body>
</form>

</body>
</html>
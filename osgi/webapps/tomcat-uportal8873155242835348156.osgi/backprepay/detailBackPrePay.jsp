<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="申请预收退款" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../tcp_js/expense.js"></script>
<script language="javascript">
function load()
{

}

</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../tcp/expense.do"  method="post">
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="stafferId" value="${g_stafferBean.id}"> 
<input type="hidden" name="departmentId" value="${g_stafferBean.principalshipId}"> 

<p:navigation height="22">
	<td width="550" class="navigation">申请预收退款明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		 <strong>申请预收退款</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
	
	    <p:class value="com.china.center.oa.finance.bean.BackPrePayApplyBean" opr="2"/>
	    
		<p:table cells="2">
			<p:cell title="处理流程" width="8" end="true">
            ${bean.flowDescription}
            </p:cell>
            
            <p:pro field="id" cell="0"/>
            
            <p:cell title="申请人">
            ${bean.stafferName}
            </p:cell>
            
            <p:cell title="部门">
            ${bean.departmentName}
            </p:cell>
            
            <p:pro field="name" cell="0" innerString="size=60"/>
            
            <p:pro field="customerName" innerString="readonly='readonly'" cell="0">
            </p:pro>
            
            <p:pro field="payType" cell="0">
                <p:option type="outbillPayType" empty="true"></p:option>
            </p:pro>
            
            <p:pro field="receiver" cell="0"/>
            
            <p:pro field="receiveBank" innerString="size=40" cell="0"/>
            
            <p:pro field="receiveAccount" innerString="size=40" cell="0"/>
            
            <p:pro field="billId">
            </p:pro>
            
            <p:pro field="paymentId" />
            
            <p:pro field="total" value="${my:formatNum(bean.total / 100.0)}"/>
            
            <%-- <p:pro field="ownMoney" value="${my:formatNum(bean.ownMoney / 100.0)}"/> --%>
            
            <p:pro field="backMoney" value="${my:formatNum(bean.backMoney / 100.0)}"/>
            
            <p:pro field="customerAccount" innerString="readonly='readonly' size=40" cell="0"/>
            
            <p:pro field="bankName" innerString="readonly='readonly' size=40" cell="0"/>
            
            <p:pro field="description" cell="0" innerString="rows=4 cols=55" />
            
            <p:cell title="处理人" width="8" end="true">
            ${bean.processer}
            </p:cell>
            
            <p:cell title="附件" width="8" end="true">
            <c:forEach items="${bean.attachmentList}" var="item">
            <a href="../tcp/backprepay.do?method=downAttachmentFile&id=${item.id}" title="点击下载附件">${item.name}</a>
            <br>
            <br>
            </c:forEach>
            </p:cell>
            
        </p:table>
	</p:subBody>
	
    <p:line flag="0" />
    
    <p:title>
        <td class="caption">
         <strong>流程日志</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr id="flowLog">
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="10%" align="center">审批人</td>
                        <td width="10%" align="center">审批动作</td>
                        <td width="10%" align="center">前状态</td>
                        <td width="10%" align="center">后状态</td>
                        <td width="45%" align="center">意见</td>
                        <td width="15%" align="center">时间</td>
                    </tr>

                    <c:forEach items="${logList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center">${item.actor}</td>

                            <td  align="center">${item.oprModeName}</td>

                            <td  align="center">${item.preStatusName}</td>

                            <td  align="center">${item.afterStatusName}</td>

                            <td  align="center">${item.description}</td>

                            <td  align="center">${item.logTime}</td>

                        </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
    <p:line flag="1" />
    
	<p:button leftWidth="98%" rightWidth="0%">
        <div align="right">
        <input type="button" name="pr"
            class="button_class" onclick="pagePrint()"
            value="&nbsp;&nbsp;打 印&nbsp;&nbsp;">&nbsp;&nbsp;
        <input type="button" class="button_class"
            id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
            onclick="javaScript:window.history.go(-1);"></div>
    </p:button>
	
	<p:message2/>
</p:body>
</form>
</body>
</html>


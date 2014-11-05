<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="申请预收退款" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../tcp_js/travelApply.js"></script>
<script language="javascript">

function processBean(opr)
{
	$("input[name='oprType']").val(opr);
    
    var msg = '';
    
    var checkFun = null;
    
    if ("0" == opr)
    {
        msg = '确定通过预收退款申请?';
    }
    
    if ("1" == opr)
    {
        msg = '确定驳回到初始?';
    }
    
    if ($O('processer'))
    {
	    if ("0" != opr)
	    {
	        $O('processer').oncheck = '';
	    }
	    else
	    {
	        $O('processer').oncheck = 'notNone';
	    }
    }

    submit(msg, null, checkMoney);
}

function checkMoney()
{
	<c:if test="${bean.status == 29}">
		var total = parseFloat('${my:formatNum(bean.backMoney / 100.0)}');
	    
	    var mels = document.getElementsByName('money');
	    
	    var addMoney = 0.0;
	    
	    for (var i = 0; i < mels.length; i++)
	    {
	        if (mels[i].value != '')
	        {
	            addMoney += parseFloat(mels[i].value);
	        }
	    }
	    
	    if (addMoney != total)
	    {
	        alert('付款金额必须是' + total);
	        
	        return false;
	    }
    </c:if>
    
    return true;
}

var g_obj;

function selectBank(obj)
{
    g_obj = obj;
    
    //单选
    window.common.modal('../finance/bank.do?method=rptQueryBank&load=1');
}

function getBank(obj)
{
    g_obj.value = obj.pname;
    
    var hobj = getNextInput(g_obj.nextSibling);
    
    hobj.value = obj.value;
}

function getNextInput(el)
{
    if (el.tagName && el.tagName.toLowerCase() == 'input')
    {
        return el;
    }
    else
    {
        return getNextInput(el.nextSibling);
    }
}

function load()
{
    <c:if test="${bean.status == 29}">
    addTr();
    </c:if>
}

</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../tcp/backprepay.do?method=processBackPrePayBean" method="post">
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="processId" value=""> 
<input type="hidden" name="id" value="${bean.id}"> 
<input type="hidden" name="customerId" value="${bean.customerId}">
<input type="hidden" name="totalMoney" value="${my:formatNum(bean.backMoney / 100.0)}">

<p:navigation height="22">
	<td width="550" class="navigation">申请预收退款</td>
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
            
            <p:pro field="stafferId" value="${bean.stafferName}"/>
            <p:pro field="departmentId" value="${bean.departmentName}"/>            
            
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
            <p:pro field="paymentId"/>
            
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
            
            <c:if test="${bean.status == 29}">
	            <p:cell title="申请退款金额" end="true">
	                <input type="text" oncheck="notNone;isFloat;" readonly="readonly" name="backPay" value="${my:formatNum(bean.backMoney / 100.0)}">
	                <font color="red">*</font>
	            </p:cell> 
	            
	            <tr>
			        <td colspan='4' align='center'>
			        <table width="100%" border="0" cellpadding="0" cellspacing="0"
			            class="border" id="inner_table">
			            <tr>
			                <td>
			                <table width="100%" border="0" cellspacing='1' id="tables">
			                    <tr align="center" class="content0">
			                        <td width="30%" align="center">银行</td>
			                        <td width="30%" align="center">付款类型</td>
			                        <td width="15%" align="center">金额</td>
			                        <td width="5%" align="left"><input type="button" accesskey="A"
			                            value="增加" class="button_class" onclick="addTr()"></td>
			                    </tr>
			                </table>
			                </td>
			            </tr>
			        </table>
			
			        </td>
                 </tr>
	            
            </c:if>   
            
        </p:table>
	</p:subBody>
	
    <p:title>
        <td class="caption">
         <strong><span style="cursor: pointer;" onclick="showFlowLogTr()">流程日志(点击查看)</span></strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr id="flowLogTr">
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
    
    <p:title>
        <td class="caption">
         <strong>审核-${my:get('tcpStatus', bean.status)}</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_pay">
                    <tr align="center" class="content0">
                        <td width="15%" align="center">审批意见</td>
                        <td align="left">
                        <textarea rows="3" cols="55" oncheck="notNone;maxLength(200);" name="reason"></textarea>
                        <font color="red">*</font>
                        </td>
                    </tr>
                    <c:if test="${requestScope.pluginType == 'group'}">
                    <tr align="center" class="content1">
                        <td width="15%" align="center">提交到</td>
                        <td align="left">
                        <input type="text" name="processer" readonly="readonly" oncheck="notNone" head="下环处理人"/>&nbsp;
                        <font color=red>*</font>
                        <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                            class="button_class" onclick="selectNext('${pluginType}', '${pluginValue}')">&nbsp;&nbsp;
                        </td>
                    </tr>
                    </c:if>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
    <p:line flag="1" />
    
	<p:button leftWidth="98%" rightWidth="0%">
        <div align="right">
        <input type="button" class="button_class" id="sub_b1"
            value="&nbsp;&nbsp;通 过&nbsp;&nbsp;" onclick="processBean(0)">
          &nbsp;&nbsp;
	          <input type="button" class="button_class" id="sub_b2"
	            value="&nbsp;&nbsp;驳回到初始&nbsp;&nbsp;" onclick="processBean(1)">
          <input type="button" name="pr"
            class="button_class" onclick="pagePrint()"
            value="&nbsp;&nbsp;打 印&nbsp;&nbsp;">
        </div>
    </p:button>
	
	<p:message2/>
</p:body>
</form>
<table>
    <tr class="content1" id="trCopy" style="display: none;">
         <td width="50%" align="center">
         <input name="bankName" type="text" readonly="readonly" style="width: 100%;cursor: pointer;" oncheck="notNone" onclick="selectBank(this)">
         <input type="hidden" name="bankId" value=""> 
         </td>
         <td width="25%" align="center">
         <select name="payType" style="width: 100%" class="select_class" oncheck="notNone">
                <p:option type="outbillPayType"></p:option>
                </select>
         </td>
         <td width="20%" align="center"><input type="text" style="width: 100%"
                    name="money" value="0.0" oncheck="notNone;isFloat">
         </td>
         <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
</table>
</body>
</html>

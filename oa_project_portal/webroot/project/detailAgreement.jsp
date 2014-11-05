<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="合同申请" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../project_js/project.js"></script>
<script language="javascript">


function addBean(opr)
{
    $("input[name='oprType']").val(opr);
    
    if ("0" == opr)
    {
        $O('processer').oncheck = '';
    }
    else
    {
        $O('processer').oncheck = 'notNone';
    }
    
    submit('确定合同申请?', null, checks);
}

function load()
{
		 $(":text").each(function(){      this.disabled=true;   });
}

</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../project/project.do?method=addOrUpdateAgreement" enctype="multipart/form-data" method="post">
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="addOrUpdate" value="0"> 
<input type="hidden" name="processId" value=""> 
<input type="hidden" name="type" value="0"> 
<input type="hidden" name="stafferId" value="${g_stafferBean.id}"> 
<input type="hidden" name="departmentId" value="${g_stafferBean.principalshipId}"> 
<input type="hidden" name="stype" value="${g_stafferBean.otype}">
<input type="hidden" value="${selType}" name="selType"> 
<input type="hidden" name="partaid" value="">
<input type="hidden" name="partbid" value="">
<input type="hidden" name="beforeAgreeids" value="">
<input type="hidden" name="afterAgreeids" value="">
<input type="hidden" name="update" value="${update }"> 

<p:navigation height="22">
	<td width="550" class="navigation">合同申请</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		 <strong>合同申请</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
	
	    <p:class value="com.china.center.oa.project.bean.AgreementBean" opr="1"/>
	    
		<p:table cells="2">
            <p:pro field="agreementName" />
            <p:pro field="lineCode" />
            <p:pro field="agreementMoney" />
            <p:pro field="signDate" />
            <p:pro field="agreementType"  innerString="onchange='borrowChange()'">
                <p:option type="218"></p:option>
            </p:pro>
            <p:pro field="agreementProperty"  innerString="onchange='borrowChange()'">
                <p:option type="228"></p:option>
            </p:pro>
            <p:pro field="party_a" innerString="onclick='selectpartyA(this)'" />
            <p:pro field="party_b" innerString="onclick='selectpartyB(this)'" />
             <p:pro field="firstparty" />
            <p:pro field="secondparty"  />
            <p:cell title="合同文本" width="8" end="true"><input type="file" name="agreementText" size="70" >  
            </p:cell>
            <p:pro field="refProject" cell="0" />
            <p:pro field="beforeAgreement" innerString="onclick='selectBeforeAgre(this)' rows=2 cols=45 style='cursor: pointer;'"/>
            <p:pro field="afterAgreement" innerString="onclick='selectAfterAgre(this)' rows=2 cols=45 style='cursor: pointer;'"/>
            <p:pro field="agreementDesc" cell="0" innerString="rows=4 cols=55" />
            
        </p:table>
	</p:subBody>
	
	

	<p:title>
        <td class="caption">
         <strong>产品行项目</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr id="pay_main_tr">
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_Pro">
                    <tr align="center" class="content0">
                        <td width="10%" align="center">合同产品</td>
                        <td width="15%" align="center">产品数量</td>
                        <td width="15%" align="center">产品单价</td>
                    </tr>
                    <c:forEach items="${bean.proLineList}" var="item">
                    <tr align="center" class="content1">
                        <td width="15%" align="center">${item.projectproName}</td>
                        <td width="10%" align="center">${item.procount}</td>
                        <td width="10%" align="center">${item.prounitprice}</td>
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
         <strong>付款行项目</strong>
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
                        <td width="15%" align="center">付款类型</td>
                        <td width="15%" align="center">付款金额</td>
                        <td width="15%" align="center">付款时间</td>
                        <td width="15%" align="center">完成天数</td>
                        <td width="6%" align="center">当前任务</td>
                        <td width="15%" align="center">前提任务</td>
                        <td width="15%" align="center">后续任务</td>
                    </tr>
                     <c:forEach items="${bean.payLineProjectList}" var="item">
	                    <tr align="center" class="content1">
	                        <td width="15%" align="center">${my:get('219', item.payType)}</td>
	                        <td width="10%" align="center">${item.payMoney}</td>
	                        <td width="10%" align="center">${item.payTime}</td>
	                        <td width="15%" align="center">${item.finishDays}</td>
	                        <td width="10%" align="center">${item.paycurrentTask1}</td>
	                        <td width="10%" align="center">${item.beforeTask}</td>
	                        <td width="10%" align="center">${item.afterTask}</td>
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
         <strong>交付行项目</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_trans">
                    <tr align="center" class="content0">
                        <td width="11%" align="center">交付类型</td>
                        <td width="7%" align="center">交付物</td>
                        <td width="5%" align="center">交付物数量</td>
                        <td width="7%" align="center">交付时间</td>
                        <td width="5%" align="center">完成天数</td>
                        <td width="6%" align="center">当前任务</td>
                        <td width="5%" align="center">接收人</td>
                        <td width="20%" align="center">前提任务</td>
                        <td width="20%" align="center">后续任务</td>
                    </tr>
                    <c:forEach items="${bean.transLineProjectList}" var="item">
		                    <tr align="center" class="content1">
		                        <td width="11%" align="center">${my:get('220', item.transType)}</td>
		                        <td width="7%" align="center">${my:get('226', item.transobj)}</td>
		                        <td width="5%" align="center">${item.transobjCount}</td>
		                        <td width="7%" align="center">${item.transTime}</td>
		                        <td width="5%" align="center">${item.transDays}</td>
		                        <td width="6%" align="center">${item.currentTask}</td>
		                        <td width="5%" align="center">${item.receiver}</td>
		                        <td width="20%" align="center">${item.beforeTask1}</td>
		                        <td width="20%" align="center">${item.afterTask1}</td>
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
         <strong>开票行项目</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_invoice">
                    <tr align="center" class="content0">
                        <td width="8%" align="center">开票类型</td>
                        <td width="8%" align="center">开票金额</td>
                        <td width="8%" align="center">开票时间</td>
                        <td width="5%" align="center">完成天数</td>
                        <td width="20%" align="center">前提任务</td>
                        <td width="20%" align="center">后续任务</td>
                    </tr>
                    <c:forEach items="${bean.invoiceLineProjectList}" var="item">
		                    <tr align="center" class="content1">
		                        <td width="8%" align="center">${my:get('221', item.invoiceType)}</td>
		                        <td width="8%" align="center">${item.invoiceMoney}</td>
		                        <td width="8%" align="center">${item.invoiceTime}</td>
		                        <td width="5%" align="center">${item.finishiDays1}</td>
		                        <td width="20%" align="center">${item.beforeTask2}</td>
		                        <td width="20%" align="center">${item.afterTask2}</td>
		                    </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>
        
        <p:button leftWidth="98%" rightWidth="0%">
        <div align="right">
        <input type="button" class="button_class"
            id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
            onclick="javaScript:window.history.go(-1);"></div>
   		 </p:button>
</p:body>
</form>
</body>
</html>


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
	var agreementMoney = $O('agreementMoney').value;
	if(isNaN(agreementMoney))
    {  
	alert("合同金额必须为数字");
	return false;
     }
	processPro();
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
	addProTr();
	
	addPayTr();

	addTransTr();
	
	addInvoiceTr();
	
	$v('tr_att_more', false);
	
	borrowChange();
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
<input type="hidden" name="partaid" value="">
<input type="hidden" name="partbid" value="">
<input type="hidden" name="beforeAgreeids" value="">
<input type="hidden" name="afterAgreeids" value="">
<input type=hidden name="saves" value="" />
<input type="hidden" name="pa_selType" value="" />
<input type="hidden" name="pb_selType" value="" />
<!-- 产品行项目 -->
<input type="hidden" name="proIds"  value="">
<input type="hidden" name="pCount" value="">
<input type=hidden name="proPrice" value="" />
<input type="hidden" name="s_projectId" value="" />



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
	
	    <p:class value="com.china.center.oa.project.bean.AgreementBean" />
	    
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
            <p:pro field="refProject" cell="0" innerString="onclick='selectProject(this)'"/>
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
                        <td width="5%" align="left"><input type="button" accesskey="B"
                            value="增加" class="button_class" onclick="addProTr()"></td>
                    </tr>
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
                        <td width="15%" align="center">当前任务</td>
                        <td width="15%" align="center">前提任务</td>
                        <td width="15%" align="center">后续任务</td>
                        <td width="5%" align="left"><input type="button" accesskey="B"
                            value="增加" class="button_class" onclick="addPayTr()"></td>
                    </tr>
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
                        <td width="11%" align="center">交付物</td>
                        <td width="11%" align="center">交付物数量</td>
                        <td width="11%" align="center">交付时间</td>
                        <td width="11%" align="center">完成天数</td>
                        <td width="11%" align="center">当前任务</td>
                        <td width="11%" align="center">接收人</td>
                        <td width="11%" align="center">前提任务</td>
                        <td width="11%" align="center">后续任务</td>
                        <td width="5%" align="left"><input type="button" accesskey="B"
                            value="增加" class="button_class" onclick="addTransTr()"></td>
                    </tr>
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
                        <td width="13%" align="center">开票类型</td>
                        <td width="13%" align="center">开票金额</td>
                        <td width="13%" align="center">开票时间</td>
                        <td width="13%" align="center">完成天数</td>
                        <td width="13%" align="center">当前任务</td>
                        <td width="13%" align="center">前提任务</td>
                        <td width="13%" align="center">后续任务</td>
                        <td width="5%" align="left"><input type="button" accesskey="B"
                            value="增加" class="button_class" onclick="addInvoiceTr()"></td>
                    </tr>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
    <p:title>
        <td class="caption">
         <strong>提交/审核</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr id="sub_main_tr">
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_pay">
                    <tr align="center" class="content0">
                        <td width="15%" align="center">提交到</td>
                        <td align="left">
                        <input type="text" name="processer" readonly="readonly" oncheck="notNone" head="下环处理人"/>&nbsp;
                        <font color=red>*</font>
                        <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                            class="button_class" onclick="initSelectNext()">&nbsp;&nbsp;
                        </td>
                    </tr>
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
            value="&nbsp;&nbsp;保存&nbsp;&nbsp;" onclick="addBean(0)">
		  &nbsp;&nbsp;
		  <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean(1)">
        </div>
	</p:button>
	
	<p:message2/>
</p:body>
</form>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<table>
    <tr class="content1" id="trCopy" style="display: none;">
         <td align="left">
         <input type=text name = 'agreementPro'  id = 'agreementPro'  onclick="selectAgreementpro(this)"   style="width: 100%" >
         <input type="hidden" name="s_agreementproId" value=""> 
         </td>
         <td align="left">
         <input type=text name = 'proCounts'  id = 'proCounts'  oncheck = ""    style="width: 100%" >
         </td>
         <td align="left">
         <input type=text name = 'proUnitPrice'  id = 'proUnitPrice'  oncheck = ""    style="width: 100%" >
         </td>
        <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    
    <tr class="content1" id="trCopy_pay" style="display: none;">
    	<td align="left">
   		 <select name="payType" class="select_class" style="width: 100%;" oncheck="notNone">
            <p:option type="219" empty="true"></p:option>
         </select>
         </td>
         <td align="left"><input type="text" style="width: 100%"
                    name="payMoney" value="" >
         </td>
         
         <td align="left">
         <input type=text name = 'payTime'  id = 'payTime'  oncheck = ""  value = ''  readonly=readonly class='input_class' size = '20' ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "payTime");' height='20px' width='20px'/>
         </td>
         <td align="left"><input type="text" style="width: 100%"
                    name="finishDays" value="" >
         </td>
         <td align="left"><input type="text" style="width: 100%" onclick="selectPaycurrentTask1(this)"
                    name="paycurrentTask1" value="" >
                    <input type="hidden" name="s_paycurrentTask1id" value=""> 
         </td>
          <td align="left"><textarea rows="3" cols="20" onclick="selectPayBeforeTask(this)" style="width: 100%"
                    name="beforeTask" id="beforeTask" ></textarea>
                    <input type="hidden" name="s_payBeforeids" value=""> 
         </td>
          <td align="left"><textarea rows="3" cols="20" onclick="selectPayAfterTask(this)" style="width: 100%"
                    name="afterTask" id="afterTask"></textarea>
                    <input type="hidden" name="s_payAfterids" value=""> 
         </td>
        <td width="5%" align="center"><input type=button name="pay_del_bu"
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    
    <tr class="content1" id="trCopy_trans" style="display: none;">
    	<td align="left">
   		 <select name="transType" class="select_class" style="width: 100%;" oncheck="notNone">
            <p:option type="220" empty="true"></p:option>
         </select>
         </td>
         <td align="left">
         <select name="transobj" class="select_class" style="width: 100%;" oncheck="notNone">
            <p:option type="226" empty="true"></p:option>
         </select>
         </td>
         <td align="left"><input type="text" style="width: 100%"
                    name="transobjCount" value="" >
         </td>
         <td align="left">
         <input type=text name = 'transTime'  id = 'transTime'  oncheck = ""  value = ''  readonly=readonly class='input_class' size = '20' ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "transTime");' height='20px' width='20px'/>
         </td>
         
          <td align="left"><input type="text" style="width: 100%"
                    name="transDays" value="" >
         </td>
          <td align="left"><input type="text" style="width: 100%" onclick="selectTrancurrentTask1(this)"
                    name="trancurrentTask1" value="" >
                    <input type="hidden" name="s_trancurrentTask1id" value=""> 
         </td>
          <td align="left"><input type="text" style="width: 100%"
                    name="receiver" value="" onclick="selectReceiver(this)">
                    <input type="hidden" name="receiverid" value="" />
         </td>
          <td align="left"><textarea rows="3" cols="20" onclick="selectTranBeforeTask(this)" style="width: 100%"
                    name="beforeTask1" id="beforeTask1"></textarea>
                    <input type="hidden" name="s_tranBeforeids" value=""> 
         </td>
         <td align="left"><textarea rows="3" cols="20" onclick="selectTranAfterTask(this)" style="width: 100%"
                    name="afterTask1" id="afterTask1"></textarea>
                     <input type="hidden" name="s_tranAfterids" value=""> 
         </td>
        <td width="5%" align="center"><input type=button name="pay_del_bu"
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    
    <tr class="content1" id="trCopy_invoice" style="display: none;">
    	<td align="left">
   		 <select name="invoiceType" class="select_class" style="width: 100%;" oncheck="notNone">
            <p:option type="221" empty="true"></p:option>
         </select>
         </td>
         <td align="left"><input type="text" style="width: 100%"
                    name="invoiceMoney" value="" >
         </td>
         <td align="left">
         <input type=text name = 'invoiceTime'  id = 'invoiceTime'  oncheck = ""  value = ''  readonly=readonly class='input_class' size = '20' ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "invoiceTime");' height='20px' width='20px'/>
         </td>
         
          <td align="left"><input type="text" style="width: 100%"
                    name="finishiDays1" value="" >
         </td>
         
         <td align="left"><input type="text" style="width: 100%" onclick="selectinvocurrentTask(this)"
                    name="invocurrentTask" value="" >
                    <input type="hidden" name="s_invocurrentTask" value=""> 
         </td>
         
          <td align="left"><textarea rows="3" cols="20" onclick="selectInvoiceBeforeTask(this)" style="width: 100%"
                    name="beforeTask2" id="beforeTask2"></textarea>
                    <input type="hidden" name="s_invoiceBeforeids" value=""> 
                    
         </td>
          <td align="left">
          <textarea rows="3" cols="20" onclick="selectInvoiceAfterTask(this)" style="width: 100%"
                    name="afterTask2" id="afterTask2"></textarea>
                    <input type="hidden" name="s_invoiceAfterids" value=""> 
         </td>
        <td width="5%" align="center"><input type=button name="pay_del_bu"
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    
</table>
</body>
</html>


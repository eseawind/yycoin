<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="提成特批申请" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../tcp_js/travelApply.js"></script>

<script type="text/javascript" src="../tcp_js/jquery-calendar.js"></script>
<!--<script type="text/javascript" src="../tcp_js/jquery-calendar2.js"></script>-->
<link rel="stylesheet" type="text/css" href="../tcp_js/jquery-calendar.css">
<link rel="stylesheet" type="text/css" href="../tcp_js/styles.css">

<script language="javascript">

$(document).ready(function (){


    $("#beginDate").calendar();
    $("#endDate").calendar();

});


function initSelectNext1(status)
{
	if ($$('stype') == '')
	{
		alert('请选择系列');
		
		return false;
	}
	
	if ($$('stype') != '2')
	{
		selectNext('group', 'A220110406000200001',status)
	}
	else
	{
		selectNext('group', 'A220110406000200004',status)
	}
}

function selectNext(type, value,status)
{
    if (type == 'group')
    {
    	sflag = 0;
    	window.common.modal('../group/group.do?method=vocationAndWorkQueryGroupMember&selectMode=1&load=1&pid=' + value+'&status=' + status);
    }	
}

function addBean(opr)
{
    $("input[name='oprType']").val(opr);
    var msg = '';
    var checkFun = null;

    if ("0" == opr)
    {
        msg = '确定通过申请?';
    }
    
    if ("1" == opr)
    {
        msg = '确定驳回到申请到初始?';
    }
    
    if ("2" == opr)
    {
        msg = '确定驳回到申请到上一步?';
    }
    
    submit(msg, null, checkFun);
}

function load()
{
	showFlowLogTr();
	$v('tr_att_more', false);
	
}
var showTr = false;
function showFlowLogTr()
{
    $v('flowLogTr', showTr);
    
    showTr = !showTr;
}

function purposeChange(obj)
{
	if(obj.value==31)
		{
			$("#vocationType").show();
		}
	else
	{
		$("#vocationType").hide();
	}
}

function processBean(opr)
{
    $("input[name='oprType']").val(opr);

    if($("#reason").val()=="")
    {
        alert("审批意见不能为空");
		return false;
    }
    
    var msg = '';
    
    var checkFun = null;
    
    if ("0" == opr)
    {
        msg = '确定通过申请?';
    }
    
    if ("1" == opr)
    {
        msg = '确定驳回到申请到初始?';
    }
    
    if ("2" == opr)
    {
        msg = '确定驳回到申请到上一步?';
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
    
    
    submit(msg, null, checkFun);
}

</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../tcp/apply.do?method=processCommission" method="post">
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="addOrUpdate" value="0"> 
<input type="hidden" name="processId" value=""> 
<input type="hidden" name="id" value="${bean.id}"> 
<input type="hidden" name="departid" value="">
<input type="hidden" name="applyProduct" value="">
<input type="hidden" name="type" value="0"> 
<input type="hidden" name="stafferId" value="${g_stafferBean.id}"> 
<input type="hidden" name="departmentId" value="${g_stafferBean.principalshipId}"> 
<input type="hidden" name="stype" value="${g_stafferBean.otype}">

<p:navigation height="22">
	<td width="550" class="navigation">提成特批申请</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		 <strong>提成特批申请</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
	
	    <p:class value="com.china.center.oa.tcp.bean.CommissionApplyBean" opr="1"/>
		<p:table cells="2">
			<p:pro field="applyobj"  innerString="disabled='disabled' onchange='purposeChange(this)'">
                <p:option type="selApplyobj"></p:option>
            </p:pro>
            <p:pro field="applyer" value="${staffervo.name}"/>
            <p:cell title="申请部门" end="true" >
				<textarea name='departs'
					head='申请部门' id='departs' rows=2 cols=40 readonly=true
					>${bean.departName }</textarea> 
			</p:cell>
			<p:cell title="申请产品" end="true">
				<textarea name='products'
					head='申请产品' id='products' rows=2 cols=40 readonly=true
					>${bean.productName }</textarea> 
			</p:cell>
			
          <p:pro field="applyType" cell="0" innerString="disabled='disabled'">
                <p:option type="selApplyType"></p:option>
            </p:pro>
            <p:pro field="startTime" innerString="disabled='disabled'"/>
            <p:pro field="endTime" innerString="disabled='disabled'"/>
            <p:pro field="changeType" innerString="disabled='disabled'" >
                <p:option type="selChangeType"></p:option>
            </p:pro>
            <p:pro field="moneys"  innerString="disabled='disabled'"/>
            <p:pro field="detail"  cell="0" innerString="disabled='disabled' rows=4 cols=55"/>
        </p:table>
	</p:subBody>
	
	 <p:line flag="0" />
	 
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
                        <td width="15%" align="center">审批意见</td>
                        <td align="left">
                       <textarea rows="3" cols="55" id="reason" name="reason"></textarea>
                        <font color="red">*</font>
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
	            value="&nbsp;&nbsp;返回&nbsp;&nbsp;" onclick="javascript:window.history.go(-1);">
		  &nbsp;&nbsp;
		  <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;同意&nbsp;&nbsp;" onclick="addBean(0)">
            <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;驳回到初始&nbsp;&nbsp;" onclick="addBean(1)">
            <c:if test="${bean.status!=2 }">
          <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;驳回到上一步&nbsp;&nbsp;" onclick="addBean(2)">
            </c:if>
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
         <input type=text name = 'i_beginDate'  id = 'i_beginDate'  oncheck = ""  value = ''  readonly=readonly class='input_class' size = '20' ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "i_beginDate");' height='20px' width='20px'/>
         </td>
         <td align="left">
         <input type=text name = 'i_endDate'  id = 'i_endDate'  oncheck = ""  value = ''  readonly=readonly class='input_class' size = '20' ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "i_endDate");' height='20px' width='20px'/>
         </td>
         <td align="left">
         <select name="i_feeItem" class="select_class" style="width: 100%;" oncheck="notNone">
	         <option value="">--</option>
	         <c:forEach var="item" items="${feeItemList}">
	             <option value="${item.id}">${item.name}</option>
	         </c:forEach>
         </select>
         </td>
         
         <td align="left"><input type="text" style="width: 100%"
                    name="i_moneys" value="" oncheck="notNone;isFloat3"></td>
         <td align="left">
         <textarea name="i_description" rows="3" style="width: 100%"></textarea>
         </td>
        <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    
    <tr class="content1" id="trCopy_pay" style="display: none;">
         <td align="left">
         <select name="p_receiveType" class="select_class" style="width: 100%;" oncheck="notNone">
            <p:option type="travelApplyReceiveType" empty="true"></p:option>
         </select>
         </td>
         
         <td align="left"><input type="text" style="width: 100%"
                    name="p_bank" value="" >
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%"
                    name="p_userName" value="" >
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%"
                    name="p_bankNo" value="" >
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%"
                    name="p_moneys" value="" oncheck="notNone;isFloat3">
         </td>
         
         <td align="left">
         <textarea name="p_description" rows="3" style="width: 100%"></textarea>
         </td>
        <td width="5%" align="center"><input type=button name="pay_del_bu"
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    
    <%@include file="share_tr.jsp"%>
    
</table>
</body>
</html>


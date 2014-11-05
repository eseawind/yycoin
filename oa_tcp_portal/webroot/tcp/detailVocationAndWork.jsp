<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="请假/加班申请" guid="true"/>
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
    var begin = $("#beginDate").val();
    var end = $("#endDate").val();
    if((begin==null ||begin=='')||(end==null||end==''))
    {
		alert("日期不能为空");
		return false;
    }
    if($("#reason").val()=='' || $("#reason").val() == null)
    {
		alert("审批意见不能为空");
		return false;
    }
    if(begin.split(":")[1].replace(" ","")!="00"&&begin.split(":")[1].replace(" ","")!="30")
    {
        alert('时间只能是整点与半点，即00或30');
        return false;
    }
    if(end.split(":")[1].replace(" ","")!='00'&&end.split(":")[1].replace(" ","")!='30')
    {
        alert('时间只能是整点与半点，即00或30');
        return false;
    }
    if(parseInt(begin.replace(/\-/g,"").replace(/\:/g,"").replace(/\ /g,"")) >= 
        parseInt(end.replace(/\-/g,"").replace(/\:/g,"").replace(/\ /g,"")))
    {
        alert("开始日期不能大于或等于结束日期。");
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
<form name="formEntry" action="../tcp/apply.do"  method="post">
<input type="hidden" name="method" value="processVocationAndWork"> 
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="addOrUpdate" value="1"> 
<input type="hidden" name="id" value="${bean.id}"> 
<input type="hidden" name="processId" value=""> 
<input type="hidden" name="type" value="0"> 
<input type="hidden" name="stafferId" value="${staffervo.id}"> 
<input type="hidden" name="departmentId" value="${staffervo.principalshipId}"> 
<input type="hidden" name="stype" value="${staffervo.otype}">

<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
        onclick="javascript:history.go(-1)">待我处理</span> &gt;&gt; 请假/加班申请处理</td>
    <td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		 <strong>请假/加班申请-${my:get('tcpStatus', bean.status)}</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
	    <p:class value="com.china.center.oa.tcp.bean.TravelApplyBean" opr="1"/>
        	<p:table cells="2">
        	<p:cell title="处理流程" width="8" end="true">
            ${bean.flowDescription}
            </p:cell>
            
            <p:pro field="stafferId" value="${staffervo.name}" />
            <p:pro field="departmentId" value="${staffervo.principalshipName}" />
            
            <p:pro field="qingJiapurpose"  cell="0" innerString="rows=4 cols=55 disabled='disabled'"/>
            <p:pro field="purposeType"  innerString="onchange='purposeChange(this)' disabled='disabled'">
                <p:option type="purposeTypeContent"></p:option>
            </p:pro>
            <c:if test="${bean.purposeType eq '31' }">
            <p:pro field="vocationType"  innerString="disabled='disabled'">
                <p:option type="vocationTypeContent"></p:option>
            </p:pro>
            </c:if>
            <c:if test="${bean.purposeType ne '31' }">
            <p:pro field="vocationType"  innerString="style='display:none;'">
                <p:option type="vocationTypeContent"></p:option>
            </p:pro>
            </c:if>
            <p:pro field="oldNumber" cell="0" innerString="disabled='disabled'"/>
            
          <p:cell title="开始日期" width="8" >
            <input id="beginDate" name="beginDate" value="${bean.beginDate }" disabled class="calendarFocus" type="text"><span class="calendar_append"></span>
            <font color="red" >*</font></p:cell>
            <p:cell title="结束日期" width="8" end="true">
            <input id="endDate" name="endDate" value="${bean.endDate }" disabled class="calendarFocus" type="text"><span class="calendar_append"></span>
            <font color="red" >*</font></p:cell>
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
    
    <p:line flag="0" />
    
	<p:button leftWidth="98%" rightWidth="0%">
		<div align="right">
		<input type="button" class="button_class" id="sub_b1"
	            value="&nbsp;&nbsp;返回&nbsp;&nbsp;" onclick="javascript:window.history.go(-1);">
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


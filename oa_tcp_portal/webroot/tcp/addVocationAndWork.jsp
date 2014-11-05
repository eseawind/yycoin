<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="请假加班申请" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
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
    var d = new Date();
    var curTime='';
    var years = d.getYear();
    curTime = curTime+years+'-';
    var month = d.getMonth()+1;
    curTime = curTime+month+'-';
    var days = d.getDate();
    curTime = curTime+days+' ';
    var hours = d.getHours();
    curTime = curTime+hours+':';
    var minutes = d.getMinutes();
    curTime = curTime+minutes;
    if((begin==null ||begin=='')||(end==null||end==''))
    {
		alert("日期不能为空");
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
    //if(parseInt(begin.replace(/\-/g,"").replace(/\:/g,"").replace(/\ /g,"")) <= 
    //    parseInt(curTime.replace(/\-/g,"").replace(/\:/g,"").replace(/\ /g,"")))
    //{
     //   alert("开始日期不能小于当前时间。");
     //   return false;
    //}
    
    if(parseInt(begin.replace(/\-/g,"").replace(/\:/g,"").replace(/\ /g,"")) >= 
        parseInt(end.replace(/\-/g,"").replace(/\:/g,"").replace(/\ /g,"")))
    {
        alert("开始日期不能大于或等于结束日期。");
        return false;
    }

    if($("#purposeType").val()!=31 && $("#purposeType").val()!=21 && $("#purposeType").val()!=0)
    {
        var num = $("#oldNumber").val();
        if(num == null || num == '')
        {
        alert("原申请单号不能为空");
        return false;
        }
    }
    if($("#purposeType").val()==31 && $("#vocationType").val()==0)
    {
        alert("请假类型不能为空");
        return false;
    }
    if($("#vocationType").val()==2)
    {
    	var begin1=begin.substr(11).split(":");
    	var begin2=begin.substr(0,10).split("-");
    	var end1=end.substr(11).split(":");
    	var end2=end.substr(0,10).split("-");
    	var h=parseInt(end1[0],10)-parseInt(begin1[0],10);
    	var d=parseInt(end2[2],10)-parseInt(begin2[2],10);
    	if(h<2&&d<1)
    	{
        	alert("事假不得低于两小时");
        	return false;
    	}
    }
    if($("#purposeType").val()==0)
    {
        alert("目的类型不能为空");
        return false;
    }
    
    submit('确定提交申请?');
}

function load()
{
	
	$v('tr_att_more', false);
	
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
		if(obj.value!=21)
		{
			$("#oldNumber").attr("disabled",false);
		}
		else
		{
			$("#oldNumber").val('');
			$("#oldNumber").attr("disabled",true);
		}	
	}
}

</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../tcp/apply.do?method=addOrUpdateVocationAndWork" method="post">
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="addOrUpdate" value="0"> 
<input type="hidden" name="processId" value=""> 
<input type="hidden" name="type" value="0"> 
<input type="hidden" name="stafferId" value="${g_stafferBean.id}"> 
<input type="hidden" name="departmentId" value="${g_stafferBean.principalshipId}"> 
<input type="hidden" name="stype" value="${g_stafferBean.otype}">

<p:navigation height="22">
	<td width="550" class="navigation">请假/加班申请</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		 <strong>请假/加班申请</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
	
	    <p:class value="com.china.center.oa.tcp.bean.TravelApplyBean" />
		<p:table cells="2">
            <p:pro field="stafferId" value="${g_stafferBean.name}"/>
            <p:pro field="departmentId" value="${g_stafferBean.principalshipName}"/>
            
            <p:pro field="qingJiapurpose"  cell="0" innerString="rows=4 cols=55"/>
            <p:pro field="purposeType"  innerString="onchange='purposeChange(this)'">
                <p:option type="purposeTypeContent"></p:option>
            </p:pro>
            <c:if test="${purposeType=='31' }">
            <p:pro field="vocationType"  >
                <p:option type="vocationTypeContent"></p:option>
            </p:pro>
            </c:if>
            <c:if test="${purposeType!='31' }">
            <p:pro field="vocationType"  innerString="style='display:none;'">
                <p:option type="vocationTypeContent"></p:option>
            </p:pro>
            </c:if>
            
            <p:pro field="oldNumber" cell="0" innerString="disabled='disabled'"/>
            <p:cell title="开始日期" width="8" >
            <input id="beginDate" name="beginDate" class="calendarFocus" readonly type="text"><span class="calendar_append"></span>
            <font color="red" >*</font></p:cell>
            <p:cell title="结束日期" width="8" end="true">
            <input id="endDate" name="endDate" readonly class="calendarFocus" type="text"><span class="calendar_append"></span>
            <font color="red" >*</font></p:cell>
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
                        <td width="15%" align="center">提交到</td>
                        <td align="left">
                        <input type="text" name="processer" readonly="readonly" oncheck="notNone" head="下环处理人"/>&nbsp;
                        <font color=red>*</font>
                        <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                            class="button_class" onclick="initSelectNext1(${tempStatus})">&nbsp;&nbsp;
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
            value="&nbsp;&nbsp;取消&nbsp;&nbsp;" onclick="javascript:window.history(-1);">
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


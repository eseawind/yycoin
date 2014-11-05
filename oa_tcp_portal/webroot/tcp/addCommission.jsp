<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="提成审批申请" guid="true"/>
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

function selectDeparts()
{
    window.common.modal('../admin/pop.do?method=selectDeparts&load=1&selectMode=0&flag=0');
}

function getDeparts(oos)
{

    var ids = '';
    var names = '';
    for (var i = 0; i < oos.length; i++)
    {
        if (i == oos.length - 1)
        {
            ids = ids + oos[i].value ;
            names = names + oos[i].pname ;
        }
        else
        {
            ids = ids + oos[i].value + ',';
            names = names + oos[i].pname + ',' ;
        }        
    }

    $O('departid').value = ids;
    $O('departs').value = names;
    
}

function selectProducts()
{
    window.common.modal('../product/product.do?method=selectProducts&load=1&selectMode=0');
}

function getProducts(oos)
{

    var ids = '';
    var names = '';
    for (var i = 0; i < oos.length; i++)
    {
        if (i == oos.length - 1)
        {
            ids = ids + oos[i].value ;
            names = names + oos[i].pname ;
        }
        else
        {
            ids = ids + oos[i].value + ',';
            names = names + oos[i].pname + ',' ;
        }        
    }

    $O('applyProduct').value = ids;
    $O('products').value = names;
    
}

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
	var obj = $("#applyobj").val();
	if(obj==0&&$("#applyProduct").val()=="")
	{
		alert("请选择产品");
		return false;
	}
	else if(obj==1 &&$("#departid").val()=="" )
	{
		alert("请选择部门");
		return false;
	}
	var depart = $("#departs").val();
    var begin = $("#startTime").val();
    var end = $("#endTime").val();
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
    if(parseInt(begin.replace(/\-/g,"").replace(/\:/g,"").replace(/\ /g,"")) >= 
        parseInt(end.replace(/\-/g,"").replace(/\:/g,"").replace(/\ /g,"")))
    {
        alert("开始日期不能大于或等于结束日期。");
        return false;
    }
	
    submit('确定提交申请?');
}

function load()
{
	$("#departs").hide();
	$("#qout").hide();
	$('#secondType').hide();
	$v('tr_att_more', false);
	
}


function applyobjChange(obj)
{
	if(obj.value==0 && $("#changeType").val()==1 && $("#products").val()=="")
	{
		$("#moneys").show();
		$("#changeType").show();
	}
	else
	{
		$("#moneys").hide();
		$("#changeType").hide();
	}
	if(obj.value==0)
	{
		$("#stafferName_td1").show();
		$("#stafferName_td2").show();
		$("#qout").hide();
		$("#departs").hide();
	}
	else
	{
		$("#departs").show();
		$("#qout").show();
		$("#stafferName_td1").hide();
		$("#stafferName_td2").hide();
	}
}

function applyChangeType(obj)
{
	if(obj.value==1 && $("#applyobj").val()==0 && $("#products").val()=="")
	{
		$("#moneys").show();
	}
	else
	{
		$("#moneys").hide();
	}
}

function chanSecType()
{
	if($("#applyType").val()=='9058353')
	{
		$('#secondType').show();
	}
	else
	{
		$('#secondType').hide();
	}
	
}

</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../tcp/apply.do?method=addOrUpdateCommission" method="post">
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="addOrUpdate" value="0"> 
<input type="hidden" name="processId" value=""> 
<input type="hidden" name="departid" id="departid" value="">
<input type="hidden" name="applyProduct" id="applyProduct" value="">
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
	
	    <p:class value="com.china.center.oa.tcp.bean.CommissionApplyBean" />
		<p:table cells="2">
            <p:pro field="applyobj" innerString="onchange='applyobjChange(this)'">
            	<p:option type="selApplyobj"></p:option>
            </p:pro>
            <p:pro field="applyer" value="${g_stafferBean.name}" />
            <p:cell title="申请部门" >
				<textarea name='departs' 
					 id='departs' rows=2 cols=40 readonly=true
					></textarea> <input
					type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
					class="button_class" onclick="selectDeparts()">&nbsp;&nbsp;
			</p:cell>
			<p:cell title="申请产品" end="true">
				<textarea name='products'
					id='products' rows=2 cols=40 readonly=true
					></textarea><input
					type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
					class="button_class" onclick="selectProducts()">&nbsp;&nbsp;
			</p:cell>
            <p:pro field="applyType"  innerString="onchange='chanSecType()' ">
                <p:option type="216" empty="true"></p:option>
            </p:pro>
            <p:pro field="secondType" >
                <p:option type="selApplyType"></p:option>
            </p:pro>
            <p:pro field="startTime" cell="1"/>
            <p:pro field="endTime" />
            <p:pro field="changeType"  innerString="onchange='applyChangeType(this)'">
                <p:option type="selChangeType"></p:option>
            </p:pro>
            <p:pro field="moneys"  />
            <p:pro field="detail"  cell="0" innerString="rows=4 cols=55"/>
        </p:table>
	</p:subBody>
	
	 <p:line flag="0" />

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
</body>
</html>


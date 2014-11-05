<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="客户利润分配审核" link="true" guid="true" cal="false"/>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
	 guidMap = {
		 title: '客户利润分配审核',
		 url: '../customer/customer.do?method=queryApplyCustomerForAssignPer',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lname={name}>', width : 40, sortable : false, align: 'center'},
		     {display: '客户', name : 'name', width : '20%', sortable : false, align: 'left'},
		     {display: '操作', name : 'opr', width : '10%', sortable : false, align: 'left', cc: 'customerOpr'},
		     {display: '申请人', name : 'stafferName', width : '10%', sortable : false, align: 'left'},
		     {display: '分配比(事业部)', name : 'assignPer1', width : '10%', sortable : false, align: 'left', toFixed: 2},
		     {display: '分配比(分公司)', name : 'assignPer2', width : '10%', sortable : false, align: 'left', toFixed: 2},
		     {display: '分配比(产品部)', name : 'assignPer3', width : '10%', sortable : false, align: 'left', toFixed: 2},
		     {display: '分配比(其他)', name : 'assignPer4', width : '11%', sortable : false, align: 'left', toFixed: 2},
		     {display: '时间', name : 'loginTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../customer/customer.do?method=findCustomer&update=0&id={id} title=点击查看客户>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'pass', caption: '通过',bclass: 'pass', auth: '021802', onpress : doPass},
             {id: 'reject', caption: '驳回',bclass: 'reject', auth: '021802', onpress : doReject}
		     ],
		 <p:conf callBack="loadForm" queryMode="0"/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }
 

function doPass()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('确定通过--' + getRadio('checkb').lname))
        {
            $ajax('../customer/customer.do?method=processApplyAssignPer&operation=0&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}

function callBackFun(data)
{
    reloadTip(data.msg, data.ret == 0);
    
    if (data.ret == 0)
    commonQuery();
}

function doReject()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('确定驳回--' + getRadio('checkb').lname))
        {
            $ajax('../customer/customer.do?method=processApplyAssignPer&operation=1&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}
</script>
</head>
<body onload="load()" class="body_class">
<form>
</form>
<p:cache/>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>
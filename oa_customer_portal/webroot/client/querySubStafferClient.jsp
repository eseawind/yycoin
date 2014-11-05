<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="客户回收管理" link="true" guid="true" cal="true" dialog="true"/>
<script src="../js/common.js"></script>
<script src="../js/cnchina.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;

var lbuffalo = window.top.topFrame.gbuffalo;

var guidMap;
var thisObj;

function load()
{
	preload();
    guidMap = {
         title: '客户列表',
         url: '../client/client.do?method=queryClientBySubStaffer',
         colModel : [
             {display: '选择', name : 'check', content : '<input type=checkbox id={customerId} name=checkb value={customerId}>', width : 40, sortable : false, align: 'center'},
             {display: '客户', name : 'customerName', width : '20%', sortable : false, align: 'left'},
             {display: '编码', name : 'customerCode', width : '10%', sortable : false, align: 'left'},
             {display: '所属职员', name : 'stafferName', width : '10%', sortable : false, align: 'left'},
             {display: '类型', name : 'sellType', width : '10%', sortable : false, align: 'left', cc: 101},
             {display: '时间', name : 'logTime', width : 'auto', sortable : true, align: 'left'}
             ],
         extAtt: {
             customerName : {begin : '<a href=../client/client.do?method=findClient&id={customerId}>', end : '</a>'}
         },
         buttons : [
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
         <p:conf callBack="loadForm" queryMode="1"/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);	 
}
 
function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryCustomerBySubStaffer');
}

function callBackFun(data)
{
    $.unblockUI();
    reloadTip(data.msg, data.ret == 0);

    if (data.ret == 0)
    commonQuery();
}

function commonQuery(par)
{
    gobal_guid.p.queryCondition = par;
    
    gobal_guid.grid.populate(true);
}
</script>
</head>
<body onload="load()" class="body_class">
<form>
<p:cache></p:cache>
</form>
<div id="stafferList" style="display:none">
<p align='left'><label><font color=""><b>请选择需要回收客户关系职员</b></font></label></p>
<p><label>&nbsp;</label></p>
<select name="stafferId" id="staffer" quick="true" style="width: 85%"></select>
<p><label>&nbsp;</label></p>
<p>
<input type='button' value='&nbsp;&nbsp;全部回收&nbsp;&nbsp;' id='div_b_ok1' class='button_class' onclick='$process(0)'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' value='&nbsp;&nbsp;回收拓展&nbsp;&nbsp;' id='div_b_ok2' class='button_class' onclick='$process(1)'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' value='&nbsp;&nbsp;回收终端&nbsp;&nbsp;' id='div_b_ok3' class='button_class' onclick='$process(2)'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' id='div_b_cancle' value='&nbsp;&nbsp;关 闭&nbsp;&nbsp;' class='button_class' onclick='$close()'/>
</p>
<p><label>&nbsp;</label></p>
</div>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>
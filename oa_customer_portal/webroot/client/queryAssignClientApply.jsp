<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="可分配客户申请列表" link="true" guid="true" cal="true" dialog="true"/>
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
     preload();
     
	 guidMap = {
		 title: '可分配客户申请列表',
		 url: '../client/client.do?method=queryAssignApply',
		 colModel : [
				     {display: '<input type=checkbox id=flexi_Check onclick=checkAll(this)>选择', name : 'check', content : '<input type=checkbox name=checkb id={id} value={id} lname={name}>', width : 40, sortable : false, align: 'center'},
				     {display: '申请客户', name : 'customerName', width : '40%', sortable : false, align: 'left'},
				     {display: '客户编码', name : 'customerCode', width : '20%', sortable : false, align: 'left'},
				     {display: '客户类型', name : 'customerSellType', width : '15%', sortable : false, align: 'left', cc: 101},
				     {display: '申请职员', name : 'stafferName', width : 'auto', sortable : false, align: 'left'}
				     ],
		 extAtt: {
		     customerName : {begin : '<a href=../client/client.do?method=findClient&id={id}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'pass', caption: '通过',bclass: 'pass', auth: '0203', onpress : doPass},
             {id: 'reject', caption: '驳回',bclass: 'reject', auth: '0203', onpress : doReject},
             {id: 'search', bclass: 'search', onpress : doSearch}
		     ],
		 <p:conf callBack="loadForm"/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }
 
function doPass()
{
   doAjax("0");
}

function doReject()
{
   doAjax("1");
}

function doAjax(opr)
{
    var clis = getCheckBox('checkb');
    
    if (clis.length > 0)
    {
        var str = '';
        for (var i = 0; i < clis.length; i++)
        {
            str += clis[i].value + '~';
        }
        
        if (window.confirm('确定申请分配选中的客户?'))
        {
            $ajax('../client/client.do?method=processAssignApply&cids=' + str + "&opr=" + opr, callBackFun);
        }
    }
}

function callBackFun(data)
{
    reloadTip(data.msg, data.ret == 0);
    
    if (data.ret == 0)
    commonQuery();
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryAssignApply');
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
<input type="hidden" name="cacheFlag" id="cacheFlag" value="0"/>
<input type="hidden" name="cacheEle" id="cacheEle" value=""/>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>
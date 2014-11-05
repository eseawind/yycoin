<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="可分配客户列表" link="true" guid="true" cal="true" dialog="true"/>
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
		 title: '可分配客户列表(通过检索可以查询终端客户)',
		 url: '../customer/customer.do?method=queryCanAssignCustomer',
		 colModel : [
		     {display: '<input type=checkbox id=flexi_Check onclick=checkAll(this)>选择', name : 'check', content : '<input type=checkbox name=checkb value={id} lname={name}>', width : 40, sortable : false, align: 'center'},
		     {display: '客户', name : 'name', width : '20%', sortable : false, align: 'left'},
		     {display: '编码', name : 'code', width : '10%', sortable : false, align: 'left'},
		     {display: '类型', name : 'selltype', width : '10%', sortable : false, align: 'left', cc: 101},
		     {display: '类型1', name : 'selltype1', width : '10%', sortable : false, align: 'left', cc: 124},
		     {display: '级别', name : 'qqtype', width : '10%', sortable : false, align: 'left', cc: 104},
		     {display: '分类', name : 'rtype', width : '10%', sortable : false, align: 'left', cc: 105},
		     //{display: '分公司', name : 'locationName', width : '10%', sortable : false, align: 'left'},
		     {display: '状态', name : 'status', width : '10%', sortable : false, align: 'left', cc: 'realCustomerStatus'},
		     {display: '时间', name : 'loginTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../customer/customer.do?method=findCustomer&id={id}&update=1>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', caption: '申请分配', bclass: 'add', onpress : addBean},
		     {id: 'add', caption: '接受转移客户', bclass: 'add', onpress : batchAddBean},
		     {id: 'search', bclass: 'search', onpress : doSearch},
		     {separator: true}
		     ],
		 <p:conf callBack="loadForm"/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }

function addBean()
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
            $ajax('../customer/customer.do?method=addAssignApply&cids=' + str, callBackFun);
        }
    }
}

function batchAddBean()
{
	$l('../customer/customer.do?method=rptQueryCustomerBatchTrans&load=1&selectMode=1');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryCanAssignCustomer');
}

function callBackFun(data)
{
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
<p:cache/>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>
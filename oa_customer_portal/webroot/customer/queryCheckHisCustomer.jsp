<%@page contentType="text/html; charset=UTF-8" errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="核对客户修改" link="true" guid="true" cal="true" dialog="true"/>
<script src="../js/json.js"></script>
<script src="../js/common.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/public.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
	 preload();
	 
	 guidMap = {
		 title: '核对客户',
		 url: '../customer/customer.do?method=queryCheckHisCustomer',
		 colModel : [
		     {display: '<input type=checkbox id=flexi_Check onclick=checkAll(this)>选择', name : 'check', content : '<input type=checkbox name=checkb value={id} lname={name}>', width : 55, sortable : false, align: 'center'}, 
		     {display: '客户', name : 'name', width : '20%', sortable : false, align: 'left'},
		     {display: '编码', name : 'code', width : '10%', sortable : false, align: 'left'},
		     {display: '类型', name : 'selltype', width : '10%', sortable : false, align: 'left', cc: 101},
		     {display: '类型1', name : 'selltype1', width : '10%', sortable : false, align: 'left', cc: 124},
		     {display: '级别', name : 'qqtype', width : '10%', sortable : false, align: 'left', cc: 104},
		     {display: '分类', name : 'rtype', width : '10%', sortable : false, align: 'left', cc: 105},
		     {display: '修改人', name : 'updaterName', width : '10%', sortable : false, align: 'left'},
		     {display: '时间', name : 'loginTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../customer/customer.do?method=findHisCustomer&id={id} title=查看明细>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'back', caption: '核对', bclass: 'pass', onpress : checkHis},
		     {id: 'search', bclass: 'search', onpress : doSearch}
		     ],
		 <p:conf callBack="loadForm"/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryCheckHisCustomer');
}
 
function checkHis(opr)
{
    var clis = getCheckBox('checkb');
    
    if (clis.length > 0)
    {
        var str = '';
        for (var i = 0; i < clis.length; i++)
        {
            str += clis[i].value + '~';
        }
        
        if (window.confirm('确定核对选中的客户?'))
        {
            $ajax('../customer/customer.do?method=checkHisCustomer&cids=' + str, callBackFun);
        }
    }
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
<body class="body_class" onload="load()">
<form>
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>
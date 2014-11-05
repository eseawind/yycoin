<%@page contentType="text/html; charset=UTF-8" errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="核对供应商修改" link="true" guid="true" cal="true" dialog="true"/>
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
		 title: '核对供应商',
		 url: '../provider/provider.do?method=queryCheckHisProvider',
		 colModel : [
		     {display: '<input type=checkbox id=flexi_Check onclick=checkAll(this)>选择', name : 'check', content : '<input type=checkbox name=checkb value={id} lname={name}>', width : 55, sortable : false, align: 'center'}, 
		     {display: '供应商', name : 'name', width : '20%', align: 'left'},
             {display: '编码', name : 'code', width : '10%', align: 'left'},
             {display: '联系人', name : 'connector', width : '20%', align: 'left'},
             {display: '类型', name : 'type', width : '10%', align: 'left', cc: 109},
             {display: '时间', name : 'logTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../provider/provider.do?method=findHisProvider&id={id}>', end : '</a>'}
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
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryCheckHisProvider');
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
        
        if (window.confirm('确定核对选中的供应商?'))
        {
            $ajax('../provider/provider.do?method=checkHisProvider&cids=' + str, callBackFun);
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
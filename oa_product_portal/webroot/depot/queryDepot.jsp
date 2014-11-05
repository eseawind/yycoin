<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
.flexigrid div.fbutton .draw
{
    background: url(../css/flexigrid/images/get.png) no-repeat center left;
}

.flexigrid div.fbutton .odraw
{
    background: url(../css/flexigrid/images/oget.png) no-repeat center left;
} 
</style>
<p:link title="仓库管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../depot/depot.do?method=';
var addUrl = '../depot/addDepot.jsp';
var ukey = 'Depot';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '仓库列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
             {display: '名称', name : 'name', width : '20%'},
             {display: '类型', name : 'type', width : '15%', cc: 'depotType'},
             {display: '地点', name : 'industry2Name', width : '20%'},
             {display: '事业部', name : 'industryName', width : '20%'},
             {display: '状态', name : 'status', width : '5%', cc: 'orgStatus'},
             {display: '描述', name : 'description', width : 'auto'}
             ],
         extAtt: {
             //name : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', onpress : addBean, auth: '1102'},
             {id: 'update', bclass: 'update', onpress : updateBean, auth: '1102'},
             {id: 'update1', bclass: 'update', caption: '仓区转移', onpress : preForMoveDepotpart, auth: '1104'},
             {id: 'del', bclass: 'del',  onpress : delBean, auth: '1102'},
             {id: 'export', bclass: 'odraw',  caption: '导出所有库存(含价格)', onpress : exports},
             {id: 'export2', bclass: 'odraw',  caption: '导出所有库存(不含价格)', onpress : exports2},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
         <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();

    highlights($("#mainTable").get(0), ['停用'], 'red');
    highlights($("#mainTable").get(0), ['在用'], 'blue');
}

function addBean(opr, grid)
{
    //$l(gurl + 'preForAdd' + ukey);
    $l(addUrl);
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        if(window.confirm('确定删除?'))    
        $ajax(gurl + 'delete' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function updateBean()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		$l(gurl + 'find' + ukey + '&update=1&id=' + getRadioValue('checkb'));
	}
	else
	$error('不能操作');
}

function preForMoveDepotpart()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		$l('../depot/storage.do?method=preForMoveDepotpart&id=' + getRadioValue('checkb'));
	}
	else
	$error('不能操作');
}

function exports()
{
    document.location.href = '../depot/storage.do?method=exportStorageRelation';
}

function exports2()
{
    document.location.href = '../depot/storage.do?method=exportStorageRelation2';
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=query' + ukey);
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>
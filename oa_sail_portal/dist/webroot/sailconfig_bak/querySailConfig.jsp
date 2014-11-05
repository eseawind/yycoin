<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="销售规则管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../sail/config.do?method=';
var addUrl = '../sail/config.do?method=';
var ukey = 'SailConfig';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '销售规则列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} ppareid={pareId}>', width : 40, align: 'center'},
             {display: '开票品名', name : 'showAllName', width : '25%'},
             {display: '销售类型', name : 'sailType', cc: 'productSailType',  width : '6%'},
             {display: '销售品类', name : 'productType', cc: 'productType',  width : '6%'},
             {display: '一般纳税人', name : 'finTypeName0', width : '10%'},
             {display: '一般纳税人(17+2)', name : 'finTypeName4', width : '10%'},
             {display: '一般纳税人(SPE)', name : 'finTypeName5', width : '10%'},
             {display: '小规模纳税人', name : 'finTypeName1', width : '10%'},
             {display: '服务类', name : 'finTypeName2', width : '10%'},
             {display: '个体户', name : 'finTypeName3', width : 'auto'}
             ],
         extAtt: {
             showAllName : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', onpress : addBean, auth: '0111'},
             {id: 'update', bclass: 'update', onpress : updateBean, auth: '0111'},
             {id: 'del', bclass: 'del',  onpress : delBean, auth: '0111'},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    highlights($("#mainTable").get(0), ['不开票'], 'red');
    highlights($("#mainTable").get(0), ['开票(170‰)', '开票(20‰)', '开票(30‰)'], 'blue');
}

function addBean(opr, grid)
{
    $l(gurl + 'preForAdd' + ukey);
    //$l(addUrl);
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        if(window.confirm('确定删除?'))    
        $ajax(gurl + 'delete' + ukey + '&id=' + getRadio('checkb').ppareid, callBackFun);
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
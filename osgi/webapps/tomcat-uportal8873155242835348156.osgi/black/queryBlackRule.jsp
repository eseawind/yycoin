<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="黑名单例外规则" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../commission/black.do?method=';
var addUrl = '../black/addBlackRule.jsp';
var ukey = 'BlackRule';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '黑名单例外规则列表',
         url: gurl + 'query' + ukey  ,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} >', width : 40, align: 'center'},
             {display: '名称', name : 'name', width : '10%'},
             {display: '类型', name : 'type', width : '5%', cc: 'blackType'},
             {display: '品类', name : 'productTypeName',  width : '8%'},
             {display: '品名', name : 'productName',  width : '20%'},
             {display: '事业部', name : 'industryName',  width : '10%'},
             {display: '部门', name : 'departmentName',  width : '10%'},             
             {display: '职员', name : 'stafferName',width : '10%' },
             {display: '销售单号', name : 'outId',width : '10%' },
             {display: '销售开始日期', name : 'beginOutTime',  width : '8%'},
             {display: '销售结束日期', name : 'endOutTime',width : 'auto' }
             ],
         extAtt: {
             name : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', onpress : addBean},
             {id: 'update', bclass: 'update', onpress : updateBean},
             {id: 'del', bclass: 'del',  onpress : delBean},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],         
         <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
     
}
 
function $callBack()
{
    loadForm();
    
    //highlights($("#mainTable").get(0), ['无效'], 'red');
    //highlights($("#mainTable").get(0), ['有效'], 'blue');
    
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
<p:query height="40px"/>

<p:query />
</body>
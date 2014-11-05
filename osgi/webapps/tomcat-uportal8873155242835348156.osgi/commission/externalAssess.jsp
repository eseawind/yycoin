<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="提成管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript">

var gurl = '../commission/commission.do?method=';
var addUrl = '../commission/addCommission.jsp';
var ukey = 'ExternalAssess';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '导入的KPI...',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
             {display: '月份', name : 'month', width : '10%'},
             {display: '职员', name : 'stafferName',  width : '15%'},
             {display: 'kpi指数', name : 'kpi',  width : '8%', toFixed: 2},
             {display: '冻结比例', name : 'ratio', width : '8%', toFixed : 2},
             {display: '应收成本', name : 'cost', sortable : true, width : '8%', toFixed : 2},
             {display: '年度KPI返款', name : 'yearKpi', sortable : true, width : '8%', toFixed : 2},
             {display: '应发提成', name : 'shouldCommission', sortable : true, width : 'auto', toFixed : 2}
             ],
         extAtt: {
             //pid : {begin : '<a title=点击查看明细 href=' + gurl + 'findFinance&id={pid}>', end : '</a>'}
         },
         buttons : [
           
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
}

function addBean(opr, grid)
{
	if (window.confirm('确定准备提成计算,计算需要一段时间?'))
    {
        $.blockUI({ message: $('#loadingDiv'),css:{width: '40%'}}, true);
        
        $l(gurl + 'add' + ukey);
    }
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        if(window.confirm('确定撤销提成统计?'))    
        $ajax(gurl + 'delete' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
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
<p:message/>
<table id="mainTable" style="display: none"></table>
<p:query/>
<div id="loadingDiv" style="display:none">
<p>&nbsp;</p>
<p>月结准备中......</p>
<p><img src="../images/oa/process.gif" /></p>
<p>&nbsp;</p>
</div>
</body>
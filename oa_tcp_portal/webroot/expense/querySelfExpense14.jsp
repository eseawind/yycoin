<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="通用报销管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../tcp/expense.do?method=';
var addUrl = '../tcp/addExpense.jsp';
var ukey = 'Expense';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '通用报销列表',
         url: gurl + 'querySelfExpense&type=14',
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status}>', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '目的', name : 'name', width : '15%'},
             {display: '报销人', name : 'stafferName', width : '8%'},
             {display: '处理人', name : 'processer', width : '8%'},
             {display: '系列', name : 'stype', cc: 'tcpStype', width : '5%'},
             {display: '状态', name : 'status', cc: 'tcpStatus', width : '10%'},
             {display: '费用', name : 'showTotal', sortable: true, cname: 'total', width : '8%'},
             {display: '付款', name : 'showBorrowTotal', sortable: true, cname: 'borrowTotal', width : '8%'},
             {display: '合规标识', name : 'compliance', cc: 'tcpComplianceType', width : '5%'},
             {display: '时间', name : 'logTime', sortable: true, width : 'auto'}
             ],
         extAtt: {
             id : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', onpress : addBean, auth: '0000'},
             {id: 'update', bclass: 'update', onpress : updateBean, auth: '0000'},
             {id: 'del', bclass: 'del',  onpress : delBean, auth: '0000'},
             {id: 'export', bclass: 'replied',  caption: '导出通用模板', onpress : exports},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    highlights($("#mainTable").get(0), ['结束'], 'blue');
    highlights($("#mainTable").get(0), ['驳回'], 'red');
}

function addBean(opr, grid)
{
    $l(gurl + 'preForAdd' + ukey + '&type=14');
    //$l(addUrl);
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && (getRadio('checkb').lstatus == 0 || getRadio('checkb').lstatus == 1))
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

function exports()
{
    document.location.href = '../admin/down.do?method=downTemplateFileByName&fileName=tcp_template.xls';
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=tcp.querySelfExpense');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query height="300px"/>
</body>
<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="凭证管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../finance/finance.do?method=';
var addUrl = '../finance/addFinance.jsp?tempFlag=1';
var ukey = 'TempFinance';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '临时凭证列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status}>', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '类型', name : 'type', cc: 'financeType', width : '10%'},
             {display: '分类', name : 'createType', cc: 'financeCreateType', width : '10%'},
             {display: '状态', name : 'status', cc: 'financeStatus', width : '8%'},
             {display: '纳税实体', name : 'dutyName',  width : '10%'},
             {display: '金额', name : 'showInmoney', width : '10%'},
             {display: '创建人', name : 'createrName', width : '10%'},
             {display: '凭证日期', name : 'financeDate', sortable : true, width : '10%'},
             {display: '创建时间', name : 'logTime', sortable : true, width : 'auto'}
             ],
         extAtt: {
             id : {begin : '<a title=点击查看明细 href=' + gurl + 'findFinance&tempFlag=1&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', onpress : addBean, auth: '1802'},
             {id: 'pass', bclass: 'pass', caption: '提交为正式凭证', onpress : sumbitBean, auth: '1802'},
             {id: 'update', bclass: 'update', onpress : updateBean, auth: '1802'},
             {id: 'del', bclass: 'del',  onpress : delBean, auth: '1802'},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf queryMode="0"/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['未核对'], 'red');
}

function exports()
{
    document.location.href = gurl + 'exportFinance';
}

function addBean(opr, grid)
{
    $l(gurl + 'preForAddFinance&tempFlag=1');
    //$l(addUrl);
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 0)
    {    
        if(window.confirm('确定删除?'))    
        $ajax(gurl + 'delete' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function sumbitBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        if(window.confirm('确定提交为正式凭证?'))    
        $ajax(gurl + 'moveTempFinanceBeanToRelease&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function updateBean()
{
	if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 0)
	{	
		$l(gurl + 'findFinance&tempFlag=1&update=1&id=' + getRadioValue('checkb'));
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
</body>
<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="信用分配管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../admin/staffer.do?method=';
var ukey = 'InvoiceCredit';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '信用分配列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lcredit={credit}>', width : 40, align: 'center'},
             {display: '职员', name : 'stafferName', width : '25%'},
             {display: '事业部', name : 'invoiceName', width : '25%'},
             {display: '信用金额', name : 'credit', toFixed: 2, width : 'auto'}
             ],
         extAtt: {
             //name : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'update', bclass: 'update', caption: '更新信用', onpress : updateBean, auth: '0102'},
             {id: 'export', bclass: 'replied',  caption: '导出所有职员信用', onpress : exports},
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

function updateBean()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
	    $.messager.prompt('配置信用', '请输入信用', getRadio('checkb').lcredit, function(value, isOk){
                if (isOk)
                {
                    if (isFloat(value))
                    {
                        $ajax(gurl + 'updateCredit&id=' + getRadioValue('checkb') + '&credit=' + value, callBackFun);
                    }
                    else
                    {
                        alert('只能输入金额');
                    }
                }
            });
	}
	else
	$error('不能操作');
}

function exports()
{
    if (window.confirm('确定导出导出所有职员信用明细?'))
    document.location.href = '../sail/out.do?method=exportAllStafferCredit';
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
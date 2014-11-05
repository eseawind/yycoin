<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="资金校验管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../finance/finance.do?method=';
var addUrl = '../finance/addFinance.jsp';
var ukey = 'CheckView';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '客户核对列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={checkStatus} ltype={type}>', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '名称', name : 'refId', width : '25%'},
             {display: '类型', name : 'type', cc: 'checkType', width : '10%'},
             {display: '状态', name : 'checkStatus',  cc: 'pubCheckStatus', width : '10%'},
             {display: '职员', name : 'stafferName', width : '10%'},
             {display: '时间', name : 'logTime',  sortable : true, width : 'auto'}
             ],
         extAtt: {
             id : {begin : '<a title=点击查看明细 href="javaScript:openDetail(\'{id}\', {type})">', end : '</a>'}
         },
         buttons : [
             {id: 'pass', bclass: 'pass', caption: '总部核对', onpress : checkBean, auth: '1803'},
             {id: 'update', bclass: 'update', caption: '同步核对单据', onpress : synBean, auth: '1803'},
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

function openDetail(id, type)
{
    var link = '';
    
    if (type == 1)
    {
        link = '../product/product.do?method=findCompose&id=' + id + '&check=1';
    }
    
    if (type == 2)
    {
        link = '../product/product.do?method=findPriceChange&id=' + id + '&check=1';
    }
    
    if (type == 3)
    {
        link = '../finance/bill.do?method=findInBill&id=' + id + '&check=1';
    }
    
    if (type == 4)
    {
        link = '../finance/bill.do?method=findOutBill&id=' + id + '&check=1';
    }
    
    if (type == 5)
    {
        link = '../stock/stock.do?method=findStock&id=' + id + '&check=1';
    }
    
    if (type == 6)
    {
        link = '../sail/out.do?method=findOut&fow=99&outId=' + id + '&check=1';
    }
    
     if (type == 7)
    {
        link = '../client/client.do?method=findClient&update=0&id=' + id + '&check=1';
    }
    
    $l(link + '&ltype=' + type);
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

function synBean(opr, grid)
{
    if(window.confirm('同步需要十几秒,确认同步核对单据?'))    
        $ajax(gurl + 'syn' + ukey, callBackFun);
}

function checkBean()
{
	if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 0)
	{	
		$.messager.prompt('总部核对', '请核对说明', '', function(msg){
                if (msg)
                {
                    $ajax2(gurl + 'checks&id=' + getRadioValue('checkb') + '&type=' + getRadio('checkb').ltype , {'reason' : msg}, 
                        callBackFun);
                }
               
            }, 2);
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
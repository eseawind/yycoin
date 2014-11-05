<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="采购付款审核" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../finance/stock.do?method=';
var addUrl = '';
var ukey = 'StockPayApply';

var allDef = getAllDef();
var guidMap;
var thisObj;

var mode = '<p:value key="mode"/>';

function load()
{
     preload();
     
     guidMap = {
         title: '申请列表',
         url: gurl + 'query' + ukey + '&mode=' + mode,
         colModel : [
             {display: '<input type=checkbox id=flexi_Check onclick=checkAll(this)> 选择', name : 'check', content : '<input type=checkbox name=checkb value={id} lstatus={status} lstafferId={stafferId} lprovideId={provideId} >', width : 40, align: 'center'},
             {display: '采购人', name : 'stafferName', width : '8%'},
             {display: '状态', name : 'status', cc: 'stockPayApplyStatus', width : '10%'},
             {display: '采购', name : 'stockId', content: '{stockId}/{stockItemId}', width : '20%'},
             {display: '供应商', name : 'provideName', width : '15%'},
             {display: '总金额', name : 'moneys', width : '8%', toFixed: 2},
             {display: '纳税实体', name : 'dutyName', width : '10%'},
             {display: '最早付款', name : 'payDate', sortable : true, width : '10%'},
             {display: '时间', name : 'logTime', sortable : true, width : 'auto'}
             ],
         extAtt: {
             stafferName : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}&mode= ' + mode + '>', end : '</a>'}
         },
         buttons : [
             {id: 'pass', caption: '处理',bclass: 'update', auth: '1310,1311,1312', onpress : doProcess},
             {id: 'pass', caption: '合并付款',bclass: 'update', auth: '1310', onpress : composeProcess},
             //{id: 'pass2', caption: '绑定发票',bclass: 'update', auth: '1310', onpress : bindInvoice},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['驳回'], 'red');
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

function doProcess()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {   
        $l(gurl + 'find' + ukey + '&update=1&id=' + getRadioValue('checkb') + '&mode=' + mode);
    }
    else
    $error('不能操作');
}

function composeProcess()
{
    var clis = getCheckBox('checkb');
    
    var pid = '';
    
    if (clis.length > 1)
    {
        var str = '';
        
        for (var i = 0; i < clis.length; i++)
        {
			if (clis[i].lstatus != 0 && clis[i].lstatus != 1)
			{
				alert('只能是初始或驳回态才能合并');
                return false;
			} 
            
            str += clis[i].value + ';';
            
	        if (pid == '')
	        {
	            pid = clis[i].lprovideId; 
	        }
	        else
	        {
	            if (pid != clis[i].lprovideId)
	            {
	                alert('供应商必须一致');
	                return false;
	            }
	        }
        }
        
        if (window.confirm('确定合并选中的付款申请?'))
        {
            $l(gurl + 'compose' + ukey + '&ids=' + str + '&mode=' + mode);
        }
    }
    else
    $error('不能操作');
}

function bindInvoice()
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 4)
    {   
        $l('../finance/invoiceins.do?method=queryInvoiceForConfirmStockPay&id=' + getRadioValue('checkb'));
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
<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="库存管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../depot/storage.do?method=';
var ukey = 'DepotStorageRelation';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '库存列表',
         url: gurl + 'query' + ukey + '&depotId=${param.depotId}',
         colModel : [
             {display: '选择', name : 'check', content : 
             '<input type=radio name=checkb value={id} lamount={amount} ldepotpartId={depotpartId} lproductId={productId} llocationId={locationId} lpriceKey={priceKey}>', 
             		width : 40, align: 'center'},
             {display: '产品', name : 'productName', width : '15%', cname: 'StorageRelationBean.productId', sortable : true},
             {display: '编码', name : 'productCode', width : '15%'},
             {display: '实际/预占/在途', name : 'amount', width : '15%', content: '{amount}/{preassignAmount}/{inwayAmount}', sortable : true},
             {display: '价格', name : 'price', toFixed: 2, sortable : true, width : '10%'},
             {display: '储位', name : 'storageName', width : '10%'},
             {display: '仓区', name : 'depotpartName', width : '10%'},
             {display: '仓库', name : 'locationName', width : '10%'},
             {display: '职员', name : 'stafferName', width : 'auto'}
             ],
         extAtt: {
             //name : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'search', bclass: 'search', onpress : doSearch},
             {id: 'add', bclass: 'add', caption: '申请转移库存',  onpress : applyBean},
             {id: 'search1', bclass: 'search', caption: '仓区下异动历史', onpress : depotpartLog},
             {id: 'search2', bclass: 'search', caption: '仓区下异动(价格)', onpress : depotpartLog2},
             {id: 'search3', bclass: 'search', caption: '仓库下异动历史', onpress : depotLog},
             {id: 'search4', bclass: 'search', caption: '仓库下异动(价格)', onpress : depotLog2},
             {id: 'search5', bclass: 'search', caption: '产品当天进出明细', onpress : currentDetail},
             {id: 'del', bclass: 'del',  onpress : delBean, auth: '1106'}
             ],
         // 预留的标签实例
         usepager: true,
         useRp: true,
         queryMode: 0,
         cache: 0,
         height: 'page',
		 rp: ${g_page},
         queryCondition: null,
         showTableToggleBtn: true,
         auth: $auth(),
         def: allDef,
         callBack: $callBack //for firefox load ext att
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
}


function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') &&getRadio('checkb').lamount == 0)
    {    
        if(window.confirm('确定删除?'))    
        $ajax(gurl + 'deleteStorageRelation&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作,只能删除数量为0的库存');
}

function currentDetail()
{
    $l('../depot/storage.do?method=queryProductInOut&depotId=${param.depotId}');
}

function depotpartLog(opr, grid)
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		$l(gurl + 'queryStorageLog&queryType=1&productId=' 
			+ getRadio('checkb').lproductId + '&depotpartId=' + getRadio('checkb').ldepotpartId);
	}
	else
	$error('不能操作');
}

function depotpartLog2(opr, grid)
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		$l(gurl + 'queryStorageLog&queryType=1&productId=' 
			+ getRadio('checkb').lproductId + '&depotpartId=' + getRadio('checkb').ldepotpartId 
			+ '&priceKey=' + getRadio('checkb').lpriceKey);
	}
	else
	$error('不能操作');
}

function depotLog(opr, grid)
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		$l(gurl + 'queryStorageLog&queryType=2&productId=' 
			+ getRadio('checkb').lproductId + '&locationId=' + getRadio('checkb').llocationId);
	}
	else
	$error('不能操作');
}

function depotLog2(opr, grid)
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		$l(gurl + 'queryStorageLog&queryType=2&productId=' 
			+ getRadio('checkb').lproductId + '&locationId=' + getRadio('checkb').llocationId 
			+ '&priceKey=' + getRadio('checkb').lpriceKey);
	}
	else
	$error('不能操作');
}

function applyBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        $l(gurl + 'preForAddStorageApply' + '&id=' + getRadioValue('checkb'));
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
<p:query height="300px" width="500px"/>
</body>
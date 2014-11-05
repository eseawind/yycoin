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
var addUrl = '../depot/addStorageRelation.jsp';
var ukey = 'StorageRelation';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '我的库存',
         url: gurl + 'querySelf' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : 
             '<input type=radio name=checkb value={id} lamount={amount} ldepotpartId={depotpartId} lproductId={productId} llocationId={locationId} lpriceKey={priceKey}>', 
             		width : 40, align: 'center'},
             {display: '产品', name : 'productName', width : '15%', cname: 'StorageRelationBean.productId', sortable : true},
             {display: '编码', name : 'productCode', width : '15%'},
             {display: '数量', name : 'amount', width : '10%', sortable : true},
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
             {id: 'add', bclass: 'add', caption: '申请转移库存',  onpress : applyBean},
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
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=querySelf' + ukey);
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
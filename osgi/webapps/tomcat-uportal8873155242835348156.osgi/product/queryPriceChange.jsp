<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
.flexigrid div.fbutton .draw
{
    background: url(../css/flexigrid/images/get.png) no-repeat center left;
}

.flexigrid div.fbutton .odraw
{
    background: url(../css/flexigrid/images/oget.png) no-repeat center left;
} 
</style>
<p:link title="调价管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../product/product.do?method=';
var addUrl = '../product/addProduct.jsp';
var ukey = 'PriceChange';

var checkStr = '';

if (containInList($auth(), '1803'))
{
    checkStr = '&check=1';
}

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '<span id=tt_title>调价列表</span>',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status}>', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '状态', name : 'status', cc : 'priceChangeStatus', width : '10%'},
             {display: '核对', name : 'checkStatus', cc: 'pubCheckStatus', width : '8%'},
             {display: '调价人', name : 'stafferName', width : '10%'},
             {display: '原因', name : 'description', width : '20%'},
             {display: '时间', name : 'logTime', content: '{logTime}' ,cname: 'logTime',  sortable : true, width : 'auto'}
             ],
         extAtt: {
             id : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}' + checkStr + '>', end : '</a>'}
         },
         buttons : [
             {id: 'update1', bclass: 'edit', caption: '锁定库存', onpress : lock, auth: '1006'},
             {id: 'add', bclass: 'add', caption: '产品调价', onpress : openImport, auth: '1006x'},
             {id: 'del', bclass: 'odraw',  caption: '调价回滚', onpress : rollback, auth: '1006x'},
             {id: 'update2', bclass: 'edit',  caption: '解锁库存', onpress : unlock, auth: '1006'},
             {id: 'pass', bclass: 'pass', caption: '总部核对', onpress : checkBean, auth: '1803'},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
         <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
     
     $('#dlg').dialog({
                modal:true,
                closed:true,
                buttons:{
                    '确 认':function(){
                        addBean();
                    },
                    '取 消':function(){
                        $('#dlg').dialog({closed:true});
                    }
                }
     });
     
     $ESC('dlg');
}

function addBean(opr, grid)
{
    document.forms[0].submit();
}

function openImport()
{
    $('#dlg').dialog({closed:false});
}

function checkBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {   
        $l('../product/product.do?method=findPriceChange&id=' + getRadioValue('checkb') + '&check=1');
    }
    else
    $error('不能操作');
}

function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['回滚', '未核对'], 'red');
    
    $ajax(gurl + 'findStorageRelationStatus', callBackFunRelatio);
}

function lock(opr, grid)
{
    if(window.confirm('确定锁定库存,锁定库存期间任何采购销售都不能进行,请确认?'))
    $ajax(gurl + 'lockStorageRelation', callBackFunReal);
}

function unlock(opr, grid)
{
    if(window.confirm('确定解锁库存,解锁库存可以恢复采购销售,请确认?'))
    $ajax(gurl + 'unlockStorageRelation', callBackFunReal);
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=query' + ukey);
}

function callBackFunRelatio(data)
{
    if (data.ret == 0)
    $O('tt_title').innerHTML = '调价列表 (库存状态: ' + data.msg + ')';
    
}

function callBackFunReal(data)
{
    callBackFun(data);
    
    $ajax(gurl + 'findStorageRelationStatus', callBackFunRelatio);
}

function rollback(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 0)
    {    
        if(window.confirm('确定回滚调价?'))    
        $ajax(gurl + 'rollbackPriceChange&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

</script>
</head>
<body onload="load()" class="body_class">
<form id="mainForm" method="post" action="../product/product.do?method=preForAddPriceChange">
<p:cache></p:cache>
<p:message/>
<table id="mainTable" style="display: none"></table>
<div id="dlg" title="输入调价产品编码(最多100)" style="width:320px;">
    <div style="padding:20px;height:300px;" id="dia_inner" title="">
    <textarea name="products" cols="30" rows="20"></textarea>
   </div>
</div>
</form>
<p:query/>
</body>

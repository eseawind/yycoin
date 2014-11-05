<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="库存申请" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../depot/storage.do?method=';
var ukey = 'StorageApply';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '库存申请列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status}>', width : 40, align: 'center'},
             {display: '产品', name : 'productName', content: '{productName}', width : '15%'},
             {display: '数量', name : 'amount', width : '5%'},
             {display: '价格', name : 'price', width : '10%', toFixed: 2},
             {display: '申请人', name : 'applyName', width : '10%'},
             {display: '描述', name : 'description', width : '30%'},
             {display: '时间', name : 'logTime', content: '{logTime}' ,cname: 'logTime',  sortable : true, width : 'auto'}
             ],
         extAtt: {
             //id : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
         	 {id: 'pass', bclass: 'pass', caption: '接受转移', onpress : passBean},
             {id: 'reject', bclass: 'reject', caption: '废弃转移', onpress : deleteBean}
             ],
         <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
}

function passBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 0)
    {    
        if(window.confirm('接受后此产品只能自己销售,需要承担销售风险，确定接受此库存？'))    
        $ajax(gurl + 'pass' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}


function deleteBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus != 3)
    {    
        if(window.confirm('确定废弃此申请?'))    
        $ajax(gurl + 'reject' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
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

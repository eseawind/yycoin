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
<p:link title="合成管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../product/product.do?method=';
var addUrl = '../product/addProduct.jsp';
var ukey = 'Compose';

var checkStr = '';

<c:if test="${param.foward == 3}">
if (containInList($auth(), '1803'))
{
    checkStr = '&check=1';
}
</c:if>

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '产品合成列表',
         url: gurl + 'query' + ukey + '&foward=${param.foward}',
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} ltype={type} lparentId={parentId}>', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '产品', name : 'productName', content: '{productName}({productCode})', width : '15%'},
             {display: '数量', name : 'amount', width : '5%'},
             {display: '价格', name : 'price', width : '8%', toFixed: 2},
             {display: '核对', name : 'checkStatus', cc: 'pubCheckStatus', width : '8%'},
             {display: '类型', name : 'type', cc : 'composeType', width : '8%'},
             {display: '状态', name : 'status', cc : 'composeStatus', width : '15%'},
             {display: '合成人', name : 'stafferName', width : '8%'},
             {display: '时间', name : 'logTime', content: '{logTime}' ,cname: 'logTime',  sortable : true, width : 'auto'}
             ],
         extAtt: {
             id : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}' + checkStr + '>', end : '</a>'}
         },
         buttons : [
             <c:if test="${param.foward == 0}">
             {id: 'oget1', bclass: 'odraw', caption: '合成回滚', onpress : rollback, auth: '100501'},
             </c:if>
             <c:if test="${param.foward == 1}">
         	 {id: 'pass', bclass: 'pass', caption: '生产部经理通过', onpress : passBean, auth: '100502'},
         	 </c:if>
         	 <c:if test="${param.foward == 2}">
         	 {id: 'pass1', bclass: 'pass', caption: '运营总监通过', onpress : lastPassBean, auth: '100503'},
         	 </c:if>
         	 <c:if test="${param.foward == 1 || param.foward == 2}">
             {id: 'reject', bclass: 'reject', caption: '废弃', onpress : deleteBean, auth: '100502,100503'},
             </c:if>
             <c:if test="${param.foward == 3}">
             {id: 'passCheck', bclass: 'pass', caption: '总部核对', onpress : checkBean, auth: '1803'},
             </c:if>
             {id: 'search', bclass: 'search', onpress : doSearch},
             {id: 'export', bclass: 'replied',  caption: '导出结果', onpress : exports}
             ],
         <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['合成', '已合成'], 'blue');
    
    highlights($("#mainTable").get(0), ['分解', '未核对'], 'red');
}

function checkBean()
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 3)
    {   
        $l('../product/product.do?method=findCompose&id=' + getRadioValue('checkb') + '&check=1');
    }
    else
    $error('不能操作');
}

function rollback(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 3 && getRadio('checkb').ltype == 0 && getRadio('checkb').lparentId == '')
    {    
        if(window.confirm('确定回滚合成?'))    
        $ajax(gurl + 'rollbackComposeProduct&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function passBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 0)
    {    
        if(window.confirm('确定通过此合成单?'))    
        $ajax(gurl + 'pass' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function lastPassBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 2)
    {    
        if(window.confirm('确定通过此合成单?'))    
        $ajax(gurl + 'lastPass' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function deleteBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus != 3)
    {    
        if(window.confirm('确定废弃此合成单?'))    
        $ajax(gurl + 'delete' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function exports()
{
    document.location.href = gurl + 'exportCompose';
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

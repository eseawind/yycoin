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
var ukey = 'Decompose';

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
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} ltype={type}>', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '产品', name : 'productName', content: '{productName}({productCode})', width : '15%'},
             {display: '数量', name : 'amount', width : '5%'},
             {display: '价格', name : 'price', width : '8%', toFixed: 2},
             {display: '类型', name : 'type', cc : 'storageType', width : '8%'},
             {display: '状态', name : 'status', cc : 'composeStatus', width : '15%'},
             {display: '操作人', name : 'stafferName', width : '8%'},
             {display: '时间', name : 'logTime', content: '{logTime}' ,cname: 'logTime',  sortable : true, width : 'auto'}
             ],
         extAtt: {
             id : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}' + checkStr + '>', end : '</a>'}
         },
         buttons : [
         	 <c:if test="${param.foward == 1}">
         	 {id: 'pass1', bclass: 'pass', caption: '处理', onpress : lastPassBean},
         	 </c:if>
         	{id: 'export', bclass: 'replied',  caption: '导出', onpress : exports},
             {id: 'search', bclass: 'search', onpress : doSearch}
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

function lastPassBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 4)
    {    
        if(window.confirm('确定处理此拆分单?'))    
        //$ajax(gurl + 'pass' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
            $l(gurl + 'find' + ukey + '&update=2&id=' + getRadioValue('checkb'));
    }
    else
    $error('不能操作');
}

function exports()
{
    document.location.href = gurl + 'exportDecompose';
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

<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="信用使用管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../sail/out.do?method=';
var ukey = 'SelfCredit';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '信用使用列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
             {display: '销售单', name : 'fullId', width : '20%'},
             {display: '行业', name : 'industryId', width : '10%'},
             {display: '客户担保金额', name : 'curcredit', width : '15%', toFixed: 2},
             {display: '职员担保金额', name : 'staffcredit', width : '15%', toFixed: 2},
             {display: '信用担保担保金额', name : 'managercredit', width : '15%', toFixed: 2},
             {display: '已收金额', name : 'hadPay', width : '15%', toFixed: 2},
             {display: '职员', name : 'stafferId', width : '10%'},
             {display: '时间', name : 'outTime', width : 'auto'}
             ],
         extAtt: {
             fullId : {begin : '<a href=../sail/out.do?method=findOut&fow=99&outId={fullId}>', end : '</a>'}
         },
         buttons : [
             {id: 'search', bclass: 'search', caption: '信用明细', onpress : doSearch}
             ],
         <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
     
     $('#dlg').dialog({
                modal:true,
                closed:true,
                buttons:{
                    '确 认':function(){
                        $('#dlg').dialog({closed:true});
                    }
                }
     });
     
     $ESC('dlg');
}
 
function $callBack()
{
    loadForm();
    
}

function doSearch()
{
    $ajax('../sail/out.do?method=findCreditDetail', callBackFunLocation);
}

function callBackFunLocation(data)
{
    $O('dia_inner').innerHTML = data.msg;
    
    $('#dlg').dialog({closed:false});
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<div id="dlg" title="信用明细" style="width:320px;">
    <div style="padding:20px;height:200px;" id="dia_inner" title="">
   </div>
</div>
<p:query/>
</body>
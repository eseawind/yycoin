<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="空开空退管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../sail/out.do?method=';
var addUrl = '../sail/out.do?method=';
var ukey = 'ApproveOutRepaireApply';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '销售单空开空退审批',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} >', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '销售单', name : 'outId', width : '15%'},
             {display: '原因', name : 'reason',  width : '25%', cc : 'outRepaireReason'},
             {display: '申请人', name : 'stafferName',  width : '10%'},
             {display: '状态', name : 'status',  width : '10%', cc : 'outRepaireStatus' },
             {display: '时间', name : 'logTime',  width : 'auto'}
             ],
         extAtt: {
             id : {begin : '<a href=../sail/out.do?method=findOut&fow=922&outId={outId}&repaireId={id}>', end : '</a>'},
             outId : {begin : '<a href=../sail/out.do?method=findOut&fow=99&outId={outId}>', end : '</a>'}
         },
         buttons : [
             {id: 'pass', bclass: 'pass', caption: '通过', onpress : passBean},
             {id: 'reject', bclass: 'reject',  caption: '驳回', onpress : rejectBean},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    //highlights($("#mainTable").get(0), ['不开票'], 'red');
}

function passBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if(window.confirm('确定通过此申请?'))    
        $ajax('../sail/out.do?method=passOutRepaireApply&id=' + $$('checkb'), callBackFun);
    }
}

function rejectBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        $.messager.prompt('销售单空开空退', '请输入驳回原因', '',
            function(value, isOk)
            {
                if (isOk)
                {
	                $ajax2('../sail/out.do?method=rejectOutRepaireApply&id=' + $$('checkb'), {'reason': value}, callBackFun);
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
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>
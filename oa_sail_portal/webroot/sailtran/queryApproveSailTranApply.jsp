<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="结算价格管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../sail/tran.do?method=';
var addUrl = '../sail/tran.do?method=';
var ukey = 'ApproveSailTranApply';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '销售单移交审批',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} ppareid={pareId}>', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '销售单', name : 'outId', width : '15%'},
             {display: '客户', name : 'customerName',  width : '25%'},
             {display: '申请人', name : 'stafferName',  width : '10%'},
             {display: '原拥有人', name : 'oldStafferName',  width : '10%'},
             {display: '时间', name : 'logTime',  width : 'auto'}
             ],
         extAtt: {
             id : {begin : '<a href=' + gurl + 'findSailTranApply&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'pass', bclass: 'pass', caption: '通过', onpress : passBean, auth: '1420'},
             {id: 'reject', bclass: 'reject',  caption: '驳回', onpress : rejectBean, auth: '1420'},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    highlights($("#mainTable").get(0), ['不开票'], 'red');
}

function passBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if(window.confirm('确定通过此申请?'))    
        $ajax('../sail/tran.do?method=passApply&id=' + $$('checkb'), callBackFun);
    }
}

function rejectBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        $.messager.prompt('销售单移交', '请输入驳回原因', '',
            function(value, isOk)
            {
                if (isOk)
                {
	                $ajax2('../sail/tran.do?method=rejectApply&id=' + $$('checkb'), {'reason': value}, callBackFun);
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
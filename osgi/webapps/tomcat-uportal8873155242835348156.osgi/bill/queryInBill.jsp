<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="收款单管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../finance/bill.do?method=';
var addUrl = '../finance/addInBill.jsp';
var ukey = 'InBill';

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
         title: '收款单列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} lcheck={checkStatus} llock={lock} lctype={createType}>', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '帐号', name : 'bankName', width : '20%'},
             {display: '类型', name : 'type', cc: 'inbillType', width : '5%'},
             {display: '状态', name : 'status', cc: 'inbillStatus', width : '5%'},
             {display: '核对', name : 'checkStatus', cc: 'pubCheckStatus', width : '5%'},
             {display: '金额', name : 'moneys',  toFixed: 2, width : '8%'},
             {display: '客户', name : 'customerName', width : '12%'},
             {display: '创建', name : 'createType', cc: 'billCreateType', width : '5%'},
             {display: '职员/更新', name : 'ownerName', content: '{ownerName}/{updateId}', sortable : true, cname: 'updateId', width : '8%'},
             {display: '销售单', name : 'outId', width : '12%'},
             {display: '时间', name : 'logTime', sortable : true, width : '15%'}
             ],
         extAtt: {
             id : {begin : '<a title=点击查看明细 href=' + gurl + 'find' + ukey + '&id={id}' + checkStr + '>', end : '</a>'},
             outId : {begin : '<a href=../sail/out.do?method=findOut&fow=99&outId={outId}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', onpress : addBean, auth: '1603'},
             {id: 'add2', bclass: 'add', caption: '创建关联凭证', onpress : addFinance, auth: '1802'},
             //{id: 'update', bclass: 'update', caption: '分拆预收', auth: '1603', onpress : splitInBill},
             {id: 'update2', bclass: 'update', caption: '总部核对', auth: '1803', onpress : updateInBillBeanChecks},
             {id: 'del', bclass: 'delete', auth: '1603', onpress : delBean},
             {id: 'export', bclass: 'replied',  caption: '导出查询结果', onpress : exports},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['未核对'], 'red');
}

function addBean(opr, grid)
{
    $l(gurl + 'preForAdd' + ukey);
    //$l(addUrl);
}

function addFinance(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') 
        && getRadio('checkb').lctype == 1 
        && getRadio('checkb').lcheck == 0)
    {
        $l('../finance/finance.do?method=preForAddFinance&refId=' + getRadioValue('checkb'));
    }
    else
    $error('只有手工创建的单据且未核对才能操作');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').llock == 0)
    {    
        if(window.confirm('确定删除?'))    
        $ajax(gurl + 'delete' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function splitInBill(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 2)
    {    
        $.messager.prompt('分拆预收', '请输入分拆金额(只能是数字)', 0.0,
            function(value, isOk)
            {
                if (isOk)
                if (isFloat(value))
                $ajax('../finance/bill.do?method=splitInBill&id=' + $$('checkb') + '&newMoney=' + value, callBackFun);
                else
                $error('只能输入数字');           
            });
    }
    else
    $error('不能操作');
}

function updateInBillBeanChecks()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    $l(gurl + 'find' + ukey + '&id=' + getRadioValue('checkb') + '&check=1');
    else
    $error('不能操作');
}

function updateInBillBeanChecks2()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {   
        $.messager.prompt('总部核对', '请输入核对说明', '', function(msg){
                if (msg)
                {
                    $ajax('../finance/bill.do?method=updateInBillBeanChecks&id=' 
                        + getRadioValue('checkb') + '&checks=' + ajaxPararmter(msg) , 
                        callBackFun);
                }
               
            }, 2);
    }
    else
    $error('不能操作');
}

function exports()
{
    document.location.href = '../finance/bill.do?method=exportInBill';
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
<p:query height="350px"/>
</body>
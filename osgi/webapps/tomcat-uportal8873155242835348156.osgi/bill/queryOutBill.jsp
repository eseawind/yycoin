<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="付款单管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../finance/bill.do?method=';
var addUrl = '../finance/addOutBill.jsp';
var ukey = 'OutBill';

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
         title: '付款单列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} lcheck={checkStatus} llock={lock} lctype={createType}>', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '帐号', name : 'bankName', sortable : true, cname: 'bankId', width : '18%'},
             {display: '类型', name : 'type', cc: 'outbillType', sortable : true , width : '8%'},
             {display: '核对', name : 'checkStatus', cc: 'pubCheckStatus', sortable : true, width : '5%'},
             {display: '金额', name : 'moneys',  toFixed: 2, sortable : true, width : '5%'},
             {display: '单位', name : 'provideName', sortable : true, cname: 'provideId', width : '10%'},
             {display: '创建', name : 'createType', cc: 'billCreateType', width : '5%'},
             {display: '职员', name : 'ownerName', sortable : true, cname: 'ownerId', width : '5%'},
             {display: '状态', name : 'status', cc: 'outbillStatus', width : '8%'},
             {display: '时间', name : 'logTime', sortable : true, width : 'auto'}
             ],
         extAtt: {
             id : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}' + checkStr + '>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', onpress : addBean, auth: '1607'},
             {id: 'add2', bclass: 'add', caption: '创建关联凭证', onpress : addFinance, auth: '1802'},
             {id: 'update', bclass: 'update', caption: '总部核对', auth: '1803', onpress : updateOutBillBeanChecks},
             {id: 'del', bclass: 'delete', auth: '1607', onpress : delBean},
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

function updateOutBillBeanChecks()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    $l(gurl + 'find' + ukey + '&id=' + getRadioValue('checkb') + '&check=1');
    else
    $error('不能操作');
}

function updateOutBillBeanChecks2()
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 0)
    {   
        $.messager.prompt('总部核对', '请输入核对说明', '', function(msg){
                if (msg)
                {
                    $ajax('../finance/bill.do?method=updateOutBillBeanChecks&id=' 
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
    document.location.href = '../finance/bill.do?method=exportOutBill';
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
<p:query height="300px"/>
</body>
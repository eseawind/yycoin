<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="信用等级管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '信用等级列表',
         url: '../credit/credit.do?method=queryCreditLevel',
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lmoney={money}>', width : 40, align: 'center'},
             {display: '信用等级', name : 'name', width : '30%'},
             {display: '等级分下限', name : 'min', width : '20%', toFixed: 2},
             {display: '等级分上限', name : 'max', width : '20%', toFixed: 2},
             {display: '客户数量', name : 'customerAmount', width : '10%'},
             {display: '开单额度', name : 'money', width : 'auto', toFixed: 2}
             ],
         extAtt: {
             
         },
         buttons : [
             {id: 'update', bclass: 'update', caption: '修改额度', onpress : updateBean, auth: '0902'}
             ],
         <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $.messager.prompt('修改开单额度', '请输入开单额度(只能是数字)', getRadio('checkb').lmoney,
            function(value, isOk)
            {
                if (isOk)
                if (isFloat(value))
                $ajax('../credit/credit.do?method=updateCreditLevel&id=' + $$('checkb') + '&newMoney=' + value, callBackFun);
                else
                $error('只能输入数字');           
            });
    }
    else
    $error('不能操作');
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
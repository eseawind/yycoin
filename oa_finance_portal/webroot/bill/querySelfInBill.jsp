<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="客户预收管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../bill_js/billdlg.js"></script>
<script type="text/javascript">

var gurl = '../finance/bill.do?method=';
var addUrl = '../finance/addInBill.jsp';
var ukey = 'InBill';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

var customerId = "<p:value key='customerId'/>";

var mode = "<p:value key='mode' />";

function load()
{
     preload();
     
     guidMap = {
         title: '收款单列表',
         url: gurl + 'querySelf' + ukey+'&customerId=' + customerId + '&mode=' + mode,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} lfreeze={freeze}>', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '帐号', name : 'bankName', width : '20%'},
             {display: '管理属性', name : 'mtype', cc: 'pubManagerType', width : '5%'},
             {display: '类型', name : 'type', cc: 'inbillType', width : '8%'},
             {display: '状态', name : 'status', cc: 'inbillStatus', width : '5%'},
             {display: '金额', name : 'moneys',  toFixed: 2, width : '8%'},
             {display: '客户', name : 'customerName', width : '20%'},
             {display: '职员', name : 'ownerName', width : '5%'},
             {display: '冻结', name : 'freeze', width : '5%', cc: 'billFreezeType'},
             {display: '时间', name : 'logTime', sortable : true, width : 'auto'}
             ],
         extAtt: {
             id : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
              <c:if test="${mode == '0'}">
              // {id: 'update', bclass: 'update', caption: '申请预收退款', onpress : splitInBill},
              {id: 'update1', bclass: 'update', caption: '预收转费用', onpress : splitInBill2},
              {id: 'update2', bclass: 'update', caption: '移交客户下所有预收', onpress : changeBill},
              {id: 'update3', bclass: 'update', caption: '预收拆分转移', onpress : splitInBill3},
              {id: 'update31', bclass: 'update', caption: '预收批量拆分', onpress : splitInBill31,  auth: '1626'},
              {id: 'update4', bclass: 'update', caption: '预收冻结', onpress : splitInBill4},
              </c:if>

             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
	highlights($("#mainTable").get(0), ['已冻结'], 'red');
	
    loadForm();
    
}

function splitInBill(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        $.messager.prompt('申请预收退款', '请输入预收退款金额(只能是数字)', 0.0,
            function(value, isOk)
            {
                if (isOk)
                if (isFloat(value))
                {
                	$.messager.prompt('申请预收退款事由', '请输入退款事由，不能为空', "",
                            function(reason, isOk)
                            {
                                if (isOk)
                                if (reason!="" && reason!=null && reason.length>0)
                                {
                                $ajax(encodeURI('../finance/bill.do?method=splitInBill&id=' + $$('checkb') + '&newMoney=' + value+'&reason='+reason), callBackFun);
                                }
                                else
                                $error('申请预收退款事由不能为空!');           
                            });
                }
                else
                $error('只能输入数字');           
            });
    }
    else
    $error('不能操作');
}

function splitInBill2(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {   
        openCheckDiv();
        
        if (1 == 2)
        $.messager.prompt('预收转费用', '请输入备注', '',
            function(value, isOk)
            {
                if (isOk)
                $ajax2('../finance/bank.do?method=drawPayment4&billId=' + $$('checkb'), {'reason' : value},  callBackFun);
            });
    }
    else
    $error('不能操作');
}

function splitInBill3(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {        
        $l('../finance/bank.do?method=queryForDrawPayment5&billId=' + $$('checkb'));
    }
    else
    $error('不能操作');
}

function splitInBill31(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {        
        $l('../finance/bank.do?method=queryForDrawPayment51&billId=' + $$('checkb'));
    }
    else
    $error('不能操作');
}

function checkSubmit(descrition, refMoney)
{
    if (descrition == '' || refMoney == '')
    {
        alert('备注和金额不能为空');
        
        return false;
    }
    
    if (!isFloat(refMoney))
    {
        $error('只能输入数字');
        return false;
    }
    
    closeCheckDiv();
    
    $ajax2('../finance/bank.do?method=drawPayment4&billId=' + $$('checkb'), {'reason' : descrition, "refMoney" : refMoney},  callBackFun);
}

function changeBill(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
	    if (window.confirm('确定是否移交此客户下所有的预收?'))
	    {
	        $ajax('../finance/bill.do?method=changeBill&billId=' + $$('checkb'), callBackFun);
	    }
    }
    else
    $error('不能操作');
}

function splitInBill4(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        var freeze = 0;

		freeze = getRadio('checkb').lfreeze;
        
		if (freeze == 1)
		{
			$error('当前已是冻结状态');

			return;
		}
        
        $.messager.prompt('预收冻结', '请输入预收冻结金额(只能是数字)', 0.0,
            function(value, isOk)
            {
                if (isOk)
                if (isFloat(value))
                {
                	// 冻结出来的预收打上标记
                	$ajax('../finance/bill.do?method=splitInBill4&id=' + $$('checkb') + '&freezeMoney=' + value, callBackFun);
                }
                else
                $error('只能输入数字');           
            });
    }
    else
    $error('不能操作');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=querySelf' + ukey);
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query height="300"/>
</body>
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

var mode = "<p:value key='mode' />";

function load()
{
     preload();
     
     guidMap = {
         title: '收款单列表',
         url: gurl + 'querySelfCustomer' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={customerId} lcustomerName={customerName} ltotalMoney={totalMoney} lcommonTotalMoney={commonTotalMoney} lmanageTotalMoney={manageTotalMoney} >', width : 40, align: 'center'},
             {display: '客户', name : 'customerName', width : '20%'},
             {display: '预收', name : 'totalMoney', toFixed: 2, width : '8%'},
             {display: '普通类预收', name : 'commonTotalMoney', toFixed: 2, width : '8%'},
             {display: '管理类预收', name : 'manageTotalMoney',  toFixed: 2, width : '8%'},
             {display: '冻结预收', name : 'freezeTotalMoney', toFixed: 2, width : '8%'},
             {display: '职员', name : 'stafferName', width : '5%'},
             {display: '最近预收日期', name : 'latestDate', sortable : true, width : 'auto'}
             ],
         extAtt: {
    	 	customerName : {begin : '<a href=../bill/querySelf' + ukey + '.jsp?menu=1&customerId={customerId}&mode=' + mode + '>', end : '</a>'}
         },
         buttons : [
			 <c:if test="${mode == '1'}">
			 {id: 'update', bclass: 'update', caption: '采购退货确认', onpress : splitProviderInBill,  auth: '1632'},
			 </c:if>
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
	//highlights($("#mainTable").get(0), ['已冻结'], 'red');
	
    loadForm();
    
}

function refInBill(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {        
        $l('../finance/bank.do?method=queryForDrawPayment6&customerId=' + $$('checkb'));
    }
    else
    $error('不能操作');
}

function splitProviderInBill(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {        
        $l('../sail/out.do?method=queryPurchaseBack&firstLoad=1&customerId=' + $$('checkb') + '&customerName=' + getRadio('checkb').lcustomerName + '&total=' + getRadio('checkb').ltotalMoney);
    }
    else
    $error('不能操作');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=querySelfCustomer' + ukey);
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
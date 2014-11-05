<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="客户管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

var updatFlag = window.top.topFrame.containAuth('0202') ? '1' : '0';
function load()
{
     preload();
     
	 guidMap = {
		 title: '分公司客户列表',
		 url: '../client/client.do?method=queryLocationClient',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lname={name}>', width : 40, sortable : false, align: 'center'},
		     {display: '客户', name : 'name', width : '20%', sortable : true, align: 'left', cname: 'id'},
		     {display: '业务员', name : 'stafferName', width : '10%', sortable : false, align: 'left'},
		     {display: '编码', name : 'code', width : '8%', sortable : false, align: 'left'},
		     //{display: '联系人', name : 'connector', width : '10%', sortable : false, align: 'left'},
		     {display: '类型', name : 'selltype', width : '8%', sortable : false, align: 'left', cc: 101},
		     {display: '类型1', name : 'selltype1', width : '8%', sortable : false, align: 'left', cc: 124},
		     {display: '积分', name : 'creditVal', width : '8%', toFixed: 2},
		     {display: '信用更新', name : 'creditUpdateTime', width : '8%', sortable : false, align: 'left'},
		     {display: '时间', name : 'createTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../client/client.do?method=findClient&id={id}&update=0>', end : '</a>'}
		 },
		 buttons : [
		     //{id: 'queryHis', caption: '客户修改历史', bclass: 'history', onpress : queryHis, auth: '0206'},
		     //{id: 'table', caption: '客户分布', bclass: 'table', onpress : queryCustomerDistribute, auth: '0209'},
		     //{id: 'table1', caption: '所有职员客户分布', bclass: 'table', onpress : queryAllStafferCustomerDistribute, auth: '0209'},
		     //{id: 'syn', caption: '客户分公司同步', bclass: 'table', onpress : synAll, auth: '0210'},
		     {id: 'queryCreditLog', caption: '信用变更日志', bclass: 'search', onpress : queryCreditLog, auth: '0219'},
		     {id: 'queryCredit', caption: '信用明细', bclass: 'search', onpress : queryCredit, auth: '0219'},
		     //{id: 'queryVistor', caption: '拜访记录', bclass: 'search', onpress : queryVistor},
		     {id: 'search', bclass: 'search', onpress : doSearch}
		     ],
		 <p:conf callBack="loadForm" queryMode="0"/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }
 
function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryLocationClient');
}

function queryCustomerDistribute()
{
    $l('../client/client.do?method=queryCustomerDistribute');
}

function queryAllStafferCustomerDistribute()
{
    $l('../client/client.do?method=queryAllStafferCustomerDistribute');
}

function synAll()
{
    if (window.confirm('确定全量同步客户分公司属性?'))
    {
        $.blockUI({ message: '同步中......' });
        $ajax('../client/client.do?method=synchronizationAllCustomerLocation', callBackFun);
    }
}


function queryHis()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
         $l('../customer/queryHisCustomer.jsp?id=' + getRadioValue('checkb') + '&menu=1');
    }
}

function queryVistor()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
         $l('../customer/queryWorkCustomer.jsp?targerId=' + getRadioValue('checkb') + '&menu=1');
    }
    else
    {
        $error();
    }
}

function queryCreditLog()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
         $l('../credit/queryCustomerCreditLog.jsp?targerId=' + getRadioValue('checkb') + '&menu=1');
    }
    else
    {
        $error();
    }
}

function queryCredit()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
         $l('../credit/queryCustomerCredit.jsp?targerId=' + getRadioValue('checkb') + '&menu=1');
    }
    else
    {
        $error();
    }
}

function delBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('确定申请删除--' + getRadio('checkb').lname))
        {
            $ajax('../client/client.do?method=addDelApplyCustomer&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}

function callBackFun(data)
{
    $.unblockUI();
    reloadTip(data.msg, data.ret == 0);

    //if (data.ret == 0)
    //commonQuery();
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../client/client.do?method=findCustomer&update=1&id=' + getRadioValue('checkb'));
    }
}

function configCredit(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../credit/customer.do?method=preForConfigStaticCustomerCredit&cid=' + getRadioValue('checkb'));
    }
}

function commonQuery(par)
{
    gobal_guid.p.queryCondition = par;
    
    gobal_guid.grid.populate(true);
}
</script>
</head>
<body onload="load()" class="body_class">
<form>
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>
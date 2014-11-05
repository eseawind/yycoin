<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="客户回收管理" link="true" guid="true" cal="true" dialog="true"/>
<script src="../js/common.js"></script>
<script src="../js/cnchina.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;

var lbuffalo = window.top.topFrame.gbuffalo;

var guidMap;
var thisObj;

function load()
{
	preload();
    guidMap = {
         title: '客户列表',
         url: '../client/client.do?method=queryClientAssign',
         colModel : [
                     {display: '选择', name : 'check', content : '<input type=checkbox id={customerId} name=checkb value={customerId}>', width : 40, sortable : false, align: 'center'},
                     {display: '客户', name : 'customerName', width : '20%', sortable : false, align: 'left'},
                     {display: '编码', name : 'customerCode', width : '10%', sortable : false, align: 'left'},
                     {display: '所属职员', name : 'stafferName', width : '10%', sortable : false, align: 'left'},
                     {display: '类型', name : 'sellType', width : '10%', sortable : false, align: 'left', cc: 101},
                     {display: '时间', name : 'loginTime', width : 'auto', sortable : true, align: 'left'}
                     ],
         extAtt: {
             customerName : {begin : '<a href=../client/client.do?method=findClient&id={customerId}>', end : '</a>'}
         },
         buttons : [
             {id: 'del', caption: '回收客户', bclass: 'delete', onpress : reclaimBeans, auth: 'A0211'},
             {id: 'queryHis', caption: '回收职员所有客户', bclass: 'history', onpress : queryHis, auth: '0211'},
             {id: 'table', caption: '职员客户分布', bclass: 'table', onpress : queryCustomerDistribute, auth: '0209'},
             {id: 'batchTrans', caption: '批量回收客户', bclass: 'history', onpress : batchTrans, auth: '0211'},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
         <p:conf callBack="loadForm" queryMode="1"/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);	 
}
 
function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryCustomerAssign');
}

function queryCustomerDistribute()
{
    $l('../client/client.do?method=queryStafferClientDistribute');
}

function reclaimBeans(flag)
{
	var type = '';

	var mess = '确定回收选择的客户?';
	
	if (flag && flag == '1')
	{
		type = '1';

		mess = '确定批量回收选择的客户?';
	}

	var destStafferId = $$('destStafferId1');
	
    var clis = getCheckBox('checkb');
    
    if (clis.length > 0)
    {
        var str = '';
        for (var i = 0; i < clis.length; i++)
        {
            str += clis[i].value + '~';
        }
        
        if (window.confirm(mess))
        {
            $ajax('../client/client.do?method=reclaimAssignClient&customerIds=' + str + '&type=' + type + '&destStafferId='+destStafferId, callBackFun);
        }
    }
}


function queryHis()
{
    //var id = '${user.locationId}';
    
    var id = '${user.stafferId}';
    
    if ($O('staffer').options.length > 0)
    {
        $.blockUI({ message: $('#stafferList'),css:{width: '40%'}});
        return;
    }

    lbuffalo.remoteCall("commonManager.queryAllSubStaffers",[id], function(reply) {
                var result = reply.getResult();
                
                var sList = result;
                
                removeAllItem($O('staffer'));
                
                $O('staffer').size = 10;
                
                for (var i = 0; i < sList.length; i++)
                {
                    setOption($O('staffer'), sList[i].id,  sList[i].name);
                }
                
                $.blockUI({ message: $('#stafferList'),css: { width: '40%'}});
        });
}

function $process(flag)
{
    var sid = $$('stafferId');
    
    if (sid == '')
    {
        alert('请选择需要回收的职员');
        return;
    }    

	var destStafferId = $$('destStafferId');

	if (flag == '3')
	{
		if (destStafferId == '')
		{
			alert('请选择需要接收客户的职员');
			return;
		}
	}

	if (flag == '0')
	{
		if (destStafferId != '')
		{
			alert('请取消选择接收客户的职员');
			return;
		}
	}
    
    if (window.confirm('确定回收选择职员的客户?'))
    {
	    $ajax('../client/client.do?method=reclaimStafferClient&stafferId=' + sid + '&flag=' + flag + '&destStafferId=' + destStafferId, callBackFun);
	    
	    $.unblockUI();
    }
}

function $close()
{
    $.unblockUI();
}

function callBackFun(data)
{
    $.unblockUI();
    reloadTip(data.msg, data.ret == 0);

    if (data.ret == 0)
    commonQuery();
}

function commonQuery(par)
{
    gobal_guid.p.queryCondition = par;
    
    gobal_guid.grid.populate(true);
}

function batchTrans()
{
	$.blockUI({ message: $('#destStafferDIV'),css: { width: '40%'}});
}

var flag = '';
function $selectStaffer(type)
{   
	flag = type;
	
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
	 var oo = oos[0];

	 if (flag == '0')
	 {
		 $O('destStafferName').value = oo.pname;
	     $O('destStafferId').value = oo.value;
	 }
	 else
	 {
		 $O('destStafferName1').value = oo.pname;
	     $O('destStafferId1').value = oo.value;
	 }
     
}

function $batchSelect()
{
	var batch = $O('batch');

	var destStaffer = $O('destStaffer');
	
	if (batch.checked)
	{
		destStaffer.disabled = false;
	}
	else
	{
		destStaffer.disabled = true;

		$O('destStafferName').value = '';
	     $O('destStafferId').value = '';
	}
}

</script>
</head>
<body onload="load()" class="body_class">
<form>
<p:cache></p:cache>
</form>
<div id="stafferList" style="display:none">
<p align='left'><label><font color=""><b>请选择需要回收客户关系职员</b></font></label></p>
<p><label>&nbsp;</label></p>
<select name="stafferId" id="staffer" quick="true" style="width: 85%"></select>
<p><label>&nbsp;</label></p>
<p align='left'>
&nbsp;&nbsp;&nbsp;&nbsp;<strong>批量移交到</strong>&nbsp;&nbsp;&nbsp;&nbsp;
<input type="text" name="destStafferName" readonly="readonly"/>
<input type="hidden" name="destStafferId"/>&nbsp;&nbsp;
<input type="button" value="&nbsp;...&nbsp;" name="qout" id="destStaffer"
                            class="button_class" onclick="$selectStaffer(0)"/>&nbsp;&nbsp;
</p>
<p><label>&nbsp;</label></p>
<p>
<!--<input type='button' value='&nbsp;&nbsp;全部回收&nbsp;&nbsp;' id='div_b_ok1' class='button_class' onclick='$process(0)'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' value='&nbsp;&nbsp;回收拓展&nbsp;&nbsp;' id='div_b_ok2' class='button_class' onclick='$process(1)'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' value='&nbsp;&nbsp;回收终端&nbsp;&nbsp;' id='div_b_ok3' class='button_class' onclick='$process(2)'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
--><input type='button' value='&nbsp;&nbsp;批量移交&nbsp;&nbsp;' id='div_b_ok4' class='button_class' onclick='$process(3)'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' id='div_b_cancle' value='&nbsp;&nbsp;关 闭&nbsp;&nbsp;' class='button_class' onclick='$close()'/>
</p>
<p><label>&nbsp;</label></p>
</div>

<div id="destStafferDIV" style="display:none;width: 200">
<p align='left'><label><font color=""><b>请选择接受客户的职员</b></font></label></p>
<p><label>&nbsp;</label></p>
<p align='center'>
<input type="text" name="destStafferName1" readonly="readonly"/>
<input type="hidden" name="destStafferId1"/>&nbsp;&nbsp;
<input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                            class="button_class" onclick="$selectStaffer(1)"/>&nbsp;&nbsp;
</p>
<p><label>&nbsp;</label></p>
<input type='button' value='&nbsp;&nbsp;批量移交&nbsp;&nbsp;' id='div_b_ok5' class='button_class' onclick='reclaimBeans(1)'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' id='div_b_cancle' value='&nbsp;&nbsp;关 闭&nbsp;&nbsp;' class='button_class' onclick='$close()'/>
<p><label>&nbsp;</label></p>
</div>

<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>
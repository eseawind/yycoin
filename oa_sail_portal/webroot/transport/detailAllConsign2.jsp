<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="处理发货单"/>
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script src="../js/title_div.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/adapter.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/key.js"></script>

<script language="javascript">
function sub()
{
    if (formCheck())
    {
        if (window.confirm('确定审核通过发货单?'))
        {
            outForm.submit();
        }
    }
}

function sub1()
{
	// 如果是通过状态，只能当天允许修改
	if ($$('currentStatus') == 99)
	{
		if (compareDays($$('now'), $$('passDate')) != 0)
		{
			alert('通过状态发货单，只允许通过当天修改。');

			return false;
		}
	}
	
    if (formCheck())
    {
        if (window.confirm('确定修改发货信息?'))
        {
            $O('method').value = 'reportConsign';
            outForm.submit();
        }
    }
}

function sub2()
{
    if (formCheck())
    {
        if (window.confirm('确定增加新的发货单?'))
        {
            $O('method').value = 'readdConsign';
            outForm.submit();
        }
    }
}

function load()
{
	loadForm();

    loadTransport();
    
    loadForm();
    
    <c:forEach items="${beanList}" var="consignBean" varStatus="vs">
    <c:if test="${consignBean.reprotType > 0 || readonly}">
    setAllReadOnly($O('showTable_${vs.index}'));
    $detail($O('showTable_${vs.index}'), ['transport1', 'transport', 'view_bu', 'transportNo']);
    </c:if>
    </c:forEach>

	$('#dlg').dialog({
        modal:true,
        closed:true,
        buttons:{
            '关 闭':function(){
                $('#dlg').dialog({closed:true});
            }
        }
	});

	$ESC('dlg');
}

var jmap = new Object();
<c:forEach items="${transportList}" var="item">
    jmap['${item.id}'] = "${map[item.id]}";
</c:forEach>

function loadTransport()
{
	var transport1s = document.getElementsByName('transport1');
	var transport = document.getElementsByName('transport');

	for (var j = 0; j < transport1s.length; j++)
	{
		var transport1Obj = transport1s[j];

		if (transport1Obj.value != '')
		{
			removeAllItem(transport[j]);
		    
		    setOption(transport[j], '', '--');
		    var items = jmap[transport1Obj.value];
		    
		    if (isNoneInCommon(items))
		    {
		        return;
		    }
		    
		    var ss = items.split('#');
		    for (var i = 0; i < ss.length; i++)
		    {
		        if (!isNoneInCommon(ss[i]))
		        {
		            var kk = ss[i].split('~');
		            setOption(transport[j], kk[1], kk[0]);
		        }
		    }
		}
	}
}

function change(obj)
{
	var tr = getTrObject(obj);
	
	var e1 = getSelectInTr(tr, 'transport1');
	var e2 = getSelectInTr(tr, 'transport');
    
    removeAllItem(e2);
    
    setOption(e2, '', '--');
    var items = jmap[e1.value];
    
    if (isNoneInCommon(items))
    {
        return;
    }
    
    var ss = items.split('#');
    for (var i = 0; i < ss.length; i++)
    {
        if (!isNoneInCommon(ss[i]))
        {
            var kk = ss[i].split('~');
            setOption(e2, kk[1], kk[0]);
        }
    }
}

function removeTr(obj)
{
    obj.parentNode.parentNode.removeNode(true);
}

function addTr()
{
    for (var i = 0; i < 1; i++)
    {
        addTrInner("tables", "trCopy");
    }
}

function calDateTimeInner(obj, name)
{
	var tr = getTrObject(obj);
	
	var el = getInputInTr(tr, name);
	
	return calDateTime(el)
}

function  viewTr(obj)
{
	var tr = getTrObject(obj);
	
	var e1 = getInputInTr(tr, 'transportNo');
	var e2 = getSelectInTr(tr, 'transport');

	var transportNo = e1.value;
	var transport = e2.value;

	if (transportNo == '' || transport == '')
	{
		alert('请输入运输单位及发货单号');

		return;
	}
	//ajax
	$ajax('../sail/transport.do?method=callKuaidi100&transport=' + transport + '&transportNo='+transportNo, callBack100);
}

function callBack100(data)
{
	$O('dia_inner').innerHTML = '';
	    
    var htm = data.msg;
    var par = 'height=100, width=400, top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes,status=yes';

    if (htm.indexOf('http://www.kuaidi100.com') != -1)
		window.open(htm, "mainOpen", par);
    else{
        $O('dia_inner').innerHTML = htm;
        
        $('#dlg').dialog({closed:false});
    }
}

function getSelectInTr(tr, name)
{
    var eles = tr.getElementsByTagName('select');
    
    for (var i = 0; i < eles.length; i++)
    {
        if (eles[i].name == name)
        {
            return eles[i];
        }
    }
    
    return null;
}

function dialog_open()
{
    $v('dia_inner', true);
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="outForm" action="../sail/transport.do"><input
	type="hidden" name="method" value="passConsign">
<input type=hidden name="fullId" value="${consignBean.fullId}" /> 
<input type=hidden name="gid" value="${consignBean.gid}" /> 
<input type=hidden name="distId" value="${consignBean.distId}" /> 
<input type=hidden name="arriveDate" value="${consignBean.arriveDate}" /> 
<input type="hidden" value="3" name="statuss">
<input type="hidden" value="${consignBean.currentStatus}" name="currentStatus">
<input type="hidden" value="${consignBean.passDate}" name="passDate">
<input type="hidden" value="${now}" name="now">

<p:navigation
	height="22">
	<td width="550" class="navigation">发货单</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>发货单信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1" id="showTable">
			        <tr class="content1">
                        <td width="15%">发货单：</td>
                        <td colspan="3">
                              <c:forEach items="${distBeanList}" var="item0">
		                       <a href="../sail/out.do?method=findDistribution&id=${item0.id}">${item0.id}</a>
		                       &nbsp;
		                     </c:forEach>
						</td>
                    </tr>
                    
                    <tr class="content2">
                     <td width="15%">销售单号：</td>
                        <td colspan="3"><a title="点击查看明细" href="../sail/out.do?method=findOut&fow=99&outId=${out.fullId}">${out.fullId}</a></td>
                    </tr>
				
				    <tr class="content1">
                     <td width="15%">收货人：</td>
                        <td colspan="3">${consignBean.reveiver}</td>
                    </tr>
				
                    <c:if test="${consignBean.currentStatus >= 2}">
                    
                      <tr class="content2">
                            <td>备注：</td>
                            <td colspan="3"><textarea rows="3" cols="55" name="applys"
                                >${consignBean.applys}</textarea></td>
                       </tr>
                       
                    </c:if>

		</p:table>
	</p:subBody>

       <c:if test="${consignBean.currentStatus >= 2}">
       <p:line flag="0" />
       
        <tr id="pay_main_tr">
        	<td colspan='2' align='center'>
        	<table width="98%" border="0" cellpadding="0" cellspacing="0"
            	class="border">
	            <tr>
	                <td>
	                <table width="100%" border="0" cellspacing='1' id="tables">
	                    <tr align="center" class="content0">
	                        <td width="10%" align="center">运输方式</td>
	                        <td width="10%" align="center">运输单位</td>
	                        <td width="10%" align="center">发货单号</td>
	                        <td width="10%" align="center">发货地</td>
	                        <td width="7%" align="center">发货地类型</td>
	                        <td width="7%" align="center">备货人</td>
	                        <td width="7%" align="center">检验人</td>
	                        <td width="7%" align="center">打包人</td>
	                        <td width="7%" align="center">打包时间</td>
	                        <td width="7%" align="center">监控设备</td>
							<td width="5%" align="center">包装件数</td>
	                        <td width="5%" align="center">包装重量</td>
	                        <td width="5%" align="center">运费</td>
	                        <td width="8%" align="center"><input type="button" accesskey="B"
	                            value="增加" class="button_class" onclick="addTr()"></td>
	                    </tr>
	                    <c:forEach items="${beanList}" var="item">
                    	<tr align="center" class="content1">
                    	<td align="left">
				         <select name="transport1" style="width: 100%"
				               values="${tss1.id}" onchange="change(this)">
				               <option value="">--</option>
				               <c:forEach items="${transportList}" var="item1">
				                   <option value="${item1.id}">${item1.name}</option>
				               </c:forEach>
				         </select>
				         </td>
				         
				         <td align="left">
				         <select name="transport" style="width: 100%" values="${item.transport}" >
				              <option value="">--</option>
				          </select>
				         </td>
				         
				         
				         <td align="left"><input type="text" style="width: 100%" 
				         						name="transportNo" id="transportNo" readonly='readonly'
				         						value="${item.transportNo}" class="input_class">
				         </td>
				         
				         <td align="left">
				         <input type="text" style="width: 100%"
				         		name="sendPlace" value="${item.sendPlace}" class="input_class">
				         </td>
				         
				         <td align="left">
				         <select name="addrType" style="width: 100%" values="${item.addrType}" >
				              <p:option type="233" empty="true"></p:option>
				          </select>
				         </td>
				         
				         <td align="left">
				         <input type="text" style="width: 100%"
				         		name="preparer" value="${item.preparer}" class="input_class">
				         </td>
				         
				         <td align="left">
				         <input type="text" style="width: 100%"
				         		name="checker" value="${item.checker}" class="input_class">
				         </td>
				         
				         <td align="left">
				         <input type="text" style="width: 100%"
				         		name="packager" value="${item.packager}" class="input_class">
				         </td>
				         
			         	 <td align="left">
				         <input type=text name = 'packageTime'  id = 'packageTime'  value="${item.packageTime}"
				         readonly=readonly class='input_class'><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateTimeInner(this, "packageTime");' height='20px' width='20px'/>
				         </td>
				         
				         <td align="left">
				         <input type="text" style="width: 100%"
				         		name="mathine" value="${item.mathine}" class="input_class">
				         </td>
				         
				         <td align="left">
				         <input type="text" style="width: 100%"
				         		name="packageAmount" value="${item.packageAmount}" class="input_class">
				         </td>
				         
				         <td align="left">
				         <input type="text" style="width: 100%"
				         		name="packageWeight" value="${item.packageWeight}" class="input_class">
				         </td>
				         
				         <td align="left">
				         <input type="text" style="width: 100%"
				                    name="transportFee" value="${item.transportFee}">
				         </td>
				         
				        <td width="8%" align="center"><input type=button name="view_bu"
				            value="查看物流" class=button_class onclick="viewTr(this)">&nbsp;
				           </td>
                    	</tr>
                    	</c:forEach>
	                    
	                </table>
	                </td>
	            </tr>
        	</table>

        </td>
    </tr>
    
	</c:if>                

<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right" id="pr">
            <input type="button" class="button_class"
            onclick="javascript:history.go(-1)"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message2/>
</p:body>
</form>
<div id="dlg" title="物流概览" style="width:500px;height:300px;">
    <div style="padding:20px;height:200px;display:none" id="dia_inner" title="" >
   </div>
</div>    
</body>
</html>


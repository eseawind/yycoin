<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="配送信息" />
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

function load()
{
	var radioElements = document.getElementsByName('shipping');

    var shipping = "${distributionBean.shipping}";
	for (var i=0; i< radioElements.length; i++)
	{
		if (radioElements[i].value == shipping)
		{
			radioElements[i].checked = "checked";
		}
	}
	
    loadForm();
    
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

function  viewTr()
{
	var transportNo = $$('transportNo');
	var transport = $$('transport');

	if (transportNo == '' || transport == '')
	{
		alert('运输单位及发货单号不存在,无法查询');

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

function dialog_open()
{
    $v('dia_inner', true);
}


function pagePrint()
{
	outForm.submit();
}

</script>
</head>
<body class="body_class" onload="load()">
<form name="outForm" method=post action="../sail/out.do">
<input type="hidden" name="method" value="printOut">
<input type="hidden" name="fullId" value="${outBean.fullId}">
<input type="hidden" name="id" value="${distributionBean.id}">
<input type="hidden" name="transport" value="${distributionBean.transport}">
<input type="hidden" name="transportNo" value="${distributionBean.transportNo}">

<p:navigation
	height="22">
	<td width="550" class="navigation">库单管理 &gt;&gt; 销售配送信息</td>
				<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		 <strong>配送信息-${distributionBean.id}</strong>
		</td>
	</p:title>
	
	<p:line flag="0" />
	
	
	<p:subBody width="98%">

		<p:table cells="2">
		
		<p:cell title="销售单号" width="8" end="true">
			${outBean.fullId}
		</p:cell>	
		
		  <p:cell title="发货方式" width="1" end="true">
             <input type="radio" name="shipping" value="0" >自提&nbsp;&nbsp;
             <input type="radio" name="shipping" value="1" >公司&nbsp;&nbsp;
             <input type="radio" name="shipping" value="2" >第三方快递&nbsp;&nbsp;
             <input type="radio" name="shipping" value="3" >第三方货运&nbsp;&nbsp;
             <input type="radio" name="shipping" value="4" >第三方快递+货运&nbsp;&nbsp;
             <input type="radio" name="shipping" value="99" >空发&nbsp;&nbsp;
          </p:cell>
		
		  <p:cell title="运输方式" end="true">
	         	快递：${distributionBean.transportName1}&nbsp;&nbsp;&nbsp;&nbsp;	货运：${distributionBean.transportName2}
          </p:cell>
		
		  <p:cell title="运费支付方式" end="true">             
	         	快递：${my:get('deliverPay',distributionBean.expressPay)}&nbsp;&nbsp;&nbsp;&nbsp;	货运：${my:get('deliverPay',distributionBean.transportPay)}
          </p:cell>
		
		  <tr  class="content1">
		  	<td></td>
		  	<td colspan="3">详细地址：${distributionBean.provinceName}&nbsp;&nbsp;${distributionBean.cityName}&nbsp;&nbsp;${distributionBean.areaName}&nbsp;&nbsp;${distributionBean.address}</td>		  	
		  </tr>
		  
		  <p:cell title="收 货 人" end="true">
             ${distributionBean.receiver}
          </p:cell>
          
          <tr  class="content1">
            <td>手&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
            <td colspan="3">
              ${distributionBean.mobile}&nbsp;&nbsp;&nbsp;&nbsp;固定电话：${distributionBean.telephone}
            </td>
          </tr>

		  <p:cell title="计划收货日期" end="true">             
             ${outBean.arriveDate}
          </p:cell>
					
		  <p:cell title="发货日期" end="true">             
             ${distributionBean.outboundDate}
          </p:cell>
          
          <p:cell title="发货运单号" end="true">             
             ${distributionBean.transportNo}&nbsp;&nbsp;
            <input type=button name="view_bu"
				            value="查看物流" class=button_class onclick="viewTr()">&nbsp;
          </p:cell> 			
						
		</p:table>

	</p:subBody>
	
    <p:title>
        <td class="caption">
         <strong>源产品明细</strong>
        </td>
    </p:title>

    <p:line flag="0" />

	<tr>
		<td colspan='2' align='center'>
		<table width="98%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables">
					<tr align="center" class="content0">
						<td width="20%" align="center">仓库</td>
						<td width="30%" align="center">品名</td>						
						<td width="15%" align="center">数量</td>
						<td width="35%" align="center">配送方式</td>						
					</tr>

					<c:forEach items="${baseList}" var="item" varStatus="vs">
						<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
							<td align="left">${item.depotName}</td>
							<td align="center">${item.productName}								
							</td>
							<td align="center">${item.amount}</td>
							<td align="center">${my:get('deliverType',item.deliverType)}</td>
						</tr>
					</c:forEach>
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<input type="button" name="pr"
            class="button_class" onclick="pagePrint()"
            value="&nbsp;&nbsp;打 印 预 览&nbsp;&nbsp;">&nbsp;&nbsp;
		<input type="button"
			class="button_class" id="ok_b" style="cursor: pointer"
			value="&nbsp;&nbsp;返回&nbsp;&nbsp;" onclick="javaScript:window.history.go(-1);"></div>
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


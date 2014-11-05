<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="填写销售单(now)" />
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/buffalo.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/jquery/jquery.js"></script>
<script language="JavaScript" src="../js/json.js"></script>

<script language="javascript">
<%@include file="../sail_js/out1.jsp"%>

var END_POINT="${pageContext.request.contextPath}/bfapp";

var buffalo = new Buffalo(END_POINT);

var cmap = window.top.topFrame.cmap;
var pList = window.top.topFrame.pList;

var areaJson = JSON.parse('${areaStrJSON}');

function load()
{
    setOption($O('provinceId'), "", "--");
    for (var i = 0; i < pList.length; i++)
    {
        setOption($O('provinceId'), pList[i].id, pList[i].name);
    }    
    
    var radioElements = document.getElementsByName('shipping');

    var shipping = "${distributionBean.shipping}";

	for (var i=0; i< radioElements.length; i++)
	{
		if (radioElements[i].value == shipping)
		{
			radioElements[i].checked = "checked";

			radio_click(radioElements[i]);
		}
	}
	
    loadForm();

    changes($O('cityId'));
    
    loadForm();

    changeArea('${distributionBean.areaId}');
}

function del(id)
{
    $O('span_' + id).innerHTML = '';
    
    $O('attacmentIds').value = $O('attacmentIds').value.delSubString(id + ';')
}

function changes(obj)
{
    removeAllItem($O('cityId'));
    setOption($O('cityId'), "", "--");
    
    if ($$('provinceId') == "")
    {
        return;
    }
    
    var cityList = cmap[$$('provinceId')];
    for (var i = 0; i < cityList.length; i++)
    {
        setOption($O('cityId'), cityList[i].id, cityList[i].name);
    }
    
    removeAllItem($O('areaId'));
    
    setOption($O('areaId'), "", "--");
}

function changeArea(areaId)
{
    var id = $$('cityId');
    
    if (id == "")
    {
        return;
    }
    
    removeAllItem($O('areaId'));
    setOption($O('areaId'), "", "--");
    
    var areaList = areaJson[$$('cityId')];

    removeAllItem($O('areaId'));
    
    setOption($O('areaId'), "", "--");
    
    for (var i = 0; i < areaList.length; i++)
    {
        setOption($O('areaId'), areaList[i].id,  areaList[i].name);
    }
    
    return;
}

function selectAddr()
{
	window.common.modal('../customer/address.do?method=rptQueryAddress&first=1&load=1&selectMode=1&customerId=' + ${outBean.customerId});
}

function getAddress(oos)
{
	obj = oos[0];

	$O('provinceId').value = obj.pprovinceid;
	
	changes($O('provinceId'));

    $O('cityId').value = obj.pcityid;

    changeArea(obj.pareaid);
    $O('areaId').value = obj.pareaid;
    
    $O('address').value = obj.paddress;
    $O('receiver').value = obj.preceiver;
    $O('mobile').value = obj.pmobile;
    $O('telephone').value = obj.ptelephone;
}

</script>
</head>
<body class="body_class" onload="load()">
<form name="outForm" action="../sail/out.do?method=addOutStep2" method="post">
<input type=hidden name="id" value="${distributionBean.id}" />
<input type=hidden name="update" value="${update}" />
<input type=hidden name="saves" value="" />
<input type=hidden name="fullId" value="${outBean.fullId}" /> 
<input type=hidden name="outType" value="${outBean.outType}" />
<input type=hidden name="customerId" value="${outBean.customerId}" />
<input type=hidden name="reserve3" value="${outBean.reserve3}" />

<input type=hidden name="step" value="2" />
<input type=hidden name="refAddId" value="" />
<input type=hidden name="rshipping" value="" />

<!-- 促销模块 -->
<input type=hidden name="eventId" value="${outBean.eventId}" />
<input type=hidden name="refBindOutId" value="${outBean.refBindOutId}" />
<input type=hidden name="isAddUp" value="" />
<input type=hidden name="eventDate" value="" />
<input type=hidden name="hasProm" value="${hasProm}" />
<input
    type="hidden" name="attacmentIds" value="${attacmentIds}">

<input type=hidden name="canProm" value="${canProm}" />

<p:navigation
	height="22">
	<td width="550" class="navigation">库单管理 &gt;&gt; 填写销售配送信息2</td>
				<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<tr>
		<td colspan='2' align='center'>
			<font color="#FF0000">${logDesc}</font>
		</td>
	</tr>

	<p:title>
		<td class="caption">
		 <strong>配送信息</strong>
		</td>
	</p:title>
	
	<p:line flag="0" />
	
	
	<p:subBody width="98%">

		<p:table cells="2">
		
		  <p:cell title="发货方式" width="1" end="true">
             <input type="radio" name="shipping" value="0" onClick="radio_click(this)">自提&nbsp;&nbsp;
             <input type="radio" name="shipping" value="1" onClick="radio_click(this)">公司&nbsp;&nbsp;
             <input type="radio" name="shipping" value="2" onClick="radio_click(this)">第三方快递&nbsp;&nbsp;
             <input type="radio" name="shipping" value="3" onClick="radio_click(this)">第三方货运&nbsp;&nbsp;
             <input type="radio" name="shipping" value="4" onClick="radio_click(this)">第三方快递+货运&nbsp;&nbsp;
             <input type="radio" name="shipping" value="99" onClick="radio_click(this)">空发&nbsp;&nbsp;             
          </p:cell>
		
		  <p:cell title="运输方式" end="true">
             <select name="transport1" quick=true class="select_class" style="width:20%" values="${distributionBean.transport1}">
	         </select>&nbsp;&nbsp;
	         <select name="transport2" quick=true class="select_class" style="width:20%" values="${distributionBean.transport2}">
	         </select>
          </p:cell>
		
		  <p:cell title="运费支付方式" end="true">
             <select name="expressPay" quick=true class="select_class" style="width:20%" values="${distributionBean.expressPay}">
             <p:option type="deliverPay" empty="true"></p:option>
	         </select>&nbsp;&nbsp;
	         <select name="transportPay" quick=true class="select_class" style="width:20%" values="${distributionBean.transportPay}">
	         <p:option type="deliverPay" empty="true"></p:option>
	         </select>
          </p:cell>
		
		  <tr  class="content1">
		  	<td>送货地址：</td>
		  	<td>选择地址：</td>
		  	<td colspan="2">
			  	<select name="provinceId" quick=true onchange="changes(this)" values="${distributionBean.provinceId}" class="select_class" oncheck="notNone;"></select>&nbsp;&nbsp;
	         	<select name="cityId" quick=true onchange="changeArea()" values="${distributionBean.cityId}" class="select_class" oncheck="notNone;"></select>&nbsp;&nbsp;
	         	<select name="areaId" quick=true values="${distributionBean.areaId}" class="select_class" oncheck="notNone;"></select>&nbsp;&nbsp;
	         	<input type="button" class="button_class"
						value="&nbsp;选择地址&nbsp;" onClick="selectAddr();" />&nbsp;&nbsp;
		  	</td>
		  </tr>	
		  
		  <tr  class="content2">
		  	<td></td>
		  	<td>详细地址：</td>
		  	<td colspan="2">
		  		<input type="text" name="address" value="${distributionBean.address}" oncheck="notNone;" size=100 maxlength="300" style="width: 100%;">
		  	</td>
		  </tr>
		  
		  <p:cell title="收 货 人" end="true">
             <input type="text" name="receiver" value="${distributionBean.receiver}" oncheck="notNone;" size=20 maxlength="30">
          </p:cell>
          
          <tr  class="content2">
            <td>手&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
            <td colspan="3">
              <input type="text" name="mobile" value="${distributionBean.mobile}" oncheck="notNone;isMathNumber" size=13 maxlength="13"><font color="#FF0000">*</font>
              &nbsp;&nbsp;固定电话：&nbsp;&nbsp;            
              <input type="text" name="telephone" value="${distributionBean.telephone}" size=20 maxlength="30">
            </td>
          </tr>

		</p:table>

		<p:table>
			 <tr class="content1">
				<td height="10" colspan='4'></td>
			 </tr>
	
			<tr class="content2">
				<td>促销活动：</td>
				<td><input type="text" name="eventName" style="width: 350px"
					value="${outBean.eventName}" readonly="readonly"></td>
					<td align="right">折扣金额：</td>
					<td><input type="text" name="promValue" maxlength="20"
						value="${outBean.promValue}" readonly="readonly"></td>
			</tr>
			
			<c:if test="${update == 1}">
			<p:cell title="原附件" width="8" end="true">
            <c:forEach items="${outBean.attachmentList}" var="item" varStatus="vs">
            <span id="span_${item.id}"><img src=../images/oa/attachment.gif>
            <a target="_blank" href="../sail/out.do?method=downAttachmentFile&id=${item.id}">${item.name}</a>&nbsp;
            
            <a title="删除附件" href="javascript:del('${item.id}')"> <img
                        src="../images/oa/del.gif" border="0" height="15" width="15"></a>
            </span>
            </c:forEach>
            </p:cell>
			</c:if><!--
						
			<p:cell title="附件" width="8" end="true"><input type="file" name="atts" size="70" >  
            <font color="blue"><b>建议压缩后上传,最大支持2M</b></font>
            </p:cell>
            
		--></p:table>

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
								<input type=hidden name="baseId" 															
								value="${item.id}" />
							</td>
							<td align="center">${item.amount}</td>
							<td align="center">
							<select name="deliverType" class="select_class" values="" style="width: 60%;" quick=true>
				            	<p:option type="deliverType" empty="true"></p:option>
				         	</select>
							</td>
						</tr>
					</c:forEach>
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<p:line flag="1" />

	<tr>
		<td width="100%">
		<div align="right">
			<input type="button" class="button_class"
			value="&nbsp;&nbsp;保 存&nbsp;&nbsp;" onClick="save()" />&nbsp;&nbsp;
			<input type="button" class="button_class" id="sub_b"
			value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onClick="sub()" /></div>
		</td>
		<td width="0%"></td>
	</tr>

</p:body>
</form>
</body>
</html>


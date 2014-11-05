<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="发票" link="true" guid="true" cal="true" dialog="true" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script src="../stockapply_js/scheck.js"></script>
<script language="javascript">

function passBean()
{
    $O('method').value = 'passInvoiceins';

    var status = parseInt("${bean.status}", 10);
    var insAmount = parseInt("${bean.insAmount}", 10);

	if (status == 1 && insAmount > 0)
	{
		var totals = 0;
		
		var totalArr = document.getElementsByName('moneys');

		for (var i = 0; i < totalArr.length; i++)
		{
			totals += parseFloat(totalArr[i].value);
		}

		var total = 0;

		total = formatNum($$('total'), 2)
			
		if ("${bean.otype}" == 0)
		{
			if (compareDouble(totals, parseFloat(total)) != 0)
			{
				 alert('发票号对应的总金额不等于开票金额');
			        
			     return false;
			}
		}
	}
    
    submit('确定通过?', null, null);
}

function rejectBean()
{
    $O('method').value = 'rejectInvoiceins';
    
    submit('确定驳回?', null, null);
}

function backInvoiceins()
{
    $O('method').value = 'backInvoiceins';
    
    submit('确定退票?', null, null);
}

function checkSubmit(checks, checkrefId)
{
    if (checks == '' || checkrefId == '')
    {
        alert('意见和关联单据不能为空');
        
        return false;
    }
    
    closeCheckDiv();
    
    $ajax2('../finance/invoiceins.do?method=checkInvoiceins&id=${bean.id}', {'checks' : checks, 'checkrefId' : checkrefId}, 
                        callBackFun1);
}

function callBackFun1(data)
{
    alert(data.msg);
    
    if (data.ret == 0)
    {
        $('#checkCell_SEC').html('已核对 / ' + $('#checks').val() + ' / ' + $('#checkrefId').val());
    }
}

function checkBean()
{
    openCheckDiv();
}

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
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../finance/invoiceins.do" method="post">
<input type="hidden" name="method" value=""> 
<input type="hidden" name="id" value="${bean.id}"> 
<input type="hidden" name="mode" value="${mode}">
<input type="hidden" name="total" value="${my:formatNum(bean.moneys)}"> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">发票管理</span> &gt;&gt; 发票明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>发票基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:class value="com.china.center.oa.finance.bean.InvoiceinsBean" opr="2"/>

		<p:table cells="2">
		    <p:cell title="标识">
               ${bean.id}
            </p:cell>

			<p:pro field="otype">
				<p:option type="invoiceinsOtype" />
			</p:pro>

			<p:pro field="invoiceDate"/>
			
			<p:pro field="unit" innerString="size=60" />
			
			<p:pro field="headType">
				<p:option type = "invoiceinsHeadType" />
			</p:pro>
			
			<p:pro field="headContent"/>

			<p:pro field="invoiceId" innerString="style='WIDTH: 340px;'">
			    <c:forEach items="${invoiceList}" var="item">
			    <option value="${item.id}">${item.fullName}</option>
			    </c:forEach>
			</p:pro>

			<p:pro field="dutyId" innerString="onchange=loadShow()">
				<p:option type="dutyList" />
			</p:pro>
			
			<p:cell title="客户/分公司">
               ${bean.customerName}
            </p:cell>
			
            <p:cell title="开票人">
               ${bean.stafferName}
            </p:cell>
            
            <p:cell title="经办人">
               ${bean.operatorName}
            </p:cell>   
            
            <p:cell title="审核人">
               ${bean.processName}
            </p:cell>
            
            <p:cell title="总金额">
               ${my:formatNum(bean.moneys)}
            </p:cell>
            
            <p:cell title="开票时间">
               ${bean.logTime}
            </p:cell>
            
            <p:cell title="发票数量">
               ${bean.insAmount}
            </p:cell>
            
            <p:cell title="核对信息" id="checkCell">
               ${my:get('pubCheckStatus', bean.checkStatus)} / ${bean.checks} / ${bean.checkrefId}
            </p:cell>

			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />
			
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
            
            <c:if test="${(bean.status == 1 || bean.status == 2) && (mode == 1 || mode == 3)}">
                <p:cell title="审批意见" end="true">
                    <textarea rows="3" cols="55" oncheck="maxLength(200);" name="reason"></textarea>
                </p:cell>
            </c:if>
            
            <p:cell title="附件" width="8" end="true">
            <c:forEach items="${bean.attachmentList}" var="item">
            <a href="../finance/invoiceins.do?method=downAttachmentFile&id=${item.id}" title="点击下载附件">${item.name}</a>
            <br>
            <br>
            </c:forEach>
            </p:cell>

		</p:table>

	</p:subBody>

	<p:tr />


	<p:subBody width="100%">

		<p:table cells="1">

			<tr align="center" class="content0">
				<td width="22%" align="center">销售单</td>
				<td width="28%" align="center">品名/税率</td>
				<td width="10%" align="center">单价</td>
				<td width="5%" align="center">开票数量</td>
				<td width="10%" align="center">开票金额</td>
				<td width="10%" align="center">单位</td>
				<td width="15%" align="center">开票品名</td>
			</tr>
			
			<c:forEach items="${itemList}" var="item">
			<tr align="center" class="content0">
				<td align="center">
				<c:if test="${item.type == 0}">
                <a href="../sail/out.do?method=findOut&outId=${item.outId}">${item.outId}</a>
                </c:if>
                <c:if test="${item.type == 1}">
                <a href="../sail/out.do?method=findOutBalance&id=${item.outId}">${item.outId}</a>
                </c:if>				
				</td>
				<td align="center">${item.productName}/${my:formatNum(item.taxrate * 100)}%</td>
				<td align="center">${my:formatNum(item.price)}</td>
                <td align="center">${item.amount}</td>
                <td align="center">${my:formatNum(item.moneys)}</td>
                <c:if test="${item.outId == ''}">
                <td align="center">${item.unit}</td>
                </c:if>
                <c:if test="${item.outId != ''}">
                <td align="center">${my:get('200', item.unit)}</td>
                </c:if>
                
                <td align="center">${item.showName}</td>
            </tr>
            </c:forEach>

		</p:table>

	</p:subBody>
	
	<p:tr />
	
	<c:if test="${bean.otype == 0 && bean.status == 1 && bean.insAmount > 0}">
	<tr>
        <td colspan='2' align='center'>
        <table width="100%" border="0" cellpadding="0" cellspacing="0"
            class="border" id="inner_table">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="30%" align="center">发票号</td>
                        <td width="15%" align="center">金额</td>
                    </tr>
                    <c:forEach begin="0" end="${bean.insAmount - 1}" var="item">
	                    <tr class="content1" >
					         <td width="50%" align="center">
					         	<input name="invoiceNum" type="text" style="width: 100%;" oncheck="notNone">
					         </td>
					         <td width="20%" align="center">
					         	<input type="text" style="width: 100%" name="moneys" value="" oncheck="notNone;isFloat">
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
	</c:if>
	
	<p:subBody width="100%">

		<p:table cells="1">

			<tr align="center" class="content0">
				<td width="20%" align="center">类型</td>
				<td width="40%" align="center">单据</td>
<!--				<td width="20%" align="center">子项</td>-->
				<td width="30%" align="center">开票金额</td>
			</tr>
			
			<c:forEach items="${bean.vsList}" var="item">
			<tr align="center" class="content0">
                <td align="center">${my:get('invsoutType', item.type)}</td>
                <td align="center">
                <c:if test="${item.type == 0}">
                <a href="../sail/out.do?method=findOut&outId=${item.outId}">${item.outId}</a>
                </c:if>
                <c:if test="${item.type == 1}">
                <a href="../sail/out.do?method=findOutBalance&id=${item.outBalanceId}">${item.outBalanceId}</a>
                </c:if>
                </td>
<!--                <td align="center">${item.baseId}</td>-->
                <td align="center">${my:formatNum(item.moneys)}</td>
            </tr>
            </c:forEach>

		</p:table>

	</p:subBody>
	
	<c:if test="${my:length(bean.detailList) > 0}">
		<p:subBody width="100%">
	        <table width="100%" border="0" cellspacing='1' id="tables">
            <tr align="center" class="content0">
                <td width="20%" align="center">开票品名</td>
				<td width="60%" align="center">产品</td>
				<td width="10%" align="center">数量</td>
				<td width="10%" align="center">金额</td>
            </tr>

            <c:forEach items="${bean.detailList}" var="item" varStatus="vs">
                <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                    <td align="center">${item.showName}</td>

					<td align="center">${item.productName}</td>

                    <td align="center">${item.amount}</td>
                    
                    <td align="center">${my:formatNum(item.moneys)}</td>

                </tr>
            </c:forEach>
            </table>
	    </p:subBody>
	    
	    <p:tr />
	</c:if>
	<c:if test="${my:length(bean.numList) > 0}">
		<p:subBody width="100%">
	        <table width="100%" border="0" cellspacing='1' id="tables">
	            <tr align="center" class="content0">
	                <td width="30%" align="center">发票号码</td>
					<td width="10%" align="center">发票金额</td>
	            </tr>
	
	            <c:forEach items="${bean.numList}" var="item" varStatus="vs">
	                <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
	                    <td align="center">${item.invoiceNum}</td>
	
	                    <td align="center">${my:formatNum(item.moneys)}</td>
	
	                </tr>
	            </c:forEach>
	        </table>
	    </p:subBody>
	    
	    <p:tr />
	</c:if>
	
	<p:line flag="0" />

    <p:subBody width="100%">
        <table width="100%" border="0" cellspacing='1' id="tables">
            <tr align="center" class="content0">
                <td width="10%" align="center">审批人</td>
                <td width="10%" align="center">审批动作</td>
                <td width="10%" align="center">前状态</td>
                <td width="10%" align="center">后状态</td>
                <td width="45%" align="center">意见</td>
                <td width="15%" align="center">时间</td>
            </tr>

            <c:forEach items="${logList}" var="item" varStatus="vs">
                <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                    <td align="center">${item.actor}</td>

                    <td align="center">${my:get('oprMode', item.oprMode)}</td>

                    <td align="center">${my:get('invoiceinsStatus', item.preStatus)}</td>

                    <td align="center">${my:get('invoiceinsStatus', item.afterStatus)}</td>

                    <td align="center">${item.description}</td>

                    <td align="center">${item.logTime}</td>

                </tr>
            </c:forEach>
        </table>
    </p:subBody>
	
	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		
		<c:if test="${(bean.status == 1 || bean.status == 2) && (mode == 1 || mode == 3)}">
                <input type="button" class="button_class"
                    id="ok_p" style="cursor: pointer" value="&nbsp;&nbsp;通 过&nbsp;&nbsp;"
                    onclick="passBean()">&nbsp;&nbsp;
                <input type="button" class="button_class"
                id="re_b" style="cursor: pointer" value="&nbsp;&nbsp;驳 回&nbsp;&nbsp;"
                onclick="rejectBean()">&nbsp;&nbsp;
         </c:if>
         
         <c:if test="${update==3}">
         	<input
	            type="button" name="ba" class="button_class"
	            onclick="backInvoiceins()"
	            value="&nbsp;&nbsp;确认退票&nbsp;&nbsp;">&nbsp;&nbsp;    
         </c:if>
         
         <c:if test="${bean.status == 99}">
	        <input
	            type="button" name="ba" class="button_class"
	            onclick="checkBean()"
	            value="&nbsp;&nbsp;总部核对&nbsp;&nbsp;">&nbsp;&nbsp;    
         </c:if>
       
		<input
            type="button" name="ba" class="button_class"
            onclick="javascript:history.go(-1)"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message2 />
</p:body></form>

</body>
</html>
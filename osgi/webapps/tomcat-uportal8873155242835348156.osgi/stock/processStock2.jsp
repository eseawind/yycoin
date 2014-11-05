<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="采购单" />
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script src='../js/adapter.js'></script>
<script language="javascript">

var g_id;

function fech(id)
{
    g_id = id;
    
	$('#dlg').dialog({closed:false});
}

function load()
{
     $('#dlg').dialog({
                modal:true,
                closed:true,
                buttons:{
                    '确 定':function(){
                        updatePrice();
                    },
                    '取 消':function(){
                        $('#dlg').dialog({closed:true});
                    }
                }
     });
     
     $ESC('dlg');
}

function updatePrice()
{
    if ($$('depotpartId') == '')
    {
        alert('请选择仓区');
        return false;
    }
    
    document.location.href = '../stock/stock.do?method=fechProduct&id=${bean.id}&itemId=' + g_id + '&depotpartId=' + $$('depotpartId');
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../stock/stock.do" method="post">
<input type="hidden" name="method" value="updateStockStatus">
<input type="hidden" name="id" value="${bean.id}">
<input type="hidden" name="pass" value="1">
<input type="hidden" name="nearlyPayDate" value="">
<input type="hidden" name="reject" value="">
<p:navigation height="22">
	<td width="550" class="navigation">采购单明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>采购单信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:table cells="2">
			<p:class value="com.china.center.oa.stock.bean.StockBean" opr="2"/>
			
			<p:cell title="单号">
            ${bean.id}
            </p:cell>

            <p:cell title="采购人">
            ${bean.userName}
            </p:cell>
            
            <p:cell title="库存人">
            ${bean.owerName}
            </p:cell>

			<p:cell title="区域">
			${bean.locationName}
			</p:cell>

			<p:cell title="状态">
			${my:get('stockStatus', bean.status)}
			</p:cell>
			
			<p:pro field="stockType">
				<option value="">--</option>
               <p:option type="stockSailType"></p:option>
            </p:pro>
            
             <p:pro field="stype">
				<option value="">--</option>
               <p:option type="stockStype"></p:option>
            </p:pro>
			
            <p:pro field="invoiceType" innerString="style='width: 300px'">
                <option value="">没有发票</option>
                <c:forEach items="${invoiceList}" var="item">
				<option value="${item.id}">${item.fullName}</option>
				</c:forEach>
            </p:pro>

			<p:cell title="录入时间">
			${bean.logTime}
			</p:cell>

			<p:cell title="需到货时间">
			${bean.needTime}
			</p:cell>

			<p:cell title="物流">
			${bean.flow}
			</p:cell>

			<p:cell title="总计金额">
			${my:formatNum(bean.total)}
			</p:cell>

			<p:cells celspan="1" title="异常状态">
			${my:get('stockExceptStatus', bean.exceptStatus)}
			</p:cells>
			
			<p:cells celspan="1" title="询价类型">
            ${my:get('priceAskType', bean.type)}
            </p:cells>
            
            <p:pro field="willDate" cell="2"/>
            
             <p:cells celspan="2" title="纳税实体">
            ${bean.dutyName}
            </p:cells>

			<p:cells celspan="2" title="备注">
			${bean.description}
			</p:cells>
			
		</p:table>
	</p:subBody>

	<p:tr />

	<p:subBody width="100%">
		<table width="100%" border="0" cellspacing='1' id="tables">
			<tr align="center" class="content0">
				<td width="10%" align="center">采购产品</td>
				<td width="5%" align="center">采量</td>
				<td width="10%" align="center">当前数量</td>
				<td width="10%" align="center">是否询价</td>
				<td width="10%" align="center">参考/实际价格</td>
				<td width="10%" align="center">付款时间</td>
				<td width="15%" align="center">供应商</td>
				<td width="5%" align="center">合计金额</td>
				<td width="10%" align="center">描述</td>
				<td width="10%" align="center">拿货人</td>
				<td width="10%" align="center">拿 货</td>
			</tr>

			<c:forEach items="${bean.itemVO}" var="item" varStatus="vs">
			    <c:if test="${bean.stafferId == user.stafferId || item.stafferId == user.stafferId}">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center">${item.productName}</td>

					<td align="center">${item.amount}</td>
					
					<td align="center">${item.productNum}</td>

					<td align="center">${item.status == 0 ? "<font color=red>否</font>" : "<font color=blue>是</font>"}</td>

					<td align="center">${my:formatNum(item.prePrice)}/${my:formatNum(item.price)}</td>

					<td align="center">${item.nearlyPayDate}</td>

					<td align="center">${item.providerName}</td>

					<td align="center">${my:formatNum(item.total)}</td>
					
					<td align="center">${item.description}</td>
					
					<td align="center">${item.stafferName}</td>

					<td align="center">
					<c:if test="${item.fechProduct == 0 && item.stafferId == user.stafferId}">
					<a title="拿货"
						href="javascript:fech('${item.id}')">
					<img src="../images/opr/change.gif" border="0" height="15" width="15"></a>
					</c:if>
					</td>
				</tr>
				</c:if>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
			<input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message2/>
	
</p:body>
<div id="dlg" title="选择到货的仓区" style="width:320px;">
    <div style="padding:20px;height:200px;" id="dia_inner1" title="">
    <c:forEach items="${depotpartList}" var="item" varStatus="vs">
     <input type="radio" name="depotpartId" value="${item.id}"  ${vs.index == 0 ? 'checked=checked' : '' }>${item.name}<br>
    </c:forEach>
   </div>
</div>
</form>
</body>
</html>


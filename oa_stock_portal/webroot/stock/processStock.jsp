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

<script language="javascript">
function ask(id)
{
	document.location.href = '../stock/stock.do?method=preForSockAsk&id=${bean.id}&ltype=${ltype}&itemId=' + id;
}

function passTO2()
{
	$.messager.prompt('最早付款日期', '请选择最早付款日期', '', function(value, opr){
                if (opr)
                {
                    $Dbuttons(true);
                    
                    var sss = value;
                    
                    if (!(sss == null || sss == ''))
                    {
                    	$O('nearlyPayDate').value = sss;
                        $O('method').value = 'updateStockStatus';
						$O('reject').value = '';
						$O('pass').value = '1';
						formEntry.submit();
                    }
                    else
                    {
                    	alert('请选择最早付款日期');
                    	
                        $Dbuttons(false);
                        
                        passTo(id, type);
                    }
                }
            }, 1);
}

function passTO()
{
    if (window.confirm('确定审核通过此采购单?'))
    {
        $O('nearlyPayDate').value = '';
        $O('method').value = 'updateStockStatus';
        $O('reject').value = '';
        $O('pass').value = '1';
        formEntry.submit();
    }
}

function update2()
{
    $O('method').value = 'findStock';
    $O('id').value = '${bean.id}';
    $O('stockAskChange').value = '';
    $O('process').value = '';
    $O('update').value = '2';
    formEntry.submit();
}
</script>

</head>
<body class="body_class">
<form name="formEntry" action="../stock/stock.do" method="post">
<input type="hidden" name="method" value="updateStockStatus">
<input type="hidden" name="id" value="${bean.id}">
<input type="hidden" name="pass" value="1">
<input type="hidden" name="nearlyPayDate" value="">
<input type="hidden" name="reject" value="">
<input type="hidden" name="update" value="">
<input type="hidden" name="process" value="">
<input type="hidden" name="stockAskChange" value="">
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
            
            <p:pro field="willDate" cell="1"/>
            
            <p:pro field="ptype" cell="1">
            <p:option type="natureType"></p:option>
            </p:pro>
            
            <p:pro field="nearlyPayDate" cell="2"/>
            
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
				<td width="15%" align="center">采购产品</td>
				<td width="10%" align="center">采购数量</td>
				<td width="10%" align="center">当前数量</td>
				<td width="10%" align="center">是否询价</td>
				<td width="10%" align="center">参考价格</td>
				<td width="10%" align="center">实际价格</td>
				<td width="20%" align="center">供应商</td>
				<td width="5%" align="center">合计金额</td>
				<td width="10%" align="center">确认价格</td>
			</tr>

			<c:forEach items="${bean.itemVO}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center">${item.productName}</td>

					<td align="center">${item.amount}</td>
					
					<td align="center">${item.productNum}</td>

					<td align="center">${item.status == 0 ? "<font color=red>否</font>" : "<font color=blue>是</font>"}</td>

					<td align="center">${my:formatNum(item.prePrice)}</td>

					<td align="center">${my:formatNum(item.price)}</td>

					<td align="center">${item.providerName}</td>

					<td align="center">${my:formatNum(item.total)}</td>

					<td align="center">
					<c:if test="${item.status == 0}">
					<a title="采购询价"
						href="javascript:ask('${item.id}')">
					<img src="../images/opr/change.gif" border="0" height="15" width="15"></a>
					</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			name="sub1" style="cursor: pointer"
			value="&nbsp;&nbsp;采购税务配置&nbsp;&nbsp;" onclick="update2()">
			&nbsp;&nbsp;
			<input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message2/>
	
</p:body></form>
</body>
</html>


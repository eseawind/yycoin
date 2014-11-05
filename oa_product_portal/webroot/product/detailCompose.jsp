<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="产品合成" guid="true" dialog="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">

function load()
{
}

function checkBean()
{
    $.messager.prompt('总部核对', '请核对说明', '', function(msg){
                if (msg)
                {
                    $l('../finance/finance.do?method=checks2&id=${bean.id}&reason=' + ajaxPararmter(msg) + '&type=1');
                }
               
            }, 2);
}

function pagePrint()
{
    var old = $O('b_div').style.display;
    
    $O('b_div').style.display = 'none';
    window.print();

    $O('b_div').style.display = old;
}
</script>

</head>

<body class="body_class">
<form name="formEntry">
<input type="hidden" name="id" value="${bean.id}">
<p:navigation
	height="22">
	<td width="550" class="navigation">产品合成明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>产品合成基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:table cells="2">
			<p:cell title="合成产品">${bean.productName}</p:cell>
			<p:cell title="产品编码">${bean.productCode}</p:cell>
			<p:cell title="数量">${bean.amount}</p:cell>
			<p:cell title="最终价格">${my:formatNum(bean.price)}</p:cell>
			<p:cell title="金价">${my:formatNum(bean.goldPrice)}</p:cell>
			<p:cell title="银价">${my:formatNum(bean.silverPrice)}</p:cell>
			<p:cell title="合成人" end="true">${bean.stafferName}</p:cell>
			<p:cell title="仓库">${bean.deportName}</p:cell>
			<p:cell title="目的仓区">${bean.depotpartName}</p:cell>
			<p:cell title="目的储位">${bean.storageName}</p:cell>
			<p:cell title="操作类型">${my:get('composeType', bean.type)}</p:cell>
			<p:cell title="管理类型">${my:get('pubManagerType', bean.mtype)}</p:cell>
			<p:cell title="时间" end="true">${bean.logTime}</p:cell>
			
			<p:cell title="关联凭证" end="true">
                <c:forEach items="${financeBeanList}" var="item">
                       <a href="../finance/finance.do?method=findFinance&id=${item.id}">${item.id}</a>
                       &nbsp;
                       </c:forEach>
            </p:cell>
			
			<p:cell title="核对状态" end="true">
               ${my:get('pubCheckStatus', bean.checkStatus)}
            </p:cell>
            
            <p:cell title="核对信息" end="true">
               ${bean.checks}
            </p:cell>
			
		</p:table>
	</p:subBody>
	
	<p:tr/>
	
	<p:subBody width="100%">
		<p:table cells="2">
			<tr align="center" class="content0">
				<td width="30%" align="center">合成费用项</td>
				<td width="20%" align="center">费用</td>
				<td width="50%" align="center">备注</td>
			</tr>
			
			<c:forEach items="${bean.feeVOList}" var="item">
			<tr align="center" class="content1">
				<td width="30%" align="center">${item.feeItemName}</td>
				<td width="30%" align="center">${my:formatNum(item.price)}</td>
				<td width="50%" align="center">${item.description}</td>
			</tr>
			</c:forEach>
		</p:table>
	</p:subBody>
	
	<p:tr/>
	
	<p:subBody width="100%">
		<p:table cells="2">
			<tr align="center" class="content0">
				<td width="15%" align="center">源仓区</td>
				<td width="45%" align="center">源产品</td>
				<td width="15%" align="center">数量</td>
				<td width="15%" align="center">价格</td>
			</tr>
			
			<c:forEach items="${bean.itemVOList}" var="item">
			<tr align="center" class="content1">
				<td align="center">${item.depotpartName}</td>
				<td align="center">${item.productName}(${item.productCode})</td>
				<td align="center">${item.amount}</td>
				<td align="center">${my:formatNum(item.price)}</td>
			</tr>
			</c:forEach>
		</p:table>
	</p:subBody>
	
	

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right" id="b_div">
		<c:if test="${check == 1}">
        <input
            type="button" name="ba" class="button_class"
            onclick="checkBean()"
            value="&nbsp;&nbsp;总部核对&nbsp;&nbsp;">&nbsp;&nbsp;
        </c:if>
        
         <input type="button" name="pr"
            class="button_class" onclick="pagePrint()"
            value="&nbsp;&nbsp;打 印&nbsp;&nbsp;">&nbsp;&nbsp;
        
		<input type="button" class="button_class" id="return_b"
			style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
			onclick="javascript:history.go(-1)">
		</div>
	</p:button>

	<p:message2/>
</p:body></form>
</body>
</html>


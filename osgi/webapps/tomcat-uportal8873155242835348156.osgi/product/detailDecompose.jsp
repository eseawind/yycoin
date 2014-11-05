<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="产品拆分" />
<script language="JavaScript" src="../js/JCheck.js"></script>
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

function passBean()
{
	if ($$('reason') == '')
	{
		$O('reason').value = '同意';		
	}
	
    $O('method').value = 'passDecompose';
    
	submit('确定通过?', null, null);
}

function rejectBean()
{
    $O('method').value = 'rejectDecompose';
    
    submit('确定驳回?', null, null);
}

</script>

</head>

<body class="body_class">
<form name="formEntry" action="../product/product.do" method="post">
<input type="hidden" name="method" value="">
<input type="hidden" name="id" value="${bean.id}">
<p:navigation
	height="22">
	<td width="550" class="navigation">产品拆分明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>产品拆分基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:table cells="2">
			<p:cell title="拆分产品">${bean.productName}</p:cell>
			<p:cell title="产品编码">${bean.productCode}</p:cell>
			<p:cell title="数量">${bean.amount}</p:cell>
			<p:cell title="最终价格">${my:formatNum(bean.price)}</p:cell>
			<p:cell title="拆分人" end="true">${bean.stafferName}</p:cell>
			<p:cell title="仓库">${bean.deportName}</p:cell>
			<p:cell title="目的仓区">${bean.depotpartName}</p:cell>
			<p:cell title="目的储位">${bean.storageName}</p:cell>
			<p:cell title="操作类型">${my:get('composeType', bean.type)}</p:cell>
			<p:cell title="管理类型">${my:get('pubManagerType', bean.mtype)}</p:cell>
			<p:cell title="时间" end="true">${bean.logTime}</p:cell>
			
			<p:cell title="关联凭证" end="true">
               <a href='../finance/finance.do?method=findFinance&id=${bean.otherId}'>${bean.otherId}</a>
            </p:cell>
            
            <c:if test="${update==2}">
            <p:cell title="审批意见" end="true">
				<textarea rows=3 cols=55 oncheck="notNone;maxLength(200);" name="reason"></textarea>
				<font color="red">*</font>
			</p:cell>
            </c:if>
			
		</p:table>
	</p:subBody>
	
	<p:tr/>
	
	<p:subBody width="100%">
		<p:table cells="2">
			<tr align="center" class="content0">
				<td width="10%" align="center">类型</td>
				<td width="15%" align="center">仓库</td>
				<td width="15%" align="center">仓区</td>
				<td width="35%" align="center">产品</td>
				<td width="15%" align="center">数量</td>
				<td width="15%" align="center">价格</td>
			</tr>
			
			<c:forEach items="${bean.itemVOList}" var="item">
			<tr align="center" class="content1">
				<td align="center">${my:get('decomposeProductType',item.stype)}</td>
				<td align="center">${item.deportName}</td>
				<td align="center">${item.depotpartName}</td>
				<td align="center">${item.productName}(${item.productCode})</td>
				<td align="center">${item.amount}</td>
				<td align="center">${my:formatNum(item.price)}</td>
			</tr>
			</c:forEach>
		</p:table>
	</p:subBody>
	
	<p:line flag="1" />
	
	<tr>
        <td colspan='2' align='center'>
        <div id="desc1" style="display: block;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
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

                            <td  align="center">${item.oprModeName}</td>

                            <td  align="center">${item.preStatusName}</td>

                            <td  align="center">${item.afterStatusName}</td>

                            <td  align="center">${item.description}</td>

                            <td  align="center">${item.logTime}</td>

                        </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>
        </div>
        </td>
    </tr>
	

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right" id="b_div">
		<c:if test="${update==2}">
			<input type="button" name="pr"
            class="button_class" onclick="passBean()"
            value="&nbsp;&nbsp;通 过&nbsp;&nbsp;">&nbsp;&nbsp;
            <input type="button" name="ok_b"
            class="button_class" onclick="rejectBean()"
            value="&nbsp;&nbsp;驳 回&nbsp;&nbsp;">&nbsp;&nbsp;
		</c:if>
         <input type="button" name="re_b"
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


<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="金银料出库" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../product_js/composeProduct.js"></script>
<script language="javascript">

function load()
{
	loadForm();
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../depot/storage.do" method="post"><input
	type="hidden" name="method" value="addGSOut"> 
	<input type=hidden name="type" value='0' />

<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javaScript:window.history.go(-1);">产品管理</span> &gt;&gt; 金银料出入库</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:subBody width="98%">
		<p:table cells="1">
			<p:cell title="日期">
				${bean.outTime}
			</p:cell>
			
			<p:cell title="申请人">
				${bean.stafferName}
			</p:cell>
			
			<p:cell title="状态">
				${my:get('gsOutStatus',bean.status)}
			</p:cell>
			
			<p:cell title="类型">
				${my:get('storageGSType',bean.type)}
			</p:cell>
			
			<p:cell title="关联出库单">
				${bean.refId}
			</p:cell>
			
			<p:cell title="备注" end="true">
				${bean.description}
			</p:cell>
			
			<p:cell title="关联凭证" end="true">
               <a href='../finance/finance.do?method=findFinance&id=${bean.otherId}'>${bean.otherId}</a>
            </p:cell>
		
		</p:table>
	</p:subBody>
	
	<p:line flag="1" />
	
	<tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="15%" align="center">仓库</td>
                        <td width="15%" align="center">仓区</td>
                        <td width="30%" align="center">产品</td>
                        <td width="5%" align="center">数量</td>
                        <td width="5%" align="center">成本</td>
                        <td width="5%" align="center">金料(克)</td>
                        <td width="5%" align="center">金料成本</td>
                        <td width="5%" align="center">银料(克)</td>
                        <td width="5%" align="center">银料成本</td>
                    </tr>
                    <c:forEach items="${bean.itemVOList}" var="item" varStatus="vs">
                    <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>	
	                    <td align="center">
				         ${item.deportName}
				         </td>
				         <td align="center">
				         ${item.depotpartName}
				         </td>
				         <td align="center">
				         ${item.productName}
				         </td>
				         <td align="center">${item.amount}
				         </td>
				         <td align="center">${my:formatNum(item.price)}
				                    </td>
				         <td align="center">${item.goldWeight}
				         </td>
				         <td align="center">${my:formatNum(item.goldPrice)}
				         </td>
				         <td align="center">${item.silverWeight}
				         </td>
				         <td align="center">${my:formatNum(item.silverPrice)}
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
		<div align="right">
		  <input type="button" class="button_class" id="sub_b"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;" onclick="javascrip:history.go(-1)">
        </div>
	</p:button>
	
	<p:message2/>
</p:body>
</form>
</body>
</html>


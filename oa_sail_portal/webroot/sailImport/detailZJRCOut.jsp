<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="销售单明细" guid="true" dialog="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../sailImport_js/addZJRCOut.js"></script>
<script language="JavaScript" src="../js/plugin/highlight/jquery.highlight.js"></script>
<script language="javascript">

function load()
{
    loadForm();

    //$detail($O('viewTable'), ['pr', 'ba', 'pr2']);
    $detail($O('viewTable'), ['pr','ba']);
    
}

function hides(boo)
{
    
}


function pagePrint()
{
    $O('na').style.display = 'none';
    $O('pr').style.display = 'none';
    $O('ba').style.display = 'none';
    $O('desc1').style.display = 'none';
    window.print();

    $O('pr').style.display = 'inline';
    $O('ba').style.display = 'inline';
    $O('na').style.display = 'block';
    $O('desc1').style.display = 'block';
}

function pagePrint2()
{
    $O('na').style.display = 'none';
    //$O('pr').style.display = 'none';
    $O('pr2').style.display = 'none';
    $O('ba').style.display = 'none';
    $O('desc1').style.display = 'none';
    $O('cost_td').style.display = 'none';
    hiddenCost('none');
    window.print();

    //$O('pr').style.display = 'inline';
    $O('pr2').style.display = 'inline';
    $O('ba').style.display = 'inline';
    $O('na').style.display = 'block';
    $O('desc1').style.display = 'block';
    $O('cost_td').style.display = 'block';
    hiddenCost('block');
}

function hiddenCost(str)
{
	//var costList = document.getElementsByName('desciprt');
	
	for (var i = 0; i < 30; i++)
	{
		if ($O('sub_td_' + i))
		{
			$O('sub_td_' + i).style.display = str;
		}

		if ($O('subp_td_' + i))
		{
			$O('subp_td_' + i).style.display = str;
		}
	}
}

function checkBean()
{
    $.messager.prompt('总部核对', '请核对说明', '', function(msg){
                if (msg)
                {
                    $l('../sail/out.do?method=checks&outId=${bean.fullId}&formDetail=1&reason=' + ajaxPararmter(msg) + '&queryType=6');
                }
               
            }, 2);
}
</script>
</head>
<body class="body_class" onload="load()">
<form name="outForm" method=post action="../sail/extout.do">
<input
	type=hidden name="method" value="addOut" />
<input type=hidden name="nameList" /> 
<input type=hidden name="idsList" /> 
<input
	type=hidden name="unitList" /> 
	<input type=hidden name="amontList" />
<input type=hidden name="priceList" /> 
<input type=hidden
	name="totalList" /> 
<input type=hidden name="totalss" /> 
<input type=hidden name="customerId" value="${bean.customerId}"/> 
<input type=hidden name="type"
	value='0' /> 
<input type=hidden name="saves" value="" />
<input type=hidden name="desList" value="" />
<input type=hidden name="otherList" value="" />
<input type=hidden name="showIdList" value="" />
<input type=hidden name="showNameList" value="" />
<input type=hidden name="customercreditlevel" value="" />
<div id="na">
<p:navigation
	height="22">
	<td width="550" class="navigation">销售单明细</td>
				<td width="85"></td>
</p:navigation> <br>
</div>

<table width="98%" border="0" cellpadding="0" cellspacing="0" id="viewTable"
	align="center">

	<tr>
		<td colspan='2' align='center'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="mainTable"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content2">
						<td width="15%" align="right">销售日期：</td>

						<td width="35%"><input type="text" name="outTime"
							value="${bean.outTime}" maxlength="20" size="20"
							readonly="readonly"><font color="#FF0000">*</font></td>

							<td width="15%" align="right">销售类型：</td>
							<td width="35%"><select name="outType" class="select_class" values="0" readonly=true>
								<p:option type="outType_out"></p:option>
							</select><font color="#FF0000">*</font></td>
						
					</tr>

					<tr class="content1">
						<td align="right" id="outd">客户：</td>
						<td colspan="3"><input type="text" name="customerName" maxlength="14" value="${bean.customerName}" onclick="selectCustomer()"
							 style="cursor: pointer;"
							readonly="readonly"><font color="#FF0000">*</font></td>
					</tr>
					
                    <tr class="content1">
                       <td align="right">单据标识：</td>
						<td><input type="text" name="fullId" maxlength="40"
							value="${bean.fullId}" readonly="readonly"></td>
                        <td align="right">总金额：</td>
                        <td colspan="1">
                       ${my:formatNum(bean.total)}
                       </td>
                    </tr>
                    
                     <tr class="content2">
                        <td align="right">状态：</td>
                        <td colspan="1">
                        <select name="status" class="select_class"  values="${bean.status}">
                           <p:option type="zjrcOutStatus"></p:option>
                        </select>
                        </td>
                        <td align="right">申请人：</td>
                        <td colspan="1">
                       ${bean.stafferName}
                       </td>
                    </tr>

					<tr class="content2">
						<td align="right">销售单备注：</td>
						<td colspan="3"><textarea rows="3" cols="55" oncheck="notNone;"
							name="description"><c:out value="${bean.description}"/></textarea>
							<font color="#FF0000">*</font>
							</td>
					</tr>
					
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>


	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td colspan='2' align='center'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" 
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables">
					<tr align="center" class="content0">
						<td width="15%" align="center">品名</td>
						<td width="5%" align="center">数量</td>
						<td width="10%" align="center">销售价</td>
						<td width="10%" align="center">金额<span id="total"></span></td>
					</tr>

					<tr class="content1" id="trCopy" style="display: none;">
						<td>
						<input type="text" name="productName"
							onclick="opens(this)"
							productid="" 
							productcode="" 
							price=""
							stafferid=""
							depotpartid=""
							readonly="readonly"
							style="width: 100%; cursor: hand">
						</td>

						<td align="center"><input type="text"
							style="width: 100%" maxlength="6" onkeyup="cc(this)"
							name="amount"></td>

						<td align="center"><input type="text"
							style="width: 100%" maxlength="8" onkeyup="cc(this)"
							onblur="blu(this)" name="price"></td>

						<td align="center"><input type="text"
							value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

						<td align="center"></td>
					</tr>

					<tr class="content2">
						<td align="center"><input type="text" name="productName" id="unProductName"
							onclick="opens(this)" 
							productid="${fristBase.productId}" 
                            productcode="" 
							readonly="readonly"
							style="width: 100%; cursor: pointer"
							value="${fristBase.productName}"></td>

						<td align="center"><input type="text" style="width: 100%" id="unAmount" value="${fristBase.amount}"
							maxlength="6" onkeyup="cc(this)" name="amount"></td>
						
						<td align="center"><input type="text" style="width: 100%" id="unPrice" value="${my:formatNum(fristBase.price)}"
							maxlength="8" onkeyup="cc(this)" onblur="blu(this)" name="price"></td>

						<td align="center"><input type="text" value="${my:formatNum(fristBase.value)}"
							value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

					</tr>
					
					<c:forEach items="${lastBaseList}" var="fristBase" varStatus="vs">
                    <tr class="content2">
                        <td align="center"><input type="text" name="productName"
                            onclick="opens(this)" 
                            productid="${fristBase.productId}" 
                            productcode="" 
                            readonly="readonly"
                            style="width: 100%; cursor: pointer"
                            value="${fristBase.productName}"></td>

                        <td align="center"><input type="text" style="width: 100%"  value="${fristBase.amount}"
                            maxlength="6" onkeyup="cc(this)" name="amount"></td>
                            
                        <td align="center"><input type="text" style="width: 100%"  value="${my:formatNum(fristBase.price)}"
                            maxlength="8" onkeyup="cc(this)" onblur="blu(this)" name="price"></td>

                        <td align="center"><input type="text" value="${my:formatNum(fristBase.value)}"
                            value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

                    </tr>
                    </c:forEach>
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>
	
    <tr>
        <td height="10" colspan='2'></td>
    </tr>

	<tr>
        <td background="../images/dot_line.gif" colspan='2'></td>
    </tr>
	
	<tr>
        <td colspan='2' align='center'>
        <div id="desc1" style="display: block;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="10%" align="center">物流状态</td>
                        <td width="15%" align="center">时间</td>
                    </tr>

                    <c:forEach items="${zjrcdistList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>

                            <td  align="center">${my:get('zjrcOutStatus',item.status)}</td>

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
    
    <tr>
        <td height="10" colspan='2'></td>
    </tr>

    <tr>
        <td background="../images/dot_line.gif" colspan='2'></td>
    </tr>

    <tr>
        <td height="10" colspan='2'></td>
    </tr>

	<tr>
        <td width="100%">
        <div align="right">
        <input type="button" name="pr"
            class="button_class" onclick="pagePrint()"
            value="&nbsp;&nbsp;打 印&nbsp;&nbsp;">&nbsp;&nbsp;
            
            <input
            type="button" name="ba" class="button_class"
            onclick="javascript:history.go(-${goback})"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
        </td>
        <td width="0%"></td>
    </tr>

</table>
</form>
</body>
</html>


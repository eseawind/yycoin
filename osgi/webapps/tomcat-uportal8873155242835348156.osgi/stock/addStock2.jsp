<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="处理采购(废弃)" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/jquery/jquery.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="javascript">

var showJSON = JSON.parse('${showJSON}');

function loadShow()
{
    var json = showJSON;
    
    var pid = $$('dutyId');
    
    var showArr = $("select[name^='showId']") ;
    
    for (var i = 0; i < showArr.length; i++)
    {
        var each = showArr[i];
        
        removeAllItem(each);
        
        for (var j = 0; j < json.length; j++)
        {
            var item = json[j];
            
            if (item.dutyId == pid)
            {
                setOption(each, item.id, item.name);
            }
        }
    }
}

var cindex = -1;
function addBean(opr)
{
	$O('oprMode').value = opr;

	submit('确定申请产品采购?', null, lverify);
}

function lverify()
{
	var checkArr = document.getElementsByName('check_init');

	var isSelect = false;

	var imap = {};

	for (var i = 0; i < checkArr.length; i++)
	{
		var obj = checkArr[i];

		var index = obj.value;

		if (obj.checked)
		{
			isSelect = true;
			if ($O('productName_' + i).value == '' || $O('productId_' + i).value == '' )
			{
				alert('产品不能为空');
				return false;
			}

			if ($$('amount_' + i)  == null)
			{
				alert('请选择产品是否满足数量要求');
				return false;
			}

			if (imap[$O('productId_' + i).value] == $O('productId_' + i).value)
			{
				alert('选择的产品不能重复');
				return false;
			}

			imap[$O('productId_' + i).value] = $O('productId_' + i).value;
		}
	}

	if (!isSelect)
	{
		alert('请选择采购产品');
		return false;
	}

	return true;
}

function load()
{
	init();
	
	change();
	
	loadForm();
	
	loadShow();
}

function init()
{
	var checkArr = document.getElementsByName('check_init');

	for (var i = 0; i < checkArr.length; i++)
	{
		var obj = checkArr[i];

		var index = obj.value;

		if (obj.checked)
		{
			$d('qout_' + index, false);
			$d('price_' + index, false);
			$d('amount_' + index, false);
		}
		else
		{
			$O('price_' + index).value = '';
			$O('productName_' + index).value = '';
			$O('productId_' + index).value = '';
			$d('qout_' + index);
			$d('price_' + index);
			$d('amount_' + index);
		}
	}
}

function selectProduct(index)
{
	cindex = index;
	
	if ($$('type') == 0)
	{
	   window.common.modal(RPT_PRODUCT);
	}
	else
	{
	   window.common.modal(RPT_PRODUCT);
	}
}

function getProduct(oos)
{
	var oo = oos[0];
	
	if (cindex != -1)
	{
		$O("productName_" + cindex).value = oo.pname;
		$O("productId_" + cindex).value = oo.value;
	}
}

function getPriceAskProvider(oo)
{
    if (cindex != -1)
    {
        $O("productName_" + cindex).value = oo.pn;
        $O("productId_" + cindex).value = oo.value;
        $O("price_" + cindex).value = oo.pp;
        $O("netaskId_" + cindex).value = oo.ppid;
    }
}

function change()
{
	//tr_CG
	if ($$('stockType') == '1')
	{
		$hide('tr_CG', false);
	}
	else
	{
		clearValues();
		$hide('tr_CG', true);
	}
	
	if ($$('stype') == '1')
	{
		$hide('type_TR', true);
		$hide('type', true);
		$hide('stockType_TR', true);
		$hide('stockType', true);
		
		return;
	}
	else
	{
		$hide('type_TR', false);
		$hide('type', false);
		$hide('stockType_TR', false);
		$hide('stockType', false);
	}
	
	if ($$('stype') == '2')
	{
		$hide('stockType_TR', true);
		$hide('stockType', true);
		
		return;
	}
	else
	{
		$hide('stockType_TR', false);
		$hide('stockType', false);
	}
}

function selectStaffer()
{
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
	var oo = oos[0];
	
    $O('owerName').value = oo.pname;
    $O('stafferId').value = oo.value;
}

function clearValues()
{
	$O('owerName').value = '';
    $O('stafferId').value = '';
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../stock/stock.do" method="post"><input
	type="hidden" name="method" value="addStock">
	<input type="hidden" name="productId_0" value="">
	<input type="hidden" name="productId_1" value="">
	<input type="hidden" name="productId_2" value="">
	<input type="hidden" name="productId_3" value="">
	<input type="hidden" name="productId_4" value="">
	
	<input type="hidden" name="netaskId_0" value="">
    <input type="hidden" name="netaskId_1" value="">
    <input type="hidden" name="netaskId_2" value="">
    <input type="hidden" name="netaskId_3" value="">
    <input type="hidden" name="netaskId_4" value="">
    
	<input type="hidden" name="stafferId" value="">
	<input type="hidden" name="oprMode" value="">
	<input
	type="hidden" name="id" value="${bean.id}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">采购管理</span> &gt;&gt; 处理采购</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>采购信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.stock.bean.StockBean" />

		<p:table cells="1">
			<p:pro field="stype" outString="代销采购不占用自有资金,付款方式使用委托代销清单付款,无需询价" innerString="onchange=change()">
				<option value="">--</option>
               <p:option type="stockStype"></p:option>
            </p:pro>
			
			<p:pro field="needTime"/>
			
			<p:pro field="willDate"/>
			
			<p:pro field="type">
                <option value="1">外网/卢工/马甸询价</option>
            </p:pro>

			<p:pro field="flow" innerString="quick='true'" outString="支持简拼选择">
			<option value="">--</option>
			<c:forEach items="${departementList}" var="item">
			<option value="${item.name}">${item.name}</option>
			</c:forEach>
			</p:pro>
			
			<p:pro field="stockType" outString="公卖是全公司的都可销售 自卖是只有自己可以销售" innerString="onchange=change()">
				<option value="">--</option>
               <p:option type="stockSailType"></p:option>
            </p:pro>
            
            <p:cell title="采购人" id="CG">
            <input type="text" readonly="readonly" name="owerName" style="width: 240px"/>
            <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectStaffer()">&nbsp;&nbsp;
            <input type="button" value="&nbsp;清 空&nbsp;" name="qout1" id="qout1"
                    class="button_class" onclick="clearValues()">
            </p:cell>
            
            <p:pro field="dutyId" innerString="style='width: 300px' onchange=loadShow()">
                <c:forEach items="${dutyList}" var="item">
                <option value="${item.id}">${item.name}</option>
                </c:forEach>
            </p:pro>
            
            <p:pro field="invoiceType" innerString="style='width: 300px'">
                <option value="">没有发票</option>
                <c:forEach items="${invoiceList}" var="item">
				<option value="${item.id}">${item.fullName}</option>
				</c:forEach>
            </p:pro>
            
            <p:pro field="areaId" innerString="style='width: 300px'">
                <option value="">--</option>
               <p:option type="123"></p:option>
            </p:pro>

			<p:pro field="description"  innerString="cols=80 rows=3" />


			<p:cells id="selects" celspan="2" title="采购处理">
				<table id="mselect">
					<tr>
						<td>
							<input type="checkbox" name="check_init" id="check_init_0" value="0" onclick="init()">产品一：<input type="button"
								value="&nbsp;选 择&nbsp;" name="qout_0" class="button_class"
								onclick="selectProduct(0)">&nbsp;
							产品:<input
							type="text" name="productName_0" value="" size="20" readonly="readonly">&nbsp;
							参考价格:<input
							type="text" name="price_0" id="price_0" value="" size="6" oncheck="notNone;isFloat;">&nbsp;
							数量:<input
							type="text" name="amount_0" id="amount_0" value="" size="6" oncheck="notNone;isNumber;">&nbsp;
                               开发票品名:
                               <select name="showId_0" style="WIDTH: 150px;" quick=true>
                                 <p:option type="123"></p:option>
                               </select>
							</td>
					</tr>

					<tr>
						<td><input type="checkbox" name="check_init" id="check_init_1" value="1" onclick="init()">产品二：<input type="button"
								value="&nbsp;选 择&nbsp;" name="qout_1" class="button_class"
								onclick="selectProduct(1)">&nbsp;
							产品:<input
							type="text" name="productName_1" value="" size="20" readonly="readonly">&nbsp;
							参考价格:<input
							type="text" name="price_1" id="price_1" value="" size="6" oncheck="notNone;isFloat;">&nbsp;
							数量:<input
							type="text" name="amount_1" id="amount_1" value="" size="6" oncheck="notNone;isNumber;">&nbsp;
							开发票品名:
                               <select name="showId_1" style="WIDTH: 150px;" quick=true>
                                 <p:option type="123"></p:option>
                               </select>

						</td>
					</tr>

					<tr>
						<td><input type="checkbox" name="check_init" value="2" id="check_init_2" onclick="init()">产品三：<input type="button"
								value="&nbsp;选 择&nbsp;" name="qout_2" class="button_class"
								onclick="selectProduct(2)">&nbsp;
							产品:<input
							type="text" name="productName_2" value="" size="20" readonly="readonly">&nbsp;
							参考价格:<input
							type="text" name="price_2" id="price_2" value="" size="6" oncheck="notNone;isFloat;">&nbsp;
							数量:<input
							type="text" name="amount_2" id="amount_2" value="" size="6" oncheck="notNone;isNumber;">&nbsp;
							开发票品名:
                               <select name="showId_2" style="WIDTH: 150px;" quick=true>
                                 <p:option type="123"></p:option>
                               </select>
							</td>
					</tr>

					<tr>
						<td><input type="checkbox" name="check_init" value="3" id="check_init_3" onclick="init()">产品四：<input type="button"
								value="&nbsp;选 择&nbsp;" name="qout_3" class="button_class"
								onclick="selectProduct(3)">&nbsp;
							产品:<input
							type="text" name="productName_3" value="" size="20" readonly="readonly">&nbsp;
							参考价格:<input
							type="text" name="price_3" id="price_3" value="" size="6" oncheck="notNone;isFloat;">&nbsp;
							数量:<input
							type="text" name="amount_3" id="amount_3" value="" size="6" oncheck="notNone;isNumber;">&nbsp;
                            开发票品名:
                               <select name="showId_3" style="WIDTH: 150px;" quick=true>
                                 <p:option type="123"></p:option>
                               </select>
							</td>
					</tr>

					<tr>
						<td><input type="checkbox" name="check_init" value="4" id="check_init_4" onclick="init()">产品五：<input type="button"
								value="&nbsp;选 择&nbsp;" name="qout_4" class="button_class"
								onclick="selectProduct(4)">&nbsp;
							产品:<input
							type="text" name="productName_4" value="" size="20" readonly="readonly">&nbsp;
							参考价格:<input
							type="text" name="price_4" id="price_4" value="" size="6" oncheck="notNone;isFloat;">&nbsp;
							数量:<input
							type="text" name="amount_4" id="amount_4" value="" size="6" oncheck="notNone;isNumber;">&nbsp;
                            开发票品名:
                               <select name="showId_4" style="WIDTH: 150px;" quick=true>
                                 <p:option type="123"></p:option>
                               </select>
							</td>
					</tr>
				</table>
			</p:cells>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			name="b_saves" style="cursor: pointer"
			value="&nbsp;&nbsp;保 存&nbsp;&nbsp;" onclick="addBean(0)">&nbsp;&nbsp;
		<input type="button" class="button_class"
			name="b_submit" style="cursor: pointer"
			value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean(1)">&nbsp;&nbsp;
		</div>
	</p:button>

	<p:message2/>
	
</p:body></form>
</body>
</html>


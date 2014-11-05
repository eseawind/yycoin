<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="处理采购" />
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
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return;
	}
	
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
	
	if ($$('mode') == 0)
	{
	   removeOption();
	}
}

function removeOption()
{
    var os = $O('stype').options;

    for (var j = 0; j < os.length; j++)
    {
        if (os[j].value == 2)
        {
            os.remove(j);
            return true;
        }
    }
}

function init()
{
	if (bptype)
	{
		alert('当前是成品,请点击[选择配件产品]');
		return;
	}
	
	var checkArr = document.getElementsByName('check_init');

	for (var i = 0; i < checkArr.length; i++)
	{
		var obj = checkArr[i];

		var index = obj.value;

		if (obj.checked)
		{
			if ($$('ptype')=='')
			{
				alert('请选择采购商品类别');
				return;
			}
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
    if ($$('mtype') == '')
    {
        alert('请选择管理类型');
        return false;
    }    
    
	cindex = index;
	
	if ($$('type') == 0)
	{
	   //../product/product.do?method=rptQueryProduct&firstLoad=1&selectMode=1&abstractType=0&status=0
	   window.common.modal(RPT_PRODUCT + '&mtype=' + $$('mtype') + '&stock=stock');
	}
	else
	{
	   window.common.modal(RPT_PRODUCT + '&mtype=' + $$('mtype') + '&stock=stock');
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

function selectProductBom(index)
{
	if ($$('mtype') == '')
    {
        alert('请选择管理类型');
        return false;
    }

	if ($$('ptype') == '')
    {
        alert('请选择采购商品类别');
        return false;
    }
    
	cindex = index;
	
	window.common.modal('../product/product.do?method=rptQueryProductBom&firstLoad=1&selectMode=1&mtype=' + $$('mtype') + '&stock=stock');
}

function getProductBom(oos)
{
	var oo = oos[0];

	var bomjson = JSON.parse(oo.pbomjson);

	if (cindex != -1)
	{
		for (var j = 0; j < bomjson.length; j++)
        {
            var item = bomjson[j];

            $O("productName_" + j).value = item.subProductName;
    		$O("productId_" + j).value = item.subProductId;

    		$d('price_' + j, false);
			$d('amount_' + j, false);

			var check = document.getElementById('check_init_' + j);

			check.checked = true;
        }
		
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

function sclearValues(index)
{
	$O('check_init_' + index).checked = false;
	$O('price_' + index).value = '';
	$O('amount_' + index).value = '';
	$O('productName_' + index).value = '';
	$O('productId_' + index).value = '';
	$d('qout_' + index);
	$d('price_' + index);
	$d('amount_' + index);
}

var bptype = false; 
function natureChange()
{
	if ($$('ptype') == '1')
	{
		$d('btn_select', false);
		bptype = true;
	}else{
		$d('btn_select');
		bptype = false;
	}


	// init
	var checkArr = document.getElementsByName('check_init');

	for (var i = 0; i < checkArr.length; i++)
	{
		var obj = checkArr[i];

		var index = obj.value;

		obj.checked = false;
		$O('price_' + index).value = '';
		$O('amount_' + index).value = '';
		$O('productName_' + index).value = '';
		$O('productId_' + index).value = '';
		$d('qout_' + index);
		$d('price_' + index);
		$d('amount_' + index);
	}
}

function checkCurrentUser()
{	
     // check
     var elogin = "${g_elogin}";

 	 //  商务登陆
     if (elogin == '1')
     {
          var top = window.top.topFrame.document;
          //var allDef = window.top.topFrame.allDef;
          var srcStafferId = top.getElementById('srcStafferId').value;
         
          var currentStafferId = "${g_stafferBean.id}";

          var currentStafferName = "${g_stafferBean.name}";
         
          if (srcStafferId && srcStafferId != currentStafferId)
          {

               alert('请不要同时打开多个OA连接，且当前登陆者不同，当前登陆者应为：' + currentStafferName);
               
               return false;
          }
     }

	return true;
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../stock/stock.do" method="post"><input
	type="hidden" name="method" value="addStock">
	
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
			
			<p:pro field="type">
                <option value="1">外网/卢工/马甸询价</option>
            </p:pro>
            
            <p:pro field="mode">
                <option value="0">销售采购</option>
            </p:pro>
            
            <p:pro field="mtype">
                <p:option type="stockManagerType" empty="true"/>
            </p:pro><!--

			<p:pro field="flow" innerString="quick='true'" outString="支持简拼选择">
			<option value="">--</option>
			<c:forEach items="${departementList}" var="item">
			<option value="${item.name}">${item.name}</option>
			</c:forEach>
			</p:pro>
			
			--><p:pro field="stockType" outString="公卖是全公司的都可销售 自卖是只有自己可以销售" innerString="onchange=change()">
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
            
            <p:pro field="areaId" innerString="style='width: 300px'">
                <option value="">--</option>
               <p:option type="123"></p:option>
            </p:pro>

			<p:pro field="target"  innerString="cols=80 rows=3" />
			
			<p:pro field="description"  innerString="cols=80 rows=3" />

            <p:cell title="采购商品类别" id="CG_PTYPE">
            	<select name="ptype" class="select_class" style="width: 240px" onchange="natureChange()">
            		<p:option type="natureType" empty="true"></p:option>
            	</select>&nbsp;&nbsp;
	            <input type="button" value="选择成品产品" name="btn_select" id="btn_select"
	                    class="button_class" onclick="selectProductBom()">&nbsp;&nbsp;
            </p:cell>

			<p:cells id="selects" celspan="2" title="采购处理">
				<table id="mselect">
				 <c:forEach begin="0" end="19" var="item">
					<tr>
						<td>
							<input type="checkbox" name="check_init" id="check_init_${item}" value="${item}" onclick="init()">产品${item + 1}：<input type="button"
								value="&nbsp;选 择&nbsp;" name="qout_${item}" class="button_class"
								onclick="selectProduct(${item})">&nbsp;
							产品:<input
							type="text" name="productName_${item}" value="" size="20" readonly="readonly">
							<input type="hidden" name="productId_${item}" value="">&nbsp;
							参考价格:<input
							type="text" name="price_${item}" id="price_${item}" value="" size="6" oncheck="notNone;isFloat;">&nbsp;
							数量:<input
							type="text" name="amount_${item}" id="amount_${item}" value="" size="6" oncheck="notNone;isNumber;">&nbsp;
							<input type="button" value="&nbsp;清 空&nbsp;" 
                    			class="button_class" onclick="sclearValues(${item})">
							</td>
					</tr>

				  </c:forEach>
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


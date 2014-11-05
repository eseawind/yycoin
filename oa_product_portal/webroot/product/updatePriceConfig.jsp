<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="修改产品价格配置" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../product_js/product.js"></script>
<script language="javascript">

function addBean()
{
	
	submit('确定提交产品配置?', null, check);
}


function selectPrincipalship()
{
    window.common.modal('../admin/pop.do?method=rptQueryPrincipalship&load=1&selectMode=0');
}

function getPrincipalship(oos)
{
    var ids = '';
    var names = '';
    for (var i = 0; i < oos.length; i++)
    {
        if (i == oos.length - 1)
        {
            ids = ids + oos[i].value ;
            names = names + oos[i].pname ;
        }
        else
        {
            ids = ids + oos[i].value + ';';
            names = names + oos[i].pname + ';' ;
        }        
    }

    $O('industryId').value = ids;
    $O('industryName').value = names;
    
}

function load()
{
	if ($$('ftype') == '1')
	{
		$O('cftype').checked = true;
	}
	
	loadForm();		
}

function check()
{
	var price = $$('price');
	
	var minPrice = $$('minPrice');	 

	if (parseFloat(price)<0)
	{
		alert('不能小于0');

		return false;
	}

	if (parseFloat(minPrice) < 0 )
	{
		alert('不能小于0');

		return false;
	}

	if ($$('type') == '1')
	{
		if ($O('cftype').checked == true)
		{
			$O('ftype').value = '1'; // 不受金银价波动影响
		}else
		{
			$O('ftype').value = '0'; // 受金银价波动影响
		}
	}
	
	return true;
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../product/priceConfig.do" method="post">
	<input type="hidden" name="method" value="updatePriceConfig" />
	<input type="hidden" name="id" value="${bean.id}" />
	<input type="hidden" name="industryId" value="${bean.industryId}" />
	<input type="hidden" name="stafferId" value="${bean.stafferId}" />
	<input type="hidden" name="productId" value="${bean.productId}" />
	<input type="hidden" name="nature" value="${bean.nature}" />
	<input type="hidden" name="type" value="${bean.type}" />
	<input type="hidden" name="ftype" value="${bean.ftype}" />
	
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">产品价格配置管理</span> &gt;&gt; 修改产品价格配置</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>产品价格配置信息：${goldSilver}</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.product.bean.PriceConfigBean" opr="1"/>

		<p:table cells="2">
			
			<p:pro field="productId" cell="0" innerString="readonly=true size=80" value="${bean.productName}">
				<input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectProduct()">&nbsp;&nbsp;
			</p:pro>

			<c:if test="${bean.type=='1'}">
				<p:cell title="不受金银价波动影响" end="true">
					<input type="checkbox" name='cftype' id ='cftype' />不受金银价波动影响
				</p:cell>
			
				<p:cell title="邮票总价" end="true">
					<input type=text name='price' id ='price' value='${bean.price}' oncheck="notNone;isFloat;"  maxlength="12" />
				</p:cell>
				
				<p:cell title="辅料费用" end="true">
					<input type=text name='gsPriceUp' id ='minPrice' value='${bean.gsPriceUp}' oncheck="notNone;isFloat;"  maxlength="12" />
				</p:cell>
			</c:if>
			
			<c:if test="${bean.type=='0'}">
				<p:cell title="建议销售价" end="true">
					<input type=text name='minPrice' id ='minPrice' value='${bean.minPrice}' oncheck="notNone;isFloat;"  maxlength="12" />
				</p:cell>
				
				<p:cell title="事业部" end="true">
					<textarea name='industryName'
						head='事业部' id='industryName' rows=2 cols=80 readonly=true
						><c:out value="${bean.industryName}"/></textarea><input
						type="button" value="&nbsp;...&nbsp;" name="qout2" id="qout2"
						class="button_class" onclick="selectPrincipalship()">&nbsp;&nbsp;
						
				</p:cell>		
			</c:if>	
			
		</p:table>
	</p:subBody>

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button"
			class="button_class" id="ok_b" style="cursor: pointer"
			value="&nbsp;&nbsp;提交&nbsp;&nbsp;" onclick="addBean()"></div>
	</p:button>

	<p:message />
	
</p:body>
</form>

</body>
</html>
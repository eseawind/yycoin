<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加促销规则" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../promotion_js/promotion.js"></script>
<script language="javascript">

function addBean()
{
	
	submit('确定提交促销规则?', null, check);
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
            ids = ids + oos[i].value + ',';
            names = names + oos[i].pname + ',' ;
        }        
    }

    $O('industryId').value = ids;
    $O('industryIdsName').value = names;
    
}

function rebateTypeChange()
{

	if ($$('rebateType') == 0)
	{
		$v('rebateMoney_TR', true);
		$v('rebateRate_TR',false);
		$v('custCredit_TR', false);
		$v('busiCredit_TR',false);
	}
	else if($$('rebateType') == 2)
	{
		$v('custCredit_TR', true);
		$v('busiCredit_TR',true);
		$v('rebateMoney_TR', false);
		$v('rebateRate_TR',false);
	}
	else
	{
		$v('custCredit_TR', false);
		$v('busiCredit_TR',false);
		$v('rebateMoney_TR', false);
		$v('rebateRate_TR',true);
	}

}

function load()
{
	loadForm();
		
	rebateTypeChange();

}

function check()
{
	//alert('---------');

	if (parseInt($$('minAmount'), 10) > parseInt($$('maxAmount'), 10))
	{
		alert('最小数量不能大于最大数量');

		$f($O('minAmount'));
		
		return false;
	}

	/*
	if (parseFloat($$('minMoney')) > parseFloat($$('maxMoney')))
	{
		alert('最小金额不能大于最大金额');

		$f($O('minMoney'));
		
		return false;
	}
	*/

	if (compareDate($$('beginDate'),$$('endDate')) > 0)
	{
		alert('开始日期不能大于结束日期');

		return false;		
	}

	var now = '${now}';

	if (compareDate(now ,$$('endDate')) > 0)
	{
		alert('结束日期须大于当前日期');

		return false;		
	}

	 var sailTypeArr = document.getElementsByName("p_sailType");
	 var productTypeArr = document.getElementsByName('p_productType');
	 var productIdArr = document.getElementsByName('productId');

	 var arr = [];

	 if (!sailTypeArr || sailTypeArr.length <= 0)
	 {
		 alert('销售类型，产品类型，产品必须填写一行');
		 return false;
	 }
	 
	 for (var i = 0; i < sailTypeArr.length - 1; i++)
	 {

		 if (sailTypeArr[i].value == '' && productTypeArr[i].value == '' && productIdArr[i].value == '0')
		 {
			 alert('行项目中不能全为--');

			 $f(sailTypeArr[i]);

			 return false;
		 }
		 
	     //子商品不能重复
		 var each = sailTypeArr[i].value + productTypeArr[i].value + productIdArr[i].value
			
			if (containInList(arr, each))
			{
				alert('销售类型，产品类型，产品存在重复行');
				
				return false;
			}
			
			arr.push(each);
	 }

	 return true;
	
}



</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/promotion.do" method="post">
	<input type="hidden" name="method" value="updatePromotion">
	<input type="hidden" name="industryId" value="${bean.industryId}">
	<input type="hidden" name="id" value="${bean.id}">
	<input type="hidden" name="inValid" value="${bean.inValid}">
	
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">促销规则管理</span> &gt;&gt; 修改促销规则</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>促销规则基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.sail.bean.PromotionBean" opr="1"/>

		<p:table cells="2">
			
			<p:pro field="name" innerString="size=50 readonly=readonly" />

			<p:pro field="id" innerString="size=30 readonly=readonly" />

			<p:pro field="minAmount" innerString="size=20 oncheck='isMathNumber'" />
			
			<p:pro field="maxAmount" innerString="size=20 oncheck='isMathNumber'" />
			
			<p:pro field="minMoney" innerString="oncheck='isFloat'" cell="0"/>
			
			
			
			<p:pro field="rebateType" innerString="onchange='rebateTypeChange()'">
				<p:option type="rebateType"></p:option>
			</p:pro>

			<p:pro field="giftBag">
				<p:option type="giftBag"></p:option>
			</p:pro>
			
			<p:pro field="rebateMoney" innerString="oncheck='isFloat'" cell="0" />
			
			<c:if test="${bean.rebateType==2 }">
			<p:pro field="custCredit"  innerString="size=20 oncheck='isMathNumber'" />
			
			<p:pro field="busiCredit"  innerString="size=20 oncheck='isMathNumber'" />
			</c:if>
			<p:pro field="isReturn" cell="1">
				<p:option type="isReturn"></p:option>
			</p:pro>
			<c:if test="${bean.rebateType!=2 }">
			<p:pro field="rebateRate" innerString="size=20 oncheck='isMathNumber'" />
			</c:if>
			<p:pro field="maxRebateMoney" innerString="oncheck='isFloat'" />
			
			<p:pro field="isAddUp" cell="0">
				<p:option type="isAddUp"></p:option>
			</p:pro>
			
			<p:pro field="payType" >
			    <option value=''>--</option>
			    <option value='2'>客户信用和业务员信用额度担保</option>
                <option value='3'>信用担保</option>
                <option value='1'>款到发货(黑名单客户/零售)</option>
			</p:pro>


			
			<p:pro field="cType" innerString="quick=true">
				<p:option type="102"></p:option>
			</p:pro>
			

			
			<p:pro field="beginDate" />
			
			<p:pro field="endDate" />

			<p:pro field="refTime">
				<p:option type="refTime" empty="false"></p:option>
			</p:pro>
			
			<p:pro field="payTime" >
				<p:option type="payTime" empty="false"></p:option>
			</p:pro>			
			
			<p:cell title="事业部" end="true">
				<textarea name='industryIdsName'
					head='事业部' id='industryIdsName' rows=2 cols=80 readonly=true
					oncheck="notNone;"><c:out value=" ${bean.industryName}"/></textarea> <font color='#FF0000'>*</font> <input
					type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
					class="button_class" onclick="selectPrincipalship()">&nbsp;&nbsp;
			</p:cell>
			
			<p:pro field="description" cell="0" innerString="rows=2 cols=80" />			
			
		</p:table>
	</p:subBody>

	<p:line flag="0" />
	
	<tr id="subProduct_tr" >
		<td colspan='2' align='center'>
		<table width="98%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables">
					<tr align="center" class="content0">
						<td width="25%" align="center">销售类型</td>
						<td width="25%" align="center">产品类型</td>
						<td width="45%" align="center">产品名称</td>
						<td width="5%" align="left"><input type="button"
							accesskey="A" value="增加(A)" class="button_class"
							onclick="addTr()"></td>
					</tr>
					
					 <c:forEach items="${itemList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>

                            <td width="25%" align="center">         
								<select name="p_sailType" class="select_class" values="${item.sailType}" style="width: 100%;">
					            	<p:option type="productSailType" empty="true"></p:option>
					         	</select>
							</td>
							<td width="25%" align="center">
								<select name="p_productType" class="select_class" values="${item.productType}" style="width: 100%;">
					            	<p:option type="productType" empty="true"></p:option>
					         	</select>
							</td>			
							<td width="45%" align="center"><input type="text"
								style="width: 100%; cursor: pointer;" readonly="readonly" value="${item.productName}"
								name="targetName" onclick="selectProduct(this)">
							<input type="hidden" name="productId" value="${item.productId}"></td>		
							<td width="5%" align="center"><input type=button
								value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>

                           </tr>
                    </c:forEach>
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button"
			class="button_class" id="ok_b" style="cursor: pointer"
			value="&nbsp;&nbsp;提交&nbsp;&nbsp;" onclick="addBean()"></div>
	</p:button>

	<p:message />
	
</p:body>
</form>

<table>
	<tr class="content1" id="trCopy" style="display: none;">
		<td width="25%" align="center">         
			<select name="p_sailType" class="select_class" style="width: 100%;">
            	<p:option type="productSailType" empty="true"></p:option>
         	</select>
		</td>
		<td width="25%" align="center">
			<select name="p_productType" class="select_class" style="width: 100%;">
            	<p:option type="productType" empty="true"></p:option>
         	</select>
		</td>			
		<td width="45%" align="center"><input type="text"
			style="width: 100%; cursor: pointer;" readonly="readonly" value="--"
			name="targetName" onclick="selectProduct(this)">
		<input type="hidden" name="productId" value="0"></td>		
		<td width="5%" align="center"><input type=button
			value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
	</tr>
</table>

</body>
</html>
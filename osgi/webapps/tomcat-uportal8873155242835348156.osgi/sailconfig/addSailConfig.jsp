<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加结算价格" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定增加结算价格?', null, null);
}

function selectProduct()
{
    window.common.modal('../product/product.do?method=rptQueryProduct&load=1&selectMode=1');
}

function getProduct(oos)
{
    var obj = oos[0];
    
    $O('productName').value = obj.pname;   
    $O('productId').value = obj.value;   
    $O('sailPrice').value = obj.psailprice;   
}

//选择职位
function selectPrin()
{
    window.common.modal('../admin/org.do?method=popOrg');
}

function setOrgFromPop(id, name, level)
{
    $O('industryId').value = id;
    
    $O('industryName').value = name;
}

function clears()
{
    $O('productId').value = '';
    $O('productName').value = '公共';
}

function autoCalculation()
{
    var dir = parseFloat($O('dirSailPrice').value);
    
    var src = parseFloat($O('sailPrice').value);
    
    var pratio = parseInt($O('pratio').value);
    
    var iratio = parseInt($O('iratio').value);
    
    //返回
    if (src == 0)
    {
        return;
    }
    
    if (pratio != 0 && iratio != 0)
    {
        //计算价格
        var dest = src +  src * (pratio / 1000.0) + src * (iratio / 1000.0);
        
        $O('dirSailPrice').value = formatNum(dest, 2);
        
        return;
    }
    
    //这里dir就不计算
    if (dir == 0)
    {
        return;
    }
    
    if (math_compare(src, dir) > 0)
    {
        alert('目标结算价必须大于原结算价');
        
        return;
    }
    
    if (pratio == 0 && iratio == 0)
    {
        //都是0就平均分担
        var avg = (dir - src) / (src + 0.0) / 2;
        
        var savg = formatNumToInt(avg * 1000);
        
        $O('pratio').value = savg;
        $O('iratio').value = savg;
        
        return;
    }
    
    if (pratio != 0)
    {
        var tmp = (dir - src) / (src + 0.0) - (pratio / 1000.0);
        
        if (math_compare(tmp, 0) < 0)
        {
            alert('总部结算率过高');
            return;
        }
        
        var savg = formatNumToInt(tmp * 1000);
        
        $O('iratio').value = savg;
        
        return;
    }
    else
    {
        var tmp = (dir - src) / (src + 0.0) - (iratio / 1000.0);
        
        if (math_compare(tmp, 0) < 0)
        {
            alert('事业部结算率过高');
            return;
        }
        
        var savg = formatNumToInt(tmp * 1000);
        
        $O('pratio').value = savg;
        
        return;
    }
}

function formatNumToInt(val)
{
    var savg = formatNum(val, 0);
    
    if (savg.charAt(savg.length - 1) == '.')
    {
        return savg.substring(0, savg.length - 1);
    }
    
    return savg;
}

function resetRadio()
{
    $O('pratio').value = 0;
    $O('iratio').value = 0;
}


</script>

</head>
<body class="body_class">
<form name="formEntry" action="../sail/config.do" method="post">
<input type="hidden" name="method" value="addSailConfig">
<input type="hidden" name="productId" value="0">


<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">结算价格管理</span> &gt;&gt; 增加结算价格</td>
	<td width="85"></td>
	
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>结算价格基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.sail.bean.SailConfBean" />

		<p:table cells="1">
		    
		    <p:pro field="sailType">
                <p:option type="productSailType" empty="true"/>
            </p:pro>
            
            <p:pro field="productType">
                <p:option type="productType" empty="true"/>
            </p:pro>

			<p:pro field="productId" value="公共" innerString="size=60">
			     <input type="button" value="&nbsp;选择产品&nbsp;" name="qout1" id="qout1"
                    class="button_class" onclick="selectProduct()">&nbsp;
                 <input type="button" value="&nbsp;清 空&nbsp;" name="qout" id="qout"
                        class="button_class" onclick="clears()">&nbsp;&nbsp;
			</p:pro>
			
			<p:cell title="原结算价">
			   <input type="text" name="sailPrice" id="sailPrice" value="0.0" readonly="readonly">
			</p:cell>
			
			<p:pro field="industryId" innerString="style='width:240px'">
                <p:option type="industryList" empty="true"/>
            </p:pro>
            
            <p:pro field="pratio" value="0" innerString="size=60 oncheck='isMathNumber'"/>
            
            <p:pro field="iratio" value="0" innerString="size=60 oncheck='isMathNumber'"/>
            
            <p:cell title="目标结算价">
               <input type="text" name="dirSailPrice" id="dirSailPrice" value="0.0" oncheck="isFloat">&nbsp;
               <input type="button" value="&nbsp;自动计算&nbsp;" name="qout3" id="qout3"
                    class="button_class" onclick="autoCalculation()">&nbsp;
                 <input type="button" value="&nbsp;重置结算率&nbsp;" name="qout4" id="qout4"
                    class="button_class" onclick="resetRadio()">
            </p:cell>
			
			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message2/>
</p:body></form>
</body>
</html>


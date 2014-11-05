<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="虚拟产品" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../product_js/composeProduct.js"></script>
<script language="javascript">

function addBean()
{
    submit('确定申请此虚拟产品?', null, checks);
}

function checks()
{
	var ids = document.getElementsByName('srcProductId');
	
	var arr = [];
	
	for (var i = 0; i < ids.length; i++)
	{
		var each = ids[i];
		
		if (containInList(arr, each.value))
		{
			alert('产品不能重复');
			
			return false;
		}
		
		arr.push(each.value);
	}
	
    return true;
}

var current;

function selectProduct(obj)
{
    current = obj;
    
    window.common.modal('../product/product.do?method=rptQueryProduct&load=1&selectMode=1&abstractType=0&status=0');
}

function getProduct(oos)
{
	var oo = oos[0];
	
    current.value = oo.pname;
    
    var hobj = getNextInput(current.nextSibling);
    
    hobj.value = oo.value;
}

function getNextInput(el)
{
    if (el.tagName && el.tagName.toLowerCase() == 'input')
    {
        return el;
    }
    else
    {
        return getNextInput(el.nextSibling);
    }
}
</script>

</head>
<body class="body_class" onload="addTr()">
<form name="formEntry" action="../product/product.do" method="post"><input
	type="hidden" name="method" value="addAbstractProduct"> 

<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javaScript:window.history.go(-1);">产品管理</span> &gt;&gt; 申请虚拟产品</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>虚拟产品</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">
			<p:tr align="left">
			虚拟产品名称：<input type="text" style="width: 20%;" value="" oncheck="notNone" name="name" >
			描述：<input type="text" style="width: 50%;" value="" name="description" >
			</p:tr>
		</p:table>
	</p:subBody>
	
	<p:tr></p:tr>
	
	<tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="50%" align="center">源产品</td>
                        <td width="5%" align="left"><input type="button" accesskey="A"
                            value="增加(A)" class="button_class" onclick="addTr()"></td>
                    </tr>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		  <input type="button" class="button_class" id="sub_b"
            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean()">
        </div>
	</p:button>
	
	<p:message/>
</p:body>
</form>
<table>
    <tr class="content1" id="trCopy" style="display: none;">
         <td width="30%" align="center"><input type="text" 
         style="width: 100%;cursor: pointer;" readonly="readonly" value="" oncheck="notNone" name="targerName" onclick="selectProduct(this)">
         <input type="hidden" name="srcProductId" value="">
         </td>
        <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
</table>
</body>
</html>


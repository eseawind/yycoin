<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="仓区移动" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">

function getProductRelation(oos)
{
	var ids = '';
    var names = '';
    var table = $O("tables");
    for (var i = 0; i < oos.length; i++)
    {
			var tr = document.createElement("tr");
			trow = 	table.insertRow(-1);
			trow.className="content0";
		
			var tcell = document.createElement("td");
			var tcell2 = document.createElement("td");
			var tcell3 = document.createElement("td");
			tcell.align="center";
			tcell2.align="center";
			tcell3.align="center";
			tcell.innerHTML = oos[i].pname;
			tcell3.innerHTML = oos[i].pamount;
			tcell2.innerHTML = "<input type='text' value='' name='amount' />";
			
			trow.appendChild(tcell);
			trow.appendChild(tcell3);
			trow.appendChild(tcell2);
		
    	
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
    var sid=$O('sourceRelationId').value;
    if(sid==''||sid==null)
    {
    	$O('sourceRelationId').value =  ids;
    }
    else
    {
    $O('sourceRelationId').value = $O('sourceRelationId').value+','+ids;
    }
	return trow;
}

function reset()
{
}

function selectProducts()
{

	var depotpartId = $$('src');
	if (depotpartId == null || depotpartId == '')
	{
		alert('请选择仓区');
		return;
	}
	
	window.common.modal('../depot/storage.do?method=rptQueryProductInDepotpart&load=1&flag=1&selectMode=2&depotpartId='+ depotpartId);
}

function applyPasswords()
{
	var amounts=document.getElementsByName("amount");
	var amount='';
	 for (var i = 0; i < amounts.length; i++)
	    {
		 	 if (i == amounts.length - 1)
		        {
		 			amount=amount+amounts[i].value;
		        }
		        else
		        {
		        	amount=amount+amounts[i].value+ ',' ;
		        }       
	    }
	 $O('amounts').value =amount;
	submit('确定移动产品?');
}


function load()
{
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../depot/storage.do" method="post">
<input type="hidden" value="moveDepotpart" name="method"> 
<input type="hidden" value="" name="sourceRelationId"> 
<input type="hidden" value="" name="amounts"> 
<input type="hidden" value="${id}" name="id"> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span
		style="cursor: hand" onclick="javascript:history.go(-1)">仓区列表</span>
	&gt;&gt; 产品移动</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="80%">

	<p:title>
		<td class="caption"><strong>产品移动：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<p:table cells="1" >
			<p:cell title="源仓区" width="20">
				<select name="src" oncheck="notNone;noEquals($$('dest'))" message="源仓区和目的仓区不能相同" style="width: 240px">
					<option value="">--</option>
					<c:forEach items="${depotpartList}" var="item">
						<option value="${item.id}">${item.name}</option>
					</c:forEach>
				</select>
			</p:cell>

			<p:cell title="目的仓区" width="20">
				<select name="dest" oncheck="notNone;" style="width: 240px">
					<option value="">--</option>
					<c:forEach items="${depotpartList}" var="item">
						<option value="${item.id}">${item.name}</option>
					</c:forEach>
				</select>
			</p:cell>

			<p:cell title="备注">
                <textarea name="apply" rows=5 style="width:73%" oncheck="notNone;" ></textarea>
                <font color="#FF0000">*</font>
            </p:cell>
            
            <p:cell title="移动产品">
			<input type="button" value="&nbsp;选择产品&nbsp;"
					class="button_class" onclick="selectProducts()">
			</p:cell>
		</p:table>
	</p:subBody>
	<table width="73%" align="center" cellspacing='3' border='1' class="table0" id="tables">
				<tr align=center class="content0">
					<td align="center"><strong>产品</strong></td>
					<td align="center"><strong>可转数量</strong></td>
					<td align="center"><strong>需转数量</strong></td>
				</tr>
	</table>

	<p:line flag="1" />

	<p:button leftWidth="87%" rightWidth="13%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;移 动&nbsp;&nbsp;"
			onclick="applyPasswords()">&nbsp;&nbsp;<input type="button"
			class="button_class" onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message2 />
	
</p:body></form>
</body>
</html>


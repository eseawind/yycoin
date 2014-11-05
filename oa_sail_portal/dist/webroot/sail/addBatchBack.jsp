<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="项目申请" guid="true"/>
<LINK href="../css/tabs/jquery.tabs-ie.css" type=text/css rel=stylesheet>
<LINK href="../css/tabs/jquery.tabs.css" type=text/css rel=stylesheet>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script src="../js/jquery/jquery.js"></script>
<SCRIPT src="../js/jquery/jquery.tabs.js"></SCRIPT>
<script language="javascript">

function selCustomer(obj)
{
	    window.common.modal('../client/client.do?method=rptQueryAllClient&load=1&first=1');
}

function getCustomer(obj)
{
    $O('customerId').value = obj.value;
    $O('cname').value = obj.pname;
}

function addBean(opr)
{
    submit('确定申请?', null, checks);
}

function checks()
{
	var objs = document.getElementsByName('s_projectproId');

	if (objs.length == 1)
	{
		alert('请选择要退货的产品');
		return  false;
	}
	
	var arr = [];
	
	for (var i = 0; i < objs.length -1; i++)
	{
		var prodid = objs[i].value;

		if (containInList(arr, prodid))
		{
			alert('产品存在重复行');
			
			return false;
		}
		
		arr.push(prodid);
	}

	return true;
}

function addPayTr()
{
    for (var i = 0; i < 1; i++)
    {
        addTrInner("tables_pay", "trCopy_pay");
    }
}

function load()
{
	var err = false;

	err = '${backError}';

	if (err == '' || err == 'false')
	{
		addPayTr();
	}

	loadForm();
	
	var outType = $$('outType');
	
	if (outType == '2')
	{
		$O('cname').value = "公共客户";
		$O('customerId').value = "99";
		$O('cname').disabled=true;
		$v('staffer_tr', true);
	}
	else{
		$v('staffer_tr', false);
	}
}

function removeTr(obj)
{
    obj.parentNode.parentNode.removeNode(true);
}


var proobj
function selectProjectpro(obj)
{
	proobj=obj;	
	window.common.modal('../product/product.do?method=selectProjectpro&firstLoad=1&selectMode=1');
}

function setProjectProVal(oos)
{
	var obj = oos;
    
	proobj.value = obj.pname;
    
    var tr = getTrObject(proobj);
    
    setInputValueInTr(tr, 's_projectproId', obj.pvalue);
    
}

function typeChange()
{
	var outType = $$('outType');
	if (outType == 2)
	{
		$O('cname').value = "公共客户";
		$O('customerId').value = "99";
		$O('cname').disabled=true;
		$v('staffer_tr', true);
	}else{
		$O('cname').value = "";
		$O('customerId').value = "";
		$O('cname').disabled=false;
		$v('staffer_tr', false);
		$O('stafferName').value = "";
	    $O('stafferId').value = "";
	}
}

function selectStaffer()
{
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
    var oo = oos[0];
    
    $O('stafferName').value = oo.pname;
    $O('stafferId').value = oo.value;
}

</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/out.do?method=batchBack" method="post">
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="addOrUpdate" value="0"> 
<input type="hidden" name="processId" value="">
<input type="hidden" name="customerId" value="${customerId}">  
<input type="hidden" name="type" value="0"> 
<input type="hidden" name="stafferId" value="${stafferId}"> 
<input type="hidden" name="departmentId" value="${g_stafferBean.principalshipId}"> 
<input type="hidden" name="stype" value="${g_stafferBean.otype}">
<input type="hidden" name="stafferProject" value=""> 

<p:body width="100%">

	<p:line flag="0" />

	<p:subBody width="98%">
	    
		<p:table cells="2">
		    <p:cell title="类型" end="true">
            	<select name="outType" style="width: 300px" values="${outType}" class="select_class" onchange="typeChange()">
            		<option value="0">销售出库</option>
            		<option value="1">委托代销</option>
            		<option value="2">领样</option>
            		<option value="3">客户铺货</option>
            	</select> 
			</p:cell>  
            <p:cell title="客户" end="true">
            <input type="text" style="width: 300px" readonly="readonly" name="cname" value="${cname}" oncheck="notNone;" onclick='selCustomer(this)' />
            <font color="#FF0000">*</font> 
			</p:cell>    
			
			<tr class="content1" id = "staffer_tr">
						<td align="left" id="outd">业务员：</td>
						<td colspan="3">
						<input type="text" name="stafferName" maxlength="14" value="${stafferName}"  size="20" 
                            onclick="selectStaffer()" style="cursor: pointer;"
                            readonly="readonly">
                            <font color="#FF0000">*</font>
                        </td>
												
					</tr>
			
			<tr class="content2">
						<td align="left">备注：</td>
						<td colspan="3" align="left"><textarea rows="3" cols="55" oncheck="notNone;"
							name="description">${description}</textarea>
							<font color="#FF0000">*</font>
							</td>
			</tr>   
			
			<tr class="content2">
                        <td align="left">退货仓库：</td>
                        <td colspan="3">
                        <select name="dirDeport" style="WIDTH: 300px;" values="${dirDeport}" oncheck="notNone">
                            <c:forEach items='${locationList}' var="item">
                                        <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select><font color="#FF0000">*</font>
                       </td>
            </tr>
            
          	<p:cell title="时间限制（只限销售出库）" end="true">
            	<select name="timeLimit" style="width: 300px" values="${timeLimit}" class="select_class">
            		<option value="0">近6个月以内</option>
            		<option value="1">6个月以前</option>
            	</select> 
			</p:cell> 
            
        </p:table>
	</p:subBody>
	

	<p:title>
        <td class="caption">
         <strong>产品行项目</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr id="pay_main_tr">
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_pay">
                    <tr align="center" class="content0">
                        <td width="20%" align="center">产品</td>
                        <td width="5%" align="center">数量</td>
                        <td width="5%" align="left"><input type="button" accesskey="B"
                            value="增加" class="button_class" onclick="addPayTr()"></td>
                    </tr>
                    
                    <c:forEach items="${backWrapList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					         <td align="left">
					         <input type=text name = 'projectpro'  id = 'projectpro' value="${item.productName}" oncheck="notNone;"  onclick="selectProjectpro(this)"  readonly  style="width: 100%" >
					         <input type="hidden" name="s_projectproId" value="${item.productId}"> 
					         </td>
					         
					         <td align="left"><input type="text" oncheck="notNone;" style="width: 100%"
					                    name="procount" value="${item.amount}" >
					         </td>
					         
					        <td width="5%" align="left"><input type=button name="pay_del_bu"
					            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>

                           </tr>
                    </c:forEach>
                    
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
</p:body>
</form>
<table>
    <tr class="content1" id="trCopy_pay" style="display: none;">
         <td align="left">
         <input type=text name = 'projectpro'  id = 'projectpro' oncheck="notNone;"  onclick="selectProjectpro(this)"  readonly  style="width: 100%" >
         <input type="hidden" name="s_projectproId" value=""> 
         </td>
         
         <td align="left"><input type="text" oncheck="notNone;" style="width: 100%"
                    name="procount" value="" >
         </td>
         
        <td width="5%" align="center"><input type=button name="pay_del_bu"
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    
</table>
<br>

 <p:line flag="1" />
    
	<p:button leftWidth="98%" rightWidth="0%">
		<div align="right">
		  <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean()">
        </div>
	</p:button>
	<p:message2/>
</body>
</html>


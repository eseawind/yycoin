<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="项目申请" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../project_js/project.js"></script>
<script language="javascript">

function processBean(opr)
{
    $("input[name='oprType']").val(opr);
    
    var msg = '';
    
    var checkFun = null;
    
    if ("0" == opr)
    {
        msg = '确定通过项目申请?';
    }
    
    if ("1" == opr)
    {
        msg = '确定驳回申请到初始?';
    }
    
    if ("2" == opr)
    {
        msg = '确定驳回申请到上一步?';
    }
    
    if ($O('processer'))
    {
	    if ("0" != opr)
	    {
	        $O('processer').oncheck = '';
	    }
	    else
	    {
	        $O('processer').oncheck = 'notNone';
	    }
    }
    
    
    submit(msg, null, checkFun);
}

function selCustomer(obj)
{
	    window.common.modal('../client/client.do?method=rptQuerySelfClient1&stafferId=${user.stafferId}&load=1');
}

function getCustomer1(obj)
{
    $O('customerId').value = obj.value;
    $O('cname').value = obj.pname;
}

function addBean(opr)
{
    $("input[name='oprType']").val(opr);
    
    if ("0" == opr)
    {
        $O('processer').oncheck = '';
    }
    else
    {
        $O('processer').oncheck = 'notNone';
    }
    
    submit('确定项目申请?', null, checks);
}

function load()
{
}

</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../project/project.do?method=processProject" method="post">
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="addOrUpdate" value="0"> 
<input type="hidden" name="processId" value="">
<input type="hidden" name="customerId" value="">  
<input type="hidden" name="type" value="0"> 
<input type="hidden" name="stafferId" value="${g_stafferBean.id}"> 
<input type="hidden" name="departmentId" value="${g_stafferBean.principalshipId}"> 
<input type="hidden" name="stype" value="${g_stafferBean.otype}">
<input type="hidden" name="stafferProject" value=""> 

<p:navigation height="22">
	<td width="550" class="navigation">项目申请</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		 <strong>项目申请</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
	
	    <p:class value="com.china.center.oa.project.bean.ProjectBean" opr="1"/>
	    
		<p:table cells="2">
            <p:pro field="projectName" innerString="readonly"/>
            <p:pro field="projectType" innerString="readonly">
                <p:option type="201"></p:option>
            </p:pro>
            
            <p:cell title="客户" end="true" >
            <input type="text" size="25" readonly="readonly" value="${bean.customerName }" name="cname" onclick='selCustomer(this)' /> 
			</p:cell>            
            <p:pro field="predictSucRate" cell="0" innerString="readonly"/>
            <p:pro field="description" cell="0" innerString="rows=4 cols=55 readonly" />
            
        </p:table>
	</p:subBody>
	
	
    <p:title>
        <td class="caption">
         <strong>人员行项目</strong>
        </td>
    </p:title>

    <p:line flag="0" />
	
	<tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="15%" align="center">项目人员</td>
                        <td width="15%" align="center">人员角色</td>
                    </tr>
                    <c:forEach items="${bean.stafferProjectList}" var="item">
                    <tr align="center" class="content1">
                        <td width="15%" align="center">${item.pstafferName}</td>
                        <td width="10%" align="center">${my:get('204', item.role)}</td>
                    </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>
        </td>
    </tr>

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
                        <td width="10%" align="center">项目产品</td>
                        <td width="15%" align="center">产品数量</td>
                        <td width="15%" align="center">产品单价</td>
                    </tr>
                     <c:forEach items="${bean.proLineProjectList}" var="item">
                    <tr align="center" class="content1">
                        <td width="15%" align="center">${item.projectproName}</td>
                        <td width="10%" align="center">${item.procount}</td>
                        <td width="10%" align="center">${item.prounitprice}</td>
                    </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
    <p:title>
        <td class="caption">
         <strong>配件行项目</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_share">
                    <tr align="center" class="content0">
                        <td width="30%" align="center">配件明细</td>
                        <td width="30%" align="center">配件数量</td>
                    </tr>
                </table>
                </td>
            </tr>
        </table>
 		<p:button leftWidth="98%" rightWidth="0%" >
        <div align="right">
        <input type="button" class="button_class"
            id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
            onclick="javaScript:window.history.go(-1);"></div>
            <div align="right">
        <input type="button" class="button_class" id="sub_b1"
            value="&nbsp;&nbsp;通 过&nbsp;&nbsp;" onclick="processBean(0)">
          &nbsp;&nbsp;
          <c:if test="${token.reject == 1}">
          <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;驳回到初始&nbsp;&nbsp;" onclick="processBean(1)">
          </c:if>
          <c:if test="${token.rejectToPre == 1}">
          <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;驳回到上一步&nbsp;&nbsp;" onclick="processBean(2)">
          </c:if>
   		 </p:button>	
        </td>
    </tr>
    
</p:body>
</form>
</body>
</html>


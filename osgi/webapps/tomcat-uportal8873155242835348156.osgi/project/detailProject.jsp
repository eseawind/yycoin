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
<script language="JavaScript" src="../project_js/project.js"></script>
<script src="../js/jquery/jquery.js"></script>
<SCRIPT src="../js/jquery/jquery.tabs.js"></SCRIPT>
<script language="javascript">
function changeURL(id,url)
{
	$('#'+id).attr("src",url);
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
	$('#container-1').tabs();
    
    //显示container-1
    $('#container-1').css({display:"block"});
}

</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../project/project.do?method=addOrUpdateProject" method="post">
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
                <p:option type="213"></p:option>
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
                        <td width="10%" align="center">${my:get('217', item.role)}</td>
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
   		 </p:button>	
        </td>
    </tr>
    
</p:body>
</form>
        <div id="container-1" style="display: none;">
<ul>
	<li><a href="#fragment-1" ><span>合同</span></a></li>
	
	<li><a href="#fragment-2" onclick="changeURL('ifr2','../project/project.do?method=queryTabTask')"><span>任务</span></a></li>
	
    <li><a href="#fragment-3" onclick="changeURL('ifr3','../sail/out.do?method=queryTabOrder')"><span>订单</span></a></li>
    
    <li><a href="#fragment-4" onclick="changeURL('ifr4','../finance/bill.do?method=queryTabInBillAndOutBill')"><span>收付款单</span></a></li>
    
    <li><a href="#fragment-5" onclick="changeURL('ifr5','../tcp/expense.do?method=queryTabExpense')"><span>申请报销</span></a></li>
	
    <li><a href="#fragment-6" onclick="changeURL('ifr6','../project/project.do?method=turnDrawGant')"><span>甘特图</span></a></li>
    
    <li><a href="#fragment-7" onclick="changeURL('ifr7','../project/project.do?method=uploadFileTab&flag=1')"><span>附件</span></a></li>
	
</ul>

<div id="fragment-1"><IFRAME height="100%"
	src="../project/project.do?method=getTabAgreement"
	id="ifr1" frameborder="0" width="100%" scrolling="auto"></IFRAME></div>

<!-- ///////////////////////////////////////////////////////////////////////// -->

<div id="fragment-2"><IFRAME height="100%"
    src=""
    id="ifr2" frameborder="0" width="100%" scrolling="auto"></IFRAME></div>

<div id="fragment-3"><IFRAME height="100%"
    src=""
    id="ifr3" frameborder="0" width="100%" scrolling="auto"></IFRAME></div>

<div id="fragment-4"><IFRAME height="100%"
    src=""
    id="ifr4" frameborder="0" width="100%" scrolling="auto"></IFRAME></div>
    
    <div id="fragment-5"><IFRAME height="100%"
    src=""
    id="ifr5" frameborder="0" width="100%" scrolling="auto"></IFRAME></div>

<div id="fragment-6"><IFRAME height="100%"
    src=""
    id="ifr6" frameborder="0" width="100%" scrolling="auto"></IFRAME></div>

<div id="fragment-7"><IFRAME height="100%"
    src=""
    id="ifr7" frameborder="0" width="100%" scrolling="auto"></IFRAME></div>

</div>
</body>
</html>


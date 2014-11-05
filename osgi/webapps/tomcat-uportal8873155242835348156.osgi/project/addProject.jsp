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
    if(isNaN($O('predictSucRate').value))
        {  
    	alert("预计成功率必须为数字");
    	return false;
         }
    
    submit('确定项目申请?', null, checks);
}

function load()
{
	addTr();
	
	addPayTr();
	
	addShareTr();
	
	$v('tr_att_more', false);
	
	borrowChange();

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
	
	    <p:class value="com.china.center.oa.project.bean.ProjectBean" />
	    
		<p:table cells="2">
            <p:pro field="projectName" />
            <p:pro field="projectType" innerString="onchange='borrowChange()'">
                <p:option type="213"></p:option>
            </p:pro>
            
            <p:cell title="客户" end="true">
            <input type="text" size="25" readonly="readonly" name="cname" onclick='selCustomer(this)' /> 
			</p:cell>            
            <p:pro field="predictSucRate" cell="0"/>
            <p:pro field="description" cell="0" innerString="rows=4 cols=55" />
            
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
                        <td width="5%" align="left"><input type="button" accesskey="A"
                            value="增加" class="button_class" onclick="addTr()"></td>
                    </tr>
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
                        <td width="5%" align="left"><input type="button" accesskey="B"
                            value="增加" class="button_class" onclick="addPayTr()"></td>
                    </tr>
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
                        <td width="5%" align="left"><input type="button" accesskey="B"
                            value="增加" class="button_class" onclick="addShareTr()"></td>
                    </tr>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
    <p:title>
        <td class="caption">
         <strong>提交/审核</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr id="sub_main_tr">
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_pay">
                    <tr align="center" class="content0">
                        <td width="15%" align="center">提交到</td>
                        <td align="left">
                        <input type="text" name="processer" readonly="readonly" oncheck="notNone" head="下环处理人"/>&nbsp;
                        <font color=red>*</font>
                        <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                            class="button_class" onclick="initSelectNext()">&nbsp;&nbsp;
                        </td>
                    </tr>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
    <p:line flag="1" />
    
	<p:button leftWidth="98%" rightWidth="0%">
		<div align="right">
		<input type="button" class="button_class" id="sub_b1"
            value="&nbsp;&nbsp;保存&nbsp;&nbsp;" onclick="addBean(0)">
		  &nbsp;&nbsp;
		  <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean(1)">
        </div>
	</p:button>
	
	<p:message2/>
</p:body>
</form>
<table>
    <tr class="content1" id="trCopy" style="display: none;">
         <td align="left">
         <input type=text name = 'staffer'  id = 'staffer' onclick="selectAllstaffer(this)"  readonly   style="width: 100%" >
         <input type="hidden" name="s_prostafferId" value=""> 
         </td>
         <td align="left">
         <select name="role" class="select_class" style="width: 100%;" oncheck="notNone">
            <p:option type="217" empty="true"></p:option>
         </select>
         </td>
        <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    
    <tr class="content1" id="trCopy_pay" style="display: none;">
         <td align="left">
         <input type=text name = 'projectpro'  id = 'projectpro'  onclick="selectProjectpro(this)"  readonly  style="width: 100%" >
         <input type="hidden" name="s_projectproId" value=""> 
         </td>
         
         <td align="left"><input type="text" style="width: 100%"
                    name="procount" value="" >
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%"
                    name="prounitprice" value="" >
         </td>
         
        <td width="5%" align="center"><input type=button name="pay_del_bu"
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    
    <%@include file="share_tr.jsp"%>
    
</table>


</body>
</html>


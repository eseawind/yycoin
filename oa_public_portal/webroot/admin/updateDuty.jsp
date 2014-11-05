<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="修改纳税实体" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定修改纳税实体?');
}

function selectStaffer()
{	
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
	 $O('invoicerName').value = oo.pname;
     $O('invoicer').value = oo.value;
}

</script>

</head>
<body class="body_class">
<form name="addApply" action="../admin/duty.do" method="post">
<input type="hidden" name="method" value="updateDuty">
<input type="hidden" name="id" value="${bean.id}">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">纳税实体管理</span> &gt;&gt; 修改纳税实体</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>纳税实体基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.publics.bean.DutyBean" opr="1"/>

		<p:table cells="1">

			<p:pro field="name" />
			
			<p:pro field="type">
				<p:option type="dutyType"/>
			</p:pro>
			
			<p:pro field="mtype" innerString="readonly=true">
                <p:option type="pubManagerType" empty="true"/>
            </p:pro>
            
            <p:pro field="showType">
				<p:option type="dutyShowType"/>
			</p:pro>

			<p:pro field="icp"  />
			
			<p:pro field="dues"  />
			
			<p:pro field="invoicer">
                <p:option type="stafferList" empty="true"/>
            </p:pro>

			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message/>
</p:body></form>
</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加退货快递信息" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/buffalo.js"></script>
<script language="javascript">
var END_POINT="${pageContext.request.contextPath}/bfapp";

var buffalo = new Buffalo(END_POINT);

var cmap = window.top.topFrame.cmap;

var pList = window.top.topFrame.pList;

function addBean()
{
	submit('确定认领?', null, checkValue);
}

function checkValue()
{   
    var fileName = $O('atts').value;
        
    if ("" == fileName)
    {
        alert("请输入要上传的文件名");
        return false;
    }
    
    if (fileName.indexOf('xls') == -1)
    {
        alert("只支持XLS文件格式!");
        return false;
    }
    
    return true;
}

function changes(obj)
{
    removeAllItem($O('fromCity'));
    setOption($O('fromCity'), "", "--");
    if ($$('fromProvince') == "")
    {
        return;
    }
    
    var cityList = cmap[$$('fromProvince')];
    for (var i = 0; i < cityList.length; i++)
    {
        setOption($O('fromCity'), cityList[i].id, cityList[i].name);
    }
}

function changes2(obj)
{
    removeAllItem($O('toCity'));
    setOption($O('toCity'), "", "--");
    if ($$('toProvince') == "")
    {
        return;
    }
    
    var cityList = cmap[$$('toProvince')];
    for (var i = 0; i < cityList.length; i++)
    {
        setOption($O('toCity'), cityList[i].id, cityList[i].name);
    }
}

function load()
{
    setOption($O('fromProvince'), "", "--");
    for (var i = 0; i < pList.length; i++)
    {
        setOption($O('fromProvince'), pList[i].id, pList[i].name);
    }
    
    setOption($O('toProvince'), "", "--");
    for (var i = 0; i < pList.length; i++)
    {
        setOption($O('toProvince'), pList[i].id, pList[i].name);
    }
    
    loadForm();
}

function selectStaffer()
{
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
    var oo = oos[0];
    
    $O('receiver').value = oo.pname;
    $O('receiverId').value = oo.value;
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/outback.do?method=claimOutBack" enctype="multipart/form-data" method="post">
<input type="hidden" name="receiverId" value="${bean.receiverId}">
<input type="hidden" name="id" value="${bean.id}">
<input type="hidden" name="mode" value="1">

<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">退货管理</span> &gt;&gt; 增加退货快递信息</td>
	<td width="85"></td>
	
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.sail.bean.OutBackBean" opr="2"/>

		<p:table cells="2">
		    
		    <p:pro field="expressCompany">
               <option value="">--</option>
               <c:forEach items="${transportList}" var="item">
                   <option value="${item.id}">${item.name}</option>
               </c:forEach>
            </p:pro>
            
            <p:pro field="transportNo" />

			<p:pro field="fromProvince"
				innerString="quick=true onchange=changes(this)" />
				
			<p:cell title="发货城市">${bean.fromCityName}</p:cell>
			
			<p:pro field="fromAddress" innerString="size=120" cell="0"/>
			
			<p:pro field="fromer" />
			
			<p:pro field="fromMobile" />
            
            <p:pro field="toProvince"
				innerString="quick=true onchange=changes2(this)" />
			<p:cell title="收货城市">${bean.toCityName}</p:cell>
            
            <p:pro field="toAddress" innerString="size=120" cell="0"/>
            
            <p:pro field="to" />
            
            <p:pro field="toMobile" />

			<p:pro field="receiver" />
			
			<p:pro field="receiverDate" />
			
			<p:pro field="goods" innerString="oncheck='isMathNumber'" cell="0"/>					
            
			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />
			
		</p:table>
	</p:subBody>
	
	<p:title>
        <td class="caption">
         <strong>入库清单附件</strong>
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
                <p:cell title="导入模板" end="true">
				<a target="_blank"
					href="../admin/down.do?method=downTemplateFileByName&fileName=outBackTemplate.xls">下载入库清单模板</a>
				</p:cell>
			
                <p:cell title="附件" width="8" end="true">
                	<input type="file" name="atts" size="70" >
	            </p:cell>
                </table>
                </td>
            </tr>
        </table>
        </td>
    </tr>

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


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
<script language="javascript">

var cmap = window.top.topFrame.cmap;

var pList = window.top.topFrame.pList;

function addBean()
{
	submit('确定增加退货快递信息?', null, null);
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
    
    changes($O('fromCity'));

    setOption($O('toProvince'), "", "--");
    for (var i = 0; i < pList.length; i++)
    {
        setOption($O('toProvince'), pList[i].id, pList[i].name);
    }

	$O('toProvince').value = '320000';
    
    changes2($O('toCity'));

    setSelect($O('toCity'), '320100');
    
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
<form name="formEntry" action="../sail/outback.do" method="post">
<input type="hidden" name="method" value="addOutBack">
<input type="hidden" name="receiverId" value="">
<input type="hidden" name="mode" value="0">

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
		<p:class value="com.china.center.oa.sail.bean.OutBackBean" />

		<p:table cells="2">
		    
		    <p:pro field="expressCompany">
               <option value="">--</option>
               <c:forEach items="${transportList}" var="item">
                   <option value="${item.id}">${item.name}</option>
               </c:forEach>
            </p:pro>
            
            <p:pro field="transportNo" />

			<p:pro field="fromProvince"
				innerString="quick=true onchange=changes(this)"/>
			<p:pro field="fromCity" innerString="quick=true" />
			
			<p:pro field="fromAddress" innerString="size=120" cell="0"/>
			
			<p:pro field="fromer" />
			
			<p:pro field="fromMobile" />
            
            <p:pro field="toProvince"
				innerString="quick=true onchange=changes2(this)" />
			<p:pro field="toCity" innerString="quick=true"/>
            
            <p:pro field="toAddress" innerString="size=120" cell="0" value="秦淮区1865创意园C2栋"/>
            
            <p:pro field="to" />
            
            <p:pro field="toMobile" />
            
			<p:cell title="库管收货人">
			   <input type="text" name="receiver" maxlength="14" value=""  size="20" 
                            onclick="selectStaffer()" style="cursor: pointer;"
                            readonly="readonly" oncheck="notNone;">
                            <font color="#FF0000">*</font>
			</p:cell>
			
			<p:pro field="receiverDate" />
			
			<p:pro field="goods" innerString="oncheck='isMathNumber'" cell="0"/>					
            
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


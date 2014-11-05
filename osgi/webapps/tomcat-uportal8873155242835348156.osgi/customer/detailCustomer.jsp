<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="客户明细" guid="true" dialog="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">

var cmap = window.top.topFrame.cmap;

var pList = window.top.topFrame.pList;

var updateCode = '${updateCode}';

function load()
{
    setOption($O('provinceId'), "", "--");
    for (var i = 0; i < pList.length; i++)
    {
        setOption($O('provinceId'), pList[i].id, pList[i].name);
    }
    
    loadForm();
    
    //changes($O('cityId'));
    
    //loadForm();
    
    
    
    //testclip();
}

function changes(obj)
{
    removeAllItem($O('cityId'));
    setOption($O('cityId'), "", "--");
    if ($$('provinceId') == "")
    {
        return;
    }
    
    var cityList = cmap[$$('provinceId')];
    for (var i = 0; i < cityList.length; i++)
    {
        setOption($O('cityId'), cityList[i].id, cityList[i].name);
    }
}


function subCode()
{
    submit('确定提交客户编码?');
}

var boocopy = true;

function copy()
{
   return false;
}

function testclip()
{
  if (!boocopy)
  {
      return;
  }
  
  try   
  {   
      if (clipboardData)
      {
          clipboardData.setData("Text","");  
      }
  }  
  catch(e)
  {
  }  
   
  setTimeout("testclip()", 50)   
}

function checkBean()
{
    $.messager.prompt('总部核对', '请核对说明', '', function(msg){
                if (msg)
                {
                    $l('../finance/finance.do?method=checks2&id=${bean.id}&reason=' + ajaxPararmter(msg) + '&type=${ltype}');
                }
               
            }, 2);
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../customer/customer.do"><input
	type="hidden" name="method" value="assignApplyCustomerCode">
<input type="hidden" name="id" value="${bean.id}">
<input type="hidden" name="createTime" value="${bean.createTime}">
	 <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">客户管理</span> &gt;&gt; 客户明细</td>
	<td width="85"></td>
</p:navigation> <br>
<p:body width="100%">

	<p:title>
		<td class="caption"><strong>客户基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
	
		<p:class opr="2"/>

		<p:table cells="2">
			<p:pro field="name" cell="2" innerString="size=60"/>
			
			<p:pro field="code" cell="2" innerString="size=60"/>

            <p:pro field="connector" />

            <p:pro field="sex">
                <option value="0">男</option>
                <option value="1">女</option>
            </p:pro>


            <p:pro field="provinceId"
                innerString="quick=true onchange=changes(this)" />
                
            <p:cell title="地市">${bean.cityName}</p:cell>
            
            <p:cell title="县区">${bean.areaName}</p:cell>
            
            <!-- <p:cell title="分公司">${bean.locationName}</p:cell> -->
                
            <p:pro field="selltype" innerString="quick=true">
                <p:option type="101"></p:option>
            </p:pro>
            
            <p:pro field="selltype1" innerString="quick=true">
                <p:option type="124" empty="true"></p:option>
            </p:pro>            
            
            <p:pro field="protype" innerString="quick=true">
                <p:option type="102"></p:option>
            </p:pro>

            <p:pro field="newtype" innerString="quick=true">
                <p:option type="103"></p:option>
            </p:pro>
            <p:pro field="qqtype" innerString="quick=true">
                <p:option type="104"></p:option>
            </p:pro>

            <p:pro field="rtype" innerString="quick=true">
                <p:option type="105"></p:option>
            </p:pro>
            <p:pro field="formtype" innerString="quick=true">
                <p:option type="106"></p:option>
            </p:pro>

            <p:pro field="mtype" innerString="quick=true">
                <p:option type="107"></p:option>
            </p:pro>
            <p:pro field="htype" innerString="quick=true">
                <p:option type="108"></p:option>
            </p:pro>
            
            <p:pro field="blog" innerString="quick=true">
                <p:option type="blog"></p:option>
            </p:pro>
            <p:pro field="card" cell="2">
                <p:option type="card"></p:option>
            </p:pro>
            
            <p:pro field="hlocal" cell="2">
                <option value="">--</option>
                <c:forEach items="${sybList}" var="item">
                <option value="${item.id}">${item.name}</option>
                </c:forEach>
            </p:pro>
            
            <p:pro field="beginConnectTime" />
            <p:pro field="post" />


            <p:pro field="company" cell="2" innerString="size=60" />
            <p:pro field="cdepartement" cell="2" innerString="size=60" />
            <p:pro field="address" cell="2" innerString="size=60" />

            <p:pro field="handphone" cell="2" innerString="size=60"/>

            <p:pro field="tel" cell="2" innerString="size=60"/>
            <p:pro field="mail" cell="2" innerString="size=60"/>
            
            <p:pro field="qq" />
            <p:pro field="msn" />
            
            <p:pro field="web" cell="2" innerString="size=60" />

            <p:pro field="fax" />
            <p:pro field="postcode" />

            <p:pro field="bank" />
            <p:pro field="accounts" />

			<p:pro field="bankClass" innerString="quick=true">
				<p:option type="bankClass" empty="true"></p:option>
			</p:pro>
			<p:pro field="birthday"/>

            <p:pro field="dutycode" />
            <p:pro field="flowcom" />

            <p:pro field="description" cell="0" innerString="rows=4 cols=60" />
            
            <p:pro field="assignPer1" />
            <p:pro field="assignPer2" />
            <p:pro field="assignPer3" />
            <p:pro field="assignPer4" />
            
            <c:if test="${apply == true}">
	            <p:cell title="审批意见" end="true">
	               <c:out value="${bean.reson}"/>
	            </p:cell>
            </c:if>
            
            <p:cell title="所属职员" end="true">
                   <c:out value="${vs.stafferName}"/>
            </p:cell>
            
		</p:table>
	</p:subBody>

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
        <div align="right">
        <c:if test="${check == 1}">
        <input
            type="button" name="ba" class="button_class"
            onclick="checkBean()"
            value="&nbsp;&nbsp;总部核对&nbsp;&nbsp;">&nbsp;&nbsp;
        </c:if>
        
        <input type="button" class="button_class"
            style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
            onclick="javascript:history.go(-1)"></div>
    </p:button>
    
</p:body></form>
</body>
</html>


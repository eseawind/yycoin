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

function passBean()
{
	$O("operation").value = "0";

	submit('确定通过客户变更信息?');
}

function rejectBean()
{
	$O("operation").value = "1";

	submit('确定驳回客户变更信息?');
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
	type="hidden" name="method" value="processApply">
<input type="hidden" name="id" value="${bean.id}">
<input type="hidden" name="createTime" value="${bean.createTime}">
<input type="hidden" name="operation" value="0">
<input type="hidden" name="type" value="1">
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
			<p:cell title="客户名称" end="true">
			<c:if test="${bean.name == applyVO.name}">
				${bean.name}
			</c:if>
			<c:if test="${bean.name != applyVO.name}">
				<font color="red">${applyVO.name}</font> 
			</c:if>
			</p:cell>
			
			<p:pro field="code" cell="2" innerString="size=60"/>

            <p:pro field="connector" />
            <p:cell title="联系人">
			<c:if test="${bean.connector == applyVO.connector}">
				${bean.connector}
			</c:if>
			<c:if test="${bean.connector != applyVO.connector}">
				<font color="red">${applyVO.connector}</font> 
			</c:if>
			</p:cell>

            <p:pro field="sex">
                <option value="0">男</option>
                <option value="1">女</option>
            </p:pro>
            <p:cell title="性别 ">
			<c:if test="${bean.sex == applyVO.sex}">
				<c:if test="${bean.sex == '0'}">男</c:if>
				<c:if test="${bean.sex == '1'}">女</c:if>
			</c:if>
			<c:if test="${bean.sex != applyVO.sex}">
				<font color="red">
				<c:if test="${applyVO.sex == '0'}">男</c:if>
				<c:if test="${applyVO.sex == '1'}">女</c:if>
				</font> 
			</c:if>
			</p:cell>

            <p:pro field="provinceId"
                innerString="quick=true onchange=changes(this)" />
            <p:cell title="省">
			<c:if test="${bean.provinceId == applyVO.provinceId}">
				${bean.provinceName}
			</c:if>
			<c:if test="${bean.provinceId != applyVO.provinceId}">
				<font color="red">${applyVO.provinceName}</font> 
			</c:if>
			</p:cell>
                
            <p:cell title="地市">${bean.cityName}</p:cell>
            <p:cell title="地市">
			<c:if test="${bean.cityId == applyVO.cityId}">
				${bean.cityName}
			</c:if>
			<c:if test="${bean.cityId != applyVO.cityId}">
				<font color="red">${applyVO.cityName}</font> 
			</c:if>
			</p:cell>
            
            <p:cell title="县区">${bean.areaName}</p:cell>
            <p:cell title="县区">
			<c:if test="${bean.areaId == applyVO.areaId}">
				${bean.areaName}
			</c:if>
			<c:if test="${bean.areaId != applyVO.areaId}">
				<font color="red">${applyVO.areaName}</font> 
			</c:if>
			</p:cell>            
            
            <p:pro field="selltype" innerString="quick=true">
                <p:option type="101"></p:option>
            </p:pro>
			<p:cell title="客户类型">
			<c:if test="${bean.selltype == applyVO.selltype}">
				${my:get('101', bean.selltype)}
			</c:if>
			<c:if test="${bean.selltype != applyVO.selltype}">
				<font color="red">${my:get('101', applyVO.selltype)}</font>
			</c:if>
			</p:cell> 
            
            <p:pro field="selltype1" innerString="quick=true">
                <p:option type="124" empty="true"></p:option>
            </p:pro>  
			<p:cell title="客户类型1">
			<c:if test="${bean.selltype1 == applyVO.selltype1}">
				${my:get('124', bean.selltype1)}
			</c:if>
			<c:if test="${bean.selltype1 != applyVO.selltype1}">
				<font color="red">${my:get('124', applyVO.selltype1)}</font> 
			</c:if>
			</p:cell> 
            
            <p:pro field="protype" innerString="quick=true">
                <p:option type="102"></p:option>
            </p:pro>
			<p:cell title="客户分类1">
			<c:if test="${bean.protype == applyVO.protype}">
				${my:get('102', bean.protype)}
			</c:if>
			<c:if test="${bean.protype != applyVO.protype}">
				<font color="red">${my:get('102', applyVO.protype)}</font> 
			</c:if>
			</p:cell>            

            <p:pro field="newtype" innerString="quick=true">
                <p:option type="103"></p:option>
            </p:pro>
			<p:cell title="客户分类2">
			<c:if test="${bean.newtype == applyVO.newtype}">
				${my:get('103', bean.newtype)}
			</c:if>
			<c:if test="${bean.newtype != applyVO.newtype}">
				<font color="red">${my:get('103', applyVO.newtype)}</font> 
			</c:if>
			</p:cell>             
            <p:pro field="qqtype" innerString="quick=true">
                <p:option type="104"></p:option>
            </p:pro>
			<p:cell title="客户等级">
			<c:if test="${bean.qqtype == applyVO.qqtype}">
				${my:get('104', bean.qqtype)}
			</c:if>
			<c:if test="${bean.qqtype != applyVO.qqtype}">
				<font color="red">${my:get('104', applyVO.qqtype)}</font> 
			</c:if>
			</p:cell>
            <p:pro field="rtype" innerString="quick=true">
                <p:option type="105"></p:option>
            </p:pro>
			<p:cell title="开发进程">
			<c:if test="${bean.rtype == applyVO.rtype}">
				${my:get('105', bean.rtype)}
			</c:if>
			<c:if test="${bean.rtype != applyVO.rtype}">
				<font color="red">${my:get('105', applyVO.rtype)}</font> 
			</c:if>
			</p:cell>           
            <p:pro field="formtype" innerString="quick=true">
                <p:option type="106"></p:option>
            </p:pro>
			<p:cell title="客户来源">
			<c:if test="${bean.formtype == applyVO.formtype}">
				${my:get('106', bean.formtype)}
			</c:if>
			<c:if test="${bean.formtype != applyVO.formtype}">
				<font color="red">${my:get('106', applyVO.formtype)}</font> 
			</c:if>
			</p:cell>
            <p:pro field="mtype" innerString="quick=true">
                <p:option type="107"></p:option>
            </p:pro>
			<p:cell title="客户性质">
			<c:if test="${bean.mtype == applyVO.mtype}">
				${my:get('107', bean.mtype)}
			</c:if>
			<c:if test="${bean.mtype != applyVO.mtype}">
				<font color="red">${my:get('107', applyVO.mtype)}</font> 
			</c:if>
			</p:cell>            
            <p:pro field="htype" innerString="quick=true">
                <p:option type="108"></p:option>
            </p:pro>
			<p:cell title="行业属性">
			<c:if test="${bean.htype == applyVO.htype}">
				${my:get('108', bean.htype)}
			</c:if>
			<c:if test="${bean.htype != applyVO.htype}">
				<font color="red">${my:get('108', applyVO.htype)}</font> 
			</c:if>
			</p:cell>            
            <p:pro field="blog" innerString="quick=true">
                <p:option type="blog"></p:option>
            </p:pro>
			<p:cell title="历史成交">
			<c:if test="${bean.blog == applyVO.blog}">
				${my:get('blog', bean.blog)}
			</c:if>
			<c:if test="${bean.blog != applyVO.blog}">
				<font color="red">${my:get('blog', applyVO.blog)}</font> 
			</c:if>
			</p:cell>             
            <p:pro field="card">
                <p:option type="card"></p:option>
            </p:pro>
			<p:cell title="有无名片">
			<c:if test="${bean.card == applyVO.card}">
				${my:get('card', bean.card)}
			</c:if>
			<c:if test="${bean.card != applyVO.card}">
				<font color="red">${my:get('card', applyVO.card)}</font> 
			</c:if>
			</p:cell>            
            <p:pro field="hlocal" cell="2">
                <option value="">--</option>
                <c:forEach items="${sybList}" var="item">
                <option value="${item.id}">${item.name}</option>
                </c:forEach>
            </p:pro>
            
            <p:pro field="beginConnectTime" />
			<p:cell title="联系时间">
			<c:if test="${bean.beginConnectTime == applyVO.beginConnectTime}">
				${bean.beginConnectTime}
			</c:if>
			<c:if test="${bean.beginConnectTime != applyVO.beginConnectTime}">
				<font color="red">${applyVO.beginConnectTime}</font> 
			</c:if>
			</p:cell>            
            <p:pro field="post" />
			<p:cell title="职务">
			<c:if test="${bean.post == applyVO.post}">
				${bean.post}
			</c:if>
			<c:if test="${bean.post != applyVO.post}">
				<font color="red">${applyVO.post}</font> 
			</c:if>
			</p:cell> 

            <p:pro field="company" innerString="size=60" />
            <p:cell title="公司/机构">
			<c:if test="${bean.company == applyVO.company}">
				${bean.company}
			</c:if>
			<c:if test="${bean.company != applyVO.company}">
				<font color="red">${applyVO.company}</font> 
			</c:if>
			</p:cell> 
			
            <p:pro field="cdepartement" innerString="size=60" />
            <p:cell title="联系人部门">
			<c:if test="${bean.cdepartement == applyVO.cdepartement}">
				${bean.cdepartement}
			</c:if>
			<c:if test="${bean.cdepartement != applyVO.cdepartement}">
				<font color="red">${applyVO.cdepartement}</font> 
			</c:if>
			</p:cell>            
            <p:pro field="address" innerString="size=60" />
            <p:cell title="地址">
			<c:if test="${bean.address == applyVO.address}">
				${bean.address}
			</c:if>
			<c:if test="${bean.address != applyVO.address}">
				<font color="red">${applyVO.address}</font> 
			</c:if>
			</p:cell>            

            <p:pro field="handphone" innerString="size=60"/>
            <p:cell title="移动电话">
			<c:if test="${bean.handphone == applyVO.handphone}">
				${bean.handphone}
			</c:if>
			<c:if test="${bean.handphone != applyVO.handphone}">
				<font color="red">${applyVO.handphone}</font> 
			</c:if>
			</p:cell>
			
            <p:pro field="tel" innerString="size=60"/>
            <p:cell title="固话">
			<c:if test="${bean.tel == applyVO.tel}">
				${bean.tel}
			</c:if>
			<c:if test="${bean.tel != applyVO.tel}">
				<font color="red">${applyVO.tel}</font> 
			</c:if>
			</p:cell>            
            <p:pro field="mail" innerString="size=60"/>
            <p:cell title="电子邮箱">
			<c:if test="${bean.mail == applyVO.mail}">
				${bean.mail}
			</c:if>
			<c:if test="${bean.mail != applyVO.mail}">
				<font color="red">${applyVO.mail}</font> 
			</c:if>
			</p:cell>             
            <p:pro field="qq" />
            <p:cell title="QQ号码">
			<c:if test="${bean.qq == applyVO.qq}">
				${bean.qq}
			</c:if>
			<c:if test="${bean.qq != applyVO.qq}">
				<font color="red">${applyVO.qq}</font> 
			</c:if>
			</p:cell>             
            <p:pro field="msn" />
            <p:cell title="MSN号码">
			<c:if test="${bean.msn == applyVO.msn}">
				${bean.msn}
			</c:if>
			<c:if test="${bean.msn != applyVO.msn}">
				<font color="red">${applyVO.msn}</font> 
			</c:if>
			</p:cell>             
            
            <p:pro field="web" innerString="size=60" />
            <p:cell title="网址">
			<c:if test="${bean.web == applyVO.web}">
				${bean.web}
			</c:if>
			<c:if test="${bean.web != applyVO.web}">
				<font color="red">${applyVO.web}</font> 
			</c:if>
			</p:cell>
            <p:pro field="fax" />
            <p:cell title="传真">
			<c:if test="${bean.fax == applyVO.fax}">
				${bean.fax}
			</c:if>
			<c:if test="${bean.fax != applyVO.fax}">
				<font color="red">${applyVO.fax}</font> 
			</c:if>
			</p:cell>            
            <p:pro field="postcode" />
            <p:cell title="邮编">
			<c:if test="${bean.postcode == applyVO.postcode}">
				${bean.postcode}
			</c:if>
			<c:if test="${bean.postcode != applyVO.postcode}">
				<font color="red">${applyVO.postcode}</font> 
			</c:if>
			</p:cell>
            <p:pro field="bank" />
            <p:cell title="银行">
			<c:if test="${bean.bank == applyVO.bank}">
				${bean.bank}
			</c:if>
			<c:if test="${bean.bank != applyVO.bank}">
				<font color="red">${applyVO.bank}</font> 
			</c:if>
			</p:cell>            
            
            <p:pro field="accounts" />
            <p:cell title="银行帐号">
			<c:if test="${bean.accounts == applyVO.accounts}">
				${bean.accounts}
			</c:if>
			<c:if test="${bean.accounts != applyVO.accounts}">
				<font color="red">${applyVO.accounts}</font> 
			</c:if>
			</p:cell>              

			<p:pro field="bankClass" innerString="quick=true">
				<p:option type="bankClass" empty="true"></p:option>
			</p:pro>
            <p:cell title="银行层级">
			<c:if test="${bean.bankClass == applyVO.bankClass}">
				${my:get('bankClass', bean.bankClass)}
			</c:if>
			<c:if test="${bean.bankClass != applyVO.bankClass}">
				<font color="red">${my:get('bankClass', applyVO.bankClass)}</font> 
			</c:if>
			</p:cell> 			
			<p:pro field="birthday"/>
            <p:cell title="生日">
			<c:if test="${bean.birthday == applyVO.birthday}">
				${bean.birthday}
			</c:if>
			<c:if test="${bean.birthday != applyVO.birthday}">
				<font color="red">${applyVO.birthday}</font> 
			</c:if>
			</p:cell>
            <p:pro field="dutycode" />
            <p:cell title="税号">
			<c:if test="${bean.dutycode == applyVO.dutycode}">
				${bean.dutycode}
			</c:if>
			<c:if test="${bean.dutycode != applyVO.dutycode}">
				<font color="red">${applyVO.dutycode}</font> 
			</c:if>
			</p:cell>            
            <p:pro field="flowcom" />
            <p:cell title="指定物流">
			<c:if test="${bean.flowcom == applyVO.flowcom}">
				${bean.flowcom}
			</c:if>
			<c:if test="${bean.flowcom != applyVO.flowcom}">
				<font color="red">${applyVO.flowcom}</font> 
			</c:if>
			</p:cell>
            
		</p:table>
	</p:subBody>

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
        <div align="right">
        <input
            type="button" name="ba" class="button_class"
            onclick="passBean()"
            value="&nbsp;&nbsp;通过&nbsp;&nbsp;">&nbsp;&nbsp;
        
        <input
            type="button" name="ba" class="button_class"
            onclick="rejectBean()"
            value="&nbsp;&nbsp;驳回&nbsp;&nbsp;">&nbsp;&nbsp;
        
        <input type="button" class="button_class"
            style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
            onclick="javascript:history.go(-1)"></div>
    </p:button>
    
</p:body></form>
</body>
</html>


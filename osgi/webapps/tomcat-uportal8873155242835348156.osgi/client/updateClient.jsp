<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<HTML>
<head>
<p:link title="修改客户" />
<link href="../js/plugin/dialog/css/dialog.css" type="text/css"	rel="stylesheet" />
<LINK href="../css/tabs/jquery.tabs-ie.css" type=text/css rel=stylesheet>
<LINK href="../css/tabs/jquery.tabs.css" type=text/css rel=stylesheet>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/buffalo.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<SCRIPT src="../js/jquery/jquery.tabs.js"></SCRIPT>
<script language="JavaScript" src="../client_js/client.js"></script>
<script language="javascript">
var END_POINT = "${pageContext.request.contextPath}/bfapp";

var buffalo = new Buffalo(END_POINT);

function addBean()
{
	if ($$('stype') == '1')
	{
		if (!check1())
		{
			return false;
		}
	}

	if ($$('stype') == '2')
	{
		if (!check2())
		{
			return false;
		}
	}

	if ($$('stype') == '3')
	{
		if (!check3())
		{
			return false;
		}
	}
	
	submit('确定申请修改客户?');
}

var cmap = window.top.topFrame.cmap;

var pList = window.top.topFrame.pList;

var areaJson = JSON.parse('${areaStrJSON}');

function load()
{
    $('#container-1').tabs();
    
    //显示container-1
    $('#container-1').css({display:"block"});

    initDialog();

	$O('name').readOnly = 'readonly';
	
    
    loadForm();

    setOption($O('provinceId'), "", "--");
    for (var i = 0; i < pList.length; i++)
    {
        setOption($O('provinceId'), pList[i].id, pList[i].name);
    }
    
    changes($O('cityId'));

    loadForm();

    changes($O('cityId'));

    loadForm();

    setOption($O('provinceId2'), "", "--");
    for (var i = 0; i < pList.length; i++)
    {
        setOption($O('provinceId2'), pList[i].id, pList[i].name);
    }
    
    changes2($O('cityId2'));
    
    loadForm();

    changeArea();

    changeArea2();
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
    
    removeAllItem($O('areaId'));
    
    setOption($O('areaId'), "", "--");
}

function changeArea()
{
    var id = $$('cityId');

    if (id == "")
    {
        return;
    }

    removeAllItem($O('areaId'));
    setOption($O('areaId'), "", "--");
    
    var areaList = areaJson[$$('cityId')];

    removeAllItem($O('areaId'));
    
    setOption($O('areaId'), "", "--");
    
    for (var i = 0; i < areaList.length; i++)
    {
        setOption($O('areaId'), areaList[i].id,  areaList[i].name);
    }
}

function selectRelateCustomer(stype)
{
	window.common.modal("../client/client.do?method=rptQuerySelfRelateClient&stafferId=${user.stafferId}&load=1&stype="+ stype);
}

function selectFormerCust(){
	var stype = $$('stype');
	
	window.common.modal("../client/client.do?method=rptQueryNotUseClient&load=1&selectMode=0&first=1&stype="+ stype);
}

function clears()
{
	$O('formerCust').value = '';
	$O('formerCustName').value = '';
}

function selectBankRelation()
{
	window.common.modal("../admin/pop.do?method=rptQueryBankRelation&load=1&selectMode=1");
}

</SCRIPT>
</HEAD>
<BODY class="body_class" onload=load()>
<form name="addApply" action="../client/client.do" method="post">
	<input type="hidden" name="method" value="addOrUpdateApplyClient">
	<input type="hidden" name="stype" value="${bean.type}">
	<input type="hidden" name="id" value="${bean.id}">
	<input type="hidden" name="code" value="${bean.code}">
	<input type="hidden" name="addOrUpdate" value="1">
	<input type="hidden" name="refDepartCustId" value="${bean.refDepartCustId}">
	<input type="hidden" name="refCorpCustId" value="${bean.refCorpCustId}">
	<input type="hidden" name="formerCust" value="${bean.formerCust}">
	<input type="hidden" name="bankId" value="${bean.bankId}">
		
	<p:navigation height="22">
		<td width="550" class="navigation"><span
			style="cursor: pointer;" onclick="javascript:history.go(-1)">客户管理</span>	&gt;&gt; 申请修改客户</td>
		<td width="85"></td>
	</p:navigation>

	<p:body width="100%">

	<p:title>
		<c:if test="${bean.type==1}">
		<td class="caption"><strong>【个人】客户信息：</strong></td>
		</c:if>
		<c:if test="${bean.type==2}">
		<td class="caption"><strong>【部门】客户信息：</strong></td>
		</c:if>
		<c:if test="${bean.type==3}">
		<td class="caption"><strong>【企业】客户信息：</strong></td>
		</c:if>
	</p:title>

	<p:line flag="0" />
		
	<p:subBody width="98%">
			
	<div id="container-1" style="display: none;">
		<ul>
			<li><a href="#fragment-basic-info"><span>基本信息</span></a></li>
			<li><a href="#fragment-linkman-info"><span>联系人信息</span></a></li>
			<li><a href="#fragment-busi-info"><span>财务信息</span></a></li>
			<li><a href="#fragment-addr-info"><span>地址信息</span></a></li>
		</ul>
	
		<div id="fragment-basic-info">
			<c:if test="${bean.type==1}">
			<%@include file="updateIndividualClient.jsp"%>
			</c:if>
			<c:if test="${bean.type==2}">
			<%@include file="updateDepartClient.jsp"%>
			</c:if>
			<c:if test="${bean.type==3}">
			<%@include file="updateCorporationClient.jsp"%>
			</c:if>
		</div>
		
		<div id="fragment-linkman-info">
			<%@include file="clientContact.jsp"%>
		</div>
		
		<div id="fragment-busi-info">
			<%@include file="clientBusiness.jsp"%>
		</div>
		
		<div id="fragment-addr-info">
			<%@include file="clientAddress.jsp"%>
		</div>
	
	</div>
	
	</p:subBody>
	
	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
			<input type="button" class="button_class" style="cursor: pointer"
				value="&nbsp;&nbsp;确 定&nbsp;&nbsp;" onclick="addBean()">
		</div>
	</p:button>

	<p:message />
	</p:body>
</form>
<div id="dlg_linkman" title="新增联系人信息" style="width: 700px;">
	<table id="tbl_linkman_1" class='table0' style="width: 100%;">
		<tbody>
			<tr class="content1">
				<td width="15%">姓名</td>
				<td width="35%"><input id="name1" oncheck="notNone"/> </td>
				<td width="15%">性别</td>
				<td width="35%"><select id="sex1"><option value="0">男</option>
						<option value="1">女</option></select> </td>
			</tr>
			<tr class="content1">
				<td width="15%">年龄段</td>
				<td width="35%"><select id="personal1"><p:option type="personal" /> </select> </td>
				<td width="15%"></td>
				<td width="35%"></td>
			</tr>
			<tr class="content1">
				<td width="15%">生日</td>
				<td width="35%">
				<input type=text name = 'birthday1'  id = 'birthday1'  oncheck = "" onblur="blu(this)" value = ''  readonly=readonly class='input_class' size = '20' ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "birthday1");' height='20px' width='20px'/>
				</td>
				<td width="15%">手机</td>
				<td width="35%"><input id="handphone1" oncheck='notNone;isMathNumber' size=11 maxlength='11'/> </td>
			</tr>
			<tr class="content1">
				<td width="15%">固话</td>
				<td width="35%"><input id="tel1" oncheck='isMathNumber' size=13 maxlength='13'/> </td>
				<td>邮箱</td>
				<td><input id="email1"/> </td>
			</tr>
			<tr class="content1">
				<td width="15%">QQ号码</td>
				<td width="35%"><input id="qq1" /></td>
				<td width="15%">微信</td>
				<td width="35%"><input id="weixin1" /></td>
			</tr>
			<tr class="content1">
				<td width="15%">微博</td>
				<td width="35%"><input id="weibo1" /></td>
				<td width="15%">职务</td>
				<td width="35%">
					<select id="duty1" >
						<p:option type="300" empty="true"></p:option>
					</select>  
				</td>
			</tr>
			<tr class="content1">
				<td width="15%">汇报对象</td>
				<td width="35%"><input id="reportTo1" /></td>
				<td width="15%">爱好</td>
				<td width="35%"><input id="interest1" /></td>
			</tr>
			<tr class="content1">
				<td width="15%">关系程度</td>
				<td width="35%"><select id="relationship1">
						<p:option type="relationShip" empty="true"></p:option>
						</select> </td>
				<td width="15%">角色</td>
				<td width="35%">
					<select id="role1" >
						<p:option type="301" empty="true"></p:option>
					</select> 
				</td>
			</tr>
			<tr class="content2">
				<td width="15%">备注</td>
				<td width="85%" colspan="3"><input id="description1" size="70" /></td>
			</tr>
		</tbody>
	</table>
</div>

<div id="dlg_busi" title="新增财务信息" style="width: 700px;">
		<table id="tbl_busi_1" class='table0' width="98%">
			<tbody>
				<tr class="content1">
					<td width="15%">客户账号性质</td>
					<td width="35%"><select id="custAccountType"><option
								value="0">对公</option>
							<option value="1">卡钞</option></select>&nbsp;<font color="#FF0000">*</font></td>
					<td width="15%">客户账号开户行</td>
					<td width="35%"><input id="custAccountBank" style="width:90%"/>&nbsp;<font color="#FF0000">*</font></td>
				</tr>
				<tr class="content1">
					<td width="15%">客户账号开户名</td>
					<td width="35%"><input id="custAccountName" style="width:90%"/>&nbsp;<font color="#FF0000">*</font></td>
					<td>客户账号</td>
					<td><input id="custAccount" style="width:90%"/>&nbsp;<font color="#FF0000">*</font></td>
				</tr>
				<tr class="content1">
					<td width="15%">我司账号性质</td>
					<td width="35%"><select id="myAccountType"><option
								value="0">对公</option>
							<option value="1">卡钞</option></select>&nbsp;<font color="#FF0000">*</font></td>
					<td width="15%">我司账号开户行</td>
					<td width="35%"><input id="myAccountBank" style="width:90%"/>&nbsp;<font color="#FF0000">*</font></td>
				</tr>
				<tr class="content1">
					<td width="15%">我司账号开户名</td>
					<td width="35%"><input id="myAccountName" style="width:90%"/>&nbsp;<font color="#FF0000">*</font></td>
					<td width="15%">我司账号</td>
					<td width="35%"><input id="myAccount" style="width:90%"/>&nbsp;<font color="#FF0000">*</font></td>
				</tr>
			</tbody>
		</table>

	 </div>
	 
	<div id="dlg_addr" title="新增地址信息" style="width: 700px;">
		<table id="tbl_addr_1" class='table0' width="98%">
			<tbody>
				<tr class="content1">
					<td width="15%">送货地址</td>
					<td width="85%" colspan="3"><select id="provinceId2"
						onchange="changes2(this)" quick=true class="select_class" oncheck="notNone;"></select>&nbsp;
						<select id="cityId2" onchange="changeArea2()" quick=true class="select_class" oncheck="notNone;"></select>&nbsp; 
						<select id="areaId2" quick=true class="select_class" oncheck="notNone;"></select>&nbsp;<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class="content1">
					<td width="15%">详细地址</td>
					<td width="85%" colspan="3"><input id="address2" maxlength="100" size="80"/>&nbsp;<font color="#FF0000">*</font></td>
				</tr>
				<tr class="content1">
					<td width="15%">收货人</td>
					<td width="35%"><input id="contact" style="width:90%"/>&nbsp;<font color="#FF0000">*</font></td>
				</tr>
				<tr class="content1">
					<td width="15%">收货人电话</td>
					<td width="35%"><input id="telephone" size=11 maxlength='11'/>&nbsp;<font color="#FF0000">*</font></td>
					<td width="15%">地址性质</td>
					<td width="35%">
					<select id="atype" style="width:90%">
						<p:option type="303" empty="true"></p:option>
					</select> 
					&nbsp;<font color="#FF0000">*</font></td>
				</tr>
			</tbody>
		</table>

	 </div>	 
</BODY>
</HTML>

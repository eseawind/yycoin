
/**
 * Dialog初始化
 */
function initDialog() {
	$('#dlg_linkman').dialog( {
		modal : true,
		closed : true,
		buttons : {
			'确 定' : function() {
				addLinkmanToTbl();
			},
			'取 消' : function() {
				$('#dlg_linkman').dialog( {
					closed : true
				});
			}
		}
	});
	
	$('#dlg_busi').dialog( {
		modal : true,
		closed : true,
		buttons : {
			'确 定' : function() {
				addBusiTbl();
			},
			'取 消' : function() {
				$('#dlg_busi').dialog( {
					closed : true
				});
			}
		}
	});
	
	$('#dlg_addr').dialog( {
		modal : true,
		closed : true,
		buttons : {
			'确 定' : function() {
				addAddrTbl();
			},
			'取 消' : function() {
				$('#dlg_addr').dialog( {
					closed : true
				});
			}
		}
	});
}

var isEdit = false;
var trgObj;

function addTr(table, tr)
{
	var trObj;
	
    for (var i = 0; i < 1; i++)
    {
    	trObj = addTrInner(table, tr);
    }
    
    return trObj;
}

function removeTr(obj)
{
    obj.parentNode.parentNode.removeNode(true);
}

function calDateInner(obj, name)
{
	var tr = getTrObject(obj);
	
	var el = getInputInTr(tr, name);
	
	return calDate(el);
}

/**
 * ******************************配送地址信息******************************
 */
var addr_arr = new Array("provinceId2","cityId2","areaId2","address2", "contact", "telephone","atype");

/**
 * 新增一行数据
 */
function opAddrDlg() 
{
	isEdit = false;
	
	resetAddrFrm();

	$('#dlg_addr').dialog( {
		closed : false
	});
}

/**
 * 重置表单
 */
function resetAddrFrm() {

	for ( var i = 0; i < addr_arr.length; i++) {
		var index_val = addr_arr[i];

		$("#" + index_val).val("");
	}
	
	$("#provinceId2").val(""); 
	$("#cityId2").val("");
}

/**
 * 动态往表格添加数据
 */
function addAddrTbl() {
	var trObj;
	
	if (isEdit)
	{
		trObj = trgObj;
	}else
	{
		trObj = addTr("tables2", "trCopy2");
	}

	var inputs = trObj.getElementsByTagName('input');
	
	var fullAddress = $("#provinceId2").find("option:selected").text() + " " 
					+ $("#cityId2").find("option:selected").text() + " " 
					+ $("#areaId2").find("option:selected").text() + " "
					+ $("#address2").val();
	
	setInputValueInTr(trObj, "p_fullAddress", fullAddress)

	for ( var i = 0; i < addr_arr.length; i++) 
	{
		var index_val = addr_arr[i];
		
		for (var m = 0; m < inputs.length; m++)
		{
			if (("p_" + index_val) == inputs[m].name)
			{
				var dval =  $("#" + index_val).val();
				
				if (dval == ''){
					alert('配送地址 - 所有值均不能为空');
					
					if (!isEdit)
						trObj.removeNode(true);
					
					return;
				}
				
				if (index_val == 'telephone'){
					if (!isNumbers(dval))
					{
						alert("配送信息 - 手机号不是数字型");
						
						if (!isEdit)
							trObj.removeNode(true);
						
						return ;
					}
					
					if (dval.length != 11)
					{
						alert("配送信息 - 手机号不是11位");
						
						if (!isEdit)
							trObj.removeNode(true);
						
						return ;
					}

					if(dval.substr(0,1) != '1')
					{
						alert("配送信息 - 手机号不是1开头的");
						
						if (!isEdit)
							trObj.removeNode(true);
						
						return;
					}
				}
				
				inputs[m].value = dval;
				
				break;
			}
		}
	}
	
	$('#dlg_addr').dialog( {
		closed : true
	});
}

function editAddr(obj) 
{
	isEdit = true;
	
	var trobj = getTrObject(obj)
	
	trgObj = trobj;
	
	for ( var i = 0; i < addr_arr.length; i++) 
	{
		var index_val = addr_arr[i];

		var trInputVal = getInputInTr(trobj, "p_" + index_val);
		
		$("#" + index_val).val(trInputVal.value);
		
		if (index_val == 'provinceId2')
		{
			changes2();
		}
		
		if (index_val == 'cityId2')
		{
			changeArea2();
		}
	}	
	
	$('#dlg_addr').dialog( {
		closed : false
	});
}

function changes2(obj) {
	removeAllItem($O('cityId2'));
	setOption($O('cityId2'), "", "--");
	if ($$('provinceId2') == "") {
		return;
	}

	var cityList = cmap[$$('provinceId2')];
	for ( var i = 0; i < cityList.length; i++) {
		setOption($O('cityId2'), cityList[i].id, cityList[i].name);
	}

	removeAllItem($O('areaId2'));

	setOption($O('areaId2'), "", "--");
}

function changeArea2() {
	var id = $$('cityId2');
	
	if (id == "") {
		return;
	}

    removeAllItem($O('areaId2'));
    setOption($O('areaId2'), "", "--");
    
    var areaList = areaJson[$$('cityId2')];

    removeAllItem($O('areaId2'));
    
    setOption($O('areaId2'), "", "--");
    
    for (var i = 0; i < areaList.length; i++)
    {
        setOption($O('areaId2'), areaList[i].id,  areaList[i].name);
    }
}

/**
 * ******************************财务往来信息******************************
 */
var busi_arr = new Array("custAccountType", "custAccountBank","custAccountName", "custAccount", 
						 "myAccountType","myAccountBank", "myAccountName", "myAccount");

/**
 * 新增一行数据
 */
function opBusiDlg() 
{
	isEdit = false;
	
	resetBusiFrm();

	$('#dlg_busi').dialog( {
		closed : false
	});
}

/**
 * 重置表单
 */
function resetBusiFrm() {

	for ( var i = 0; i < busi_arr.length; i++) {
		var index_val = busi_arr[i];

		$("#" + index_val).val("");
	}
}

/**
 * 动态往表格添加数据
 */
function addBusiTbl() {
	var trObj;
	
	if (isEdit)
	{
		trObj = trgObj;
	}else
	{
		trObj = addTr("tables3", "trCopy3");
	}

	var inputs = trObj.getElementsByTagName('input');
	
	for ( var i = 0; i < busi_arr.length; i++) 
	{
		var index_val = busi_arr[i];
		
		for (var m = 0; m < inputs.length; m++)
		{
			if (("p_" + index_val) == inputs[m].name)
			{
				var dval = $("#" + index_val).val();
				
				if (dval == ''){
					alert('所有值均不能为空');
					
					if (!isEdit)
						trObj.removeNode(true);
					
					return;
				}
				
				inputs[m].value = dval;
				
				break;
			}
		}
	}
	
	$('#dlg_busi').dialog( {
		closed : true
	});
}

function editBusi(obj) 
{
	isEdit = true;
	
	var trobj = getTrObject(obj)
	
	trgObj = trobj;
	
	for ( var i = 0; i < busi_arr.length; i++) 
	{
		var index_val = busi_arr[i];

		var trInputVal = getInputInTr(trobj, "p_" + index_val);
		
		$("#" + index_val).val(trInputVal.value);
	}	
	
	$('#dlg_busi').dialog( {
		closed : false
	});
}


/**
 * ***************************联系信息****************************************
 */
function opLinkmanDlg() 
{
	isEdit = false;
	
	resetFrm();
	$('#dlg_linkman').dialog({
		closed : false
	});
}

var contact_arr = new Array("name1", "sex1", "personal1", "birthday1",
							"handphone1", "tel1", "email1", "qq1", "weixin1", "weibo1",
							"duty1", "reportTo1", "interest1", "relationship1",
							"description1", "role1");

/**
 * 重置表单
 */
function resetFrm() {
	for ( var i = 0; i < contact_arr.length; i++) {
		var index_val = contact_arr[i];

		$("#" + index_val).val("");
	}
}

/**
 * 动态往表格添加数据
 */
function addLinkmanToTbl() {
	var trObj;
	
	if (isEdit)
	{
		trObj = trgObj;
	}else
	{
		trObj = addTr("tables", "trCopy");
	}

	var inputs = trObj.getElementsByTagName('input');

	for ( var i = 0; i < contact_arr.length; i++) {
		var index_val = contact_arr[i];

		for(var j = 0; j < inputs.length; j++)
		{
			if (("p_" + index_val) == inputs[j].name)
			{
				var dval =  $("#" + index_val).val();
				
				if (!dval || dval == null)
					dval = '';
				
				// 增加必输项检查
				if (index_val == 'name1' || index_val == 'handphone1')
				{
					if (dval == '')
					{
						alert('联系信息 - 名称与手机为必填项');
						
						if (!isEdit)
							trObj.removeNode(true);
						
						return;
					}
					
					if (index_val == 'handphone1'){
						if (!isNumbers(dval))
						{
							alert("联系信息 - 手机号不是数字型");
							
							if (!isEdit)
								trObj.removeNode(true);
							
							return ;
						}
						
						if (dval.length != 11)
						{
							alert("联系信息 - 手机号不是11位");
							
							if (!isEdit)
								trObj.removeNode(true);
							
							return ;
						}

						if(dval.substr(0,1) != '1')
						{
							alert("联系信息 - 手机号不是1开头的");
							
							if (!isEdit)
								trObj.removeNode(true);
							
							return;
						}
					}
				}
				
				if (index_val == 'tel1')
				{
					if (dval != ''){
						if (!isNumbers(dval))
						{
							alert("联系信息 - 固话不是数字型");
							
							if (!isEdit)
								trObj.removeNode(true);
							
							return ;
						}
						
						if (dval.length != 11 && dval.length != 12)
						{
							alert("联系信息 - 固话不是11或12位");
							
							if (!isEdit)
								trObj.removeNode(true);
							
							return ;
						}

						if(dval.substr(0,1) != '0')
						{
							alert("联系信息 - 固话不是0开头的");
							
							if (!isEdit)
								trObj.removeNode(true);
							
							return;
						}
					}
				}
				
				if (index_val == 'email1'){
					if (dval != ''){
						if (!checkMailValid(dval)){
							
							if (!isEdit)
								trObj.removeNode(true);
							return;
						}
					}
				}
				
				inputs[j].value = dval;
				
				break;
			}
		}
	}
	
	$('#dlg_linkman').dialog( {
		closed : true
	});
}

function editLinkman(obj) 
{
	isEdit = true;
	
	var trobj = getTrObject(obj)
	
	trgObj = trobj;
	
	for ( var i = 0; i < contact_arr.length; i++) 
	{
		var index_val = contact_arr[i];

		var trInputVal = getInputInTr(trobj, "p_" + index_val);
		
		$("#" + index_val).val(trInputVal.value);
	}	
	
	$('#dlg_linkman').dialog( {
		closed : false
	});
}

function checkMailValid(param)
{
	var re = new RegExp(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/) ;
	
    if (!re.test(param))
	{
    	alert('邮箱格式不符合规范');
    	
    	return false;
	}
    
    return true;
}

// 个人客户
function check1()
{
	var handphone = $$('handphone');
	
	var tel = $$('tel');
	
	if (handphone == '' && tel == '')
	{
		alert('基本信息 - 手机不能为空');
		
		return false;
	}
	
	if (handphone != '')
	{
		if(handphone.substr(0,1) != '1')
		{
			alert('基本信息 - 手机须是1打头的');
			
			return false;
		}
	}
	
	if (handphone.length != 11)
	{
		alert('基本信息 - 手机须是11位长');
		
		return false;
	}
	
	if (tel != '')
	{
		if(tel.substr(0,1) != '0')
		{
			alert('基本信息 - 固话须是0打头的');
			
			return false;
		}
		
		if (tel.length != 11 && tel.length != 12)
		{
			alert('基本信息 - 固话须是11或12位长');
			
			return false;
		}
	}
	
	var email = $$('email');
	
	if (email != ''){
		return checkMailValid(email);
	}
	
	return true;
}

//部门客户
function check2()
{
	return true;
}

//企业客户
function check3()
{
	return true;
}

function getRelateCustomer(oo)
{
	var stype = oo.pstype;
	
	// 部门
	if (stype == '2'){
		$O('refDepartCustId').value = oo.prefDepartCustId;
		$O('refDepartCustName').value = oo.prefDepartCustName;
		$O('refCorpCustId').value = oo.prefCorpCustId;
		$O('refCorpCustName').value = oo.prefCorpCustName;
	}
	
	if (stype == '3'){
		$O('refDepartCustId').value = '';
		$O('refDepartCustName').value = '';
		$O('refCorpCustId').value = oo.prefCorpCustId;
		$O('refCorpCustName').value = oo.prefCorpCustName;
	}
}

function getFormerCust(oos){
	for (var i = 0; i < oos.length; i++)
    {
    	var pv = $O('formerCust').value;
    	
    	if (pv && pv.indexOf(oos[i].value) != -1)
		{
    		continue;
		}
    	else
    	{
    		$O('formerCust').value =  $O('formerCust').value + ',' + oos[i].value ;
    		$O('formerCustName').value = $O('formerCustName').value + ',' + oos[i].pname ;
    	}   
    }
	
	if ($$('formerCust').substr(0,1) == ',')
	{
		$O('formerCust').value = $$('formerCust').substr(1, $$('formerCust').length - 1)
		$O('formerCustName').value = $$('formerCustName').substr(1, $$('formerCustName').length - 1)
	}

	if ($$('formerCust').substr($$('formerCust').length - 1) == ',')
	{
		$O('formerCust').value = $$('formerCust').substr(0, $$('formerCust').length - 1)
		$O('formerCustName').value = $$('formerCustName').substr(0, $$('formerCustName').length - 1)
	}
}

function getBankRelation(oo)
{
	$O('bankId').value = oo[0].value;
	$O('bankName').value = oo[0].pname;
}
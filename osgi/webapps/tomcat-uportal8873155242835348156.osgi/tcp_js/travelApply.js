function addTr()
{
    for (var i = 0; i < 1; i++)
    {
        addTrInner("tables", "trCopy");
    }
}

function addPayTr()
{
    for (var i = 0; i < 1; i++)
    {
        addTrInner("tables_pay", "trCopy_pay");
    }
}

function addShareTr()
{
    for (var i = 0; i < 1; i++)
    {
        addTrInner("tables_share", "trCopy_share");
    }
}

function compareNumber(a, b)
{
	var aa = a * 1000;
    var bb = b * 1000;
    
    if (Math.abs(aa - bb) < 10)
    {
    	return 0;
    }
    
    if (aa > bb)
    {
        return 1;
    }

    return -1;
}

function removeTr(obj)
{
    obj.parentNode.parentNode.removeNode(true);
}

function getEle(eles, name)
{
    for (var i = 0; i < eles.length; i++)
    {
        if (eles[i].name == name)
        {
            return eles[i];
        }
    }
    
    return null;
}

var cityObj;

function selectCity(obj)
{
	cityObj = obj;
    window.common.modal('../admin/pop.do?method=rptQueryCity&load=1&selectMode=1');
}

var budgetObj;

function selectBudget(obj)
{
	budgetObj = obj;
    window.common.modal('../budget/budget.do?method=rptQueryRunDepartmentBudget&load=1&selectMode=1');
}

function calDateInner(obj, name)
{
	var tr = getTrObject(obj);
	
	var el = getInputInTr(tr, name);
	
	return calDate(el)
}

function checks()
{
    sumTotal();
    
    var borrow = 0.0;
    
    if ($$('borrow') == 1)
    {
        borrow = sumborrowTotal();
        
        if (borrow == 0)
        {
        	alert('借款金额不能为0');
            
            return false;
        }
        
        var stotal = sumTotal();
        
        if (compareNumber(borrow, stotal) > 0)
        {
            alert('借款金额大于申请金额');
            
            return false;
        }
    }
    
    //s_ratio
    var total = sumRatio();
    
    if ($$('borrow') == 1)
    {
    	if (total == 0)
    	{
    		alert('分担之和不能为0');
                
            return false;
    	}
    	
	    if (total != 100 && compareNumber(borrow, total) != 0)
	    {
	        alert('分担比例之和必须等于100,或者分担金额之和等于(借款):' + borrow);
	            
	        return false;
	    }
    }
    
    //检查预算分担不能重复
    var blist = $("input[name='s_budgetId']");
    
    var tmp = {};
    
    for (var i = 0; i < blist.length; i++)
    {
        if (!isNoneInCommon(tmp[blist[i].value]))
        {
            alert('不能选择重复的预算');
            
            return false;
        }
        
        tmp[blist[i].value] = blist[i].value;
    }
    
    return true;
}

function sumRatio()
{
    var total = 0;
    
    $("input[name='s_ratio']").each(
        function()
        {
            if (this.value != '')
            {
                total += parseFloat(this.value);
            }
        }
    );   
    
    return total;
}

var gMore = 0;

function showMoreAtt()
{
    var obj = getObj('tr_att_more');
    
    if (gMore % 2 == 0)
    {
        $v('tr_att_more', true);
    }
    else
    {
        $v('tr_att_more', false);
    }
    
    gMore++;
}

function borrowChange()
{
    if ($$('borrow') == 0 || $$('borrow') == 2)
    {
        $v('pay_main_tr', false);
        
        //remove tr
        var list = formEntry.elements;
        
        if (list)
        {
        	for (var i = 0; i < list.length; i++)
            {
            	if (list[i].name== 'pay_del_bu')
            	list[i].onclick.apply(list[i]);
            }
        }
    }
    
    if ($$('borrow') == 1)
    {
        $v('pay_main_tr', true);
    }
}

function payTypeChange()
{
    if ($$('payType') == 0 || $$('payType') == 2)
    {
        $v('pay_main_tr', false);
        
        //remove tr
        var list = formEntry.elements;
        
        if (list)
        {
            for (var i = 0; i < list.length; i++)
            {
                if (list[i].name== 'pay_del_bu')
                list[i].onclick.apply(list[i]);
            }
        }
    }
    
    if ($$('payType') == 1)
    {
        $v('pay_main_tr', true);
    }
    
    if ($$('payType') == 2)
    {
        $v('lastMoney', true);
    }
    else
    {
    	$v('lastMoney', false);
    }
}


function getCitys(oos)
{
    var obj = oos[0];
    
    cityObj.value = obj.pname;
}

function sumborrowTotal()
{
    var total = 0;
    
    $("input[name='p_moneys']").each(
        function()
        {
            if (this.value != '')
            {
                total += parseFloat(this.value);
            }
        }
    );
    
    return total;
}

function sumTotal()
{
    var total = 0;
    
    $('#traTable input').each(
        function()
        {
            if (this.value != '')
            {
                total += parseFloat(this.value);
            }
        }
    );
    
    $("input[name='i_moneys']").each(
        function()
        {
            if (this.value != '')
            {
                total += parseFloat(this.value);
            }
        }
    );
    
    $("input[name='allTotal']").val(formatNum(total + '', 2));
    
    return total;
}

function getRunDepartmentBudgets(oos)
{
    var obj = oos[0];
    
    budgetObj.value = obj.pname;
    
    var tr = getTrObject(budgetObj);
    
    setInputValueInTr(tr, 's_budgetId', obj.value);
    
    setInputValueInTr(tr, 's_departmentName', obj.pdname);
    setInputValueInTr(tr, 's_departmentId', obj.pdid);
    
    setInputValueInTr(tr, 's_approverName', obj.psignername);
    setInputValueInTr(tr, 's_approverId', obj.psignerid);
}

function selectNext(type, value)
{
    if (type == 'group')
    {
    	sflag = 0;
    	window.common.modal('../group/group.do?method=rptQueryGroupMember&selectMode=1&load=1&pid=' + value);
    }	
}

function getStaffers(oos)
{
	if (sflag == 1)
	{
	    if (oos.length > 0)
	    {
	        var item = oos[0];
	        $("input[name='borrowStafferName']").val(item.pname);
	        $("input[name='borrowStafferId']").val(item.value);
	    }
	}
    
    if (sflag == 0)
    {
	    if (oos.length > 0)
	    {
	        var item = oos[0];
	        $("input[name='processer']").val(item.pname);
	        $("input[name='processId']").val(item.value);
	    }
    }
    
    if (sflag == 2)
    {
        var oo = oos[0];
    
	    var tr = getTrObject(stafferObj);
	    
	    setInputValueInTr(tr, 's_bearName', oo.pname);
	    setInputValueInTr(tr, 's_bearId', oo.value);
    }
    
    if (sflag == 3)
    {
        var oo = oos[0];
    
        var tr = getTrObject(taxStafferObj);
        
        setInputValueInTr(tr, 'taxStafferName', oo.pname);
        setInputValueInTr(tr, 'taxStafferId', oo.value);
    }
}

function pagePrint()
{
    $O('flowLog').style.display = 'none';
    $O('ok_b').style.display = 'none';
    $O('pr').style.display = 'none';
    window.print();

    $O('flowLog').style.display = 'inline';
    $O('ok_b').style.display = 'inline';
    $O('pr').style.display = 'inline';
}

function initSelectNext()
{
	if ($$('stype') == '')
	{
		alert('请选择系列');
		return false;
	}
	
	if ($$('stype') != '2')
	{
		selectNext('group', 'A220110406000200001')
	}
	else
	{
		selectNext('group', 'A220110406000200004')
	}
}

function pagePrint()
{
    window.print();
}

var sflag = 0;

function selectStaffer()
{
	sflag = 1;
	window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}


var g_obj;
function selectBank(obj)
{
	if ($$('dutyId') == '')
	{
		alert('请选择纳税实体');
		return false;
	}
	
    g_obj = obj;
    
    //单选
    window.common.modal('../finance/bank.do?method=rptQueryBank&load=1&dutyread=1&dutyId=' + $$('dutyId'));
}

var stafferObj;

function selectStaffer2(obj)
{
	//stafferObj = obj;
	
	//sflag = 2;
	
    //window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

var taxStafferObj;

function selectTaxStaffer(obj)
{
	taxStafferObj = obj;
	
	sflag = 3;
	
	window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

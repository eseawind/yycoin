function sub()
{
    $O('saves').value = 'submit';
    if (check(false))
    {
       	processProm();
    }
}

function check(isAdd)
{
    var amountList = document.getElementsByName('amount');
    
    if (isAdd)
    {
        for (var i = 0; i < amountList.length; i++)
        {
            amountList[i].ignorecheck = 'true';
        }
    }
    else
    {
         for (var i = 0; i < amountList.length; i++)
        {
            amountList[i].ignorecheck = 'false';
        }
    }
    
    if (!formCheck())
    {
        return false;
    }

    ids = '';
    amous = '';
    
    $O('inputPriceList').value = '';
    $O('priceList').value = '';
    $O('totalList').value = '';
    $O('nameList').value = '';
    $O('unitList').value = '';
    $O('otherList').value = '';
    $O('showIdList').value = '';
    $O('showNameList').value = '';
    $O('desList').value = '';
    $O('showCostList').value = '';
    
    if (trim($O('outTime').value) == '')
    {
        alert('请选择销售日期');
        return false;
    }

    if ($$('outType') == '')
    {
        alert('请选择库单类型');
        return false;
    }

    if ($O('customerId').value == '')
    {
        alert('请选择客户');
        return false;
    }

    if ($$('department') == '')
    {
        alert('请选择销售部门');
        return false;
    }

    if (!eCheck([$O('reday')]))
    {
        alert('请填入1到180之内的数字');
        $O('reday').focus();
        return false;
    }

    var proNames = document.getElementsByName('productName');
    var units = document.getElementsByName('unit');
    var amounts = document.getElementsByName('amount');
    var prices = document.getElementsByName('price');
    var inputPrices = document.getElementsByName('inputPrice');
    var values = document.getElementsByName('value');
    var outProductNames = document.getElementsByName('outProductName');

    var tmpMap = {};
    //isNumbers
    for (var i = 1; i < proNames.length; i++)
    {
        if (proNames[i].value == '' || proNames[i].productid == '')
        {
            alert('数据不完整,请选择产品名称!');
            
            return false;
        }

        ids = ids + proNames[i].productid + '~';

        $O('nameList').value = $O('nameList').value +  proNames[i].value + '~';
        
        var ikey = toUnqueStr2(proNames[i]);
        
        if (tmpMap[ikey])
        {
            alert('选择的产品重复[仓区+产品+职员+价格],请核实!');
            
            return false;
        }
        
        tmpMap[ikey] = ikey;
        
        //库存重要的标识
        $O('otherList').value = $O('otherList').value + ikey + '~';

        $O('idsList').value = ids;
    }

    for (var i = 1; i < amounts.length; i++)
    {
        if (trim(amounts[i].value) == '')
        {
            alert('数据不完整,请填写产品数量!');
            amounts[i].focus();
            return false;
        }

        if (!isNumbers(amounts[i].value))
        {
            alert('数据错误,产品数量 只能是整数!');
            amounts[i].focus();
            return false;
        }
        
        if (parseInt(amounts[i].value, 10) < 0)
        {
            alert('数据错误,产品数量不能为负数!');
            amounts[i].focus();
            return false;
        }

        amous = amous + amounts[i].value + '~';

        $O('amontList').value = amous;
    }
    
    for (var i = 1; i < outProductNames.length; i++)
    {
        if (trim(outProductNames[i].value) == '')
        {
            alert('数据不完整,请选择!');
            outProductNames[i].focus();
            return false;
        }

        amous = amous + amounts[i].value + '~';

        $O('showIdList').value =  $O('showIdList').value + outProductNames[i].value + '~';
        
        $O('showNameList').value =  $O('showNameList').value + getOptionText(outProductNames[i]) + '~';
    }

    for (var i = 1; i < prices.length; i++)
    {
        if (trim(prices[i].value) == '')
        {
            alert('数据不完整,请填写产品价格!');
            prices[i].focus();
            return false;
        }
        
        if (!isFloat(prices[i].value))
        {
            alert('数据错误,产品价格只能是浮点数!');
            prices[i].focus();
            return false;
        }
        
        if (!isFloat(inputPrices[i].value))
        {f
            alert('数据错误,产品输入价格只能是浮点数!');
            inputPrices[i].focus();
            return false;
        }
        
        if (parseFloat(trim(prices[i].value)) == 0)
        {
            if (!window.confirm('除非赠品单价不要填0,否则到总裁审批,你确定?'))
            {
                 inputPrices[i].focus();
                 return false;
            }
        }
        
        $O('priceList').value = $O('priceList').value + prices[i].value + '~';
        $O('inputPriceList').value = $O('inputPriceList').value + inputPrices[i].value + '~';
    }

    //成本
    var desList = document.getElementsByName('costPrice');
    
    var desciprtList = document.getElementsByName('desciprt');
    
    for (var i = 1; i < desList.length; i++)
    {
        if (trim(desList[i].value) == '')
        {
            alert('成本是必填!');
            desList[i].focus();
            return false;
        }
        
        if (!isFloat(desList[i].value))
        {
            alert('格式错误,成本只能是浮点数!');
            desList[i].focus();
            return false;
        }
        
        if (parseFloat(trim(prices[i].value)) != 0 
            && (parseFloat(trim(prices[i].value)) < parseFloat(trim(desciprtList[i].value))))
        {
            if (!window.confirm('产品销售价['+prices[i].value+']低于成本价['+desciprtList[i].value+'],你确定?'))
            {
                 inputPrices[i].focus();
                 return false;
            }
        }
    }
    
    for (var i = 1; i < values.length; i++)
    {
        $O('totalList').value = $O('totalList').value + values[i].value + '~';
        
        //真正的成本
        $O('desList').value = $O('desList').value + desList[i].value + '~';
        
        //显示成本
        $O('showCostList').value = $O('showCostList').value + desciprtList[i].value + '~';
    }

    for (var i = 1; i < units.length; i++)
    {
        $O('unitList').value = $O('unitList').value + units[i].value + '~';
    }

    $O('totalss').value = tsts;

    return true;
}

function addTr()
{
    for (var i = 0; i < 1; i++)
    {
        addTrInner("tables", "trCopy");
    }
}

function addProTr()
{
    for (var i = 0; i < 1; i++)
    {
        addTrInner("tables_Pro", "trCopy");
    }
}

function addPayTr()
{
    for (var i = 0; i < 1; i++)
    {
        addTrInner("tables_pay", "trCopy_pay");
    }
}

function addTransTr()
{
    for (var i = 0; i < 1; i++)
    {
        addTrInner("tables_trans", "trCopy_trans");
    }
}

function addInvoiceTr()
{
    for (var i = 0; i < 1; i++)
    {
        addTrInner("tables_invoice", "trCopy_invoice");
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
    if ($$('borrow') == 0)
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

var stafferObj;

function selectAllstaffer(obj)
{
		stafferObj=obj;	
    	window.common.modal('../group/group.do?method=rptQueryAllStaffer&selectMode=1&load=1&flag=0');
}

function setStaffersVal(oos)
{
	var obj = oos;
	stafferObj.value = obj.pname;
    
    var tr = getTrObject(stafferObj);
    
    setInputValueInTr(tr, 's_prostafferId', obj.pvalue);
    
}


function selectDutyStaffer()
{
	window.common.modal('../group/group.do?method=selectDutyStaffer&selectMode=1&load=1&flag=1');
}



function setDutyStafferVal(oos)
{
	var pv = $O('partakerids').value;
	
	if (pv && pv.indexOf(oos.pvalue) != -1)
	{
		alert("责任人在参与人中存在，请清除参与人.");
		return;
	}
	
	$O('dutyStaffer').value=oos.pname;
	$O('dutyStafferID').value=oos.pvalue;
    
}

var receiverobj
function selectReceiver(obj)
{
	receiverobj=obj;
	window.common.modal('../project/project.do?method=selectReceiver&selectMode=1&load=1');
}

function setReceiverVal(oos,selType)
{
	receiverobj.value = oos.pname;
	$O('receiverid').value=oos.pvalue ; // +","+selType;
}


function selectpartyA(obj)
{
	window.common.modal('../project/project.do?method=selectpartyA&stafferId=${user.stafferId}&load=1');
}

function setpartyAVal(oos,selType)
{
	$O('party_a').value = oos.pname;
	$O('partaid').value = oos.pvalue;
    $O('pa_selType').value=selType;
}

function selectpartyB(obj)
{
	window.common.modal('../project/project.do?method=selectpartyB&stafferId=${user.stafferId}&load=1');
}

function setpartyBVal(oos,selType)
{
	$O('party_b').value = oos.pname;
	$O('partbid').value = oos.pvalue;
    $O('pb_selType').value=selType;
}

function selectpartaker()
{
	sflag = 4;
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=0');
}

var proobj
function selectProjectpro(obj)
{
	proobj=obj;	
	window.common.modal('../product/product.do?method=selectProjectpro&firstLoad=1&selectMode=1');
}

function setProjectProVal(oos)
{
	var obj = oos;
    
	proobj.value = obj.pname;
    
    var tr = getTrObject(proobj);
    
    setInputValueInTr(tr, 's_projectproId', obj.pvalue);
    
}

var agreobj
function selectAgreementpro(obj)
{
	agreobj=obj;	
	window.common.modal('../product/product.do?method=selectAgreementpro&firstLoad=1&selectMode=1');
}

function setAgreementProVal(oos)
{
	var obj = oos[0];
    
	agreobj.value = obj.pname;
    
    var tr = getTrObject(agreobj);
    
    setInputValueInTr(tr, 's_agreementproId', obj.value);
    
}

var curTaskobj
function selectPaycurrentTask1(obj)
{
	curTaskobj=obj;	
	window.common.modal('../project/project.do?method=selectPaycurrentTask1&firstLoad=1&selectMode=1');
}

function setCurTaskobjVal(oos)
{
	var obj = oos;
    
	curTaskobj.value = obj.pname;
    
    var tr = getTrObject(curTaskobj);
    
    setInputValueInTr(tr, 's_paycurrentTask1id', obj.value);
    
}

var tranCurTaskobj
function selectTrancurrentTask1(obj)
{
	tranCurTaskobj=obj;	
	window.common.modal('../project/project.do?method=selectTrancurrentTask1&firstLoad=1&selectMode=1');
}

function setTranCurTaskobjVal(oos)
{
	var obj = oos;
    
	tranCurTaskobj.value = obj.pname;
    
    var tr = getTrObject(tranCurTaskobj);
    
    setInputValueInTr(tr, 's_trancurrentTask1id', obj.value);
    
}

var invocurrentobj
function selectinvocurrentTask(obj)
{
	invocurrentobj=obj;	
	window.common.modal('../project/project.do?method=selectinvocurrentTask&firstLoad=1&selectMode=1');
}

function setinvocurrentobjVal(oos)
{
	var obj = oos;
    
	invocurrentobj.value = obj.pname;
    
    var tr = getTrObject(invocurrentobj);
    
    setInputValueInTr(tr, 's_invocurrentTask', obj.value);
    
}


var payBeforeTaskobj
function selectPayBeforeTask(obj)
{
	payBeforeTaskobj=obj;	
	window.common.modal('../project/project.do?method=selectPayBeforeTask&firstLoad=1&selectMode=1');
}

function setPayBeforeTaskVal(oos)
{
    
    var tr = getTrObject(payBeforeTaskobj);
	var ids = '';
    var names = '';
    for (var i = 0; i < oos.length; i++)
    {
        if (i == oos.length - 1)
        {
            ids = ids + oos[i].value ;
            names = names + oos[i].pname ;
        }
        else
        {
            ids = ids + oos[i].value + ',';
            names = names + oos[i].pname + ',' ;
        }        
    }
    	var eles = tr.getElementsByTagName('input');
        
        for (var i = 0; i < eles.length; i++)
        {
            if (eles[i].name == 's_payBeforeids')
            {
            	if(eles[i].value!=''&&eles[i].value!=null)
            		{
            		eles[i].value =eles[i].value+','+ids;
            		}
            	else
            		{
            		eles[i].value =ids;
            		}
            }
        }
        
    if(payBeforeTaskobj.innerText!="" && payBeforeTaskobj.innerText!=null)
		{
    	payBeforeTaskobj.innerText=payBeforeTaskobj.innerText+","+names;
		}
    else
		{
    	payBeforeTaskobj.innerText = names;
		}
    
}

var payAfterobj
function selectPayAfterTask(obj)
{
	payAfterobj=obj;	
	window.common.modal('../project/project.do?method=selectPayAfterTask&firstLoad=1&selectMode=1');
}


function setPayAfterTaskVal(oos)
{
	var tr = getTrObject(payAfterobj);
	var ids = '';
    var names = '';
    for (var i = 0; i < oos.length; i++)
    {
        if (i == oos.length - 1)
        {
            ids = ids + oos[i].value ;
            names = names + oos[i].pname ;
        }
        else
        {
            ids = ids + oos[i].value + ',';
            names = names + oos[i].pname + ',' ;
        }        
    }
    	var eles = tr.getElementsByTagName('input');
        
        for (var i = 0; i < eles.length; i++)
        {
            if (eles[i].name == 's_payAfterids')
            {
            	if(eles[i].value!=''&&eles[i].value!=null)
            		{
            		eles[i].value =eles[i].value+','+ids;
            		}
            	else
            		{
            		eles[i].value =ids;
            		}
            }
        }
        
    if(payAfterobj.innerText!="" && payAfterobj.innerText!=null)
		{
    	payAfterobj.innerText=payAfterobj.innerText+","+names;
		}
    else
		{
    	payAfterobj.innerText = names;
		}
}



var tranBeforeobj
function selectTranBeforeTask(obj)
{
	tranBeforeobj=obj;	
	window.common.modal('../project/project.do?method=selectTranBeforeTask&firstLoad=1&selectMode=1');
}

function setTranBeforeTaskVal(oos)
{
	var tr = getTrObject(tranBeforeobj);
	var ids = '';
    var names = '';
    for (var i = 0; i < oos.length; i++)
    {
        if (i == oos.length - 1)
        {
            ids = ids + oos[i].value ;
            names = names + oos[i].pname ;
        }
        else
        {
            ids = ids + oos[i].value + ',';
            names = names + oos[i].pname + ',' ;
        }        
    }
    	var eles = tr.getElementsByTagName('input');
        
        for (var i = 0; i < eles.length; i++)
        {
            if (eles[i].name == 's_tranBeforeids')
            {
            	if(eles[i].value!=''&&eles[i].value!=null)
            		{
            		eles[i].value =eles[i].value+','+ids;
            		}
            	else
            		{
            		eles[i].value =ids;
            		}
            }
        }
        
    if(tranBeforeobj.innerText!="" && tranBeforeobj.innerText!=null)
		{
    	tranBeforeobj.innerText=tranBeforeobj.innerText+","+names;
		}
    else
		{
    	tranBeforeobj.innerText = names;
		}
    
}

var tranAfterobj
function selectTranAfterTask(obj)
{
	tranAfterobj=obj;	
	window.common.modal('../project/project.do?method=selectTranAfterTask&firstLoad=1&selectMode=1');
}

function setTranAfterTaskVal(oos)
{
	var tr = getTrObject(tranAfterobj);
	var ids = '';
    var names = '';
    for (var i = 0; i < oos.length; i++)
    {
        if (i == oos.length - 1)
        {
            ids = ids + oos[i].value ;
            names = names + oos[i].pname ;
        }
        else
        {
            ids = ids + oos[i].value + ',';
            names = names + oos[i].pname + ',' ;
        }        
    }
    	var eles = tr.getElementsByTagName('input');
        
        for (var i = 0; i < eles.length; i++)
        {
            if (eles[i].name == 's_tranAfterids')
            {
            	if(eles[i].value!=''&&eles[i].value!=null)
            		{
            		eles[i].value =eles[i].value+','+ids;
            		}
            	else
            		{
            		eles[i].value =ids;
            		}
            }
        }
        
    if(tranAfterobj.innerText!="" && tranAfterobj.innerText!=null)
		{
    	tranAfterobj.innerText=tranAfterobj.innerText+","+names;
		}
    else
		{
    	tranAfterobj.innerText = names;
		}
    
}

var invoiceBeforeobj
function selectInvoiceBeforeTask(obj)
{
	invoiceBeforeobj=obj;	
	window.common.modal('../project/project.do?method=selectInvoiceBeforeTask&firstLoad=1&selectMode=1');
}

function setInvoiceBeforeTaskVal(oos)
{
	var tr = getTrObject(invoiceBeforeobj);
	var ids = '';
    var names = '';
    for (var i = 0; i < oos.length; i++)
    {
        if (i == oos.length - 1)
        {
            ids = ids + oos[i].value ;
            names = names + oos[i].pname ;
        }
        else
        {
            ids = ids + oos[i].value + ',';
            names = names + oos[i].pname + ',' ;
        }        
    }
    	var eles = tr.getElementsByTagName('input');
        
        for (var i = 0; i < eles.length; i++)
        {
            if (eles[i].name == 's_invoiceBeforeids')
            {
            	if(eles[i].value!=''&&eles[i].value!=null)
            		{
            		eles[i].value =eles[i].value+','+ids;
            		}
            	else
            		{
            		eles[i].value =ids;
            		}
            }
        }
        
    if(invoiceBeforeobj.innerText!="" && invoiceBeforeobj.innerText!=null)
		{
    	invoiceBeforeobj.innerText=tranAfterobj.innerText+","+names;
		}
    else
		{
    	invoiceBeforeobj.innerText = names;
		}
    
}

var invoiceAfterobj
function selectInvoiceAfterTask(obj)
{
	invoiceAfterobj=obj;	
	window.common.modal('../project/project.do?method=selectInvoiceAfterTask&firstLoad=1&selectMode=1');
}

function setInvoiceAfterTaskVal(oos)
{
	var tr = getTrObject(invoiceAfterobj);
	var ids = '';
    var names = '';
    for (var i = 0; i < oos.length; i++)
    {
        if (i == oos.length - 1)
        {
            ids = ids + oos[i].value ;
            names = names + oos[i].pname ;
        }
        else
        {
            ids = ids + oos[i].value + ',';
            names = names + oos[i].pname + ',' ;
        }        
    }
    	var eles = tr.getElementsByTagName('input');
        
        for (var i = 0; i < eles.length; i++)
        {
            if (eles[i].name == 's_invoiceAfterids')
            {
            	if(eles[i].value!=''&&eles[i].value!=null)
            		{
            		eles[i].value =eles[i].value+','+ids;
            		}
            	else
            		{
            		eles[i].value =ids;
            		}
            }
        }
        
    if(invoiceAfterobj.innerText!="" && invoiceAfterobj.innerText!=null)
		{
    	invoiceAfterobj.innerText=invoiceAfterobj.innerText+","+names;
		}
    else
		{
    	invoiceAfterobj.innerText = names;
		}
    
}


var taskBeforeobj
function selectTaskBefore(obj)
{
	taskBeforeobj=obj;	
	window.common.modal('../project/project.do?method=selectTaskBefore&firstLoad=1&selectMode=1');
}

function setTaskBeforeVal(oos)
{
	var ids = '';
    var names = '';
    for (var i = 0; i < oos.length; i++)
    {
        if (i == oos.length - 1)
        {
            ids = ids + oos[i].pvalue ;
            names = names + oos[i].pname ;
        }
        else
        {
            ids = ids + oos[i].pvalue + ',';
            names = names + oos[i].pname + ',' ;
        }        
    }
    if($O('taskBeforeids').value!=""&&$O('taskBeforeids').value!=null)
    	{
    	$O('taskBeforeids').value = $O('taskBeforeids').value+","+ids;
    	}
    else
    	{
    	$O('taskBeforeids').value = ids;
    	}
    if($O('beforeTask').value!=""&&  $O('beforeTask').value!=null)
		{
    	 $O('beforeTask').value =  $O('beforeTask').value+","+names;
		}
    else
		{
    	 $O('beforeTask').value = names;
		}
}

var taskAfterobj
function selectTaskAfter(obj)
{
	taskAfterobj=obj;	
	window.common.modal('../project/project.do?method=selectTaskAfter&firstLoad=1&selectMode=1');
}

function setTaskAfterVal(oos)
{
	var ids = '';
    var names = '';
    for (var i = 0; i < oos.length; i++)
    {
        if (i == oos.length - 1)
        {
            ids = ids + oos[i].pvalue ;
            names = names + oos[i].pname ;
        }
        else
        {
            ids = ids + oos[i].pvalue + ',';
            names = names + oos[i].pname + ',' ;
        }        
    }

    
    if($O('taskAfterids').value!=""&&$O('taskAfterids').value!=null)
		{
    	$O('taskAfterids').value = $O('taskAfterids').value+","+ids;
		}
    else
		{
    	$O('taskAfterids').value = ids;
		}
    if($O('afterTask').value!=""&&$O('afterTask').value!=null)
		{
    	$O('afterTask').value =  $O('afterTask').value+","+names;
		}
    else
		{
    	$O('afterTask').value = names;
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

var taxStafferObj;

function selectTaxStaffer(obj)
{
	taxStafferObj = obj;
	
	sflag = 3;
	
	window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
	if (sflag == 4)
	{
		for (var i = 0; i < oos.length; i++)
	    {
			if ($$('dutyStafferID') == oos[i].value)
			{
				alert("参与人中含责任人，系统自动忽略.");
				continue;
			}
			
        	var pv = $O('partakerids').value;
        	
        	if (pv && pv.indexOf(oos[i].value) != -1)
    		{
        		continue;
    		}
        	else
        	{
        		$O('partakerids').value =  $O('partakerids').value + ',' + oos[i].value ;
        		$O('partaker').value = $O('partaker').value + ',' + oos[i].pname ;
        	}   
	    }
		
		if ($$('partakerids').substr(0,1) == ',')
		{
			$O('partakerids').value = $$('partakerids').substr(1, $$('partakerids').length - 1)
			$O('partaker').value = $$('partaker').substr(1, $$('partaker').length - 1)
		}

		if ($$('partakerids').substr($$('partakerids').length - 1) == ',')
		{
			$O('partakerids').value = $$('partakerids').substr(0, $$('partakerids').length - 1)
			$O('partaker').value = $$('partaker').substr(0, $$('partaker').length - 1)
		}
	}
	else if (sflag == '10') {
		var oo = oos[0];
	    
	    $O('applyerName').value = oo.pname;
	    $O('applyer').value = oo.value;
	}
	else
	{
	    if (oos.length > 0)
	    {
	        var item = oos[0];
	        $("input[name='processer']").val(item.pname);
	        $("input[name='processId']").val(item.value);
	    }
	}
}

var agrebeforeobj;
function selectBeforeAgre(obj)
{
	agrebeforeobj=obj;
	window.common.modal('../project/project.do?method=selectBeforeAgre&stafferId=${user.stafferId}&load=1');
}

function setAgreeBeforeVal(oos)
{
	var ids = '';
    var names = '';
    for (var i = 0; i < oos.length; i++)
    {
        if (i == oos.length - 1)
        {
            ids = ids + oos[i].value ;
            names = names + oos[i].pname ;
        }
        else
        {
            ids = ids + oos[i].value + ',';
            names = names + oos[i].pname + ',' ;
        }        
    }
    if($O('beforeAgreeids').value!=""&&$O('beforeAgreeids').value!=null)
    	{
    	$O('beforeAgreeids').value = $O('beforeAgreeids').value+","+ids;
    	}
    else
    	{
    	$O('beforeAgreeids').value = ids;
    	}
    if($O('beforeAgreement').value!=""&&  $O('beforeAgreement').value!=null)
		{
    	 $O('beforeAgreement').value =  $O('beforeAgreement').value+","+names;
		}
    else
		{
    	 $O('beforeAgreement').value = names;
		}
}

var agreAfterobj
function selectAfterAgre(obj)
{
	agreAfterobj=obj;
	window.common.modal('../project/project.do?method=selectAfterAgre&stafferId=${user.stafferId}&load=1');
}

function setAgreeAfterVal(oos)
{
	var ids = '';
    var names = '';
    for (var i = 0; i < oos.length; i++)
    {
        if (i == oos.length - 1)
        {
            ids = ids + oos[i].value ;
            names = names + oos[i].pname ;
        }
        else
        {
            ids = ids + oos[i].value + ',';
            names = names + oos[i].pname + ',' ;
        }        
    }
    if($O('afterAgreeids').value!=""&&$O('afterAgreeids').value!=null)
    	{
    	$O('afterAgreeids').value = $O('afterAgreeids').value+","+ids;
    	}
    else
    	{
    	$O('afterAgreeids').value = ids;
    	}
    if($O('afterAgreement').value!=""&&  $O('afterAgreement').value!=null)
		{
    	 $O('afterAgreement').value =  $O('afterAgreement').value+","+names;
		}
    else
		{
    	 $O('afterAgreement').value = names;
		}
}

function processPro()
{
	var apidArr = document.getElementsByName('s_agreementproId');
    var proCountArr = document.getElementsByName('proCounts');
    var proPriceArr = document.getElementsByName('proUnitPrice');
    var proIds='';
    var pCount='';
    var proPrice='';
    var lth=proCountArr.length-2;
    for(var i=0;i<apidArr.length-1;i++)
    {
        if(i==lth)
        {
        	proIds=proIds+apidArr[i].value;
        }
        else
        {
        	proIds=proIds+apidArr[i].value+",";
        }
    }

    for(var i=0;i<proCountArr.length-1;i++)
    {
        if(i==lth)
        {
        	pCount=pCount+proCountArr[i].value;
        }
        else
        {
        	pCount=pCount+proCountArr[i].value+",";
        }
    }
    for(var i=0;i<proPriceArr.length-1;i++)
    {
        if(i==lth)
        {
        	proPrice=proPrice+proPriceArr[i].value;
        }
        else
        {
        	proPrice=proPrice+proPriceArr[i].value+",";
        }
    }
    $O('proIds').value=proIds;
    $O('pCount').value=pCount;
    $O('proPrice').value=proPrice;
}

var projectObj;

function selectProject(obj)
{
	projectObj=obj;	
    	window.common.modal('../project/project.do?method=selectProject&selectMode=1&load=1&flag=0');
}

function setProjectVal(oos)
{
	var obj = oos;
	projectObj.value = obj.pname;
	$O('s_projectId').value=obj.pvalue;
    
}

function selectApplyer()
{
	sflag = 10;
	
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

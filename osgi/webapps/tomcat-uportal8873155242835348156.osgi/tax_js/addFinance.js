function $v2(obj, f)
{
     if (f)
     {
     	if (is_ie)
         obj.style.display = 'inline';
         else
         obj.style.display = '';
     }
     else
     obj.style.display = 'none';
}

function concat2(arr1, arr2)
{
	var arr = [];
	var j = 0;
	
    for (var i = 0; i < arr1.length; i++)
    {
        arr[j++] = arr1[i];
    }
    
    for (var i = 0; i < arr2.length; i++)
    {
        arr[j++] = arr2[i];
    }
    
    return arr;
}

function getTrInnerObj(obj, name)
{
	var tr = getTrObject(obj);
	
	var inputs1 = tr.getElementsByTagName('input');
	
	var inputs2 = tr.getElementsByTagName('select');
	
	var inputs = concat2(inputs1, inputs2);
	
	for (var i = 0; i < inputs.length; i++)
	{
		if (inputs[i].name == name)
		{
			return inputs[i];
		}
	}
	
	return null;
}

function getTrInnerObj2(tr, name)
{
    var inputs1 = tr.getElementsByTagName('input');
    
    var inputs2 = tr.getElementsByTagName('select');
    
    var inputs = concat2(inputs1, inputs2);
    
    for (var i = 0; i < inputs.length; i++)
    {
        if (inputs[i].name == name)
        {
            return inputs[i];
        }
    }
    
    return null;
}


function taxChange(obj)
{
	var option = obj;
	
	var tr = getTrObject(obj);
	
	var inputs1 = tr.getElementsByTagName('input');
	
	var inputs2 = tr.getElementsByTagName('select');
	
	var inputs = concat2(inputs1, inputs2);
	
	for (var i = 0; i < inputs.length; i++)
	{
		if (inputs[i].name == 'inmoney')
		{
			inEle = inputs[i];
		}
		
		if (inputs[i].name == 'outmoney')
		{
			outEle = inputs[i];
		}
		
		if (inputs[i].name == 'departmentId')
		{
			depEle = inputs[i];
		}
		
		if (inputs[i].name == 'stafferId')
		{
			staEle = inputs[i];
		}
		
		if (inputs[i].name == 'unitId')
		{
			unitEle = inputs[i];
		}
		
		if (inputs[i].name == 'productId')
        {
            productEle = inputs[i];
        }
        
        if (inputs[i].name == 'depotId')
        {
           depotEle = inputs[i];
        }
        
        if (inputs[i].name == 'duty2Id')
        {
           duty2Ele = inputs[i];
        }
	}
	
	for (var i = 0; i < taxList.length; i++)
	{
		if ((taxList[i].code + taxList[i].name) == option.value)
		{
			// in
            outEle.oncheck = 'notNone;isFloat';
            
            outEle.readOnly = false;
            
            inEle.readOnly = false;
            
            inEle.oncheck = 'notNone;isFloat';
			
			if (taxList[i].department == 1)
			{
				$v2(depEle, true);
				
				depEle.oncheck = 'notNone';
			}
			else
			{
				$v2(depEle, false);
				
				depEle.oncheck = '';
			}
			
			if (taxList[i].staffer == 1)
			{
				$v2(staEle, true);
				
				staEle.oncheck = 'notNone';
			}
			else
			{
				$v2(staEle, false);
				
				staEle.oncheck = '';
			}
			
			if (taxList[i].unit == 1)
			{
				$v2(unitEle, true);
				
				unitEle.oncheck = 'notNone';
			}
			else
			{
				$v2(unitEle, false);
				
				unitEle.oncheck = '';
			}
			
			if (taxList[i].product == 1)
            {
                $v2(productEle, true);
                
                productEle.oncheck = 'notNone';
            }
            else
            {
                $v2(productEle, false);
                
                productEle.oncheck = '';
            }
            
            if (taxList[i].depot == 1)
            {
                $v2(depotEle, true);
                
                depotEle.oncheck = 'notNone';
            }
            else
            {
                $v2(depotEle, false);
                
                depotEle.oncheck = '';
            }
            
            if (taxList[i].duty == 1)
            {
                $v2(duty2Ele, true);
                
                duty2Ele.oncheck = 'notNone';
            }
            else
            {
                $v2(duty2Ele, false);
                
                duty2Ele.oncheck = '';
            }
			
			var hid = getTrInnerObj(obj, 'taxId2');
			
			hid.value = taxList[i].id;
			
			break;
		}
	}
}

function checkSelect(obj)
{
    var tr = getTrObject(obj);
    
    //获取taxId
    var option = getTrInnerObj(obj, 'taxId');
    
    var inputs1 = tr.getElementsByTagName('input');
    
    var inputs2 = tr.getElementsByTagName('select');
    
    var inputs = concat2(inputs1, inputs2);
    
    for (var i = 0; i < inputs.length; i++)
    {
        
        if (inputs[i].name == 'stafferId')
        {
            staEle = inputs[i];
        }
        
        if (inputs[i].name == 'unitId')
        {
            unitEle = inputs[i];
        }
        
        if (inputs[i].name == 'productId')
        {
            productEle = inputs[i];
        }
    }
    
    for (var i = 0; i < taxList.length; i++)
    {
        if ((taxList[i].code + taxList[i].name) == option.value)
        {
            if (taxList[i].staffer == 1 && staEle.value == '选择职员')
            {
            	alert('选择职员');
            	
            	$f(staEle);
            	
            	return false;
            }
            
            if (taxList[i].unit == 1 && unitEle.value == '选择单位')
            {
                alert('选择单位');
                
                $f(unitEle);
                
                return false;
            }
           
            
            if (taxList[i].product == 1 && productEle.value == '选择产品')
            {
                alert('选择产品');
                
                $f(productEle);
                
                return false;
            }
        }
    }
    
    return true;
}

var current;

function selectStaffer(obj)
{
	current = obj;
	window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}


function selectProduct(obj)
{
	current = obj;
    window.common.modal('../product/product.do?method=rptQueryProduct&load=1&selectMode=1&abstractType=0&status=0');
}

//选择职位
function selectPrin(obj)
{
	current = obj;
    window.common.modal('../admin/pop.do?method=rptQueryOrg&load=1&selectMode=1');
}

function callClick(obj, el)
{
    taxChange(obj);
}

function callClickStaffer(obj, el)
{
	var data = findData2(obj.value);
	
	if (data == null)
	{
		return;
	}
	
    //自动赋值给部门
    var hid = getNextInput(obj.nextSibling);
    
    hid.value = data.id;
    
    var org = getNextInput(hid.nextSibling);
    
    if (org)
    {
        if (org.name == 'departmentId' && (org.value == '' || org.value == '请输入部门'))
        {
            org.value = data.description;
            
            var hid = getNextInput(org.nextSibling);
    
            hid.value = data.principalshipId;
            
            org.style.color = '';
        }
    }
}


function callClickPri(obj, el)
{
	var data = findData3(obj.value);
	
    if (data == null)
    {
        return;
    }
    
    var hid = getNextInput(obj.nextSibling);
    
    hid.value = data.id;
}

function getProduct(oos)
{
    var obj = oos[0];
    
    current.value = obj.pname;
    
    var hid = getNextInput(current.nextSibling);
    
    hid.value = obj.value;
    
    current.style.color = '';
}

function colorthis(obj)
{
	obj.style.color = '';
	
	if (obj.value == '请输入职员')
	{
		obj.value = '';
	}
	
	if (obj.value == '请输入部门')
    {
        obj.value = '';
    }
}

function getStaffers(oos)
{
	var obj = oos[0];
	
	var hid = getNextInput(current.nextSibling);
	
    hid.value = obj.value;
    
    current.value = obj.pname;
    
    current.style.color = '';
    
    var org = getNextInput(hid.nextSibling);
    
    if (org)
    {
    	if (org.name == 'departmentId')
    	{
    		org.value = obj.pdname;
    		
    		var hid = getNextInput(org.nextSibling);
    
		    hid.value = obj.pdid;
		    
		    org.style.color = '';
    	}
    }
}

function getUnit(obj)
{
	var hid = getNextInput(current.nextSibling);
    hid.value = obj.value;
    current.value = obj.pname;
    current.style.color = '';
}

function setOrgFromPop(id, name, level, pname)
{
	if (pname)
    current.value = pname + '->' + '[' + level + ']' + name;
    else
    current.value = '[' + level + ']' + name;
    
    var hid = getNextInput(current.nextSibling);
    
    hid.value = id;
    
    current.style.color = '';
}


function selectUnit(obj)
{
	current = obj;
	window.common.modal('../finance/finance.do?method=rptQueryUnit&load=1');
}

function getNextInput(el)
{
	if (el == null)
	{
	   return;	
	}   
	
    if (el.tagName && el.tagName.toLowerCase() == 'input')
    {
        return el;
    }
    else
    {
        return getNextInput(el.nextSibling);
    }
}

function initData()
{
    var dataList = [];
    
    for (var i = 0; i < taxList.length; i++)
    {
        dataList[i] = taxList[i].code + taxList[i].name;
    }
    
    return dataList;
}

function initData2()
{
    var dataList = [];
    
    for (var i = 0; i < stafferList.length; i++)
    {
        dataList[i] = stafferList[i].name + '(' + stafferList[i].description + ')';
    }
    
    return dataList;
}

function findData2(value)
{
    for (var i = 0; i < stafferList.length; i++)
    {
        if (value == (stafferList[i].name + '(' + stafferList[i].description + ')'))
        {
            return stafferList[i];
        }
    }
    
    return null;
}

function initData3()
{
    var dataList = [];
    
    for (var i = 0; i < priList.length; i++)
    {
        dataList[i] = priList[i].name;
    }
    
    return dataList;
}

function findData3(value)
{
    for (var i = 0; i < priList.length; i++)
    {
        if (value == priList[i].name)
        {
            return priList[i];
        }
    }
    
    return null;
}

function inChange()
{
	var list = document.getElementsByName('inmoney');
	
	var total = 0;
	
	for (var i = 0; i < list.length; i++)
	{
		var v = list[i].value;
		if (v != '')
		{
			total += parseFloat(v);
		}
	}
	
	inTd = document.getElementById('inHTML');
	
	inTd.innerHTML = formatNum(total, 2);
}

function outChange()
{
    var list = document.getElementsByName('outmoney');
    
    var total = 0;
    
    for (var i = 0; i < list.length; i++)
    {
        var v = list[i].value;
        if (v != '')
        {
            total += parseFloat(v);
        }
    }
    
    outTd = document.getElementById('outHTML');
    
    outTd.innerHTML = formatNum(total, 2);
}


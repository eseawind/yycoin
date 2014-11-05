function heji(obj)
{
	var os = obj.parentNode.parentNode;
	
	var m = os.cells[1].childNodes[0].value;
	var p = os.cells[2].childNodes[0].value;
	
	obj.value = mul(m, p);
}

function ccs(obj)
{
	var os = obj.parentNode.parentNode;
	
	var m = os.cells[1].childNodes[0].value;
	
	if (m == '')
	{
		m = 0;
	}
	
	var p = os.cells[2].childNodes[0].value;
	
	//var dues = parseInt(duesMap[$$('dutyId')], 10) + 1000;
	var dues = 1000;
	
	//个人领样没有税
	if ($$('outType') == 1)
	{
		dues = 1000;
	}
	
	var dp = mul(dues / 1000.0, p);
	
	os.cells[3].childNodes[0].value = mul(m, dp);
	
	var tem = os.cells[3].childNodes[0].value;
	
	if (tem == 'NaN')
	{
		os.cells[3].childNodes[0].value = '';
		return;
	}
	
	tem = tem + "";
	
	os.cells[3].childNodes[0].value = formatNum(tem, 2);
	
	total();
	
}

function cc(obj)
{
	ccs(obj);
	total();
}

function blu(obj)
{
	blus(obj);
	ccs(obj);
}



function blus(obj)
{
	obj.value = trim(obj.value);
	
	var tem = obj.value;
	
	if (tem.indexOf('.') == -1)
	{
		if (tem == '')
		{
			obj.value = '0.00';
			return;
		}
		obj.value = tem + '.00';
		return;
	}
	
	tem += '00000000';
	
	if (obj.name == 'inputPrice')
	{
	   obj.value = tem.substring(0, tem.indexOf('.') + 5);
	}
	else
	{
		obj.value = tem.substring(0, tem.indexOf('.') + 3);
	}
}

function delLast()
{
    var list = document.getElementsByName('eachDel');
    
    if (list.length > 0)
    {
        removeTr(list[list.length - 1]);
    }
}

function delAllItem()
{
    var list = document.getElementsByName('eachDel');
    
    while (list && list.length > 0)
    {
        removeTr(list[list.length - 1]);
        
        list = document.getElementsByName('eachDel');
    }
    
    clears();
}

function addTr()
{
	var length = document.getElementsByName("productName").length;
	
	if (length > 15)
	{
		alert('最多只能有15个产品');
		return null;
	}
	
	var table = $O("tables");
	var tr = $O("trCopy");
	
	trow = 	table.insertRow(-1);
	
	if (length % 2 == 1)
	{
		trow.className = 'content2';
	}
	else
	{
		trow.className = 'content1';
	}
	
	for (var i = 0; i < tr.cells.length - 1; i++)
	{
		var tcell = document.createElement("td");
		
		tcell.innerHTML = tr.cells[i].innerHTML;
		
		trow.appendChild(tcell);
	}
	
	tcell = document.createElement("td");
	
	tcell.innerHTML = '<input type=button value="删除" name=eachDel class=button_class onclick="removeTr(this)">';
	
	trow.appendChild(tcell);
	
	total();
	
	return trow;
}

function removeTr(obj)
{
	obj.parentNode.parentNode.removeNode(true);
	
	//rows
	var table = $O("tables");
	
	for (var i = 2; i < table.rows.length; i++)
	{
		if (i % 2 == 1)
		{
			table.rows[i].className = 'content1';
		}
		else
		{
			table.rows[i].className = 'content2';
		}
	}
	
	total();
}

function toUnqueStr1(ele)
{
	return ele.value;
}

function toUnqueStr2(ele)
{
    return ele.productid;
}

function distinc(ox)
{
	//productid
	var plist = document.getElementsByName('productName');
	
	var arr1 = new Array();
	
	for(var i = 0; i < plist.length; i++)
	{
		arr1[i] = toUnqueStr2(plist[i]);
	}
	
	var arr = new Array();
	var n = 0;
	for(var k = 0; k < ox.length; k++)
	{
		var ff = false;
		for(var i = 0; i < arr1.length; i++)
		{
			if (arr1[i] == toUnqueStr1(ox[k]))
			{
				ff = true;
			}
		}
		
		if (!ff)
		{
			arr1.push(toUnqueStr1(ox[k]));
			
			arr[n++] = ox[k];
		}
	}
	
	return arr;
}

function clears()
{
	document.getElementById('unAmount').value = '';
	document.getElementById('unAmount').title = '';
	document.getElementById('unPrice').value = '';
	document.getElementById('unProductName').value = '';
	document.getElementById('unProductName').productid = '';
	document.getElementById('unProductName').productcode = '';
}

function clearsAll()
{
	clearArray(document.getElementsByName('productName'));
	clearArray(document.getElementsByName('amount'), true);
	clearArray(document.getElementsByName('price'));
	clearArray(document.getElementsByName('value'));
}

function clearArray(array, flag)
{
	for (var i = 0; i < array.length; i++)
	{
		array[i].value = '';
		
		if (flag)
        array[i].oncheck = '';
		
		if (array[i].productid)
		{
			array[i].productid = '';
		}
	}
}

/**
 * oo是在addout里面定义的
 */
function getProduct(ox)
{
	ox = distinc(ox);
	
	if (ox.length <= 0)
	{
		return;
	}
	
	var indes = 0;
        
    if (indes == 0)
    {
        indes = 1;

        // oo就是选择的输入框
        setObj(oo, ox[0])
        
        var os = oo.parentNode.parentNode;
        
        os.cells[2].childNodes[0].value = ox[0].pprice;
        
    }

    for(var i = indes; i < ox.length; i++)
    {
        var newTr = addTr();

        if (newTr == null)
        {
            break;
        }
        
        var inps = newTr.getElementsByTagName('INPUT');
        
        setObj(inps[0], ox[i])
        
        inps[2].value = ox[i].pprice;
        
    }
}

function setObj(src, dest)
{
	src.value = dest.pname;
    src.productid = dest.value;
    
    src.costprice = dest.pcostprice;
    src.midrevenue = dest.pmidrevenue;
    
    return true;
}

function setObj2(src, dest)
{
    src.value = dest.name;
    
    src.productid = dest.id;
    src.costprice = dest.costprice;
    src.midrevenue = dest.midrevenue;
}

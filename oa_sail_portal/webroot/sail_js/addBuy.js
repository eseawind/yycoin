function heji(obj)
{
	var os = obj.parentNode.parentNode;
	
	var m = os.cells[2].childNodes[0].value;
	var p = os.cells[3].childNodes[0].value;
	
	obj.value = mul(m, p);
}

function ccs(obj)
{
	var os = obj.parentNode.parentNode;
	
	var m = os.cells[2].childNodes[0].value;
	
	if (m == '')
	{
		m = 0;
	}
	
	var p = os.cells[3].childNodes[0].value;
	
	os.cells[4].childNodes[0].value = mul(m, p);
	
	var tem = os.cells[4].childNodes[0].value;
	
	if (tem == 'NaN')
	{
		os.cells[4].childNodes[0].value = '';
		return;
	}
	
	tem = tem + "";
	
	os.cells[4].childNodes[0].value = formatNum(tem, 2);
	
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
	
	tem += '00';
	obj.value = tem.substring(0, tem.indexOf('.') + 3);
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
	
	tcell.innerHTML = '<input type=button value="删除" class=button_class onclick="removeTr(this)">';
	
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
	return ele.ppid + '-' + ele.pprice + '-' + ele.pstafferid + '-' + ele.pdepotpartid;
}

function toUnqueStr2(ele)
{
    return ele.productid + '-' + ele.price + '-' + ele.stafferid + '-' + ele.depotpartid;
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
	document.getElementById('unAmount').oncheck = '';
	document.getElementById('unPrice').value = '';
	document.getElementById('unProductName').value = '';
	document.getElementById('unProductName').productid = '';
	document.getElementById('unProductName').productcode = '';
	document.getElementById('unDesciprt').value = '';
	document.getElementById('unRstafferName').value = '';
}

function clearsAll()
{
	clearArray(document.getElementsByName('productName'));
	clearArray(document.getElementsByName('amount'), true);
	clearArray(document.getElementsByName('price'));
	clearArray(document.getElementsByName('value'));
	clearArray(document.getElementsByName('desciprt'));
	clearArray(document.getElementsByName('rstafferName'));
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
		
		if (array[i].productcode)
        {
            array[i].productcode = '';
        }
	}
}

/**
 * oo是在addout里面定义的
 */
function getProductRelation(ox)
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
        
        setObj(oo, ox[0]);
        
        var os = oo.parentNode.parentNode;
        os.cells[2].childNodes[0].title = '当前产品的最大数量:' + ox[0].pamount;
        os.cells[2].childNodes[0].oncheck = 'range(-' + ox[0].pamount + ')';
        os.cells[3].childNodes[0].value = ox[0].pprice;
        os.cells[5].childNodes[0].value = ox[0].pprice;
        os.cells[6].childNodes[0].value =  ox[0].pdepotpartname + '-->' + ox[0].pstaffername;
    }
    
    for(var i = indes; i < ox.length; i++)
    {
        var newTr = addTr();
        
        if (newTr == null)
        {
            break;
        }
        
        var inps = newTr.getElementsByTagName('INPUT');
        
        setObj(inps[0], ox[i]);
        
        inps[1].title = '当前产品的最大数量:' + ox[i].pamount;
        inps[1].oncheck = 'range(-' + ox[i].pamount + ')';
        inps[2].value = ox[i].pprice;
        inps[4].value = ox[i].pprice;
        inps[5].value = ox[i].pdepotpartname + '-->' + ox[i].pstaffername;
    }
}

/**
 * 虚拟产品的选择
 */
function getProductAbs(ox)
{
    if (ox.length <= 0)
    {
        return;
    }
    
    var indes = 0;
        
    if (indes == 0)
    {
        indes = 1;
        
        setObj2(oo, ox[0]);
    }
    
    for(var i = indes; i < ox.length; i++)
    {
        var newTr = addTr();
        
        if (newTr == null)
        {
            break;
        }
        
        var inps = newTr.getElementsByTagName('INPUT');
        
        setObj2(inps[0], ox[i]);
    }
}

function getProduct(oos)
{
	var obj = oos[0];
	
    oo.value = obj.pname;   
    oo.productid = obj.value;    
}

function setObj(src, dest)
{
	src.value = dest.pname;
    src.productcode = dest.pcode;
    
    src.productid = dest.value;
    src.price = dest.pprice;
    src.stafferid = dest.pstafferid;
    src.depotpartid = dest.pdepotpartid;
}

function setObj2(src, dest)
{
    src.value = dest.name;
    src.productcode = dest.code;
    
    src.productid = dest.id;
}

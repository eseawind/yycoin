function addTr()
{
    for (var i = 0; i < 1; i++)
    {
        addTrInner("tables", "trCopy");
    }
}

function addTrInner()
{
	
    var table = $O("tables");
    
    var tr = $O("trCopy");
    
    trow =  table.insertRow(-1);
    
    if (length % 2 == 1)
    {
        trow.className = 'content2';
    }
    else
    {
        trow.className = 'content1';
    }
    
    for (var i = 0; i < tr.cells.length; i++)
    {
        var tcell = document.createElement("td");
        
        tcell.innerHTML = tr.cells[i].innerHTML;
        
        trow.appendChild(tcell);
    }
    
    trow.appendChild(tcell);
    
    return trow;
}

function removeTr(obj)
{
    //rows
    var table = $O("tables");
    
    if (table.rows.length <= 2)
    {
    	alert('不能全部删除');
    	
    	return false;
    }
    
    obj.parentNode.parentNode.removeNode(true);
    
    
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
}

var current;

function selectProduct(obj)
{
    current = obj;
    //具体查询结果要修 改... add 2012.5.24
    window.common.modal('../product/product.do?method=rptQueryProduct&load=1&selectMode=1&abstractType=0&status=0');
}

function getProduct(oos)
{
	var oo = oos[0];
	
    current.value = oo.pname;
    
    var hobj = getNextInput(current.nextSibling);
    
    hobj.value = oo.value;
}

function getNextInput(el)
{
    if (el.tagName && el.tagName.toLowerCase() == 'input')
    {
        return el;
    }
    else
    {
        return getNextInput(el.nextSibling);
    }
}


function compareDate(date1, date2)
{
	var d1 = new Date(date1.replace(/-/g, "/")); 
	var d2 = new Date(date2.replace(/-/g, "/")); 

//	if (Date.parse(d1) - Date.parse(d2) == 0) 
//	{ 
//		window.alert("两个日期相等"); 
//		return false; 
//	} 
//	if (Date.parse(d1) - Date.parse(d2) < 0) { 
//	window.alert("结束日期 大于 开始日期"); 
//	} 
	
	return Date.parse(d1) - Date.parse(d2);
}
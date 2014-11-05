function addTr()
{
    addTrInner("tables", "trCopy");
}

function addStafferTr()
{
    addTrInner("tables_staffer", "trCopy1");
}

function removeTr(obj)
{

	obj.parentNode.parentNode.removeNode(true);    
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

var current1;
function selectStaffer(obj)
{
	current1 = obj;
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{    
	var oo = oos[0];
	
	current1.value = oo.pname;
    
    var hobj = getNextInput(current1.nextSibling);
    
    hobj.value = oo.value;
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

function selectProductType()
{
    window.common.modal('../commission/black.do?method=rptQueryProductType&load=1&selectMode=0');
}

function getProductType(oos)
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
            ids = ids + oos[i].value + ';';
            names = names + oos[i].pname + ';' ;
        }        
    }

    $O('productType').value = ids;
    $O('productTypeName').value = names;
    
}

function clears(i)
{
	if (i == '1')
	{
		$O('productType').value = '';
		$O('productTypeName').value = '';
	}
	else
	{
		$O('industryId').value = '';
		$O('industryIdsName').value = '';
	}
}
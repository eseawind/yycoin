function addTr()
{
    addTrInner("tables", "trCopy");
}

function addVSTr()
{
    addTrInner("tables_VS", "trCopy1");
}

function removeTr(obj)
{

	obj.parentNode.parentNode.removeNode(true);    
}

var current;

function selectProduct(obj)
{
    current = obj;
    
    if ($$('nature') == '')
    {
    	alert('请选择产品性质');
    	return;
    }
    
    var managerType = $$('managerType');
    
    // 如果是管理类型，就赋空，表示查询所有商品，如果是普通（1）则只查普通的商品
    if (managerType == '0')
    {
    	managerType = '';
    }
    
    window.common.modal('../product/product.do?method=rptQueryProduct&load=1&selectMode=1&ctype=${nature}&mtype='+managerType+'&status=0');
}

function getProduct(oos)
{
	var oo = oos[0];
	
    //current.value = oo.pname;
    
    //var hobj = getNextInput(current.nextSibling);
    
    //hobj.value = oo.value;
	$O('refProductId').value = oo.value;
	$O('refProductName').value = oo.pname;
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
var flag;
var current1;
function selectStaffer1(obj)
{
	flag = 0;
	current1 = obj;
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function selectStaffer()
{
	flag = 1;
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{    
	var oo = oos[0];
	
	if (flag == 0)
	{
		current1.value = oo.pname;
	    
	    var hobj = getNextInput(current1.nextSibling);
	    
	    hobj.value = oo.value;
	}
	else
	{
	    $O('productManagerName').value = oo.pname;
	    $O('productManagerId').value = oo.value;
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

function natureChange()
{
	if ($O('nature').value == '1')
	{
		$d('qout_ref');
	}		
	else
	{
		$d('qout_ref', false);
	}
	
	$O('refProductId').value = '';
	$O('refProductName').value = '';
}

function selectPrincipalship()
{
    window.common.modal('../admin/pop.do?method=rptQueryPrincipalship&load=1&selectMode=0');
}

function getPrincipalship(oos)
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

    $O('industryId').value = ids;
    $O('industryIdsName').value = names;
    
}



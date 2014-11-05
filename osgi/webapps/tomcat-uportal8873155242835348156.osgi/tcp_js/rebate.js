var sflag = 0;

function addPayTr()
{
    for (var i = 0; i < 1; i++)
    {
        addTrInner("tables_pay", "trCopy_pay");
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

function calDateInner(obj, name)
{
	var tr = getTrObject(obj);
	
	var el = getInputInTr(tr, name);
	
	return calDate(el)
}

function checks()
{
	var total = $$('total');
    var mels = document.getElementsByName('p_moneys');
    
    var addMoney = 0.0;
    
    for (var i = 0; i < mels.length; i++)
    {
        if (mels[i].value != '')
        {
            addMoney += parseFloat(mels[i].value);
        }
    }
    
    if (compareDouble(addMoney, total) != 0)
    {
        alert('当前入账金额:' + addMoney + '.但是单据申请金额必须是:' + total);
        
        return false;
    }
    
    return true;
}

// 预开票检查
function checkPreInvoices(){
	
	if (parseFloat($$('total'))<=0){
		alert('开票金额须大于0');
		
		return false;
	}
	
	return true;
}

function getCitys(oos)
{
    var obj = oos[0];
    
    cityObj.value = obj.pname;
}

function selectNext(type, value)
{
	sflag = 0;
	
    if (type == 'group')
    {
    	window.common.modal('../group/group.do?method=rptQueryGroupMember&selectMode=1&load=1&pid=' + value);
    }	
}

function getStaffers(oos)
{
    if (oos.length > 0)
    {
        var item = oos[0];
        $("input[name='processer']").val(item.pname);
        $("input[name='processId']").val(item.value);
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
    
    selectNext('group', 'A1201109150003363412')

}

function pagePrint()
{
    window.print();
}



function addTr()
{
	var list = initData();
    for (var i = 0; i < 2; i++)
    {
        addTrInner(list);
    }
}

function initTr()
{
    var list = initData();
    var list2 = initData2();
    var list3 = initData3();
    
    var deList = document.getElementsByName('taxId');
    var staList = document.getElementsByName('stafferId');
    var depList = document.getElementsByName('departmentId');
    
    for (var i = 0; i < deList.length; i++)
    {
       if (deList[i].value == '')
       {
           continue;
       }
       
       new hint(deList[i], list, HINT_MODE.CLIENT, false, null, callClick);
       
       new hint(staList[i], list2, HINT_MODE.CLIENT, false, null, callClickStaffer);
       
       new hint(depList[i], list3, HINT_MODE.CLIENT, false, null, callClickPri);
       
       taxChange(deList[i]);
    }
}

function addTrInner(list)
{
    var table = $O("tables");
    
    var tr = $O("trCopy");
    
    return addTrInner_2(list, tr, table, -1);
}

function addTrInner_2(list, tr, table, index)
{
    trow =  table.insertRow(index);
    
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
    
    new hint(getTrInnerObj2(trow, 'taxId'), list, HINT_MODE.CLIENT, false, null, callClick);
    
    new hint(getTrInnerObj2(trow, 'stafferId'), initData2(), HINT_MODE.CLIENT, false, null, callClickStaffer);
    
    new hint(getTrInnerObj2(trow, 'departmentId'), initData3(), HINT_MODE.CLIENT, false, null, callClickPri);
    
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
    
    if (inChange)
    {
        inChange();
        outChange();
    }
}

function copyTr(obj)
{
    //rows
    var table = $O("tables");
    
    var list = initData();
    
    var table = $O("tables");
    
    var tr = obj.parentNode.parentNode;
    
    var trResult = addTrInner_2(list, tr, table, tr.rowIndex + 1);
    
    if (inChange)
    {
	    inChange();
	    outChange();
    }
    
    return trResult;
}


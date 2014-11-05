function logBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $ajax('../budget/budget.do?method=queryLog&id=' + getRadioValue('checkb'), callBackFunLog);
    }
}

function callBackFunLog(data)
{
    $O('logD').innerHTML = '';
    
    var logs = data.msg;
    
    var htm = '';
    for(var i = 0; i < logs.length; i++)
    {
        var item = logs[i];
        
        var llog = item.logTime + ' / ' + item.stafferName + ' / ' + item.operation + ' / ' +  item.log + '<br>';
        
        htm += llog;
    }
    
    $O('logD').innerHTML = htm;
    
    $.blockUI({ message: $('#logDiv'),css:{width: '50%', left : '25%', top : '15%'}});
}
 
function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryBudget');
}

function doSearch1()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryBudget&name=queryRunBudget');
}

function addBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').status1 == 3 && getRadio('checkb').etype == 0)
    {
        $l('../budget/budget.do?method=preForAddBudget&type=1&parentId=' + getRadioValue('checkb'));
    }
    else
    {
        $error('请选择已经通过的预算且公司预算');
    }
}

function addDepartmentBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').status1 == 3 && getRadio('checkb').etype == 1)
    {
        $l('../budget/budget.do?method=preForAddBudget&type=2&parentId=' + getRadioValue('checkb'));
    }
    else
    {
        $error('请选择已经通过的预算且是事业部预算');
    }
}

function addMonthDepartmentBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') 
        && getRadio('checkb').status1 == 3 
        && getRadio('checkb').llevel == 0 
        && getRadio('checkb').etype == 2)
    {
        $l('../budget/budget.do?method=preForAddBudget&nextLevel=2&type=2&parentId=' + getRadioValue('checkb'));
    }
    else
    {
        $error('请选择已经通过的预算且是部门年度预算');
    }
}

function addRootBean(opr, grid)
{
    $l('../budget/budget.do?method=preForAddBudget&parentId=0&type=0');
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && (getRadio('checkb').status1 == 0 || getRadio('checkb').status1 == 1))
    {
        $l('../budget/budget.do?method=findBudget&update=1&id=' + getRadioValue('checkb'));
    }
    else
    {
        $error();
    }
}

function updateBean2(opr, grid)
{
    $l('../budget/budget.do?method=findBudget&oprType=0&update=2&id=' + getRadioValue('checkb'));
}

function updateBean3(opr, grid)
{
    $l('../budget/budget.do?method=findBudget&oprType=1&update=3&id=' + getRadioValue('checkb'));
}

function doReject()
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').status1 == 2 && getRadio('checkb').etype == 1)
    {
        $.blockUI({ message: $('#rejectReson') ,css:{width: '40%'} });
    }
    else
    {
        $error();
    }
}

function doReject1()
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').status1 == 2 && getRadio('checkb').etype == 0)
    {
        $.blockUI({ message: $('#rejectReson') ,css:{width: '40%'} });
    }
    else
    {
        $error();
    }
}

function doReject2()
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').status1 == 2 && getRadio('checkb').etype == 2)
    {
        $.blockUI({ message: $('#rejectReson') ,css:{width: '40%'} });
    }
    else
    {
        $error();
    }
}

function doPass()
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').status1 == 2 && getRadio('checkb').etype == 1)
    {
        if (window.confirm('确定审批通过预算:' +  getRadio('checkb').lname))
        $ajax('../budget/budget.do?method=auditingBudget&opr=0&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    {
        $error();
    }
}

function doCarry()
{
    if (window.confirm('确定立即生效预算?'))
    {
        $ajax('../budget/budget.do?method=carryBudget', callBackFun);
    }
    else
    {
        $error();
    }
}

function doPass1()
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').status1 == 2 && getRadio('checkb').etype == 0)
    {
        if (window.confirm('确定审批通过预算:' +  getRadio('checkb').lname))
        $ajax('../budget/budget.do?method=auditingBudget&opr=0&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    {
        $error();
    }
}

function doPass2()
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').status1 == 2 && getRadio('checkb').etype == 2)
    {
        if (window.confirm('确定审批通过部门预算:' +  getRadio('checkb').lname))
        $ajax('../budget/budget.do?method=auditingBudget&opr=0&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    {
        $error();
    }
}

function delBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('确定删除--' + getRadio('checkb').lname))
        {
            $ajax('../budget/budget.do?method=delBudget&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
    else
    {
        $error();
    }
}

function subBean()
{
	if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').status1 == 3)
    {
        window.common.modal('../budget/budget.do?method=queryReference&pid=' + getRadioValue('checkb'));
    }
    else
    {
        $error();
    }
}

function $cancle()
{
    $.unblockUI();
}

function $ok()
{
    if (eCheck([$O('preason')]))
    {
        $ajax2('../budget/budget.do?method=auditingBudget&opr=1&id=' + getRadioValue('checkb'), {reason: $$('preason')}, callBackFun);
        
        $.unblockUI();
        
        $O('preason').value = '';
    }
}
var gobal_opr = 0;

function doRejectApply(opr)
{
    $.blockUI({ message: $('#rejectReson') ,css:{width: '40%'} });
}

function doPassApply(opr)
{
    if (window.confirm('核准预算变更:' +  getRadio('checkb').lname))
        $ajax('../budget/budget.do?method=auditingBudgetApply&opr=0&id=' + getRadioValue('checkb'), callBackFun);
}


function $cancle()
{
    $.unblockUI();
}

function $ok()
{
    if (eCheck([$O('preason')]))
    {
        $ajax2('../budget/budget.do?method=auditingBudgetApply&opr=1&id=' + getRadioValue('checkb'), {'reason' : $$('preason')}, callBackFun);
        
        $.unblockUI();
        
        $O('preason').value = '';
    }
}

function cfo_pass()
{
	if ($c() && getRadio('checkb').status1 == 2)
	doPass(0);
	else
	$error();
}

function cfo_reject()
{
	if ($c() && getRadio('checkb').status1 == 2)
    doReject(0);
    else
    $error();
    
}

function coo_pass()
{
    if ($c() && getRadio('checkb').status1 == 3)
    doPass(1);
    else
    $error();
}

function coo_reject()
{
    if ($c() && getRadio('checkb').status1 == 3)
    doReject(1);
    else
    $error();
}

function ceo_pass()
{
    if ($c() && getRadio('checkb').status1 == 4)
    doPass(2);
    else
    $error();
}

function ceo_reject()
{
    if ($c() && getRadio('checkb').status1 == 4)
    doReject(2);
    else
    $error();
}


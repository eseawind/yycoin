function calDateInner(obj, name)
{
	var tr = getTrObject(obj);
	
	var el = getInputInTr(tr, name);
	
	return calDate(el)
}

function taskDetail(taskId, productId)
{
	window.common.modal("../customerService/feedback.do?method=rptQueryTaskDetail&taskId="+taskId+"&productId="+productId);
}

function selectReciever()
{
	var taskId = $$('taskId');
	
	window.common.modal("../customerService/feedback.do?method=rptQueryOutReceiver&taskId="+taskId+"&load=1&selectMode=1");
}

function getReciever(oos)
{
	 var oo = oos[0];

	 $O('contact').value = oo.preceiver;
     $O('contactPhone').value = oo.pmobile;
     
}

function copyValue()
{
	 var receives = document.getElementsByName("p_receive");
	 var receiptTimes = document.getElementsByName('p_receiptTime');
	 var ifHasExceptions = document.getElementsByName('p_ifHasException');
	 var exceptionAmounts = document.getElementsByName("p_exceptionAmount");
	 var exceptionTypes = document.getElementsByName('p_exceptionType');
	 var exceptionTexts = document.getElementsByName('p_exceptionText');
	 
	 
	 
	 
	 var receive = receives[0].value;
	 var receiptTime = receiptTimes[0].value;
	 var ifHasException = ifHasExceptions[0].value;
	 var exceptionAmount = exceptionAmounts[0].value;
	 var exceptionType = exceptionTypes[0].value;
	 var exceptionText = exceptionTexts[0].value;
	 
	 if (receives.length > 1)
	 {
		 for (var i=1; i < receives.length; i++)
		 {
			 receives[i].value = receive;
			 receiptTimes[i].value = receiptTime;
			 ifHasExceptions[i].value = ifHasException;
			 exceptionAmounts[i].value = exceptionAmount;
			 exceptionTypes[i].value = exceptionType;
			 exceptionTexts[i].value = exceptionText;
		 }
	 }
}

function visitCheck()
{
	var hasException = false;

	 var receives = document.getElementsByName("p_receive");
	 var receiptTimes = document.getElementsByName('p_receiptTime');
	 var ifHasExceptions = document.getElementsByName('p_ifHasException');
	 var exceptionAmounts = document.getElementsByName("p_exceptionAmount");
	 var exceptionTypes = document.getElementsByName('p_exceptionType');
	 var exceptionTexts = document.getElementsByName('p_exceptionText');

	 for (var i=0; i < receives.length; i++)
	 {
		 // 已收
		 if (receives[i].value == 1)
		 {
			 // 收货时间不能为空
			 if (receiptTimes[i].value=='')
			 {
				 alert('已收货时收货时间不能为空');
				 $f(receiptTimes[i]);
				 
				 return false;
			 }
		 }
		 else
		 {
			 // 收货时间不能为空
			 if (receiptTimes[i].value!='')
			 {
				 alert('未收货时收货时间不能有值');
				 $f(receiptTimes[i]);
				 
				 return false;
			 }
		 }

		 // 有异常
		 if (ifHasExceptions[i].value == 1)
		 {
			 hasException = true;
			 
			 if (exceptionTypes[i].value=='')
			 {
				 alert('有异常时异常类型不能为空');
				 $f(exceptionTypes[i]);
				 
				 return false;
			 }

			 if (exceptionTexts[i].value=='')
			 {
				 alert('有异常时异常描述不能为空');
				 $f(exceptionTexts[i]);
				 
				 return false;
			 }
		 }
		 else
		 {
			 if (exceptionAmounts[i].value!=0)
			 {
				 alert('无异常时异常数量为0');
				 $f(exceptionAmounts[i]);
				 
				 return false;
			 }
			 
			 if (exceptionTypes[i].value!='')
			 {
				 alert('无异常时异常类型须为空');
				 $f(exceptionTypes[i]);
				 
				 return false;
			 }

			 if (exceptionTexts[i].value!='')
			 {
				 alert('无异常时异常描述须为空');
				 $f(exceptionTexts[i]);
				 
				 return false;
			 }
		 }
	 }

	 // 有异常须填写异常处理 人
	if (hasException)
	{
		if ($$('exceptionProcesser') == '')
		{
			alert('有异常时须选择异常处理人');
			return false;
		}
	}

	if ($$('ifHasContact') == 1)
	{
		if ($$('contact')=='')
		{
			alert('联系人须填写');
			return false;
		}

		if ($$('contactPhone')=='')
		{
			alert('联系电话须填写');
			return false;
		}

		if ($$('planReplyDate')=='')
		{
			alert('计划回复时间须填写');
			return false;
		}
	}
	
	return true;
}

function clears(type)
{
	if (type == 0)
		$O('exceptRef').value = '';
	else
	{
		$O("exceptionProcesser").value = '';
		$O("exceptionProcesserName").value = '';
	}
}

var flag = 0;
function selectStaffer()
{   
	flag = 0;
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&excludeQuit=1&load=1&selectMode=1');
}

function getStaffers(oos)
{
	if (flag == 0)
	{
		var oo = oos[0];

		 $O('exceptionProcesserName').value = oo.pname;
	     $O('exceptionProcesser').value = oo.value;
	}
	else
	{
		for (var i = 0; i < oos.length; i++)
	    {
	    	var pv = $O('exceptRef').value;
	    	
	    	if (pv && pv.indexOf(oos[i].value) != -1)
			{
	    		continue;
			}
	    	else
	    	{
	    		$O('exceptRef').value = $O('exceptRef').value + ',' + oos[i].pname ;
	    	}   
	    }
		
		if ($$('exceptRef').substr(0,1) == ',')
		{
			$O('exceptRef').value = $$('exceptRef').substr(1, $$('exceptRef').length - 1)
		}

		if ($$('exceptRef').substr($$('exceptRef').length - 1) == ',')
		{
			$O('exceptRef').value = $$('exceptRef').substr(0, $$('exceptRef').length - 1)
		}
	}
}

function selectStaffer1()
{
	flag = 1;
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=0');
}


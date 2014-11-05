锘�/var BaseUrl = "http://127.0.0.1/soaaspcode/";

//闅愯棌鎴栨樉绀轰慨璁㈢棔杩�
function soaShowTrack(value) 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		formData.SOAOfficeCtrl.ShowRevisions = value;
} 
//鎺ュ彈鎵�湁淇锛屾竻闄ょ棔杩�
function soaAcceptAllRevisions() 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		formData.SOAOfficeCtrl.AcceptAllRevisions();
}
//鑾峰彇骞舵樉绀烘墍鏈夌棔杩�
function GetAllRevisions()
{
  //var i;
  //var str="";
  //for (i = 1;i <=formData.SOAOfficeCtrl.Document.Revisions.Count;i++)
  //{
  //  str=str + formData.SOAOfficeCtrl.Document.Revisions.Item(i).Author;
  //  if (formData.SOAOfficeCtrl.Document.Revisions.Item(i).Type=="1")
  //	{ 
  //  	str=str + ' 鎻掑叆锛�+formData.SOAOfficeCtrl.Document.Revisions.Item(i).Range.Text+"\r\n";
  //  }
  //	else
  //	{
  //  	str=str + ' 鍒犻櫎锛�+formData.SOAOfficeCtrl.Document.Revisions.Item(i).Range.Text+"\r\n";
  //  }
  //}
  //alert("褰撳墠鏂囨。鐨勬墍鏈変慨鏀圭棔杩瑰涓嬶細\r\n"+str);

	alert("缁胯壊鐗堟病鏈夋彁渚涙鍔熻兘銆�);
}
//鎻掑叆鏈湴鍗扮珷
function soaInsertLocalSeal() 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		alert("缁胯壊鐗堟病鏈夋彁渚涙彃鍏ュ嵃绔犵殑鍔熻兘銆�);//formData.SOAOfficeCtrl.InsertSealFromLocal();
}
//鎻掑叆鎵嬪啓绛惧悕
function soaInsertSignature() 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
	{
		 alert("缁胯壊鐗堜笉鎻愪緵鎵嬪啓绛惧悕鍔熻兘銆�);
	}
}
//鍏ㄦ枃鎵嬪啓鎵规敞
function soaStartHandDraw() 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		{
		  alert("缁胯壊鐗堜笉鎻愪緵鎵嬪啓鎵规敞鍔熻兘銆�);
		}
} 
//鍒嗗眰鏄剧ず鍏ㄦ枃鎵嬪啓鎵规敞
function soaShowHandDrawDispBar() 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		{
		  alert("缁胯壊鐗堜笉鎻愪緵鍒嗗眰鏄剧ず鎵嬪啓鎵规敞鍔熻兘銆�);
		}
} 
//缁欐枃妗ｆ坊鍔犳暟瀛楃鍚�
function soaAddDigitalSignature() 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		 alert("缁胯壊鐗堜笉鎻愪緵娣诲姞鏁板瓧绛惧悕鍔熻兘銆�);
} 
//鎻掑叆鐢靛瓙鍗扮珷
function soaInsertSeal() 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
	{
		alert("缁胯壊鐗堜笉鎻愪緵鎻掑叆鍗扮珷鍔熻兘銆�);
	}
} 
//楠岃瘉鐢靛瓙鍗扮珷/绛惧悕鐨勬湁鏁堟�
function soaValidateSeal() 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		alert("缁胯壊鐗堜笉鎻愪緵鎻掑叆鍗扮珷鍔熻兘銆�);
} 
//鍏佽鎴栫姝�澶嶅埗/鎷疯礉
function soaCanCopy(value) 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		formData.SOAOfficeCtrl.CanCopy = value;	
	if (value)
		alert("宸插厑璁告嫹璐濓紒");
	else	
		alert("宸茬姝㈡嫹璐濓紒");
} 
//鎻掑叆Web鍥剧墖
function InsertWebImage() 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		alert("缁胯壊鐗堟病鏈夋彁渚涙鍔熻兘銆�);//formData.SOAOfficeCtrl.InsertWebImage(BaseUrl + "images/SOA_05.gif");
}

//椤甸潰璁剧疆
function DocPageSetup() 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		formData.SOAOfficeCtrl.ShowDialog(5);
}
//鍒囨崲鏍囬鏍�
function ToggleTitlebar() 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		formData.SOAOfficeCtrl.Titlebar = !formData.SOAOfficeCtrl.Titlebar;
}
//鍒囨崲鑿滃崟鏍�
function ToggleMenubar() 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		formData.SOAOfficeCtrl.Menubar = !formData.SOAOfficeCtrl.Menubar;
}
//鍒囨崲宸ュ叿鏍�
function ToggleToolbars() 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		formData.SOAOfficeCtrl.Toolbars = !formData.SOAOfficeCtrl.Toolbars;
}
//绂佹/鍏佽 鎵撳嵃鏂囨。鑿滃崟鍙婃寜閽�
function EnablePrint(value) 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		formData.SOAOfficeCtrl.EnableFileCommand(5) = value;
}
//绂佹/鍏佽 淇濆瓨鏂囨。鑿滃崟鍙婃寜閽�
function EnableSave(value) 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		formData.SOAOfficeCtrl.EnableFileCommand(3) = value;
}
//绂佹/鍏佽 鍙﹀瓨鏂囨。鑿滃崟鍙婃寜閽�
function EnableSaveAs(value) 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		formData.SOAOfficeCtrl.EnableFileCommand(4) = value;
}
//淇濆瓨鏂囨。鍒皐eb鏈嶅姟鍣�
function soaSave() 
{ 
	try {
		if (!bDocOpen)
			alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
		else
			formData.SOAOfficeCtrl.WebSave();//淇濆瓨褰撳墠鏂囨。鍒皐eb鏈嶅姟鍣紝淇濆瓨锛堣鐩栵級鍒板師鎵撳紑鏂囨。鐨勫湴鍧�
		//formData.SOAOfficeCtrl.WebSave("aa.doc");琛ㄧず鎶婂綋鍓嶆枃妗ｅ彟瀛樺埌web鏈嶅姟鍣ㄤ负aa.doc
	} 
	catch (e) 
	{ 
		alert("鏂囨。淇濆瓨澶辫触!\n閿欒淇℃伅:" + e.message); 
	} 
}
//淇濆瓨鏂囨。鍒皐eb鏈嶅姟鍣紝浣跨敤椤甸潰鎻愪氦鎶�湳锛屽湪鎻愪氦鏂囨。鐨勫悓鏃舵彁浜ゅ叾浠栫敤鎴峰畾涔夌殑椤甸潰瀛楁鎴栧煙
function soaSubmitSave() 
{ 
	try {
		if (!bDocOpen)
			alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
		else
		{
			formData.SOAOfficeCtrl.WebSave();
			formData.submit();
		}
	} 
	catch (e) 
	{ 
		alert("鏂囨。淇濆瓨澶辫触!\n閿欒淇℃伅:" + e.message); 
	} 
}
//鎵撳紑鎻掑叆鏈湴鍥剧墖鐨勫璇濇
function OpenImageDialog() 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		alert("缁胯壊鐗堟病鏈夋彁渚涙鍔熻兘銆�);//formData.SOAOfficeCtrl.OpenImageDialog();
} 
//鑾峰彇鏂囨。Txt姝ｆ枃
function WordToText()
{
	alert("缁胯壊鐗堟病鏈夋彁渚涙鍔熻兘.");
    //alert(formData.SOAOfficeCtrl.DocText);
}
//VBA濂楃孩锛屽鐢╒BA缂栫▼妯℃澘
function InsertVBATemplate()
{
	//var DocObject=formData.SOAOfficeCtrl.Document;
	//var myl=DocObject.Shapes.AddLine(91,60,285,60)
	//myl.Line.ForeColor=255;
	//myl.Line.Weight=2;
	//var myl1=DocObject.Shapes.AddLine(308,60,502,60)
	//myl1.Line.ForeColor=255;
	//myl1.Line.Weight=2;

   	//var myRange=DocObject.Range(0,0);
	//myRange.Select();

	//var mtext="鈽�;
	//DocObject.Application.Selection.Range.InsertAfter (mtext+"\n");
   	//var myRange=DocObject.Paragraphs(1).Range;
   	//myRange.ParagraphFormat.LineSpacingRule =1.5;
   	//myRange.font.ColorIndex=6;
   	//myRange.ParagraphFormat.Alignment=1;
   	//myRange=DocObject.Range(0,0);
	//myRange.Select();
	//mtext="甯傛斂鍙慬2005]0168鍙�;
	//DocObject.Application.Selection.Range.InsertAfter (mtext+"\n");
	//myRange=DocObject.Paragraphs(1).Range;
	//myRange.ParagraphFormat.LineSpacingRule =1.5;
	//myRange.ParagraphFormat.Alignment=1;
	//myRange.font.ColorIndex=1;
	
	//mtext="鏌愬競鏀垮簻绾㈠ご鏂囦欢";
	//DocObject.Application.Selection.Range.InsertAfter (mtext+"\n");
	//myRange=DocObject.Paragraphs(1).Range;
	//myRange.ParagraphFormat.LineSpacingRule =1.5;
	
	//myRange.Font.ColorIndex=6;
	//myRange.Font.Name="浠垮畫_GB2312";
	//myRange.font.Bold=true;
	//myRange.Font.Size=30;
	//myRange.ParagraphFormat.Alignment=1;
	alert("缁胯壊鐗堜笉鏀寔VBA鎺ュ彛.")

}
//浣跨敤鎸囧畾鐨勬ā鏉垮绾�
function ApplyFileTemplate() 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
	{
		alert("缁胯壊鐗堟病鏈夋鍔熻兘");
		//var mDialogUrl = "images/selectTemplate.htm";
		//var mObject = new Object();
		//mObject.SelectValue = "";
		//window.showModalDialog(mDialogUrl, mObject, "dialogHeight:180px; dialogWidth:340px;center:yes;scroll:no;status:no;"); 
		//鍒ゆ柇鐢ㄦ埛鏄惁閫夋嫨绛惧悕
		//if (mObject.SelectValue!="")
		//{
		//	formData.SOAOfficeCtrl.ApplyTemplateFromURL(BaseUrl + "doc/" + mObject.SelectValue);
		//}
	}
} 
//绂佹/鍏佽WORD榧犳爣鎷栨洺鍔熻兘
function DisableDragAndDrop() 
{ 
	if (!bDocOpen)
		alert("褰撳墠娌℃湁宸叉墦寮�殑鏂囨。銆�);
	else
		alert("缁胯壊鐗堜笉鏀寔姝ゅ姛鑳姐�")
		//formData.SOAOfficeCtrl.Document.Application.Options.AllowDragAndDrop = !formData.SOAOfficeCtrl.Document.Application.Options.AllowDragAndDrop;
} 
//灏唄tml form鐨勫煙鍊兼嫹璐濆埌Word鏂囨。鐨勬爣绛句腑
function CopyTextToBookMark(inputname,BookMarkName)
{
	try
	{	
		var inputValue="";
		var j,elObj,optionItem;
		var elObj = document.forms[0].elements(inputname);		 
		if (!elObj)
		{
			alert("HTML鐨凢ORM涓病鏈夋杈撳叆鍩燂細"+ inputname);
			return;
		}
		switch(elObj.type)
		{
				case "select-one":
					inputValue = elObj.options[elObj.selectedIndex].text;
					break;
				case "select-multiple":
					var isFirst = true;
					for(j=0;j<elObj.options.length;j++)
					{
						optionItem = elObj.options[j];					
						if (optionItem.selected)
						{
							if(isFirst)
							{
								inputValue = optionItem.text;
								isFirst = false;
							}
							else
							{
								inputValue += "  " + optionItem.text;
							}
						}
					}
					
					break;
				default: // text,Areatext,selecte-one,password,submit,etc.
					inputValue = elObj.value;
					break;
		}
		var bkmkObj = formData.SOAOfficeCtrl.Document.BookMarks(BookMarkName);	
		if(!bkmkObj)
		{
			alert("Word 妯℃澘涓笉瀛樺湪鍚嶇О涓猴細\""+BookMarkName+"\"鐨勪功绛撅紒");
		}
		var saverange = bkmkObj.Range
		saverange.Text = inputValue;
		formData.SOAOfficeCtrl.Document.Bookmarks.Add(BookMarkName,saverange);
	}
	catch(err){
		
	}
	finally{
	}		
}
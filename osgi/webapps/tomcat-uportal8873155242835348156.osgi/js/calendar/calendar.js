/*00:00:00 ~ 23:59:59*/
function validTime(yourTime){
	
    var array = yourTime.split(":");    
    if(array.length != 3)
    {        
        return false ;
    }     
    mytime = pad(array[0],2) + ':' + pad(array[1],2) + ':' + pad(array[2],2)
    
    validMyTime1 = /^2[0-3]:[0-5][0-9]:[0-5][0-9]$/ ;
    validMyTime2 = /^[0-1][0-9]:[0-5][0-9]:[0-5][0-9]$/ ;
    
    return validMyTime1.test(mytime) || validMyTime2.test(mytime); 
}

var moy = new Array(
  '一月','二月','三月',
  '四月','五月','六月','七月',
  '八月','九月','十月',
  '十一月','十二月'
);

var daysOfMonth = new Array(
  31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
);

var daysOfMonthLY = new Array(
  31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
);

var dow = new Array('星期日','星期一','星期二','星期三','星期四','星期五','星期六',
                    '星期日','星期一','星期二','星期三','星期四','星期五');

var size = 'width="35" height="22" align="center"';
var border = 'border="1"';

var CalendarMonth;
var CalendarYear;
var CalendarDay;

var mytime = '00:00:00';

var oldColor;
var oldBgColor;

function y2k(num) {
  return (num < 1000) ? num + 1900 : num;
}

function padout(num) {
  return (num < 10) ? '0' + num : num;
}

function updateClock() {
	
  var time = new Date();

  var m = padout(time.getMinutes());
  var h = padout(time.getHours());
  var s = padout(time.getSeconds());

  mytime = h + ':' + m + ':' + s;  
}

function isLeapYear(num) {
  if (((num % 4 == 0) && (num % 100 != 0)) || (num % 400 == 0))
    return true;
  return false;
}

function CalendarSelect(Month,Year, offset) {
  
  if (offset == null) offset = 0;

  if (window.changeMonth) { }
  else {
    alert('A changeMonth() function has not been defined');
    return '';
  }

  if (window.changeYear) { }
  else {
    alert('A changeYear() function has not been defined');
    return '';
  }

  if (window.changeDay) { }
  else {
    alert('A changeDay() function has not been defined');
    return '';
  }

  var output = '';

  output += '<form name="Cal">';
  output += CalendarHead(Month,Year,true);
  output += CalendarMonthBody(Month,Year,offset);
  output += '</form>';
  return output;
}

function CalendarHead(Month,Year,Select) {
	
  updateClock()
  var output = '';

  output +=
    '<table cellspacing="0" class="cal" align="center">' +
    '<tr><td align="left" width="100%" class="head">' +
    moy[Month-1] + ' ' + Year +
    '</td>';
    
  if (Select) {
    
    output += '<td width="50%" align="right">' +
    '<select name="Month" onChange="CalMonth()">';

    for (var month=1; month<=12; month++) {
      output += '<option value="' + month + '"';
      if (month == Month) output += ' selected';
      output += '>' + moy[month-1] + '</option>';
    }

    output += '</select>' +
      '<select name="Year" onChange="CalYear();">';

    for (var year=1900; year<=2100; year++) {
      output += '<option value="' + year + '"';
      if (year == Year) output += ' selected';
      output += '>' + year + '</option>';
    }

    output += '</select>';
    
    //output += '<input type="text" name="getTime" size = "7" value = '+ mytime + '>';
    output += '<input type="text" name="getTime" maxlength="8" size = "7" value = '+ mytime +'>';    
  }
  
  output += '</td></tr></table>';

  return output;
}

function cleanall()
{	
	returnValue = "";
	self.close();	
}

function CalendarMonthBody(M,Y,offset) {
  M--;
  if (offset == null) offset = 0;

  firstDay = new Date(Y,M,1);
  startDay = firstDay.getDay();

  if (startDay < offset) startDay += 7;

  var days = daysOfMonth;
  if (isLeapYear(Y)) days = daysOfMonthLY;

  var output = '';

  output +=
    '<table ' + border + ' cellpadding="0" cellspacing="0" class="cal" align="center" ><tr>';

  for (var i=0; i<7; i++)
    output += '<td ' + size + 'onclick="onClick1()"' + ' class="days">' +
      dow[i + offset] + '</td>';

  output += '</tr><tr>';

  var column = 0;
  var lastM = M - 1;
  if (lastM == -1) lastM = 11;

  for (var i=0+offset; i<startDay; i++, column++)
    output += '<td ' + size + 'onclick="onClick1()"' + ' class="grey">' +
      (days[lastM]-startDay+i+1) + '</td>';

  for (var i=1; i<=days[M]; i++, column++) {
    var style = ' class="links"';
    if (day == i && month == M+1 && year == Y)
      style = ' class="today"';
    if (window.changeDay)
      output += '<td align=center ' + size  
      	+ ' ondblclick="onDblClick(' + i + ',' + (M + 1) + ',' + Y + ');"'
      	+ ' onclick="setYMD(' + Y + ',' + (M + 1) + ',' + i + ');"'
      	+ ' onfocus="onFocus();"' 
      	+ ' onblur="onBlur();"' + '>' 
      	+ i + '</td>';
    else
      output += '<td ' + size + style + '>' + i + '</td>';

    if (column == 6) {
      output += '</tr><tr>';
      column = -1;
    }
  }

  if (column > 0) {
    for (var i=1; column<7; i++, column++)
      output += '<td ' + size + 'onclick="onClick2()"' + ' class="grey">' + i + '</td>';
  }

  output += '</tr></table>';
  
  	output += '<table width="100%" border="0">';
    output += '<tr>';
    output += '<td width="10%">'; 
    output += '</td>';
    output += '<td width="30%">'; 
    output += '<img src="queren.gif" width="55" height="19" style="cursor:hand" onClick = "pSubmit()">';
    output += '</td>';
    output += '<td width="30%">'; 
    output += '<img src="chongzhi.gif" width="55" height="19" style="cursor:hand" onClick = "cleanall()">';
    output += '</td>';
	output += '<td width="30%">'; 
    output += '<img src="fanhui.gif" width="55" height="19" style="cursor:hand" onClick = "self.close()">';
    output += '</td>';
  	output += '</tr>';
	output += '</table>';
	
  return output;
}

function pSubmit()
{
	CalDay(day, month, year);
	self.close();
}

function setYMD(Y, M, D)
{
	year = Y;
	mont = M;
	day = D;
}

function getAnOptionValue(what) {
  return what.options[what.options.selectedIndex].value;
}

function CalMonth() {
	
  CalendarMonth = getAnOptionValue(document.Cal.Month) - 0;
  changeMonth(CalendarMonth);
}

function CalYear() {
  CalendarYear = getAnOptionValue(document.Cal.Year) - 0;
  changeYear(CalendarYear);
}

function pad(num, size) {
  num = '' + num;
  while (num.length < size) num = '0' + num;
  return num;
}

function CalDay(day,month,year) 
{  
  CalendarDay = day;
  CalendarMonth = month;
  CalendarYear = year;
  var input =document.all.tags("input");
  for(var i=0; i<input.length; i++)
  {
	if(input[i].name=="getTime")
	{
     		mytime = input[i].value;
	}
  }
  
  if(validTime(mytime) == false)
  {
  	alert('Time error ,please checek it!');  	
  }
  else
  {  
  	returnValue = year + "-" + pad(month,2) + "-" + pad(day,2) + " " + mytime;	
  	//alert(returnValue) ;
  	changeDay(CalendarDay,CalendarMonth,CalendarYear);
  }
}


function changeMonth(CalendarMonth) {
  	month = CalendarMonth + '';
  	createPage();
}

function changeYear(CalendarYear) {
  	year = CalendarYear + '';
	createPage();
}

function changeDay(day,month,year) {
  day = CalendarDay + '';
}

function createPage()
{
	if (window.CalendarSelect)
	{
  		document.all.item("calender").innerHTML = CalendarSelect(month,year,0);
  	}
}

/*
var today = new Date();
var day   = today.getDate();
var month = today.getMonth() + 1;
var year  = y2k(today.getYear());
var defaultDate = window.dialogArguments;
*/
var today = new Date();
var day;   
var month; 
var year;  
var defaultDate = window.dialogArguments;

//alert(defaultDate);

if(defaultDate != null && defaultDate != "")
{
	year   = defaultDate.substring(0,4) - 0;
	//alert(year);
	month = defaultDate.substring(5,7) - 0;
	//alert(month);
	day  = defaultDate.substring(8,10) - 0;
	//alert(day);    
}
else
{	
    day   = today.getDate();
    month = today.getMonth() + 1;
    year  = y2k(today.getYear());    
}

function onClick(i,M,Y)
{	
	//CalDay(i,M,Y);
}

function onClick1()
{	
	if (month != 1)
	{
		month --;
		changeMonth(month);
	}
	else
	{	
		month = 12;
		year --;
		changeMonth(month);
		changeYear(year);
	}
}

function onClick2()
{
	if (month != 12)
	{
		month ++;		
		changeMonth(month);		
	}
	else
	{
		month = 1;
		year ++;
		changeMonth(month);
		changeYear(year);
	}
}

function onDblClick(i,M,Y)
{
	CalDay(i,M,Y);
	self.close();
}

function onFocus()
{
	oldColor = window.event.srcElement.style.color;
	oldBgColor = window.event.srcElement.style.backgroundColor;
	window.event.srcElement.style.color = '#FF0000';
	window.event.srcElement.style.backgroundColor = 'yellow';
}

function onBlur()
{
	window.event.srcElement.style.color = oldColor;
	window.event.srcElement.style.backgroundColor = oldBgColor;
}

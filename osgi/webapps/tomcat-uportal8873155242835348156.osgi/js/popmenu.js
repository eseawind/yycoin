// Modifier notes(2008-06-23):
// This js comes form www, we are only learning , testing and modifying this js,but we do not have copyright,
// If your want to possess copyright,please connect author
// Also we may mdoify js, but this js is already not belong to us.
//-------------------------------------------------------------
// Copyright 1999-2006 eSolutions Limited
// Obtain written permission before using any part of this code from info@esol-group.com.

// Support: IE6, NS7.0, NS7.1, Safari, Mozilla
//   In order to support NS7.0 the Parent of menu should be a absolute DIV
// Not support: MAC IE

/*******************************************************************************
  PopMenu class
*******************************************************************************/

var intervalID;
var runningID =0
var popmenu_clicked = false // kludge to prevent click bubbling to top level

function PopMenu(parentId,MenuArray,cell_w,cell_h,cell_p,border){

	if (typeof cell_w == 'undefined' || cell_w==null) cell_w=200
	if (typeof cell_h == 'undefined' || cell_h==null) cell_h=26
	if (typeof cell_p == 'undefined' || cell_p==null) cell_p=8
	if (typeof border == 'undefined' || border==null) border=1

  this.cellWidth = cell_w
  this.cellHeight = cell_h
  this.cellPadding = cell_p
  this.border = border
  this.borderImage = 'popmenu_border.gif'
  this.spacerImage = '/sys/1x1.gif' // '/sys/1x1.gif'

	/*****************************************************************************
	 * Create and return menu item
	 * Style className used
	 *   menu0  - 1st level, no child
	 *   menu0c - 1st level, have child
	 *   menu1  - 2nd+ level, no child
	 *   menu1c - 2nd+ level, have child
	 *   ??????_on - same 4 styles above in mouseover state
	 ****************************************************************************/
	this.createItem = function(menu_level,num,DivData,x,y,w,h,padding,haveChild,isLast){
		var tempObj = document.createElement('DIV')
    tempObj.className = 'menu' + (menu_level==0?'0':'1') + (haveChild?'c':'')
		tempObj.offclass = tempObj.className
		tempObj.onclass = tempObj.offclass + "_on"
    tempObj.id = "menuItem_" + runningID
    runningID = parseInt(runningID) + 1

		var tempArray = (''+DivData).split('|')

		//Set left padding and menu title
		tempObj.innerHTML = '<img src="'+this.spacerImage+'" align=absmiddle height='+(h-1)+' width='+padding+'>' + tempArray[0]

		//storing debugging msg, not neccessary
		//tempObj.value = tempArray[0]

		if (menu_level==0) {
		  h = h - 3 // Top level Menu's height is modified to fit the outer Image
		  tempObj.innerHTML = '<img src="'+this.spacerImage+'" align=absmiddle height='+(h-1)+' width='+padding+'>' + "<font class=menu0Linkoff>" + tempArray[0] + "</font>"
			tempObj.style.width = w
			tempObj.style.height = h-1
  		if(tempArray.length>2 && tempArray[2]!='')
    			tempObj.style.width = parseInt(tempArray[2])
		}else{
			tempObj.style.position='absolute'
			tempObj.style.width = w
			tempObj.style.left = 0
			tempObj.style.top = num*(h-1)
			tempObj.style.zIndex = 1000- menu_level * (10)
  		if(tempArray.length>2 && tempArray[2]!='')
  			tempObj.style.width = parseInt(tempArray[2])
			if (this.border > 0) { //creating borders by "menu_border.gif" and "style"
				tempObj.style.borderLeft='1px solid #FFFFFF'
				tempObj.style.borderRight='1px solid #FFFFFF'
				tempObj.style.borderTop='1px solid #FFFFFF'
				if (isLast) {
					tempObj.style.borderBottom='1px solid #FFFFFF'
				}
			}
		}

		// setting opacity
		if (op){
		  if( menu_level!=0){
		    if (document.all){
            tempObj.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity=" + op + ")"
        }
        else{
            tempObj.style.MozOpacity=op/100
        }
      }
    }

		// If a hyperlink is specified, set the onclick event
		if ((tempArray[1]!="") && (tempArray[1]!=null)) {

      if(typeof tempObj.style == 'undefined')
        tempObj.style.cursor = new Object()

    	var agent = navigator.userAgent.toLowerCase()
    	var is_ie5 = (agent.indexOf('msie 5')!=-1)
    	tempObj.style.cursor = is_ie5 ? 'hand' : 'pointer'

			if ((tempArray[1].toLowerCase()).indexOf('javascript:')==-1){
				tempObj.onclick = function() {
            if(popmenu_clicked) return
            popmenu_clicked = true
						window.location = tempArray[1]
					}
			}else{
				tempObj.onclick = function() {
            if(popmenu_clicked) return
            popmenu_clicked = true
						eval(tempArray[1])
					}
			}
		}else{

		  // => Parent get's onclick instead, so cancel bubble.
      tempObj.onclick = function(){
        popmenu_clicked = true
		  }

		}

		// mouseover change the style
		tempObj.onmouseover = function() {
					//msg.appendMessage("mouseover:" + this.value);
					tempObj.className = tempObj.onclass

		      if (menu_level==0){
		        if (tempObj.getElementsByTagName("FONT").length > 0)
		          tempObj.getElementsByTagName("FONT")[0].className = "menu0Linkon"
            popmenu_clicked = false // see note above
		      }

			    if (tempObj.lastChild.tagName=='DIV'){

					    // disable the filter first
    		      if(menu_level!=0){
    		         if (document.all){
                    tempObj.style.filter = ""
                 }
              }

					    // browser width check
				      var bodyWidth;
				      var scrollX;

              var agent = navigator.userAgent.toLowerCase();

              if (agent.indexOf("mac") != -1){
                  bodyWidth = document.getElementsByTagName("HTML")[0].offsetWidth;
		              scrollX = document.getElementsByTagName("HTML")[0].scrollLeft;
              }
              else {
                  bodyWidth = document.body.clientWidth;
		              scrollX = document.body.scrollLeft;
		          }

              var thisScreenWidth = parseInt(bodyWidth) + parseInt(scrollX)
              var right ;
              if ((parseInt(thisScreenWidth)/2 < parseInt(tempObj.lastChild.offsetLeft)) == true){
                right = parseInt(tempObj.lastChild.offsetLeft) + parseInt(tempObj.offsetWidth) + w*3+  parseInt(tempObj.style.width);
                tempObj.objAlign = 'left'
              }
              else {
                right = parseInt(tempObj.lastChild.offsetLeft) + parseInt(tempObj.offsetWidth)*3;
                tempObj.objAlign = 'right'
              }

  	          // reposition menu
              if (menu_level==0) {
						    tempObj.lastChild.style.top=-1
                tempObj.lastChild.style.left = 0
                if (tempObj.objAlign == 'left'){
                // tempObj.lastChild.style.left = - w + parseInt(tempObj.offsetWidth)
                 // temp change career's tab width in menu

                 if(tempArray[0]=="Careers")
                 tempObj.lastChild.style.left=-parseInt(tempObj.offsetWidth)+10
                 else
                 tempObj.lastChild.style.left = - w + parseInt(tempObj.offsetWidth)

                }
              } else if (menu_level>0) {
						      tempObj.lastChild.style.top= parseInt(tempObj.style.top) - parseInt((num+1)*(h)) + num
                  tempObj.objAlign = tempObj.parentNode.parentNode.objAlign

                  if (tempObj.objAlign == 'left'){
                        tempObj.lastChild.style.left = -parseInt(tempObj.lastChild.offsetWidth) -2
                  } else {
                        tempObj.lastChild.style.left = parseInt(tempObj.lastChild.offsetWidth)
                  }
              }

				      tempObj.lastChild.style.visibility='visible'
					    tempObj.lastChild.style.position='relative'

			    }
		}

		// mouseout change the style
		tempObj.onmouseout = function() {
					//msg.appendMessage("mouseout:" + this.value);
					tempObj.className = tempObj.offclass
		      if (menu_level==0){
		        if (tempObj.getElementsByTagName("FONT").length > 0)
		          tempObj.getElementsByTagName("FONT")[0].className = "menu0Linkoff"
		      }
					if (tempObj.lastChild.tagName=='DIV'){
              tempObj.lastChild.style.visibility='hidden'
               // setting opacity again
          		if (op){
          		    if (document.all){
                      tempObj.lastChild.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity=" + op + ")"
                  }
              }
					 // intervalID = setTimeout('setVisible("' + tempObj.lastChild.id + '")',800)
					}
				}

    return(tempObj) // return the HTML Obj created
	}


  /*****************************************************************************
   * Create 1 level of menu
   ****************************************************************************/
	this.createMenu = function(ParentObj,Level,SubArray,cx){
		var divHolder;
		var rowHolder;
		var cellHolder;
		var bodyHolder;
		var fx=0
		var fy=0
		if (Level==0){
			rowHolder = document.createElement("TR")
			bodyHolder = document.createElement("TBODY")
			divHolder = document.createElement("TABLE")
			bodyHolder.appendChild(rowHolder)
			divHolder.appendChild(bodyHolder)
			divHolder.cellSpacing=0
			divHolder.cellPadding=0
		}
		else {
			divHolder = document.createElement('DIV')
      divHolder.id = "menuItem_" + runningID
      runningID = parseInt(runningID) + 1
		  divHolder.style.visibility = 'hidden'
			divHolder.style.position='relative'
			divHolder.style.left=-200
		}

  	ParentObj.appendChild(divHolder)
		for (var i=0; i<SubArray.length; i++) {

			if (SubArray[i].length>1) {  // With submenus
				if (Level==0){
				  cellHolder = document.createElement("TD")
					sParentObj = cellHolder.appendChild(this.createItem(Level,i,SubArray[i][0],fx,fy,this.cellWidth,this.cellHeight,this.cellPadding,true,(i==SubArray.length-1)))
				  rowHolder.appendChild(cellHolder)
				}else {
				  sParentObj = divHolder.appendChild(this.createItem(Level,i,SubArray[i][0],fx,fy,this.cellWidth,this.cellHeight,this.cellPadding,true,(i==SubArray.length-1)))
				 }
				this.createMenu(sParentObj,Level+1,SubArray[i][1],fx)
			}else{   // Without submenus
				if (Level==0){
				  cellHolder = document.createElement("TD")
				  cellHolder.appendChild(this.createItem(Level,i,SubArray[i],fx,fy,this.cellWidth,this.cellHeight,this.cellPadding,false,(i==SubArray.length-1)))
				  rowHolder.appendChild(cellHolder)
				}else {
				  divHolder.appendChild(this.createItem(Level,i,SubArray[i],fx,fy,this.cellWidth,this.cellHeight,this.cellPadding,false,(i==SubArray.length-1)))
				}
     	}
		}
	}

  /*****************************************************************************
    Create the entire menu
  *****************************************************************************/
	this.init = function(){
  	var root = document.getElementById(parentId)
  	if(root == null) return false
    // popmenu_clicked = false // in case clicked and then navigate back which preserves global values => doesn't work with firefox!
	  this.createMenu(root, 0, MenuArray)
	}
}
function setVisible(mouseOutID){
      var mouseOutObj = document.getElementById(mouseOutID)
     // alert(mouseOutID)
      mouseOutObj.style.visibility='hidden'
       // setting opacity again
  		if (op){
  		    if (document.all){
              mouseOutObj.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity=" + op + ")"
          }
      }
  		clearTimeout(intervalID)
}

$(function() {
 jQuery.highlight = document.body.createTextRange ? 

/*
Version for IE using TextRanges.
*/
  function(node, te, color) {
   var r = document.body.createTextRange();
   r.moveToElementText(node);
   for (var i = 0; r.findText(te); i++) {
    r.pasteHTML('<font color="' + color + '">' +  r.text + '<\/font>');
    r.collapse(false);
   }
  }

 :

/*
 (Complicated) version for Mozilla and Opera using span tags.
*/
  function(node, te, color) {
   var pos, skip, spannode, middlebit, endbit, middleclone;
   
   skip = 0;
   if (node.nodeType == 3) {
    pos = node.data.toUpperCase().indexOf(te);
    if (pos >= 0) {
     spannode = document.createElement('span');
     //spannode.className = ctype;
     $(spannode).css('color', color);
     middlebit = node.splitText(pos);
     endbit = middlebit.splitText(te.length);
     middleclone = middlebit.cloneNode(true);
     spannode.appendChild(middleclone);
     middlebit.parentNode.replaceChild(spannode, middlebit);
     skip = 1;
    }
   }
   else if (node.nodeType == 1 && node.childNodes && !/(script|style)/i.test(node.tagName)) {
    for (var i = 0; i < node.childNodes.length; ++i) {
     i += $.highlight(node.childNodes[i], te, color);
    }
   }
   return skip;
  }

 ;
});

jQuery.fn.removeHighlight = function() {
 return this.find("span.highlight").each(function() {
  this.parentNode.replaceChild(this.firstChild, this).normalize();
 });
};

function highlights(node, textArry, color)
{
	for(var i = 0; i < textArry.length; i++)
	$.highlight(node, textArry[i], color);
}

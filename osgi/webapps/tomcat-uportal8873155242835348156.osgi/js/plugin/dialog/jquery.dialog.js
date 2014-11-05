/**
 * dialog 1.1 - jQuery Plug-in
 * 
 */
// depends public_resources.js
// lang of button 
var BUTTON_OK = Res._Button['OK'];
var BUTTON_CANCEL = Res._Button['Cancel'];
 
(function($){
    function open(target) {
        var state = $.data(target, 'dialog');
        var options = state.options;
        
        if (typeof(dialog_open) != 'undefined' && dialog_open)
        {
        	dialog_open();
        }
        
        switch(options.showType) {
            case null:
                state.dialog.css('display', 'block');
                break;
            case 'slide':
                state.dialog.slideDown(options.showSpeed, function(){resize(target)});
                break;
            case 'fade':
                state.dialog.fadeIn(options.showSpeed, function(){resize(target)});
                break;
            case 'show':
                state.dialog.show(options.showSpeed, function(){resize(target)});
                break;
        }
        
        if (state.mask) {
            state.mask.css('display', 'block');
        }
        if (state.shadow) {
            state.shadow.css('display', 'block');
        }
        state.options.onOpen.call(target, target);
    }
    
    function close(target) {
        var state = $.data(target, 'dialog');
        var options = state.options;
        
        if (state.options.onClose.call(target, target) == false) return;
        
        switch(options.showType) {
            case null:
                state.dialog.css('display', 'none');
                break;
            case 'slide':
                state.dialog.slideUp(options.showSpeed);
                break;
            case 'fade':
                state.dialog.fadeOut(options.showSpeed);
                break;
            case 'show':
                state.dialog.hide(options.showSpeed);
                break;
                
        }
        
        if (state.mask) {
            state.mask.css('display', 'none');
        }
        if (state.shadow) {
            state.shadow.css('display', 'none');
        }
        
        // destroy the dialog window and remove it from the dom
        if (options.destroyOnClose == true) {
            var timeout = options.showSpeed;
            if (options.showType == null) {
                timeout = 0;
            }
            setTimeout(function(){
                state.dialog.remove();
                if (state.mask) {
                    state.mask.remove();
                }
                if (state.shadow) {
                    state.shadow.remove();
                }
            }, timeout);
        }
    }
    
    function resize(target) {
        var dialog = $.data(target, 'dialog').dialog;
        var content = $('div.dialog-content', dialog);
        var height = $(dialog).height() - $('div.dialog-header', dialog).outerHeight(true);
        height -= $('div.dialog-button', dialog).outerHeight(true);
        if ($.boxModel == true) {
            height -= content.outerHeight(true) - content.height();
        } else {
            height += content.outerHeight(true) - content.outerHeight();
        }
        $('div.dialog-content', dialog).css('height', height);
        
        if ($.boxModel == false) {
            var width = $(dialog).width();
            $('div.dialog-content', dialog).css('width', width);
            $('div.dialog-button', dialog).css('padding', '5px 0px 5px 0px;');
        }
        
        var shadow = $.data(target, 'dialog').shadow;
        if (shadow) {
            shadow.css({
                top: parseInt(dialog.css('top')),
                left: parseInt(dialog.css('left')) - 5,
                width: dialog.outerWidth() + 10,
                height: dialog.outerHeight() + 5
            });
            $('.dialog-shadow-inner', shadow).shadow({hidden:false});
        }
    }
    
    // create and return the dialog
    function create(target, opts) {
        var header = [
                      '<div class="dialog-header">',
                      '<div class="dialog-title">&nbsp;</div>',
                      '<div class="dialog-icon">&nbsp;</div>',
                      '<a href="javascript:void(0)" class="dialog-close"></a>',
                      '</div>'
                      ];
        
        var dialog = $('<div class="dialog"></div>').width(opts.width);
        
        $(target).before(dialog);
        dialog.append(header.join('')).append($(target).addClass('dialog-content'));
        $('a.dialog-close',dialog).click(function(){
            close(target);
        });
        
        if (opts.buttons) {
            var buttons = $('<div class="dialog-button"></div>');
            for(var label in opts.buttons) {
                $('<a></a>').attr('href', 'javascript:void(0)').addClass('l-btn').text(label)
                            .css('margin-right', '10px')
                            .bind('click', eval(opts.buttons[label]))
                            .appendTo(buttons);
            }
            $(dialog).append(buttons);
            $('a.l-btn', buttons).linkbutton();
        }
            
        return dialog;
    }
    
    // center the dialog
    function center(dialog) {
        $(dialog).css({
            left: ($(window).width() - $(dialog).outerWidth(true)) / 2 + $(document).scrollLeft(),
            //top: ($(window).height() - $(dialog).outerHeight(true)) / 2 + $(document).scrollTop()
            top: 100
        });
    }
    
    $.fn.dialog = function(options){
        // method invoking
        if (typeof options == 'string') {
            switch(options) {
                case 'options': // return the first element's options
                    return $.data(this[0], 'dialog').options;
            }
        }
        
        options = options || {};
        
        return this.each(function(){
            var opts = null;
            var dialog = null;
            var state = $.data(this, 'dialog');
            if (state) {
                opts = $.extend(state.options, options || {});
                dialog = state.dialog;
            } else {
                opts = $.extend({}, $.fn.dialog.defaults, options || {});
                dialog = create(this, opts);
                $.data(this, 'dialog', {
                    options: opts,
                    dialog: dialog
                });
                
                // read the options information from the tag
                if (!options.width) {
                    options.width = opts.width = parseInt($(this).css('width')) || opts.width;
                }
                if (!options.height) {
                    options.height = opts.height = parseInt($(this).css('height'));
                }
                if (options.top == null || options.top == undefined) {
                    options.top = opts.top = parseInt($(this).css('top')) || $.fn.dialog.defaults.top;
                }
                if (options.left == null || options.left == undefined) {
                    options.left = opts.left = parseInt($(this).css('left')) || $.fn.dialog.defaults.left;
                }
                if (!options.title) {
                    opts.title = $(this).attr('title') || opts.title;
                }
                
                $(this).css('width', null);
                $(this).css('height', null);
                if (opts.width) dialog.width(opts.width);
                if (opts.height) dialog.height(opts.height);
                center(dialog);
            }
            
            // set dialog position, width and height
            if (options.width) dialog.width(options.width);
            if (options.height) dialog.height(options.height);
            if (options.left != undefined && options.left != null) dialog.css('left', options.left);
            if (options.top != undefined && options.top != null) dialog.css('top', options.top);
            
            // set dialog title
            $('div.dialog-title', dialog).html(opts.title); 
            if (/^[u4E00-u9FA5]/.test(opts.title) == false && $.browser.msie) {
                $('div.dialog-title', dialog).css('padding-top', '8px');
            }
            
            if (opts.iconCls) {
                $('.dialog-header .dialog-icon', dialog).addClass(opts.iconCls);
                $('.dialog-header .dialog-title', dialog).css('padding-left', '20px');
            } else {
                $('.dialog-header .dialog-icon', dialog).attr('class', 'dialog-icon');
                $('.dialog-header .dialog-title', dialog).css('padding-left', '5px');
            }
            
            var target = this;
            $(dialog).draggable({
                handle: 'div.dialog-header',
                disabled: opts.draggable == false,
                onDrag:function(){
                    resize(target);
                }
            });
            
            $(dialog).resizable({
                disabled: opts.resizable == false,
                onResize: function(){
                    resize(target);
                }
            });
            
            if ($.data(this, 'dialog').mask) {
                $.data(this, 'dialog').mask.remove();
            }
            if (opts.modal == true) {
                $.data(this, 'dialog').mask = $('<div class="dialog-mask"></div>')
                        .css({
                            zIndex: $.fn.dialog.defaults.zIndex++,
                            width: getPageArea().width,
                            height: getPageArea().height
                        })
                        .appendTo($(document.body));
            }
            
            if ($.data(this, 'dialog').shadow) {
                $.data(this, 'dialog').shadow.remove();
            }
            if (opts.shadow == true) {
                var shadow = $('<div class="dialog-shadow"><div class="dialog-shadow-inner"></div></div>');
                $('.dialog-shadow-inner', shadow).shadow({width:5, fit:true, hidden:true});
                $.data(this, 'dialog').shadow = shadow
                        .css('z-index', $.fn.dialog.defaults.zIndex++)
                        .insertAfter(dialog);
            }
            
            dialog.css('z-index', $.fn.dialog.defaults.zIndex++);
            
            
            // load the href content
            if (options.href) {
                $(this).load(options.href, null, function(){
                    resize(target);
                    opts.onLoad.apply(this, arguments); // trigger the onLoad event
                });
            }
            
            if (opts.closed == false) {
                if (dialog.css('display') == 'none') {
                    open(this);
                    resize(this);   // resize the dialog
                }
            } else {
                if (dialog.css('display') == 'block') {
                    close(this);
                }
            }
            
        });
        
    };
    
    // when window resize, reset the width and height of the dialog's mask
    $(window).resize(function(){
        $('.dialog-mask').css({
            width: $(window).width(),
            height: $(window).height()
        });
        setTimeout(function(){
            $('.dialog-mask').css({
                width: getPageArea().width,
                height: getPageArea().height
            });
        }, 50);
    });
    
    function getPageArea() {
        if (document.compatMode == 'BackCompat') {
            return {
                width: Math.max(document.body.scrollWidth, document.body.clientWidth),
                height: Math.max(document.body.scrollHeight, document.body.clientHeight)
            }
        } else {
            return {
                width: Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth),
                height: Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight)
            }
        }
    }
    
    
    $.fn.dialog.defaults = {
        zIndex: 9000,
        title: 'title',
        closed: false,
        destroyOnClose: false,
        draggable: true,
        resizable: true,
        modal: false,
        shadow: true,
        width: 300,
        height: null,
        showType: null,
        showSpeed: 600,
        left: null,
        top: null,
        iconCls: null,
        href:null,
        onOpen: function(){},
        onClose: function(){},
        onLoad:function(){}
    };
})(jQuery);

/**
 * shadow 1.0 - jQuery Plug-in
 * 
 * Licensed under the GPL:
 *   http://gplv3.fsf.org
 *
 * Copyright 2009 stworthy [ stworthy@gmail.com ] 
 * 
 * options:
 *  hidden: boolean false to show the shadow and true to hide the shadow 
 *  fit: boolean true to fit the parent container and false not
 *  width: integer width The width in pixels of the shadow. Default: 8
 * 
 */
(function($){
    $.fn.shadow = function(options){
        
        return this.each(function(){
            
            // wrap the element and return the jQuery object
            function wrapElem(target) {
                var wraps = [
                             '<div class="shadow">',
                             '<div class="shadow-one">',
                             '<div class="shadow-corner-a"></div>',
                             '<div class="shadow-corner-b"></div>',
                             '<div class="shadow-two">',
                             '  <div class="shadow-three">',
                             '      <div class="shadow-four">',
                             '      </div>',
                             '  </div>',
                             '</div>',
                             '</div>',
                             '</div>'
                             ];
                
                var shadow = $(wraps.join('')).insertAfter($(target));
                $(target).appendTo($('.shadow-four', shadow));
                return shadow;
            }
            
            if ($.data(this, 'shadow')) {
                $.extend($.data(this, 'shadow').options, options || {});
            } else {
                $.data(this, 'shadow', {
                    options: $.extend({}, $.fn.shadow.defaults, options || {}),
                    shadow: wrapElem(this),
                    oldWidth: $(this).width(),  // the element old width and height
                    oldHeight: $(this).height()
                });
            }
            
            if (!$.data(this, 'shadow').shadow) {
                $.data(this, 'shadow').shadow = wrapElem(this);
            }
            
            var opts = $.data(this, 'shadow').options;
            var shadow = $.data(this, 'shadow').shadow;
            
            if (opts.hidden == true) {
                $(this).insertAfter(shadow);
                shadow.remove();
                $.data(this, 'shadow').shadow = null;
                return;
            }
            
            $('.shadow-one', shadow).css({
                paddingLeft: opts.width * 2,
                paddingTop: opts.width * 2
            });
            $('.shadow-corner-a', shadow).css({
                width: opts.width * 2,
                height: opts.width * 2
            });
            $('.shadow-corner-b', shadow).css({
                width: opts.width * 2,
                height: opts.width * 2
            });
            $('.shadow-three', shadow).css({
                left: opts.width * -2,
                top: opts.width * -2
            });
            $('.shadow-four', shadow).css({
                left: opts.width,
                top: opts.width
            });
            
            if (opts.fit == true) {
                // make element and shadow fit the parent container
                
                var parent = $(shadow).parent();    // the parent container
                
                if ($.boxModel == true) {
                    var delta = $(this).outerWidth(true) - $(this).width();
                    $(this).css({
                        width: parent.width() - 2*opts.width - delta,
                        height: parent.height() - 2*opts.width - delta
                    });
                    $(shadow).css({
                        width: parent.width(),
                        height: parent.height()
                    });
                    $('.shadow-one', shadow).css({
                        width: parent.width() - 2*opts.width,
                        height: parent.height() - 2*opts.width
                    });
                
                } else {
                    $(this).css({
                        width:'100%',
                        height:'100%'
                    });
                    $(shadow).css({
                        width: parent.width(),
                        height: parent.height()
                    });
                    $('.shadow-one', shadow).css({
                        width: parent.width(),
                        height: parent.height()
                    });
                }
            } else {
                // restore the element's width and height
                $(this).width($.data(this, 'shadow').oldWidth)
                        .height($.data(this, 'shadow').oldHeight);
                
                $('.shadow-one', shadow).css({
                    width:'100%',
                    height:'100%'
                });
                
                if ($.boxModel == true) {
                    $(shadow).css({
                        width: $(this).outerWidth(),
                        height: $(this).outerHeight()
                    });
                } else {
                    $(shadow).css({
                        width: $.data(this, 'shadow').oldWidth + 2*opts.width,
                        height: $.data(this, 'shadow').oldHeight + 2*opts.width
                    });
                }
            }
            
        });
    };
    
    $.fn.shadow.defaults = {
            hidden: false,
            fit: false,
            width: 8
    };
})(jQuery);

/**
 * draggable 1.0 - jQuery Plug-in
 * 
 * Licensed under the GPL:
 *   http://gplv3.fsf.org
 *
 * Copyright 2009 stworthy [ stworthy@gmail.com ] 
 */
(function($){
    $.fn.draggable = function(options){
        function doDown(e){
            $.data(e.data.target, 'draggable').options.onStartDrag(e);
            return false;
        }
        
        function doMove(e){
            var dragData = e.data;
            var left = dragData.startLeft + e.pageX - dragData.startX;
            var top = dragData.startTop + e.pageY - dragData.startY;
            
            if (e.data.parnet != document.body) {
                if ($.boxModel == true) {
                    left += $(e.data.parent).scrollLeft();
                    top += $(e.data.parent).scrollTop();
                }
            }
            
            var opts = $.data(e.data.target, 'draggable').options;
            if (opts.axis == 'h') {
                $(dragData.target).css('left', left);
            } else if (opts.axis == 'v') {
                $(dragData.target).css('top', top);
            } else {
                $(dragData.target).css({
                    left: left,
                    top: top
                });
            }
            $.data(e.data.target, 'draggable').options.onDrag(e);
            return false;
        }
        
        function doUp(e){
            $(document).unbind('.draggable');
            $.data(e.data.target, 'draggable').options.onStopDrag(e);
            return false;
        }
        
        
        return this.each(function(){
            $(this).css('position','absolute');
            
            var opts;
            var state = $.data(this, 'draggable');
            if (state) {
                state.handle.unbind('.draggable');
                opts = $.extend(state.options, options);
            } else {
                opts = $.extend({}, $.fn.draggable.defaults, options || {});
            }
            
            if (opts.disabled == true) {
                $(this).css('cursor', 'default');
                return;
            }
            
            var handle = null;
            if (typeof opts.handle == 'undefined' || opts.handle == null){
                handle = $(this);
            } else {
                handle = (typeof opts.handle == 'string' ? $(opts.handle, this) : handle);
            }
            $.data(this, 'draggable', {
                options: opts,
                handle: handle
            });
            
            // bind mouse event using event namespace draggable
            handle.bind('mousedown.draggable', {target:this}, onMouseDown);
            handle.bind('mousemove.draggable', {target:this}, onMouseMove);
            
            function onMouseDown(e) {
                if (checkArea(e) == false) return;

                var position = $(e.data.target).position();
                var data = {
                    startLeft: position.left,
                    startTop: position.top,
                    startX: e.pageX,
                    startY: e.pageY,
                    target: e.data.target,
                    parent: $(e.data.target).parent()[0]
                };
                
                $(document).bind('mousedown.draggable', data, doDown);
                $(document).bind('mousemove.draggable', data, doMove);
                $(document).bind('mouseup.draggable', data, doUp);
            }
            
            function onMouseMove(e) {
                if (checkArea(e)){
                    $(this).css('cursor', 'move');
                } else {
                    $(this).css('cursor', 'default');
                }
            }
            
            // check if the handle can be dragged
            function checkArea(e) {
                var offset = $(handle).offset();
                var width = $(handle).outerWidth();
                var height = $(handle).outerHeight();
                var t = e.pageY - offset.top;
                var r = offset.left + width - e.pageX;
                var b = offset.top + height - e.pageY;
                var l = e.pageX - offset.left;
                
                return Math.min(t,r,b,l) > opts.edge;
            }
            
        });
    };
    
    $.fn.draggable.defaults = {
            handle: null,
            disabled: false,
            edge:0,
            axis:null,  // v or h
            onStartDrag: function(){},
            onDrag: function(){},
            onStopDrag: function(){}
    };
})(jQuery);

/**
 * linkbutton 1.0 - jQuery Plug-in
 * 
 * Licensed under the GPL:
 *   http://gplv3.fsf.org
 *
 * Copyright 2009 stworthy [ stworthy@gmail.com ] 
 */
(function($){
    $.fn.linkbutton = function(options){
        
        // wrap the link button, make sure to execute once
        function wrapButton(target) {
            $(target).addClass('l-btn')
                     .wrapInner('<span class="l-btn-left"><span class="l-btn-text"></span></span>');
            var iconCls = $(target).attr('icon');
            if (iconCls) {
                $('.l-btn-text', target).addClass(iconCls).css('padding-left', '20px');
            }
        }
        
        return this.each(function(){
            var opts;
            var state = $.data(this, 'linkbutton');
            if (state) {
                opts = $.extend(state.options, options || {});
                state.options = opts;
            } else {
                wrapButton(this);
                opts = $.extend({}, $.fn.linkbutton.defaults, options || {});
                
                if ($(this).hasClass('l-btn-plain')) {
                    opts.plain = true;
                }
                
                // the button initialize state is disabled
                if ($(this).attr('disabled')) {
                    opts.disabled = true;
                    $(this).removeAttr('disabled');
                }
                
                state = {options: opts};
                
            }
            
            if (state.options.disabled) {
                var href = $(this).attr('href');
                if (href) {
                    state.href = href;
                    $(this).removeAttr('href');
                }
                var onclick = $(this).attr('onclick');
                if (onclick) {
                    state.onclick = onclick;
                    $(this).attr('onclick', null);
                }
                $(this).addClass('l-btn-disabled');
            } else {
                if (state.href) {
                    $(this).attr('href', state.href);
                }
                if (state.onclick) {
                    this.onclick = state.onclick;
                }
                $(this).removeClass('l-btn-disabled');
            }
            
            if (state.options.plain == true) {
                $(this).addClass('l-btn-plain');
            } else {
                $(this).removeClass('l-btn-plain');
            }
            
            $.data(this, 'linkbutton', state);  // save the button state
        });
    };
    
    $.fn.linkbutton.defaults = {
            disabled: false,
            plain: false
    };
    
    $(function(){
        $('a.l-btn').linkbutton();
    });
})(jQuery);


/**
 * resizable 1.0 - jQuery Plug-in
 * 
 * Licensed under the GPL:
 *   http://gplv3.fsf.org
 *
 * Copyright 2009 stworthy [ stworthy@gmail.com ] 
 */
(function($){
    $.fn.resizable = function(options){
        function doDown(e){
            $.data(e.data.target, 'resizable').options.onStartResize(e);
            return false;
        }
        
        function doMove(e){
            var resizeData = e.data;
            var options = $.data(resizeData.target, 'resizable').options;
            var target = resizeData.target;
            if (resizeData.dir.indexOf('e') != -1) {
                var width = resizeData.startWidth + e.pageX - resizeData.startX;
                if ($.boxModel == false) {
                    width += resizeData.deltaWidth;
                }
                width = Math.min(
                            Math.max(width, options.minWidth),
                            options.maxWidth
                        );
                $(target).css('width', width + 'px');
            }
            if (resizeData.dir.indexOf('s') != -1) {
                var height = resizeData.startHeight + e.pageY - resizeData.startY;
                if ($.boxModel == false) {
                    height += resizeData.deltaHeight;
                }
                height = Math.min(
                            Math.max(height, options.minHeight),
                            options.maxHeight
                        );
                $(target).css('height', height + 'px');
            }
            if (resizeData.dir.indexOf('w') != -1) {
                var width = resizeData.startWidth - e.pageX + resizeData.startX;
                if ($.boxModel == false) {
                    width += resizeData.deltaWidth;
                }
                if (width >= options.minWidth && width <= options.maxWidth) {
                    var left = resizeData.startLeft + e.pageX - resizeData.startX;
                    $(target).css({
                        left: left + 'px',
                        width: width + 'px'
                    });
                }
            }
            if (resizeData.dir.indexOf('n') != -1) {
                var height = resizeData.startHeight - e.pageY + resizeData.startY;
                if ($.boxModel == false) {
                    height += resizeData.deltaHeight;
                }
                if (height >= options.minHeight && height <= options.maxHeight) {
                    var top = resizeData.startTop + e.pageY - resizeData.startY;
                    $(target).css({
                        top: top + 'px',
                        height: height + 'px'
                    });
                }
            }
            $.data(e.data.target, 'resizable').options.onResize(e);
            return false;
        }
        
        function doUp(e){
            $(document).unbind('.resizable');
            $.data(e.data.target, 'resizable').options.onStopResize(e);
            return false;
        }
        
        return this.each(function(){
            var opts = null;
            var state = $.data(this, 'resizable');
            if (state) {
                $(this).unbind('.resizable');
                opts = $.extend(state.options, options || {});
            } else {
                opts = $.extend({}, $.fn.resizable.defaults, options || {});
            }
            
            if (opts.disabled == true) {
                return;
            }
            
            $.data(this, 'resizable', {
                options: opts
            });
            
            var target = this;
            
            // bind mouse event using namespace resizable
            $(this).bind('mousemove.resizable', onMouseMove)
                   .bind('mousedown.resizable', onMouseDown);
            
            function onMouseMove(e) {
                var dir = getDirection(e);
                if (dir == '') {
                    $(target).css('cursor', 'default');
                } else {
                    $(target).css('cursor', dir + '-resize');
                }
            }
            
            function onMouseDown(e) {
                var dir = getDirection(e);
                if (dir == '') return;
                
                var data = {
                    target: this,
                    dir: dir,
                    startLeft: getCssValue('left'),
                    startTop: getCssValue('top'),
                    startX: e.pageX,
                    startY: e.pageY,
                    startWidth: $(target).width(),
                    startHeight: $(target).height(),
                    deltaWidth: $(target).outerWidth() - $(target).width(),
                    deltaHeight: $(target).outerHeight() - $(target).height()
                };
                $(document).bind('mousedown.resizable', data, doDown);
                $(document).bind('mousemove.resizable', data, doMove);
                $(document).bind('mouseup.resizable', data, doUp);
            }
            
            // get the resize direction
            function getDirection(e) {
                var dir = '';
                var offset = $(target).offset();
                var width = $(target).outerWidth();
                var height = $(target).outerHeight();
                var edge = opts.edge;
                if (e.pageY > offset.top && e.pageY < offset.top + edge) {
                    dir += 'n';
                } else if (e.pageY < offset.top + height && e.pageY > offset.top + height - edge) {
                    dir += 's';
                }
                if (e.pageX > offset.left && e.pageX < offset.left + edge) {
                    dir += 'w';
                } else if (e.pageX < offset.left + width && e.pageX > offset.left + width - edge) {
                    dir += 'e';
                }
                return dir;
            }
            
            function getCssValue(css) {
                var val = parseInt($(target).css(css));
                if (isNaN(val)) {
                    return 0;
                } else {
                    return val;
                }
            }
            
        });
    };
    
    $.fn.resizable.defaults = {
            disabled:false,
            minWidth: 10,
            minHeight: 10,
            maxWidth: 10000,//$(document).width(),
            maxHeight: 10000,//$(document).height(),
            edge:5,
            onStartResize: function(){},
            onResize: function(){},
            onStopResize: function(){}
    };
    
})(jQuery);

/**
 * linkbutton 1.0 - jQuery Plug-in
 * 
 * Licensed under the GPL:
 *   http://gplv3.fsf.org
 *
 * Copyright 2009 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 *  shadow
 *  draggable
 *  resizable
 *  linkbutton
 *  dialog
 */
(function($){
    
    $.messager = {
        show: function(options) {
            options = $.extend({
                showType: 'slide',
                showSpeed: 600,
                width: 250,
                height: 100,
                msg: '',
                title: '',
                timeout: 4000
            }, options || {});
            
            var win = $('<div></div>').appendTo(document.body);
            win.html(options.msg);
            win.dialog({
                title:options.title,
                shadow:false,
                resizable:false,
                showType:options.showType,
                showSpeed:options.showSpeed,
                width:options.width,
                height:options.height,
                closed:false,
                onClose:function(){
                    $(this).dialog('options').destroyOnClose = true;
                }
            });
            
            win.parent().css({
                left: null,
                top: null,
                right: 0,
                bottom: -document.body.scrollTop-document.documentElement.scrollTop
            });
            
            $(window).scroll(function(){
                win.parent().css('bottom', -document.body.scrollTop-document.documentElement.scrollTop);
            });
            
            if (options.timeout > 0) {
                setTimeout(function(){
                    win.dialog({closed:true});
                }, options.timeout);
            }
        },
        
        alert: function(title, msg, icon, fn) {
            var content = '<div>' + msg + '</div>';
            switch(icon) {
                case 'error':
                    content = '<div class="messager-icon messager-error"></div>' + content;
                    break;
                case 'info':
                    content = '<div class="messager-icon messager-info"></div>' + content;
                    break;
                case 'question':
                    content = '<div class="messager-icon messager-question"></div>' + content;
                    break;
                case 'warning':
                    content = '<div class="messager-icon messager-warning"></div>' + content;
                    break;
            }
            content += '<div style="clear:both;"/>';
            
            var win = $('<div></div>').appendTo(document.body);
            win.html(content);
            win.dialog({
                title: title,
                modal:true,
                resizable:false,
                buttons:{
                    'Ok':function(){
                        win.dialog({closed:true});
                        if (fn){
                            fn();
                        }
                    }
                },
                onClose:function(){
                    $(this).dialog('options').destroyOnClose = true;
                }
            });
        },
        
        confirm: function(title, msg, fn) {
            var win = $('<div></div>').appendTo(document.body);
            win.html('<div class="messager-icon messager-question"></div>'
                        + '<div>' + msg + '</div>'
                        + '<div style="clear:both;"/>');
            win.dialog({
                title: title,
                modal:true,
                resizable:false,
                buttons:{
                    'Ok':function(){
                        win.dialog({closed:true});
                        if (fn){
                            fn(true);
                        }
                    },
                    'Cancel':function(){
                        win.dialog({closed:true});
                        if (fn){
                            fn(false);
                        }
                    }
                },
                onClose:function(){
                    $(this).dialog('options').destroyOnClose = true;
                }
            });
        },
        
        prompt: function(title, msg, initValue, fn, type) {
            var win = $('<div></div>').appendTo(document.body);
            
            var typeStr = '';
            var textStr = '';
            var inputHtml = '';
            
            if (type == 1)
            {
            	typeStr = "<img src='../images/calendar.gif' style='cursor: pointer' align='top' onclick='return calDate(\"prompt-messager-input\");' height='20px' width='20px'/>";
            	
            	textStr = 'readonly=readonly'; 
            }
            
            inputHtml = '<input class="messager-input" id="prompt-messager-input" type="text" ' + textStr + ' value="' + initValue + '" style="width:220px;"/>' + typeStr;
            
            if (type == 2)
            {
            	inputHtml = '<textarea class="messager-input" style="width:95%" id="prompt-messager-input"></textarea>';
            }
            
            win.html('<div class="messager-icon messager-question"></div>'
                        + '<div>' + msg + '</div>'
                        + '<br/>'
                        + inputHtml
                        + '<div style="clear:both;"/>');
                        
            var bmap = {};
            
            bmap[BUTTON_OK] = function(){
                        win.dialog({closed:true});
                        if (fn){
                            fn($('.messager-input', win).val(), true, $('.messager-input', win));
                        }
                    };
                    
            bmap[BUTTON_CANCEL] = function(){
                        win.dialog({closed:true});
                        if (fn){
                            fn(null, false, $('.messager-input', win));
                        }
                    };
            win.dialog({
                title: title,
                modal:true,
                resizable:false,
                buttons:bmap,
                onClose:function(){
                    $(this).dialog('options').destroyOnClose = true;
                }
            });
        }
    };
    
})(jQuery);


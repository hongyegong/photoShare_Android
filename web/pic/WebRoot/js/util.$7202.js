KISSY.add("util/event",function(a,b){return{Dispatcher:a.mix({},b.Target)}},{requires:["event"]}),KISSY.add("util/url",function(a){var b="diandian.com",c="http://www."+b,d=a.Config.baseHost;return{root:b,host:c,staticHost:d}}),KISSY.add("util/func",function(a,b,c){function j(){var a=d("#PopElementContainer");return a?a:(a=b.create("<div>",{id:"PopElementContainer"}),b.insertBefore(a,document.body.firstChild),a)}function k(a,b){if(!a||!b)return;var d=function(a){if(!a.ctrlKey&&!f||a.keyCode!="13")return;b(a)};e(a,"focus",function(){e(a,"keydown",d)}),e(a,"blur",function(){c.remove(a,"keydown",d)})}function l(c){function h(){if(e.scrollTop==e.offsetTop){i=!1,a.isFunction(e.callback)&&e.callback();return}Math.abs(e.scrollTop-e.offsetTop)<Math.abs(e.step)&&e.offsetTop>0&&(e.step=e.step>0?1:-1),e.scrollTop=e.scrollTop+e.step,e.scrollTop<0&&(e.scrollTop=0),window.scrollTo(0,e.scrollTop),setTimeout(function(){h()},0)}if(i)return;i=!0;var e={el:null,step:8,offsetTop:null,callback:null},f=b.docHeight(),g=b.scrollTop();a.isString(c)?e.el=c:a.mix(e,c),e.el=d(e.el),!e.el||(e.offsetTop=b.offset(e.el).top),e.scrollTop=g,e.step=g>e.offsetTop?-e.step:e.step;if(!e.scrollTop)return;h()}function m(a){window.scrollTo(0,0)}function n(c){g&&o(),c=c||{};var d=c.zIndex||9999,e=c.bgColor||"#000",f=c.opacity||.6,i=b.docHeight(),j=b.create("<div>",{width:"100%",height:i}),k;b.css(j,{position:"absolute","z-index":d,opacity:f,background:e,left:0,top:0}),document.body.appendChild(j),a.UA.ie===6&&(k=b.create('<iframe frameborder="0">',{width:"100%","z-index":d,height:i}),b.css(k,{position:"absolute",top:0,left:0,opacity:0}),document.body.appendChild(k),h=k),g=j}function o(){try{b.remove(g),b.remove(h)}catch(a){}}function p(f){a.isString(f)&&(f=d(f));if(!f)return;var g=f.value,h=a.JSON.parse(b.attr(f,"tip"));if(!g||g==h.text)b.addClass(f,h["class"]),ENV.searchKeyword?f.value=decodeURIComponent(ENV.searchKeyword):f.value=decodeURIComponent(h.text);c.remove(f,"focus"),c.remove(f,"blur"),e(f,"focus",function(){f.value==h.text&&(f.value=""),b.removeClass(f,h["class"])}),e(f,"blur",function(){f.value||(f.value=h.text,b.addClass(f,h["class"]))})}function q(a){var b={text:"",start:0,end:0};a.focus();if(a.setSelectionRange)b.start=a.selectionStart,b.end=a.selectionEnd,b.text=b.start!=b.end?a.value.substring(b.start,b.end):"";else if(document.selection){var c,d=document.selection.createRange(),e=document.body.createTextRange();e.moveToElementText(a),b.text=d.text,b.bookmark=d.getBookmark();for(c=0;e.compareEndPoints("StartToStart",d)<0&&d.moveStart("character",-1)!==0;c++)a.value.charAt(c)=="\n"&&c++;b.start=c,b.end=b.text.length+b.start}return b}function r(a,b){b||alert("You must get cursor position first.");if(a.setSelectionRange)a.focus(),a.setSelectionRange(b.start,b.end);else if(a.createTextRange){var c=a.createTextRange();a.value.length===b.start?(c.collapse(!1),c.select()):b.bookmark&&(c.moveToBookmark(b.bookmark),c.select())}}var d=b.get,e=c.on,f=!1,g,h,i;return e(document,"keydown keyup",function(a){var b=a.keyCode;if(224===b||91===b||93===b)f="keydown"===a.type}),{getPopElementContainer:j,bindCtrlEnterKey:k,scrollViewTo:l,scrollToTop:m,disableDom:n,enableDom:o,inputTip:p,getCursorPosition:q,setCursorPosition:r}},{requires:["dom","event"]}),KISSY.add("util/notify",function(a,b,c,d){function j(c){var g=d.getPopElementContainer(),h="dd-global-notify",i,j="dd-global-notify",k,l={};a.isString(c)?(l.msg=c,l.time=3e3,l.id=""):(l=c,l.time=l.time||1500,l.id=l.id||""),i=e("#"+h+l.id),!i||b.remove(i),i=f('<div id="'+h+l.id+'" class="dd-global-notify-position">'+'<div class="dd-global-notify-wrap">'+'<div class="'+j+'"></div>'+"</div>"+"</div>"),k=e("."+j,i),k.innerHTML=l.msg,g.appendChild(i),b.show(i),l.time!="always"&&setTimeout(function(){a.Anim(i,"opacity:0",.3,"linear",function(){b.hide(i)}).run()},l.time)}function l(a){k&&k.hide(),KISSY.use("sky.$6939,sky/Dialog.$7202",function(b,c){k=c.Dialog.alert({content:a})})}function m(b){a.mix(this,b),this.init()}var e=b.get,f=b.create,g=c.on,h=6===a.UA.ie,i={},k;return a.augment(m,a.EventTarget,{guid:0,disableDom:!1,init:function(){var b=this;this.popContainer=d.getPopElementContainer(),this.guid=this.guid||a.guid(),i[this.guid]=this,this.renderUI(),g(this.container,"click",function(a){b.dispatchClickEvent(a)})},renderUI:function(){var a=this.popContainer,c,d,g,i,j,k,l,m;c=f("<div>",{id:"dd-dialog-"+this.guid}),b.addClass(c,"dd-dialog"),i=f("<div>"),b.addClass(i,"dd-dialog-header clearfix"),j=f("<h2>"),k=f("<a>",{html:"\u5173\u95ed"}),b.addClass(k,"dd-dialog-close-btn"),l=f("<div>"),b.addClass(l,"dd-dialog-content clearfix"),m=f("<div>"),b.addClass(m,"dd-dialog-footer clearfix"),c.innerHTML='<table width="100%" height="100%"><tr class="dd-dialog-tr-t"><td class="dd-dialog-td-lt"></td><td class="dd-dialog-td-ct"></td><td class="dd-dialog-td-rt"></td></tr><tr class="dd-dialog-tr-c"><td class="dd-dialog-td-lc"><td class="dd-dialog-td-cc"><div class="dd-dialog-wrap"></div></td><td class="dd-dialog-td-rc"></td></tr><tr class="dd-dialog-tr-b"><td class="dd-dialog-td-lb"><td class="dd-dialog-td-cb"></td><td class="dd-dialog-td-rb"></td></tr></table>',c.style.display="none",h&&(d=f('<iframe frameborder="0">'),c.appendChild(d)),g=e(".dd-dialog-wrap",c),g.appendChild(i),i.appendChild(j),i.appendChild(k),g.appendChild(l),g.appendChild(m),a.appendChild(c),this.container=c,this.wrap=g,this.header=i,this.content=l,this.footer=m,this.title=j,this.closeButton=k,!d||(this.iframe=d)},dispatchClickEvent:function(a){var c=a.target;b.hasClass(c,"dd-dialog-close-btn")&&this.hide(),this.fire("click",{dialogClickEventObj:a})},setPosition:function(a){var c,d,e=this.container,f=b.viewportWidth(),g=b.viewportHeight();contW=b.width(e),contH=b.height(e),a=="center"&&(c=(f-contW)/2,d=(g*.8-contH)/2,a={top:d,left:c}),h&&(a.top=a.top+b.scrollTop()),b.css(this.container,a);if(!!this.iframe){var i=b.width(this.container)-20,j=b.height(this.container)-20;b.css(this.iframe,{width:i,height:j})}},setSize:function(a){b.css(this.container,a)},setContentHtml:function(a){this.content.innerHTML=a},setTitle:function(a){this.title.innerHTML=a},setFooterHtml:function(a){this.footer.innerHTML=a},hideHeader:function(){b.hide(this.header)},hideFooter:function(){b.hide(this.footer)},hide:function(){b.hide(this.container),!this.disableDom||d.enableDom({zIndex:101}),this.fire("hide")},show:function(){b.show(this.container),!this.disableDom||d.disableDom({zIndex:101}),this.fire("show")},destory:function(){this.popContainer.removeChild(this.container)}}),m.getInstance=function(a){return i[a]},{globalNotify:j,alert:l,Dialog:m}},{requires:["dom","event","util/func.$4772"]}),KISSY.add("util/follow",function(a,b,c,d,e){var f=b.post,g,h=e.Dispatcher;return g={follow:function(b,e,g){if(!b)return;if(a.isObject(b)&&b.id){var i=b;b=i.id}var j=i||{id:b};!g||(j.ref="feed",j.feed_id=g),f(c.host+"/follow",j,function(b){b.status_code<0?d.alert(b.result):a.isFunction(e)&&e(b)}),h.fire("blog::follow")},unfollow:function(b,d){if(!b)return;f(c.host+"/unfollow",{id:b},function(b){b.status_code<0?nofity.alert(b.result):a.isFunction(d)&&d(b)}),h.fire("blog::unfollow")}},g},{requires:["ajax","util/url.$5373","util/notify.$7066","util/event.$3822"]}),KISSY.add("util/block",function(a,b,c,d,e){function g(b){var c=document.createElement("a");return b+=b.indexOf(".")>-1?"":".diandian.com",c.href=a.startsWith(b,"http")?b:"http://"+b,b=c.hostname,b}function h(b,c){b.errCode>=0?a.isFunction(c)&&c(b):e.alert(b.result)}var f;return f={add:function(c,e,f){var c=g(c);b({type:"post",url:d.host+"/blacklist/add",data:{blogUrl:c},success:function(a){h(a,e)},complete:function(b){a.isFunction(f)&&f(b)}})},addById:function(c,e,f){b({type:"post",url:d.host+"/blacklist/add",data:{userId:c},success:function(a){h(a,e)},complete:function(b){a.isFunction(f)&&f(b)}})},report:function(c,e,f){b({type:"post",url:d.host+"/blacklist/report",data:c,success:function(a){h(a,e)},complete:function(b){a.isFunction(f)&&f(b)}})},remove:function(c,e,f){var c=g(c);b({type:"post",url:d.host+"/blacklist/remove",data:{blogUrl:c},success:function(a){h(a,e)},complete:function(b){a.isFunction(f)&&f()}})}},f},{requires:["ajax","json","util/url.$5373","util/notify.$6373"]}),KISSY.add("util/log",function(a){function c(c,d){var e="http://log.libdd.com/"+c+".html?"+a.param(d)+"&t="+ +(new Date)+"&logid="+b;window.DDHostName&&(e=e+"&host="+encodeURIComponent(window.DDHostName)),(new Image).src=e}var b=(new Date).getTime();return{log:c}}),KISSY.add("util/uploadToken",function(a,b){function e(d,e){e||(e="www.diandian.com"),b({type:"post",url:"http://"+e+"/image/token",success:function(b){c[e]={},c[e].tt=b,c[e].lastCheckTime=(new Date).getTime(),a.isFunction(d)&&d(b)},dataType:"text"})}function f(b,f){if(!a.isFunction(b))return;f||(f="www.diandian.com"),!!c[f]&&(new Date).getTime()-c[f].lastCheckTime<d*1e3?b(c[f].tt):e(b,f)}var c={},d=600;return{tokenUpload:f}},{requires:["ajax"]}),KISSY.add("util",function(a,b,c,d,e,f,g,h,i){ENV.SWFUPLOAD_IMG_TYPES="*.jpg;*.png;*.gif;*.jpeg;*.bmp;*.JPG;*.PNG;*.GIF;*.JPEG;*.BMP;*.Jpg;*.Png;*.Gif;*.Jpeg;*.Bmp";var j=window.DD={},k=a.merge(b,{url:c},d,e,{follow:f},{block:g},h,i);return a.mix(j,k),a.merge(b,{url:c},d,e,{follow:f},{block:g},h,i)},{requires:["util/event.$3822","util/url.$5373","util/func.$4772","util/notify.$7202","util/follow.$7202","util/block.$7202","util/log.$4558","util/uploadToken.$6781"]});
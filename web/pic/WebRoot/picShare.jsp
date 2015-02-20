<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%
List<Map<String,Object>> resList = (List<Map<String,Object>>)request.getAttribute("resList");
if(resList == null){
	out.println("<script>window.location.href='index.jsp';</script>");
	return ;
}
Object pkMember = request.getAttribute("pkMember");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0034)http://pin.aisonghua.com/all/0/0/2 -->
<html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>图片广场</title>
<meta name="Description" content="">
<meta name="keywords" content="">
<link href="./picShare_files/style.css" rel="stylesheet" type="text/css">
<link href="./picShare_files/default.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="./picShare_files/jquery.min.js"></script>
<script type="text/javascript" src="./picShare_files/myou.min.js"></script>
<script src="./picShare_files/artDialog.js"></script>
<script src="./picShare_files/iframeTools.js"></script>
<script type="text/javascript" src="./picShare_files/myou.tools.provinces.min.js"></script>

<script type="text/javascript">
	//页面加载的时候需要动态创建div
	$(document).ready(function(){
		var isShow = "true";
		if(isShow == "null"){
			grayDiv();
			oursDiv();
			showDiv();	
		}
		
	});
	
	//判断是否是IE浏览器
	function isIE(){
		return (document.all && window.ActiveXObject && !window.opera) ? true : false;
	}
	var loginDivWidth  = 380;
	var loginDivHeight = 170;
	//js动态创建一个使页面变灰的DIV
	function grayDiv(){
		var coverDiv = document.createElement('div');
		document.body.appendChild(coverDiv);
		with (coverDiv.style){
			display      = 'none';
			position     = 'absolute';
			background   = '#FFFFFF';
			left         = '0px';
			top          = '0px';
			var bodySize = getBodySize();
			width        = bodySize[0] + 'px'
			height       = bodySize[1] + 'px';
			zIndex       = 98;
			if (isIE()){
				filter = "Alpha(Opacity=60)";
			} else {
				opacity = 0.6;
			}
		}
		coverDiv.id = 'gray_div';
	}
	
	//js动态创建一个你自己需要操作的DIV
	function oursDiv(){
		var signDiv = document.createElement('div');
		document.body.appendChild(signDiv);
		with (signDiv.style){
			display    = 'none';
			cursor     = 'move';
			position   = 'absolute';
			left       = 870 + 'px';
			top        = 130 + 'px';
			width      = loginDivWidth + 'px';
			zIndex     = 99;
		}
		signDiv.id    = 'ours_div';
		signDiv.align = "center";
		document.getElementById("ours_div").innerHTML = '<a href="javascript:closeDiv()"><img src="http://cdn.aisonghua.com/skins/tese/images/display.gif" /></a>';
		var obj        = $(".conone_t_r");
		var marginLeft = obj.offset().left - 240;
		var marginTop  = obj.offset().top + obj.height() + 10;
		$("#ours_div").get(0).style.left = marginLeft + 'px';
		$("#ours_div").get(0).style.top  = marginTop + 'px';
	}
	
	//弹出当前要弹出的DIV
	function showDiv(){
		document.getElementById("gray_div").style.display = '';
		document.getElementById("ours_div").style.display = '';
		document.body.style.overflow = "hidden";
	}
	
	//关闭当前弹出的DIV
	function closeDiv(){
		document.getElementById("gray_div").style.display = 'none';
		document.getElementById("ours_div").style.display = 'none';
		document.body.style.overflow = '';
	};
	
	//得到页面的高度和宽度
	function getBodySize(){
		var bodySize = [];
		with (document.documentElement){
			bodySize[0] = (scrollWidth > clientWidth) ? scrollWidth : clientWidth;
			bodySize[1] = (scrollHeight > clientHeight) ? scrollHeight : clientHeight;
		}
		return bodySize;
	}
</script>
<script type="text/javascript" src="./picShare_files/myou.event.min.js" async="" charset="utf-8"></script><link type="text/css" rel="stylesheet" href="./picShare_files/win.css"><script type="text/javascript" src="./picShare_files/myou.utils.min.js" async="" charset="utf-8"></script><script type="text/javascript" src="./picShare_files/myou.res.province.min.js" async="" charset="utf-8"></script><script type="text/javascript" src="./picShare_files/myou.res.city.min.js" async="" charset="utf-8"></script><script type="text/javascript" src="./picShare_files/myou.res.region.min.js" async="" charset="utf-8"></script><script type="text/javascript" src="./picShare_files/myou.drag.min.js" async="" charset="utf-8"></script><script type="text/javascript" src="./picShare_files/myou.win.min.js" async="" charset="utf-8"></script><script type="text/javascript" src="./picShare_files/myou.tools.provinces.min.js" async="" charset="utf-8"></script></head>
<body><div style="display: none; position: absolute;" class=""><div class="aui_outer"><table class="aui_border"><tbody><tr><td class="aui_nw"></td><td class="aui_n"></td><td class="aui_ne"></td></tr><tr><td class="aui_w"></td><td class="aui_c"><div class="aui_inner"><table class="aui_dialog"><tbody><tr><td colspan="2" class="aui_header"><div class="aui_titleBar"><div class="aui_title" style="cursor: move; display: block;"></div><a class="aui_close" href="javascript:/*artDialog*/;" style="display: block;">×</a></div></td></tr><tr><td class="aui_icon" style="display: none;"><div class="aui_iconBg" style="background-image: none; background-position: initial initial; background-repeat: initial initial;"></div></td><td class="aui_main" style="width: auto; height: auto;"><div class="aui_content" style="padding: 20px 25px;"></div></td></tr><tr><td colspan="2" class="aui_footer"><div class="aui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="aui_e"></td></tr><tr><td class="aui_sw"></td><td class="aui_s"></td><td class="aui_se" style="cursor: se-resize;"></td></tr></tbody></table></div></div>
	<div class="conbody">
		




<script type="text/javascript">
/**
 * 弹出登录窗口
 */
function shopLogin(){
	art.dialog.open('/login.html',{title: '登录', width: 659, height: 369});
}
/**
 * 弹出注册窗口
 */
function shopRegister(){
	art.dialog.open('/register.html',{title: '注册', width: 659, height: 369});
}
function exits(){
	$.ajax({
		type : "GET",
		dataType : "text",
		url : "/exit.json",
		success : function(data){
			art.dialog('用户退出!', function(){window.location.reload();});
		}
	});
}
</script>
<div class="contop">
			<div class="contop_c">
				<a href="http://pin.aisonghua.com/"></a><b style="font-weight: normal; float: right; margin-top: 9px;">
				
	    	  </b>
			     
			   
			</div>
		</div>
		<div class="concon">
			<div class="content">
				<div class="conone">
					<div class="conone_t">
						<div class="conone_t_l">
							
							<ul>
								<li class="hover"><a href="login?query=query" title="图片广场">图片广场</a>
								</li>
								<% String  linkUrl = pkMember == null ? "login?query=query" : "login?query=query&pkMember=" + pkMember; %>
								<li><a href="<%=linkUrl %>" title="只看我的分享">我的分享</a>
								</li>
							</ul>
						</div>
						
					</div>
					
					<!-- 循环数据开始 -->
					<div class="conone_c">
						
							        <div class="condiv_s">
							             
							                <%
							                for(Map<String,Object> resMap : resList){%>
							                	<div class="condiv">
												<div class="condiv_img">
													<a>
														<img src="<%=resMap.get("PIC_PATH") %>" width="237" height="237" alt="<%=resMap.get("PIC_NAME") %>">
													</a>
												</div>
												
												<div class="condiv_div2">
													<p><%=resMap.get("PIC_NAME") %></p>
												</div>
											</div>
							                <%}
							              %>
							    	</div>      				 
						
					</div>
					<!-- 循环数据结束 -->
					<div class="confanye">
					
					<ul class="pagedown">
								<%
								    int pageNo       = (Integer) request.getAttribute("page");
								    int totalPage    = (Integer) request.getAttribute("totalPage");
								    String myUrl = "login?query=query";
								    if(myUrl != null){
								    	myUrl += "&pkMember=" + pkMember;
								    }
								    boolean needFirst = pageNo != 1 && totalPage != 0;
								    out.println("<li><a class='next'"
								            + (needFirst ? " href='" + myUrl + "&page=1' " : "") + ">首页</a></li>");
								    if (needFirst) {
								        out.println("<li><a class='current' href ='" + myUrl + "&page=" + (pageNo - 1) + "'>上一页</a></li>");
								    }
								    // 循环页数与当前页比较,如果在其范围内(前三后三),将其连接显示出来
								    for (int i = 1; i <= totalPage; i++) {
								        if (i > pageNo - 3 && i < pageNo + 3) {
								            boolean isHover = i == pageNo;
								            out.println("<li><a "
								                    + i
								                    + (isHover ? " class='hover' " : " href='" + myUrl + "&page=" + i + "'") + ">" + i + "</a></li>");
								        }
								    }
								    boolean needEnd = pageNo != totalPage && totalPage != 0;
								    if (needEnd && ((pageNo + 1) <= totalPage)) {
								        out.println("<li><a class='con' href =" + myUrl + "&page=" + (pageNo + 1) + ">下一页</a></li>");
								    }
								    out.println("<li><a class='next'"
								            + (needEnd ? " href='" + myUrl + "&page=" + totalPage + "'" : "") + ">末页</a></li>");
								%>
							</ul>
					
					</div>
				</div>
				
<div class="confoot">
					
					<div class="confoot_r">
						<div class="confoot_div">
							<span>图分享-2013 Resered devise by</span>
							<a href="http://pin.aisonghua.com/women" target="_blank" title="弓鸿烨 朱绍昊">弓鸿烨 朱绍昊</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


<div style="display: none; position: fixed; left: 0px; top: 0px; width: 100%; height: 100%; cursor: move; opacity: 0; background-color: rgb(255, 255, 255); background-position: initial initial; background-repeat: initial initial;"></div></body></html>
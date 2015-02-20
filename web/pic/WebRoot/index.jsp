<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图分享</title>
<link rel="stylesheet" type="text/css" href="css/style.css"/>
<script>
var error = '<%=request.getAttribute("error")%>';
if(error != 'null'){
	alert(error);
}
function logins(){
	document.getElementById("login-form").submit();
}
</script>
</head>

<body>

	<div id="startpage" class="login-bg" style="margin-left: 0px;">
		<div id="login-wrap">
			<div id="login-form-wrap">
				<form id="login-form" class="clearfix" style="margin-top: 150px; margin-left: 500px;"
					action="login" method="post">
					<div class="input-wrap" id="input-login-email">
						<span class="input-icon" style="background-position: 0 -197px;"></span>
						<label style="display: none;">用户名</label>
						<input class="startpage-input-text" type="text" name="account" value=""/>
					</div>
					<div class="input-wrap" id="input-login-pwd">
						<span class="input-icon" style="background-position: 0 -300px;"></span>
						<label style="display: none;">密码</label>
						<input class="startpage-input-text" type="password" name="password" value=""/>
						<div class="tip" style="display: none"></div>
					</div>
					<input type="hidden" name="nextUrl" value=""/>
					<input type="hidden" name="lcallback" value=""/>
					<input type="hidden" name="persistent" value="1" checked="checked"/>
					<input type="submit" name="login-submit" class="input-button hidden-submit" value="登录"/>
					<div cloud="" id="ctrlbuttonlogin-submit" data-control="login-submit" class="ui-button skin-button-willblue" style="width: 283px;">
					<span class="ui-button-bg-left skin-button-willblue-bg-left"></span>
					<div id="ctrlbuttonlogin-submitlabel" class="ui-button-label skin-button-willblue-label" onclick="logins()">
						<span id="ctrlbuttonlogin-submittext" class="ui-button-text skin-button-willblue-text">登录</span>
					</div>
					</div>
				</form>
			</div>
			<div id="third-party-wrap" class="login-third-party-wrap"></div>
		</div>
		<div id="footer" style="bottom: 30px;">
			<span class="footer-item">图分享-2013&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;devise by </span> <a class="footer-item"
				href="http://yuntixing.com"> &nbsp;by弓鸿烨 朱绍昊</a>
		</div>
	</div>

</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SpringMVC_demo</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/common.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/themes/icon.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
function showDialog(){
	$('#dialog_login').dialog({
	    title: '用户登录',
	    buttons: '#buttons',
	    cache: false,
	    modal: false
	});
	$('#dialog_login').dialog('open');
}

function login(){
	var options = {
		success: function (data){
        	if($.parseJSON(data).result == 'success'){
        		window.location.href = '<%=path %>/common/toMain.do';
        	}else{
        		$.messager.alert('温馨提示','用户名或密码错误！', 'info');
        	}
        },
		error:function(){
			$.messager.alert('温馨提示','操作失败！', 'error');
		}
	};
	$('#loginForm').form('submit', options);
}
</script>
</head>
<body>
<img src="<%=path %>/images/background.gif" width="100%" height="100%" alt="点击任意开登录" title="点击任意开始登录" onclick="showDialog();">
<div id="dialog_login" class="easyui-dialog" data-options="iconCls:'icon-man',closed: true">
	<form id="loginForm" action="<%=path %>/common/login.do" method="POST">
		<table style="padding:30px">
			<tr>
				<td align="center">
					<div style="margin-bottom:10px">
			            <input type="text" class="easyui-textbox" id="userName" name="userName"
			            	style="width:200px;height:40px;padding:12px" value="system"
			            	data-options="prompt:'用户名',iconCls:'icon-man',iconWidth:38,required:true,validType:'length[6,20]'"
			            	missingMessage="用户名必须填写" invalidMessage="用户名长度应介于6和20之间" onkeyup="value=value.replace(/[^\w\/]/ig,'')">
			        </div>
			        <div style="padding: 10px;"></div>
			        <div style="margin-bottom:20px">
			            <input type="password" class="easyui-textbox" id="password" name="password"
			            	style="width:200px;height:40px;padding:12px" value="12345678"
			            	data-options="prompt:'密码',iconCls:'icon-lock',iconWidth:38,required:true"
			            	missingMessage="密码不能为空">
			        </div>
			        <div style="margin-bottom:20px">
			            <input type="checkbox" checked="checked">
			            <span>记住我</span><font color="red">（暂时记不住）</font>
			        </div>
			        <div>
			            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="padding:5px;width:200px;" onclick="login();">
			                <span style="font-size:14px;">登录</span>
			            </a>
			        </div>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>


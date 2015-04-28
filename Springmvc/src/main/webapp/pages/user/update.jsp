<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>update</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/common.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/themes/icon.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<style scoped="scoped">
.textbox {
	height: 20px;
	margin: 0;
	padding: 0 2px;
	box-sizing: content-box;
}
</style>
<script type="text/javascript">
function update(){
	var validate = $("#form1").form('enableValidation').form('validate');
	if(validate){
		$("#form1").submit();
		waiting();
	}
}
function returnQuery(){
	var form = document.forms[1];
	form.action = "<%=path%>/user/queryUser.do";
	form.submit();
	waiting();
}
function downloadFile(id){
	window.location.href = '<%=path %>/user/downloadFile.do?userId='+id;
}
function deleteFile(id){
	$.messager.confirm('温馨提示','是否删除附件？', function(y){
		if(y){
			$.ajax({
				async: false,
		        cache: false,
		        type: "POST",
		        url: "<%=path %>/user/deleteFile.do",
		        data: "userId="+id,
		        global: false,
		        success: function(data){
		        	$.messager.alert('温馨提示','附件删除成功！', 'info', function(){
		        		window.location.href = '<%=path %>/user/editUser.do?userId='+id;
		    		});
		        },
				error:function(){
					$.messager.alert('温馨提示','系统异常，请联系管理员！', 'info');
				}
		    });
		}
	});
}
</script>
</head>
<body style="background: #FFFFFF">
	<form id="form1" name="form1" method="post" action="<%=path%>/user/updateUser.do" enctype="multipart/form-data">
		<fieldset class="fieldset-self">
		<legend>更新用户信息</legend>
		<table width="100%">
			<tr>
				<td align="right">用户名：</td>
				<td align="left"><form:input path="user.userName"
						id="userName" cssClass="easyui-textbox"
						data-options="required:true,validType:'length[6,20]'"
						missingMessage="用户名必须填写" readonly="true" /></td>
				<td align="right">电子邮件：</td>
				<td align="left"><form:input path="user.mail" id="mail"
						cssClass="easyui-textbox" missingMessage="电子邮件必须填写"
						data-options="required:true,validType:'email'" /></td>
				<td align="right">电话号码：</td>
				<td align="left"><form:input path="user.phone" id="phone"
						cssClass="easyui-textbox"
						data-options="required:false,validType:'mobile'"
						invalidMessage="手机号码格式不正确" /></td>
				<td align="right">用户级别：</td>
				<td align="left">
					<!-- 
					<form:select path="user.userLevel" items="${map }" id="userLevel"
						name="userLevel">
					</form:select>
					-->
					<form:select path="user.userLevel" id="userLevel"
						cssClass="easyui-combobox" cssStyle="width:150px;"
						data-options="required:true,editable:false">
						<form:options items="${userLevelMap }" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td align="right">姓名：</td>
				<td align="left">
					<form:input path="user.trueName" id="trueName" cssClass="easyui-textbox"
						data-options="required:true" missingMessage="姓名必须填写" /></td>
				<td align="right">所在部门：</td>
				<td align="left">
					<input id="deptName" class="easyui-validatebox textbox" value="${deptName }" 
						data-options="required:true" missingMessage="所在部门必须填写" readonly="true"/>
					<form:hidden path="user.deptId" id="deptId"/>
					<a href="javascript:void(0);" class="easyui-linkbutton"
							data-options="plain:true,iconCls:'icon-open'" onclick="openDeptTree();"></a>
				</td>
				<c:if test="${user.fileName != null && user.fileName != '' }">
				<td align="center" colspan="2">
					${user.fileName }&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-download'"
						onclick="downloadFile('${user.userId}')">下载</a>&nbsp;
					<a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-clear'"
						onclick="deleteFile('${user.userId}')">删除</a>&nbsp;
					<div style="display: none;"><input type="file" name="fileData"></div>
				</td>
				</c:if>
				<c:if test="${user.fileName == null || user.fileName == '' }">
				<td align="right">附件：</td>
				<td align="left" colspan="3">
					<input class="easyui-filebox" name="fileData"
						data-options="prompt:'选择文件',buttonText:'选择文件'" style="width:100%"/>
				</td>
				</c:if>
			</tr>
			<tr>
				<td colspan="8">
					<div align="center">
						<form:hidden path="user.userId" id="userId" />
						<a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-save" onclick="update();">保存</a>&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-cancel" onclick="returnQuery();">取消</a>&nbsp;
					</div>
				</td>
			</tr>
		</table>
		</fieldset>
	</form>
	<form></form>
	<div id="dialog">
		<div id="tree"></div>
		<div id="dlg-toolbar" style="display: none;">
			<a href="javascript:void(0)" class="easyui-linkbutton" 
				data-options="iconCls:'icon-ok',plain:true"
				onclick="selectDept('deptId','','deptName',true);">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" 
				data-options="iconCls:'icon-cancel',plain:true"
				onclick="closeDialog();">关闭</a>
	    </div>
    </div>
</body>
</html>

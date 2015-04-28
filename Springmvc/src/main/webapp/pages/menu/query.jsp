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
<title>query</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/common.css"/>
<link rel="stylesheet" type="text/css" href="<%=path %>/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=path %>/themes/icon.css"/>
<script type="text/javascript" src="<%=path %>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<style type="text/css">

</style>
<script type="text/javascript">
function add(){
	var form = $("#form2");
	form.attr("action", "<%=path%>/menu/addMenu.do");
	form.attr("method", "post");
	form.submit();
	waiting();
}

function update(){
	var menuId = $("input[type='radio']:checked").val();
	//var menuId = $('input:radio:checked').val();
	//var menuId = $("input[name='rd']:checked").val();
	
	if(menuId == null){
		$.messager.alert('温馨提示', '请选择记录！', 'info');
		return false;
	}else{
		$("#menuId").val(menuId);
		var form = $("#form2");
		form.attr("action", "<%=path%>/menu/editMenu.do");
		form.attr("method", "post");
		form.submit();
		waiting();
	}
}

function del(){
	var menuId = $("input[type='radio']:checked").val();
	//var menuId = $('input:radio:checked').val();
	//var menuId = $("input[name='rd']:checked").val();
	
	if(menuId == null){
		$.messager.alert('温馨提示', '请选择记录！', 'info');
		return false;
	}
	$.messager.confirm('温馨提示', '是否删除所选记录？', function(y){
		if(y){
			$("#menuId").val(menuId);
			var options = {
				//type: 'post',
				url: '<%=path%>/menu/deleteMenu.do',
				success: function (data){
					var result = $.parseJSON(data).result;
		        	if(result == '1'){
		        		$.messager.alert('温馨提示','菜单删除成功！', 'info', function(){
		        			window.location.href = '<%=path%>/menu/queryMenu.do';
		        			waiting();
		        		});
		        	}else{
		        		$.messager.alert('温馨提示','菜单删除失败！', 'info');
		        	}
		        },
				error:function(){
					$.messager.alert('温馨提示','操作失败！', 'error');
				}
			};
			$('#form2').attr("method", "post");
			$('#form2').form('submit', options);
		}else {
			return false;
		}
	}); 
}

function queryData(){
	$("#form1").submit();
	waiting();
}

function returnQuery(){
	var form = document.forms[3];
	form.action = "<%=path%>/menu/queryMenu.do";
	form.submit();
	waiting();
}
</script>
</head>
<body style="background: #FFFFFF">
	<form id="form1" action="<%=path%>/menu/queryMenu.do" method="post">
	<fieldset class="fieldset-self">
	<legend>查询菜单信息</legend>
	<table>
		<tr>
			<td align="right">菜单名称：</td>
			<td align="left"><form:input path="menu.menuName" id="menuName" cssClass="easyui-textbox"/></td>
			<td align="left" colspan="6">
				<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="queryData();">查询</a>&nbsp;
				<a href="#" class="easyui-linkbutton" icon="icon-reload" onclick="returnQuery();">重置</a>&nbsp;
			</td>
		</tr>
	</table>
	</fieldset>
	</form>
	<table>
		<tr><td>
			<a href="#" class="easyui-linkbutton" icon="icon-add" onclick="add();">添加</a>&nbsp;
			<a href="#" class="easyui-linkbutton" icon="icon-edit" onclick="update();">修改</a>&nbsp;
			<a href="#" class="easyui-linkbutton" icon="icon-remove" onclick="del();">删除</a>&nbsp;
		</td></tr>
	</table>
	<form id="form2">
		<input type="hidden" id="menuId" name="menuId" value="">
	</form>
	<table class="table" width="100%" align="center">
	  <thead>
	  	<tr>
	      <th>选择</th>
	      <th>菜单名称</th>
	      <th>菜单地址</th>
	      <th>菜单描述</th>
	      <th>菜单类型</th>
	    </tr>
	  </thead>
	  <tbody id="tab">
	  	<c:forEach items="${menuList }" var="menu">
			<tr onmouseover="this.bgColor='#EAF2FF'" onmouseout="this.bgColor='#FFFFFF'" align="center">
				<td><input type="radio" id="ra" name="ra" value="${menu.menuId }"></td>
				<td>${menu.menuName }</td>
				<td>${menu.menuUri }</td>
				<td>${menu.menuDesc }</td>
				<td>
					<c:if test="${menu.menuType == 'system'}">系统菜单</c:if>
					<c:if test="${menu.menuType == 'web'}">网站链接</c:if>
				</td>
			</tr>
		</c:forEach>
	  </tbody>
	  <tfoot>
	      <td colspan="9">
	      	<form id="page" action="<%=path%>/menu/queryMenu.do" method="post">
		        <div class="easyui-pagination"
			        data-options="
			        	total : ${pageInfo.totalRecords },
			        	pageSize: ${pageInfo.pageSize},
			        	pageNumber: ${pageInfo.currentPage},
			        	layout:['list','sep','first','prev','links','next','last','sep','refresh'],
			        	onSelectPage: function(pageNumber, pageSize){   
			                easyPage(pageNumber, pageSize);
			            }
			        ">
			    </div>
				<form:hidden path="pageInfo.currentPage" id="currentPage"/>
				<form:hidden path="pageInfo.pageSize" id="pageSize"/>
				<form:hidden path="menu.menuName"/>
			</form>
	      </td>
	  </tfoot>
	</table>
	<form></form>
	</body>
</html>

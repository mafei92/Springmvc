<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/common.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/themes/icon.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#tree").tree({
		url: '<%=path%>/dept/getDeptTree.do',
		method: 'post',
		animate: true,
		checkbox: false,
		//lines:true,
		onBeforeExpand: function(node){//点击展开按钮事件
			$('#tree').tree('options').url = '<%=path%>/dept/getDeptTree.do?superId='+node.id;
        }, 
		onClick: function(node){//点击节点事件
			//$('#tree').tree('options').url = '<%=path%>/dept/getDeptTree.do?superId='+node.id;
			//alert(node.state);
            //$(this).tree('toggle', node.target);//点击节点展开子节点
            $("#detail").panel({
            	title: '查看部门信息',
            	href: '<%=path%>/dept/getDeptByDeptId.do?deptId='+node.id
            });
        },
        onContextMenu: function(e,node){//右键点击事件
            e.preventDefault();
            $(this).tree('select',node.target);
            var ifLeaf = node.attributes.ifLeaf;
            if(node.id == 'd10000'){
            	$('#mmRoot').menu('show',{
                    left: e.pageX,
                    top: e.pageY
                });
            }else{
            	if(ifLeaf == '0'){
               	 $('#mm0').menu('show',{
                        left: e.pageX,
                        top: e.pageY
                    });
               }else if(ifLeaf == '1'){
               	$('#mm1').menu('show',{
                       left: e.pageX,
                       top: e.pageY
                   });
               }
            }
        }
	});
})
function add(){
	 var t = $('#tree');
     var node = t.tree('getSelected');
     $("#detail").panel({
    	 title: '添加部门信息',
    	 href: '<%=path%>/dept/addDept.do?superId='+node.id
	});
}
function edit(){
	 var t = $('#tree');
     var node = t.tree('getSelected');
     $("#detail").panel({
    	 title: '编辑部门信息',
    	 href: '<%=path%>/dept/editDept.do?deptId='+node.id
	});
}
function del() {
	var t = $('#tree');
	var node = t.tree('getSelected');
	$.messager.confirm('温馨提示', '确定删除此部门?', function(y){
		if(y){
			$.ajax({
		        url: '<%=path%>/dept/deleteDept.do',
				data : 'deptId=' + node.id,
				type : 'POST',
				dataType : 'json',
				timeout : 5000,
				async : false,
				cache : false,
				global : false,
				success : function(data) {
					if (data.result == '1') {
						$.messager.alert('温馨提示', '部门信息删除成功！', 'info', function(){
							window.location.reload();
						});
					} else if (data.result == '2') {
						$.messager.alert('温馨提示', '无法删除，请先删除下级部门！', 'warning');
					} else {
						$.messager.alert('温馨提示', '部门信息删除失败！', 'error');
					}
				},
				error : function() {
					$.messager.alert('温馨提示', '操作失败！', 'error');
				}
			});
		}
	});
}
function expand() {
	var node = $('#tree').tree('getSelected');
	$('#tree').tree('expand', node.target);
}
function collapse() {
	var node = $('#tree').tree('getSelected');
	$('#tree').tree('collapse', node.target);
}
</script>
</head>
<body style="background: #FFFFFF; padding: 8px">
	<div class="easyui-layout" fit="true">
		<div id="dept" data-options="region:'west',collapsible:false"
			title="组织机构树" style="width: 25%; padding: 5px">
			<ul id="tree" class="easyui-tree"></ul>
		</div>
		<div id="detail" data-options="region:'center'" title=""
			style="width: 100%; padding: 5px"></div>
	</div>
	<div id="mmRoot" class="easyui-menu" style="width: 120px;">
		<div onclick="add()" data-options="iconCls:'icon-add'">添加</div>
		<div onclick="edit()" data-options="iconCls:'icon-edit'">编辑</div>
	</div>
	<div id="mm0" class="easyui-menu" style="width: 120px;">
		<div onclick="add()" data-options="iconCls:'icon-add'">添加</div>
		<div onclick="edit()" data-options="iconCls:'icon-edit'">编辑</div>
		<div onclick="del()" data-options="iconCls:'icon-remove'">删除</div>
		<div class="menu-sep"></div>
		<div onclick="expand()">展开</div>
		<div onclick="collapse()">关闭</div>
	</div>
	<div id="mm1" class="easyui-menu" style="width: 120px;">
		<div onclick="add()" data-options="iconCls:'icon-add'">添加</div>
		<div onclick="edit()" data-options="iconCls:'icon-edit'">编辑</div>
		<div onclick="del()" data-options="iconCls:'icon-remove'">删除</div>
	</div>
</body>
</html>
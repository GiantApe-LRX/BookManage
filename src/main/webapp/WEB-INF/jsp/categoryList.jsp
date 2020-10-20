<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>图书后台管理</title>
        <link rel="stylesheet" href="css/index.css">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <script type="text/javascript" src="/js/jquery-3.5.1.min.js"></script>
        <script type="text/javascript" src="/js/sweetalert2.js"></script>
        <script>
        	function del(delObj){
        		var id = $(delObj).attr("data-id");
        		var name = $(delObj).attr("data-name");
        		Swal.fire({
        			type:"warning",
        			title:"确认删除["+name+"]分类吗",
        			confirmButtonColor: '#d33',// 确定按钮的 颜色
        		    confirmButtonText: '确定',// 确定按钮的 文字
        		    showCancelButton: true, // 是否显示取消按钮
        		    cancelButtonColor: '#3085d6', // 取消按钮的 颜色
        		    cancelButtonText: "取消", // 取消按钮的 文字
        		    focusCancel: true // 是否聚焦 取消按钮
        		}).then(function(result){
        			if(result.value == true){
        				$.ajax({
        					"url":"/category?action=delete&categoryId="+id,
							"type":"Get",
							"dataType":"json",
							"success": function(json){
								if(json.result == "ok"){
									Swal.fire({
										type : 'error',
										title : "删除成功"
									}).then(function(){
										window.location.reload();
									});
								}else{
									Swal.fire({
										title : json.result
									});
								} 
								console.log(json);
							}
        				})
        			}
        		});
        	}
        </script>
    </head>

    <body>
       <header>
            <div class="container">
                    <nav>
                            <a href="/book?action=list" >图书信息管理</a>
                    </nav>
                    <nav>
                            <a href="/category?action=list" >分类管理</a>
                    </nav>
                   
            </div>
        </header>
        <section class="banner">
            <div class="container">
                <div>
                    <h1>图书管理系统</h1>
                    <p>图书分类管理</p>
                </div>
            </div>
        </section>
        <section class="main">
            <div class="container">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>分类编号</th>
                        <th>分类名称</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.categories }" var="category" varStatus="status">
                            <tr>
                                <td>${status.index+1}</td>
                                <td>${category.id}</td>
                                <td>${category.name}</td>
                                <td><a data-name="${category.name}" data-id="${category.id}" href="javascript:void(0)" onclick="del(this)">删除</a></td>
                                <!--在循环显示数据时，此处的ca0001可以用EL表达式进行替换-->
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>
        <section class="page">
            <div class="container">
                <div id="fatie">
                    <a href="/category?action=appendPage"><button>新建</button></a>
                </div>
            </div>
        </section>
        <footer>
            copy@慕课网
        </footer>
    </body>
</html>
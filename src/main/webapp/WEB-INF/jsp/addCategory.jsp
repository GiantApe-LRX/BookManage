<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>新建图书分类</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="/css/add.css">
        <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
        <script type="text/javascript" src="js/sweetalert2.js"></script>
        <script type="text/javascript" src="js/check.js"></script>
        <script>
        	$(function(){
        		$("#addCategoryFrm").submit(function(){
        			var hasNotBlank = isNotBlank('categoryId', 'errCategoryId') && isNotBlank('categoryName', 'errCategoryName');
					var hasNum = isId('categoryId', 'errCategoryId', '请输入数字编号');
					if (!hasNotBlank){
        				massage = "请检查表单中是否有未填写的内容";
        			}else if(!hasNum){
        				massage = "请检查表单中是否有错误的格式";
        			}
        			if(hasNotBlank && hasNum){
        				Swal.fire({
	        				  type: 'success',
	        				  title: "添加成功" ,
	        				  showConfirmButton: false,
	        			});
        				return true;
        			}else{
	        			Swal.fire({
	        				  type: 'error',
	        				  title: massage
	        			});
        				return false;
        			}
        		});
        	})
        </script>
    </head>
    <body>
        <nav class="navbar navbar-default">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/category?action=list">
                        	图书分类管理
                    </a>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="jumbotron">
                <h1>Hello, ${user }!</h1>
                <p>请小心地新增图书分类，要是建了一个错误的就不好了。。。</p>
            </div>
            <div class="page-header">
                <h3><small>新建</small></h3>
            </div>
            <form id="addCategoryFrm" class="form-horizontal" action="/category?action=add" method="post">
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">分类ID ：</label>
                    <div class="col-sm-8">
                        <input name="categoryId" class="form-control" id="categoryId" onblur="isId('categoryId', 'errCategoryId', '请输入数字编号');">
                    	<span id="errCategoryId" class="errSpan"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">分类名称 ：</label>
                    
                    <div class="col-sm-8">
                        <input name="categoryName" class="form-control" id="categoryName" onblur="isNotBlank('categoryName', 'errCategoryName')">
                        <span id="errCategoryName" class="errSpan"></span>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary">保存</button>&nbsp;&nbsp;&nbsp;
                    </div>
                </div>
            </form>
        </div>
        <footer class="text-center" >
            copy@imooc
        </footer>
    </body>
</html>

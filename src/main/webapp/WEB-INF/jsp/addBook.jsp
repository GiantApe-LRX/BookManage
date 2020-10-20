<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>新建图书信息</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/add.css">
        <script type="text/javascript" src="js/check.js"></script>
       	<script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
        <script type="text/javascript" src="js/sweetalert2.js"></script>
    </head>
    <body>
        <nav class="navbar navbar-default">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/book?action=list">
                        图书信息管理
                    </a>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="jumbotron">
                <h1>Hello, ${user }</h1>
                <p>请小心地新增图书信息，要是建了一个错误的就不好了。。。</p>
            </div>
            <div class="page-header">
                <h3><small>新建</small></h3>
            </div>
            <form id="addBookFrm" class="form-horizontal" action="/book?action=add" method="post"
            	enctype="multipart/form-data" onsubmit="return checkSubmitBook()"
            >

                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">图书编号 ：</label>
                    <div class="col-sm-8">
                        <input name="bookId" class="form-control" id="bookId" onblur="isId('bookId', 'errBookId', '请输入数字编号');">
                        <span id="errBookId" class="errSpan"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">图书名称 ：</label>
                    <div class="col-sm-8">
                        <input name="bookName" class="form-control" id="bookName" onblur="isNotBlank('bookName', 'errBookName')">
                        <span id="errBookName" class="errSpan"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="categoryId" class="col-sm-2 control-label">分类 ：</label>
                    <select id="categoryId" name="categoryId" class="col-sm-2 form-control" style="width: auto;margin-left: 15px">
                    	<c:forEach items="${categories }" var="category" varStatus="status">
                       		<c:if test="${status.index == 0 }">
                       			<option value="${ category.id }" selected="">${category.name }</option>
                       		</c:if>
                       		<c:if test="${status.index != 0 }">
                       			<option value="${ category.id }">${category.name }</option>
                       		</c:if>
                    	</c:forEach>
                       <!-- 下拉列表的内容要从分类中进行读取，value值是分类id -->
                    </select>
                </div>

                 <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">价格 ：</label>
                    <div class="col-sm-8">
                        <input name="bookPrice" class="form-control" id="bookPrice" onblur="isPrice('bookPrice', 'errBookPrice', '请输入正确的书籍价格')">
                        <span id="errBookPrice" class="errSpan"></span>
                    </div>
                  </div>
                   
                  <div class="form-group" >
                    <label for="name" class="col-sm-2 control-label">图书封面 ：</label>
                    <span id="errBookPic" class="errSpan"></span>
                    <input type="file" id="bookPic" name="bookPic" style="padding-left: 15px" onchange="checkFile('bookPic', 'errBookPic')">
                  </div>

                  <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">备注 ：</label>
                    <div class="col-sm-8">
                        <input name="remarks" class="form-control" id="remarks" onblur="isNotBlank('remarks', 'errRemarksSpan')">
                        <span id="errRemarksSpan" class="errSpan"></span>
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

<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>图书后台管理</title>
        <link rel="stylesheet" href="css/index.css">
        <link rel="stylesheet" href="css/bootstrap.min.css">
      	<script type="text/javascript" src="/js/jquery-3.5.1.min.js"></script>
      	<script type="text/javascript" src="/js/sweetalert2.js"></script>
      	<script type="text/javascript">
      		function deleteBook(obj){
      			var id = $(obj).attr("data-id");
      			var name = $(obj).attr("data-name");
      			Swal.fire({
      				type:"warning",
        			title:"确认删除["+name+"]吗",
        			confirmButtonColor: '#d33',// 确定按钮的 颜色
        		    confirmButtonText: '确定',// 确定按钮的 文字
        		    showCancelButton: true, // 是否显示取消按钮
        		    cancelButtonColor: '#3085d6', // 取消按钮的 颜色
        		    cancelButtonText: "取消", // 取消按钮的 文字
        		    focusCancel: true, // 是否聚焦 取消按钮

        		}).then(function(result){
        			if(result.value == true){
        				$.ajax({
        					"url":"/book?action=delete&bookId="+id,
							"type":"Get",
							"dataType":"json",
							"success": function(json){
								if(json.result == "ok"){
									Swal.fire({
										type : "success",
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
      		function listByCategoryId(){
					categoryName = $("#searchContent").val();
					if(categoryName != "" ){					
	      				$.ajax({
	      					"url":"/book",
	      					"data":{"action":"listByCategoryId",
	      							"categoryName":categoryName},
	      					"type":"get",
	      					"dataType":"json",
	      					"success": function(books){
	      						var content = "";
	      						for(var i = 0; i < books.length; i++){
	      							var book = books[i];
	      							content += "<tr id='tr"+ (i+1) +"'>";
	                                content += "<td>"+(i+1)+"</td>";
	                                content += "<td>"+book.id+"</td>";
	                                content += "<td>"+book.name+"</td>";
	                                content += "<td>" + categoryName + "</td>";
	                                content += "<td>￥"+book.price.toFixed(2)+"</td>";
	                                content += "<td><img src='"+book.picture+"'></td>";
	                                content += "<td>";
		                            content += "<a href=\"/book?action=updatePage&bookId="+book.id+"\">修改</a> ";
		                            content += "<a id= 'delete' data-id=\""+book.id+"\" data-name=\""+book.name+"\" href='javascript:void(0)' onclick='deleteBook(this);'>删除</a>";
	                                content += "</td>";
	                            	content += "</tr>";
	      						}
	      						$("#cont").html(content);
	      					},
	      					"error":function (){
	      						content = "<tr><td colspan='7' align='center' style='color:red; font-weight:bold;'>找不到该分类结果</td></tr>"
	      						$("#cont").html(content);
	      					}
	      				});
					}else{
						window.location.reload();
					}
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
                    <p>图书信息管理</p>
                </div>
            </div>
        </section>
        <section class="main">


            <div class="container">
                <form class="form-horizontal" action="" method="post">
                <div class="form-group"  style="float: right;">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="button" onclick="listByCategoryId();" class="btn btn-primary" id="search">查询</button>&nbsp;&nbsp;&nbsp;
                    </div>
                </div>
                <div class="form-group" style="float: right;width: 300px;">
                    <div class="col-sm-8">
                        <input name="searchContent" class="form-control" id="searchContent"
                        placeholder="输入要查询的分类" style="width: 250px" onkeydown="if(event.keyCode==13){listByCategoryId(); return false;}">
                    </div>
                </div>
            </form>
            </div>
            <div class="container">

                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>图书编号</th>
                        <th>图书名称</th>
                        <th>分类</th>
                        <th>价格</th>
                        <th>图书封面</th>
                        <th>操作</th>

                    </tr>
                    </thead>
                    <tbody id="cont">
                        <c:forEach items="${books }" var="book" varStatus="status">
                            <tr id="tr${status.index+1 }">
                                <td>${status.index+1 }</td>
                                <td>${book.id }</td>
                                <td>${book.name }</td>
                                <td>
                                	<c:forEach items="${categories}" var="category">
                                		<c:if test="${category.id == book.categoryId}">
                                			${category.name}
                                		</c:if>
                                	</c:forEach>
                                </td>
                                <td>￥<fmt:formatNumber pattern="0.00" value="${book.price }"></fmt:formatNumber> </td>
                                <td><img src="${book.picture }"></td>
                                <td>
	                                <a href="/book?action=updatePage&bookId=${book.id }">修改</a>
	                                <a id="delete" data-id="${book.id }" data-name="${book.name }" href="javascript:void(0)" onclick="deleteBook(this);">删除</a>
                                </td>
                                <!--在循环显示数据时，此处的book0001可以用EL表达式进行替换-->

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>
        <section class="page">
            <div class="container">
                <div id="fatie">
                    <a href="/book?action=appendPage"><button>新建</button></a>
                </div>
            </div>
        </section>
        <footer>
            copy@慕课网
        </footer>
    </body>
</html>
<%@page contentType="text/html charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>登录</title>
        <link rel="stylesheet" href="/css/login.css">
        <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
        <script type="text/javascript" src="js/sweetalert2.js"></script>
        <script type="text/javascript">
        	$(function(){
        		$("#login-btn").click(function(){
        			var username = $("#name").val();
        			var password = $("#pwd").val();
        			if(username!=""&&password!=""){
        				$.ajax({
        					"url":"/login",
        					"data":{"username":username,"password":password},
        					"type":"post",
        					"dataType":"json",
        					"success":function(status){
        						if(status.login == "success"){
	        						Swal.fire({
	        							type:"success",
	        							title:"登陆成功"
	        						});
	        						window.location.href = "/book?action=list";
        						}else{
        							Swal.fire({
	        							type:"error",
	        							title:"用户名或密码错误"
	        						});
            						$("#pwd").val("");
        						}
        					},
        					"error":function(error){
        						Swal.fire({
        							type:"error",
        							title:"出现未知错误，请联系管理员"
        						});
        					}
        				})
        			}else{
        				Swal.fire({
        					type:"error",
        					title:"用户名和密码不能为空"
        				});
        				$("#name").val("");
						$("#pwd").val("");
        			}
        		});
        	});
        </script>
    </head>
    <body>
        <div class="login">
            <div class="header">
                <h1>
                    <a href="/login">登录</a>
                </h1>
                <button></button>
            </div>
            <form id="loginFrm" action="/login" method="post">
                <div class="name">
                    <input type="text" id="name" name="username">
                    <p></p>
                </div>
                <div class="pwd">
                    <input type="password" id="pwd" name="password">
                    <p></p>
                </div>
               
                <div class="btn-red">
                    <input type="button" value="登录" id="login-btn">
                </div>
            </form>
        </div>
    </body>
</html>
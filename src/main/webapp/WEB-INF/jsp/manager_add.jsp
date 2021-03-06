<%--
  Created by IntelliJ IDEA.
  User: 56474
  Date: 2019/2/21
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>更改密码</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js" ></script>
    <style>
        body{
            background-color: rgb(240,242,245);
        }
    </style>

</head>
<body>
<nav  style="position:fixed;z-index: 999;width: 100%;background-color: #fff" class="navbar navbar-default" role="navigation" >
    <div class="container-fluid">
        <div class="navbar-header" style="margin-left: 8%;margin-right: 1%">
            <a class="navbar-brand" href="admin_main.html">小莫机器人后台系统</a>
        </div>
        <div class="collapse navbar-collapse" >
            <ul class="nav navbar-nav navbar-left">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        管理员管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="managerlist.html">管理员列表</a></li>
                        <li class="divider"></li>
                        <li><a href="manageradd.html">增加管理员</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        用户管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="userlist.html">用户列表</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        聊天信息管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="opList.html">操作流水记录</a></li>
                        <li class="divider"></li>
                        <li><a href="acList.html">活跃度排名</a></li>
                        <li class="divider"></li>
                        <li><a href="keywordList.html">关键词排名</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <%--<li><a href="login.html"><span class="glyphicon glyphicon-user"></span>${admin.managerId}，已登录</a></li>--%>
                <li><a href="logout.html" class="nav-link">退出</a></li>
            </ul>
        </div>
    </div>
</nav>
<div style="position: relative;top: 15%">
    <c:if test="${!empty succ}">
        <div class="alert alert-success alert-dismissable">
            <button type="button" class="close" data-dismiss="alert"
                    aria-hidden="true">
                &times;
            </button>
                ${succ}
        </div>
    </c:if>
    <c:if test="${!empty error}">
        <div class="alert alert-danger alert-dismissable">
            <button type="button" class="close" data-dismiss="alert"
                    aria-hidden="true">
                &times;
            </button>
                ${error}
        </div>
    </c:if>
</div>

<div class="col-xs-6 col-md-offset-3" style="position: relative;top: 25%">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">添加管理员</h3>
        </div>
        <div class="panel-body">
            <form  id="manageredit"  action="manageradd_do.html" method="post">
                <div class="input-group">
                    <span  class="input-group-addon">管理员编号</span>
                    <input  type="text" class="form-control" name="managerId" id="managerId">
                </div>

                <div class="input-group">
                    <span class="input-group-addon">管理员姓名</span>
                    <input type="text" class="form-control" name="managerName" id="managerName"  >
                </div>
                <div class="input-group">
                    <span  class="input-group-addon">管理员密码</span>
                    <input type="text" class="form-control" name="managerPwd" id="managerPwd" >
                </div>
                <div class="input-group">
                    <span class="input-group-addon">管理员状态</span>
                    <input type="text" class="form-control" name="managerStatus" id="managerStatus"  >
                </div>
                <p style="text-align: right;color: red;position: absolute" id="info2"></p><br/>
                <input type="submit" value="添加" class="btn btn-success btn-sm" class="text-left">
                <script>
                    function mySubmit(flag){
                        return flag;
                    }
                    $("#readeredit").submit(function () {
                        // var managerId =$("#managerId").val();
                        // var managerName=$("#managerName").val();
                        // var managerPwd=$("#managerPwd").val();
                        // var managerStatus=$("#managerStatus").val();
                        if($("#managerName").val()==''||$("#managerPwd").val()==''||$("#managerStatus").val()==''){
                            alert("请填入完整管理员信息！");
                            return mySubmit(false);
                        }else {
                            return mySubmit(true);
                                }
                    })
                </script>
            </form>
        </div>
    </div>

</div>


</body>
</html>

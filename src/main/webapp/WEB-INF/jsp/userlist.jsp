<%--
  Created by IntelliJ IDEA.
  User: 56474
  Date: 2019/2/19
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户列表</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js" ></script>
    <style>
        body{
            margin: 0;
            padding: 0;
            overflow: visible;
            background-color: rgb(240,242,245);
        }
        #newsa{
            width:500px;
            height: 200px;
            position: fixed;
            left: 35%;
            top:30%;
        }
    </style>

</head>

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
                <li >
                    <a href="admin_repasswd.html" >
                        密码修改
                    </a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="login.html"><span class="glyphicon glyphicon-user"></span>&nbsp;${admin.managerId}，已登录</a></li>
                <li><a href="logout.html"><span class="glyphicon glyphicon-log-in"></span>&nbsp;退出</a></li>
            </ul>
        </div>
    </div>
</nav>

<div style="padding: 70px 550px 10px">
    <form   method="post" action="queryUser.html" class="form-inline"  id="searchform">
        <div class="input-group">
            <input type="text" placeholder="输入用户昵称" class="form-control" id="search" name="userName" class="form-control">
            <span class="input-group-btn">
                            <input type="submit" value="搜索" class="btn btn-default">
            </span>
        </div>
    </form>
    <script>
        function mySubmit(flag){
            return flag;
        }
        $("#searchform").submit(function () {
            var val=$("#search").val();
            if(val==''){
                alert("请输入关键字");
                return mySubmit(false);
            }
        })

    </script>
</div>
<div style="position: relative;top: 10%">
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
<div class="panel panel-default" style="width: 90%;margin-left: 5%">
    <div class="panel-heading">
        <h3 class="panel-title">
            用户列表
        </h3>
    </div>
    <div class="panel-body">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>用户编号</th>
                <th>用户标识id</th>
                <th>用户昵称</th>
                <th>聊天文件</th>
                <th>订阅状态</th>
                <th>删除操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="alog">
                <tr  v-for="alog in users">
                    <td><c:out value="${alog.userId}"></c:out></td>
                    <td><c:out value="${alog.userOpenId}"></c:out></td>
                    <td><c:out value="${alog.userName}"></c:out></td>
                    <td><c:out value="${alog.chatData}"></c:out></td>
                    <td><c:out value="${alog.isSubscribe}"></c:out></td>
                    <td>
                        <a href="deleteUser.html?userId=<c:out value="${alog.userId}"></c:out>"><button type="button" class="btn btn-danger btn-xs">删除</button></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    温馨提示
                </h4>
            </div>
            <div class="modal-body">
                使用结束后请安全退出。
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">知道了
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


</body>
</html>



<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>操作流水记录</title>
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

<div style="padding: 70px 550px 10px">
    <form   method="post" action="queryoperater.html" class="form-inline"  id="searchform">
        <div class="input-group">
            <input type="text" placeholder="输入用户ID" class="form-control" id="search" name="searchWord" class="form-control">
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
            操作流水记录
        </h3>
    </div>
    <div class="panel-body">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>编号</th>
                <th>用户ID</th>
                <th>开始聊天时间</th>
                <th>结束聊天时间</th>
                <th>文件名</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="alog" varStatus="s">
                <tr  v-for="alog in list">
                    <%--<td><c:out value="${alog.operatorId}"></c:out></td>--%>
                    <td>${s.count}</td>
                    <td><c:out value="${alog.userId}"></c:out></td>
                    <td><c:out value="${alog.startTime}"></c:out></td>
                    <td><c:out value="${alog.endTime}"></c:out></td>
                    <td>
                        <a  name="fileName" href="openfile.html?fileName=<c:out value="${alog.fileName}"></c:out>">
                            <c:out value="${alog.fileName}"></c:out>
                        </a>
                        <%--<a id="openfile" type="submit" href="#"><c:out value="${alog.fileName}"></c:out></a>--%>
                    </td>
                    <td><a href="deleteop.html?operatorId=<c:out value="${alog.operatorId}"></c:out>"><button type="button" class="btn btn-danger btn-xs">删除</button></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%--<script>--%>
            <%--$("#openfile").click(function () {--%>
                <%--<c:if test= "${!empty filecontent}">--%>
                <%--$("#myfile").hidden = false--%>
                <%--</c:if>--%>
            <%--});--%>
        <%--</script>--%>
    </div>
</div>

<%--<textarea   id="myfile"  cols="80" rows="20" name="filecontent" hidden="true">    ${filecontent}</textarea>--%>




</body>
</html>

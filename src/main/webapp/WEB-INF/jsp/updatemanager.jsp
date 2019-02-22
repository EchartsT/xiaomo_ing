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
    <title>修改管理员</title>
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
                        <li><a href="allreaders.html">用户信息</a></li>
                        <li class="divider"></li>
                        <li><a href="reader_add.html">增加读者</a></li>
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
            <h3 class="panel-title">修改管理员</h3>
        </div>
        <div class="panel-body">
            <form action="updatemanager.html" method="post" id="manageredit" >
                <c:forEach items="${list}" var="alog" varStatus="s">
                <div class="input-group">
                    <span  class="input-group-addon">管理员编号</span>
                    <input type="text" class="form-control" name="managerId" id="managerId"  value="<c:out value="${alog.managerId}" escapeXml="true"></c:out>">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">管理员姓名</span>
                    <input type="text" class="form-control" name="managerName" id="managerName" value="<c:out value="${alog.managerName}" escapeXml="true"></c:out>" >
                </div>
                <div class="input-group">
                    <span  class="input-group-addon">管理员密码</span>
                    <input type="text" class="form-control" name="managerPwd" id="managerPwd" value="<c:out value="${alog.managerPwd}" escapeXml="true"></c:out>" >
                </div>
                <div class="input-group">
                    <span class="input-group-addon">管理员状态</span>
                    <input type="text" class="form-control" name="managerStatus" id="managerStatus"  value="<c:out value="${alog.managerStatus}" escapeXml="true"></c:out>" >
                </div>
                </c:forEach>
                <input type="submit" value="确认" class="btn btn-success btn-sm text-left">
                <p style="text-align: right;color: red;position: absolute" id="info2"></p><br/>
                <script>
                    function mySubmit(flag){
                        return flag;
                    }
                    $("#manageredit").submit(function () {
                        var managerId =$("#managerId").val();
                        var managerName=$("#managerName").val();
                        var managerPwd=$("#managerPwd").val();
                        var managerStatus=$("#managerStatus").val();

                        if ($("#managerName").val() == '' || $("#managerPwd").val() == '' || $("#managerStatus").val() == '') {
                            alert("请填入完整管理员信息！");
                            return mySubmit(false);
                        } else {
                            mySubmit(true);
                            // $.ajax({
                            //     type: "POST",
                            //     url: "/updatemanager.html",
                            //     data: {
                            //         managerId: managerId,
                            //         managerName: managerName,
                            //         managerPwd: managerPwd,
                            //         managerStatus: managerStatus
                            //     },
                            //     dataType: "json",
                            //     success: function(data) {
                            //         if (data.stateCode.trim() == "0") {
                            //             $("#info2").text("提示:修改失败");
                            //         } else if (data.stateCode.trim() == "1") {
                            //             $("#info2").text("提示:修改成功");
                            //             window.location.href = "managerlist.html";
                            //             //window.location.href="operatorList.html";
                            //         }
                            //     }
                            //
                            // })
                        }
                    })
                </script>
            </form>
        </div>
    </div>

</div>



<script>
    function mySubmit(flag){
        return flag;
    }

    $(document).keyup(function () {
        if($("#newPasswd").val()!=$("#reNewPasswd").val()&&$("#newPasswd").val()!=""&&$("#reNewPasswd").val()!=""&&$("#newPasswd").val().length==$("#reNewPasswd").val().length){
            $("#tishi").text("两次输入的新密码不同，请检查");
        }
        else {
            $("#tishi").text("");
        }
    })

    $("#repasswd").submit(function () {
        if($("#oldPasswd").val()==''||$("#newPasswd").val()==''||$("#reNewPasswd").val()==''){
            $("#tishi").text("请填写完毕后提交");
            return mySubmit(false);
        }
        else if($("#newPasswd").val()!=$("#reNewPasswd").val()){
            $("#tishi").text("两次输入的新密码不同，请检查");
            return mySubmit(false);
        }
    })
</script>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>首页</title>
    <link rel="stylesheet" type="text/css" href="css/index.css"/>
</head>
<body>
<h1 class="index_title">欢迎来到自动化办公管理系统</h1>
<div class="index_content">
    <div class="login_content">
        <table>
            <tr>
                <td class="login_text">用户名:</td>
                <td><input name="loginAccount" id="loginAccount" type="text" value=""/></td>
            </tr>
            <tr>
                <td class="login_text">密码:</td>
                <td><input name="password" id="password" type="password" value=""/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input id="login" type="button" value="登陆"/>
                    <input id="register" type="button" value="注册"/>
                </td>
            </tr>
        </table>
    </div>
</div>
</body>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/index.js"></script>
</html>
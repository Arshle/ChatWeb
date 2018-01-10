<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>注册用户</title>
    <link rel="stylesheet" type="text/css" href="../css/register.css"/>
</head>
<body>
    <h1 class="register_title">注册新用户</h1>
    <div class="register_content">
        <table class="register_table">
            <tr>
                <td class="register_text">用户名:</td>
                <td><input type="text" name="loginAccount" id="loginAccount" value=""/></td>
            </tr>
            <tr>
                <td class="register_text">密码:</td>
                <td><input type="password" name="password" id="password" value=""/></td>
            </tr>
            <tr>
                <td class="register_text">确认密码:</td>
                <td><input type="password" name="repassword" id="repassword" value=""/></td>
            </tr>
            <tr>
                <td class="register_text">兴趣爱好:</td>
                <td><input type="text" name="interests" id="interests" value=""/></td>
            </tr>
            <tr>
                <td class="register_text">生日:</td>
                <td><input type="text" name="birthday" id="birthday" value=""/></td>
            </tr>
            <tr>
                <td class="register_text">真实姓名:</td>
                <td><input type="text" name="userName" id="userName" value=""/></td>
            </tr>
            <tr>
                <td class="register_text">身份证号:</td>
                <td><input type="text" name="identity" id="identity" value=""/></td>
            </tr>
            <tr>
                <td class="register_text">手机号:</td>
                <td><input type="text" name="mobile" id="mobile" value=""/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="button" id="register" value="注册"/>
                    <input type="button" id="back" value="返回"/>
                </td>
            </tr>
        </table>
    </div>
</body>
<script type="text/javascript" src="../js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../js/register.js"></script>
</html>
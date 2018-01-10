<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>欢迎来到首页</title>
</head>
<body>
    <h2>Hello World!</h2>
    <input id="textMessage" type="text" value=""/>
    <input id="sendText" type="button" value="发送文本消息"/><br/>
    用户名:<input id="playerName" type="text" value=""/><br>
    密码:<input id="password" type="password" value=""/><br>
    <input id="sendObject" type="button" value="发送对象消息"/><br/>
</body>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
    $(function(){
        $('#sendText').on({
            'click' : function(){
                var textMessage = $('#textMessage').val();
                $.ajax({
                    url : 'index/send',
                    data : {'message':textMessage},
                    dataType : 'json',
                    type : 'POST',
                    success : function(data){

                    }
                });
            }
        });
        $('#sendObject').on({
            'click' : function(){
                var playerName = $('#playerName').val();
                var password = $('#password').val();
                $.ajax({
                    url : 'index/sendObject',
                    data : {
                        'playerName' : playerName,
                        'password' :  password
                    },
                    dataType : 'json',
                    type : 'POST',
                    success : function(data){

                    }
                });
            }
        });
    })
</script>
</html>

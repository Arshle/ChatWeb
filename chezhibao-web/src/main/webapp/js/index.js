$(function(){
    $('#register').on({
        'click' : function(){
            window.location.href = 'user/toRegister';
        }
    });

    $('#login').on({
        'click' : function(){
            $.ajax({
                type : 'POST',
                url : 'user/login',
                data : {
                    loginAccount : $('#loginAccount').val(),
                    password : $('#password').val()
                },
                dataType : 'json',
                success : function(data){
                    if(data.flag){
                        window.location.href = '../user/loginSuccess';
                    }else{
                        alert('登陆失败');
                    }
                }
            });
        }
    });
})
$(function(){
    buttonInit();
})

/*注册按钮点击事件*/
function buttonInit() {
    //返回
    $('#back').on({
        'click' : function(){
            window.history.go(-1);
        }
    });
    //注册
    $('#register').on({
        'click' : function(){
            if(checkUserInfo()){
                var data = {};
                data.loginAccount = $('#loginAccount').val();
                data.password = $('#password').val();
                data.interests = $('#interests').val();
                data.birthday = $('#birthday').val();
                data.userName = $('#userName').val();
                data.identity = $('#identity').val();
                data.mobile = $('#mobile').val();
                $.ajax({
                    type : 'POST',
                    url : '../user/register',
                    data : data,
                    dataType : 'json',
                    success : function(data){
                        if(data.flag){
                            alert('注册成功!');
                            window.location.href = '../';
                        }else{
                            alert('注册失败,请检查填写的信息');
                        }
                    }
                });
            }
        }
    });
}

/*校验用户表单数据*/
function checkUserInfo(){
    return true;
}
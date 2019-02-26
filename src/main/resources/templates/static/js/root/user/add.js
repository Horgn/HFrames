$(function(){
  var $currentHtml = $('.HF_RootUserAddPage').parent();
  //添加用户
  $currentHtml.find('.HF_SUBMIT').on('click',function(){
    var securecode = $currentHtml.find('.HF_Securecode').val();
    if(!securecode){
      $horgn.errorMsg("获取页面令牌失败，请重试！");return false;
    }
    var usn = $currentHtml.find(".HF_USN").val();
      if(!usn){$horgn.errorMsg("请输入用户名！");return false;}
      if(usn.length < 4){$horgn.errorMsg("用户名不得少于4个字符！");return false;}
      if(usn.length > 15){$horgn.errorMsg("用户名不得超过15个字符！");return false;}
    var nic = $currentHtml.find(".HF_NIC").val();
      if(!nic){$horgn.errorMsg("请输入用户昵称！");return false;}
      if(nic.length > 10){$horgn.errorMsg("用户昵称不得超过10个字符！");return false;}

    var pwd = $currentHtml.find('.HF_IN_PWD').val();
      if(!pwd){$horgn.errorMsg("请输入密码！");return false;}
      if(pwd.length < 6){$horgn.errorMsg("密码长度不得少于6位！");return false;}
      if(pwd.length > 20){$horgn.errorMsg("密码长度不得超过20位！");return false;}
    var pwd2 = $currentHtml.find('.HF_IN_PWD2').val();
      if(!pwd2){$horgn.errorMsg("请确认密码！");return false;}
    if(pwd != pwd2){$horgn.errorMsg("两次密码不相同！");return false;}
    $('.HF_PWD').val(securecode + HMAC_SHA256_MAC(usn,pwd));
    $('.HF_PWD2').val(securecode + HMAC_SHA256_MAC(usn,pwd2));

    var code = $currentHtml.find('.HF_CODE').val();
    if(!code){$horgn.errorMsg("请输入验证码！");return false;}

    $currentHtml.find(".HF_USER_ADD_FORM").bjuiajax('ajaxForm',{callback:function(data){
      console.log(data)
      if(data.code == 1){
        $(this).dialog('close', "addUserDialog");
        refreshTab();
      }else{
        $horgn.errorMsg(data.message);
        var img = document.getElementById("captcha_img");
        img.src = 'code?'+Math.random();
        $currentHtml.find('.HF_CODE').val("");
      }
    }});
  })

})

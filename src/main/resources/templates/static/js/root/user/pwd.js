$(function(){
  var $currentHtml = $('.HF_RootUserPwdPage').parent();

  $currentHtml.find('.HF_SUBMIT_PWD').on('click',function(){
    var securecode = $currentHtml.find('.HF_Securecode').val();
    if(!securecode){
      $horgn.errorMsg("获取页面令牌失败，请重试！");return false;
    }

    var usn = $currentHtml.find(".HF_USN").val();
      if(!usn){$horgn.errorMsg("用户名错误！");return false;}

    var pwd = $currentHtml.find('.HF_IN_PWD').val();
      if(!pwd){$horgn.errorMsg("请输入旧密码！");return false;}
    var npwd = $currentHtml.find('.HF_IN_NPWD').val();
      if(!npwd){$horgn.errorMsg("请输入新密码！");return false;}
      if(pwd.length < 6){$horgn.errorMsg("密码长度不得少于6位！");return false;}
      if(pwd.length > 20){$horgn.errorMsg("密码长度不得超过20位！");return false;}
    var npwd2 = $currentHtml.find('.HF_IN_NPWD2').val();
      if(!npwd2){$horgn.errorMsg("请确认新密码！");return false;}
      if(npwd != npwd2){$horgn.errorMsg("两次新密码不相同！");return false;}
    if(pwd == npwd){$horgn.errorMsg("新密码不能与旧密码相同！");return false;}
    $currentHtml.find('.HF_PWD').val(securecode + HMAC_SHA256_MAC(usn,pwd));
    $currentHtml.find('.HF_NPWD').val(securecode + HMAC_SHA256_MAC(usn,npwd));
    $currentHtml.find('.HF_NPWD2').val(securecode + HMAC_SHA256_MAC(usn,npwd2));

    var code = $currentHtml.find('.HF_CODE').val();
    if(!code){$horgn.errorMsg("请输入验证码！");return false;}

    $currentHtml.find(".HF_USER_ADD_FORM").bjuiajax('ajaxForm',{callback:function(data){
      if(data.code == 1){
        $(this).dialog('close', "dialogId");
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

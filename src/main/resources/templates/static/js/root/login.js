var COOKIE_NAME = 'sys__username';
$(function() {

  //判断当前页面是否在子页面，如果在子页面，刷新整个页面显示
  var ae = $(this.activeElement).get(0);
  if ($(ae).is('body') == false){
    top.location.href = location.href;
  }

  $('#j_captcha').val("");
  choose_bg();
  $("#login_form").submit(function() {
    var issubmit = true;
    var i_index = 0;
    $(this).find('.in').each(function(i) {
      if ($.trim($(this).val()).length == 0) {
        $(this).css('border', '1px #ff0000 solid');
        issubmit = false;
        if (i_index == 0)
          i_index = i;
      }
    });
    if (!issubmit) {
      $(this).find('.in').eq(i_index).focus();
      return false;
    }
    var $remember = $("#j_remember");
    if ($remember.attr('checked')) {
      $.cookie(COOKIE_NAME, $("#j_username").val(), {
        path: '/',
        expires: 15
      });
    } else {
      $.cookie(COOKIE_NAME, null, {
        path: '/'
      }); //删除cookie
    }
    $("#login_ok").attr("disabled", true).val('登陆中..');

    var secucode = $('.MySecucode').val();
    if(!secucode){
      secucode = 'a4bs425f';
    }
    var username = $("#j_username").val();
    var password = $("#j_password").val();
    var checkcode = $("#j_captcha").val();
    var rememberMe = $("#j_remember").val();
    var url = $(this).get(0).action;
    var pwd = HMAC_SHA256_MAC(username,password);
    var npwd = secucode + pwd;
    var param = {url:url,username:username,password:npwd,checkcode:checkcode,rememberMe:rememberMe};
    $.post(url,param,function(data){
      if(data.code == 1){
        window.location.href = '/';
      }else{
        $horgn.errorMsg(data.message);
        $("#login_ok").attr("disabled", false).val('  登 陆  ');
      }
    });
    return false;
  });



});//end========================================================================

//更换背景
function choose_bg() {
  var bg = Math.floor(Math.random() * 3 + 1);
  $('body').css('background-image', 'url(static/images/loginbg_0' + bg + '.jpg)');
}

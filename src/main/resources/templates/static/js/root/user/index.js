$(function(){
  var $currentHtml = $('.HF_RootUserIndexPage').parent();

  //初始化完成，显示页面内容
  $currentHtml.find('.HF_USER_HIDE').show();

  $currentHtml.find(".HF_Search").click(function(){
    var sUserId = $(this).data('userId');
    $currentHtml.bjuiajax('doLoad', {url:'r/u/role?sUserId='+sUserId, target:".HF_Role_Div"});
    
  });
});

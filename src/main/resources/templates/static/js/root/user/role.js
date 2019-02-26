$(function(){
  var $currentHtml = $('.HF_RootUserRolePage').parent();

  //
  $currentHtml.find('.HF_UROLE_HIDE').show();

  //保存用户角色
  $currentHtml.find('.HF_SaveRole_btn').click(function(){
    $horgn.confirmMsg('确定更改该用户角色？',function(){
      var roleIds = [];
      $currentHtml.find('input[name="HF_Role_Check_Id"]:checked').each(function(){
        var box = $(this).get(0);
        var sId = $(box).val();
        roleIds.push(sId);
      })
      $(this).bjuiajax('doAjax', {
        url:"r/u/role",
        data:{'sUserId':$('.HF_RootUserRolePage').val(),'roleIdArrays':JSON.stringify(roleIds)},
        callback:function(data){
          // hframeCallbackMsg(data);
          $horgn.callbackMsg(data);
        }
      });
    });
    // $(this).alertmsg("confirm", "确定更改该用户角色？", {okCall:function(){
    //   var roleIds = [];
    //   $currentHtml.find('input[name="HF_Role_Check_Id"]:checked').each(function(){
    //     var box = $(this).get(0);
    //     var sId = $(box).val();
    //     roleIds.push(sId);
    //   })
    //   $(this).bjuiajax('doAjax', {
    //     url:"r/u/role",
    //     data:{'sUserId':$('.HF_RootUserRolePage').val(),'roleIdArrays':JSON.stringify(roleIds)},
    //     callback:function(data){
    //       hframeCallbackMsg(data);
    //     }
    //   });
    // }});
  });//End

});//$(function(){ End========================================================

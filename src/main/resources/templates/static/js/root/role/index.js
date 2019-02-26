$(function(){
  var $currentHtml = $('.HF_RootRoleIndexPage').parent();

  //初始化完成，显示页面内容
  $currentHtml.find('.HF_ROLE_HIDE').show();

  $currentHtml.find(".HF_Role_Per").click(function(){
    var roleId = $(this).data('roleId');
    $currentHtml.find(".HF_RootRoleIndexPage").val(roleId);
    getRolePermision($currentHtml,roleId);
    $currentHtml.find(".HF_ROLR_PERMI_DIV").show();
  });//

  //保存权限
  $currentHtml.find(".HF_SavePermi_btn").click(function(){
    $horgn.confirmMsg('确定更改该用户角色？',function(){
      var sRoleId = $currentHtml.find(".HF_RootRoleIndexPage").val();
      if(!sRoleId){
        $(this).alertmsg('error',"请选择指定角色！");
      }
      var treeObj = $.fn.zTree.getZTreeObj("HF_PERMISSION_ZTREE");
      var nodes = treeObj.getCheckedNodes(true);
      var permissionIds = [];
      for(var i=0;i<nodes.length;i++){
        var node = nodes[i];
        permissionIds.push(node.id);
      }
      var param = {'sRoleId':sRoleId,'menuIds':JSON.stringify(permissionIds)};
      // console.log(param)
      $(this).bjuiajax('doAjax', {
        url:"r/r/permi",
        type:'POST',
        data:param,
        callback:function(data){
          // hframeCallbackMsg(data);
          $horgn.callbackMsg(data);
        }
      });
    })
    // $(this).alertmsg("confirm", "确定更改该用户角色？", {okCall:function(){
    //   var sRoleId = $currentHtml.find(".HF_RootRoleIndexPage").val();
    //   if(!sRoleId){
    //     $(this).alertmsg('error',"请选择指定角色！");
    //   }
    //   var treeObj = $.fn.zTree.getZTreeObj("HF_PERMISSION_ZTREE");
    //   var nodes = treeObj.getCheckedNodes(true);
    //   var permissionIds = [];
    //   for(var i=0;i<nodes.length;i++){
    //     var node = nodes[i];
    //     permissionIds.push(node.id);
    //   }
    //   var param = {'sRoleId':sRoleId,'menuIds':JSON.stringify(permissionIds)};
    //   // console.log(param)
    //   $(this).bjuiajax('doAjax', {
    //     url:"r/r/permi",
    //     type:'POST',
    //     data:param,
    //     callback:function(data){
    //       hframeCallbackMsg(data);
    //     }
    //   });
    // }});
  })//

  function getRolePermision($currentHtml,sRoleId){
    if(!sRoleId){
      $(this).alertmsg('error',"获取角色信息失败！");
    }
    $.get('r/r/rpermi',{'sRoleId':sRoleId},function(data){
      hframeCallback(data,function(data){
        var entity = data.data.entity;
        //HF_ENRIRY_NAME
        $currentHtml.find('.HF_ENRIRY_NAME').val(entity.sRoleName);
        $currentHtml.find('.HF_ENRIRY_SIGN').val(entity.sRoleSign);
        var permissions = data.data.permissions;
        var treeObj = $.fn.zTree.getZTreeObj("HF_PERMISSION_ZTREE");

        treeObj.checkAllNodes(false);//先取消所有的选中状态
        for(var i=0;i<permissions.length;i++){
          var permi = permissions[i];
          var node = treeObj.getNodeByParam("id",permi.sMenuId);
          treeObj.checkNode(node,true,false);//将指定ID的节点选中
        }
      });
    })
  }//end===

});//end========================================================================



//取消子节点时，当所有子节点都为false，不去除父节点选择状态
function hframeRoleClickZtree(event, treeId, treeNode){
  if(!treeNode.checked){
    if(!treeNode.isParent){
      var parentNode = treeNode.getParentNode();
      var children = parentNode.children;
      var flag = true;
      for(var i=0;i<children.length;i++){
        var node = children[i];
        if(node.checked == true){
          flag = false;
        }
      }
      if(flag){
        var treeObj = $.fn.zTree.getZTreeObj("HF_PERMISSION_ZTREE");
        hf_role_loopParentCheck(treeObj,parentNode);
      }
    }
  }
}//end===

//递归选择父节点
function hf_role_loopParentCheck(treeObj,node){
  treeObj.checkNode(node,true,false);
  if(node.getParentNode()){
    loopParentCheck(treeObj,node.getParentNode());
  }
}//end===

$(function(){
  var $currentHtml = $('.HF_RootDeptIndexPage').parent();

  //初始化完成，显示页面内容
  $currentHtml.find('.HF_DEPT_HIDE').show();

})//end=======================================================================


//数据字典ztree列表点击事件
function hframeSysDeptClickZtree(event, treeId, treeNode){
  var $currentHtml = $('.HF_RootDeptIndexPage').parent();
  var sId = treeNode.id;
  $currentHtml.bjuiajax('doLoad', {url:'r/p/detail?sId='+sId, target:".HF_DEPT_DET"});
}

//添加部门回调函数
function hf_callbackDept_dept(data){
  hframeCallbackMsg(data,function(data){
    var saveEntity = data.data.saveEntity;
    if(saveEntity){
      var treeObj = $.fn.zTree.getZTreeObj("HF_DEPT_ZTREE");
      if(saveEntity.sParentId){
        var parentNode = treeObj.getNodeByParam("id",saveEntity.sParentId);
        treeObj.addNodes(parentNode, {name:saveEntity.sDeptName,id:saveEntity.sId},false);
      }else{
        treeObj.addNodes(null, {name:saveEntity.sDeptName,id:saveEntity.sId},true);
      }
    }
    if(data.data.isDelete){
      var sId = data.data.entityId;
      var treeObj = $.fn.zTree.getZTreeObj("HF_DEPT_ZTREE");
      var deleteNode = treeObj.getNodeByParam("id",sId);
      treeObj.removeNode(deleteNode);
    }
    $(this).dialog('close', $.CurrentDialog);
    var $currentHtml = $('.HF_RootDeptIndexPage').parent();
    var sId = $currentHtml.find('.HF_RootDeptInfoPage').val();
    $currentHtml.bjuiajax('doLoad', {url:'r/p/detail?sId='+sId, target:".HF_DEPT_DET"});
  })
}

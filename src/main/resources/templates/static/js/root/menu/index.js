$(function(){
  var $currentHtml = $('.HF_RootMenuIndexPage').parent();
  //获取并显示菜单列表
  // getZtreeMenu();
  //初始化完成，显示页面内容
  $currentHtml.find('.HF_MENU_HIDE').show();

  // function getZtreeMenu(){
  //   $.get('r/m/ztree',function(data){
  //     if(data.code == 1){
  //       var treeObj = $.fn.zTree.getZTreeObj("HF_MENU_ZTREE");
  //       var map = data.data.ztreeMenu;
  //       var mainMenus = map.mainMenus;
  //       if(!mainMenus){return;}
  //       //主菜单
  //       for(var i=0;i<mainMenus.length;i++){
  //         var main = mainMenus[i];
  //         treeObj.addNodes(null, {name:main.sMenuName,id:main.sId},true);
  //         //分组菜单
  //         var groupMenus = map[main.sId];
  //         if(groupMenus){
  //           for(var j=0;j<groupMenus.length;j++){
  //             var group = groupMenus[j];
  //             addTreeNodes(treeObj,group);
  //             //页面菜单
  //             var pageMenus = map[group.sId];
  //             if(pageMenus){
  //               for(var k=0;k<pageMenus.length;k++){
  //                 var page = pageMenus[k];
  //                 addTreeNodes(treeObj,page);
  //                 //元素菜单
  //                 var elementMenus = map[page.sId];
  //                 if(elementMenus){
  //                   for(var l=0;l<elementMenus.length;l++){
  //                     var element = elementMenus[l];
  //                     addTreeNodes(treeObj,element);
  //                   }
  //                 }
  //               }
  //             }
  //           }
  //         }
  //       }
  //
  //     }else{
  //       $(this).alertmsg("error", data.message);
  //     }
  //   });
  // }
  //
  // //添加节点
  // function addTreeNodes(treeObj,group){
  //   var parentNode = treeObj.getNodeByParam("id",group.sParentId);
  //   treeObj.addNodes(parentNode, {name:group.sMenuName,id:group.sId},true);
  // }//end===

})//end=======================================================================



//添加子字典后回调方法
function hf_dialogCallback_menu(data){
  // hframeCallback(data,function(data){
  $horgn.callback(data,function(data){
    if(data.data){
      //添加回调
      var isSave = data.data.isSave;
      if(isSave){
        var saveEntity = data.data.saveEntity;
        if(saveEntity.iMenuType == 0){
          refreshTab();
          $(this).dialog('close', 'addDialog');
          return;
        }
        var treeObj = $.fn.zTree.getZTreeObj("HF_MENU_ZTREE");
        if(saveEntity.sParentId){
          var parentNode = treeObj.getNodeByParam("id",saveEntity.sParentId);
          treeObj.addNodes(parentNode, {name:saveEntity.sMenuName,id:saveEntity.sId},false);
        }else{
          treeObj.addNodes(null, {name:saveEntity.sMenuName,id:saveEntity.sId},true);
        }
      }
      //删除回调
      var isDelete = data.data.isDelete;
      if(isDelete){
        var entityId = data.data.entityId;
        var treeObj = $.fn.zTree.getZTreeObj("HF_MENU_ZTREE");
        var deleteNode = treeObj.getNodeByParam("id",entityId);
        treeObj.removeNode(deleteNode);
      }
    }
    var $html = $('.HF_RootMenuInfoPage');
    var sId = $($html).val();
    var $currentHtml = $($html).parent();
    $currentHtml.bjuiajax('doLoad', {url:'r/m/detail?sId='+sId, target:".HF_MENU_DET"});
    $(this).dialog('close', 'addDialog');
  })
}//end===

//系统菜单列表ztree点击事件
function hframeSysMenuClickZtree(event, treeId, treeNode){
  var $currentHtml = $('.HF_RootMenuIndexPage').parent();
  var sId = treeNode.id;
  $currentHtml.bjuiajax('doLoad', {url:'r/m/detail?sId='+sId, target:".HF_MENU_DET"});
}

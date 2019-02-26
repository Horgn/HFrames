$(function(){
  var $currentHtml = $('.HF_RootDictIndexPage').parent();
  //初始化数据字典Ztree
  // getZtreeDict($currentHtml);
  //初始化完成，显示页面内容
  $currentHtml.find('.HF_DICT_HIDE').show();

  //初始化数据字典Ztree
  // function getZtreeDict($currentHtml){
  //   $.get('r/d/ztree',function(data){
  //     hframeCallback(data,function(data){
  //       var treeObj = $.fn.zTree.getZTreeObj("HF_DICT_ZTREE");
  //       var map = data.data.dictionaries;
  //       var parents = map.parentDicts;
  //       //根节点
  //       for(var i=0;i<parents.length;i++){
  //         var parent = parents[i];
  //         treeObj.addNodes(null, {name:parent.sDictName,id:parent.sId},true);
  //         //子节点一
  //         var sons1 = map[parent.sId];
  //         if(sons1){
  //           for(var j=0;j<sons1.length;j++){
  //             var son1 = sons1[j];
  //             addTreeNodes(treeObj,son1);
  //             //子节点二
  //             var sons2 = map[son1.sId];
  //             if(sons2){
  //               for(var k=0;k<sons2.length;k++){
  //                 var son2 = sons2[k];
  //                 addTreeNodes(treeObj,son2);
  //                 //子节点三
  //                 var sons3 = map[son2.sId];
  //                 if(sons3){
  //                   for(var l=0;l<sons3.length;l++){
  //                     var son3 = sons3[l];
  //                     addTreeNodes(treeObj,son3);
  //                   }
  //                 }
  //               }
  //             }
  //           }
  //         }
  //       }
  //     });
  //   });
  // }//end===

  //添加节点
  // function addTreeNodes(treeObj,dictionary){
  //   var parentNode = treeObj.getNodeByParam("id",dictionary.sParentId);
  //   treeObj.addNodes(parentNode, {name:dictionary.sDictName,id:dictionary.sId},true);
  // }//end===

})//End=======================================================================

//字典列表点击事件
function hframeSysDictClickZtree(event, treeId, treeNode){
  var $currentHtml = $('.HF_RootDictIndexPage').parent();
  var sId = treeNode.id;
  $currentHtml.bjuiajax('doLoad', {url:'r/d/detail?sId='+sId, target:".HF_DICT_DET"});
}//end===

//添加子字典后回调方法
function hf_dialogCallback_dict(data){
  // hframeCallbackMsg(data,function(data){
  $horgn.callbackMsg(data,function(data){
    if(data.data){
      //添加回调
      var isSave = data.data.isSave;
      if(isSave){
        var saveEntity = data.data.saveEntity;
        var treeObj = $.fn.zTree.getZTreeObj("HF_DICT_ZTREE");
        if(saveEntity.sParentId){
          var parentNode = treeObj.getNodeByParam("id",saveEntity.sParentId);
          treeObj.addNodes(parentNode, {name:saveEntity.sDictName,id:saveEntity.sId},false);
        }else{
          treeObj.addNodes(null, {name:saveEntity.sDictName,id:saveEntity.sId},true);
        }
      }
      //删除回调
      var isDelete = data.data.isDelete;
      if(isDelete){
        var entityId = data.data.entityId;
        var treeObj = $.fn.zTree.getZTreeObj("HF_DICT_ZTREE");
        var deleteNode = treeObj.getNodeByParam("id",entityId);
        treeObj.removeNode(deleteNode);
      }
    }
    var $html = $('.HF_RootDictInfoPage');
    var sId = $($html).val();
    var $currentHtml = $($html).parent();
    $currentHtml.bjuiajax('doLoad', {url:'r/d/detail?sId='+sId, target:".HF_DICT_DET"});
    $(this).dialog('close', 'addDialog');
  })
}//end===

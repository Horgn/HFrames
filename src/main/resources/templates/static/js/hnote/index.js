$(function(){
  var $currentHtml = $('.HN_HNoteIndexPage').parent();

  //初始化代码高亮
  // SyntaxHighlighter.all();
  $(document).on(BJUI.eventType.initUI, function(e) {
    SyntaxHighlighter.defaults['toolbar'] = false;
    SyntaxHighlighter.highlight();
  })

  //初始化完成，显示页面内容
  $currentHtml.find('.HN_NOTE_INDEX').show();

  //添加笔记
  $currentHtml.find('.HF_NOTE_ADD').click(function(){
    var cateId = $(this).data('cateId');
    $currentHtml.bjuiajax('doLoad', {url:'hn/nt/add?sCategoryId='+cateId, target:".HF_NOTE_DIV"});
    $currentHtml.find('.HF_N_T').text("添加笔记");
    $currentHtml.find(".HF_NOTE_DIV").show();
    $currentHtml.find(".HF_NOTE_DRTAL").hide();
  })

  //编辑分类
  $currentHtml.find('.HF_Cate_Edit').click(function(){
    var sCategoryId = $currentHtml.find('.HN_HCategoryId').val();
    if(!sCategoryId){$horgn.errorMsg("请选择指定笔记分类");return;}
    $currentHtml.dialog({id:'addDialog', url:'hn/cg/edit?sId='+sCategoryId, width:400,height:200, title:'编辑分类'});
  })
  //删除分类
  $currentHtml.find('.HF_Cate_Del').click(function(){
    var sCategoryId = $currentHtml.find('.HN_HCategoryId').val();
    if(!sCategoryId){$horgn.errorMsg("请选择指定笔记分类");return;}
    $horgn.confirmMsg('确定删除该分类吗？',function(){
      $.post('hn/cg/del',{'sId':sCategoryId},function(data){
        $horgn.callbackMsg(data,function(data){
          if(data.data.isDelete){
            var sId = data.data.entityId;
            var treeObj = $.fn.zTree.getZTreeObj("HN_CATE_ZTREE");
            var deleteNode = treeObj.getNodeByParam("id",sId);
            treeObj.removeNode(deleteNode);
          }
        })
      })
    })
    // $currentHtml.alertmsg('confirm', '确定删除该分类吗？', {okCall:function(){
    //   $.post('hn/cg/del',{'sId':sCategoryId},function(data){
    //     hframeCallbackMsg(data,function(data){
    //       if(data.data.isDelete){
    //         var sId = data.data.entityId;
    //         var treeObj = $.fn.zTree.getZTreeObj("HN_CATE_ZTREE");
    //         var deleteNode = treeObj.getNodeByParam("id",sId);
    //         treeObj.removeNode(deleteNode);
    //       }
    //     })
    //   })
    // }});
  })



})//end=======================================================================



//笔记分类及笔记列表点击事件
function hframeNoteIndexClickZtree(event, treeId, treeNode){
  var $currentHtml = $('.HN_HNoteIndexPage').parent();
  if(treeNode.cate){
    //选择分类
    $currentHtml.find('.HF_NOTE_ADD,.HF_Cate_Edit,.HF_Cate_Del').show();
    var id = treeNode.id;
    $currentHtml.find('.HF_NOTE_ADD').data('cateId',id);
    $currentHtml.find('.HN_HCategoryId').val(id);
  }else{
    //查看笔记
    var id = treeNode.id;
    var name = treeNode.name;
    $(".HF_NOTE_DRTAL").hide();
    $(".HF_NOTE_DRTAL").html('');
    $currentHtml.find('.HF_NOTE_ADD,.HF_Cate_Edit,.HF_Cate_Del').hide();
    $currentHtml.bjuiajax('doLoad', {url:'hn/nt/detail?sId='+id, target:".HF_NOTE_DRTAL"});
    $currentHtml.find('.HF_N_T').text(name);
    $(".HF_NOTE_DIV").hide();
    // $(".HF_NOTE_DRTAL").show();
  }
}

//添加\删除分类回调函数
function hf_callbackNote_cg(data){
  console.log('del')
  hframeCallbackMsg(data,function(data){
    var saveEntity = data.data.saveEntity;
    if(saveEntity){
      var treeObj = $.fn.zTree.getZTreeObj("HN_CATE_ZTREE");
      if(saveEntity.sParentId){
        var parentNode = treeObj.getNodeByParam("id",saveEntity.sParentId);
        treeObj.addNodes(parentNode, {name:saveEntity.sCategoryName,id:saveEntity.sId,cate:true},false);
      }else{
        treeObj.addNodes(null, {name:saveEntity.sCategoryName,id:saveEntity.sId,cate:true},false);
      }
    }
    if(data.data.isDelete){
      var sId = data.data.entityId;
      var treeObj = $.fn.zTree.getZTreeObj("HN_CATE_ZTREE");
      var deleteNode = treeObj.getNodeByParam("id",sId);
      treeObj.removeNode(deleteNode);
      $('.HF_NOTE_DIV').html("");
      $('.HF_N_T').text("小锤笔记");
    }
    $(this).dialog('close', $.CurrentDialog);
  })
}

//编辑分类回调
function hf_callbackCate_edit(data){
  hframeCallbackMsg(data,function(data){
    var editEntity = data.data.editEntity;
    if(editEntity){
      var treeObj = $.fn.zTree.getZTreeObj("HN_CATE_ZTREE");
      var deleteNode = treeObj.getNodeByParam("id",editEntity.sId);
      treeObj.removeNode(deleteNode);
      if(editEntity.sParentId){
        var parentNode = treeObj.getNodeByParam("id",editEntity.sParentId);
        treeObj.addNodes(parentNode, {name:editEntity.sCategoryName,id:editEntity.sId,cate:true},false);
      }else{
        treeObj.addNodes(null, {name:editEntity.sCategoryName,id:editEntity.sId,cate:true},false);
      }
    }
    $(this).dialog('close', $.CurrentDialog);
  });
}

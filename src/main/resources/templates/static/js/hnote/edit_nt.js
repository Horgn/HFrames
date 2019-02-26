
$(function(){
  var _editor;
  var $currentHtml = $('.HN_NoteAddPage').parent();

  //初始化编辑器
  initKindEditor($currentHtml);

  var nameObj = $currentHtml.find('.HF_N_Name');
  var tagObj = $currentHtml.find('.HF_N_Tag');
  var typeObj = $currentHtml.find('.HF_N_Type');
  var orderObj = $currentHtml.find('.HF_N_Order');

  //取消
  $currentHtml.find('.HF_CA').click(function(){
    $('.HF_N_T').text($('.HN_DETAIL_N').val());
    $(".HF_NOTE_DIV").hide();
    $(".HF_NOTE_DRTAL").show();
    // var sId = $(this).data('nid');
    // var name = nameObj.val();
    // $('.HF_N_T').text(name);
    // $(this).bjuiajax('doLoad', {url:'hn/nt/detail?sId='+sId, target:".HF_NOTE_DIV"});
  })
  //保存
  $currentHtml.find('.HF_AN').click(function(){
    var sId = $('.HN_NID').val();
    var iVersion = $('.HN_NVer').val();
    var sCategoryId = $('.HN_NCa').val();
    if(!sCategoryId){$horgn.errorMsg("获取所属分类失败");return;};
    var sNoteTitle = nameObj.val();
    if(!sNoteTitle){$horgn.errorMsg( "请输入笔记名称");return;};
    var sNoteTag = tagObj.val();
    var iShowType = typeObj.val();
    var iOrder = orderObj.val();
    var sNoteCentent = _editor.html();
    if(!sNoteCentent){$horgn.errorMsg( "请输入笔记内容");return;};

    var param = {'sId':sId,'iVersion':iVersion,'sNoteTitle':sNoteTitle,'sNoteContent':sNoteCentent,'sNoteTag':sNoteTag,'iShowType':iShowType,'sCategoryId':sCategoryId,'iOrder':iOrder};
    // console.log(param)
    $.post('hn/nt/save',param,function(data){
      $horgn.callbackMsg(data,function(data){
        var editEntity = data.data.editEntity;
        if(editEntity){
          var treeObj = $.fn.zTree.getZTreeObj("HN_CATE_ZTREE");
          var deleteNode = treeObj.getNodeByParam("id",editEntity.sId);
          treeObj.removeNode(deleteNode);
          var parentNode = treeObj.getNodeByParam("id",editEntity.sCategoryId);
          treeObj.addNodes(parentNode, {name:editEntity.sNoteTitle,id:editEntity.sId,faicon:'file-text'},false);
          //
          $('.HF_N_T').text(editEntity.sNoteTitle);
          $(this).bjuiajax('doLoad', {url:'hn/nt/detail?sId='+editEntity.sId, target:".HF_NOTE_DRTAL"});
          $(".HF_NOTE_DIV").hide();
          $(".HF_NOTE_DRTAL").show();
        }
      });
    })
  })
  //清空
  $currentHtml.find('.HF_CN').click(function(){
    nameObj.val('');
    tagObj.val('');
    orderObj.val('');
    _editor.html('');
  })

  //初始化编辑器
  function initKindEditor($currentHtml) {
    _editor = KindEditor.create("#HF_N_Content", {
       width: '100%',
       height: '400px',
       resizeMode: 0,
       resizeType: 1, //类型：1不可调节宽度，
       fixToolBar:true,
       allowPreviewEmoticons: true,
       themeType:'simple',
       cssPath:'static/css/hnote/ke-editor.css',
       afterBlur: function() {
          this.sync();
       }, //编辑器失去焦点(blur)时执行的回调函数（将编辑器的HTML数据同步到textarea）
       uploadJson: 'hn/nt/uimg', //图片上传地址
       items: [
          'source', '|', 'undo', 'redo',   '|', 'justifyleft', 'justifycenter', 'justifyright',
          'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent',  '|',
          'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
          'italic', 'underline', 'strikethrough',  'removeformat', '|', 'code', 'image',
          'table', 'hr', 'anchor', 'link', 'unlink','|', 'fullscreen'
       ]
    });
     //初始化完成，显示页面内容
     setTimeout(function(){
       $currentHtml.find('.HN_NOTE_EDIT').show();
     },200);//延时显示
  }



})//end=======================================================================

//编辑笔记ztree点击事件
function hframeNoteEditcheckCategory(event, treeId, treeNode){
  var $currentHtml = $('.HN_NoteAddPage').parent();
  $currentHtml.find('.HN_NCa').val(treeNode.id);
  $currentHtml.find('.HF_CAT_INP').val(treeNode.name);
}

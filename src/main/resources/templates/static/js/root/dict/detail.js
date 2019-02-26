$(function(){
  var $currentHtml = $('.HF_RootDictInfoPage').parent();

  //添加子字典
  $currentHtml.find(".HF_DICT_ADD_SON").click(function(){
    var sId = $(this).data('id');
    $(this).dialog({id:'addDialog', url:'r/d/add?sParentId='+sId, width:400,height:250,title:'添加子字典'});
  })
  //修改
  $currentHtml.find(".HF_DICT_EDIT").click(function(){
    var sId = $(this).data('id');
    $(this).dialog({id:'addDialog', url:'r/d/edit?sId='+sId, width:400,height:250,title:'修改字典'});
  })

})//end=======================================================================

$(function(){
  var $currentHtml = $('.HF_RootDeptInfoPage').parent();

  //添加子字典
  $currentHtml.find(".HF_MENU_ADD_SON").click(function(){
    var sId = $(this).data('id');
    $(this).dialog({id:'addDialog', url:'r/p/add?sParentId='+sId, width:500,height:390,title:'添加子部门（分组）'});
  })
  //修改
  $currentHtml.find(".HF_MENU_EDIT").click(function(){
    var sId = $(this).data('id');
    $(this).dialog({id:'addDialog', url:'r/p/edit?sId='+sId, width:500,height:390,title:'修改部门（分组）'});
  })

})//end=======================================================================

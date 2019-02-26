$(function(){
  var $currentHtml = $('.HF_RootMenuInfoPage').parent();

  //添加子字典
  $currentHtml.find(".HF_MENU_ADD_SON").click(function(){
    var sId = $(this).data('id');
    $(this).dialog({id:'addDialog', url:'r/m/add?sId='+sId, width:400,height:300,title:'添加子菜单'});
  })
  //修改
  $currentHtml.find(".HF_MENU_EDIT").click(function(){
    var sId = $(this).data('id');
    $(this).dialog({id:'addDialog', url:'r/m/edit?sId='+sId, width:400,height:270,title:'修改菜单'});
  })

})//end=======================================================================

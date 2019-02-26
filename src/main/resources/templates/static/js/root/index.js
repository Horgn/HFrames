

$(function() {

  //初始化框架
  MyBjuiInit();

  function MyBjuiInit() {
    BJUI.init({
      JSPATH: 'BJUI/', //[可选]框架路径
      PLUGINPATH: 'BJUI/plugins/', //[可选]插件路径
      loginInfo: {
        url: 'login/',
        title: '登录',
        width: 400,
        height: 200
      }, // 会话超时后弹出登录对话框
      statusCode: {
        ok: 200,
        error: 300,
        timeout: 301
      }, //[可选]
      ajaxTimeout: 50000, //[可选]全局Ajax请求超时时间(毫秒)
      pageInfo: {
        total: 'total',
        pageCurrent: 'pageCurrent',
        pageSize: 'pageSize',
        orderField: 'orderField',
        orderDirection: 'orderDirection'
      }, //[可选]分页参数
      alertMsg: {
        displayPosition: 'topcenter',
        displayMode: 'slide',
        alertTimeout: 3000
      }, //[可选]信息提示的显示位置，显隐方式，及[info/correct]方式时自动关闭延时(毫秒)
      keys: {
        statusCode: 'statusCode',
        message: 'message'
      }, //[可选]
      ui: {
        windowWidth: 0, //框架显示宽度，0=100%宽，> 600为则居中显示
        showSlidebar: true, //[可选]左侧导航栏锁定/隐藏
        clientPaging: true, //[可选]是否在客户端响应分页及排序参数
        overwriteHomeTab: false //[可选]当打开一个未定义id的navtab时，是否可以覆盖主navtab(我的主页)
      },
      debug: true, // [可选]调试模式 [true|false，默认false]
      theme: 'horgn' // 若有Cookie['bjui_theme'],优先选择Cookie['bjui_theme']。皮肤[五种皮肤:default, orange, purple, blue, red, green]
    })
  }

  // main - menu
  $('#bjui-accordionmenu')
    .collapse()
    .on('hidden.bs.collapse', function(e) {
      $(this).find('> .panel > .panel-heading').each(function() {
        var $heading = $(this),
          $a = $heading.find('> h4 > a')

        if ($a.hasClass('collapsed')) $heading.removeClass('active')
      })
    })
    .on('shown.bs.collapse', function(e) {
      $(this).find('> .panel > .panel-heading').each(function() {
        var $heading = $(this),
          $a = $heading.find('> h4 > a')

        if (!$a.hasClass('collapsed')) $heading.addClass('active')
      })
    })

  $(document).on('click', 'ul.menu-items li > a', function(e) {
    var $a = $(this),
      $li = $a.parent(),
      options = $a.data('options').toObj(),
      $children = $li.find('> .menu-items-children')
    var onClose = function() {
      $li.removeClass('active')
    }
    var onSwitch = function() {
      $('#bjui-accordionmenu').find('ul.menu-items li').removeClass('switch')
      $li.addClass('switch')
    }

    $li.addClass('active')
    if (options) {
      options.url = $a.attr('href')
      options.onClose = onClose
      options.onSwitch = onSwitch
      if (!options.title) options.title = $a.text()

      if (!options.target)
        $a.navtab(options)
      else
        $a.dialog(options)
    }
    if ($children.length) {
      $li.toggleClass('open')
    }

    e.preventDefault()
  })

  //时钟
  var today = new Date(),
    time = today.getTime()
  $('#bjui-date').html(today.formatDate('yyyy/MM/dd'))
  setInterval(function() {
    today = new Date(today.setSeconds(today.getSeconds() + 1))
    $('#bjui-clock').html(today.formatDate('HH:mm:ss'))
  }, 1000)

})//end=========================================================================

//菜单-事件
function MainMenuClick(event, treeId, treeNode) {
  event.preventDefault()

  if (treeNode.isParent) {
    var zTree = $.fn.zTree.getZTreeObj(treeId)

    zTree.expandNode(treeNode, !treeNode.open, false, true, true)
    return
  }

  if (treeNode.target && treeNode.target == 'dialog')
    $(event.target).dialog({
      id: treeNode.tabid,
      url: treeNode.url,
      title: treeNode.name
    })
  else
    $(event.target).navtab({
      id: treeNode.tabid,
      url: treeNode.url,
      title: treeNode.name,
      fresh: treeNode.fresh,
      external: treeNode.external
    })
}


// $(function() {
//
//   // SyntaxHighlighter.config.clipboardSwf = 'static/js/syntaxhighlighter-2.1.382/scripts/clipboard.swf'
//   $(document).on(BJUI.eventType.initUI, function(e) {
//     SyntaxHighlighter.highlight();
//   })
// })
//刷新当前navtab
function refreshTab() {
  $(this).navtab('refresh');
}
//删除记录回调方法
//data-callback="deleteCallBack"
function deleteCallBack(data) {
  if (data.code == 1) {
    $(this).alertmsg('ok', data.message);
    refreshTab();
  } else {
    $(this).alertmsg('error', data.message);
  }
}
//弹窗ajax回调方法
//data-callback="dialogCallback" data-dialog-id="dialogAdd"
function dialogCallback(data) {
  var dialogId = $(this).get(0).options.dialogId;
  if (data.code == 1) {
    $(this).alertmsg('ok', data.message);
    setTimeout(function() {
      // $(this).dialog('close',dialogId);
      closeDialog(dialogId);
    }, 500); //延时，以防卡顿
    setTimeout(function() {
      refreshTab();
    }, 1000); //延时，以防卡顿
  } else {
    $(this).alertmsg('error', data.message);
  }
}

//关闭指定弹窗
function closeDialog(dialogId) {
  // console.log($(this));
  $(this).dialog('close', dialogId);
}

function doLogout() {
  $(this).alertmsg('confirm', '确认注销登录？', {
    okCall: function() {
      $.get('hf/logout');
    }
  })
}

function afterLogout() {
  window.location.reload();
}

/*ajax 请求响应方法==============================================================*/
//参数1：服务器返回的请求状态响应类
//参数2：1）如果是一个方法，则执行该方法
//      2）如果是true，则刷新当前tab页面
//      3）如果为空，则什么也不做
function hframeCallback(data,cb){
  var reg = RegExp(/权限不足！/);
  if(reg.exec(data)){
    // $(this).alertmsg("error", "权限不足！");
    $horgn.errorMsg('权限不足！');
    return;
  }
  if(data.code == 1){
    if(typeof cb == 'function'){
      cb(data);
    }
    if(cb == true){
      refreshTab();
    }
  }else if(data.code == 0){
    // $(this).alertmsg("error", data.message);
    $horgn.errorMsg(data.message);
  }
}
//
function hframeCallbackMsg(data,cb){
  var reg = RegExp(/权限不足！/);
  if(reg.exec(data)){
    // $(this).alertmsg("error", "权限不足！");
    $horgn.errorMsg('权限不足！');
    return;
  }
  if(data.code == 1){
    // $(this).alertmsg("ok", data.message);
    $horgn.okMsg(data.message);
    if(typeof cb == 'function'){
      cb(data);
    }
    if(cb == true){
      refreshTab();
    }
  }else{
    // $(this).alertmsg("error", data.message);
    $horgn.errorMsg(data.message);
  }
}
//关闭当前弹窗
//$(this).dialog('close', $.CurrentDialog);

//$currentHtml.alertmsg('confirm', '确定删除该分类吗？', {okCall:function(){}})

//	$currentHtml.dialog({id:'addDialog', url:'hn/cg/edit?sId='+sCategoryId, width:400,height:200, title:'编辑分类'});

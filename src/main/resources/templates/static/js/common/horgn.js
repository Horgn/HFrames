
/*Horgn 框架自定义工具类*/
var $horgn = function(){};

/*居中弹窗正确信息*/
$horgn.okMsg = function(msg){
  $(this).alertmsg('ok',msg,{
    displayPosition:'middlecenter'
  });
}//end ===

/*居中弹窗错误信息*/
$horgn.errorMsg = function(msg){
  $(this).alertmsg('error',msg,{
    displayPosition:'middlecenter'
  });
}//end ===

/*居中弹窗确认信息*/
$horgn.confirmMsg = function(msg,cb){
  $(this).alertmsg('confirm', msg, {
    displayPosition:'middlecenter',
    okCall: function() {
      if(typeof cb == 'function'){
        cb();
      }
    }
  })
}//end ===

/*ajax 回调数据预处理方法*/
$horgn.callback = function(data,cb){
  var reg = RegExp(/权限不足！/);
  if(reg.exec(data)){
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
    $horgn.errorMsg(data.message);
  }
}//end ===

/*ajax 回调数据预处理方法*/
$horgn.callbackMsg = function(data,cb){
  var reg = RegExp(/权限不足！/);
  if(reg.exec(data)){
    $horgn.errorMsg('权限不足！');
    return;
  }
  if(data.code == 1){
    $horgn.okMsg(data.message);
    if(typeof cb == 'function'){
      cb(data);
    }
    if(cb == true){
      refreshTab();
    }
  }else{
    $horgn.errorMsg(data.message);
  }
}//end ===

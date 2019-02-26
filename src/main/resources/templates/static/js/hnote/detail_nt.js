$(function(){
  var $currentHtml = $('.HN_NoteDetailPage').parent();

  $('.HF_NOTE_DRTAL').show();

  //编辑
  $currentHtml.find('.HF_RD').click(function(){
    $(".HF_NOTE_DIV").show();
    $(".HF_NOTE_DRTAL").hide();
    var sNoteId = $('.HN_NoteDetailPage').val();
    if(!sNoteId){$horgn.errorMsg("请选择指定笔记");return;}
    $currentHtml.bjuiajax('doLoad', {url:'hn/nt/edit?sId='+sNoteId, target:".HF_NOTE_DIV"});
    var dname = $('.HF_N_T').text();
    $('.HN_DETAIL_N').val(dname);
    $('.HF_N_T').text("编辑笔记");
  })

  //分享
  $currentHtml.find('.HF_SA').click(function(){
    var sNoteId = $('.HN_NoteDetailPage').val();
    if(!sNoteId){$horgn.errorMsg("请选择指定笔记");return;}
    $.get('hn/nt/shepath/'+sNoteId,function(data){
      $horgn.callback(data,function(data){
        $(this).alertmsg('info', data.data.path, {
          width:'600px',
          autoClose:false,
          displayPosition:'middlecenter',
          title:'笔记分享链接'
        })
      })
    })
  })
})//end=======================================================================

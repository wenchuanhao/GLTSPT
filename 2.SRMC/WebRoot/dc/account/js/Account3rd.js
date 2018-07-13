var exporting = {
  buttons: {
    contextButton: {
      menuItems: [
        {
          text: '导出PNG',
          onclick: function () {
            this.exportChart({
              type: 'image/png'
            });
          }
        },
        {
          text: '导出JPEG',
          onclick: function () {
            this.exportChart({
              type: 'image/jpeg'
            });
          },
          separator: false
        },
        {
          text: '导出PDF',
          onclick: function () {
            this.exportChart({
              type: 'application/pdf'
            });
          },
          separator: false
        },
        {
          text: '导出SVG',
          onclick: function () {
            this.exportChart({
              type: 'image/svg+xml'
            });
          },
          separator: false
        }
      ]
    }
  }
};


//数据与列表切换
$('.switch02').click(function(){
  $(this).toggleClass('switch_list');
  if($(this).children('i').html()=='数据'){
    $(this).children('i').html('列表');
    $(this).prev().css('right',140+'px');
    $(this).next().hide();
    $(this).next().next().show();
  }else{
    $(this).children('i').html('数据');
    $(this).prev().css('right',82+'px');
    $(this).next().show();
    $(this).next().next().hide();
  }
});
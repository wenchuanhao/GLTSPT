/**
 * 表格数据导出
 * @param target
 */
function export_table(target){
	var $tab = $(target).parent().find(".listTable");
	var data = get_table_data($tab);
    post(base_url+"/copperationController/exportTableData", {data:data});  
}


//作者：ikmb@163.com  
//参数：tid表格ID   
function get_table_data(tid) {  
     var t_data = "";  
     var f = -1;  
     var td_data_temp = "";  
     //tr  
     $(tid).find("tr").each(function(i) {  
         if ($(tid).find("tr:eq(" + i + ")").find("td").length != 0) {  
             //td  
             $(tid).find("tr:eq(" + i + ")").find("td").each(function(j) {  
                 if ($(this).css("display") != "none") {  
                     if ($(this).find("input[type='text']").length == 0) {  
  
                         td_data_temp = ($(this).text() == "" ? " " : $(this).text());  
                     } else {  
                         td_data_temp = ($(this).find("input[type='text']:eq(0)").val() == "" ? " " : $(this).find("input[type='text']:eq(0)").val());  
                     }  
  
                     if (td_data_temp.toString().indexOf("全选") != -1) {  
                         f = j;  
                     } else {  
                         if (f != j)  
                             t_data += td_data_temp + "|";  
                     }  
                 }  
             });  
             //alert(t_data);  
         } else {  
             //th  
             $(tid).find("tr:eq(" + i + ")").find("th").each(function(j) {  
                 if ($(this).css("display") != "none") {  
                     if ($(this).find("input[type='text']").length == 0) {  
  
                         td_data_temp = ($(this).text() == "" ? " " : $(this).text());  
                     } else {  
                         td_data_temp = ($(this).find("input[type='text']:eq(0)").val() == "" ? " " : $(this).find("input[type='text']:eq(0)").val());  
                     }  
                       
                     if (td_data_temp.toString().indexOf("全选") != -1) {  
                         f = j;  
                     } else {  
                         if (f != j)  
                             t_data += td_data_temp + "|";  
                     }  
                 }  
             });  
         }  
  
         t_data += ";";  
  
     });  
     return t_data;  
 } 
 
 
 function post(URL, PARAMS) {        
    var temp = document.createElement("form");        
    temp.action = URL;        
    temp.method = "post";        
    temp.style.display = "none";        
    for (var x in PARAMS) {        
        var opt = document.createElement("textarea");        
        opt.name = x;        
        opt.value = PARAMS[x];        
        // alert(opt.name)        
        temp.appendChild(opt);        
    }        
    document.body.appendChild(temp);        
    temp.submit();        
    return temp;        
} 
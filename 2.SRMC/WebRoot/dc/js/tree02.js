
function showsubmenu(ss,ii,aa,openimg,closeimg)
{
 if (ss.style.display=="none") 
  {ss.style.display="";
   ii.src="images/left_fold2b.png";
   ii.alt="关闭菜单";
}
 else
  {ss.style.display="none"; 
	ii.src="images/left_fold1b.png";
	ii.alt="展开菜单";
   }
}

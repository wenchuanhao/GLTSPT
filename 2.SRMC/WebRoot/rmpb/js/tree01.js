
function showsubmenu(ss,ii,aa,openimg,closeimg)
{
 if (ss.style.display=="none") 
  {ss.style.display="";
   ii.src="/SRMC/rmpb/images/left_fold2.gif";
   ii.alt="关闭菜单";
}
 else
  {ss.style.display="none"; 
	ii.src="/SRMC/rmpb/images/left_fold1.gif";
	ii.alt="展开菜单";
   }
}

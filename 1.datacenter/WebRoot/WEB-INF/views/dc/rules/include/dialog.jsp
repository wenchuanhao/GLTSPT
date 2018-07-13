<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div style="display:none">
	<!-- 遮罩内容开始 -->
	<div id="inline2" class="modal_dialog">
	  <div class="alarm_content">
	    <img class="modal_pic" src="/SRMC/dc/images/alarm.png"/>
	    本操作不可撤销，原制度将立即废止！请创建一个新版本的制度。
	  </div>
	  <div class="btn_wrap">
	    <input name="" type="button" class="btn_common02" onclick="reviseRules()" value="确定" />
	    <input name="" type="button" class="btn_common04" onclick="$.fancybox.close();" value="取 消" />
	  </div>
	</div>
	<!-- 遮罩内容结束 -->
</div>

<input type="hidden" id="dialog_input"/>
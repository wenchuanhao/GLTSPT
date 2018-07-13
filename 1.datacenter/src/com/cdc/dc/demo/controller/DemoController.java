package com.cdc.dc.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.system.core.authentication.controller.CommonController;
import com.trustel.common.MD5Helper;

/**
 * demo 控制器
 * @author WEIFEI
 * @date 2016-4-7 下午2:45:44
 */
@Controller
@RequestMapping(value = "/demo/")
public class DemoController extends CommonController {

    private Log logger = LogFactory.getLog(getClass());

    /**
     * 列表
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(HttpServletRequest request,HttpServletResponse response) {
        return "core/login/login";
    }

    /**
     * 新增
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET,RequestMethod.POST})
    public String add(HttpServletRequest request) {
        return "core/login/toConfirm";
    }

    /**
     * 修改
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public @ResponseBody String update(HttpServletRequest request, HttpServletResponse response,
                      String oldPwd, String newPwd) {
        try {
            SysUser sysUser = getVisitor(request);
            String phone=request.getParameter("phone");//获取输入的手机号
            if(sysUser==null){
                return "noLogin";
            }
            if(sysUser!=null && sysUser.getMobile()!=null){
                if(sysUser.getMobile().equals(phone)){
                    //request.getSession().setAttribute("phone", phone);
                    return "yes";
                }else{
                    return "no";
                }
            }else{
                return "nofind";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    /**
     * 删除
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public  @ResponseBody String delete(HttpServletRequest request, HttpServletResponse response,String oldPwd) {
        try {
            SysUser sysUser = getVisitor(request);
            String passwordOld=request.getParameter("oldPwd");//没有加密的密码 旧密码
            MD5Helper helper = new MD5Helper();
            String oldPwdTemp = helper.getDoubleMD5ofStr(passwordOld);//加了密的旧密码
            if(sysUser==null){
                return "noLogin";
            }
            if(sysUser.getPassword().equals(oldPwdTemp)){
                return "yes";
            }else{
                return "no";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

}

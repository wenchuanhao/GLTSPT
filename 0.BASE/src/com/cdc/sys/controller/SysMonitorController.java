package com.cdc.sys.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.druid.sql.visitor.functions.Substring;
import com.cdc.sys.form.SystemMonitor;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 
 * @author xms
 *服务器监控
 */
@Controller
@RequestMapping(value = "/sys/monitor/")
public class SysMonitorController {
	
	@RequestMapping(value="sysMonitor",method={RequestMethod.POST,RequestMethod.GET})
	public String sysMonitor(HttpServletRequest request,HttpServletResponse response) throws SigarException{
		
		//获取内存信息
		SystemMonitor monitor = new SystemMonitor();
		NumberFormat formatter = NumberFormat.getPercentInstance(); 
		
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        float total = mem.getTotal();
        float used = mem.getUsed();
        
        //可用量
        monitor.setMenFree(mem.getFree() / 1024L/1024L + "M");
        //总量
        monitor.setMenSum(mem.getTotal() / 1024L/1024L + "M");
        //使用量
        monitor.setMenUsed( mem.getUsed() / 1024L/1024L + "M");
   
        monitor.setMenUsePercent(formatter.format(mem.getUsedPercent()/100D));

        //获取CPU信息
        CpuInfo[] infos = sigar.getCpuInfoList();
        CpuPerc cpuList[] = null;
        cpuList = sigar.getCpuPercList();
        
        Double sum = 0.0;
        for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
            CpuInfo info = infos[i];
            //printCpuPerc(cpuList[i]);
            sum += cpuList[i].getCombined();
        }
        
        //CPU使用率
        monitor.setCpuUsed(CpuPerc.format(sum/infos.length));
        
        SystemMonitor monitor2 =null;
	    List<SystemMonitor> list =  new ArrayList();;
       //获取硬盘信息
	   FileSystem fslist[] = sigar.getFileSystemList();
	   for (int i = 0; i < fslist.length; i++) {
		monitor2  =  new SystemMonitor(); 
		
	    FileSystem fs = fslist[i];
	    
	    FileSystemUsage usage = null;
	    try {
	     usage = sigar.getFileSystemUsage(fs.getDirName());
	     monitor2.setDir(fs.getDirName());
	    
	    } catch (SigarException e) {
	     if (fs.getType() == 2)
	      throw e;
	     continue;
	    }
	    switch (fs.getType()) {
	    case 0: // TYPE_UNKNOWN ：未知
	    break;
	    case 1: // TYPE_NONE
	     break;
	    case 2: // TYPE_LOCAL_DISK : 本地硬盘
	     double usePercent = usage.getUsePercent() * 100D;
	     //可用
	     monitor2.setDiskFree("Avail： "+usage.getAvail()/1024L/1024L + " G");
	     //共
	     monitor2.setDiskSum("Total: "+usage.getTotal()/1024L/1024L + " G");
	     //已使用
	     monitor2.setDiskUsed("Used： "+usage.getUsed()/1024L/1024L + " G");
	     monitor2.setDiskUsePercent("UsePt： "+formatter.format(usage.getUsePercent()));
	     break;
	    case 3:// TYPE_NETWORK ：网络
	    break;
	    case 4:// TYPE_RAM_DISK ：闪存
	    break;
	    case 5:// TYPE_CDROM ：光驱
	    break;
	    case 6:// TYPE_SWAP ：页面交换
	    break;
	    }

	    list.add(monitor2);
	   }
	   
	   //获取操作系统信息
	   OperatingSystem OS = OperatingSystem.getInstance();   
	   // 系统描述  
	   monitor.setOperaSystem(OS.getDescription()+" "+ OS.getArch());
	   // 操作系统的版本号  
	   monitor.setVersion(OS.getVersion());
	   
	    request.setAttribute("monitor2", list);
		request.setAttribute("monitor", monitor);
		return "sys/monitor/SystemMonitor";
	}
	
}

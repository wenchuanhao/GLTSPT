package com.cdc.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.log4j.Logger;

import com.swetake.util.Qrcode;

/**
 */
public class QrcodeUtil {
	private static Logger logger = Logger.getLogger(QrcodeUtil.class);
	/**
	 * 生成QR CODE图片
	 * @param text 需要编码文本内容
	 * @param width 生成图片宽度,单位像素
	 * @param height 生成图片高度,单位像素
	 * @param imagePath 生成图片存放路径
	 * @throws Exception
	 */
    public static File createImage(String text,int width,int height,String imagePath)throws Exception{  
    	File f = null;
        try{  
              Qrcode qrcode =new Qrcode();  
              qrcode.setQrcodeErrorCorrect('M');  
              qrcode.setQrcodeEncodeMode('B');  
              qrcode.setQrcodeVersion(8);  
              byte[] d = text.getBytes("utf-8");  
              BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);  
              Graphics2D g = bi.createGraphics();  
              g.setBackground(Color.WHITE);  
              g.clearRect(0, 0, width, height);  
              g.setColor(Color.BLACK);  
                    
              // 限制最大字节数为119  
              if (d.length>0 && d.length <120){
            	  boolean[][] s = qrcode.calQrcode(d);  
                      for (int i=0;i<s.length;i++){  
                          for (int j=0;j<s.length;j++){  
                              if (s[j][i]) {  
                                  g.fillRect(j*8,i*8,8,8);  
                              }
                          }
                      }
               }
               g.dispose();  
               bi.flush();  
               f = new File(imagePath);
               if(f.exists()){
            	  f.delete();
               }
               f.mkdirs();
               f.createNewFile();  
               ImageIO.write(bi, "jpg", f);  
         }catch (Exception e) {  
        	 logger.error("生成二维码发生异常", e);
         }   
        return f;
   }
    /**
     * 生成QR CODE图片
     * @param text 需要编码文本内容
     * @param width 生成图片宽度,单位像素
     * @param height 生成图片高度,单位像素
     * @param imagePath 生成图片存放路径
     * @throws Exception
     */
    public static InputStream createInputStreamImage(String text,int width,int height)throws Exception{  
    	InputStream is = null; 

    	try{  
    		Qrcode qrcode =new Qrcode();  
    		qrcode.setQrcodeErrorCorrect('M');  
    		qrcode.setQrcodeEncodeMode('B');  
    		qrcode.setQrcodeVersion(8);  
    		byte[] d = text.getBytes("utf-8");  
    		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);  
    		Graphics2D g = bi.createGraphics();  
    		g.setBackground(Color.WHITE);  
    		g.clearRect(0, 0, width, height);  
    		g.setColor(Color.BLACK);  
    		
    		// 限制最大字节数为119  
    		if (d.length>0 && d.length <120){
    			boolean[][] s = qrcode.calQrcode(d);  
    			for (int i=0;i<s.length;i++){  
    				for (int j=0;j<s.length;j++){  
    					if (s[j][i]) {  
    						g.fillRect(j*8,i*8,8,8);  
    					}
    				}
    			}
    		}
    		g.dispose();  
    		bi.flush();  
            
	        ByteArrayOutputStream bs = new ByteArrayOutputStream();  
	        ImageOutputStream imOut; 
	        try { 
	            imOut = ImageIO.createImageOutputStream(bs); 
	            ImageIO.write(bi, "png",imOut); 
	            is= new ByteArrayInputStream(bs.toByteArray()); 
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        }  
	        return is; 
    	}catch (Exception e) {  
    		logger.error("生成二维码发生异常", e);
    	}   
    	return is;
    }
    
    public static void main(String args[]){
    	try {
			QrcodeUtil.createImage("T1402001#abcDEFGabcDEFGDabcDEFGabcDEFGDabcDEFGabcDEFGDabcDEFGabcDEFGDDEFGDDEFGDEFFGDDEFGDEFFGDDEFGDEFFGDDEFGDEFFGDDEeAa",392,392,"d:\\qrcode.jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("生成二维码发生异常", e);
		}
    }
}

package org.trustel.util;

/**
 * 用于生成缩略图、添加水印、生成无重复的文件名
 * zoujing
 */

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class FileUpload {

	private static final int BUFFER_SIZE = 16 * 1024;
	private static final String WATER_TEXT = "文字水印";
	private static final String WATER_IMG_NAME = "logo.png";

	/*生成无重复文件名*/
    public static String getFileName(String name){

		SimpleDateFormat df =new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String filename=null;
		if(!"".equals(name) && name != null){
			filename=df.format(date) + "_"+RandomNum.random2() + name.substring(name.lastIndexOf("."), name.length());
		}
		return filename;
	}

	/**
	 * 文件保存到服务器
	 * @param src
	 * @param des
	 */
	public static boolean copyFile(File src, String path) {
		FileOutputStream fos=null;
		FileInputStream fis=null;
		try{
			fos=new FileOutputStream(path);
			fis=new FileInputStream(src);
		byte[] buffer=new byte[1024];
		int len=0;
		while((len=fis.read(buffer))>0)
		{
			fos.write(buffer, 0, len);
		}
		fos.flush();
		return true;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		finally{
			 if(fis != null) try{ fis.close();}catch(Exception e){}
			 if(fos != null) try{ fos.close();}catch(Exception e){}
		}
	}
    
    /*上传文件*/
	public static void copy(File src, File dst) {
        try {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
                out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
                byte[] buffer = new byte[BUFFER_SIZE];
                while (in.read(buffer) > 0) {
                    out.write(buffer);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/*生成缩略图*/
	public static String scaleImage(String fromFileStr, String saveToFileStr, int formatWideth, int formatHeight) throws Exception {
		ScaleImage is = new ScaleImage();
		try {
			is.saveImageAsJpg(fromFileStr, saveToFileStr, formatWideth, formatHeight);
        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "error";
        }
        return "ok";
	}

	/**
	     * 添加文字水印
	     *
	     * @return
	     * @throws Exception
	     * @throws Exception
	     */
	    public static void watermark(File img) throws Exception {
	        try {  

	            if (!img.exists()) {
	                throw new IllegalArgumentException("file not found!");
	            }  

	            // 创建一个FileInputStream对象从源图片获取数据流  
	            FileInputStream sFile = new FileInputStream(img);  

	            // 创建一个Image对象并以源图片数据流填充  
	            Image src = ImageIO.read(sFile);  

	            // 得到源图宽  
	            int width = src.getWidth(null);
	            // 得到源图长  
	            int height = src.getHeight(null);  

	            // 创建一个BufferedImage来作为图像操作容器  
	            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	            // 创建一个绘图环境来进行绘制图象  
	            Graphics2D g = image.createGraphics();
	            // 将原图像数据流载入这个BufferedImage  
	            g.drawImage(src, 0, 0, width, height, null);
	            // 设定文本字体  
	            g.setFont(new Font("宋体", Font.BOLD, 28));
	            String rand = WATER_TEXT;
	            // 设定文本颜色  
	            g.setColor(Color.blue);
	            // 设置透明度  
	            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
	            // 向BufferedImage写入文本字符,水印在图片上的坐标  
	            g.drawString(rand, width - (width - 20), height - (height - 60));
	            // 使更改生效  
	            g.dispose();
	            // 创建输出文件流  
	            FileOutputStream outi = new FileOutputStream(img);
	            // 创建JPEG编码对象  
	            JPEGImageEncoder encodera = JPEGCodec.createJPEGEncoder(outi);
	            // 对这个BufferedImage (image)进行JPEG编码  
	            encodera.encode(image);
	            // 关闭输出文件流  
	            outi.close();
	            sFile.close();  

	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new Exception(e);
	        }
	    }  

	    /**
	     * 添加图片水印
	     *
	     */
	    public static void imageWaterMark(File imgFile, String waterFilePath) throws Exception {
	        try {
	            // 目标文件  
	            Image src = ImageIO.read(imgFile);
	            int wideth = src.getWidth(null);
	            int height = src.getHeight(null);
	            BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
	            Graphics2D g = image.createGraphics();
	            g.drawImage(src, 0, 0, wideth, height, null);  

	            // 水印文件 路径  
	            String waterImgPath = waterFilePath + WATER_IMG_NAME;
	            File waterFile = new File(waterImgPath);
	            Image waterImg = ImageIO.read(waterFile);  

	            int w_wideth = waterImg.getWidth(null);
	            int w_height = waterImg.getHeight(null);
	            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
	            g.drawImage(waterImg, (wideth - w_wideth) / 2, (height - w_height) / 2, w_wideth, w_height, null);
	            // 水印文件结束  

	            g.dispose();
	            FileOutputStream out = new FileOutputStream(imgFile);
	            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	            encoder.encode(image);
	            out.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
		public static void createFolder(String folderpath){				
			java.io.File file = new java.io.File(folderpath);
			if(file.exists() && file.isDirectory()){					
		
			}else{
				file.mkdirs();
			}			
		}
}


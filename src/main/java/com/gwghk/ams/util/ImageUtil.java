package com.gwghk.ams.util;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import magick.CompositeOperator;
import magick.CompressionType;
import magick.DrawInfo;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import magick.PixelPacket;
import magick.PreviewType;

public class ImageUtil {
	private static final Logger logger = Logger.getLogger(ImageUtil.class);
	static{   
	    //不能漏掉这个，不然jmagick.jar的路径找不到   
		System.setProperty("jmagick.systemclassloader","no");   
	}   
	     
   /**
    * 功能：压缩图片  
    * @param filePath 源文件路径  
    * @param toPath   缩略图路径  
    * @param width 
    * @param height
    * @param b        是否等比
    * @throws MagickException
    */
	public static void createThumbnail(String filePath, String toPath, float width,float height,boolean b) throws MagickException{   
		ImageInfo info = null;
		MagickImage image = null;  
		Dimension imageDim = null;   
		MagickImage scaled = null;
		try{
			info = new ImageInfo(filePath);   
			image = new MagickImage(info);   
			imageDim = image.getDimension();   
			// 获得缩放的比例
			double ratio = 1.0;
			// 判断如果高、宽都不大于设定值，则不处理
			if (imageDim.height > height || imageDim.width > width) {
				if(b){
					if (imageDim.height > imageDim.width) {
						ratio = height / imageDim.height;
					} else {
						ratio = width / imageDim.width;
					}
				}else{
					ratio = width / imageDim.width;
				}
			}
			// 计算新的图面宽度和高度
			int newWidth = (int) (imageDim.width * ratio);
			int newHeight = (int) (imageDim.height * ratio);
			scaled = image.scaleImage(newWidth,newHeight);//小图片文件的大小.   wideth, height
			scaled.setFileName(toPath);   
			scaled.writeImage(info);   
			logger.error("toPath:"+toPath);
		}finally{
			if(scaled != null){
				scaled.destroyImages();   
			}   
		}   
	}   
	     
   /**  
    * 功能：水印(图片logo)  
    * @param filePath  源文件路径  
    * @param toImg     修改图路径  
    * @param logoPath  logo图路径  
    * @throws MagickException  
    */  
	public static void initLogoImg(String filePath, String toImg, String logoPath) throws MagickException {   
		ImageInfo info = new ImageInfo();   
	    MagickImage fImage = null;   
	    MagickImage sImage = null;   
	    MagickImage fLogo = null;   
	    MagickImage sLogo = null;   
	    Dimension imageDim = null;   
	    Dimension logoDim = null;   
	    try {   
	    	fImage = new MagickImage(new ImageInfo(filePath));   
	        imageDim = fImage.getDimension();   
	        int width = imageDim.width;   
	        int height = imageDim.height;   
	        sImage = fImage.scaleImage(width, height);   
	             
	        fLogo = new MagickImage(new ImageInfo(logoPath));   
	        logoDim = fLogo.getDimension();   
	        int lw =logoDim.width; //width / 5;   
	        int lh =logoDim.height; //logoDim.height * lw / logoDim.width;   
	        sLogo = fLogo.scaleImage(lw, lh); 
	        DrawInfo aInfo = new DrawInfo(new ImageInfo(logoPath));   
	        aInfo.setOpacity(40);    
	        fLogo.annotateImage(aInfo);
	          
	        sImage.compositeImage(CompositeOperator.AtopCompositeOp, sLogo,  width-(lw + lh/10), height-(lh + lh/10));   
	        sImage.setFileName(toImg);   
	        sImage.writeImage(info);   
	    } finally {   
	    	if(sImage != null){   
	    		sImage.destroyImages();   
	        }   
	    }   
	}   
	     
   /**  
    * 功能：水印(文字)  
    * @param filePath 源文件路径  
    * @param toImg    修改图路径  
    * @param text     名字(文字内容自己随意)  
    * @throws MagickException  
    */  
	public static void initTextToImg(String filePath, String toImg,  String text) throws MagickException{   
		ImageInfo info = new ImageInfo(filePath);   
	    if (filePath.toUpperCase().endsWith("JPG") || filePath.toUpperCase().endsWith("JPEG")) {   
	    	info.setCompression(CompressionType.JPEGCompression); 	//压缩类别为JPEG格式   
	    	info.setPreviewType(PreviewType.JPEGPreview); 		    //预览格式为JPEG格式   
	    	info.setQuality(95);   
	    }
        MagickImage aImage = new MagickImage(info);   
        Dimension imageDim = aImage.getDimension();   
        int wideth = imageDim.width;   
        int height = imageDim.height;   
        if (wideth > 660) {   
        	height = 660 * height / wideth;   
            wideth = 660;   
        }   
        int a = 0;   
        int b = 0;   
        String[] as = text.split("");   
        for (String string : as) {   
        	if(string.matches("[\u4E00-\u9FA5]")){   
        		a++;   
        	}   
        	if(string.matches("[a-zA-Z0-9]")){   
        		b++;   
        	}   
        }   
        int tl = a*12 + b*6 + 300;   
	    MagickImage scaled = aImage.scaleImage(wideth, height);   
        if(wideth > tl && height > 5){   
        	DrawInfo aInfo = new DrawInfo(info);   
            aInfo.setFill(PixelPacket.queryColorDatabase("white"));   
            aInfo.setUnderColor(new PixelPacket(0,0,0,100));   
            aInfo.setPointsize(12);   
            //解决中文乱码问题,自己可以去随意定义个自己喜欢字体，我在这用的微软雅黑   
            String fontPath = "C:/WINDOWS/Fonts/MSYH.TTF";  
            aInfo.setFont(fontPath);   
            aInfo.setTextAntialias(true);   
            aInfo.setOpacity(0);   
            aInfo.setText("　" + text + "于　" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "　上传于XXXX网，版权归作者所有！");   
            aInfo.setGeometry("+" + (wideth-tl) + "+" + (height-5));   
            scaled.annotateImage(aInfo);   
        }   
	    scaled.setFileName(toImg);   
	    scaled.writeImage(info);   
	    scaled.destroyImages();   
	}   
	     
   /**  
    * 功能：切图  
    * @param imgPath 源图路径  
    * @param toPath  修改图路径  
    * @param w         
    * @param h         
    * @param x         
    * @param y  
    * @throws MagickException  
    */  
	public static void cutImg(String imgPath, String toPath, int w, int h, int x, int y) throws MagickException {   
		ImageInfo infoS = null;
	    MagickImage image = null;
	    MagickImage cropped = null;
	    Rectangle rect = null; 
	    try {   
	    	infoS = new ImageInfo(imgPath);   
	        image = new MagickImage(infoS);
	        rect = new Rectangle(x, y, w, h);   
	        cropped = image.cropImage(rect);  
	        cropped.setFileName(toPath);
	        cropped.writeImage(infoS); 
	    } finally {
	    	if (cropped != null) {
	    		cropped.destroyImages();
	        }
	    } 
	} 
	  
   /**  
    *  功能：复制单个文件  
    *  @param  oldPath  String  原文件路径  如：c:/fqf.txt  
    *  @param  newPath  String  复制后路径  如：f:/fqf.txt  
    *  @return  boolean  
    */  
	public  void  copyFile(String  oldPath,  String  newPath)  {  
       try  {  
           int  byteread  =  0;
           File  oldfile  =  new  File(oldPath); 
           File  newfile  =  new  File(newPath); 
           if(!newfile.getParentFile().exists())  {  
        	   newfile.getParentFile().mkdir();
           }  
           if(oldfile.exists())  {  //文件存在时  
        	   InputStream  inStream  =  new  FileInputStream(oldPath);  //读入原文件  
               FileOutputStream  fs  =  new  FileOutputStream(newPath);
               byte[]  buffer  =  new  byte[1444]; 
               while((byteread  =  inStream.read(buffer))  !=  -1)  {  
                   fs.write(buffer,  0,  byteread); 
               }
               inStream.close();
               fs.close();
           }  
       }catch  (Exception  e)  {
           System.out.println("复制单个文件操作出错");
           e.printStackTrace();
       }
    } 
	   
   /**  
    *  功能：删除文件夹里面的所有文件  
    *  @param  path  String  文件夹路径  如  c:/fqf  
    */  
	public  void  delAllFile(String  path)  {  
		File  file  =  new  File(path);
		if(!file.exists())  {
			return;
		} 
		if(!file.isDirectory())  {  
			return;
		}  
		String[]  tempList  =  file.list();
		File  temp  =  null;
		for(int  i  =  0;  i  <  tempList.length;  i++)  {  
			if(path.endsWith(File.separator))  {  
				temp  =  new  File(path  +  tempList[i]);
			}  
			else{  
				temp  =  new  File(path  +  File.separator  +  tempList[i]);
			}
			if(temp.isFile()){
				temp.delete();
			}  
			if(temp.isDirectory()){  
				delAllFile(path+"/"+  tempList[i]);//先删除文件夹里面的文件
			}  
		}  
	}
	  
	public static void main(String[] args) throws MagickException{
		  createThumbnail("D:/DSC_0691.jpg","D:/temp/等比.jpg",750,500,true);
		  initLogoImg("D:/temp/等比.jpg","D:/temp/等比_log.jpg","D:/LOGO_QB.png");
//		  initTextToImg("D:/DSC_0691.jpg","D:/tempImage/aha.jpg","谁的眼睛大,看看有什么区别?");
//		  initLogoImg("D:/DSC_0691.jpg","D:/tempImage/shuiyin.jpg","D:/LOGO_QB.png");
		  // cutImg("D:/tempImage/aha.jpg","D:/tempImage/cut.jpg",170,200,220,70);
	}

}

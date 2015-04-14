package com.gwghk.mis.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.UploadFileInfo;
import com.gwghk.mis.enums.ResultCode;

/**
 * 摘要：图片处理工具类
 * @author  Gavin.guo
 * @date 2015-03-24
 */
public class ImageHelper {

	private static final Logger logger = LoggerFactory.getLogger(ImageHelper.class);
	
   /**
    * 几种常见的图片格式
    */
	public static String IMAGE_TYPE_GIF = "gif";	// 图形交换格式
    public static String IMAGE_TYPE_JPG = "jpg";	// 联合照片专家组
    public static String IMAGE_TYPE_JPEG = "jpeg";	// 联合照片专家组
    public static String IMAGE_TYPE_BMP = "bmp";	// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
    public static String IMAGE_TYPE_PNG = "png";	// 可移植网络图形
    public static String IMAGE_TYPE_PSD = "psd";	// Photoshop的专用格式Photoshop

   /**
    * 功能：缩放图像（按比例缩放）
    * @param srcImageFile 源图像文件地址
    * @param destImageFile 缩放后的图像地址
    * @param scale 缩放比例
    * @param flag 缩放选择:true 放大; false 缩小;
    */
    public final static void scale(String srcImageFile, String destImageFile,int scale, boolean flag) {
    	try {
    		BufferedImage src = ImageIO.read(new File(srcImageFile)); 		// 读入文件
            int width = src.getWidth(); 									// 得到源图宽
            int height = src.getHeight(); 									// 得到源图长
            if (flag) {		// 放大
            	width = width * scale;
                height = height * scale;
            } else {		// 缩小
                width = width / scale;
                height = height / scale;
            }
            Image image = src.getScaledInstance(width, height,Image.SCALE_DEFAULT);	//使用默认的图像缩放算法
            BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB); //表示一个图像，该图像具有打包成证书像素的8位RGB颜色分量
            Graphics g = tag.getGraphics();											//Graphics2D，可以将它绘制到此图像中
            g.drawImage(image, 0, 0, null); 										// 绘制缩小后的图
            g.dispose();															// 释放此图形的上下文并释放它所使用的所有系统资源
            ImageIO.write(tag, "JPEG", new File(destImageFile));				    // 输出到文件流
        } catch (IOException e) {
        	logger.error("<<method:scale() | 缩放图像(按比例缩放)出错!",e);
        }
    }
    
    /**
     * 功能：缩放图像(备注：当高度和宽度大于指定的 height和widths时，才进行压缩，否则的话不进行压缩,直接输出原图片)
     * @param srcImageFile 源图像文件地址
     * @param destImageFile 缩放后的图像地址
     * @param height 缩放后的高度
     * @param width 缩放后的宽度
      */
    @SuppressWarnings("static-access")
    public final static void scaleWithWidthHeight(String srcImageFile, String destImageFile, int height, int width) {
    	 try {
    		 double ratio = 1.0;
             File f = new File(srcImageFile);
             BufferedImage bi = ImageIO.read(f);
             if ((bi.getHeight() > height) || (bi.getWidth() > width)) {     //计算比例
                 if (bi.getHeight() > bi.getWidth()) {
                     ratio = (new Integer(height)).doubleValue() / bi.getHeight();
                 } else {
                     ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                 }
                 AffineTransformOp op = new AffineTransformOp(AffineTransform	//仿射转换
                         			 .getScaleInstance(ratio, ratio), null);	//返回表示剪切变换的变换
                 Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);	//bi.SCALE_SMOOTH  选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
                 itemp = op.filter(bi, null);									//转换源 BufferedImage 并将结果存储在目标 BufferedImage 中。
                 ImageIO.write((BufferedImage) itemp, "JPEG", new File(destImageFile));
             }else{
            	 FileCopyUtils.copy(f, new File(destImageFile));
             }
         } catch (IOException e) {
         	logger.error("<<method:scaleWithWidthHeight() | 缩放图像（按高度和宽度缩放）出错!",e);
         }
     }

   /**
    * 功能：缩放图像（按高度和宽度缩放）
    * @param srcImageFile 源图像文件地址
    * @param result 缩放后的图像地址
    * @param height 缩放后的高度
    * @param width 缩放后的宽度
    * @param bb 比例不对时是否需要补白：true为补白; false为不补白
     */
    @SuppressWarnings("static-access")
    public final static void scaleWithWidthHeight(String srcImageFile, String result, int height, int width, boolean bb) {
        try {
        	double ratio = 0.0; 				// 缩放比例
            File f = new File(srcImageFile);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);	//bi.SCALE_SMOOTH  选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform	//仿射转换
                        			 .getScaleInstance(ratio, ratio), null);	//返回表示剪切变换的变换
                itemp = op.filter(bi, null);									//转换源 BufferedImage 并将结果存储在目标 BufferedImage 中。
            }
            if (bb) {	//补白
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);							//构造一个类型为预定义图像类型之一的 BufferedImage。
                Graphics2D g = image.createGraphics();							//创建一个 Graphics2D，可以将它绘制到此 BufferedImage 中。
                g.setColor(Color.white);									    //控制颜色
                g.fillRect(0, 0, width, height);								// 使用 Graphics2D 上下文的设置，填充 Shape 的内部区域。
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                g.dispose();
                itemp = image;
            }
            ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
        } catch (IOException e) {
        	logger.error("<<method:scaleWithWidthHeight() | 缩放图像（按高度和宽度缩放）出错!",e);
        }
    }
    
    /**
     * 功能：图像裁剪(按指定起点坐标和宽高切割)
     * @param src   		  源图片地址
     * @param dest 			   切割后的图片地址
     * @param x 目标切片起点坐标X
     * @param y 目标切片起点坐标Y
     * @param w 目标切片宽度
     * @param h 目标切片高度
     */
    public final static boolean cut(String src, String dest,int x, int y, int w, int h) {
    	try{
    		Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("jpg");
    	    ImageReader reader = (ImageReader)iterator.next();
    	    InputStream in = new FileInputStream(src);
    	    ImageInputStream iis = ImageIO.createImageInputStream(in);
    	    reader.setInput(iis, true);
    	    ImageReadParam param = reader.getDefaultReadParam();
    	    Rectangle rect = new Rectangle(x, y, w,h);
    	    param.setSourceRegion(rect);
    	    BufferedImage bi = reader.read(0,param);
    	    ImageIO.write(bi, "jpg", new File(dest));
    	    return true;
    	}catch (IOException e) {
        	logger.error("<<method:cut() | 图像裁剪(按指定起点坐标和宽高切割) 出错!",e);
        	return false;
        }
    }
    
    /**
     * 功能：图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     * @param srcImageFile 源图像地址
     * @param formatName 包含格式非正式名称的 String：如JPG、JPEG、GIF等
     * @param destImageFile 目标图像地址
     */
    public final static void convert(String srcImageFile, String formatName, String destImageFile) {
        try {
            File f = new File(srcImageFile);
            f.canRead();
            f.canWrite();
            BufferedImage src = ImageIO.read(f);
            ImageIO.write(src, formatName, new File(destImageFile));
        } catch (Exception e) {
            logger.error("<<method:convert() | 图像类型转换出错!",e);
        }
    }

    /**
     * 功能：彩色转为黑白 
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     */
    public final static void gray(String srcImageFile, String destImageFile) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile));
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            ImageIO.write(src, "JPEG", new File(destImageFile));
        } catch (IOException e) {
        	logger.error("<<method:gray() | 彩色转为黑白出错!",e);
        }
    }

    /**
     * 功能：给图片添加文字水印
     * @param pressText 水印文字
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param x 修正值
     * @param y 修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressText(String pressText,
            String srcImageFile, String destImageFile, String fontName,
            int fontStyle, Color color, int fontSize,int x,
            int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
            // 在指定坐标绘制水印文字
            g.drawString(pressText, (width - (getLength(pressText) * fontSize))/ 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));// 输出到文件流
        } catch (Exception e) {
    	    logger.error("<<method:pressText() | 给图片添加文字水印出错!",e);
        }
    }

    /**
     * 功能：给图片添加文字水印
     * @param pressText 水印文字
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     * @param fontName 字体名称
     * @param fontStyle 字体样式
     * @param color 字体颜色
     * @param fontSize 字体大小
     * @param x 修正值
     * @param y 修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressText2(String pressText, String srcImageFile,String destImageFile,
            String fontName, int fontStyle, Color color, int fontSize, int x,
            int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
            // 在指定坐标绘制水印文字
            g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
        } catch (Exception e) {
        	logger.error("<<method:pressText2() | 给图片添加文字水印出错!",e);
        }
    }

    /**
     * 功能：给图片添加图片水印
     * @param pressImg 水印图片
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     * @param x 修正值。 默认在中间
     * @param y 修正值。 默认在中间
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressImage(String pressImg, String srcImageFile,String destImageFile,
            int x, int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            Image src_biao = ImageIO.read(new File(pressImg));    // 水印文件
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
            g.drawImage(src_biao, (wideth - wideth_biao) / 2,
                    (height - height_biao) / 2, wideth_biao, height_biao, null);
            g.dispose();										  // 水印文件结束
            ImageIO.write((BufferedImage) image,  "JPEG", new File(destImageFile));
        } catch (Exception e) {
        	logger.error("<<method:pressImage() | 给图片添加图片水印出错!",e);
        }
    }

    /**
     * 计算text的长度（一个中文算两个字符）
     * @param text
     * @return 
     */
    public final static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }
    
	/**
	 * 功能：判断文件是否为图片
	 * @param filename     文件名
	 * @return boolean     true:图片  false:非图片 
	 * @throws Exception
	 */
	public static boolean isPicture(String filename) {
		if (StringUtils.isEmpty(filename)) {
			return false;
		}
		String tmpName = FileUtils.getExtend(filename);
		String imgeArray[][] = { { "bmp", "0" }, { "dib", "1" },
				{ "gif", "2" }, { "jfif", "3" }, { "jpe", "4" },
				{ "jpeg", "5" }, { "jpg", "6" }, { "png", "7" },
				{ "tif", "8" }, { "tiff", "9" }, { "ico", "10" } };
		for (int i = 0; i < imgeArray.length; i++) {
			if (imgeArray[i][0].equals(tmpName.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
    
	/**
	 * 功能：上传图片处理
	 * @param uploadFileInfo 文件上传对象
	 * @return ApiResult 结果对象
	 */
	public static ApiResult uploadImage(UploadFileInfo uploadFileInfo){
		FileUtils.createFilePath(uploadFileInfo);//创建文件路径
		String fileName=uploadFileInfo.getFileName();
		String tempDestFilePath = fileName+"_temp"+"." + uploadFileInfo.getFileExt();	
		String realDestFilePath = fileName+"." + uploadFileInfo.getFileExt();
		String realPath=uploadFileInfo.getFileAbsolutePath();
		ApiResult result = new ApiResult();
		MultipartFile srcFile = uploadFileInfo.getSrcFile();
		try{
			File  tempDestfile = new File(realPath + tempDestFilePath);
			//通过复制创建新的临时图片
			FileCopyUtils.copy(srcFile.getBytes(), tempDestfile);
			//按指定的宽度和高度压缩临时图片，并生成指定想要的图片
			ImageHelper.scaleWithWidthHeight(realPath + tempDestFilePath, realPath + realDestFilePath
					   ,uploadFileInfo.getDefaultHeight(), uploadFileInfo.getDefaultWidth());
			//删除临时压缩的图片
			FileUtils.delete(tempDestfile);
			result.setCode(ResultCode.OK);
			result.setReturnObj(new Object[]{uploadFileInfo.getFilePath()+realDestFilePath});
		}catch(IOException e){
			result.setCode(ResultCode.Error1007);
			logger.error("<<method:uploadImage() | upload Image "+srcFile.getOriginalFilename()+" error!",e);
		}
		return result;
	}
	
    /**
     * 程序入口：用于测试
    * @param args
     */
    public static void main(String[] args) throws Exception{
       /* // 1-缩放图像：
        // 方法一：按比例缩放
    	ImageHelper.scale("e:/abc.jpg", "e:/abc_scale.jpg", 2, true);//测试OK
        // 方法二：按高度和宽度缩放
    	ImageHelper.scaleWithWidthHeight("e:/abc.jpg", "e:/abc_scale2.jpg", 500, 300, true);//测试OK

        // 2-切割图像：
        // 方法一：按指定起点坐标和宽高切割
    	ImageHelper.cut("e:/abc.jpg", "e:/abc_cut.jpg", 0, 0, 400, 400 );//测试OK
        // 方法二：指定切片的行数和列数
    	ImageHelper.cut2("e:/abc.jpg", "e:/", 2, 2 );//测试OK
        // 方法三：指定切片的宽度和高度
    	ImageHelper.cut3("e:/abc.jpg", "e:/", 300, 300 );//测试OK

        // 3-图像类型转换：
    	ImageHelper.convert("e:/abc.jpg", "GIF", "e:/abc_convert.gif");//测试OK

        // 4-彩色转黑白：
    	ImageHelper.gray("e:/abc.jpg", "e:/abc_gray.jpg");//测试OK

        // 5-给图片添加文字水印：
        // 方法一：
    	ImageHelper.pressText("我是水印文字","e:/abc.jpg","e:/abc_pressText.jpg","宋体",Font.BOLD,Color.white,80, 0, 0, 0.5f);//测试OK
        // 方法二：
    	ImageHelper.pressText2("我也是水印文字", "e:/abc.jpg","e:/abc_pressText2.jpg", "黑体", 36, Color.white, 80, 0, 0, 0.5f);//测试OK
        
        // 6-给图片添加图片水印：
    	ImageHelper.pressImage("e:/abc2.jpg", "e:/abc.jpg","e:/abc_pressImage.jpg", 0, 0, 0.5f);	//测试OK
        */
    	//ImageHelper.scale2("e:/1.jpg", "e:/2.jpg", 300, 500, true); //测试OK
    	//ImageHelper.scale("e:/1.jpg", "e:/2.jpg", 8, false);
    	//ImageHelper.pressText("我是水印文字","e:/1.jpg","e:/2.jpg","宋体",Font.BOLD,Color.white,80, 0, 0, 0.5f);
    	//ImageHelper.pressImage("e:/3.jpg", "e:/1.jpg","e:/2.jpg", 0, 0, 0.5f);	//测试OK
    	ImageHelper.scaleWithWidthHeight("e:/6.jpg", "e:/7.jpg",  500, 700);//测试OK
    }
}

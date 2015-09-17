package com.gwghk.mis.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageMarkLogoByIcon {

	public static void main(String[] args) {   
        String srcImgPath = "E:/1363055545144_source500.jpg";   
        String iconPath = "E:/水印LOGO不透明描白边.png";   
        String iconPath1 = "E:/LOGO_QB.png"; //200 * 53  
        String targerPath = "E:/1363055545144_source500_1_不透明描白边.jpg";   
        String targerPath2 = "E:/1362556790769_source500_2_不透明全白.jpg";   
        // 给图片添加水印   
        ImageMarkLogoByIcon.markImageByIcon(iconPath1, srcImgPath, targerPath);   
        ImageMarkLogoByIcon.markImageByIcon(iconPath1, srcImgPath, targerPath2);
        // 给图片添加水印,水印旋转-45   
        // ImageMarkLogoByIcon.markImageByIcon(iconPath, srcImgPath, targerPath2, -45);   
  
    }   
	
	/**  
     * 给图片添加水印  
     * @param iconPath 水印图片路径  
     * @param srcImgPath 源图片路径  
     * @param targerPath 目标图片路径  
     */  
    public static void markImageByIcon(String iconPath, String srcImgPath,   
            String targerPath) {   
        markImageByIcon(iconPath, srcImgPath, targerPath, null);   
    }   
  
    /**  
     * 给图片添加水印、可设置水印图片旋转角度  
     * @param iconPath 水印图片路径  
     * @param srcImgPath 源图片路径  
     * @param targerPath 目标图片路径  
     * @param degree 水印图片旋转角度  
     */  
    public static void markImageByIcon(String iconPath, String srcImgPath,   
            String targerPath, Integer degree) {   
        OutputStream os = null;   
        try {   
            Image srcImg = ImageIO.read(new File(srcImgPath));   
            System.out.println(srcImg.getWidth(null)+"--"+srcImg.getHeight(null));
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),   
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);   
  
            // 得到画笔对象   
            // Graphics g= buffImg.getGraphics();   
            Graphics2D g = buffImg.createGraphics();   
  
            // 设置对线段的锯齿状边缘处理   
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,   
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
  
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg   
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);   
  
            if (null != degree) {   
                // 设置水印旋转   
                g.rotate(Math.toRadians(degree),   
                        (double) buffImg.getWidth() / 2, (double) buffImg   
                                .getHeight() / 2);   
            }   
  
            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度   
            ImageIcon imgIcon = new ImageIcon(iconPath);   
  
            // 得到Image对象。   
            Image img = imgIcon.getImage();   
  
            float alpha = 0.4f; // 透明度   
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,   
                    alpha));   
  
            // 表示水印图片的位置   
            int widthtemp=0;
            int heighttemp=0;
            if(srcImg.getWidth(null)>img.getWidth(null)){
            	widthtemp=srcImg.getWidth(null)-img.getWidth(null);
            }else{
            	widthtemp=img.getWidth(null);
            }
            if(srcImg.getHeight(null)>img.getHeight(null)){
            	heighttemp=srcImg.getHeight(null)-img.getHeight(null);
            }else{
            	heighttemp=img.getHeight(null);
            }
            g.drawImage(img, widthtemp, heighttemp, null);   
  
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));   
  
            g.dispose();   
  
            os = new FileOutputStream(targerPath);   
  
            // 生成图片   
            ImageIO.write(buffImg, "JPG", os);   
  
            System.out.println("图片完成添加Icon印章。。。。。。");   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (null != os)   
                    os.close();   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
        }   
    }   
}

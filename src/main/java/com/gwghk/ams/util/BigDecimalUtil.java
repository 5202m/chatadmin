package com.gwghk.ams.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 摘要：数字处理类
 * @author Gavin.guo
 * @date 2014-10-17
 */
public class BigDecimalUtil {

	// 默认除法运算精度 
    private static final int DEF_DIV_SCALE = 10;
	
	public enum Operator {
		Add, Subtract, Muliply, Divide;
	}

	public static BigDecimal convertFromDouble(Double arg0) {
		if (arg0 == null) {
			return BigDecimal.ZERO;
		} else {
			return new BigDecimal(arg0);
		}
	}

	public static Double convertToDouble(BigDecimal arg0) {
		if (arg0 == null) {
			return 0.0;
		}
		return arg0.doubleValue();
	}

	public static BigDecimal calculate(Operator operator, Double source,
			Double target) {
		BigDecimal sourceBigDecimal = convertFromDouble(source);
		BigDecimal targetBigDecimal = convertFromDouble(target);
		switch (operator) {
		case Add:
			return add(sourceBigDecimal, targetBigDecimal);
		case Subtract:
			return subtract(sourceBigDecimal, targetBigDecimal);
		case Muliply:
			return multiply(sourceBigDecimal, targetBigDecimal);
		case Divide:
			return divide(sourceBigDecimal, targetBigDecimal);
		}
		return null;
	}

	public static BigDecimal add(BigDecimal source, BigDecimal target) {
		return target.add(source);
	}

	public static BigDecimal subtract(BigDecimal source, BigDecimal target) {
		return target.subtract(source);
	}

	public static BigDecimal multiply(BigDecimal source, BigDecimal target) {
		return target.multiply(source);
	}

	public static BigDecimal divide(BigDecimal source, BigDecimal target) {
		return target.divide(source);
	}

	public static BigDecimal divide(BigDecimal source, BigDecimal target,
			RoundingMode roundingMode) {
		return target.divide(source, roundingMode);
	}
	
	/**
	 * 功能：求字符串的绝对值
	 * @param str  字符串
	 * @return 字符串的绝对值
	 */
	public static Double abs(String str){
		return Math.abs(Double.valueOf(str));
	}
	
	/** 
     * 功能：提供精确的加法运算。 
     * @param v1 被加数 
     * @param v2 加数 
     * @return 两个参数的和 
     */ 
    public static double add(double v1,double v2){ 
        BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
        BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
        return b1.add(b2).doubleValue();
    }
  
    /** 
     * 功能：提供精确的减法运算。 
     * @param v1 被减数 
     * @param v2 减数 
     * @return 两个参数的差 
     */ 
    public static double sub(double v1,double v2){ 
        BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
        BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
        return b1.subtract(b2).doubleValue(); 
    }
  
    /** 
     * 功能：提供精确的乘法运算。 
     * @param v1 被乘数 
     * @param v2 乘数 
     * @return 两个参数的积 
     */ 

    public static double mul(double v1,double v2){ 
        BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
        BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
        return b1.multiply(b2).doubleValue(); 
    }

    /** 
     * 功能：提供（相对）精确的除法运算，当发生除不尽的情况时，精确到小数点以后 10 位，以后的数字四舍五入
     * @param v1 被除数 
     * @param v2 除数 
     * @return 两个参数的商 
     */ 
    public static double div(double v1,double v2){ 
        return div(v1,v2,DEF_DIV_SCALE); 
    }

    /** 
     * 功能：提供（相对）精确的除法运算。当发生除不尽的情况时，由 scale 参数指定精度，以后的数字四舍五入
     * @param v1 被除数 
     * @param v2 除数 
     * @param scale 表示表示需要精确到小数点以后几位。 
     * @return 两个参数的商 
     */ 
    public static double div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero"); 
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
        BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
    }

    /**
     * 功能：提供精确的小数位四舍五入处理。 
     * @param v 需要四舍五入的数字 
     * @param scale 小数点后保留几位 
     * @return 四舍五入后的结果
     */ 
    public static double round(double v,int scale){ 
        if(scale<0){ 
            throw new IllegalArgumentException("The scale must be a positive integer or zero"); 
        } 
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1"); 
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
    }
    
    /**
	 * 功能：格式化小数位
	 * @param source   需要格式化的数字
	 * @param format   格式    默认为2位
	 * @return 		    格式化后的字符串#.00
	 */
	public static String formatStr(Double source,String format){
		if(null == format || format.isEmpty()){
			format = "#.00";
		}
		DecimalFormat decimalFormat = new DecimalFormat(format);
		return decimalFormat.format(source);
	}
	
    /**
	 * 功能：格式化小数位
	 * @param source   需要格式化的数字
	 * @param format   格式    默认为2位
	 * @return 		    格式化后的字符串#.00
	 */
	/*public static String formatStr(BigDecimal source,String format){
		if(null == source || source.doubleValue() == 0.0){
			return "0.00";
		}
		if(null == format || format.isEmpty()){
			format = "#.00";
		}
		//DecimalFormat decimalFormat = new DecimalFormat(format);
		//String.format("%1$.2f", data)
		//return decimalFormat.format(source);	
		return String.format("%1$.2f", source.doubleValue());
	}*/
	
	/**
	 * 功能：格式化为2位
	 * @param source  需要格式化的数字
	 * @return 2位的字符串
	 */
	public static String toFixedTwoDights(BigDecimal source){
		if(null == source || source.doubleValue() == 0.0){
			return "0.00";
		}
		return String.format("%1$.2f", source.doubleValue());
	}
	
	/**
	 * 功能：格式化为2位
	 * @param source  需要格式化的数字
	 * @return 2位的字符串
	 */
	public static String toFixedTwoDights(double source){
		if(source == 0.0){
			return "0.00";
		}
		return String.format("%1$.2f", source);
	}
	
	/**
	 * 功能：格式化为3位
	 * @param source  需要格式化的数字
	 * @return 3位的字符串
	 */
	public static String toFixedThreeDights(BigDecimal source){
		if(null == source || source.doubleValue() == 0.0){
			return "0.000";
		}
		return String.format("%1$.3f", source.doubleValue());
	}
	
	/**
	 * 功能：格式化为3位
	 * @param source  需要格式化的数字
	 * @return 3位的字符串
	 */
	public static String toFixedThreeDights(double source){
		if(source == 0.0){
			return "0.000";
		}
		return String.format("%1$.3f", source);
	}
	
	/**
	 * 功能：格式化为指定的位数
	 * @param source   需要格式化的数字
	 * @param dights   显示的位数
	 * @return 指定的位数的值
	 */
	public static String toFixedFormatDights(BigDecimal source,int dights){
		if(null == source || source.doubleValue() == 0.0){
			if(null == source){
				return String.format("%1$."+dights+"f", 0d); 
			}
			if(dights < 0){
				dights = 0;
			}
			return String.format("%1$."+dights+"f", source.doubleValue());
		}
		return String.format("%1$."+dights+"f", source.doubleValue());
	}
	
	/**
	 * 功能：格式化为指定的位数
	 * @param source   需要格式化的数字
	 * @param dights   显示的位数
	 * @return 指定的位数的值
	 */
	public static String toFixedFormatDights(double source,int dights){
		if(source == 0.0){
			if(dights < 0){
				dights = 0;
			}
			return String.format("%1$."+dights+"f", source);
		}
		return String.format("%1$."+dights+"f", source);
	}
	
	/**
	 * 功能：判断当前值(BigDecimal类型)是否大于等于0
	 * @param source 当前值
	 * @return  true 大于等于0  false 小于0
	 */
	public static boolean isGteZero(BigDecimal source){
		return source != null && source.compareTo(new BigDecimal(0)) >= 0;
	}
	
	/**
	 * 功能：判断当前值(BigDecimal类型)是否小于等于0
	 * @param source 当前值
	 * @return  true 小于等于0  false 大于0
	 */
	public static boolean islteZero(BigDecimal source){
		return source != null && source.compareTo(new BigDecimal(0)) <= 0;
	}
	
	public static void main(String[] args) {
		System.out.println(String.format("%1$,d", 1222302562));
	}
}

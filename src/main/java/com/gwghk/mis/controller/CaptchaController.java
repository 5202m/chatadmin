package com.gwghk.mis.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gwghk.mis.captcha.SimpleCaptcha;
import com.gwghk.mis.captcha.SimpleCaptchaBlan;
import com.gwghk.mis.captcha.SimpleCaptchaLine;
import com.gwghk.mis.captcha.SuperCaptcha;
import com.gwghk.mis.captcha.SuperSpecCaptcha;
import com.gwghk.mis.captcha.util.Randoms;
import com.gwghk.mis.util.ContextHolderUtils;

@Scope("prototype")
@Controller
public class CaptchaController extends BaseController{
	
	/**
	 * 功能：系统管理-后台登录-获取验证码
	 */
	@RequestMapping(value = "/captchaController/get", method = RequestMethod.GET)
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String type = request.getParameter("type");
		if(StringUtils.isBlank(type)){
			type = String.valueOf(Randoms.num(1, 3));	//产生随机类别[from,to),目前3或4暂时不用
		}
		if("1".equals(type)){							//png格式验证码
			SuperCaptcha captcha = new SuperSpecCaptcha(120,50,4);
			captcha.out(response.getOutputStream());
			ContextHolderUtils.getSession().setAttribute("complexCaptcha", captcha.text());   //将验证码存入session
		}
		else if("2".equals(type)){
			String text = SimpleCaptcha.captcha(120, 50, 4, response.getOutputStream());
			ContextHolderUtils.getSession().setAttribute("complexCaptcha", text);  			  //将验证码存入session
		}
		else if("3".equals(type)){
			SimpleCaptchaBlan blan = new SimpleCaptchaBlan();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			String text = blan.captcha(120, 50, 4, out);
			ContextHolderUtils.getSession().setAttribute("complexCaptcha", text);   		 	//将验证码存入session
		}
		else if("4".equals(type)){
			SimpleCaptchaLine line = new SimpleCaptchaLine();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			String text = line.captcha(120, 50, 4, out);
			ContextHolderUtils.getSession().setAttribute("complexCaptcha", text);   			//将验证码存入session
		}
	}
}

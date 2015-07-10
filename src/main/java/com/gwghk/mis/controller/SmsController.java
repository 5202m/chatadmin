package com.gwghk.mis.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gwghk.mis.common.model.AjaxJson;
import com.gwghk.mis.util.SmsUtil;
import com.gwghk.mis.util.StringUtil;

/**
 * 摘要：发送短信
 * @author Gavin.guo
 * @date   2015-07-10
 */
@Scope("prototype")
@Controller
public class SmsController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(SmsController.class);
	
	/**
	 * 功能：发送短信
	 */
	@RequestMapping(value = "/sms/send", method = RequestMethod.POST)
	@ResponseBody
	public String send(HttpServletRequest request){
		String mobile = request.getParameter("mobile"),content=request.getParameter("content");
		AjaxJson result = new AjaxJson();
		if(StringUtils.isEmpty(mobile)){
			result.setSuccess(false);
    		result.setMsg("手机号码不能为空");
    		logger.info("<<send()|mobile:"+mobile+",手机号码不能为空!");
    		return JSON.toJSONString(result);
		}
		if(StringUtils.isEmpty(content)){
			content = StringUtil.randomNum(6);
		}
		String sendSmsResult = SmsUtil.sendSms(mobile, content);
		if("error".equals(sendSmsResult)){
			result.setSuccess(false);
    		result.setMsg("发送短信失败!");
    		logger.info("<<send()|mobile:"+mobile+",发送短信失败!");
    		return JSON.toJSONString(result);
		}
		result.setSuccess(true);
		logger.info("<<send()|mobile:"+mobile+",send sms success!");
		return JSON.toJSONString(result);
	}
}

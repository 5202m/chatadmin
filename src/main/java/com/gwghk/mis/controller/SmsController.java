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
	 * 功能：发送短信(result:0  表示发送成功 ,result:1 表示发送失败)
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
    		return "{\"result\":1,\"msg\":\"手机号码不能为空\"}";
		}
		if(StringUtils.isEmpty(content)){
			content = StringUtil.randomNum(6);
		}
		String sendSmsResult = SmsUtil.sendSms(mobile, content);
		if("error".equals(sendSmsResult)){
			result.setSuccess(false);
    		result.setMsg("发送短信失败!");
    		return "{\"result\":1,\"msg\":\"发送短信失败!\"}";
		}
		result.setSuccess(true);
		logger.info("<<send()|mobile:"+mobile+",send sms success!");
		return "{\"result\":0,\"msg\":\"发送短信成功!\",\"verifyCode\":"+content+"}";
	}
}

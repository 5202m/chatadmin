package com.gwghk.mis.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.gwghk.mis.common.model.AjaxJson;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.enums.SortDirection;
import com.gwghk.mis.model.BoDict;
import com.gwghk.mis.model.BoUser;
import com.gwghk.mis.service.DictService;
import com.gwghk.mis.service.UserService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.CacheManager;
import com.gwghk.mis.util.ContextHolderUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;
import com.gwghk.mis.util.ResourceUtil;

/**
 * 摘要：登录
 * @author Gavin.guo
 * @date   2014-10-14
 */
@Scope("prototype")
@Controller
public class LoginController extends BaseController{

	private Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DictService dictService;
	
	/**
	 * 功能：进入登录页
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap map){
		String locale = request.getParameter("locale");
		map.addAttribute("locale",StringUtils.isEmpty(locale) ? WebConstant.LOCALE_ZH_CN : locale);
		return "login/login";
	}
	
	/**
	 * 功能：进入登录页
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(HttpServletRequest request, ModelMap map){
		String locale = request.getParameter("locale");
		map.addAttribute("locale",StringUtils.isEmpty(locale) ? WebConstant.LOCALE_ZH_CN : locale);
		return "login/login";
	}
	
   /**
	* 功能：检查用户登录
	*/
	@RequestMapping(value="/loginController/checkLogin",method=RequestMethod.POST)
	@ResponseBody
	public AjaxJson checkLogin(BoUser mngUser, HttpServletRequest req){
		logger.info(">>method:checkLogin()|"+mngUser.getUserNo()+" try to login！");
		AjaxJson ajaxResult = new AjaxJson();
		String captcha = req.getParameter("code");
		Object sessionCaptcha = ContextHolderUtils.getSession().getAttribute("complexCaptcha");
		if(StringUtils.isEmpty(captcha) || sessionCaptcha == null || !captcha.equalsIgnoreCase(sessionCaptcha.toString())){
			ajaxResult.setMsg(ResourceBundleUtil.getByMessage("1017"));  //验证码不正确
			ajaxResult.setSuccess(false);
            return ajaxResult;
		}
		mngUser.setLoginIp(req.getRemoteAddr());
		ApiResult result = userService.login(mngUser);
		if(result != null && result.isOk()){
			// 更新用户信息(如IP、登录时间、登录次数)，保存当前登录用户,并写入登录日志
			mngUser = (BoUser)result.getReturnObj()[0];
			String message = "用户: " + mngUser.getUserNo() + ",IP:"+IPUtil.getClientIP(req)+","
						   + DateUtil.getDateSecondFormat(new Date()) + " 登录成功";
            //将账户放入session中
			ContextHolderUtils.getSession().setAttribute(ContextHolderUtils.getSessionId(), mngUser.getUserNo());
            logService.addLog(message, WebConstant.Log_Leavel_INFO,WebConstant.Log_Type_LOGIN
            				 ,BrowserUtils.checkBrowse(req),IPUtil.getClientIP(req));
            logger.info(message);
            ajaxResult.setSuccess(true);
            return ajaxResult;
		}else{
			logger.error("<<method:checkLogin()|"+mngUser.getUserNo()+" login fail,[IP]:"+IPUtil.getClientIP(req)
						+",userService.login()"+",[ErrorMsg]:"+result.toString());
			ajaxResult.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
			ajaxResult.setSuccess(false);
            return ajaxResult;
		}
	}
	
	/**
	 * 功能：跳转到主页
	 */
	@RequestMapping(value="/main",method=RequestMethod.GET)
	public ModelAndView main(){
		HashMap<String,SortDirection> dictMap = new HashMap<String,SortDirection>();
		dictMap.put("id", SortDirection.ASC);
		dictMap.put("sort", SortDirection.ASC);
		DetachedCriteria<BoDict> detachedCriteria = new DetachedCriteria<BoDict>();
		detachedCriteria.setOrderbyMap(dictMap);
		List<BoDict> dictParamList =dictService.getDictList(detachedCriteria);
		if(dictParamList != null && dictParamList.size() > 0){//将数据字典列表放到缓存中
			CacheManager.putContent(WebConstant.DICT_KEY, dictParamList, -1);
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("userNo", userParam.getUserNo());
		map.put("locale", ResourceUtil.getSessionLocale());
		return new ModelAndView("main/main",map);
	}
	
	/**
	 * 功能：修改密码
	 */
	@RequestMapping(value="/loginController/pwdChange")
	public ModelAndView toPwdChange(HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("userNo", userParam.getUserNo());
		return new ModelAndView("main/pwdchange",map);
	}
	
	/**
	 * 功能：保存修改的密码
	 */
	@RequestMapping(value="/loginController/doPwdChange",method=RequestMethod.POST)
	@ResponseBody
	public AjaxJson doPwdChange(HttpServletRequest request) {
		AjaxJson ajaxResult = new AjaxJson();
		String userNo = request.getParameter("userNo");
		String oldpwd = request.getParameter("oldpwd");
		String newpwd = request.getParameter("newpwd");
		ApiResult result = userService.saveChangePwd(userNo,oldpwd,newpwd);
		if(result != null && result.isOk()){
			String message = "用户："+userNo+",旧密码："+oldpwd+",新密码："+newpwd+" 修改成功!";
			logService.addLog(message, WebConstant.Log_Leavel_INFO,WebConstant.Log_Type_UPDATE
    				 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
			logger.info(message);
			ajaxResult.setSuccess(true);
			return ajaxResult;
		}else{
			logger.error("<<method:doPwdChange()|用户："+userNo+",旧密码："+oldpwd+",新密码："+newpwd+" 修改失败,[IP]:"
						+IPUtil.getClientIP(request)+",[ErrorMsg]:"+result.toString());
			ajaxResult.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
			ajaxResult.setSuccess(false);
			return ajaxResult;
		}
	}
	
	
	/**
	 * 功能：无权限页面提示跳转
	 */
	@RequestMapping(value="/loginController/noAuth")
	public ModelAndView noAuth(HttpServletRequest request) {
		return new ModelAndView("common/noAuth");
	}
	
	/**
	 * 功能：判断session是否过期
	 */
	@RequestMapping(value="/sessionValid",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJson sessionValid(HttpServletRequest request) {
		Object userNoSession = ContextHolderUtils.getSession().getAttribute(ContextHolderUtils.getSessionId());
		AjaxJson j = new AjaxJson();
		j.setSuccess(userNoSession != null);
		return j;
	}
	
	/**
	 * 功能：退出系统
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public  ModelAndView logout(HttpServletRequest request) {
		if(userParam != null){
			String message = "用户: " + userParam.getUserNo() + ",IP:"+IPUtil.getClientIP(request)+","+DateUtil.getDateSecondFormat(new Date()) + " 退出系统";
			logService.addLog(message,WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_EXIT
							 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
			logger.info(message);
		}
		ContextHolderUtils.getSession().removeAttribute(ContextHolderUtils.getSessionId());
		ContextHolderUtils.getSession().removeAttribute(WebConstant.SESSION_MENU_KEY);
		ContextHolderUtils.getSession().removeAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		ContextHolderUtils.getSession().removeAttribute(WebConstant.WW_TRANS_I18N_LOCALE);
		ContextHolderUtils.getSession().invalidate();
		return new ModelAndView("redirect:/login.do");
	}
}

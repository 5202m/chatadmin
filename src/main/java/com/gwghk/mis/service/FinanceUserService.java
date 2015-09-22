package com.gwghk.mis.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.dao.FinanceUserDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.BindPlatForm;
import com.gwghk.mis.model.Collect;
import com.gwghk.mis.model.FinancePlatForm;
import com.gwghk.mis.model.LoginPlatform;
import com.gwghk.mis.model.Member;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.MD5;

/**
 * 投资社区--成员管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年6月11日 <BR>
 * Description : <BR>
 * <p>
 * </p>
 */
@Service
public class FinanceUserService {

	@Autowired
	private FinanceUserDao financeUserDao;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private MemberBalanceService memberBalanceService;

	/**
	 * 成员信息列表查询
	 * @param financeUser
	 * @param dCriteria
	 * @param memberId
	 * @param mobilephone
	 * @return
	 */
	public Page<Member> getFinanceUsers(FinancePlatForm financeUser, DetachedCriteria<Member> dCriteria, String memberId, String mobilephone) {
		Query query=new Query();
		
		Criteria criteria = Criteria.where("valid").is(1);
		criteria.and("loginPlatform.financePlatForm.isDeleted").is(1);
		if (StringUtils.isNotBlank(memberId)) {
			criteria.and("_id").is(memberId);
		}
		if(StringUtils.isNotBlank(mobilephone)){
			criteria.and("mobilePhone").regex(mobilephone);
		}
		criteria.and("loginPlatform.financePlatForm").exists(true);
		if(financeUser != null){
			boolean hasStartTime = StringUtils.isNotBlank(financeUser.getTimeStart()); 
			if(hasStartTime){
				criteria = criteria.and("loginPlatform.financePlatForm.registerDate").gte(DateUtil.parseDateSecondFormat(financeUser.getTimeStart()));
			}
			if(StringUtils.isNotBlank(financeUser.getTimeEnd())){
				if(hasStartTime){
					criteria.lte(DateUtil.parseDateSecondFormat(financeUser.getTimeEnd()));
				}else{
					criteria.and("loginPlatform.financePlatForm.registerDate").lte(DateUtil.parseDateSecondFormat(financeUser.getTimeEnd()));
				}
			}
			if(ArrayUtils.isNotEmpty(financeUser.getBindPlatformType())){
				criteria.and("loginPlatform.financePlatForm.bindPlatformList.type").all((Object[])financeUser.getBindPlatformType());
			}
			
			if(financeUser.getUserGroup() != null){
				criteria.and("loginPlatform.financePlatForm.userGroup").is(financeUser.getUserGroup());
			}
			if(financeUser.getIsRecommend() != null){
				criteria.and("loginPlatform.financePlatForm.isRecommend").is(financeUser.getIsRecommend());
			}
			if(StringUtils.isNotBlank(financeUser.getNickName())){
				criteria.and("loginPlatform.financePlatForm.nickName").regex(financeUser.getNickName());
			}
			if(financeUser.getSex() != null){
				criteria.and("loginPlatform.financePlatForm.sex").is(financeUser.getSex());
			}
			if(financeUser.getIsGag() != null){
				criteria.and("loginPlatform.financePlatForm.isGag").is(financeUser.getIsGag());
			}
			if(financeUser.getIsBack() != null){
				criteria.and("loginPlatform.financePlatForm.isBack").is(financeUser.getIsBack());
			}
		}
		query.addCriteria(criteria);
		return financeUserDao.getFinanceUsers(query, dCriteria);
	}
	
	/**
	 * 按照id查找
	 * @param id
	 * @return
	 */
	public Member findById(String id) {
		return financeUserDao.findByKey("_id", id);
	}
	
	/**
	 * 根据memberId-->获取member统计信息
	 */
	public Member getMemberById(String memberId){
		return financeUserDao.getMemberById(memberId);
	}
	
	/**
	 * 按照手机号查找
	 * @param mobilePhone
	 * @return
	 */
	public Member findByMobile(String mobilePhone) {
		return financeUserDao.findByKey("mobilePhone", mobilePhone);
	}
	
	/**
	 * 按照昵称查询用户
	 * @param nickName
	 * @return
	 */
	public List<Member> getMembersByNickName(String nickName){
		return financeUserDao.findByNickName(nickName);
	}
	
	/**
	 * 按照memberId查询用户昵称，如果没有查询到，返回null
	 * @param id
	 * @return
	 */
	public String getNickNameById(String id){
		Member loc_member = this.findById(id);
		String loc_nickName = null;
		if(loc_member != null && loc_member.getLoginPlatform() != null && loc_member.getLoginPlatform().getFinancePlatForm() != null){
			loc_nickName = loc_member.getLoginPlatform().getFinancePlatForm().getNickName();
		}
		return loc_nickName;
	}
	
	/**
	 * 按照昵称模糊查询memberId数组，如果没有记录，返回一个空数组，非null。
	 * @param nickName
	 * @return
	 */
	public String[] getMemberIdsByNickName(String nickName){
		List<Member> loc_members = financeUserDao.findByNickName(nickName);
		int len = loc_members == null ? 0 : loc_members.size();
		String[] loc_ids = new String[len];
		for(int i = 0; i < len; i++){
			loc_ids[i] = loc_members.get(i).getMemberId();
		}
		return loc_ids;
	}
	
	/**
	 * 新增保存
	 * @param member
	 * @return
	 */
	public ApiResult add(Member member) {
		ApiResult result=new ApiResult();
		Member loc_member = memberService.getByMobilePhone(member.getMobilePhone());
		//客户手机号已经存在，并且投资社区登录平台有效
		if (loc_member != null && loc_member.getLoginPlatform() != null 
				&& loc_member.getLoginPlatform().getFinancePlatForm() != null 
				&& new Integer(1).equals(loc_member.getLoginPlatform().getFinancePlatForm().getIsDeleted())) {
			return result.setCode(ResultCode.Error102);
		}
		List<Member> memberList = memberService.getBackMember();
		if(memberList != null && memberList.size() > 0 && member.getLoginPlatform().getFinancePlatForm().getIsBack() == 1){
			return result.setCode(ResultCode.Error1011);
		}
		
		//初始化绑定平台数据结构
		FinancePlatForm loc_financePlatForm = member.getLoginPlatform().getFinancePlatForm();
		loc_financePlatForm.setRegisterDate(new Date());
		loc_financePlatForm.setIsDeleted(1);
		loc_financePlatForm.setPassword(MD5.getMd5(WebConstant.MD5_KEY + member.getMobilePhone().substring(3)));
		loc_financePlatForm.setLoginSystem("");
		loc_financePlatForm.setIsGag(0);    //默认不禁言
		loc_financePlatForm.setAttentions(new ArrayList<String>());
		loc_financePlatForm.setBeAttentions(new ArrayList<String>());
		loc_financePlatForm.setTopicCount(0);
		loc_financePlatForm.setCollects(new ArrayList<Collect>());
		List<BindPlatForm> loc_bindPlatformList = new ArrayList<BindPlatForm>();
		String[] accoNos = null;
		if(StringUtils.isNotBlank(loc_financePlatForm.getBindPlatformMicroBlog())){
			accoNos = loc_financePlatForm.getBindPlatformMicroBlog().split(";");
			for (String accoNo : accoNos) {
				loc_bindPlatformList.add(new BindPlatForm(1, accoNo));
			}
			loc_financePlatForm.setBindPlatformMicroBlog(null);
		}
		if(StringUtils.isNotBlank(loc_financePlatForm.getBindPlatformWeiChat())){
			accoNos = loc_financePlatForm.getBindPlatformWeiChat().split(";");
			for (String accoNo : accoNos) {
				loc_bindPlatformList.add(new BindPlatForm(2, accoNo));
			}
			loc_financePlatForm.setBindPlatformWeiChat(null);
		}
		if(StringUtils.isNotBlank(loc_financePlatForm.getBindPlatformQQ())){
			accoNos = loc_financePlatForm.getBindPlatformQQ().split(";");
			for (String accoNo : accoNos) {
				loc_bindPlatformList.add(new BindPlatForm(3, accoNo));
			}
			loc_financePlatForm.setBindPlatformQQ(null);
		}
		if(loc_bindPlatformList.isEmpty() == false){
			loc_financePlatForm.setBindPlatformList(loc_bindPlatformList);
		}
		
		if(loc_member != null){
			//手机号存在，直接设置投资社区的LoginPlatform
			if(loc_member.getLoginPlatform() != null){
				loc_member.setLoginPlatform(new LoginPlatform());
			}
			loc_member.getLoginPlatform().setFinancePlatForm(member.getLoginPlatform().getFinancePlatForm());
			loc_member.setUpdateDate(new Date());
			loc_member.setUpdateIp(member.getCreateIp());
			loc_member.setUpdateUser(member.getCreateUser());
		}else{
			loc_member = member;
			loc_member.setStatus(1);
			loc_member.setValid(1);
			loc_member.setCreateDate(new Date());
			loc_member.setCreateIp(member.getCreateIp());
			loc_member.setCreateUser(member.getCreateUser());
			loc_member.setUpdateDate(new Date());
			loc_member.setUpdateIp(member.getCreateIp());
			loc_member.setUpdateUser(member.getCreateUser());
		}
		memberService.save(loc_member);
		//重建一个新的资产信息，对于新会员，在执行save之后会有memberId
		memberBalanceService.rebuild(loc_member.getMemberId(), member.getCreateIp(), member.getCreateUser());
    	return result.setCode(ResultCode.OK);
	}

	
	/**
	 * 功能：修改保存
	 */
	public ApiResult edit(Member member) {
		ApiResult result=new ApiResult();
		FinancePlatForm loc_financePlatForm = member.getLoginPlatform().getFinancePlatForm();
		List<BindPlatForm> loc_bindPlatformList = new ArrayList<BindPlatForm>();  //初始化绑定平台数据结构
		String[] accoNos = null;
		if(StringUtils.isNotBlank(loc_financePlatForm.getBindPlatformMicroBlog())){
			accoNos = loc_financePlatForm.getBindPlatformMicroBlog().split(";");
			for (String accoNo : accoNos) {
				loc_bindPlatformList.add(new BindPlatForm(1, accoNo));
			}
			loc_financePlatForm.setBindPlatformMicroBlog(null);
		}
		if(StringUtils.isNotBlank(loc_financePlatForm.getBindPlatformWeiChat())){
			accoNos = loc_financePlatForm.getBindPlatformWeiChat().split(";");
			for (String accoNo : accoNos) {
				loc_bindPlatformList.add(new BindPlatForm(2, accoNo));
			}
			loc_financePlatForm.setBindPlatformWeiChat(null);
		}
		if(StringUtils.isNotBlank(loc_financePlatForm.getBindPlatformQQ())){
			accoNos = loc_financePlatForm.getBindPlatformQQ().split(";");
			for (String accoNo : accoNos) {
				loc_bindPlatformList.add(new BindPlatForm(3, accoNo));
			}
			loc_financePlatForm.setBindPlatformQQ(null);
		}
		loc_financePlatForm.setBindPlatformList(loc_bindPlatformList);
		
		Member oldMember = memberService.getByMemberId(member.getMemberId());
		if(oldMember != null){
			FinancePlatForm oldfinancePlatForm = oldMember.getLoginPlatform().getFinancePlatForm();
			BeanUtils.copyExceptNull(oldfinancePlatForm, loc_financePlatForm);
			oldfinancePlatForm.setBindPlatformList(loc_financePlatForm.getBindPlatformList());
			oldMember.getLoginPlatform().setFinancePlatForm(oldfinancePlatForm);
			financeUserDao.update(oldMember);
		}
    	return result.setCode(ResultCode.OK);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public ApiResult delete(String id) {
		ApiResult result=new ApiResult();
		financeUserDao.delete(id);
		//删除资产信息
		memberBalanceService.delete(id);
    	return result.setCode(ResultCode.OK);
	}
	
	/**
	 * 功能：重置密码
	 * @param userId 用户ID
	 * @param pwd 新密码
	 */
    public ApiResult saveResetPwd(String memberId, String newPwd){
    	ApiResult result = new ApiResult();
    	Member member = memberService.getByMemberId(memberId);
    	if(member != null && member.getLoginPlatform() != null && member.getLoginPlatform().getFinancePlatForm() != null){
    		member.getLoginPlatform().getFinancePlatForm().setPassword(MD5.getMd5(newPwd));
    		financeUserDao.update(member);
    		return result.setCode(ResultCode.OK);
    	}else{
    		return result.setCode(ResultCode.Error1010);
    	}
    }
    
    /**
	 * 功能：禁止发言(取消禁止发言)
	 */
    public ApiResult saveGag(String memberId, Integer isGag){
    	ApiResult result = new ApiResult();
    	Member member = memberService.getByMemberId(memberId);
    	LoginPlatform loginPlatform = member.getLoginPlatform();
    	if(member != null && loginPlatform != null && loginPlatform.getFinancePlatForm() != null){
    		loginPlatform.getFinancePlatForm().setIsGag(isGag);
    		financeUserDao.update(member);
    		topicService.updateTopic(memberId, isGag);
    		return result.setCode(ResultCode.OK);
    	}else{
    		return result.setCode(ResultCode.FAIL);
    	}
    }
    
    /**
	 * 功能：推荐用户
	 */
    public ApiResult saveRecommend(String memberId, Integer isRecommend){
    	ApiResult result = new ApiResult();
    	Member member = memberService.getByMemberId(memberId);
    	LoginPlatform loginPlatform = member.getLoginPlatform();
    	if(member != null && loginPlatform != null && loginPlatform.getFinancePlatForm() != null){
    		loginPlatform.getFinancePlatForm().setIsRecommend(isRecommend);;
    		financeUserDao.update(member);
    		return result.setCode(ResultCode.OK);
    	}else{
    		return result.setCode(ResultCode.FAIL);
    	}
    }
    
    /**
	 * 功能：更新发帖数
	 * @param  memberId  会员Id
	 * @param  num       更新贴的数量
	 */
	public boolean updateTopicCount(String memberId,Number num){
		return financeUserDao.updateTopicCount(memberId, num);
	}
}

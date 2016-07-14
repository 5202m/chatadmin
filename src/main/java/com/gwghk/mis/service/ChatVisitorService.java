package com.gwghk.mis.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.controller.ChatVisitorController;
import com.gwghk.mis.dao.ChatVisitorDao;
import com.gwghk.mis.enums.ChatClientGroup;
import com.gwghk.mis.enums.ChatOnlineDuration;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.ChatGroup;
import com.gwghk.mis.model.ChatVisitor;
import com.gwghk.mis.model.ChatVisitorStat;
import com.gwghk.mis.model.ChatVisitorStatData;
import com.gwghk.mis.model.ChatVisitorStatGroup;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPParser;
import com.gwghk.mis.util.StringUtil;

/**
 * 聊天室访客记录service<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2016年01月08日 <BR>
 * Description : <BR>
 * <p>
 * 聊天室访客记录
 * </p>
 */
@Service
public class ChatVisitorService
{
	private static final Logger logger = LoggerFactory.getLogger(ChatVisitorService.class);
	
	@Autowired
	private ChatVisitorDao chatVisitorDao;
	
	@Autowired
	private ChatGroupService chatGroupService;
	
	/**
	 * 访客信息列表查询
	 * 
	 * @param dCriteria
	 * @return
	 */
	public Page<ChatVisitor> getChatVisitors(DetachedCriteria<ChatVisitor> dCriteria)
	{
		ChatVisitor chatVisitor = dCriteria.getSearchModel();
		Query query = new Query();
		Criteria criteria = Criteria.where("valid").is(1);
		if (chatVisitor != null)
		{
			if (StringUtils.isNotBlank(chatVisitor.getMobile()))
			{
				criteria.and("mobile").is(chatVisitor.getMobile());
			}
			if (StringUtils.isNotBlank(chatVisitor.getRoomId()))
			{
				criteria.and("roomId").is(chatVisitor.getRoomId());
			}
			if (StringUtils.isNotBlank(chatVisitor.getGroupType()))
			{
				criteria.and("groupType").is(chatVisitor.getGroupType());
			}
			if (chatVisitor.getLoginStatus() != null)
			{
				if(new Integer(2).equals(chatVisitor.getLoginStatus()))
				{
					criteria.and("loginStatus").is(new Integer(0));
					criteria.and("loginTimes").is(new Integer(0));
				}else{
					criteria.and("loginStatus").is(chatVisitor.getLoginStatus());
				}
			}
			if (chatVisitor.getOnlineStatus() != null)
			{
				criteria.and("onlineStatus").is(chatVisitor.getOnlineStatus());
			}
			
			boolean hasStartTime = StringUtils.isNotBlank(chatVisitor.getOnlineDateStart()); 
			if(hasStartTime){
				criteria = criteria.and("onlineDate").gte(DateUtil.parseDateSecondFormat(chatVisitor.getOnlineDateStart()));
			}
			if(StringUtils.isNotBlank(chatVisitor.getOnlineDateEnd())){
				if(hasStartTime){
					criteria.lte(DateUtil.parseDateSecondFormat(chatVisitor.getOnlineDateEnd()));
				}else{
					criteria.and("onlineDate").lte(DateUtil.parseDateSecondFormat(chatVisitor.getOnlineDateEnd()));
				}
			}
			
			hasStartTime = StringUtils.isNotBlank(chatVisitor.getLoginDateStart()); 
			if(hasStartTime){
				criteria = criteria.and("loginDate").gte(DateUtil.parseDateSecondFormat(chatVisitor.getLoginDateStart()));
			}
			if(StringUtils.isNotBlank(chatVisitor.getLoginDateEnd())){
				if(hasStartTime){
					criteria.lte(DateUtil.parseDateSecondFormat(chatVisitor.getLoginDateEnd()));
				}else{
					criteria.and("loginDate").lte(DateUtil.parseDateSecondFormat(chatVisitor.getLoginDateEnd()));
				}
			}
			if("pc".equalsIgnoreCase(chatVisitor.getUserAgent())){
				criteria.and("userAgent").not().regex(Pattern.compile(".*?(iphone|ipod|ipad|android|mobile|playbook|bb10|meego).*", Pattern.CASE_INSENSITIVE));
			}else if("mobile".equalsIgnoreCase(chatVisitor.getUserAgent())){
				criteria.and("userAgent").regex(Pattern.compile(".*?(iphone|ipod|ipad|android|mobile|playbook|bb10|meego).*", Pattern.CASE_INSENSITIVE));
			}
			if(StringUtils.isNotBlank(chatVisitor.getPlatform())){
				if(chatVisitor.getPlatform().equals("studio")){
					criteria.and("platform").in(null, "");
				}else{
					criteria.and("platform").is(chatVisitor.getPlatform());
				}
			}
			if (StringUtils.isNotBlank(chatVisitor.getNickname()))
			{
				criteria.and("nickname").regex("(?i)" + StringUtil.toFuzzyMatch(chatVisitor.getNickname()));
			}
			query.addCriteria(criteria);
		}
		Page<ChatVisitor> loc_result = chatVisitorDao.queryChatVisitors(query, dCriteria);
		this.precessOfflineAndIp(loc_result);
		return loc_result;
	}
	
	/**
	 * 计算在线时长
	 * @param chatVisitorPage
	 */
	private void precessOfflineAndIp(Page<ChatVisitor> chatVisitorPage){
		if(chatVisitorPage == null || chatVisitorPage.getCollection() == null || chatVisitorPage.getCollection().isEmpty()){
			return;
		}
		long loc_nowTime = new Date().getTime();
		Integer loc_onlineFlag = new Integer(1);
		long loc_duration = 0;
		String loc_durationStr = null;
		IPParser ipParser=new IPParser(); 
		for (ChatVisitor loc_chatVisitor : chatVisitorPage.getCollection())
		{
			//解析IP
			try{
				loc_chatVisitor.setIpCity(ipParser.getIPLocation(loc_chatVisitor.getIp()).getCountry());
			}catch(Exception e){
				logger.error("precessOfflineAndIp<< parse ip error:" + e);
			}
			//在线时长
			if(loc_onlineFlag.equals(loc_chatVisitor.getOnlineStatus())){
				//在线
				loc_duration = loc_nowTime;
			}else{
				//离线
				loc_duration = loc_chatVisitor.getOfflineDate() != null ? loc_chatVisitor.getOfflineDate().getTime() : 0;
			}
			if(loc_duration != 0 && loc_chatVisitor.getOnlineDate() != null){
				loc_duration = loc_duration - loc_chatVisitor.getOnlineDate().getTime();
			}else{
				loc_duration = 0;
			}
			loc_durationStr = "";
			if (loc_duration >= 86400000)
			{
				loc_durationStr += loc_duration / 86400000 + "天";
				loc_duration = loc_duration % 86400000;
			}
			if (loc_duration >= 3600000)
			{
				loc_durationStr += loc_duration / 3600000 + "小时";
				loc_duration = loc_duration % 3600000;
			}
			if (loc_duration >= 60000)
			{
				loc_durationStr += loc_duration / 60000 + "分";
				loc_duration = loc_duration % 60000;
			}
			if (loc_duration >= 1000)
			{
				loc_durationStr += loc_duration / 1000 + "秒";
			}
			loc_chatVisitor.setOnLineDuration(loc_durationStr);
		}
	}
	
	public static void main(String[] args)
	{
		IPParser ipParser=new IPParser();
		System.out.println(ipParser.getIPLocation("120.237.129.78").getCountry());
		System.out.println(ipParser.getIPLocation("42.120.226.92").getCountry());
	}

	/**
	 * 删除
	 * 
	 * @param chatVisitorIds
	 * @return
	 */
	public ApiResult delete(String[] chatVisitorIds)
	{
		ApiResult result = new ApiResult();
		boolean isOk = chatVisitorDao.delete(chatVisitorIds);
		return result.setCode(isOk ? ResultCode.OK : ResultCode.FAIL);
	}
	
	/**
	 * 查找指定时间段有登录的用户
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param isOnlyLogin 是否仅查询已登录的用户
	 * @return
	 */
	public List<ChatVisitor> getChatVisitorsByDate(Date startDate, Date endDate, boolean isOnlyLogin){
		Query query = new Query();
		Criteria criteria = Criteria.where("valid").is(1);
		if (isOnlyLogin)
		{
			criteria.and("userId").nin(new Object[]{null, ""});
		}else{
			criteria.and("userId").in(new Object[]{null, ""});
		}
		criteria.orOperator(Criteria.where("offlineDate").gte(startDate).lt(endDate), 
							Criteria.where("onlineStatus").is(1));
		query.addCriteria(criteria);

		List<Order> orders = new ArrayList<Order>();
		if (isOnlyLogin){
			orders.add(new Order(Direction.ASC, "userId"));
		}else{
			orders.add(new Order(Direction.ASC, "clientStoreId"));
		}
		orders.add(new Order(Direction.ASC, "groupType"));
		orders.add(new Order(Direction.ASC, "roomId"));
		query.with(new Sort(orders));
		return chatVisitorDao.queryChatVisitors(query);
	}
	
	/**
	 * 保存统计信息数据
	 * @param visitorStat
	 * @param isTimePoint 是否按照时间点统计
	 */
	public void saveVisitorStat(ChatVisitorStat visitorStat, boolean isTimePoint){
		ChatVisitorStat loc_visitorStat = chatVisitorDao.findChatVisitorStat(visitorStat.getDataDate(), visitorStat.getGroupType(), visitorStat.getGroupId());
		if(loc_visitorStat == null){
			if (isTimePoint){
				visitorStat.setStatDuration(new ArrayList<ChatVisitorStatData>());
				visitorStat.setStatOnline(new ArrayList<ChatVisitorStatGroup>());
			}else{
				visitorStat.setStatTimePoint(new ArrayList<ChatVisitorStatData>());
			}
			chatVisitorDao.add(visitorStat);
		}else{
			if (isTimePoint){
				loc_visitorStat.getStatTimePoint().add(visitorStat.getStatTimePoint().get(0));
			}else{
				loc_visitorStat.setStatOnline(visitorStat.getStatOnline());
				loc_visitorStat.setStatDuration(visitorStat.getStatDuration());
			}
			chatVisitorDao.update(loc_visitorStat);
		}
	}
	
	/**
	 * 计算访客在线时长毫秒数
	 * @param visitor 访客记录
	 * @param start   统计日期开始时间
	 * @param end     统计日期结束时间
	 * @return
	 */
	private long getOnlineDuration(ChatVisitor visitor, Date start, Date end){
		long loc_result = 0;
		if(visitor.getOnlineStatus() == 1){
			//当前在线
			if(visitor.getOnlineDate() != null && visitor.getOnlineDate().compareTo(start) > 0){
				//当前在线且上线时间是统计日00:00之后
				loc_result = Math.max(end.getTime() - visitor.getOnlineDate().getTime(), 0);
			}else{
				//当前在线，且上线时间是统计日00:00之前(全天在线)
				loc_result = 86400000;
			}
		}
		if(visitor.getOnlineMSDate() != null && visitor.getOnlineMSDate().equals(start) && visitor.getOnlineMS() != null){
			loc_result += visitor.getOnlineMS();
		}
		return loc_result;
	}
	
	/**
	 * 初始化统计数据(客户组)
	 * @param statDate
	 * @return
	 */
	private List<ChatVisitorStatGroup> initVisitorStatClientGroups(){
		List<ChatVisitorStatGroup> loc_result = new ArrayList<ChatVisitorStatGroup>();
		ChatClientGroup[] chatClientGroups = ChatClientGroup.values(); 
		ChatClientGroup clientGroup = null;
		ChatVisitorStatGroup loc_stat = null;
		for(int i = 0, lenI = chatClientGroups.length; i < lenI; i++){
			clientGroup = chatClientGroups[i];
			loc_stat = new ChatVisitorStatGroup();
			loc_stat.setClientGroup(clientGroup.getValue());
			loc_stat.setOnlineNum(0);
			loc_stat.setUsers(new ArrayList<String>());
			loc_result.add(loc_stat);
		}
		return loc_result;
	}
	
	/**
	 * 初始化统计数据（时长区间、时间点）
	 * @return
	 */
	private List<ChatVisitorStatData> initVisitorStatDurations()
	{
		List<ChatVisitorStatData> loc_result = new ArrayList<ChatVisitorStatData>();
		ChatVisitorStatData loc_data = null;
		ChatOnlineDuration[] durations = ChatOnlineDuration.values();
		for(ChatOnlineDuration duration : durations)
		{
			loc_data = new ChatVisitorStatData();
			loc_data.setDuration(duration.name());
			loc_data.setData(this.initVisitorStatClientGroups());
			loc_result.add(loc_data);
		}
		return loc_result;
	}
	
	/**
	 * 初始化统计数据（各类在线人数统计，按天）（整点在线人数统计，按时间点）
	 * @param chatGroups
	 * @return
	 */
	private Map<String, List<ChatVisitorStatGroup>> statInitOnline(List<ChatGroup> chatGroups){
		Map<String, List<ChatVisitorStatGroup>> loc_result = new HashMap<String, List<ChatVisitorStatGroup>>();
		ChatGroup chatGroup = null;
		String loc_groupType = null;
		for (int i = 0, lenI = chatGroups == null ? 0 : chatGroups.size(); i < lenI; i++)
		{
			chatGroup = chatGroups.get(i);
			if(chatGroup.getGroupType().contains("studio"))//目前只统计直播间
			{
				if(chatGroup.getGroupType().equals(loc_groupType) == false){
					//按房间组别分组合计数据
					loc_groupType = chatGroup.getGroupType();
					loc_result.put(chatGroup.getGroupType(), this.initVisitorStatClientGroups());
				}
				//按房间分组数据
				loc_result.put(chatGroup.getGroupType() + "_" + chatGroup.getId(), this.initVisitorStatClientGroups());
			}
		}
		//所有合计
		loc_result.put("", this.initVisitorStatClientGroups());
		return loc_result;
	}
	
	/**
	 * 统计访客信息（各类在线人数统计，按天）（整点在线人数统计，按时间点）
	 * @param statMap
	 * @param visitors
	 * @param isLogin
	 * @return
	 */
	private Map<String, List<ChatVisitorStatGroup>> statVisitorsOnline(Map<String, List<ChatVisitorStatGroup>> statMap, List<ChatVisitor> visitors, boolean isLogin){
		List<ChatVisitorStatGroup> loc_statAll = statMap.get(""); //总统计
		ChatVisitor loc_visitor = null;
		List<ChatVisitorStatGroup> loc_stat = null;
		List<ChatVisitorStatGroup> loc_statGroup = null;
		String loc_userKey = null;    //客户标识
		String loc_groupType = null;        //客户组别标识
		String loc_groupId = null;          //房间号标识
		boolean isNewCst = false;           //是否新客户
		boolean isNewGroupType = false;     //是否新客户组
		boolean isNewGroupId = false;       //是否新房间
		int loc_index = -1;
		for (int i = 0, lenI = visitors == null ? 0 : visitors.size(); i < lenI; i++)
		{
			loc_visitor = visitors.get(i);
			loc_index = ChatClientGroup.getClientGroupIndex(loc_visitor.getClientGroup());
			if(loc_index < 0){
				continue;
			}
			
			if(loc_userKey == null || loc_userKey.equals(isLogin ? loc_visitor.getUserId() : loc_visitor.getClientStoreId()) == false){//新的客户端访客信息
				isNewCst = true;
				isNewGroupType = true;
				isNewGroupId = true;
			}else if(loc_groupType == null || loc_groupType.equals(loc_visitor.getGroupType()) == false){//新的房间组别
				isNewCst = false;
				isNewGroupType = true;
				isNewGroupId = true;
			}else if(loc_groupId == null || loc_groupId.equals(loc_visitor.getRoomId()) == false){
				isNewCst = false;
				isNewGroupType = false;
				isNewGroupId = true;
			}else{
				isNewCst = false;
				isNewGroupType = false;
				isNewGroupId = false;
			}
			
			if (isNewCst)
			{
				loc_userKey = isLogin ? loc_visitor.getUserId() : loc_visitor.getClientStoreId();
				loc_userKey = loc_userKey == null ? "" : loc_userKey;
				loc_statAll.get(loc_index).addUser(loc_userKey);
			}
			if(isNewGroupType){
				loc_groupType = loc_visitor.getGroupType();
				loc_groupType = loc_groupType == null ? "" : loc_groupType;
				loc_statGroup = statMap.get(loc_groupType);
				if(loc_statGroup != null){
					loc_statGroup.get(loc_index).addUser(loc_userKey);
				}
			}
			if (isNewGroupId)
			{
				loc_groupId = loc_visitor.getRoomId();
				loc_groupId = loc_groupId == null ? "" : loc_groupId;
				loc_stat = statMap.get(loc_visitor.getGroupType() + "_" + loc_groupId);
				if(loc_stat != null){
					loc_stat.get(loc_index).addUser(loc_userKey);
				}
			}
		}
		return statMap;
	}
	
	/**
	 * 统计访客信息（在线时长人数统计，按天）
	 * @param chatGroups
	 * @return
	 */
	private Map<String, List<ChatVisitorStatData>> statInitDuration(List<ChatGroup> chatGroups){
		Map<String, List<ChatVisitorStatData>> loc_result = new HashMap<String, List<ChatVisitorStatData>>();
		ChatGroup chatGroup = null;
		String loc_groupType = null;
		for (int i = 0, lenI = chatGroups == null ? 0 : chatGroups.size(); i < lenI; i++)
		{
			chatGroup = chatGroups.get(i);
			if(chatGroup.getGroupType().contains("studio"))//目前只统计直播间
			{
				if(chatGroup.getGroupType().equals(loc_groupType) == false){
					//按房间组别分组合计数据
					loc_groupType = chatGroup.getGroupType();
					loc_result.put(chatGroup.getGroupType(), this.initVisitorStatDurations());
				}
				//按房间分组数据
				loc_result.put(chatGroup.getGroupType() + "_" + chatGroup.getId(), this.initVisitorStatDurations());
			}
		}
		//所有合计
		loc_result.put("", this.initVisitorStatDurations());
		return loc_result;
	}
	
	/**
	 * 统计访客信息（在线时长人数统计，按天）
	 * @param statMap
	 * @param visitors
	 * @param dateStart
	 * @param dateEnd
	 * @param isLogin
	 * @return
	 */
	private Map<String, List<ChatVisitorStatData>> statVisitorsDuration(Map<String, List<ChatVisitorStatData>> statMap, List<ChatVisitor> visitors, Date dateStart, Date dateEnd, boolean isLogin){
		List<ChatVisitorStatData> loc_statAll = statMap.get(""); //总统计
		ChatVisitor loc_visitor = null;
		List<ChatVisitorStatData> loc_stat = null;
		List<ChatVisitorStatData> loc_statGroup = null;
		String loc_userKey = null;    //客户标识
		String loc_groupType = null;        //客户组别标识
		String loc_groupId = null;          //房间号标识
		long loc_duration = 0;              //客户房间在线时长
		long loc_durationGroup = 0;         //客户房间组别在线总时长
		long loc_durationAll = 0;           //客户在线总时长
		boolean isNewCst = false;           //是否新客户
		boolean isNewGroupType = false;     //是否新客户组
		boolean isNewGroupId = false;       //是否新房间
		int loc_index1 = -1;
		int loc_index2 = -1;
		for (int i = 0, lenI = visitors == null ? 0 : visitors.size(); i < lenI; i++)
		{
			loc_visitor = visitors.get(i);
			loc_index1 = ChatClientGroup.getClientGroupIndex(loc_visitor.getClientGroup());
			if(loc_index1 < 0){
				continue;
			}
			
			if(loc_userKey == null || loc_userKey.equals(isLogin ? loc_visitor.getUserId() : loc_visitor.getClientStoreId()) == false){//新的客户端访客信息
				isNewCst = true;
				isNewGroupType = true;
				isNewGroupId = true;
			}else if(loc_groupType == null || loc_groupType.equals(loc_visitor.getGroupType()) == false){//新的房间组别
				isNewCst = false;
				isNewGroupType = true;
				isNewGroupId = true;
			}else if(loc_groupId == null || loc_groupId.equals(loc_visitor.getRoomId()) == false){//新的房间组别
				isNewCst = false;
				isNewGroupType = false;
				isNewGroupId = true;
			}else{
				isNewCst = false;
				isNewGroupType = false;
				isNewGroupId = false;
			}

			if(isNewGroupId){
				loc_groupId = loc_visitor.getRoomId();
				loc_groupId = loc_groupId == null ? "" : loc_groupId;
				if(loc_stat != null){
					loc_index2 = ChatOnlineDuration.getDurationIndex(loc_duration);
					if(loc_index2 != -1){
						loc_stat.get(loc_index2).getData().get(loc_index1).addUser(loc_userKey);
					}
				}
				loc_stat = statMap.get(loc_visitor.getGroupType() + "_" + loc_groupId);
				loc_durationGroup += loc_duration;
				loc_durationAll += loc_duration;
				loc_duration = this.getOnlineDuration(loc_visitor, dateStart, dateEnd);
			}else{
				loc_duration += this.getOnlineDuration(loc_visitor, dateStart, dateEnd);
			}
			
			if(isNewGroupType){
				if(loc_statGroup != null){
					loc_index2 = ChatOnlineDuration.getDurationIndex(loc_durationGroup);
					if(loc_index2 >= 0){
						loc_statGroup.get(loc_index2).getData().get(loc_index1).addUser(loc_userKey);
					}
				}
				loc_groupType = loc_visitor.getGroupType();
				loc_groupType = loc_groupType == null ? "" : loc_groupType;
				loc_statGroup = statMap.get(loc_groupType);
				loc_durationGroup = 0;
			}

			if(isNewCst){
				if(loc_userKey != null){
					loc_index2 = ChatOnlineDuration.getDurationIndex(loc_durationAll);
					if(loc_index2 >= 0){
						loc_statAll.get(loc_index2).getData().get(loc_index1).addUser(loc_userKey);
					}
				}
				loc_userKey = isLogin ? loc_visitor.getUserId() : loc_visitor.getClientStoreId();
				loc_userKey = loc_userKey == null ? "" : loc_userKey;
				loc_durationAll = 0;
			}
		}
		if(loc_index1 != -1)
		{
			loc_durationGroup += loc_duration;
			loc_durationAll += loc_duration;
			if (loc_stat != null)
			{
				loc_index2 = ChatOnlineDuration.getDurationIndex(loc_duration);
				if(loc_index2 != -1){
					loc_stat.get(loc_index2).getData().get(loc_index1).addUser(loc_userKey);
				}
			}
			if(loc_statGroup != null){
				loc_index2 = ChatOnlineDuration.getDurationIndex(loc_durationGroup);
				if(loc_index2 != -1){
					loc_statGroup.get(loc_index2).getData().get(loc_index1).addUser(loc_userKey);
				}
			}
			if(loc_userKey != null){
				loc_index2 = ChatOnlineDuration.getDurationIndex(loc_durationAll);
				if(loc_index2 != -1){
					loc_statAll.get(loc_index2).getData().get(loc_index1).addUser(loc_userKey);
				}
			}
		}
		return statMap;
	}
	
	/**
	 * 统计访客信息（按天统计）
	 * @param startDate 统计开始日期时间（统计日期00:00:00）
	 * @param endDate  统计结束时间（统计日期23:59:59）
	 * @return
	 */
	public Map<String, ChatVisitorStat> statVisitors(Date startDate, Date endDate){
		List<ChatGroup> loc_groups = chatGroupService.getChatGroupList("id","name", "groupType");
		List<ChatVisitor> loc_users = this.getChatVisitorsByDate(startDate, endDate, true);
		List<ChatVisitor> loc_visitors = this.getChatVisitorsByDate(startDate, endDate, false);
		
		Map<String, List<ChatVisitorStatGroup>> loc_statOnline = this.statInitOnline(loc_groups);
		loc_statOnline = this.statVisitorsOnline(loc_statOnline, loc_users, true);
		loc_statOnline = this.statVisitorsOnline(loc_statOnline, loc_visitors, false);
		
		Map<String, List<ChatVisitorStatData>> loc_statDuration = this.statInitDuration(loc_groups);
		loc_statDuration = this.statVisitorsDuration(loc_statDuration, loc_users, startDate, endDate, true);
		loc_statDuration = this.statVisitorsDuration(loc_statDuration, loc_visitors, startDate, endDate, false);
		
		Map<String, ChatVisitorStat> loc_result = new HashMap<String, ChatVisitorStat>();
		//将统计数据保存到数据库
		ChatGroup loc_group = null;
		String loc_groupType = null;
		String loc_key = null; 
		ChatVisitorStat loc_stat = null;
		for (int i = 0, lenI = loc_groups == null ? 0 : loc_groups.size(); i < lenI; i++)
		{
			loc_group = loc_groups.get(i);
			if(loc_group.getGroupType().contains("studio"))//目前只统计直播间
			{
				if(loc_group.getGroupType().equals(loc_groupType) == false){
					//按房间组别分组合计数据
					loc_groupType = loc_group.getGroupType();
					loc_key = loc_groupType;
					loc_stat = new ChatVisitorStat();
					loc_stat.setDataDate(startDate);
					loc_stat.setGroupType(loc_groupType);
					loc_stat.setGroupId("");
					loc_stat.setStatOnline(loc_statOnline.get(loc_key));
					loc_stat.setStatDuration(loc_statDuration.get(loc_key));
					loc_result.put(loc_key, loc_stat);
					this.saveVisitorStat(loc_stat, false);
				}
				//按房间分组数据
				loc_key = loc_group.getGroupType() + "_" + loc_group.getId();
				loc_stat = new ChatVisitorStat();
				loc_stat.setDataDate(startDate);
				loc_stat.setGroupType(loc_group.getGroupType());
				loc_stat.setGroupId(loc_group.getId());
				loc_stat.setStatOnline(loc_statOnline.get(loc_key));
				loc_stat.setStatDuration(loc_statDuration.get(loc_key));
				loc_result.put(loc_key, loc_stat);
				this.saveVisitorStat(loc_stat, false);
			}
		}
		//总计
		loc_key = "";
		loc_stat = new ChatVisitorStat();
		loc_stat.setDataDate(startDate);
		loc_stat.setGroupType("");
		loc_stat.setGroupId("");
		loc_stat.setStatOnline(loc_statOnline.get(loc_key));
		loc_stat.setStatDuration(loc_statDuration.get(loc_key));
		loc_result.put(loc_key, loc_stat);
		this.saveVisitorStat(loc_stat, false);
		
		return loc_result;
	}
	
	/**
	 * 统计访客信息（按整点统计）
	 * @param statDate 统计日期，要求统计日期的开始（时 分 秒 毫秒值均为0）
	 * @param startDate 统计开始时间
	 * @param endDate  统计结束时间
	 * @return
	 */
	public Map<String, ChatVisitorStat> statVisitors(Date statDate, Date startDate, Date endDate){
		List<ChatGroup> loc_groups = chatGroupService.getChatGroupList("id","name", "groupType");
		String loc_timeStr = DateUtil.formatDate(startDate, "HH:mm"); 
		List<ChatVisitor> loc_users = this.getChatVisitorsByDate(startDate, endDate, true);
		List<ChatVisitor> loc_visitors = this.getChatVisitorsByDate(startDate, endDate, false);
		
		Map<String, List<ChatVisitorStatGroup>> loc_statOnline = this.statInitOnline(loc_groups);
		loc_statOnline = this.statVisitorsOnline(loc_statOnline, loc_users, true);
		loc_statOnline = this.statVisitorsOnline(loc_statOnline, loc_visitors, false);
		
		Map<String, ChatVisitorStat> loc_result = new HashMap<String, ChatVisitorStat>();
		//将统计数据保存到数据库
		ChatGroup loc_group = null;
		String loc_groupType = null;
		String loc_key = null; 
		ChatVisitorStat loc_stat = null;
		List<ChatVisitorStatData> loc_statDataList = null;
		ChatVisitorStatData loc_statData = null; 
		for (int i = 0, lenI = loc_groups == null ? 0 : loc_groups.size(); i < lenI; i++)
		{
			loc_group = loc_groups.get(i);
			if(loc_group.getGroupType().contains("studio"))//目前只统计直播间
			{
				if(loc_group.getGroupType().equals(loc_groupType) == false){
					//按房间组别分组合计数据
					loc_groupType = loc_group.getGroupType();
					loc_key = loc_groupType;
					loc_stat = new ChatVisitorStat();
					loc_stat.setDataDate(statDate);
					loc_stat.setGroupType(loc_groupType);
					loc_stat.setGroupId("");
					loc_statData = new ChatVisitorStatData();
					loc_statDataList = new ArrayList<ChatVisitorStatData>();
					loc_statData.setTimePoint(loc_timeStr);
					loc_statData.setData(loc_statOnline.get(loc_key));
					loc_statDataList.add(loc_statData);
					loc_stat.setStatTimePoint(loc_statDataList);
					loc_result.put(loc_key, loc_stat);
					this.saveVisitorStat(loc_stat, true);
				}
				//按房间分组数据
				loc_key = loc_group.getGroupType() + "_" + loc_group.getId();
				loc_stat = new ChatVisitorStat();
				loc_stat.setDataDate(statDate);
				loc_stat.setGroupType(loc_group.getGroupType());
				loc_stat.setGroupId(loc_group.getId());
				loc_statData = new ChatVisitorStatData();
				loc_statDataList = new ArrayList<ChatVisitorStatData>();
				loc_statData.setTimePoint(loc_timeStr);
				loc_statData.setData(loc_statOnline.get(loc_key));
				loc_statDataList.add(loc_statData);
				loc_stat.setStatTimePoint(loc_statDataList);
				loc_result.put(loc_key, loc_stat);
				this.saveVisitorStat(loc_stat, true);
			}
		}
		//总计
		loc_key = "";
		loc_stat = new ChatVisitorStat();
		loc_stat.setDataDate(statDate);
		loc_stat.setGroupType("");
		loc_stat.setGroupId("");
		loc_statData = new ChatVisitorStatData();
		loc_statDataList = new ArrayList<ChatVisitorStatData>();
		loc_statData.setTimePoint(loc_timeStr);
		loc_statData.setData(loc_statOnline.get(loc_key));
		loc_statDataList.add(loc_statData);
		loc_stat.setStatTimePoint(loc_statDataList);
		loc_result.put(loc_key, loc_stat);
		this.saveVisitorStat(loc_stat, true);
		
		return loc_result;
	}
	
	/**
	 * 观看时长用户数量统计
	 * @param groupType
	 * @param groupId
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	public JSONArray repVisitorDuration(String groupType, String groupId, Date dateStart, Date dateEnd){
		JSONArray loc_result = new JSONArray();
		List<ChatVisitorStat> loc_dbStats = chatVisitorDao.queryChatVisitorStat(groupType, groupId, dateStart, dateEnd);
		if(loc_dbStats == null || loc_dbStats.isEmpty()){
			return loc_result;
		}
		
		//总计
		JSONObject loc_totalStat = new JSONObject();
		for (ChatOnlineDuration duration : ChatOnlineDuration.values())
		{
			loc_totalStat.put(duration.name(), 0);
		}
		loc_totalStat.put("statSum", 0);
		
		//提取数据
		ChatVisitorStat loc_dbStat = null;
		List<ChatVisitorStatData> loc_dbStatDataArr = null;
		ChatVisitorStatData loc_dbStatData = null;
		JSONObject loc_stat = null;
		int loc_statTemp = 0;
		int loc_statSum = 0;
		int loc_statSumAll = 0;
		for(int i = 0, lenI = loc_dbStats.size(); i < lenI; i++){
			loc_statSum = 0;
			loc_dbStat = loc_dbStats.get(i);
			loc_dbStatDataArr = loc_dbStat.getStatDuration();
			loc_stat = new JSONObject();
			for(int j = 0, lenJ = loc_dbStatDataArr == null ? 0 : loc_dbStatDataArr.size(); j < lenJ; j++){
				loc_dbStatData = loc_dbStatDataArr.get(j);
				loc_statTemp = loc_dbStatData.sumOnlineNum();
				loc_stat.put(loc_dbStatData.getDuration(), loc_statTemp);
				loc_totalStat.put(loc_dbStatData.getDuration(), loc_totalStat.getInt(loc_dbStatData.getDuration()) + loc_statTemp);
				loc_statSum += loc_statTemp;
			}
			loc_stat.put("dataDate", DateUtil.formatDate(loc_dbStat.getDataDate(), "yyyy-MM-dd"));
			loc_stat.put("statSum", loc_statSum);
			loc_statSumAll += loc_statSum;
			loc_result.add(loc_stat);
		}
		
		//总计
		loc_totalStat.put("dataDate", "合计");
		loc_totalStat.put("statSum", loc_statSumAll);
		loc_result.add(loc_totalStat);
		return loc_result;
	}
	
	/**
	 * 各类在线人数统计
	 * @param groupType
	 * @param groupId
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	public JSONArray repVisitorOnline(String groupType, String groupId, Date dateStart, Date dateEnd){
		JSONArray loc_result = new JSONArray();
		List<ChatVisitorStat> loc_dbStats = chatVisitorDao.queryChatVisitorStat(groupType, groupId, dateStart, dateEnd);
		if(loc_dbStats == null || loc_dbStats.isEmpty()){
			return loc_result;
		}
		
		//总计
		Map<String, HashSet<String>> loc_usersMap = new HashMap<String, HashSet<String>>();
		for (ChatClientGroup group : ChatClientGroup.values())
		{
			loc_usersMap.put(group.name(), new HashSet<String>());
		}
		
		//提取数据
		ChatVisitorStat loc_dbStat = null;
		List<ChatVisitorStatGroup> loc_dbStatGroups = null;
		ChatVisitorStatGroup loc_dbStatGroup = null;
		JSONObject loc_stat = null;
		int loc_statTemp = 0;
		int loc_statSum = 0;
		for(int i = 0, lenI = loc_dbStats.size(); i < lenI; i++){
			loc_statSum = 0;
			loc_dbStat = loc_dbStats.get(i);
			loc_dbStatGroups = loc_dbStat.getStatOnline();
			loc_stat = new JSONObject();
			for(int j = 0, lenJ = loc_dbStatGroups == null ? 0 : loc_dbStatGroups.size(); j < lenJ; j++){
				loc_dbStatGroup = loc_dbStatGroups.get(j);
				loc_statTemp = loc_dbStatGroup.getOnlineNum();
				loc_stat.put(loc_dbStatGroup.getClientGroup(), loc_statTemp);
				if(loc_usersMap.containsKey(loc_dbStatGroup.getClientGroup())){
					loc_usersMap.get(loc_dbStatGroup.getClientGroup()).addAll(loc_dbStatGroup.getUsers());
				}
				loc_statSum += loc_statTemp;
			}
			loc_stat.put("dataDate", DateUtil.formatDate(loc_dbStat.getDataDate(), "yyyy-MM-dd"));
			loc_stat.put("statSum", loc_statSum);
			
			loc_result.add(loc_stat);
		}
		
		//总计
		JSONObject loc_totalStat = new JSONObject();
		int loc_statSumAll = 0;
		for (ChatClientGroup group : ChatClientGroup.values())
		{
			loc_statSum = loc_usersMap.get(group.name()).size();
			loc_statSumAll += loc_statSum;
			loc_totalStat.put(group.name(), loc_statSum);
		}
		loc_totalStat.put("dataDate", "合计");
		loc_totalStat.put("statSum", loc_statSumAll);
		loc_result.add(loc_totalStat);
		return loc_result;
	}
	
	/**
	 * 整点在线人数统计
	 * @param groupType
	 * @param groupId
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	public JSONArray repVisitorTimePoint(String groupType, String groupId, Date dateStart, Date dateEnd){
		JSONArray loc_result = new JSONArray();
		List<ChatVisitorStat> loc_dbStats = chatVisitorDao.queryChatVisitorStat(groupType, groupId, dateStart, dateEnd);
		if(loc_dbStats == null || loc_dbStats.isEmpty()){
			return loc_result;
		}
		
		//提取数据
		ChatVisitorStat loc_dbStat = null;
		List<ChatVisitorStatData> loc_dbStatDataArr = null;
		ChatVisitorStatData loc_dbStatData = null;
		JSONObject loc_stat = null;
		for(int i = 0, lenI = loc_dbStats.size(); i < lenI; i++){
			loc_dbStat = loc_dbStats.get(i);
			loc_dbStatDataArr = loc_dbStat.getStatTimePoint();
			loc_stat = new JSONObject();
			loc_stat.put("dataDate", DateUtil.formatDate(loc_dbStat.getDataDate(), "yyyy-MM-dd"));
			for(int j = 0, lenJ = loc_dbStatDataArr == null ? 0 : loc_dbStatDataArr.size(); j < lenJ; j++){
				loc_dbStatData = loc_dbStatDataArr.get(j);
				loc_stat.put(loc_dbStatData.getTimePoint(), loc_dbStatData.sumOnlineNum());
			}
			loc_result.add(loc_stat);
		}
		
		return loc_result;
	}
}

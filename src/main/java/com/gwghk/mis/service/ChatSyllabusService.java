package com.gwghk.mis.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.dao.ChatSyllabusDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.BoUser;
import com.gwghk.mis.model.ChatGroup;
import com.gwghk.mis.model.ChatSyllabus;

/**
 * 聊天室课程安排服务类
 * 
 * @author Dick.guo
 * @date 2015年4月1日
 */
@Service
public class ChatSyllabusService
{
	@Autowired
	private ChatSyllabusDao chatSyllabusDao;
	
	@Autowired
	private ChatGroupService chatGroupService;	
	
	@Autowired
	private UserService userService;

	/**
	 * 通过id找对应课程表记录
	 * 
	 * @param chatGroupType
	 * @param chatGroupId
	 * @return
	 */
	public ChatSyllabus getChatSyllabus(String chatGroupType, String chatGroupId)
	{
		Query query = new Query();
		Criteria criteria = Criteria.where("groupType").is(chatGroupType);
		//如果是空的，也需要传递
		criteria.and("groupId").is(chatGroupId);
		query.addCriteria(criteria);
		ChatSyllabus loc_result = chatSyllabusDao.findByGroupId(query);
		if(loc_result == null)
		{
			loc_result = new ChatSyllabus();
			loc_result.setGroupType(chatGroupType);
			loc_result.setGroupId(chatGroupId);
		}
		return loc_result;
	}
	
	/**
	 * 获取授权分析师
	 * @param chatGroupType
	 * @param chatGroupId
	 * @return
	 */
	public List<BoUser> getAuthUsers(String chatGroupType, String chatGroupId)
	{
		Set<String> loc_userNos = null;
		String[] loc_userIdArr = null;
		if(StringUtils.isNotBlank(chatGroupId))
		{
			ChatGroup chatGroup = chatGroupService.getChatGroupById(chatGroupId);
			if(chatGroup != null)
			{
				loc_userIdArr = chatGroup.getAuthUsers();
				if(loc_userIdArr != null && loc_userIdArr.length != 0)
				{
					loc_userNos = new HashSet<String>(Arrays.asList(loc_userIdArr));
				}
			}
		}
		else
		{
			List<ChatGroup> chatGroups = chatGroupService.findByGroupType(chatGroupType);
			loc_userNos = new HashSet<String>();
			for(int i = 0, lenI = (chatGroups == null) ? 0 : chatGroups.size(); i < lenI; i++)
			{
				loc_userIdArr = chatGroups.get(i).getAuthUsers();
				if(loc_userIdArr != null && loc_userIdArr.length != 0)
				{
					loc_userNos.addAll(Arrays.asList(loc_userIdArr));
				}
			}
		}
		
		
		List<BoUser> loc_authUsers = null;
		if(loc_userNos != null && loc_userNos.isEmpty() == false)
		{
			loc_authUsers = userService.getUserListByNo(loc_userNos.toArray(new String[0]));
			for(int i = (loc_authUsers == null) ? -1 : loc_authUsers.size() - 1; i >= 0; i--)
			{
				if(loc_authUsers.get(i) == null 
						|| loc_authUsers.get(i).getRole() == null 
						|| loc_authUsers.get(i).getRole().getRoleNo() == null
						||loc_authUsers.get(i).getRole().getRoleNo().startsWith("analyst") == false)
				{
					loc_authUsers.remove(i);
				}
			}
		}
		return loc_authUsers;
	}
	
	public static void main(String[] args)
	{
		String[] loc_s = null;
		Set<String> loc_userIds = new HashSet<String>(Arrays.asList(loc_s));
		System.out.println(loc_userIds);
	}

	/**
	 * 保存课程表
	 * 
	 * @param ChatSyllabus
	 * @return
	 */
	public ApiResult saveChatSyllabus(ChatSyllabus syllabus) {
		ApiResult loc_result=new ApiResult();
		ChatSyllabus loc_syllabus = this.getChatSyllabus(syllabus.getGroupType(), syllabus.getGroupId());
		if (loc_syllabus.getId() == null)
		{
			//没有相关课程表，直接添加
			chatSyllabusDao.add(syllabus);
		}
		else
		{
			//已经存在课程表，直接更新
			loc_syllabus.setCourses(syllabus.getCourses());
			loc_syllabus.setUpdateUser(syllabus.getUpdateUser());
			loc_syllabus.setUpdateIp(syllabus.getUpdateIp());
			loc_syllabus.setUpdateDate(syllabus.getUpdateDate());
			chatSyllabusDao.update(loc_syllabus);
		}
    	return loc_result.setCode(ResultCode.OK);
	}
}

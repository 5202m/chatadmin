package com.gwghk.mis.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.ChatPushInfoDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.ChatPushInfo;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.StringUtil;

/**
 * 聊天室组别管理服务类
 * @author Alan.wu
 * @date  2015年4月1日
 */
@Service
public class ChatPushInfoService{

	@Autowired
	private ChatPushInfoDao chatPushInfoDao;
	@Autowired
	private ChatApiService chatApiService;
	/**
	 * 查询列表
	 * @return
	 */
	public List<ChatPushInfo> getList() {
		return chatPushInfoDao.getList();
	}
	
	/**
	 * 通过id找对应记录
	 * @param id
	 * @return
	 */
	public ChatPushInfo getById(String id) {
		return chatPushInfoDao.findById(ChatPushInfo.class,id);
	}

	/**
	 * 保存
	 * @param pushInfoParam
	 * @param isUpdate
	 * @return
	 */
	public ApiResult save(ChatPushInfo pushInfoParam, boolean isUpdate) {
		ApiResult result=new ApiResult();
		pushInfoParam.setValid(1);
		if(isUpdate && StringUtils.isBlank(pushInfoParam.getId())){
			return result.setCode(ResultCode.Error103);
		}
		ChatPushInfo pushInfo=getById(pushInfoParam.getId());
    	if(isUpdate){
    		if(pushInfo==null){
    			return result.setCode(ResultCode.Error104);
    		}
    		BeanUtils.copyExceptNull(pushInfo, pushInfoParam);
    		pushInfo.setOnlineMin(pushInfoParam.getOnlineMin());
    		pushInfo.setIntervalMin(pushInfoParam.getIntervalMin());
    		chatPushInfoDao.update(pushInfo);
    		if(pushInfo.getPushType().equals(1) && pushInfo.getPosition().equals(4) && pushInfo.getValid().equals(1) && pushInfo.getStatus().equals(1)){
    			boolean isPushDate = DateUtil.dateTimeWeekCheck(pushInfo.getPushDate(), true);
    			if(isPushDate){
    				chatApiService.submitPushInfo(pushInfo.getId());//视频通知到客服端
    			}
    		}
    	}else{
    		if(pushInfo!=null){
    			return result.setCode(ResultCode.Error102);
    		}
    		chatPushInfoDao.add(pushInfoParam);	
    		if(pushInfoParam.getPushType().equals(1) && pushInfoParam.getPosition().equals(4) && pushInfoParam.getValid().equals(1) && pushInfoParam.getStatus().equals(1)){
    			boolean isPushDate = DateUtil.dateTimeWeekCheck(pushInfoParam.getPushDate(), true);
    			if(isPushDate){
    				chatApiService.submitPushInfo(pushInfoParam.getId());//视频通知到客服端
    			}
    		}
    	}
    	return result.setCode(ResultCode.OK);
	}

	/**
	 * 删除组别
	 * @param ids
	 * @return
	 */
	public ApiResult delete(String[] ids) {
		ApiResult api=new ApiResult();
    	boolean isSuccess =  chatPushInfoDao.softDelete(ChatPushInfo.class,ids);
    	if(isSuccess){
	    	int size=0;
	    	StringBuffer idsBuffer=new StringBuffer();
	    	for(int i =0;i<ids.length;i++){
	    		idsBuffer.append(ids[i]);
				if(i!=ids.length-1){
					idsBuffer.append(",");
    			}
			}
	    	ChatPushInfo row ;
	    	StringBuffer buffer=new StringBuffer();
	    	List<ChatPushInfo> list=chatPushInfoDao.getListByIds(ChatPushInfo.class,ids, "id","roomIds" ,"pushType","position","valid" , "status" , "pushDate");
	    	
	    	if(list!=null && (size=list.size())>0){
	    		boolean isPushDate;
	    		for(int i=0;i<size;i++){
	    			isPushDate = false;
	    			row = list.get(i);
	    			if(row.getPushType().equals(1) && row.getPosition().equals(4) && row.getValid().equals(1) && row.getStatus().equals(1)){
	    				isPushDate = DateUtil.dateTimeWeekCheck(row.getPushDate(), true);
	    			}
	    			if(isPushDate == false){
	    				continue;
	    			}
	    			if(row.getRoomIds() != null && row.getRoomIds().length>0){
	    				for(int j=0;j<row.getRoomIds().length;j++){
	    					if(buffer.indexOf(row.getRoomIds()[j] + ',')==-1){
	    						buffer.append(row.getRoomIds()[j]);
				    			buffer.append((j==row.getRoomIds().length-1 && i==size-1) ? "" :",");
	    					}
		    			}
	    			}
	    		}
		    }
	    	//通知聊天室客户端移除对应记录
    		if(idsBuffer.length()>0 && buffer.length()>0){
    			chatApiService.removePushInfo(buffer.toString() , idsBuffer.toString());
    		}
    	}
    	return api.setCode(isSuccess?ResultCode.OK:ResultCode.FAIL);
	}

	/**
	 * 分页查询
	 * @param dCriteria
	 * @return
	 */
	public Page<ChatPushInfo> getPage(
			DetachedCriteria<ChatPushInfo> dCriteria) {
		Criteria criter=new Criteria();
		criter.and("valid").is(1);
		ChatPushInfo model=dCriteria.getSearchModel();
		if(model!=null){
			if(StringUtils.isNotBlank(model.getGroupType())){
				criter.and("groupType").is(model.getGroupType());
			}
			if(model.getStatus()!=null){
				criter.and("status").is(model.getStatus());
			}
			if(StringUtils.isNotBlank(model.getContent())){
				criter.and("content").regex(StringUtil.toFuzzyMatch(model.getContent()));
			}
			if(model.getPosition()!=null){
				criter.and("position").is(model.getPosition());
			}
			if(model.getClientGroup()!=null && model.getClientGroup().length>0){
				criter.and("clientGroup").in((Object[])model.getClientGroup());
			}
			if(model.getRoomIds()!=null && model.getRoomIds().length>0){
				criter.and("roomIds").in((Object[])model.getRoomIds());
			}
			if(model.getPushType()!=null){
				criter.and("pushType").is(model.getPushType());
			}
			
		}
		return chatPushInfoDao.findPage(ChatPushInfo.class, Query.query(criter), dCriteria);
	}

}

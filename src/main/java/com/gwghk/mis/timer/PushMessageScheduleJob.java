package com.gwghk.mis.timer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import cn.jpush.api.push.PushResult;
import com.alibaba.fastjson.JSON;
import com.gwghk.mis.enums.JobType;
import com.gwghk.mis.model.PushMessage;
import com.gwghk.mis.service.JobService;
import com.gwghk.mis.service.PushMessageService;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.SpringContextUtil;

/**
 * 摘要：消息推送业务逻辑任务类
 * @author Gavin.guo
 * @date   2015年7月22日
 */
public class PushMessageScheduleJob extends ScheduleJob{
	
	private static Logger logger = Logger.getLogger(PushMessageScheduleJob.class);
	
	/**
	 * 数据ID
	 */
	private String dataId;
	
	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	@Override
	public void execute() {
		logger.info("execute->[JobGroup:"+this.getJobGroup()+",JobName:"+this.getJobName()+",JobDesc:"+this.getDesc()+",dataId="+getDataId()+"],Job start.");
		try{
			PushMessageService  pushMessageService = (PushMessageService)SpringContextUtil.getBean("pushMessageService");
			PushMessage pm = pushMessageService.getPushMessageById(getDataId().toString());
			if(pm != null && pm.getValid() == 1 && pm.getPushStatus() == 1){
				JPushObj jpush = new JPushObj();
				BeanUtils.copyExceptNull(jpush, pm);
				Map<String,String> buildExtraMap = new HashMap<String,String>();
				buildExtraMap.put("dataid", jpush.getArticleId());              //数据Id
				buildExtraMap.put("lang", jpush.getLang());			    		//语言
				buildExtraMap.put("tipType", jpush.getTipType());       		//显示方式  (1、系统通知中心 2、小秘书 3、首次登陆时弹窗)
				buildExtraMap.put("messageType", jpush.getMessageType());       //消息类型 (1:自定义 2：文章资讯 3：关注订阅 4：评论提醒  5:公告 6:反馈)
				buildExtraMap.put("url", jpush.getUrl());       				//外部链接(url有值,内容及栏目ID资讯Id无效，以URL值为准)
				buildExtraMap.put("content", jpush.getContent());       	    //内容(当消息类型为1时，url没有值使用的内容)
				buildExtraMap.put("categoryId", jpush.getFullCategoryId());     //栏目Id(当消息类型为2或5时，有值)
				buildExtraMap.put("articleId", jpush.getArticleId());   	    //资讯Id(当消息类型为2或5时，有值)
				PushResult result = JPushUtil.pushAndroidMessage(1,"蜘蛛投资", jpush.getTitle() , null , buildExtraMap);  //调用推送消息方法
				logger.debug("PushMessageJobTask.execute->result:"+result);
				if(result != null && result.sendno > 0){  //发送成功
					pushMessageService.updatePushMessageStatus(pm.getPushMessageId(), 2,result.msg_id);
					JobService  jobService = (JobService)SpringContextUtil.getBean("jobService");
					jobService.updateJob(pm.getPushMessageId(), JobType.Pushmessage.getValue(), 2);
					logger.info("execute->[JobGroup:"+this.getJobGroup()+",JobName:"+this.getJobName()+",JobDesc:"
								+this.getDesc()+",dataId="+getDataId()+"],Job end. send Result:"+JSON.toJSONString(result));
				}else{  //发送失败
					pushMessageService.updatePushMessageStatus(pm.getPushMessageId(), 3,null);
					JobService  jobService = (JobService)SpringContextUtil.getBean("jobService");
					jobService.updateJob(pm.getPushMessageId(), JobType.Pushmessage.getValue(), 3);
					logger.info("execute->[JobGroup:"+this.getJobGroup()+",JobName:"+this.getJobName()+",JobDesc:"
								+this.getDesc()+",dataId="+getDataId()+"],Job end. send fail!");
				}
			}else{
				logger.info("execute->[JobGroup:"+this.getJobGroup()+",JobName:"+this.getJobName()+",JobDesc:"
							 +this.getDesc()+",dataId="+getDataId()+"],[valid="+pm.getValid()+",pushStatus="+pm.getPushStatus()+"],Job end.");
			}
		}
		catch(Exception e){
			logger.error("execute->[JobGroup:"+this.getJobGroup()+",JobName:"+this.getJobName()+",JobDesc:"+this.getDesc()+",dataId="+dataId+"],Exception:",e);
		}
	}
	
	/**
	 * 功能：添加推送消息job
	 * @param id   		消息Id
	 * @param exeDate   消息执行时间
	 * @param title     消息标题
	 */
	public static void addPushMessageScheduleJob(String id,Date exeDate,String title){
		PushMessageScheduleJob pushMessageScheduleJob = new PushMessageScheduleJob();
		pushMessageScheduleJob.setJobName(id);
		pushMessageScheduleJob.setJobGroup(JobType.Pushmessage.getValue());
		pushMessageScheduleJob.setCronExpression(DateUtil.formatDate(exeDate,"ss mm HH dd MM ? yyyy"));
		pushMessageScheduleJob.setDesc(title);
		pushMessageScheduleJob.setDataId(id);
		QuartzJobManager.addOrModifyJob(pushMessageScheduleJob);
	}
}

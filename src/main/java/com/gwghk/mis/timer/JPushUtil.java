package com.gwghk.mis.timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.AndroidNotification.Builder;
import cn.jpush.api.push.model.notification.Notification;

import com.gwghk.mis.util.PropertiesUtil;

/**
 * 摘要：JPush 极光推送工具类
 * @author Gavin.guo
 * @date 2014-07-28
 */
public class JPushUtil {
	
	protected static final Logger LOG = Logger.getLogger(JPushUtil.class);

	private static final String appKey = PropertiesUtil.getInstance().getProperty("appKey");
	private static final String masterSecret = PropertiesUtil.getInstance().getProperty("masterSecret");

    /**
     * 功能：推送Android消息
     * @param type    消息类型(1:通知消息或2:自定义消息 3:两种消息都推送)
     * @param title   标题
     * @param conent  内容
     * @param aliasList 推送设备别名(以集合的方式)
     * @param buildExtraMap 额外需要发送的参数(除了标题和内容以后的参数)
     */
	public static PushResult pushAndroidMessage(Integer type,String title,String conent,List<String> aliasList,Map<String,String> buildExtraMap){
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
		PushPayload payload = buildAndroidSendPush(type,title,conent,aliasList,buildExtraMap);
		PushResult result = null;
		try {
        	result = jpushClient.sendPush(payload);
            LOG.info("sendAndroidPush->send success,got result :" + result);
            return result;
        }catch (APIConnectionException e) {
            LOG.error("sendAndroidPush->Connection error. Should retry later. ", e);
        }catch (APIRequestException e) {
            LOG.error("sendAndroidPush->[Error response from JPush server. Should review and fix it."
            		  + ",HTTP Status: " + e.getStatus() + ",Error Code: " + e.getErrorCode()
            		  + ",Error Message:" + e.getErrorMessage()+",Msg ID: " + e.getMsgId()+"]", e);
        }catch(Exception e){
        	LOG.error("sendAndroidPush->[title:"+title+",conent:"+conent+",buildExtraMap:"+buildExtraMap+"],error!");
        }
        return result;
	}
	
	/**
	 * 功能：构造Android发送推送消息
	 * @param type    消息类型(1:通知消息或2:自定义消息 3:两种消息都推送)
     * @param title   标题
     * @param conent  内容
     * @param aliasList 推送设备别名(以集合的方式)
     * @param buildExtraMap 额外需要发送的参数(除了标题和内容以后的参数)
	 */
	private static PushPayload buildAndroidSendPush(Integer type,String title,String content,List<String> aliasList,Map<String,String> buildExtraMap){
		PushPayload.Builder  builder = PushPayload.newBuilder().setPlatform(Platform.android())
									  			  .setAudience(aliasList != null && aliasList.size() > 0 ?
									  			  Audience.alias(aliasList) : Audience.all());
		if(type == 1){    //通知消息
			builder = builder.setNotification(getNotification(title,content,buildExtraMap));
		}else if(type == 2){  //自定义消息(例如小秘书那里的显示)
			builder = builder.setMessage(getMessage(title,content,buildExtraMap));
		}else if(type == 3){  //通知消息或自定义消息都推送
			builder = builder.setNotification(getNotification(title,content,buildExtraMap))
							 .setMessage(getMessage(title,content,buildExtraMap));
		}
		return builder.build();
	}
	
	private static Notification getNotification(String title,String content,Map<String,String> buildExtraMap){
		Builder androidNotifyBuilder = AndroidNotification.newBuilder();
		androidNotifyBuilder.setTitle(title);
		if(buildExtraMap != null && buildExtraMap.size() > 0){
			for(Map.Entry<String, String> entry : buildExtraMap.entrySet()){
				androidNotifyBuilder.addExtra(entry.getKey(), entry.getValue());
			}
		}
		Notification notification = Notification.newBuilder()
								  			    .setAlert(content)
								                .addPlatformNotification(androidNotifyBuilder.build())
									            .build();
		return notification;
	}
	
	private static Message getMessage(String title,String content,Map<String,String> buildExtraMap){
		Message message = Message.newBuilder()
				 .setTitle(title)
				 .setMsgContent(content)
				 .addExtras(buildExtraMap)
				 .build();
		return message;
	}
	
	public static void main(String[] args) {
		String title = "蜘蛛投资";
	    String alert = "反馈内容";
	    
	    Map<String,String> map = new HashMap<String,String>();
	    map.put("dataid", "");
	    map.put("lang", "zh");
	    map.put("tipType", "2");
	    map.put("messageType", "6");
	    
	    List<String> aliasList = new ArrayList<String>();
	    aliasList.add("55b1ed5658527aec14ec7877");
	    
	    PushResult result =pushAndroidMessage(2,title,alert,aliasList,map);
	    System.out.println(result != null && result.sendno > 0);
	    System.out.println(result.msg_id);
	}
}

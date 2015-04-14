package com.gwghk.ams.common.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.gwghk.ams.common.model.Client;

/**
 * 摘要：对在线用户的管理
 * @author Gavin.guo
 */
public class ClientManager {
	
	private ClientManager() {
	}
	
	private static class ClientManagerInstance {
		private static final ClientManager instance = new ClientManager();
	}
	public static ClientManager getInstance() { 
		return ClientManagerInstance.instance; 
	}
	
	private Map<String,Client> map = new HashMap<String, Client>();
	
	/**
	 * 功能：注册对应的在线用户
	 * @param sessionId  session Id
	 * @param client    在线用户
	 */
	public void addClinet(String sessionId,Client client){
		map.put(sessionId, client);
	}
	
	/**
	 * 功能：根据sessionId -->删除对应的在线用户
	 * @param sessionId  session Id
	 */
	public void removeClinet(String sessionId){
		map.remove(sessionId);
	}
	
	/**
	 * 功能：根据sessionId-->获取对应的在线用户
	 * @param sessionId  session Id
	 * @return 在线用户
	 */
	public Client getClient(String sessionId){
		return map.get(sessionId);
	}
	
	/**
	 * 功能：获取当前所有的在线用户
	 */
	public Collection<Client> getAllClient(){
		return map.values();
	}
	
	/**
	 * 功能：获取在线人数
	 */
	public String getOnlineCount(){
		return String.valueOf(getAllClient().size());
	}
}

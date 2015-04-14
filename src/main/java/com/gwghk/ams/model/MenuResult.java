package com.gwghk.ams.model;

import java.util.List;
import java.util.Map;

/**
 * 菜单输出类
 * @author Alan.wu
 *
 */
public class MenuResult {
	private String menuJson;
    private Map<String,List<BoMenu>> funMap;
	public String getMenuJson() {
		return menuJson;
	}
	public void setMenuJson(String menuJson) {
		this.menuJson = menuJson;
	}
	public Map<String, List<BoMenu>> getFunMap() {
		return funMap;
	}
	public void setFunMap(Map<String, List<BoMenu>> funMap) {
		this.funMap = funMap;
	}
}
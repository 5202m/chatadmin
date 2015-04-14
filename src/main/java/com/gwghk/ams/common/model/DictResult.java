/* System generated code from MenuPo */

package com.gwghk.ams.common.model;

import java.util.List;
import java.util.Map;

import com.gwghk.ams.model.BoDict;


/**
 * 字典输出类
 * @author Alan.wu
 *
 */
public class DictResult implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
    private Map<String,List<BoDict>> dictMap;
	public Map<String, List<BoDict>> getDictMap() {
		return dictMap;
	}
	public void setDictMap(Map<String, List<BoDict>> dictMap) {
		this.dictMap = dictMap;
	}
}
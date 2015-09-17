package com.gwghk.cms.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gwghk.cms.SpringJunitTest;
import com.gwghk.mis.util.HttpClientUtils;

public class UserDaoTest extends SpringJunitTest {

	@Test
	public void testFind(){
		
		   Map<String, String> paramMap=new HashMap<String, String>();
		   paramMap.put("appId", "hexun");
		   paramMap.put("appSecret", "F11E6E10F494DCFF80ED0A611D560EAD");
	         try {
				String str=HttpClientUtils.httpPostString("http://pmapi.24k.hk/api/token/getToken",paramMap);
				System.out.println("str:"+str);
				if(StringUtils.isNotBlank(str)){
					JSONObject obj=JSON.parseObject(str);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	
	
	
}

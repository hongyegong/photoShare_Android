package com.myou.appclient.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTest {

	public static List<Map<String, Object>> getHdpData(int count){
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < count; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pic", "http://kfc.img.xixik.net/c/bsk/e2581acc15f1803d.jpg");
			map.put("tag", "http://kfc.img.xixik.net/c/bsk/e2581acc15f1803d.jpg");
			data.add(map);
		}
		return data;
	}

	public static List<Map<String, Object>> getHdpData2(int count) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < count; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pic", "http://img4.anzhi.com/thumb/201305/21/cn.goapk.market_33965800_0.jpg");
			map.put("tag", "http://img4.anzhi.com/thumb/201305/21/cn.goapk.market_33965800_0.jpg");
			data.add(map);
		}
		return data;
	}

}

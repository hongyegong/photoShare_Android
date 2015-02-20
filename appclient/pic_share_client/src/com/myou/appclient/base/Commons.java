package com.myou.appclient.base;

import java.util.List;
import java.util.Map;

/**
 * Title: 智旅app<br>
 * Description:  客户端缓存<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
public class Commons {

    /** 工具类实例 */
    private static Commons commons;

    /** 用户pk */
	private String userPk;

    /** 名字 */
	private String userName;
	
	/** 版本号*/
	private String version;

	/** 搜索的值 */
	private String searchValue;
	
	/** 搜索的值 */
	private String city;
	
	private List<Map<String, Object>> hdp;
	
	/** 搜索的值 */
	private Map<String, Object> YouhuiMap;
	
	/** xy */
	private String xy;
	private String addr;
	private String x;
	private String y;

	/**
     * 获得工具类实例
     * 
     * @return
     */
    public static final Commons getInstance() {
        if (commons == null) {
            commons = new Commons();
        }
        return commons;
    }

	public String getXy() {
		return xy;
	}

	public void setXy(String xy) {
		this.xy = xy;
	}
	
	public String getUserPk() {
		return userPk;
	}

	public String getUserName() {
		return userName;
	}

	public String getVersion() {
		return version;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setUserPk(String userPk) {
		this.userPk = userPk;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Map<String, Object> getYouhuiMap() {
		return YouhuiMap;
	}

	public void setYouhuiMap(Map<String, Object> youhuiMap) {
		YouhuiMap = youhuiMap;
	}

	public String getX() {
		return x;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public List<Map<String, Object>> getHdp() {
		return hdp;
	}

	public void setHdp(List<Map<String, Object>> hdp) {
		this.hdp = hdp;
	}

	public String getY() {
		return y;
	}

	public void setX(String x) {
		this.x = x;
	}

	public void setY(String y) {
		this.y = y;
	}
	

}

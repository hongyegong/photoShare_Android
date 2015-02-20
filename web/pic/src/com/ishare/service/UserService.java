package com.ishare.service;

import java.util.List;
import java.util.Map;

public interface UserService {

	/**
	 * 用户登录
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public Map<String,Object> login(String userName, String passWord);
	
	/**
	 * 查询我的分享总记录数
	 * @param pkMember
	 * @return
	 */
	public int queryMinePicCount(String pkMember);
	
	/**
	 * 查询我的分享图片
	 * @param pkMember
	 * @return
	 */
	public List<Map<String,Object>> queryMinePic(String pkMember, int page, int number);
}

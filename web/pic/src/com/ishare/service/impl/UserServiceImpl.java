package com.ishare.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ishare.conn.JdbcUtils;
import com.ishare.service.UserService;

public class UserServiceImpl implements UserService{
	
	/** 单列 */
	private static UserService instance = null;
	
	public static UserService getInstance(){
		if(instance == null){
			instance = new UserServiceImpl();
		}
		return instance;
	}
	
	private UserServiceImpl(){
	}
	
	/**
	 * 用户登录
	 */
	public Map<String, Object> login(String userName, String passWord) {
		
		String sql =" SELECT PK_MEMBER,NAME,NICK_NAME,PASSWORD,LOGO_PATH,TS FROM TB_MEMBER WHERE NAME=? AND PASSWORD=? ";
		List<Map<String,Object>> resList = JdbcUtils.queryForList(sql, new Object[]{userName, passWord});
		if(resList != null && resList.size() > 0){
			return resList.get(0);
		}else {
			return null;
		}
	}
	
	/**
	 * 查询我的分享总记录数
	 * @param pkMember
	 * @return
	 */
	public int queryMinePicCount(String pkMember){
		
		String sql = " SELECT COUNT(*) FROM TB_PIC ";
		Object[] args = new Object[]{};
		if(pkMember != null){
			sql += "   WHERE PK_MEMBER=? ";
			args = new Object[]{pkMember};
		}
		return JdbcUtils.queryForInt(sql, args);
	}
	
	/**
	 * 查询我的分享图片
	 * @param pkMember
	 * @return
	 */
	public List<Map<String,Object>> queryMinePic(String pkMember, int page, int number){
		String sql = " SELECT PK_PIC,PK_MEMBER,NICK_NAME,PIC_PATH,PIC_NAME,PIC_MEMO,TS FROM TB_PIC ";
		List<Object> args = new ArrayList<Object>();
		if(pkMember != null){
			sql += "   WHERE PK_MEMBER=? ";
			args.add(pkMember);
		}
		sql += " limit ?,? ";
		args.add((page-1)*number);
		args.add(number);
		return JdbcUtils.queryForList(sql, args.toArray());
	}
}

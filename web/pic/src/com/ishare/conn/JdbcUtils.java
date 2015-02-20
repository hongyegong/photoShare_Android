package com.ishare.conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: codehp.com代码助手<br>
 * Description: 简单JDBC工具类<br>
 * Copyright: Copyright (c) 2011<br>
 * Company: codehp<br>
 * 
 * @author lyy
 * @version 1.0
 */
public class JdbcUtils {

    /**
     * 查询List
     * @return
     * @throws SQLException 
     */
    public static List<Map<String, Object>> queryForList(String sql, Object[] args) {

        Connection con = MyConnection.getConnection();
        PreparedStatement sqlStatement = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            sqlStatement = con.prepareStatement(sql);
            if (args != null){
                for (int i = 1 ;i<= args.length;i++){
                    Object obj = args[i-1];
                    sqlStatement.setObject(i, obj);
                }
            }
            ResultSet result = sqlStatement.executeQuery();
            ResultSetMetaData rsmd = result.getMetaData();
            while (result.next()) {
                HashMap<String, Object> value = new HashMap<String, Object>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String columnName = rsmd.getColumnName(i);
                    value.put(columnName, result.getObject(columnName));
                }
                list.add(value);
            }
            result.close();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (sqlStatement != null) {
                    sqlStatement.close();
                }
                if (con != null) {
                	con.close();
//                	MyConnection.returnConnection(con);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 查询Int
     * @return
     * @throws SQLException 
     */
    public static int queryForInt(String sql, Object[] args) {

        Connection con = MyConnection.getConnection();
        PreparedStatement sqlStatement = null;
        try {
            sqlStatement = con.prepareStatement(sql);
            if (args != null){
                for (int i = 1 ;i<= args.length;i++){
                    Object obj = args[i-1];
                    sqlStatement.setObject(i, obj);
                }
            }
            ResultSet result = sqlStatement.executeQuery();
            while (result.next()) {
                int flag = result.getInt(1);
                result.close();
                return flag;
            }
            result.close();
            throw new RuntimeException("查询结果集为0，要求结果集为1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (sqlStatement != null) {
                    sqlStatement.close();
                }
                if (con != null) {
                	con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询Int
     * @param <T>
     * @return
     * @throws SQLException 
     */
    public static Object queryForObject(String sql, Object[] args) {

    	Connection con = MyConnection.getConnection();
        PreparedStatement sqlStatement = null;
        try {
            sqlStatement = con.prepareStatement(sql);
            if (args != null){
                for (int i = 1 ;i<= args.length;i++){
                    Object obj = args[i-1];
                    sqlStatement.setObject(i, obj);
                }
            }
            ResultSet result = sqlStatement.executeQuery();
            while (result.next()) {
                Object obj = result.getObject(1);
                result.close();
                return obj;
            }
            result.close();
            throw new RuntimeException("查询结果集为0，要求结果集为1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (sqlStatement != null) {
                    sqlStatement.close();
                }
                if (con != null) {
                	con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 批量修改/新增
     */
    public static int[] updateBatch(String sql, List<Object[]> args){

    	Connection con = MyConnection.getConnection();
        PreparedStatement sqlStatement = null;
        try {
            sqlStatement = con.prepareStatement(sql);
            for  (int i =  0 ; i <  args.size() ; i++) {   
                Object[] arg = args.get(i);
                for (int j = 1; j<= arg.length; j++){
                    sqlStatement.setObject(j, arg[j-1]);
                }
                sqlStatement.addBatch();   
              }
            return sqlStatement.executeBatch();
        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            try {
                if (sqlStatement != null) {
                    sqlStatement.close();
                }
                if (con != null) {
                	con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行修改sql
     * @param sql
     * @param args
     */
    public static void update(String sql, Object[] args) {
    	Connection con = MyConnection.getConnection();
        PreparedStatement sqlStatement = null;
        try {
            sqlStatement = con.prepareStatement(sql);
            if (args != null){
                for (int j = 1; j <= args.length; j++) {
                    sqlStatement.setObject(j, args[j - 1]);
                }
            }
            sqlStatement.execute();
        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            try {
                if (sqlStatement != null) {
                    sqlStatement.close();
                }
                if (con != null) {
                	con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

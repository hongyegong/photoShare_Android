package com.ishare.conn;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

public class DBConnPool{
	private int inUsed = 0;
	private Vector<Connection> connections = new Vector();
	private String dbSource;
	private String driver;
	public static String url;
	public static String userName;
	public static String password;
	private int minConn;
	private int maxConn;
	private long timeOut;
	private static DBConnPool pool = null;

	public static DBConnPool getInstance()
	{
		if (pool == null)
			pool = new DBConnPool();

		return pool;
	}

	private DBConnPool() {
		readConfig();
	}

	private void readConfig()
	{
		InputStream ins = null;
		try {
			ins = DBConnPool.class.getResourceAsStream("/datasource.properties");
			Properties p = new Properties();
			p.load(ins);
			this.dbSource = p.getProperty("dbsource").trim();
			this.driver = p.getProperty("driver").trim();
			this.url = p.getProperty("url").trim();
			this.userName = p.getProperty("username").trim();
			this.password = p.getProperty("password").trim();
			this.maxConn = Integer.parseInt(p.getProperty("maxconn").trim());
			this.timeOut = Long.parseLong(p.getProperty("timeOut").trim());
		} catch (IOException e) {
			e.printStackTrace();
			try{
				ins.close();
			} catch (IOException e1) {
				e.printStackTrace();
			}
		}
		finally
		{
			try
			{
				ins.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

//	public Connection getConnection()
//	{
//		Connection con = null;
//		con = dbConnection();
//		if (con == null)
//			con = getConnection(this.timeOut);
//
//		if (con != null)
//			this.inUsed += 1;
//
//		return con;
//	}

	private Connection dbConnection() {
		Connection conn = null;
		label84: synchronized (this.connections) {
			if (this.connections.size() > 0) {
				conn = (Connection)this.connections.elementAt(0);
				this.connections.removeElement(conn);
				try {
					if (conn.isValid(10)) break label84;
					conn = dbConnection();
				}
				catch (SQLException e) {
					conn = dbConnection();
				}
			} else if (this.inUsed < this.maxConn) {
				conn = newConnection();
			}
		}
		return conn;
	}

	private synchronized Connection getConnection(long timeout)
	{
		Connection conn = null;
		long startTime = new Date().getTime();
		try
		{
			synchronized (this) {
				super.wait(200L); }
		} catch (InterruptedException e) {
			do {
				e.printStackTrace();

				long endTime = new Date().getTime();
				if (endTime - startTime >= timeout)
					return null;
			}
			while ((conn = dbConnection()) == null);
		}

		return conn;
	}

	public void returnConnection(Connection conn){
		synchronized (this.connections) {
			if (!(this.connections.contains(conn))) 
				return;

			this.connections.addElement(conn);
			this.inUsed -= 1;
		}
		synchronized (this) {
			super.notifyAll();
		}
	}

	public synchronized void release()
	{
		Connection conn = null;
		synchronized (this.connections) {
			try {
				for (int i = 0; i < this.connections.size(); ++i) {
					conn = (Connection)this.connections.elementAt(i);
					if ((conn != null) && (!(conn.isClosed())))
						conn.close();

					this.inUsed -= 1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.connections.removeAllElements();
		}
	}

	private Connection newConnection()
	{
		Connection con = null;
		try {
			Class.forName(this.driver);
			con = DriverManager.getConnection(this.url, this.userName, this.password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("sorry can't find db driver!");
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("sorry can't create Connection!");
		}
		return con;
	}
}

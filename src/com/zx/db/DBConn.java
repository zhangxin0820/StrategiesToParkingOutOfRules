package com.zx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
	
	private static final String URL = "jdbc:mysql://localhost:3306/STRATEGY?useUnicode=true&characterEncoding=utf8";
	private static final String NAME = "root";
	private static final String PASSWORD = "";
	public static Connection conn = null;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, NAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		// TODO Auto-generated method stub
		return conn;
	}

}

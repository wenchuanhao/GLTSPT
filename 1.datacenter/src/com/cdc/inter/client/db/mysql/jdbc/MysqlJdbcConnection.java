package com.cdc.inter.client.db.mysql.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.cdc.common.properties.SqlPropertiesConfig;

public class MysqlJdbcConnection {

	
    /**
     * 取得一个连接
     * @date 2016-6-5 下午4:52:55
     * @return	Connection
     */
    public static Connection getConnection(String type) {
    	String message = "恭喜，数据库连接正常！";
        try {
        	SqlPropertiesConfig sConfig = new SqlPropertiesConfig("jdbc/mysql.properties");
        	String driverClassName = sConfig.getProperty("jdbc.driverClassName");
        	String url = sConfig.getDesProperty(type + ".jdbc.url");
        	String userName = sConfig.getDesProperty(type + ".jdbc.username");
        	String password = sConfig.getDesProperty(type + ".jdbc.password");
            Class.forName(driverClassName);
            Connection con = DriverManager.getConnection(url, userName, password);
            return con;
        } catch (Exception e) {
        	message = "数据库连接失败！"; 
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 关闭连接
     * 
     * @date 2016-6-5 下午6:06:02
     * @param conn
     * @param stm
     * @param rs	void
     */
	public static void close( Connection conn , Statement stm , ResultSet rs ){
		if( conn != null )try{ conn.close();}catch( Exception e ){}
		if( stm != null )try{ stm.close() ;}catch( Exception e ){}
		if( rs != null )try{ rs.close(); }catch( Exception e ){}
	}
	
	/**
	 * @date 2016-6-5 下午4:50:34
	 * @param args	void
	 */
	public static void main(String[] args) {
		Connection  conn = null;
        try{  
        	String type = "zsnj.link";//掌上南基link
        	
            conn = MysqlJdbcConnection.getConnection(type);  
            
        }catch(Exception e){  
            e.printStackTrace();  
        }  finally{
        	close(conn, null, null);
        }
	}

}

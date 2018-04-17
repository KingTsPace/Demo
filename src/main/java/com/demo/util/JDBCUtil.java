package com.demo.util;

import com.demo.common.Developer;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;



/**
 * @author zWX454479 JDBC工具类
 */
public class JDBCUtil
{

    private static JDBCUtil jdbcUtil = null;
    
    // 表示定义数据库的用户名  
    private static String userName;  
  
    // 定义数据库的密码  
    private static String password;  
  
    // 定义数据库的驱动信息  
    private static String driver;  
  
    // 定义访问数据库的地址  
    private static String url;  
  
    // 定义数据库的链接  
    private Connection connection;  
    
    // 定义sql语句的执行对象  
    private PreparedStatement pstmt;  
  
    // 定义查询返回的结果集合  
    private ResultSet resultSet; 
    
    /**
     * 数据池
     */
    private DataSource dataSource;

    /**
     * 单例
     */
    private static synchronized void syncInit()
    {
        if (jdbcUtil == null)
        {
            jdbcUtil = new JDBCUtil();
        }
    }

    /**
     * @return
     */
    public static JDBCUtil getInstance()
    {
        if (jdbcUtil == null)
        {
            syncInit();
        }
        return jdbcUtil;
    }
    
    public JDBCUtil()
    {
        loadConfig();
    }
    
    public void initPool(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }
    
    /**
     * 配置文件
     */
    public void loadConfig() {  
        FileInputStream fileInputStream = null;
        try {  
            URL resource = this.getClass().getClassLoader().getResource("resources/jdbc.properties");
            File file = new File(resource.toURI());
            fileInputStream = new FileInputStream(file);
            Properties prop = new Properties();  
            prop.load(fileInputStream);  
            userName = prop.getProperty("username");  
            password = prop.getProperty("password");  
            driver= prop.getProperty("driver");  
            url = prop.getProperty("url");  
            fileInputStream.close();
        } catch (Exception e) {  
            IOHelperUtil.close(fileInputStream);
            throw new RuntimeException("读取数据库配置文件异常！", e);  
        }  
        finally
        {
            IOHelperUtil.close(fileInputStream);
        }
    }  

    /**
      * 获取jdbc连接
     * @return
     * @throws ClassNotFoundException
     * @throws URISyntaxException
     * @throws IOException
     * @throws SQLException 
     */
    public Connection getConnection() {  
        try {  
            DESTool desUtil = new DESTool("QWEqwe!@~");
            password = desUtil.decryptStr(password);
            Class.forName(driver); // 注册驱动  
            connection = DriverManager.getConnection(url, userName, password); // 获取连接  
        } catch (Exception e) {  
            throw new RuntimeException("get connection error!", e);  
        }  
        return connection;  
    }  
    
    /**
     * 获取jdbc连接
    * @return
    * @throws ClassNotFoundException
    * @throws URISyntaxException
    * @throws IOException
    * @throws SQLException 
    */
   public Connection getPoolConnection() {  
       try {  
           connection = dataSource.getConnection();
       } catch (Exception e) {  
           throw new RuntimeException("get connection error!", e);  
       }  
       return connection;  
   }  
    
    
    /**
     * 关闭连接，释放资源
     */
    public void releaseConn()
    {
        if (resultSet != null) {  
            try {  
                resultSet.close();  
            } catch (SQLException e) {  
                Developer.error("releaseConn error", e);
                throw new RuntimeException("releaseConn error!", e);  
            }  
        }  
        if (pstmt != null) {  
            try {  
                pstmt.close();  
            } catch (SQLException e) {  
                Developer.error("releaseConn error", e);
                throw new RuntimeException("releaseConn error!", e);  
            }  
        }  
        if (connection != null) {  
            try {  
                connection.close();  
            } catch (SQLException e) {  
                Developer.error("releaseConn error", e);
                throw new RuntimeException("releaseConn error!", e);  
            }  
        }
    }
    
    /**
     * 回滚
     */
    public void rollback()
    {
        if(connection != null)
        {
            try
            {
                connection.rollback();
            }
            catch (SQLException e)
            {
                Developer.error("rollBack error", e);
                throw new RuntimeException("rollBack error!", e);
            }
        }
    }

}

package io.github.michstabe.securitymonitor.utils;

import io.github.michstabe.securitymonitor.SecurityMonitor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.sql.*;
import java.util.logging.Level;

/**
 * @projectName: SecurityMonitor
 * @package: io.github.michstabe.securitymonitor.utils
 * @className: DBUtils
 * @author: Zydot
 * @description: TODO
 * @date: 2023/12/2 19:22
 */
public class DBUtils {

    private static final JavaPlugin plugin = SecurityMonitor.INSTANCE;
    private static final FileConfiguration config = plugin.getConfig();

    private static final String host = config.getString("mysql.host");
    private static final String port = config.getString("mysql.port");
    private static final String username = config.getString("mysql.username");
    private static final String password = config.getString("mysql.password");
    private static final String database = config.getString("mysql.database");

    private static final String url = "jdbc:mysql://"+ host + ":" + port + "/" + database +
            "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public static boolean loadDriver() {
        if (port == null || host == null || username == null || database == null || password == null) {
            plugin.getLogger().warning("数据库配置不完全。");
            return true;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            plugin.getLogger().log(Level.INFO, "数据库驱动加载成功");
            return false;

        } catch (ClassNotFoundException e) {
            plugin.getLogger().log(Level.WARNING, "数据库驱动加载失败。");
            e.printStackTrace();
            return true;
        }
    }

    public static Connection getConn(){
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }


    public static void close(@Nullable Connection conn, @Nullable PreparedStatement statement, @Nullable ResultSet rs){
        try {
            if (conn != null) conn.close();
            if (statement != null) statement.close();
            if (rs != null) rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(@Nullable Connection conn, @Nullable PreparedStatement statement){
        try {
            if (conn != null) conn.close();
            if (statement != null) statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void close(@Nullable PreparedStatement statement){
        close(null,statement);
    }

    public static void close(@Nullable Connection conn){
        close(conn,null);
    }
}

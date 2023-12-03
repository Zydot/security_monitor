package io.github.michstabe.securitymonitor.data;

import io.github.michstabe.securitymonitor.SecurityMonitor;
import io.github.michstabe.securitymonitor.utils.DBUtils;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @projectName: SecurityMonitor
 * @package: io.github.michstabe.securitymonitor.data
 * @className: DBDataManager
 * @author: Zydot
 * @description: TODO
 * @date: 2023/12/2 19:46
 */
public class DBDataManager implements IDataManager{
    @Override
    public void loadAll() {
        DBUtils.loadDriver();

        Connection conn = DBUtils.getConn();
        String SQL = "CREATE TABLE IF NOT EXISTS `monitor_player_data`  (" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                "  `player_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL," +
                "  `player_uuid` varbinary(64) NULL DEFAULT NULL," +
                "  `hostname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL," +
                "  `ip_address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL," +
                "  `type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL," +
                "  `time` datetime NULL DEFAULT NULL," +
                "  `is_deleted` tinyint(4) NULL DEFAULT 0," +
                "  PRIMARY KEY (`id`) USING BTREE" +
                ") ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;";
        try {
            PreparedStatement pstm = conn.prepareStatement(SQL);
            pstm.execute();
            SecurityMonitor.INSTANCE.getLogger().info("§a§lSecurityMonitor成功与数据库建立连接！");
            DBUtils.close(pstm);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBUtils.close(conn);
        }
    }

    @Override
    public void insert(Player player, String hostname, String ip, String type){
        CompletableFuture.runAsync(() ->{
            Connection conn = DBUtils.getConn();
            String SQL = "INSERT INTO `monitor_player_data` (player_name,player_uuid,hostname, ip_address,type,time) VALUES (?,?,?,?,?,?)";
            try {
                PreparedStatement pstm = conn.prepareStatement(SQL);
                pstm.setString(1, player.getDisplayName());
                pstm.setString(2, player.getUniqueId().toString());
                pstm.setString(3, hostname);
                pstm.setString(4, ip);
                pstm.setString(5, type);
                pstm.setString(6, getNow());
                pstm.executeUpdate();
                DBUtils.close(pstm);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                DBUtils.close(conn);
            }
        });
    }

    public String getNow(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    }
}

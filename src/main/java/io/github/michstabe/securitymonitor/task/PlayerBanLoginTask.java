package io.github.michstabe.securitymonitor.task;

import io.github.michstabe.securitymonitor.SecurityMonitor;
import io.github.michstabe.securitymonitor.config.ConfigLoader;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

/**
 * @projectName: SecurityMonitor
 * @package: io.github.michstabe.securitymonitor.task
 * @className: LoginAttempTask
 * @author: Zydot
 * @description: TODO
 * @date: 2023/12/1 21:16
 */
public class PlayerBanLoginTask {

    // 异步开启登录封禁解除
    public static void run(UUID uuid, Map<UUID, Integer> countMap) {
        new BukkitRunnable() {
            @Override
            public void run() {
                countMap.remove(uuid);
            }
        }.runTaskLaterAsynchronously(SecurityMonitor.INSTANCE, 20L * ConfigLoader.getConfigYaml().getConfig().getLong("settings.login.loginTime"));
        ;
    }
}

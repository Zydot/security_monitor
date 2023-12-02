package io.github.michstabe.securitymonitor.task;

import io.github.michstabe.securitymonitor.SecurityMonitor;
import io.github.michstabe.securitymonitor.config.ConfigLoader;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

/**
 * @projectName: SecurityMonitor
 * @package: io.github.michstabe.securitymonitor.task
 * @className: PlayerBanChatTask
 * @author: Zydot
 * @description: TODO
 * @date: 2023/12/2 0:06
 */
public class PlayerBanChatTask {

    // 异步开启玩家禁言解除
    public static void run(UUID uuid, Map<UUID, Integer> countMap) {
        new BukkitRunnable() {
            @Override
            public void run() {
                countMap.remove(uuid);
            }
        }.runTaskLaterAsynchronously(SecurityMonitor.INSTANCE, 20 * ConfigLoader.getConfigYaml().getConfig().getLong("settings.chat.chatTime"));
    }
}

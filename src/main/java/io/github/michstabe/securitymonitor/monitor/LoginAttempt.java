package io.github.michstabe.securitymonitor.monitor;

import io.github.michstabe.securitymonitor.SecurityMonitor;
import io.github.michstabe.securitymonitor.config.ConfigLoader;
import io.github.michstabe.securitymonitor.data.DBDataManager;
import io.github.michstabe.securitymonitor.data.IDataManager;
import io.github.michstabe.securitymonitor.task.TaskManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LoginAttempt implements Listener {
    private final Map<UUID, Integer> countMap = new ConcurrentHashMap<>();

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        int count = countMap.getOrDefault(uuid, 0);
        int num = ConfigLoader.getConfigYaml().getConfig().getInt("settings.login.loginNum");
        String kickMsg = ConfigLoader.getConfigYaml().getConfig().getString("settings.login.kickMsg").replace("&", "§");
        if (count >= num) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, kickMsg);
            if (countMap.get(uuid) == num) {
                countMap.put(uuid, count + 1);
                TaskManager.enablePlayerBanLoginTask(uuid, countMap);
                IDataManager data = new DBDataManager();
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        data.insert(event.getPlayer(), event.getHostname(), event.getRealAddress().toString(),"限制登录");
                    }
                }.runTaskAsynchronously(SecurityMonitor.INSTANCE);
            }
        } else {
            countMap.put(uuid, count + 1);
        }
    }
}

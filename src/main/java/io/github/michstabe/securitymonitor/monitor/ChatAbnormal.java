package io.github.michstabe.securitymonitor.monitor;

import io.github.michstabe.securitymonitor.SecurityMonitor;
import io.github.michstabe.securitymonitor.config.ConfigLoader;
import io.github.michstabe.securitymonitor.data.DBDataManager;
import io.github.michstabe.securitymonitor.data.IDataManager;
import io.github.michstabe.securitymonitor.task.TaskManager;
import io.github.michstabe.securitymonitor.utils.MessageUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @projectName: SecurityMonitor
 * @package: io.github.michstabe.securitymonitor.monitor
 * @className: ChatAbnormal
 * @author: Zydot
 * @description: TODO
 * @date: 2023/12/1 22:32
 */
public class ChatAbnormal implements Listener {

    private final Map<UUID, Integer> countMap = new ConcurrentHashMap<>();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String msg = event.getMessage();
        UUID uuid = event.getPlayer().getUniqueId();
        int count = countMap.getOrDefault(uuid, 0);
        int num = ConfigLoader.getConfigYaml().getConfig().getInt("settings.chat.chatNum");
        String chatMessage = ConfigLoader.getConfigYaml().getConfig().getString("settings.chat.chatMessage");
        String chatBanMessage = ConfigLoader.getConfigYaml().getConfig().getString("settings.chat.chatBanMessage");
        boolean flag = containsWordSet(msg);
        if (flag) {
            event.setCancelled(true);
            if (count >= num) {
                MessageUtils.sendMessage(event.getPlayer(), chatBanMessage);
                if (countMap.get(uuid) == num) {
                    countMap.put(uuid, count + 1);
                    TaskManager.enablePlayerBanChatTask(uuid, countMap);
                    IDataManager data = new DBDataManager();
                    data.insert(event.getPlayer(), "", "", "禁言");
                }
                return;
            } else {
                MessageUtils.sendMessage(event.getPlayer(), chatMessage);
                countMap.put(uuid, count + 1);
            }
        }
        if (count >= num) {
            event.setCancelled(true);
            MessageUtils.sendMessage(event.getPlayer(), chatBanMessage);
            if (countMap.get(uuid) == num) {
                TaskManager.enablePlayerBanChatTask(uuid, countMap);
            }
        }
    }

    private boolean containsWordSet(String msg) {
        for (String word : ConfigLoader.getBlackListWord()) {
            if (msg.toLowerCase().contains(word)) {
                return true;
            }
        }
        return false;
    }
}

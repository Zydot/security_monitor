package io.github.michstabe.securitymonitor.utils;

import io.github.michstabe.securitymonitor.config.ConfigLoader;
import org.bukkit.entity.Player;

/**
 * @projectName: SecurityMonitor
 * @package: io.github.michstabe.securitymonitor.utils
 * @className: MessageUtils
 * @author: Zydot
 * @description: TODO
 * @date: 2023/12/2 15:39
 */
public class MessageUtils {


    public static void sendMessage(Player player, String... info) {
        String prefix = ConfigLoader.getConfigYaml().getConfig().getString("settings.prefix");
        for (String msg : info) {
            player.sendMessage(prefix.replace("&", "§") + " " + msg.replace("&", "§"));
        }
    }

}

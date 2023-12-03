package io.github.michstabe.securitymonitor.data;

import org.bukkit.entity.Player;

public interface IDataManager {
    void loadAll();

    void insert(Player player, String hostname, String ip, String type);

}

package io.github.michstabe.securitymonitor;

import io.github.michstabe.securitymonitor.command.CommandHandler;
import io.github.michstabe.securitymonitor.config.ConfigLoader;
import io.github.michstabe.securitymonitor.data.DBDataManager;
import io.github.michstabe.securitymonitor.data.IDataManager;
import io.github.michstabe.securitymonitor.inform.InformMonitor;
import io.github.michstabe.securitymonitor.monitor.ChatAbnormal;
import io.github.michstabe.securitymonitor.monitor.LoginAttempt;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SecurityMonitor extends JavaPlugin {

    public static JavaPlugin INSTANCE;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("SecurityMonitor has been enable!!");
        INSTANCE = this;
        ConfigLoader.initConfigLoader(this);
        IDataManager data = new DBDataManager();
        data.loadAll();
        Bukkit.getPluginCommand("sm").setExecutor(new CommandHandler());
        getServer().getPluginManager().registerEvents(new LoginAttempt(), this);
        getServer().getPluginManager().registerEvents(new ChatAbnormal(), this);
        getServer().getPluginManager().registerEvents(new InformMonitor(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

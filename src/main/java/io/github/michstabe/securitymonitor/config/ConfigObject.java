package io.github.michstabe.securitymonitor.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * @projectName: SecurityMonitor
 * @package: io.github.michstabe.securitymonitor.config
 * @className: YamlObject
 * @author: Zydot
 * @description: TODO
 * @date: 2023/12/2 13:04
 */
public class ConfigObject {

    private final File file;
    private FileConfiguration fileConfiguration;

    public ConfigObject(String config, JavaPlugin plugin) {
        this.file = new File(plugin.getDataFolder(), config);
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        if (!file.exists()){
            plugin.saveResource(config, false);
            this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        }
    }

    public FileConfiguration getConfig(){
        return this.fileConfiguration;
    }

    public void reloadConfig(){
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        try {
            this.fileConfiguration.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}

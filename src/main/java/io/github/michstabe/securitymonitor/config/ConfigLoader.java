package io.github.michstabe.securitymonitor.config;

import io.github.michstabe.securitymonitor.SecurityMonitor;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @projectName: SecurityMonitor
 * @package: io.github.michstabe.securitymonitor.config
 * @className: ConfigLoader
 * @author: Zydot
 * @description: 配置文件加载
 * @date: 2023/12/2 12:41
 */
public class ConfigLoader {
    private static ConfigObject configYaml;
    private static ConfigObject zhCnYaml;
    private static ConfigObject enUsYaml;

    private static final CopyOnWriteArrayList<String> blackListWord = new CopyOnWriteArrayList<>();


    public static void initConfigLoader(SecurityMonitor securityMonitor) {
        configYaml = new ConfigObject("config.yml", securityMonitor);
        zhCnYaml = new ConfigObject("lang/zh_CN.yml", securityMonitor);
        enUsYaml = new ConfigObject("lang/en_US.yml", securityMonitor);
        reload();
    }

    public static void reload() {
        configYaml.reloadConfig();
        zhCnYaml.reloadConfig();
        enUsYaml.reloadConfig();
        blackListWord.addAll(configYaml.getConfig().getStringList("settings.chat.blackListWord"));
    }

    public static ConfigObject getConfigYaml() {
        return configYaml;
    }

    public static ConfigObject getZhCnYaml() {
        return zhCnYaml;
    }

    public static ConfigObject getEnUsYaml() {
        return enUsYaml;
    }

    public static CopyOnWriteArrayList<String> getBlackListWord() {
        return blackListWord;
    }
}

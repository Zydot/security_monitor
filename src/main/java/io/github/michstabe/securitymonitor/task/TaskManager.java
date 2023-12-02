package io.github.michstabe.securitymonitor.task;

import java.util.Map;
import java.util.UUID;

/**
 * @projectName: SecurityMonitor
 * @package: io.github.michstabe.securitymonitor.task
 * @className: TaskManager
 * @author: Zydot
 * @description: TODO
 * @date: 2023/12/1 21:44
 */
public class TaskManager {

    // 开启异步解除禁止玩家登录计划任务
    public static void enablePlayerBanLoginTask(UUID uuid, Map<UUID,Integer> countMap){
        PlayerBanLoginTask.run(uuid, countMap);
    }

    // 开启异步解除玩家禁言计划任务
    public static void enablePlayerBanChatTask(UUID uuid, Map<UUID, Integer> countMap){
        PlayerBanChatTask.run(uuid, countMap);
    }

}

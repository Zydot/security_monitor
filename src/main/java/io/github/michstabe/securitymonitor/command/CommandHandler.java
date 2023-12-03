package io.github.michstabe.securitymonitor.command;

import io.github.michstabe.securitymonitor.SecurityMonitor;
import io.github.michstabe.securitymonitor.inform.InformBook;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;

/**
 * @projectName: SecurityMonitor
 * @package: io.github.michstabe.securitymonitor.command
 * @className: CommandHandler
 * @author: Zydot
 * @description: TODO
 * @date: 2023/12/3 18:45
 */
public class CommandHandler implements TabExecutor {

    /*
     * runTask - 同步
     * runTaskAsynchronously - 异步
     *
     * runTaskLater - 同步延迟运行
     * runTaskLaterAsynchronously - 异步延迟运行
     *
     * runTaskTimer - 同步重复运行
     * runTaskTimerAsynchronously - 异步重复运行
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("该命令必须玩家执行");
            return true;
        }

        Bukkit.getScheduler().runTaskAsynchronously(SecurityMonitor.INSTANCE, () ->{
            Player player = (Player) sender;
            if (args.length == 0 || args[0].equalsIgnoreCase("help")){
                System.out.println("Help待完善");
            }
            switch (args[0].toLowerCase(Locale.ROOT)){
                case "inform":
                    new InformBook().giveInformBook(player);
                    break;
                case "other":
                    break;
            }
        });



        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}

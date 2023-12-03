package io.github.michstabe.securitymonitor.inform;

import io.github.michstabe.securitymonitor.mail.MailSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Optional;

/**
 * @projectName: SecurityMonitor
 * @package: io.github.michstabe.securitymonitor.inform
 * @className: InformMonitor
 * @author: Zydot
 * @description: TODO
 * @date: 2023/12/3 21:17
 */
public class InformMonitor implements Listener {

    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent event){
        BookMeta previousMeta = event.getPreviousBookMeta();
        Optional<String> bookName = Optional.ofNullable(previousMeta.getDisplayName());
        String bookNameOrDefault = bookName.orElse("default");
        if (!bookNameOrDefault.equalsIgnoreCase("§a举报书")){
            return;
        }
        MailSender mailSender = new MailSender();
        BookMeta newMeta = event.getNewBookMeta();
        String content = newMeta.getPage(1);
        event.getPlayer().getInventory().clear(event.getSlot());
        mailSender.mailSender(content);
    }
}

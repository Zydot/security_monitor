package io.github.michstabe.securitymonitor.inform;

import io.github.michstabe.securitymonitor.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @projectName: SecurityMonitor
 * @package: io.github.michstabe.securitymonitor.inform
 * @className: InformBook
 * @author: Zydot
 * @description: TODO
 * @date: 2023/12/3 18:34
 */
public class InformBook {

    private ItemStack buildBook(){
        ItemStack book = new ItemStack(Material.BOOK_AND_QUILL);
        ItemMeta meta = book.getItemMeta();
        meta.setDisplayName("§a举报书");
        meta.addEnchant(Enchantment.LUCK,1, false);
        book.setItemMeta(meta);
        return book;
    }

    public void giveInformBook(Player player){
        int emptySlot = player.getInventory().firstEmpty();
        if (emptySlot == -1) {
            MessageUtils.sendMessage(player, "&c您的背包没有足够的空间这样做");
            return;
        }
        player.getInventory().addItem(buildBook());
    }
}

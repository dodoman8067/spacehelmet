package kro.dodoworld.spacehelmet.command;

import kro.dodoworld.spacehelmet.data.SpaceHelmetEdition;
import kro.dodoworld.spacehelmet.item.ItemManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SpaceHelmetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)){
            sender.sendMessage("You have to use this command in game!");
            return true;
        }
        if(!sender.isOp() && !sender.hasPermission("spacehelmet.give")){
            sender.sendMessage(Component.text("You don't have permission to do this!", NamedTextColor.RED));
            return true;
        }
        if(args.length < 1){
            giveHelmet(sender, player);
            return true;
        }
        if(Bukkit.getPlayer(args[0]) == null){
            sender.sendMessage(Component.text("No player found.", NamedTextColor.RED));
            return true;
        }
        if(!Bukkit.getPlayer(args[0]).isOnline()){
            sender.sendMessage(Component.text("No player found.", NamedTextColor.RED));
            return true;
        }
        giveHelmet(sender, Bukkit.getPlayer(args[0]));
        return true;
    }

    private void giveHelmet(CommandSender sender, Player getter){
        int edition = SpaceHelmetEdition.getSpaceHelmetConfig().getInt("edition");
        sender.sendMessage(Component.text("You", NamedTextColor.RED).append(Component.text(" gave ", NamedTextColor.YELLOW).append(Component.text(getter.getName(), NamedTextColor.RED).append(Component.text(" a ", NamedTextColor.YELLOW).append(Component.text("Space Helmet", NamedTextColor.RED).append(Component.text(" (", NamedTextColor.GRAY).append(Component.text("#" + edition, NamedTextColor.YELLOW).append(Component.text(")!", NamedTextColor.GRAY)))))))));
        getter.sendMessage(Component.text(sender.getName(), NamedTextColor.RED).append(Component.text(" gave you a ", NamedTextColor.YELLOW).append(Component.text("Space Helmet", NamedTextColor.RED).append(Component.text(" (", NamedTextColor.GRAY).append(Component.text("#" + edition, NamedTextColor.YELLOW).append(Component.text(")!", NamedTextColor.GRAY)))))));
        ItemStack helmet = ItemManager.getSpaceHelmet(Component.text(sender.getName(), NamedTextColor.AQUA), Component.text(getter.getName(), NamedTextColor.AQUA), edition);
        if(getter.getInventory().firstEmpty() == -1){
            Item item = getter.getWorld().dropItemNaturally(getter.getLocation(), helmet);
            item.setCanMobPickup(false);
            item.setGlowing(true);
            item.setWillAge(false);
            item.setUnlimitedLifetime(true);
            item.setOwner(getter.getUniqueId());
            item.setInvulnerable(true);
        }else{
            getter.getInventory().addItem(helmet);
        }
        SpaceHelmetEdition.getSpaceHelmetConfig().set("edition", edition + 1);
        SpaceHelmetEdition.saveConfig();
        SpaceHelmetEdition.reloadConfig();
    }
}

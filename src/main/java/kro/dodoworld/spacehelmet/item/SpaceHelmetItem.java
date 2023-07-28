package kro.dodoworld.spacehelmet.item;

import kro.dodoworld.spacehelmet.HypixelSpaceHelmet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SpaceHelmetItem {
    public static void run(Plugin plugin){
        new BukkitRunnable(){

            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    handleHead(player, plugin);
                }
            }
        }.runTaskTimer(plugin, 0L, 5L);
        new BukkitRunnable(){

            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    handleGlass(player, plugin);
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    private static Material nextColor(Material material){
        Material returnValue;
        switch (material){
            case RED_STAINED_GLASS -> returnValue = Material.ORANGE_STAINED_GLASS;
            case ORANGE_STAINED_GLASS -> returnValue = Material.YELLOW_STAINED_GLASS;
            case YELLOW_STAINED_GLASS -> returnValue = Material.LIME_STAINED_GLASS;
            case LIME_STAINED_GLASS -> returnValue = Material.GREEN_STAINED_GLASS;
            case GREEN_STAINED_GLASS -> returnValue = Material.CYAN_STAINED_GLASS;
            case CYAN_STAINED_GLASS -> returnValue = Material.LIGHT_BLUE_STAINED_GLASS;
            case LIGHT_BLUE_STAINED_GLASS -> returnValue = Material.BLUE_STAINED_GLASS;
            case BLUE_STAINED_GLASS -> returnValue = Material.PURPLE_STAINED_GLASS;
            case PURPLE_STAINED_GLASS -> returnValue = Material.PINK_STAINED_GLASS;
            case PINK_STAINED_GLASS -> returnValue = Material.RED_STAINED_GLASS;
            default -> throw new IllegalArgumentException("Material has to be stained glass");
        }
        return returnValue;
    }

    private static void handleHead(Player player, Plugin plugin){
        if(player.getInventory().getHelmet() == null) return;
        if(!isHelmet(player.getInventory().getHelmet())) return;
        player.getEquipment().getHelmet().setType(nextColor(player.getEquipment().getHelmet().getType()));
    }

    private static void handleGlass(Player player, Plugin plugin){
        for(ItemStack s : player.getInventory().getStorageContents()) {
            if(!isHelmet(s)) continue;
            s.setType(Material.RED_STAINED_GLASS);
        }
        if(isHelmet(player.getInventory().getItemInOffHand())){
            player.getInventory().getItemInOffHand().setType(Material.RED_STAINED_GLASS);
        }
    }

    public static boolean isHelmet(ItemStack s){
        if(s == null) return false;
        if(s.getItemMeta() == null) return false;
        return s.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(HypixelSpaceHelmet.getPlugin(HypixelSpaceHelmet.class), "dodoman_spacehelmet_item"), PersistentDataType.INTEGER);
    }
}

package kro.dodoworld.spacehelmet.listener;

import kro.dodoworld.spacehelmet.HypixelSpaceHelmet;
import kro.dodoworld.spacehelmet.item.SpaceHelmetItem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareGrindstoneEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class SpaceHelmetHandler implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getClickedInventory() == null) return;
        if(event.getCursor() == null) return;
        if(!event.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;
        if(!event.getSlotType().equals(InventoryType.SlotType.ARMOR)) return;
        if(!SpaceHelmetItem.isHelmet(event.getCursor())) return;
        if(event.getSlot() == 39){
            Player clicker = (Player) event.getWhoClicked();
            if(clicker.getInventory().getHelmet() != null && clicker.getInventory().getHelmet().getItemMeta().hasEnchant(Enchantment.BINDING_CURSE)) return;
            ItemStack stack = event.getCursor();
            clicker.setItemOnCursor(null);
            Bukkit.getScheduler().scheduleSyncDelayedTask(HypixelSpaceHelmet.getPlugin(HypixelSpaceHelmet.class), () -> clicker.getInventory().setHelmet(stack), 1L);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(event.getHand() == null) return;
        if(event.getHand().equals(EquipmentSlot.HAND)) {
            if(!SpaceHelmetItem.isHelmet(event.getItem())) return;
            if(event.getAction().isRightClick()){
                event.setCancelled(true);
                Player player = event.getPlayer();
                ItemStack stack = event.getItem();
                if(player.getInventory().getHelmet() != null){
                    if(!player.getGameMode().equals(GameMode.CREATIVE) && player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getItemMeta().hasEnchant(Enchantment.BINDING_CURSE)) return;
                    player.getInventory().setItemInMainHand(player.getInventory().getHelmet());
                    player.getInventory().setHelmet(stack);
                }else{
                    player.getInventory().setHelmet(stack);
                    player.getInventory().setItemInMainHand(null);
                }
            }
            return;
        }
        if(event.getHand().equals(EquipmentSlot.OFF_HAND)){
            if(!SpaceHelmetItem.isHelmet(event.getItem())) return;
            if(event.getAction().isRightClick()){
                event.setCancelled(true);
                Player player = event.getPlayer();
                ItemStack stack = event.getItem();
                if(player.getInventory().getHelmet() != null){
                    if(!player.getGameMode().equals(GameMode.CREATIVE) && player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getItemMeta().hasEnchant(Enchantment.BINDING_CURSE)) return;
                    player.getInventory().setItemInOffHand(player.getInventory().getHelmet());
                    player.getInventory().setHelmet(stack);
                }else{
                    player.getInventory().setHelmet(stack);
                    player.getInventory().setItemInOffHand(null);
                }
            }
        }
    }

    @EventHandler
    public void onCure(EntityTransformEvent event){
        if(!event.getTransformReason().equals(EntityTransformEvent.TransformReason.CURED)) return;
        if(!(event.getEntity() instanceof ZombieVillager zombieVillager)) return;
        if(!(event.getTransformedEntity() instanceof Villager)) return;
        if(SpaceHelmetItem.isHelmet(zombieVillager.getEquipment().getItemInMainHand())){
            Item item = zombieVillager.getWorld().dropItemNaturally(zombieVillager.getLocation(), zombieVillager.getEquipment().getItemInMainHand());
            item.setCanMobPickup(false);
            item.setGlowing(true);
            item.setWillAge(false);
            item.setUnlimitedLifetime(true);
            item.setInvulnerable(true);
            zombieVillager.getEquipment().setItemInMainHand(null);
        }
        if(SpaceHelmetItem.isHelmet(zombieVillager.getEquipment().getItemInOffHand())){
            Item item = zombieVillager.getWorld().dropItemNaturally(zombieVillager.getLocation(), zombieVillager.getEquipment().getItemInOffHand());
            item.setInvulnerable(true);
            item.setGlowing(true);
            item.setWillAge(false);
            item.setUnlimitedLifetime(true);
            item.setCanMobPickup(false);
            zombieVillager.getEquipment().setItemInOffHand(null);
        }
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event){
        for(ItemStack i : event.getInventory().getStorageContents()){
            if(i == null) continue;
            if(SpaceHelmetItem.isHelmet(i)){
                event.getInventory().setResult(null);
            }
        }
    }

    @EventHandler
    public void onCraft(PrepareGrindstoneEvent event){
        if(SpaceHelmetItem.isHelmet(event.getInventory().getUpperItem()) || SpaceHelmetItem.isHelmet(event.getInventory().getLowerItem())){
            event.setResult(null);
        }
    }
}

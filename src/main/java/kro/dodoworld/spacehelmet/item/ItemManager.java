package kro.dodoworld.spacehelmet.item;

import kro.dodoworld.spacehelmet.HypixelSpaceHelmet;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ItemManager {
    public static ItemStack getSpaceHelmet(Component giver, Component getter, int edition) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        ItemStack spaceHelmet = new ItemStack(Material.RED_STAINED_GLASS);
        ItemMeta meta = spaceHelmet.getItemMeta();
        meta.displayName(Component.text("Space Helmet").color(TextColor.color(0xff5555)).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.DURABILITY, 1, false);
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("A rare space helmet forged", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.TRUE));
        lore.add(Component.text("from shards of moon glass.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.TRUE));
        lore.add(Component.text(" "));
        lore.add(Component.text("To: ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE).append(getter.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)));
        lore.add(Component.text("From: ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE).append(giver.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)));
        lore.add(Component.text(" "));
        lore.add(Component.text("Edition #" + edition, NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        lore.add(Component.text(WordUtils.capitalize(localDate.getMonth().toString().toLowerCase()) + " " + year, NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        lore.add(Component.text(" "));
        lore.add(Component.text("This item can be reforged!", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        lore.add(Component.text("SPECIAL HELMET", NamedTextColor.RED, TextDecoration.BOLD).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        meta.lore(lore);
        meta.getPersistentDataContainer().set(new NamespacedKey(HypixelSpaceHelmet.getPlugin(HypixelSpaceHelmet.class), "dodoman_spacehelmet_item"), PersistentDataType.INTEGER, edition);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.Armor", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.ArmorThoughNess", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
        spaceHelmet.setItemMeta(meta);
        return spaceHelmet;
    }
}

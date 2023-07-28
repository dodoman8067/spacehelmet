package kro.dodoworld.spacehelmet.data;

import kro.dodoworld.spacehelmet.HypixelSpaceHelmet;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SpaceHelmetEdition {
    private static File file;
    private static FileConfiguration spaceHelmetConfig;

    public static void init(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("HypixelSpaceHelmet").getDataFolder() + "/items_created.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                HypixelSpaceHelmet.getPlugin(HypixelSpaceHelmet.class).getLogger().warning("No plugin folder found. Creating new folder...");
            }
        }

        spaceHelmetConfig = YamlConfiguration.loadConfiguration(file);
        spaceHelmetConfig.addDefault("edition", 1);
        spaceHelmetConfig.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    public static FileConfiguration getSpaceHelmetConfig() {
        return spaceHelmetConfig;
    }

    public static void saveConfig(){
        try {
            spaceHelmetConfig.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void reloadConfig(){
        spaceHelmetConfig = YamlConfiguration.loadConfiguration(file);
    }
}

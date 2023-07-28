package kro.dodoworld.spacehelmet;

import kro.dodoworld.spacehelmet.command.SpaceHelmetCommand;
import kro.dodoworld.spacehelmet.data.SpaceHelmetEdition;
import kro.dodoworld.spacehelmet.item.SpaceHelmetItem;
import kro.dodoworld.spacehelmet.listener.SpaceHelmetHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class HypixelSpaceHelmet extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        initConfigs();
        SpaceHelmetItem.run(this);
        getServer().getPluginManager().registerEvents(new SpaceHelmetHandler(), this);
        getCommand("spacehelmet").setExecutor(new SpaceHelmetCommand());
    }

    private void initConfigs(){
        SpaceHelmetEdition.init();
        SpaceHelmetEdition.saveConfig();
        SpaceHelmetEdition.reloadConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

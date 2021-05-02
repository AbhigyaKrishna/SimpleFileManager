package simpleyamlfilemanager.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import simpleyamlfilemanager.bungee.FileManager;

public class Main extends Plugin{
    
    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        if (!this.getDataFolder().exists())
            this.getDataFolder().mkdirs();

        //Test file
        FileManager config = new FileManager("configuration", null, false);
        config.addDefault("test", "abc");
        config.saveDefaults();
    }

    @Override
    public void onDisable() {
        //Plugin Disable Logic
    }

    public static Main getInstance() {
        return instance;
    }
}

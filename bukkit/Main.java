package simpleyamlfilemanager.bukkit;

import org.bukkit.plugin.java.JavaPlugin;
import simpleyamlfilemanager.FileManager;

public class Main extends JavaPlugin{

    private static Main instance;
    
    @Override
    public void onEnable() {
        instance = this;

        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

        //Test file
        FileManager config = new FileManager("configuration", null, false);
        config.addHeader("Test");
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

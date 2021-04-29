package abhigya.needop.util.Configuration;

import abhigya.needop.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private final Main plugin = Main.getInstance();

    private File file;
    private FileConfiguration config;
    private File datafolder = plugin.getDataFolder();

    /**
     * Gets the file instance and creates the file with not exist
     * @param filename Name of the file without extension
     * @param path     Path of the file with proper '/'
     */
    public FileManager(String filename, String path) {
        this.file = new File(datafolder, path + filename + ".yml");
        this.config = (FileConfiguration) YamlConfiguration.loadConfiguration(this.file);

        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Sets the default value in the root at the given path as provided.
     * If value is null, the value will be removed from the default Configuration source.
     * @param path    Path of the value to set
     * @param value   New value to set to the path
     */
    public void addDefault(String path, Object value) {
        config.addDefault(path, value);
    }

    /**
     * Set the deader of the config file
     * @param header   Header String
     */
    public void addHeader(String header) {
        config.options().header(header);
    }

    /**
     * Saves the default value of the configuration
     */
    public void saveDefaults() {
        try {
            config.options().copyDefaults(true);
            config.save(this.file);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sets the specified path to the given value.
     * If value is null, the entry will be removed.
     * Any existing entry will be replaced, regardless of what the new value is.
     * @param path     Path of the value to set
     * @param value    New value to set to the path
     */
    public void set(String path, Object value) {
        try {
            config.set(path, value);
            config.save(this.file);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Get the file value
     * @return java.io.File
     */
    public File getFile() {
        return file;
    }

    /**
     * Get file configuration of the file
     * @return FileConfiguration
     */
    public FileConfiguration getConfig() {
        return config;
    }
}

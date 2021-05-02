package simpleyamlfilemanager.bungee;

import simpleyamlfilemanager.bungee.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;

public class FileManager {

    private final Main plugin = Main.getInstance();
    private ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);

    private String name;
    private File file;
    private Configuration config;
    private File datafolder = plugin.getDataFolder();
    private HashMap<String, Object> defaults = new HashMap<>();

    /**
     * Gets the file instance and creates the file with not exist
     * @param filename Name of the file without extension
     * @param path     Path of the file with proper '/'
     */
    public FileManager(String filename, String path, boolean saveFromResource) {
        if (path != null) {

            String[] paths = path.split("/");
            for (int i = 0; i < path.length(); i++) {
                String p;

                if (i == 0)
                    p = paths[i].replaceAll("/", "").trim();
                else
                    p = paths[i - 1].replaceAll("/", "").trim() + paths[i].replaceAll("/", "").trim();

                File folder = new File(datafolder, p);

                if (!folder.exists()) {
                    folder.mkdirs();
                }
            }
        }
        else {
            path = "";
        }

        this.name = filename;
        try {
            this.file = new File(datafolder, path + filename + ".yml");
            this.config = provider.load(this.file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (!file.exists()) {
            try {
                if (saveFromResource) {
                    saveResource();
                }
                else {
                    file.createNewFile();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Sets the default value in the root at the given path as provided.
     * If value is null, the value will be removed from the default Configuration source.
     * @param key    Path of the value to set
     * @param value   New value to set to the path
     */
    public void addDefault(String key, Object value) {
        defaults.put(key, value);
    }

    /**
     * Saves the default value of the configuration
     */
    public void saveDefaults() {
        try {
            if (!this.file.exists()) {
                for (String key : defaults.keySet())
                    config.set(key, defaults.get(key));

                provider.save(this.config, this.file);
                reload();
            }
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
            provider.save(this.config, this.file);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Saves and refresh the file cache
     */
    public void reload() {
        try {
            provider.save(this.config, this.file);
            config = null;
            this.config = provider.load(this.file);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Copy and save the given file from resource folder
     * @throws FileNotFoundException    If file not found in resource folder
     */
    public void saveResource() throws FileNotFoundException {
        if (plugin.getResourceAsStream(this.name + ".yml") == null)
            throw new FileNotFoundException("The given YAML file is not found in the resource folder.");

        try {
            if (!this.file.exists())
                Files.copy(plugin.getResourceAsStream(this.name + ".yml"), this.file.toPath());
        } catch (IOException ex) {
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
    public Configuration getConfig() {
        return config;
    }
}
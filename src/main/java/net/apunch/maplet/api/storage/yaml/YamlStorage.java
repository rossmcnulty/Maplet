package net.apunch.maplet.api.storage.yaml;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import net.apunch.maplet.Maplet;
import net.apunch.maplet.api.storage.DataKey;
import net.apunch.maplet.api.storage.Storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlStorage implements Storage {
    private final File file;
    private final FileConfiguration config;

    public YamlStorage(File file, String header) {
        this.file = file;
        config = new YamlConfiguration();

        // Create file if it does not exist
        if (!file.exists()) {
            try {
                Maplet.log("Creating new file: " + file.getName());
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException ex) {
                Maplet.log(Level.SEVERE, "Could not create file: " + file.getName());
            }

            config.options().header(header);
            save();
        }
    }

    @Override
    public DataKey getKey(String key) {
        return new YamlKey(config, key);
    }

    @Override
    public void load() {
        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

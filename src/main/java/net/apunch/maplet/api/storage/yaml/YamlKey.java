package net.apunch.maplet.api.storage.yaml;

import net.apunch.maplet.api.storage.DataKey;

import org.bukkit.configuration.file.FileConfiguration;

public class YamlKey extends DataKey {
    private final FileConfiguration config;
    private final String root;

    public YamlKey(FileConfiguration config, String root) {
        this.config = config;
        this.root = root;
    }

    @Override
    public boolean getBoolean(String key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyExists(String key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setBoolean(String key, boolean value) {
        // TODO
    }
}

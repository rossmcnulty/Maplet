package net.apunch.maplet.api.storage.nbt;

import net.apunch.maplet.api.storage.DataKey;
import net.apunch.maplet.api.storage.Storage;

public class NBTStorage implements Storage {

    @Override
    public DataKey getKey(String key) {
        return new NBTKey();
    }

    @Override
    public void load() {
        // TODO Auto-generated method stub

    }

    @Override
    public void save() {
        // TODO Auto-generated method stub

    }
}

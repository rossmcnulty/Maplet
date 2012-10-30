package net.apunch.maplet;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import net.apunch.maplet.api.Map;
import net.apunch.maplet.api.application.Application;
import net.apunch.maplet.api.attachment.SelectableAttachment;
import net.apunch.maplet.api.event.MapletCreateEvent;
import net.apunch.maplet.api.menu.Menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.java.JavaPlugin;

public final class Maplet extends JavaPlugin implements Listener {
    private static final java.util.Map<String, Application> applications = new HashMap<String, Application>();
    private static final java.util.Map<Short, Map> maps = new HashMap<Short, Map>();
    private static final String ROOT_DIRECTORY = "plugins/Maplet/";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        // TODO: Separate out commands into better system
        Player player = (Player) sender;
        if (args.length == 1 && args[0].equalsIgnoreCase("create")) {
            ItemStack map = new ItemStack(Material.MAP, 1, getServer().createMap(player.getWorld()).getId());
            player.getInventory().addItem(map);
            createMap(map);
            player.sendMessage("Created map with id '" + map.getDurability() + "'.");
        }
        return true;
    }

    @Override
    public void onDisable() {
        log(toString() + " disabled.");
    }

    @Override
    public void onEnable() {
        loadApplications();

        getServer().getPluginManager().registerEvents(this, this);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

            @Override
            public void run() {
                for (Map map : maps.values()) {
                    map.render();
                }
            }
        }, 0L, 5L);

        log(toString() + " enabled.");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(getDescription().getName() + " v" + getDescription().getVersion() + " [Written by: ");
        List<String> authors = getDescription().getAuthors();
        for (int i = 0; i < authors.size(); i++) {
            builder.append((String) authors.get(i) + (i + 1 != authors.size() ? ", " : ""));
        }
        builder.append("]");

        return builder.toString();
    }

    @EventHandler
    public void onItemHeldChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
        if (isMap(newItem)) {
            Map map = getMap(newItem.getDurability());
            if (map != null && !map.isViewer(player)) {
                map.addViewer(player);
            }
        }

        ItemStack oldItem = player.getInventory().getItem(event.getPreviousSlot());
        if (isMap(oldItem)) {
            Map map = getMap(oldItem.getDurability());
            if (map != null && map.isViewer(player)) {
                map.removeViewer(player);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack hand = event.getItem();
        if (isMap(hand)) {
            getMap(hand.getDurability()).cycleSelected(event.getAction());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        if (!event.isCancelled() && event.isSneaking()) {
            ItemStack hand = event.getPlayer().getItemInHand();
            if (isMap(hand)) {
                Map map = getMap(hand.getDurability());
                Menu activeMenu = map.getActiveMenu();
                if (activeMenu != null) {
                    SelectableAttachment selected = activeMenu.getSelectedAttachment();
                    if (selected != null) {
                        selected.onClick(map);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // Remove player from any maps on quit
        for (Map map : maps.values()) {
            if (map.isViewer(player)) {
                map.removeViewer(player);
            }
        }
    }

    /**
     * Creates a new {@link Map} from the given item.
     * 
     * @param map
     *            Map item associated with this map.
     * 
     * @return Newly created map.
     */
    public static Map createMap(ItemStack mapItem) {
        short id = mapItem.getDurability();
        Map map = new Map(id);
        MapletCreateEvent event = new MapletCreateEvent(map);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            maps.put(id, map);

            // Remove the map's default renderers
            MapView view = Bukkit.getMap(id);
            for (MapRenderer renderer : view.getRenderers()) {
                view.removeRenderer(renderer);
            }

            return map;
        }

        return null;
    }

    /**
     * Gets the {@link Application} with the given name.
     * 
     * @param name
     *            Name of the application
     * @return Application with the given name, or null or no application was found.
     */
    public static Application getApplication(String name) {
        return applications.get(name);
    }

    /**
     * Gets a collection of registered {@link Application}s.
     * 
     * @return Collection of registered applications.
     */
    public static Collection<Application> getApplications() {
        return applications.values();
    }

    /**
     * Gets an {@link Image} with the given file name. This method searches through the Maplet images directory for an
     * image with the given name (plugins/Maplet/images/).
     * 
     * @param name
     *            Name of file including file extension
     * @return Image with the given name.
     */
    public static Image getImage(String name) {
        File file = new File(ROOT_DIRECTORY + "images/" + name);
        if (file.exists()) {
            try {
                return ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Gets the registered {@link Map} with the given ID.
     * 
     * @param id
     *            ID of the map, treated as the item's "durability" by Bukkit
     * @return Map with the given ID, or null if no registered map has the given ID.
     */
    public static Map getMap(short id) {
        return maps.get(id);
    }

    /**
     * Gets whether the given item is a registered map.
     * 
     * @param item
     *            ItemStack to check
     * @return True if the given item is a registered map.
     */
    public static boolean isMap(ItemStack item) {
        return item != null && item.getType() == Material.MAP && maps.containsKey(item.getDurability());
    }

    public static void log(Level level, String message) {
        Bukkit.getServer().getLogger().log(level, "[Maplet] " + message);
    }

    public static void log(String message) {
        log(Level.INFO, message);
    }

    private void loadApplications() {
        File applicationDirectory = new File(ROOT_DIRECTORY + "applications");
        if (applicationDirectory.exists()) {
            File[] files = applicationDirectory.listFiles();
            int found = 0;
            for (File file : files) {
                Plugin plugin;
                try {
                    plugin = Bukkit.getPluginManager().loadPlugin(file);
                    if (!Application.class.isAssignableFrom(plugin.getClass())) {
                        throw new Exception("Plugin's main class is not an Application.");
                    }

                    Application application = (Application) plugin;
                    applications.put(application.getName(), application);

                    plugin.onLoad();
                    found++;
                } catch (Exception e) {
                    log(Level.SEVERE, "Could not enable Maplet Application '" + file.getName() + "'. " + e.getMessage());
                    e.printStackTrace();
                }
            }

            ((CraftServer) getServer()).enablePlugins(PluginLoadOrder.POSTWORLD);
            log("Loaded " + found + " applications.");
        }
    }
}

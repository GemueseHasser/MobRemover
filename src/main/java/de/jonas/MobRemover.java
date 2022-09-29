package de.jonas;

import de.jonas.mobremover.task.MobRemoveTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Die Haupt- und Main-Klasse dieses Plugins, durch die das gesamte Plugin initialisiert wird. Der
 * {@link MobRemover} ist die Klasse, in der alle Unterinstanzen registriert werden.</p>
 *
 * <p>Der {@link MobRemover} entfernt periodisch alle Entitäten, die in der Config aufgeführt werden, bis auf
 * Entitäten, die eine bestimmte Metadata besitzen, die auch in der Config aufgeführt werden muss.</p>
 */
@NotNull
public final class MobRemover extends JavaPlugin {

    //<editor-fold desc="STATIC FIELDS">
    /** Die Instanz dieses Plugins. */
    @Getter
    private static MobRemover instance;
    /** Der Prefix dieses Plugins. */
    @Getter
    private static String prefix;
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Alle Metadata Tokens, welche eine Entität am Leben erhalten soll. */
    @Getter
    private final List<String> tokens = new ArrayList<>();
    /** Alle {@link EntityType Typen}, die durch den Mob-Remover entfernt werden sollen. */
    @Getter
    private final List<EntityType> entityTypes = new ArrayList<>();
    /** Alle {@link World Welten}, in denen dieser MobRemover aktiv sein soll. */
    @Getter
    private final List<World> worlds = new ArrayList<>();
    //</editor-fold>


    //<editor-fold desc="setup and start">
    @Override
    public void onEnable() {
        super.onEnable();

        // initialize plugin instance
        instance = this;

        // initialize prefix
        prefix = ChatColor.GRAY + "[" + ChatColor.RED + "MobRemover" + ChatColor.GRAY + "] " + ChatColor.WHITE;

        // load config
        getConfig().options().copyDefaults(true);
        saveConfig();

        // load values from config
        final int removePeriod = getConfig().getInt("removePeriodMinutes");
        this.tokens.addAll(getConfig().getStringList("tokens"));

        for (@NotNull final String name : getConfig().getStringList("entityTypes")) {
            try {
                this.entityTypes.add(EntityType.valueOf(name.toUpperCase()));
            } catch (@NotNull final IllegalArgumentException ignored) {
                getLogger().info("Es wurde keine Entität unter EntityType." + name.toUpperCase() + " gefunden.");
            }
        }

        for (@NotNull final String worldName : getConfig().getStringList("worlds")) {
            final World world = Bukkit.getWorld(worldName);

            if (world == null) {
                getLogger().info("Es gibt keine Welt mit dem Namen " + worldName + ".");
                continue;
            }

            this.worlds.add(world);
        }

        // initialize mob remove task
        new MobRemoveTask().runTaskTimer(this, 100, removePeriod * 1000L);

        getLogger().info("Das Plugin wurde erfolgreich aktiviert.");
    }
    //</editor-fold>

    //<editor-fold desc="shutdown">
    @Override
    public void onDisable() {
        super.onDisable();

        getLogger().info("Das Plugin wurde deaktiviert.");
    }
    //</editor-fold>

}

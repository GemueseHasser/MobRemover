package de.jonas;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

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
    private static MobRemover instance;
    //</editor-fold>


    //<editor-fold desc="setup and start">
    @Override
    public void onEnable() {
        super.onEnable();

        // initialize plugin instance
        instance = this;

        // load config
        getConfig().options().copyDefaults(true);
        saveConfig();

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


    /**
     * Gibt die Instanz dieses Plugins zurück.
     *
     * @return Die Instanz dieses Plugins.
     */
    @NotNull
    public static MobRemover getInstance() {
        return instance;
    }

}

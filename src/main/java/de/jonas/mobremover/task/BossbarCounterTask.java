package de.jonas.mobremover.task;

import de.jonas.MobRemover;
import de.jonas.mobremover.handler.MobRemoveHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

/**
 * Ein {@link BossbarCounterTask} stellt eine sich konstant wiederholende Prozedur dar, die die {@link BossBar} für
 * jeden Spieler so lange aktualisiert, bis die {@code BOSSBAR_DURATION} abgelaufen ist. Dann wird die jeder Spieler der
 * BossBar entfernt.
 */
@NotNull
public final class BossbarCounterTask extends BukkitRunnable {

    //<editor-fold desc="CONSTANTS">
    /** Der {@link NamespacedKey} womit sich die {@link BossBar} identifizieren lässt. */
    @NotNull
    public static final NamespacedKey BOSS_BAR_KEY = new NamespacedKey(MobRemover.getInstance(), "bossBar");
    /** Die {@link BossBar}, der jeder Spieler des Netzwerks hinzugefügt wird, bevor alle Entities entfernt werden. */
    @NotNull
    private static final BossBar BOSS_BAR = Bukkit.createBossBar(BOSS_BAR_KEY, "", BarColor.BLUE, BarStyle.SOLID);
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Der Counter bis zum Entfernen aller Entities. */
    private int count = MobRemoveTask.BOSSBAR_DURATION;
    //</editor-fold>


    //<editor-fold desc="implementation">
    @Override
    public void run() {
        // check if count is 0
        if (count <= 0) {
            // display removed entities
            final int removedEntities = MobRemoveHandler.removeMobs();
            BOSS_BAR.setColor(BarColor.RED);
            BOSS_BAR.setProgress(1);
            BOSS_BAR.setTitle(
                ChatColor.GRAY + "Es wurden " + ChatColor.RED + removedEntities + ChatColor.GRAY + " Mobs entfernt."
            );

            new BukkitRunnable() {
                @Override
                public void run() {
                    // remove boss-bar
                    BOSS_BAR.removeAll();
                }
            }.runTaskLater(MobRemover.getInstance(), MobRemover.getInstance().getRemovedDisplayTime() * 20L);

            // cancel task
            this.cancel();
            return;
        }

        // remove all player
        BOSS_BAR.removeAll();

        // add all player
        for (@NotNull final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            BOSS_BAR.addPlayer(onlinePlayer);
        }

        // update boss-bar and counter
        BOSS_BAR.setTitle(
            ChatColor.GRAY + "Mob-Remover in " + ChatColor.BLUE + count + ChatColor.GRAY + " Sekunden"
        );
        BOSS_BAR.setProgress((1.0 / MobRemoveTask.BOSSBAR_DURATION) * this.count);

        count--;
    }
    //</editor-fold>

}

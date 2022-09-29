package de.jonas.mobremover.task;

import de.jonas.MobRemover;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

/**
 * Ein {@link MobRemoveTask} stellt eine sich konstant wiederholende Prozedur dar. Mithilfe dieses
 * {@link BukkitRunnable} werden periodisch alle Entitäten, die in der Config hinterlegt sind, gelöscht.
 */
public final class MobRemoveTask extends BukkitRunnable {

    //<editor-fold desc="implementation">
    @Override
    public void run() {
        int count = 0;

        for (@NotNull final World world : MobRemover.getInstance().getWorlds()) {
            loop:
            for (@NotNull final Entity entity : world.getEntities()) {
                if (!MobRemover.getInstance().getEntityTypes().contains(entity.getType())) continue;

                for (@NotNull final String meta : MobRemover.getInstance().getTokens()) {
                    if (entity.hasMetadata(meta)) continue loop;
                }

                entity.remove();
                count++;
            }
        }

        for (@NotNull final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(
                MobRemover.getPrefix() + "Es wurden " + ChatColor.RED + count + ChatColor.WHITE + " Entities entfernt."
            );
        }
    }
    //</editor-fold>

}

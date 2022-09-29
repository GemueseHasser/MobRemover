package de.jonas.mobremover.task;

import de.jonas.MobRemover;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

/**
 * Ein {@link MobRemoveTask} stellt eine sich konstant wiederholende Prozedur dar. Mithilfe dieses
 * {@link BukkitRunnable} werden periodisch alle Entitäten, die in der Config hinterlegt sind, gelöscht.
 */
@NotNull
public final class MobRemoveTask extends BukkitRunnable {

    //<editor-fold desc="CONSTANTS">
    /** Die Zeit in Sekunden, die jeder Spieler vorgewarnt werden soll, dass alle Entities entfernt werden. */
    public static final int BOSSBAR_DURATION = 30;
    //</editor-fold>


    //<editor-fold desc="implementation">
    @Override
    public void run() {
        new BossbarCounterTask().runTaskTimer(MobRemover.getInstance(), 0, 20);
    }
    //</editor-fold>

}

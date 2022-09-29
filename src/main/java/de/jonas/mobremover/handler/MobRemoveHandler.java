package de.jonas.mobremover.handler;

import de.jonas.MobRemover;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * Mithilfe dieses {@link MobRemoveHandler} kann man alle Mobs löschen, die in der Config hinterlegt sind in allen
 * Welten, die in der Config hinterlegt sind.
 */
public final class MobRemoveHandler {

    //<editor-fold desc="utility">

    /**
     * Entfernt alle Mobs, die in der Config hinterlegt sind in allen Welten, die in der Config hinterlegt sind.
     * Entities mit bestimmten Tokens, die in der Config eingestellt werden können, werden nicht gelöscht.
     */
    public static void removeMobs() {
        for (@NotNull final World world : MobRemover.getInstance().getWorlds()) {
            loop:
            for (@NotNull final Entity entity : world.getEntities()) {
                if (!MobRemover.getInstance().getEntityTypes().contains(entity.getType())) continue;

                for (@NotNull final String meta : MobRemover.getInstance().getTokens()) {
                    if (entity.hasMetadata(meta)) continue loop;
                }

                entity.remove();
            }
        }
    }
    //</editor-fold>

}

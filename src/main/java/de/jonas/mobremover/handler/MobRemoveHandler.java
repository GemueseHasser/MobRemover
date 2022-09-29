package de.jonas.mobremover.handler;

import de.jonas.MobRemover;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.io.File;

/**
 * Mithilfe dieses {@link MobRemoveHandler} kann man alle Mobs löschen, die in der Config hinterlegt sind in allen
 * Welten, die in der Config hinterlegt sind.
 */
public final class MobRemoveHandler {

    //<editor-fold desc="utility">

    /**
     * Entfernt alle Mobs, die in der Config hinterlegt sind in allen Welten, die in der Config hinterlegt sind. Sowohl
     * Entities mit bestimmten Tokens, die in der Config eingestellt werden können, werden nicht gelöscht, als auch
     * Entities, die sich in einer bestimmten Mob-Zone befinden.
     */
    public static void removeMobs() {
        final File file = new File("plugins/MobRemover", "zone.yml");
        final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        final Integer x1 = (Integer) configuration.get("zone.1.x");
        final Integer z1 = (Integer) configuration.get("zone.1.z");

        final Integer x2 = (Integer) configuration.get("zone.2.x");
        final Integer z2 = (Integer) configuration.get("zone.2.z");

        final Integer minX = x1 == null || x2 == null ? null : Math.min(x1, x2);
        final Integer maxX = x1 == null || x2 == null ? null : Math.max(x1, x2);

        final Integer minZ = z1 == null || z2 == null ? null : Math.min(z1, z2);
        final Integer maxZ = z1 == null || z2 == null ? null : Math.max(z1, z2);

        for (@NotNull final World world : MobRemover.getInstance().getWorlds()) {
            loop:
            for (@NotNull final Entity entity : world.getEntities()) {
                // check entity type
                if (!MobRemover.getInstance().getEntityTypes().contains(entity.getType())) continue;

                // check if mob is in mob zone
                if (isInMobZone(entity.getLocation(), minX, maxX, minZ, maxZ)) continue;

                // check if mob has tokens
                for (@NotNull final String meta : MobRemover.getInstance().getTokens()) {
                    if (entity.hasMetadata(meta)) continue loop;
                }

                // remove mob
                entity.remove();
            }
        }
    }

    /**
     * Prüft, ob sich eine {@link Location} in einem 2 dimensionalen Bereich befindet.
     *
     * @param location Die {@link Location}, die getestet werden soll.
     * @param minX     Die minimale X-Koordinate des 2 dimensionalen Bereichs.
     * @param maxX     Die maximale X-Koordinate des 2 dimensionalen Bereichs.
     * @param minZ     Die minimale Z-Koordinate des 2 dimensionalen Bereichs.
     * @param maxZ     Die maximale Z-Koordinate des 2 dimensionalen Bereichs.
     *
     * @return Wenn sich die {@link Location} in dem 2 dimensionalen Bereich befindet {@code true}, ansonsten
     *     {@code false}.
     */
    private static boolean isInMobZone(
        @NotNull final Location location,
        @Range(from = 0, to = Integer.MAX_VALUE) final Integer minX,
        @Range(from = 0, to = Integer.MAX_VALUE) final Integer maxX,
        @Range(from = 0, to = Integer.MAX_VALUE) final Integer minZ,
        @Range(from = 0, to = Integer.MAX_VALUE) final Integer maxZ
    ) {
        // check if all positions set
        if (minX == null || maxX == null || minZ == null || maxZ == null) return false;

        // check if location is in zone
        return location.getBlockX() > minX
            && location.getBlockX() < maxX
            && location.getBlockZ() > minZ
            && location.getBlockZ() < maxZ;
    }
    //</editor-fold>

}

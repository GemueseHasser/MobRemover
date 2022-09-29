package de.jonas.mobremover.command;

import de.jonas.MobRemover;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Mithilfe dieses Befehls kann man die beiden Positionen des Bereichs setzen, in dem Mobs spawnen dürfen und nicht
 * entfernt werden.
 */
@NotNull
public final class MobZone implements CommandExecutor {

    //<editor-fold desc="implementation">
    @Override
    @SneakyThrows
    public boolean onCommand(
        @NotNull final CommandSender sender,
        @NotNull final Command command,
        @NotNull final String label,
        @NotNull final String[] args
    ) {
        // check if sender instanceof player
        if (!(sender instanceof Player)) {
            sender.sendMessage(MobRemover.getPrefix() + "Man kann diesen Befehl nur als Spieler ausführen!");
            return true;
        }

        final Player player = (Player) sender;

        // check if player has permission
        if (!player.hasPermission("mob.zone")) {
            player.sendMessage(MobRemover.getPrefix() + "Dazu hast du keine Rechte!");
            return true;
        }

        // check if the usage is correct
        if (args.length != 1) {
            player.sendMessage(MobRemover.getPrefix() + "Bitte benutze /mobzone <1|2>");
            return true;
        }

        if (!(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("2"))) {
            player.sendMessage(MobRemover.getPrefix() + "Bitte benutze /mobzone <1|2>");
            return true;
        }

        // save position
        final File file = new File("plugins/MobRemover", "zone.yml");
        final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        configuration.set("zone." + args[0] + ".x", player.getLocation().getBlockX());
        configuration.set("zone." + args[0] + ".z", player.getLocation().getBlockZ());

        configuration.save(file);

        player.sendMessage(
            MobRemover.getPrefix() + "Du hast erfolgreich die " + args[0] + ". Position der Mob-Zone gesetzt."
        );
        return true;
    }
    //</editor-fold>

}

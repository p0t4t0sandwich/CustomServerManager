package dev.neuralnexus.customservermanager.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.neuralnexus.customservermanager.common.commands.CustomServerManagerCommand;
import dev.neuralnexus.taterlib.velocity.abstractions.player.VelocityPlayer;

public class VelocityCustomServerManagerCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        try {
            String[] args = invocation.arguments();

            // Check if sender is a player
            boolean isPlayer = invocation.source() instanceof Player;
            VelocityPlayer player = isPlayer ? new VelocityPlayer((Player) invocation.source()) : null;

            // Execute command
            CustomServerManagerCommand.executeCommand(player, isPlayer, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

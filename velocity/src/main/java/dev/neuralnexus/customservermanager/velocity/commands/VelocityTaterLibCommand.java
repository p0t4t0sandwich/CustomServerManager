package dev.neuralnexus.customservermanager.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

public class VelocityTaterLibCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
//        runTaskAsync(() -> {
        try {
            String[] args = invocation.arguments();

            // Check if sender is a player
            boolean isPlayer = invocation.source() instanceof Player;
//            VelocityPlayer player = isPlayer ? new VelocityPlayer((Player) invocation.source()) : null;

            // Execute command
//            TaterLibCommand.executeCommand(player, isPlayer, args);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
//        });
    }
}

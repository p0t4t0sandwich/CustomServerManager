package dev.neuralnexus.customservermanager.common.commands;

import dev.neuralnexus.customservermanager.common.CustomServerManager;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;
import dev.neuralnexus.taterlib.common.placeholder.PlaceholderParser;

import static dev.neuralnexus.taterlib.common.Utils.ansiiParser;

public interface CustomServerManagerCommand {
    static String getCommandUsage() {
        return "Usage: /customservermanager <reload|version>";
    }

    static String getCommandName() {
        return "customservermanager";
    }

    static String getCommandDescription() {
        return "Root CustomServerManager Command";
    }

    static String permissionBuilder(String[] args) {
        if (args.length == 0) {
            return "customservermanager.command";
        } else if (args.length == 1) {
            return "customservermanager.command." + args[0].toLowerCase();
        } else if (args.length == 2) {
            return "customservermanager.command." + args[0].toLowerCase() + "." + args[1].toLowerCase();
        } else {
            return "customservermanager.command." + args[0].toLowerCase() + "." + args[1].toLowerCase() + "." + args[2].toLowerCase();
        }
    }

    static String executeCommand(String[] args) {
        String text;
        switch (args[0].toLowerCase()) {
            case "reload":
                try {
                    // Try to reload the plugin
                    CustomServerManager.stop();
                    CustomServerManager.start();
                    text = "&aReloaded CustomServerManager.";
                } catch (Exception e) {
                    // If an error occurs, print the error and return an error message
                    text = "&cAn error occurred while reloading the plugin.";
                    e.printStackTrace();
                }
                break;
            case "version":
                text = "&aCustomServerManager v1.0.0-SNAPSHOT";
                break;
            default:
                text = getCommandUsage();
                break;
        }
        return PlaceholderParser.substituteSectionSign(text);
    }

    static void executeCommand(AbstractPlayer player, boolean isPlayer, String[] args) {
        if (isPlayer) {
            if (!player.hasPermission(permissionBuilder(args))) {
                player.sendMessage("§cYou do not have permission to use this command.");
            } else {
                player.sendMessage(executeCommand(args));
            }
        } else {
            CustomServerManager.useLogger(ansiiParser(executeCommand(args)));
        }
    }
}

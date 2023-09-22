package dev.neuralnexus.customservermanager.common;

import dev.neuralnexus.taterlib.common.TemplatePlugin;

/**
 * The TaterComms plugin interface.
 */
public interface CustomServerManagerPlugin extends TemplatePlugin {
    /**
     * Starts the TaterComms plugin.
     */
    default void pluginStart() {
        try {
            useLogger("TaterComms is running on " + getServerType() + " " + getServerVersion() + "!");

            // Start
            CustomServerManager.start(pluginConfigPath(), pluginLogger());

            // Register hooks
            registerHooks();

            // Register event listeners
            registerEventListeners();

            // Register commands
            registerCommands();

            useLogger("TaterComms has been enabled!");

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Stops the plugin.
     */
    default void pluginStop() {
        try {
            CustomServerManager.stop();
            useLogger("TaterComms has been disabled!");
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}

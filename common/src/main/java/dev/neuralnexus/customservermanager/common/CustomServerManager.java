package dev.neuralnexus.customservermanager.common;

import dev.neuralnexus.customservermanager.common.api.CustomServerManagerAPIProvider;
import dev.neuralnexus.taterlib.common.TaterLib;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;
import dev.neuralnexus.taterlib.lib.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Main class for the CustomServerManager plugin.
 */
public class CustomServerManager {
    private static final CustomServerManager instance = new CustomServerManager();
    private static YamlDocument config;
    private static String configPath;
    private static AbstractLogger logger;
    private static boolean STARTED = false;
    private static final ArrayList<Object> hooks = new ArrayList<>();

    /**
     * Constructor for the CustomServerManager class.
     */
    public CustomServerManager() {}

    /**
     * Getter for the singleton instance of the CustomServerManager class.
     * @return The singleton instance
     */
    public static CustomServerManager getInstance() {
        return instance;
    }

    /**
     * Add a hook to the list of hooks
     * @param hook The hook to add
     */
    public static void addHook(Object hook) {
        hooks.add(hook);
    }

    /**
     * Use the Logger
     * @param message The message to log
     */
    public static void useLogger(String message) {
        logger.info(message);
    }

    /**
     * Start
     * @param configPath The path to the config file
     * @param logger The logger
     */
    public static void start(String configPath, AbstractLogger logger) {
        CustomServerManager.configPath = configPath;
        CustomServerManager.logger = logger;

        // Config
        try {
            config = YamlDocument.create(new File("." + File.separator + configPath + File.separator + "CustomServerManager", "CustomServerManager.config.yml"),
                    Objects.requireNonNull(CustomServerManager.class.getClassLoader().getResourceAsStream("CustomServerManager.config.yml"))
            );
            config.reload();
        } catch (IOException | NullPointerException e) {
            useLogger("Failed to load CustomServerManager.config.yml!\n" + e.getMessage());
            e.printStackTrace();
        }

        if (STARTED) {
            useLogger("CustomServerManager has already started!");
            return;
        }
        STARTED = true;

        // Register player listeners

        // Register server listeners

        useLogger("CustomServerManager has been started!");
        CustomServerManagerAPIProvider.register(instance);
    }

    /**
     * Start
     */
    public static void start() {
        start(configPath, TaterLib.logger);
    }

    /**
     * Stop
     */
    public static void stop() {
        if (!STARTED) {
            useLogger("CustomServerManager has already stopped!");
            return;
        }
        STARTED = false;

        // Remove references to objects
        config = null;

        useLogger("CustomServerManager has been stopped!");
        CustomServerManagerAPIProvider.unregister();
    }

    /**
     * Reload
     */
    public static void reload() {
        if (!STARTED) {
            useLogger("CustomServerManager has not been started!");
            return;
        }

        // Stop
        stop();

        // Start
        start(configPath, logger);

        useLogger("CustomServerManager has been reloaded!");
    }
}

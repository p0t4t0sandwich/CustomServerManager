package dev.neuralnexus.customservermanager.common;

import dev.neuralnexus.ampapi.AMPAPI;
import dev.neuralnexus.ampapi.modules.ADS;
import dev.neuralnexus.ampapi.types.IADSInstance;
import dev.neuralnexus.ampapi.types.Instance;
import dev.neuralnexus.ampapi.types.Result;
import dev.neuralnexus.customservermanager.common.api.CustomServerManagerAPIProvider;
import dev.neuralnexus.customservermanager.common.config.Config;
import dev.neuralnexus.taterlib.common.TaterLib;
import dev.neuralnexus.taterlib.common.Utils;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;
import dev.neuralnexus.taterlib.lib.gson.Gson;
import dev.neuralnexus.taterlib.lib.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Main class for the CustomServerManager plugin.
 */
public class CustomServerManager {
    private static final CustomServerManager instance = new CustomServerManager();
    private static Config config;
    private static String configPath;
    private static AbstractLogger logger;
    private static boolean STARTED = false;
    private static final ArrayList<Object> hooks = new ArrayList<>();
    private static final HashMap<String, AMPAPI> apiHandlers = new HashMap<>();

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
            Gson gson = new GsonBuilder().create();
            File configFile = new File("." + File.separator + configPath + File.separator + "CustomServerManager", "customservermanager.config.yml");
            Reader reader = new FileReader(configFile);
            config = gson.fromJson(reader, Config.class);
        } catch (IOException | NullPointerException e) {
            useLogger("Failed to load CustomServerManager.config.yml!\n" + e.getMessage());
            e.printStackTrace();
        }

        if (STARTED) {
            useLogger("CustomServerManager has already started!");
            return;
        }
        STARTED = true;

        // Start AMP API
        useLogger("Starting AMP API...");
        Utils.runTaskAsync(() -> {
            try {
                ADS API = new ADS(config.AMP_API_URL, config.AMP_API_USERNAME, config.AMP_API_PASSWORD, "", "");
                apiHandlers.put("ADS", API);

                List<IADSInstance> targets = API.ADSModule.GetInstances().result;
                for (IADSInstance target : targets) {
                    Instance[] instances = target.AvailableInstances;
                    for (Instance instance : instances) {
                        if (config.AMP_API_SERVERS.containsKey(instance.InstanceName)) {
                            apiHandlers.put(instance.InstanceName, API.InstanceLogin(instance.InstanceID));
                        }
                    }
                }
            } catch (Exception e) {
                useLogger(e.getMessage());
            }
        });

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

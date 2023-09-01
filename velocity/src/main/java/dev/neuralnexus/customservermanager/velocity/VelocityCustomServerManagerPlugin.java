package dev.neuralnexus.customservermanager.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

import dev.neuralnexus.customservermanager.velocity.hooks.LuckPermsHook;
import org.slf4j.Logger;

import java.util.ArrayList;

@Plugin(
        id = "customservermanager",
        name = "CustomServerManager",
        version = "1.0.0-SNAPSHOT",
        authors = "p0t4t0sandwich",
        description = "A proxy plugin that utilizes the AMP API to manage my Minecraft servers",
        url = "https://github.com/p0t4t0sandwich/CustomServerManager",
        dependencies = {
                @Dependency(id = "luckperms", optional = true)
        }
)
public class VelocityCustomServerManagerPlugin {
    private final ProxyServer server;
    private final Logger logger;
    private static final ArrayList<Object> hooks = new ArrayList<>();

    /**
     * Add a hook to the hooks list
     * @param hook The hook to add
     */
    public static void addHook(Object hook) {
        hooks.add(hook);
    }

    @Inject
    public VelocityCustomServerManagerPlugin(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

    }

    /**
     * Called when the proxy is initialized.
     * @param event The event.
     */
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // Register LuckPerms hook
        if (server.getPluginManager().getPlugin("LuckPerms").isPresent()) {
            logger.info("LuckPerms detected, enabling LuckPerms hook.");
            addHook(new LuckPermsHook());
        }
    }
}

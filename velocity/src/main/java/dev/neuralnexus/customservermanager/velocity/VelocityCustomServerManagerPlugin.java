package dev.neuralnexus.customservermanager.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

import dev.neuralnexus.customservermanager.velocity.commands.VelocityCustomServerManagerCommand;
import dev.neuralnexus.customservermanager.common.CustomServerManagerPlugin;
import dev.neuralnexus.customservermanager.common.commands.CustomServerManagerCommand;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;
import dev.neuralnexus.taterlib.velocity.TemplateVelocityPlugin;
import dev.neuralnexus.taterlib.velocity.abstractions.logger.VelocityLogger;
import org.slf4j.Logger;

@Plugin(
        id = "customservermanager",
        name = "CustomServerManager",
        version = "1.0.0-SNAPSHOT",
        authors = "p0t4t0sandwich",
        description = "A proxy plugin that utilizes the AMP API to manage my Minecraft servers",
        url = "https://github.com/p0t4t0sandwich/CustomServerManager",
        dependencies = {
                @Dependency(id = "taterlib"),
                @Dependency(id = "luckperms", optional = true)
        }
)
public class VelocityCustomServerManagerPlugin extends TemplateVelocityPlugin implements CustomServerManagerPlugin {
    @Inject private ProxyServer server;
    @Inject private Logger logger;

    /**
     * @inheritDoc
     */
    @Override
    public AbstractLogger pluginLogger() {
        return new VelocityLogger(logger);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerCommands() {
        server.getCommandManager().register(CustomServerManagerCommand.getCommandName(), new VelocityCustomServerManagerCommand());
    }

    /**
     * Called when the proxy is initialized.
     * @param event The event.
     */
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        proxyServer = server;
        pluginStart();
    }

    /**
     * Called when the proxy is shutting down.
     * @param event The event.
     */
    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        pluginStop();
    }
}

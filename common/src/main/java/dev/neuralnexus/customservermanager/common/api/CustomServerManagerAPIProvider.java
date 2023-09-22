package dev.neuralnexus.customservermanager.common.api;

import dev.neuralnexus.customservermanager.common.CustomServerManager;

/**
 * TaterComms API Provider
 */
public class CustomServerManagerAPIProvider {
    private static CustomServerManager instance = null;

    /**
     * Get the instance of BeeNameGenerator
     * @return The instance of BeeNameGenerator
     */
    public static CustomServerManager get() {
        if (instance == null) {
            throw new NotLoadedException();
        }
        return instance;
    }

    /**
     * DO NOT USE THIS METHOD, IT IS FOR INTERNAL USE ONLY
     * @param instance: The instance of TaterComms
     */
    public static void register(CustomServerManager instance) {
        CustomServerManagerAPIProvider.instance = instance;
    }

    /**
     * DO NOT USE THIS METHOD, IT IS FOR INTERNAL USE ONLY
     */
    public static void unregister() {
        CustomServerManagerAPIProvider.instance = null;
    }

    /**
     * Throw this exception when the API hasn't loaded yet, or you don't have the BeeNameGenerator plugin installed.
     */
    private static final class NotLoadedException extends IllegalStateException {
        private static final String MESSAGE = "The API hasn't loaded yet, or you don't have the TaterComms plugin installed.";

        NotLoadedException() {
            super(MESSAGE);
        }
    }
}

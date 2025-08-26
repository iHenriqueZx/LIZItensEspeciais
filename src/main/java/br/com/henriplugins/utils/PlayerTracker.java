package br.com.henriplugins.utils;

import java.util.HashSet;
import java.util.UUID;

public class PlayerTracker {
    private static final HashSet<UUID> trackingDisabled = new HashSet<>();

    public static boolean isTracking(UUID playerUUID) {
        return !trackingDisabled.contains(playerUUID);
    }

    public static void toggleTracking(UUID playerUUID) {
        if (trackingDisabled.contains(playerUUID)) {
            trackingDisabled.remove(playerUUID);
        } else {
            trackingDisabled.add(playerUUID);
        }
    }
}

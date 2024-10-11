package dev.onechris.extension.blockversion;

import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.bedrock.SessionLoginEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserPreInitializeEvent;
import org.geysermc.geyser.api.extension.Extension;
import org.geysermc.geyser.api.util.MinecraftVersion;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.geyser.text.GeyserLocale;

import java.util.Comparator;

public class BlockVersion implements Extension {

    private static MinecraftVersion latestVersion;

    @Subscribe
    public void onStartup(GeyserPreInitializeEvent ignored) {
        latestVersion = geyserApi().supportedBedrockVersions().stream()
                .max(Comparator.comparingInt(MinecraftVersion::protocolVersion))
                .orElseThrow(() -> new IllegalArgumentException("No versions available"));
        logger().info("BlockVersion is enabled! Blocking all versions except %s (%s) from connecting.".formatted(
                latestVersion.versionString(), latestVersion.protocolVersion()
        ));
    }

    @Subscribe
    public void onLogin(SessionLoginEvent event) {
        var session = ((GeyserSession) event.connection()).getUpstream();

        if (session.getProtocolVersion() != latestVersion.protocolVersion()) {
            event.setCancelled(true, GeyserLocale.getLocaleStringLog("geyser.network.outdated.client",
                    latestVersion.versionString()));
        }
    }
}

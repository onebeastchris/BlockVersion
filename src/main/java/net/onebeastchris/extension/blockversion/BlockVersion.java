package net.onebeastchris.extension.blockversion;

import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.bedrock.SessionLoginEvent;
import org.geysermc.geyser.api.extension.Extension;
import org.geysermc.geyser.network.GameProtocol;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.geyser.text.GeyserLocale;

public class BlockVersion implements Extension {

    @Subscribe
    public void onLogin(SessionLoginEvent event) {
        var session = ((GeyserSession) event.connection()).getUpstream();

        if (session.getProtocolVersion() != GameProtocol.DEFAULT_BEDROCK_CODEC.getProtocolVersion()) {
            event.setCancelled(true, GeyserLocale.getLocaleStringLog("geyser.network.outdated.client",
                    GameProtocol.DEFAULT_BEDROCK_CODEC.getMinecraftVersion()));
        }
    }
}

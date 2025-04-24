package io.github.saimonovski.smv.wallet.system.gems.cmd;

import io.github.saimonovski.smv.wallet.api.object.Config;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Command;
import org.jetbrains.annotations.NotNull;
@SuppressWarnings("unused")
public class GemCMD {
    private final Config config;

    public GemCMD(Config config) {
        this.config = config;
    }

    @Command("gemshop")
    public void run(final @NotNull Player player){
        config.mainGui().openInventory(player);
    }
    @Command("gemy")
    public void gemCMD(final @NotNull Player player){
        config.mainGui().openInventory(player);
    }
}

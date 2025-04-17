package io.github.saimonovski.smv.wallet.cmd;

import io.github.saimonovski.smv.wallet.api.object.Config;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Command;
import org.jetbrains.annotations.NotNull;

public class WalletCMD {
    private final Config config;

    public WalletCMD(Config config) {
        this.config = config;
    }

    @Command("portfel")
    public void run(final @NotNull Player player){
        config.mainGui().openInventory(player);
    }
}

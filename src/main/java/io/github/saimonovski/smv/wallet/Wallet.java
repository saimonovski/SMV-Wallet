package io.github.saimonovski.smv.wallet;

import io.github.saimonovski.smv.wallet.api.Database;
import io.github.saimonovski.smv.wallet.api.object.Config;
import io.github.saimonovski.smv.wallet.api.object.EconomyProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Wallet extends JavaPlugin implements io.github.saimonovski.smv.wallet.api.Wallet {


    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public @NotNull Config config() {
        return null;
    }

    @Override
    public @NotNull EconomyProvider provider() {
        return null;
    }

    @Override
    public @NotNull Database database() {
        return null;
    }
}

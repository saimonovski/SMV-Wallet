package io.github.saimonovski.smv.wallet;

import io.github.saimonovski.smv.wallet.api.Database;
import io.github.saimonovski.smv.wallet.api.object.Config;
import io.github.saimonovski.smv.wallet.api.object.EconomyProvider;
import io.github.saimonovski.smv.wallet.core.listeners.InventoryClickListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class Wallet extends JavaPlugin implements io.github.saimonovski.smv.wallet.api.Wallet {
  private io.github.saimonovski.smv.wallet.core.objects.Config config;
  private io.github.saimonovski.smv.wallet.core.objects.EconomyProvider provider;
  /*
  todo
  komendy
baza danych
econiomy providder instance
wiadomosci

make todos from project

make test
   */

    @Override
    public void onEnable() {
    getLogger().info("Ladowanie pluginu...");
    getLogger().info("Verse-Wallet by saimonovski");
    getLogger().info("Ladowanie eventow");
    getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
    getLogger().info("Laczenie z baza danych...");
    //todo
        getLogger().info("Ladowanie Economy Provider...");
        this.provider = new io.github.saimonovski.smv.wallet.core.objects.EconomyProvider(database());
        getLogger().info("Ladowanie komend...");
    getLogger().info("ladowanie configu...");
        try {
            this.config = new io.github.saimonovski.smv.wallet.core.objects.Config(this);
        } catch (IOException e) {
            getLogger().severe("Wystapil blad podczas ladowania configu. Sprawdz plik lub skontaktuj sie z tworca " +
                    "pluginu \n :: Wallet.java:26");
            getServer().getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
        getLogger().info("Wylaczanie pluginu");
        getLogger().info("Rozlaczanie z baza danych...");
        //todo
    }

    @Override
    public @NotNull Config config() {
        return this.config;
    }

    @Override
    public @NotNull EconomyProvider provider() {
        return this.provider;
    }

    @Override
    public @NotNull Database database() {
        return null;
    }
}

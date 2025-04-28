package io.github.saimonovski.smv.wallet;

import io.github.saimonovski.smv.wallet.api.object.Database;
import io.github.saimonovski.smv.wallet.api.object.Config;
import io.github.saimonovski.smv.wallet.api.object.EconomyProvider;
import io.github.saimonovski.smv.wallet.api.object.GemWallet;
import io.github.saimonovski.smv.wallet.cloud.CMDManager;
import io.github.saimonovski.smv.wallet.placeholderapi.GemBalance;
import io.github.saimonovski.smv.wallet.system.wallet.core.listeners.InventoryClickListener;
import io.github.saimonovski.smv.wallet.placeholderapi.PlayerBalance;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class Wallet extends JavaPlugin implements io.github.saimonovski.smv.wallet.api.Wallet, GemWallet {
  private io.github.saimonovski.smv.wallet.system.wallet.core.objects.Config config;
  private io.github.saimonovski.smv.wallet.system.wallet.core.objects.EconomyProvider provider;
  private Database database;
  private Database gemDatabase;
  private EconomyProvider gemProvider;
  private Config gemConfig;
  /*
make test
   */

    @Override
    public void onEnable() {
    getLogger().info("Ladowanie pluginu...");
    getLogger().info("Verse-Wallet by saimonovski");
        getLogger().info("ladowanie configow...");
        try {
            this.config = new io.github.saimonovski.smv.wallet.system.wallet.core.objects.Config(this);
        } catch (IOException e) {
            getLogger().severe("Wystapil blad podczas ladowania configu portfela. Sprawdz plik lub skontaktuj sie z " +
                    "tworca " +
                    "pluginu ");
            getServer().getPluginManager().disablePlugin(this);
        }
        try {
            this.gemConfig = new io.github.saimonovski.smv.wallet.system.gems.core.objects.Config(this);
        } catch (IOException e) {
            getLogger().severe("Wystapil blad podczas ladowania configu gemow. Sprawdz plik lub skontaktuj sie z " +
                    "tworca " +
                    "pluginu ");
            getServer().getPluginManager().disablePlugin(this);
        }
    getLogger().info("Ladowanie eventow");
    getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
    getLogger().info("Laczenie z baza danych...");
    this.database = new io.github.saimonovski.smv.wallet.system.wallet.core.objects.Database(this);
    this.gemDatabase = new io.github.saimonovski.smv.wallet.system.gems.core.objects.Database(this);
    this.database.connect();
    this.gemDatabase.connect();
        getLogger().info("Ladowanie Economy Provider...");
        this.provider = new io.github.saimonovski.smv.wallet.system.wallet.core.objects.EconomyProvider(database(), config);
        this.gemProvider = new io.github.saimonovski.smv.wallet.system.gems.core.objects.EconomyProvider(getGemDatabase(), getGemConfig());
        getLogger().info("Ladowanie komend...");
        new CMDManager(this);
        getLogger().info("Ladowanie Placeholderow...");
        new GemBalance(this).register();
        new PlayerBalance(this).register();
        getLogger().info("Plugin Wlaczony, milej zabawy, w razie pytan odwiedz https://saimverse.pl/");
    }

    @Override
    public void onDisable() {
        getLogger().info("Wylaczanie pluginu");
        getLogger().info("Rozlaczanie z baza danych...");
        this.database.disconnect();
        this.gemDatabase.disconnect();
        getLogger().info("Plugin wylaczony :(");

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
        return this.database;
    }
    @Override
    public Database getGemDatabase() {
        return gemDatabase;
    }
    @Override
    public EconomyProvider getGemProvider() {
        return gemProvider;
    }
    @Override
    public Config getGemConfig() {
        return gemConfig;
    }
}

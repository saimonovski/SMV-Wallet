package io.github.saimonovski.smv.wallet.system.wallet.core.objects;

import io.github.saimonovski.smv.wallet.api.Database;
import io.github.saimonovski.smv.wallet.messages.replacers.Replacer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class EconomyProvider implements io.github.saimonovski.smv.wallet.api.object.EconomyProvider {
 private final Database database;
 private final Config config;

    public EconomyProvider(Database database, Config config) {
        this.database = database;
        this.config = config;
    }

    @NotNull
    @Override
    public Database database() {
        return this.database;
    }

    @Override
    public void addBalance(UUID id, double amount) {
        io.github.saimonovski.smv.wallet.api.object.EconomyProvider.super.addBalance(id, amount);
      Player player = Bukkit.getOfflinePlayer(id).getPlayer();
      if(player != null){
          config.getMessage("money-give").send(player, Replacer.replaceAmount(amount));
      }
    }
}

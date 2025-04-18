package io.github.saimonovski.smv.wallet.api.buttons.instances;

import io.github.saimonovski.smv.wallet.api.Database;
import io.github.saimonovski.smv.wallet.api.Wallet;
import io.github.saimonovski.smv.wallet.api.buttons.Button;
import io.github.saimonovski.smv.wallet.api.object.Config;
import io.github.saimonovski.smv.wallet.api.object.EconomyProvider;
import io.github.saimonovski.smv.wallet.messages.replacers.Replacer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.function.Consumer;

public interface DailyButton extends Button {
  Wallet wallet();
  default Database database(){
      return wallet().database();
  }
    default EconomyProvider provider(){
        return wallet().provider();
    }
    double minAmount();
    double maxAmount();

    @NotNull
    @Override
    default Consumer<InventoryClickEvent> action(){
        return e ->{
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            if(!database().checkCooldown(player.getUniqueId())){
               wallet().config().getMessage("cooldown-message")
                       .send(player);
            return;
            }
            database().setOnCooldown(player.getUniqueId());
            double amount =new Random().nextDouble(minAmount(),maxAmount());
            provider().addBalance(player.getUniqueId(),amount);
            wallet().config().getMessage("daily-reward-message").send(player, Replacer.replaceAmount(amount));
        };
    }

}

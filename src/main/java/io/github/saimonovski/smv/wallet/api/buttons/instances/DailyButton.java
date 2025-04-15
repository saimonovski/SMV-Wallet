package io.github.saimonovski.smv.wallet.api.buttons.instances;

import io.github.saimonovski.smv.wallet.api.Database;
import io.github.saimonovski.smv.wallet.api.buttons.Button;
import io.github.saimonovski.smv.wallet.api.object.Config;
import io.github.saimonovski.smv.wallet.api.object.EconomyProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.function.Consumer;

public interface DailyButton extends Button {
    Database database();
    EconomyProvider provider();
    double minAmount();
    double maxAmount();

    @NotNull
    @Override
    default Consumer<InventoryClickEvent> action(){
        return e ->{
            Player player = (Player) e.getWhoClicked();
            if(!database().checkCooldown()){
                //todo send a message
            return;
            }
            provider().addBalance(player.getUniqueId(),new Random().nextDouble(minAmount(),maxAmount()));
            //todo send a message with success drop
        };
    }

}

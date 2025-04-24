package io.github.saimonovski.smv.wallet.api.buttons.instances;

import io.github.saimonovski.smv.wallet.api.buttons.Button;
import io.github.saimonovski.smv.wallet.api.object.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface BackButton extends Button {
    Config config();


    @NotNull
    @Override
   default Consumer<InventoryClickEvent> action(){
        return e -> {
            e.setCancelled(true);
          e.getWhoClicked().closeInventory();
          config().mainGui().openInventory((Player) e.getWhoClicked());
        };
    }
}

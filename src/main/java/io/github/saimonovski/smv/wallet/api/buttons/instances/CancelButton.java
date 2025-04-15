package io.github.saimonovski.smv.wallet.api.buttons.instances;

import io.github.saimonovski.smv.wallet.api.buttons.Button;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface CancelButton extends Button {
    @NotNull
    @Override
   default Consumer<InventoryClickEvent> action(){
        return e -> {
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
            //todo send a messahe
        };
    }
}

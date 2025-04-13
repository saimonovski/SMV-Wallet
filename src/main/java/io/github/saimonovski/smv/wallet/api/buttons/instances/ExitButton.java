package io.github.saimonovski.smv.wallet.api.buttons.instances;

import io.github.saimonovski.smv.wallet.api.buttons.Button;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface ExitButton extends Button {
    @Override
  default @NotNull Consumer<InventoryClickEvent> action(){
        return event -> event.getWhoClicked().closeInventory();
    }
}

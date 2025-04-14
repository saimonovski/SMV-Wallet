package io.github.saimonovski.smv.wallet.api.buttons.instances;

import io.github.saimonovski.smv.wallet.api.buttons.Button;
import io.github.saimonovski.smv.wallet.api.entity.Product;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.function.Consumer;

public interface ConfirmButton extends Button {
    Product productToBuy();

    @NotNull
    @Override
   default Consumer<InventoryClickEvent> action(){
        return event ->{
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            productToBuy().buy(event.getWhoClicked().getUniqueId());

        }
    }
}

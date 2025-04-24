package io.github.saimonovski.smv.wallet.api.buttons.instances;

import io.github.saimonovski.smv.wallet.api.buttons.Button;
import io.github.saimonovski.smv.wallet.api.object.Config;

import io.github.saimonovski.smv.wallet.messages.replacers.Replacer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface CancelButton extends Button {
    Config config();
    @NotNull
    @Override
   default Consumer<InventoryClickEvent> action(){
        return e -> {
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
            config().getMessage("cancelled-message").send((Player) e.getWhoClicked(),
                    Replacer.replacePlayer((Player) e.getWhoClicked()));
        };
    }
}

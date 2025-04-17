package io.github.saimonovski.smv.wallet.core.listeners;

import io.github.saimonovski.smv.wallet.api.entity.Gui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClickListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void handleClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getClickedInventory() == null ? null :
                event.getClickedInventory().getHolder(false);
        if(holder == null) return;
        if(!(holder instanceof Gui gui)) return;
        gui.handleClick(event);
    }

}

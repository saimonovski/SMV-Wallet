package io.github.saimonovski.smv.wallet.api.entity;

import io.github.saimonovski.smv.wallet.api.guis.InventoryButton;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

public interface Gui {
    void handleClick(InventoryClickEvent e);
   Gui addItem(int slot, InventoryButton button);

    Map<Integer, InventoryButton> getMap();
}

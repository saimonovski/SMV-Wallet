package io.github.saimonovski.smv.wallet.api.guis.util;

import io.github.saimonovski.smv.wallet.api.entity.Gui;
import io.github.saimonovski.smv.wallet.api.guis.InventoryButton;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.w3c.dom.css.CSSRule;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BackgroundItem {
    public static void fillBackGround(Material materialToFill, Inventory inventory, Gui gui){

        for (int i = 0; i < inventory.getSize(); i++) {
            if(!gui.getMap().containsKey(i)){
                gui.addItem(i, InventoryButton.of(e -> e.setCancelled(true),ItemStack.of(materialToFill)));
            }
        }
    }
}

package io.github.saimonovski.smv.wallet.api.guis.util;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BackgroundItem {
    public static void fillBackGround(Material materialToFill, Inventory inventory){

        for (int i = 0; i < inventory.getContents().length; i++) {
            if(Objects.requireNonNull(inventory.getItem(i)).getType().isEmpty()){
                inventory.setItem(i,ItemStack.of(materialToFill));
            }
        }
    }
}

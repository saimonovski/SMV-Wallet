package io.github.saimonovski.smv.wallet.api.guis;

import io.github.saimonovski.smv.wallet.api.entity.Category;
import io.github.saimonovski.smv.wallet.api.entity.Gui;
import io.github.saimonovski.smv.wallet.api.entity.Product;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CategoryGui implements InventoryHolder, Gui {
    private final Inventory inventory;
    private final Map<Integer,InventoryButton> buttonMap;
    private CategoryGui(int size, Component title, Map<Integer,InventoryButton> map){
        this.inventory = Bukkit.createInventory(null,size,title);
        this.buttonMap = map;
    }
    @Override
    public @NotNull Inventory getInventory() {
        this.buttonMap.forEach((key, value) -> value.decorate(key, this.inventory));
        return this.inventory;
    }
   @Override
    public void handleClick(InventoryClickEvent e){
        int slot = e.getRawSlot();
        InventoryButton button = buttonMap.get(slot);
        if(button == null) return;
        button.action(e);
    }
    public CategoryGui addItem(int slot, InventoryButton button){
        this.buttonMap.put(slot,button);
        return this;
    }
    public void openInventory(Player player){
        player.openInventory(this.getInventory());
    }

    public static Builder builder(){
        return new Builder();
    }
    public static class Builder{
        private Component title;
        private int size;
        private final Map<Integer,InventoryButton> buttons = new HashMap<>();
        public Builder addButton(int slot, InventoryButton button){
            this.buttons.put(slot,button);
            return this;
        }
        public Builder removeButton(int slot, InventoryButton button){
            this.buttons.put(slot,button);
            return this;
        }
        public Builder addProduct(Product product){
            return
            addButton(product.slot(),InventoryButton.of(
               event -> {
                   // TODO: 09.04.2025 send  a message & open Confirm Gui
               }, product.itemStack()));
        }

        public Builder setTitle(Component title) {
            this.title = title;
            return this;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }
        public CategoryGui build(){
            return new CategoryGui(this.size % 9 != 0 ? 54 : this.size,this.title,this.buttons);
        }
    }
}

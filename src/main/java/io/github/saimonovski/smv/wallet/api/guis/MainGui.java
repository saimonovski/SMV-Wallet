package io.github.saimonovski.smv.wallet.api.guis;

import io.github.saimonovski.smv.wallet.api.entity.Category;
import io.github.saimonovski.smv.wallet.api.entity.Gui;
import io.github.saimonovski.smv.wallet.api.guis.util.BackgroundItem;
import io.github.saimonovski.smv.wallet.core.utils.SerializeUtils;
import io.github.saimonovski.smv.wallet.messages.replacers.Replacer;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MainGui implements InventoryHolder, Gui {
    private final Inventory inventory;
    private final Map<Integer,InventoryButton> buttonMap;
    private final Material material;
    private MainGui(int size, Component title, Map<Integer,InventoryButton> map, Material material){
        this.inventory = Bukkit.createInventory(this,size,title);
        this.buttonMap = map;
        this.material = material;
    }
    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public Map<Integer, InventoryButton> getMap() {
        return this.buttonMap;
    }

    @Override
    public void handleClick(InventoryClickEvent e){
        int slot = e.getRawSlot();
        InventoryButton button = buttonMap.get(slot);
        if(button == null) return;
        button.action(e);
    }
    @Override
    public MainGui addItem(int slot,InventoryButton button){
        this.buttonMap.put(slot,button);
        return this;
    }

    public void openInventory(Player player){
        BackgroundItem.fillBackGround(this.material,this.inventory, this);
        this.buttonMap.forEach((key, value) -> value.decorate(key, this.inventory, player));

        player.openInventory(getInventory());
    }

    public static Builder builder(){
        return new Builder();
    }
    public static class Builder{
        private Component title;
        private int size;
        private final Map<Integer,InventoryButton> buttons = new HashMap<>();
        private Material material;

        public Builder addButton(int slot, InventoryButton button){
            this.buttons.put(slot,button);
            return this;
        }
        public Builder removeButton(int slot, InventoryButton button){
            this.buttons.put(slot,button);
            return this;
        }
        public Builder addCategory(Category category){
            return
            addButton(category.slot(),InventoryButton.of(
                category.shouldOpenGui() ? e-> category.openGui((Player) e.getWhoClicked()) :
                        e -> e.setCancelled(true), category.itemStack()));
        }

        public Builder setTitle(Component title) {
            this.title = title;
            return this;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }
        public MainGui build(){
            return new MainGui(this.size % 9 != 0 ? 54 : this.size,this.title,this.buttons, this.material);
        }

        public Builder setFillMaterial(Material material) {
            this.material = material;
            return this;
        }
    }
}

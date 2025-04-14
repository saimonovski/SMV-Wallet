package io.github.saimonovski.smv.wallet.api.guis;

import io.github.saimonovski.smv.wallet.api.buttons.Button;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class InventoryButton  {
    private final Consumer<InventoryClickEvent> consumer;
    private final ItemStack itemStack;

    private InventoryButton(Consumer<InventoryClickEvent> consumer, ItemStack itemStack) {
        this.consumer = consumer;
        this.itemStack = itemStack;
    }
    public static InventoryButton of(Consumer<InventoryClickEvent> consumer, ItemStack itemStack){
        return new InventoryButton(consumer, itemStack);
    }
    public static InventoryButton empty(ItemStack itemStack){
        return new InventoryButton(e -> {}, itemStack);
    }

    public void action(InventoryClickEvent e) {
        this.consumer.accept(e);
    }
    public void decorate(int slot, Inventory inventory){
        inventory.setItem(slot,this.itemStack);
    }


}

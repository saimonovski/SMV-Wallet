package io.github.saimonovski.smv.wallet.api.guis;

import io.github.saimonovski.smv.wallet.messages.ChatUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class InventoryButton  {
    private final Consumer<InventoryClickEvent> consumer;
    private  ItemStack itemStack;

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
    public void decorate(int slot, Inventory inventory, Player player){
        ItemStack item = this.itemStack.clone();
        item.editMeta(meta -> {
            if(meta.displayName() != null){
                meta.displayName(ChatUtil.fix(PlaceholderAPI.setPlaceholders(player,
                        MiniMessage.miniMessage().serialize(meta.displayName()))));
            }
            if(meta.lore() != null){
                List<Component> newLore = new ArrayList<>();
                meta.lore().forEach(comp -> {
                    newLore.add(ChatUtil.fix(PlaceholderAPI.setPlaceholders(player,
                            MiniMessage.miniMessage().serialize(comp))));
                });
               meta.lore(newLore);
            }
        });
        inventory.setItem(slot,item);
    }
    public InventoryButton setItemStack(ItemStack newItemStack){
        this.itemStack = newItemStack;
        return this;
    }



}

package io.github.saimonovski.smv.wallet.api.guis;

import io.github.saimonovski.smv.wallet.messages.ChatUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
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
    @SuppressWarnings("all")
    public void decorate(int slot, Inventory inventory, Player player){
        ItemStack item = this.itemStack.clone();
        if(slot > inventory.getSize() || slot < 0){
            Bukkit.getLogger().warning("[SMV-PORTFEL] Wprowadzono nieprawidlowy slot: "+slot);
            return;
        }
        item.editMeta(meta -> {
            if(meta.hasDisplayName()){
                meta.displayName(ChatUtil.fix(PlaceholderAPI.setPlaceholders(player,
                        MiniMessage.miniMessage().serialize(meta.displayName()))));
            }
            if(meta.hasLore()){
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

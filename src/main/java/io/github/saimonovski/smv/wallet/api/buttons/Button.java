package io.github.saimonovski.smv.wallet.api.buttons;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface Button {
   @NotNull Consumer<InventoryClickEvent> action();
   int slot();
   ItemStack itemStack();
}

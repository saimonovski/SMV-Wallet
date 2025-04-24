package io.github.saimonovski.smv.wallet.api.buttons;

import io.github.saimonovski.smv.wallet.api.guis.InventoryButton;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface Button {
   @NotNull Consumer<InventoryClickEvent> action();

   ItemStack itemStack();
   int slot();
   default InventoryButton build(){
      return InventoryButton.of(action(),itemStack());
   }

}

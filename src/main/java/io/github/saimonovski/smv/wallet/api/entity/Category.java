package io.github.saimonovski.smv.wallet.api.entity;

import io.github.saimonovski.smv.wallet.api.Wallet;
import io.github.saimonovski.smv.wallet.api.guis.CategoryGui;
import io.github.saimonovski.smv.wallet.api.object.Config;
import io.github.saimonovski.smv.wallet.system.wallet.core.utils.SerializeUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Category {

  default void openGui(Player player){
      CategoryGui.Builder builder = CategoryGui.builder()
              .setTitle(title())
              .setConfig(config())
              .setMaterial(SerializeUtils.loadFillMaterial(config().configFile().getSection("categories."+id())))
              .addButton(config().backButton().slot(),config().backButton().build())
              .setSize(size());
      products().forEach(builder::addProduct);
      builder.build().openInventory(player);
  }
    int slot();
   @NotNull String id();
   int size();
    @NotNull Component title();
   @NotNull List<Product> products();
   @NotNull
    Config config();
   default boolean shouldOpenGui(){
       return !products().isEmpty();
   }

   @NotNull ItemStack itemStack();
}

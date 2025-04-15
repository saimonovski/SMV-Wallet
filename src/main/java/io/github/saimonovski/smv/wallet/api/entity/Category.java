package io.github.saimonovski.smv.wallet.api.entity;

import io.github.saimonovski.smv.wallet.api.Wallet;
import io.github.saimonovski.smv.wallet.api.guis.CategoryGui;
import io.github.saimonovski.smv.wallet.api.guis.MainGui;
import io.github.saimonovski.smv.wallet.api.object.Config;
import io.github.saimonovski.smv.wallet.core.utils.SerializeUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.block.data.type.Wall;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public interface Category {
    Wallet wallet();
  default void openGui(Player player){
      CategoryGui.Builder builder = CategoryGui.builder()
              .setTitle(title())
              .setConfig(wallet().config())
              .setMaterial(SerializeUtils.loadFillMaterial(wallet().config().configFile().getSection("categories."+id())))
              .setSize(size());
      products().forEach(builder::addProduct);

      builder.build().openInventory(player);
  }
    int slot();
   @NotNull String id();
   int size();
    @NotNull Component title();
   @NotNull List<Product> products();
   default boolean shouldOpenGui(){
       return !products().isEmpty();
   }

   @NotNull ItemStack itemStack();
}

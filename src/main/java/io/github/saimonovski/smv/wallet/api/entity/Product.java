package io.github.saimonovski.smv.wallet.api.entity;

import io.github.saimonovski.smv.wallet.api.object.EconomyProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public interface Product {
    double price();
    @NotNull String id();
   @NotNull ItemStack itemStack();
    int slot();
   @NotNull List<String> commands();
  @NotNull EconomyProvider getProvider();
    default void executeCommands(Player player){
        commands().forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command.replace("%player%",
                player.getName())));
    }
    default boolean buy(UUID id){
        if(getProvider().getBalance(id) < price()) return false;
        getProvider().removeBalance(id,price());
        return true;
    }
}

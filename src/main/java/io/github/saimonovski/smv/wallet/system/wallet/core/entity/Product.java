package io.github.saimonovski.smv.wallet.system.wallet.core.entity;

import io.github.saimonovski.smv.wallet.api.object.EconomyProvider;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class Product implements io.github.saimonovski.smv.wallet.api.entity.Product {
    private final int slot;
    private final double price;
  @NotNull  private final String id;
   @NotNull private final List<String> commands;
   @NotNull private final EconomyProvider provider;
   @NotNull private final ItemStack itemStack;

    public Product(double price, int slot, @NotNull String id, @NotNull List<String> commands,
                   @NotNull EconomyProvider provider, @NotNull ItemStack itemStack) {
        this.price = price;
        this.slot = slot;
        this.id = id;
        this.commands = commands;
        this.provider = provider;
        this.itemStack = itemStack;
    }

    @Override
    public boolean checkEnoughMoney(UUID uniqueId) {
        double amount = this.provider.getBalance(uniqueId);

        return amount >= price;
    }

    @Override
    public double price() {
        return this.price;
    }

    @NotNull
    @Override
    public String id() {
        return this.id;
    }

    @NotNull
    @Override
    public ItemStack itemStack() {
        return this.itemStack;
    }

    @Override
    public int slot() {
        return this.slot;
    }

    @NotNull
    @Override
    public List<String> commands() {
        return this.commands;
    }

    @NotNull
    @Override
    public EconomyProvider getProvider() {
        return this.provider;
    }

    public static class Builder{
        private  int  slot;
        private double price;
        private  String id;
        private  List<String> commands;
        private  EconomyProvider provider;
        private  ItemStack itemStack;

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setSlot(int slot) {
            this.slot = slot;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setCommands(List<String> commands) {
            this.commands = commands;
            return this;
        }

        public Builder setProvider(EconomyProvider provider) {
            this.provider = provider;
            return this;
        }

        public Builder setItemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
            return this;
        }

        public static Builder builder(){
            return new Builder();
        }
        public Product build(){
            return new Product(this.price,this.slot,this.id,this.commands,this.provider,this.itemStack);
        }

    }
}

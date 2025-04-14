package io.github.saimonovski.smv.wallet.core.entity;

import io.github.saimonovski.smv.wallet.api.entity.Product;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Category implements io.github.saimonovski.smv.wallet.api.entity.Category {
    private final int slot, size;
  @NotNull  private final String id;
  @NotNull  private final Component title;
   @NotNull private final List<Product> products;
  @NotNull  private final ItemStack itemStack;

    public Category(int slot, int size, @NotNull String id, @NotNull Component title, @NotNull List<Product> products, @NotNull ItemStack itemStack) {
        this.slot = slot;
        this.size = size;
        this.id = id;
        this.title = title;
        this.products = products;
        this.itemStack = itemStack;
    }

    @Override
    public int slot() {
        return this.slot;
    }

    @NotNull
    @Override
    public String id() {
        return this.id;
    }

    @Override
    public int size() {
        return this.size;
    }

    @NotNull
    @Override
    public Component title() {
        return this.title;
    }

    @NotNull
    @Override
    public List<Product> products() {
        return this.products;
    }

    @NotNull
    @Override
    public ItemStack itemStack() {
        return this.itemStack;
    }
    public static class Builder{
        public static Builder builder(){
            return new Builder();
        }
         private int slot, size;
         private String id;
        private Component title;
         private  List<Product> products;
          private  ItemStack itemStack;

        public Builder setSlot(int slot) {
            this.slot = slot;
            return this;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(Component title) {
            this.title = title;
            return this;
        }

        public Builder setProducts(List<Product> products) {
            this.products = products;
            return this;
        }

        public Builder setItemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
            return this;
        }
        public Category build(){
            return new Category(this.slot,this.size,this.id,this.title,this.products,this.itemStack);
        }
    }
}

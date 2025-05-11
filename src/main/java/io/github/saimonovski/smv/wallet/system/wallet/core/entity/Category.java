package io.github.saimonovski.smv.wallet.system.wallet.core.entity;

import io.github.saimonovski.smv.wallet.api.Wallet;
import io.github.saimonovski.smv.wallet.api.entity.Product;
import io.github.saimonovski.smv.wallet.api.object.Config;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record Category(int slot, int size, @NotNull String id, @NotNull Component title,
                       @NotNull List<Product> products, @NotNull ItemStack itemStack,
                       Config config) implements io.github.saimonovski.smv.wallet.api.entity.Category {
    public static class Builder {
        private Config config;

        public static Category.Builder builder() {
            return new Category.Builder();
        }

        private int slot, size;
        private String id;
        private Component title;
        private List<Product> products;
        private ItemStack itemStack;

        public Category.Builder setSlot(int slot) {
            this.slot = slot;
            return this;
        }

        public Category.Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Category.Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Category.Builder setTitle(Component title) {
            this.title = title;
            return this;
        }

        public Category.Builder setProducts(List<Product> products) {
            this.products = products;
            return this;
        }

        public Category.Builder setConfig(Config config) {
            this.config = config;
            return this;
        }

        public Category.Builder setItemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
            return this;
        }

        public Category build() {
            return new Category(this.slot, this.size, this.id, this.title, this.products, this.itemStack, this.config);
        }
    }
}

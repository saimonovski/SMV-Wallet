package io.github.saimonovski.smv.wallet.placeholderapi;

import io.github.saimonovski.smv.wallet.api.Wallet;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;

public class PlayerBalance extends PlaceholderExpansion {
    private final Wallet wallet;

    public PlayerBalance(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "portfel";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Saimonovski";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @NotNull String getName() {
        return "balance";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        double balance = wallet.provider().getBalance(player.getUniqueId());
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(balance);
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        double balance = wallet.provider().getBalance(player.getUniqueId());
        DecimalFormat df = new DecimalFormat("#.##");
           return df.format(balance);
    }
}

package io.github.saimonovski.smv.wallet.placeholderapi;

import io.github.saimonovski.smv.wallet.Wallet;
import io.github.saimonovski.smv.wallet.api.object.GemWallet;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;

public class GemBalance extends PlaceholderExpansion {
    private final GemWallet wallet;

    public GemBalance(GemWallet wallet) {
        this.wallet = wallet;
    }
    @Override
    public @NotNull String getIdentifier() {
        return "gem";
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
        double balance = wallet.getGemProvider().getBalance(player.getUniqueId());
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(balance);
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        double balance = wallet.getGemProvider().getBalance(player.getUniqueId());
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(balance);
    }
}

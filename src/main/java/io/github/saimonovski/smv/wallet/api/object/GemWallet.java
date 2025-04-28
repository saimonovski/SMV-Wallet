package io.github.saimonovski.smv.wallet.api.object;

public interface GemWallet {
    Database getGemDatabase();

    EconomyProvider getGemProvider();

    Config getGemConfig();
}

package io.github.saimonovski.smv.wallet.api.object;

import io.github.saimonovski.smv.wallet.api.Database;

public interface GemWallet {
    Database getGemDatabase();

    EconomyProvider getGemProvider();

    Config getGemConfig();
}

package io.github.saimonovski.smv.wallet.api;

import io.github.saimonovski.smv.wallet.api.object.Config;
import io.github.saimonovski.smv.wallet.api.object.EconomyProvider;
import org.jetbrains.annotations.NotNull;

public interface Wallet {
   @NotNull Database database();
   @NotNull EconomyProvider provider();
   @NotNull Config config();
   
}

package io.github.saimonovski.smv.wallet.core.objects;

import io.github.saimonovski.smv.wallet.api.Database;
import org.jetbrains.annotations.NotNull;

public class EconomyProvider implements io.github.saimonovski.smv.wallet.api.object.EconomyProvider {
 private final Database database;

    public EconomyProvider(Database database) {
        this.database = database;
    }

    @NotNull
    @Override
    public Database database() {
        return this.database;
    }
}

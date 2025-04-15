package io.github.saimonovski.smv.wallet.api;

import java.util.UUID;

public interface Database {
    boolean connect();
    boolean disconnect();
    void initalizeTables();
    double getBalance(UUID id);
    void setBalance(UUID id, double newBalance);

    void addBalance(UUID id, double amount);

    void removeBalance(UUID id, double amount);

    boolean checkCooldown();
}

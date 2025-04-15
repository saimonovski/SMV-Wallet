package io.github.saimonovski.smv.wallet.api;

import java.sql.SQLException;
import java.util.UUID;

public interface Database {
    boolean connect();
    boolean disconnect();
    void initalizeTables() throws SQLException;
    double getBalance(UUID id);
    void setBalance(UUID id, double newBalance);

    void addBalance(UUID id, double amount);

    void removeBalance(UUID id, double amount);



    boolean checkCooldown(UUID id);

    void setOnCooldown(UUID id);
}

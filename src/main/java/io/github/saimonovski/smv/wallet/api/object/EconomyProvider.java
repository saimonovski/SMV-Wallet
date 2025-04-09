package io.github.saimonovski.smv.wallet.api.object;

import io.github.saimonovski.smv.wallet.api.Database;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface EconomyProvider {
   default double getBalance(UUID id){
      return database().getBalance(id);
   }
    default void addBalance(UUID id, double amount){
       database().addBalance(id, amount);
    }
  default void removeBalance(UUID id, double amount){
       database().removeBalance(id,amount);
  }
   default void setBalance(UUID id, double newBalance){
       database().setBalance(id,newBalance);
    }
    @NotNull Database database();

}

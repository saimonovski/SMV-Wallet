package io.github.saimonovski.smv.wallet.api.object;

import io.github.saimonovski.smv.wallet.api.buttons.instances.DailyButton;
import io.github.saimonovski.smv.wallet.api.entity.Category;
import io.github.saimonovski.smv.wallet.api.entity.Product;
import io.github.saimonovski.smv.wallet.api.guis.MainGui;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Config {
    @NotNull
    String databaseHostName();

    @NotNull String databaseName();
    @NotNull String databaseUserName();
    @NotNull String databasePassword();
    int databasePort();
    default String databaseTablePrefix(){
        return "portfel_";
    }
   @NotNull Material fillMaterial();
    @NotNull String currencySymbol();
    @NotNull Product getProduct(String productId);
    @NotNull MainGui mainGui();
    @NotNull List<Category> categories();
    @NotNull Category getCategory(String categoryId);
    @NotNull  String getMainGuiTitle();
    int getMainGuiSize();
    @NotNull DailyButton button();
   @NotNull YamlConfiguration configFile();


}

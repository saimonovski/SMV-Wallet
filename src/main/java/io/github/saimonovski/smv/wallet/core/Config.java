package io.github.saimonovski.smv.wallet.core;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import dev.dejvokep.boostedyaml.spigot.SpigotSerializer;
import io.github.saimonovski.smv.wallet.Wallet;
import io.github.saimonovski.smv.wallet.api.Database;
import io.github.saimonovski.smv.wallet.api.buttons.instances.*;
import io.github.saimonovski.smv.wallet.api.entity.Category;
import io.github.saimonovski.smv.wallet.api.entity.Product;
import io.github.saimonovski.smv.wallet.api.guis.MainGui;
import io.github.saimonovski.smv.wallet.api.object.EconomyProvider;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.saimonovski.smv.wallet.core.utils.SerializeUtils.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Config implements io.github.saimonovski.smv.wallet.api.object.Config {
    private final Wallet wallet;
    private final YamlDocument config;

    public Config(Wallet wallet) throws IOException {
        this.wallet = wallet;
        this.config =
             YamlDocument.create(new File(wallet.getDataFolder(),"config.yml"),
                     Objects.requireNonNull(wallet.getResource("config.yml")),
                     GeneralSettings.builder().setSerializer(SpigotSerializer.getInstance()).build(),
                     LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build());
    }

    @NotNull
    @Override
    public String databaseHostName() {
        Section section = databaseSection(configFile());
        return section == null ? "192.168.1.1" : section.getString("host", "192.168.120");
    }

    @NotNull
    @Override
    public String databaseName() {
        Section section = databaseSection(configFile());
        return section == null ? "user" : section.getString("user-name", "name");
    }

    @NotNull
    @Override
    public String databaseUserName() {
        Section section = databaseSection(configFile());
        return section == null ? "database" : section.getString("database-name", "name");
    }

    @NotNull
    @Override
    public String databasePassword() {
        Section section = databaseSection(configFile());
        return section == null ? "password" : section.getString("password", "password");
    }

    @Override
    public int databasePort() {
        Section section = databaseSection(configFile());
        return section == null ? 3306 : section.getInt("port", 3306);    }

    @NotNull
    @Override
    public Material fillMaterial() {
        return loadFillMaterial(configFile().getSection("main-gui"));
    }

    @NotNull
    @Override
    public String currencySymbol() {
        return configFile().getString("currency-symbol", "vPLN");
    }

    @NotNull
    @Override
    public Product getProduct(String productId) {
        Section productSect = configFile().getSection("items");
        return
        io.github.saimonovski.smv.wallet.core.entity.Product.Builder.builder()
                .setCommands(productSect.getStringList("commands", new ArrayList<>()))
                .setItemStack(loadItemStack(productSect))
                .setPrice(productSect.getDouble("price", 0.0))
                .setProvider(this.wallet.provider())
                .setId(productId).build();
    }

    @Override
    public @NotNull MainGui mainGui() {
        MainGui.Builder gui = MainGui.builder()
                .setSize(getMainGuiSize() %9 == 0 ? getMainGuiSize() : 54)
                .setTitle(getMainGuiTitle())
                .setFillMaterial(loadFillMaterial(configFile().getSection("main-gui")))
                .addButton(dailyButton().slot(), dailyButton().build())
                        .addButton(exitButton().slot(), exitButton().build());

        categories().forEach(gui::addCategory);
        return gui.build();
    }

    @NotNull
    @Override
    public List<Category> categories() {
        List<Category> categories = new ArrayList<>();
        configFile().getSection("categories").getKeys().forEach(key -> {
            categories.add(getCategory((String) key));
        });
        return categories;
    }

    @NotNull
    @Override
    public Category getCategory(String categoryId) {
        Section section = configFile().getSection("categories").getSection(categoryId);
        int slot = loadSlot(section);
        int size = loadSize(section);
        List<Product> products = new ArrayList<>();
        ItemStack itemStack = loadItemStack(section);
        List<String> itemIds = section.getStringList("contains-items", new ArrayList<>());
        itemIds.forEach(str -> products.add(getProduct(str)));

        return io.github.saimonovski.smv.wallet.core.entity.Category.Builder
                .builder()
                .setId(categoryId)
                .setTitle(loadTitle(section))
                .setItemStack(itemStack)
                .setProducts(products)
                .setSlot(slot)
                .setSize(size)
                .setWallet(this.wallet)
                .build();
    }

    @NotNull
    @Override
    public Component getMainGuiTitle() {
        return loadTitle(configFile().getSection("main-gui"));
    }

    @Override
    public int getMainGuiSize() {
        return loadSize(configFile().getSection("main-gui"));
    }



    @NotNull
    @Override
    public YamlDocument configFile() {
        return this.config;
    }

    @NotNull
    @Override
    public ConfirmButton confirmButton() {
        return new ConfirmButton() {
            private Product product;
            @Nullable
            @Override
            public Product productToBuy() {
                return this.product;
            }

            @Override
            public void setProduct(Product product) {
                this.product = product;
            }

            @Override
            public ItemStack itemStack() {
                return loadItemStack(configFile().getSection("confirm-gui.confirm-button"));
            }

            @Override
            public int slot() {
                return loadSlot(configFile().getSection("confirm-gui.confirm-button"));
            }
        };
    }

    @NotNull
    @Override
    public BackButton backButton() {
        return new BackButton() {
            @Override
            public io.github.saimonovski.smv.wallet.api.object.Config config() {
                return wallet.config();
            }


            @Override
            public ItemStack itemStack() {
                return loadItemStack(configFile().getSection("return-button"));
            }

            @Override
            public int slot() {
                return loadSlot(configFile().getSection("return-button"));
            }
        };
    }

    @NotNull
    @Override
    public CancelButton cancelButton() {
        return new CancelButton() {
            @Override
            public ItemStack itemStack() {
                return loadItemStack(configFile().getSection("confirm-gui.cancel-button"));
            }

            @Override
            public int slot() {
                return loadSlot(configFile().getSection("confirm-gui.cancel-button"));

            }
        };
    }

    @NotNull
    @Override
    public DailyButton dailyButton() {
        return new DailyButton() {
            @Override
            public Database database() {
                return wallet.database();
            }

            @Override
            public EconomyProvider provider() {
                return wallet.provider();
            }

            @Override
            public double minAmount() {
                return configFile().getSection("daily-reward").getDouble("min-amount", 0.0);
            }

            @Override
            public double maxAmount() {
                return configFile().getSection("daily-reward").getDouble("max-amount", 0.0);
            }

            @Override
            public ItemStack itemStack() {
                return loadItemStack(configFile().getSection("daily-reward"));}

            @Override
            public int slot() {
                return loadSlot(configFile().getSection("daily-reward"));}

        };
    }

    @NotNull
    @Override
    public ExitButton exitButton() {
        return new ExitButton() {
            @Override
            public ItemStack itemStack() {
                return loadItemStack(configFile().getSection("exit-button"));
            }

            @Override
            public int slot() {
                return loadSlot(configFile().getSection("exit-button"));

            }
        };
    }
}

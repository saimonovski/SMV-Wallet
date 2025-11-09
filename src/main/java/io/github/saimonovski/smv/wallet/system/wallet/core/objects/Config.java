package io.github.saimonovski.smv.wallet.system.wallet.core.objects;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import dev.dejvokep.boostedyaml.spigot.SpigotSerializer;
import io.github.saimonovski.smv.wallet.Wallet;
import io.github.saimonovski.smv.wallet.api.object.Database;
import io.github.saimonovski.smv.wallet.api.buttons.instances.*;
import io.github.saimonovski.smv.wallet.api.entity.Category;
import io.github.saimonovski.smv.wallet.api.entity.Product;
import io.github.saimonovski.smv.wallet.api.guis.MainGui;
import io.github.saimonovski.smv.wallet.api.object.EconomyProvider;
import io.github.saimonovski.smv.wallet.messages.Message;
import io.github.saimonovski.smv.wallet.messages.MessageType;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.saimonovski.smv.wallet.system.wallet.core.utils.SerializeUtils.*;
import static io.github.saimonovski.smv.wallet.messages.ChatUtil.fix;

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
                      DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build());
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
    public Product getProduct(String productId, int productSlot) {
        Section productSect = configFile().getSection("items."+productId);
        return
        io.github.saimonovski.smv.wallet.system.wallet.core.entity.Product.Builder.builder()
                .setCommands(productSect.getStringList("commands", new ArrayList<>()))
                .setItemStack(loadItemStack(productSect))
                .setSlot(productSlot)
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


        Section itemsSection = section.getSection("contains-items");

        if (itemsSection != null && !itemsSection.getKeys().isEmpty()) {
            itemsSection.getKeys().forEach(productIdObject -> {
                if (productIdObject instanceof String productId) {
                    int productSlot = itemsSection.getInt(productId, -1);

                    if (productSlot >= 0) {
                        try {
                            Product product = getProduct(productId, productSlot);
                            products.add(product);
                        } catch (Exception e) {
                            System.err.println("Error loading product '" + productId + "' for category '" + categoryId + "': " + e.getMessage());
                            e.printStackTrace();
                        }
                    } else {
                        System.err.println("Warning: Invalid slot '" + itemsSection.get(productId) + "' defined for product '" + productId + "' in category '" + categoryId + "'");
                    }
                }
            });
        }

        return io.github.saimonovski.smv.wallet.system.wallet.core.entity.Category.Builder
                .builder()
                .setId(categoryId)
                .setTitle(loadTitle(section))
                .setItemStack(itemStack)
                .setProducts(products)
                .setSlot(slot)
                .setSize(size)
                .setConfig(this)
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

    @Override
    public boolean useMysql() {
        return configFile().getBoolean("database.use-mysql", false);
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
            public io.github.saimonovski.smv.wallet.api.object.Config config() {
                return wallet.config();
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
            public List<Integer> slots() {
                return configFile().getSection("confirm-gui.confirm-button").getIntList("slot",List.of(0));
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
            public io.github.saimonovski.smv.wallet.api.object.Config config() {
                return wallet.config();
            }

            @Override
            public ItemStack itemStack() {
                return loadItemStack(configFile().getSection("confirm-gui.cancel-button"));
            }

            @Override
            public List<Integer> slots() {
                return configFile().getSection("confirm-gui.cancel-button").getIntList("slot",List.of(0));
            }
        };
    }

    @NotNull
    @Override
    public DailyButton dailyButton() {
        return new DailyButton() {
            @Override
            public io.github.saimonovski.smv.wallet.api.Wallet wallet() {
                return wallet;
            }

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

    @Override
    public Message getMessage(String path){
        Section sec = configFile().getSection("messages");
        Component message = fix(sec.getString(path+".message", ""));
        MessageType type = sec.getEnum(path+".type", MessageType.class, MessageType.DISABLED);
        return new Message(message,type);
    }
}

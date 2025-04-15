package io.github.saimonovski.smv.wallet.api.guis;

import io.github.saimonovski.smv.wallet.api.buttons.instances.CancelButton;
import io.github.saimonovski.smv.wallet.api.buttons.instances.ConfirmButton;
import io.github.saimonovski.smv.wallet.api.entity.Gui;
import io.github.saimonovski.smv.wallet.api.entity.Product;
import io.github.saimonovski.smv.wallet.api.guis.util.BackgroundItem;
import io.github.saimonovski.smv.wallet.api.object.Config;
import io.github.saimonovski.smv.wallet.core.utils.SerializeUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ConfirmGui implements InventoryHolder, Gui {

    private final Config config;
    Map<Integer,InventoryButton> buttons = new HashMap<>();


    public ConfirmGui(Product productToBuy, Config config) {
        ConfirmButton confirmButton = config.confirmButton();
        confirmButton.setProduct(productToBuy);
        CancelButton cancelButton = config.cancelButton();
        int itemSlot = config.configFile().getInt("confirm-gui.item-slot", 0);
        buttons.put(itemSlot,InventoryButton.of(e -> e.setCancelled(true),productToBuy.itemStack()));
        buttons.put(confirmButton.slot(),InventoryButton.of(confirmButton.action(),
                confirmButton.itemStack()));
        buttons.put(cancelButton.slot(),InventoryButton.of(cancelButton.action(),
                cancelButton.itemStack()));
        this.config = config;

    }
@Override
    public void handleClick(@NotNull InventoryClickEvent e){
        int slot = e.getRawSlot();
        InventoryButton button = this.buttons.get(slot);
        if(button == null) return;
        button.action(e);
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null,
                SerializeUtils.loadSize(this.config.configFile().getSection("confirm-gui")),
                SerializeUtils.loadTitle(this.config.configFile().getSection("confirm-gui")));
        buttons.forEach((key, value) -> value.decorate(key, inventory));
        BackgroundItem.fillBackGround(SerializeUtils.loadFillMaterial(config.configFile().getSection("confirm-gui")),
                inventory);
        return inventory;
    }
    public void openInventory(Player player){
        player.openInventory(this.getInventory());
    }
}

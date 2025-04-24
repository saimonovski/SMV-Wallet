package io.github.saimonovski.smv.wallet.api.buttons.instances;

import io.github.saimonovski.smv.wallet.api.buttons.Button;
import io.github.saimonovski.smv.wallet.api.entity.Product;
import io.github.saimonovski.smv.wallet.api.object.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import static io.github.saimonovski.smv.wallet.messages.replacers.Replacer.*;

public interface ConfirmButton extends Button {
    Product productToBuy();
    Config config();

    @NotNull
    @Override
   default Consumer<InventoryClickEvent> action(){
        return event ->{
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
           if(! productToBuy().buy(event.getWhoClicked().getUniqueId())){
            config().getMessage("not-enough-money").send((Player) event.getWhoClicked(),replacePlayer((Player) event.getWhoClicked()));
           }
            config().getMessage("product-buy").send((Player) event.getWhoClicked(),
                    replaceItemName(productToBuy().itemStack()),
                    replaceAmount(productToBuy().price()),
                    replacePlayer((Player) event.getWhoClicked())
                    );
        };
    }
    void setProduct(Product product);
}

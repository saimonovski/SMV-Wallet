package io.github.saimonovski.smv.wallet.messages;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.function.Consumer;
import java.util.function.Function;

public enum MessageType {
    ACTIONBAR(player -> message -> {
        ChatUtil.sendActionBar(player,message);
    }),
    TITLE(player -> message -> {
        ChatUtil.sendTitle(player,message);
    }),
    SUBTITLE(player -> message -> {
        ChatUtil.sendSubtitle(player,message);
    }),
    CHAT(player -> player::sendMessage),
    DISABLED(player -> message -> {
    });

    private final Function<Player, Consumer<Component>> object;

    MessageType(Function<Player, Consumer<Component>> object) {
        this.object = object;
    }
    public void send(Player player, Component message){
        this.object.apply(player).accept(message);
    }
}

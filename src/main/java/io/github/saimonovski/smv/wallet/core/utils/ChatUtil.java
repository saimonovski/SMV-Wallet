package io.github.saimonovski.smv.wallet.core.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.ArrayList;
import java.util.List;

public class ChatUtil {
  private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.builder()
          .character('&')
          .hexColors()
          .build();
  private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
  public static Component fix(String message) {
    if (message.contains("<") && message.contains(">")) {
      try {
        return
                Component.text().append(MINI_MESSAGE.deserialize(message)).build();

      } catch (Exception e) {
        return LEGACY_SERIALIZER.deserialize(message);
      }
    }
    return LEGACY_SERIALIZER.deserialize(message);
    }
    public static List<Component> fix(List<String> list){
      List<Component> components = new ArrayList<>();
        for (String s : list) {
            components.add(fix(s));
        }
        return components;
    }
}

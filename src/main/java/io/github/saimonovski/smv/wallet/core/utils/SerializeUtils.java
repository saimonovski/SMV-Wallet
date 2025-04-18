package io.github.saimonovski.smv.wallet.core.utils;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import io.github.saimonovski.smv.wallet.messages.ChatUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SerializeUtils {

    public static ItemStack loadItemStack(Section itemStackSection) {
        String name = itemStackSection.getString("display-name","");
        List<String> lore = itemStackSection.getStringList("lore", new ArrayList<>());
        int customModelData = itemStackSection.getInt("model-data", -1);
        boolean shouldGlow = itemStackSection.getBoolean("should-glow", false);
        Material material = itemStackSection.getEnum( "material", Material.class, Material.BARRIER);
        int amount = itemStackSection.getInt("itemAmount", 1);
        ItemStack stack = ItemStack.of(material,amount);
        stack.editMeta(meta -> {
            meta.displayName(ChatUtil.fix(name));
            meta.lore(ChatUtil.fix(lore));
            if(customModelData != -1) meta.setCustomModelData(customModelData);
            if(shouldGlow) meta.setEnchantmentGlintOverride(true);
        });
        return stack;
    }
    public static int loadSlot(Section section){
        return section.getInt("slot", 0);
    }
    public static Material loadFillMaterial(Section section){
        return section.getEnum("fill-material", Material.class, Material.AIR);
    }
    public static int loadSize(Section section){
        return section.getInt("size", 54);
    }

    @Nullable
    public static Section databaseSection(YamlDocument config){
        return config.getSection("database");
    }
    @NotNull
    public static Component loadTitle(Section section){
        return ChatUtil.fix(section.getString("title-gui"));
    }
}

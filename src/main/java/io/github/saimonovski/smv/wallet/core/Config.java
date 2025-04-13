package io.github.saimonovski.smv.wallet.core;

import io.github.saimonovski.smv.wallet.Wallet;
import io.github.saimonovski.smv.wallet.api.guis.MainGui;
import org.jetbrains.annotations.NotNull;

public class Config implements io.github.saimonovski.smv.wallet.api.object.Config {
    private final Wallet wallet;

    @Override
    public @NotNull MainGui mainGui() {
        MainGui.Builder gui = MainGui.builder()
                .setSize(getMainGuiSize() %9 == 0 ? getMainGuiSize() : 54)
                .setTitle(ChatUtil.fix(getMainGuiTitle()));

        categories().forEach(gui::addCategory);
        return gui.build();
    }
}
